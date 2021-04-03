MySQL(InnoDB)事务隔离级别(read uncommitted)与锁
======

###### 本次测试使用的是mysql8.0.23版本
### 准备一张测试表test_transaction:
```sql
DROP TABLE IF EXISTS `test_transaction`;
CREATE TABLE `test_transaction` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` char(20) NOT NULL COMMENT '姓名',
  `age` tinyint(3) NOT NULL COMMENT '年龄',
  `gender` tinyint(1) NOT NULL COMMENT '1:男, 2:女',
  `desctiption` text NOT NULL COMMENT '简介',
  PRIMARY KEY (`id`),
  KEY `name_age_gender_index` (`user_name`,`age`,`gender`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

INSERT INTO `test_transaction` VALUES (1, '金刚狼', 127, 1, '我有一双铁爪');
INSERT INTO `test_transaction` VALUES (2, '钢铁侠', 120, 1, '我有一身铁甲');
INSERT INTO `test_transaction` VALUES (3, '绿巨人', 0, 2, '我有一身肉');
```
### 演该隔离级别脏读效果
#### 先查看当前会话(当前客户端)事务的隔离级别: select @@transaction_isolation;                  
可以看到: REPEATABLE READ 是InnoDB存储引擎的默认事务隔离级别                  
```sql
mysql> select @@transaction_isolation;
+-------------------------+
| @@transaction_isolation |
+-------------------------+
| REPEATABLE-READ         |
+-------------------------+
1 row in set (0.00 sec)

mysql> set session transaction isolation level read uncommitted;
Query OK, 0 rows affected (0.00 sec)

mysql>                  
```
#### 重新设置当前客户端事务隔离级别为read uncommitted                           
注意:此时只是当前会话端的隔离级别被改, 其余客户端连接自然还是默认的REPEATABLE READ隔离级别                       
```sql
mysql> set session transaction isolation level read uncommitted;
Query OK, 0 rows affected (0.00 sec)

mysql>
mysql>
mysql> select @@transaction_isolation;
+-------------------------+
| @@transaction_isolation |
+-------------------------+
| READ-UNCOMMITTED        |
+-------------------------+
1 row in set (0.00 sec)

mysql>
```
#### 接下来将客户端2的事务隔离级别也设置为read uncommitted;
![read_uncommitted](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/read_uncommitted.png)

#### 客户端1开启事务,并执行一个查询'读取数据':
```sql
mysql> select @@transaction_isolation;
+-------------------------+
| @@transaction_isolation |
+-------------------------+
| READ-UNCOMMITTED        |
+-------------------------+
1 row in set (0.00 sec)

mysql> begin;
Query OK, 0 rows affected (0.00 sec)

mysql> select * from test_transaction where id=2;
+----+-----------+-----+--------+--------------------+
| id | user_name | age | gender | desctiption        |
+----+-----------+-----+--------+--------------------+
|  2 | 钢铁侠    | 120 |      1 | 我有一身铁甲       |
+----+-----------+-----+--------+--------------------+
1 row in set (0.00 sec)

mysql>
```
注意:客户端1此时的事务并未提交                
#### 客户端2开启事务, 并修改客户端1查询的数据
```sql
mysql> select @@transaction_isolation;
+-------------------------+
| @@transaction_isolation |
+-------------------------+
| READ-UNCOMMITTED        |
+-------------------------+
1 row in set (0.00 sec)

mysql> update test_transaction set user_name='钢铁侠-托尼' where id=2;
Query OK, 1 row affected (0.00 sec)
Rows matched: 1  Changed: 1  Warnings: 0

mysql>
```
注意:客户端2此时的事务也并未提交               
> 此时发现, 客户端2可以对客户端1正在读取的记录进行修改, 而根据锁相关知识, 如果说客户端1在读取记录的时候加了S锁, 
那么客户端2是不能加X锁对该记录进行更改的, 所以可以得出结论: 要么是客户端1读取记录的时候没有加S锁, 
要么是客户端2更改记录的时候不加任何锁(这样即使客户端1加了S锁,对它这个不加锁的事务也无可奈何), 那么究竟是哪中情况导致的?                            
#### 切换到客户端1, 再次查询数据, 发现数据已经变成了'钢铁侠-托尼'; 然后客户端2 rollback 事务, 再到客户端1中查询,发现user_name又变成了'钢铁侠', 那之前独到'钢铁侠-托尼'就是脏数据了, 这就是一次 脏读
![read_uncommitted2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/read_uncommitted2.png)

### 分析读未提交隔离级别如何加锁
#### 重新构造测试条件
![read_uncommitted3](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/read_uncommitted3.png)
#### 客户端1开启事务, 然后对数据做修改
```sql
mysql> select @@transaction_isolation;
+-------------------------+
| @@transaction_isolation |
+-------------------------+
| READ-UNCOMMITTED        |
+-------------------------+
1 row in set (0.00 sec)

mysql> begin;
Query OK, 0 rows affected (0.00 sec)

mysql> update test_transaction set user_name='钢铁侠-rymuscle' where id=2;
Query OK, 1 row affected (0.00 sec)
Rows matched: 1  Changed: 1  Warnings: 0

mysql>
```
注意:客户端1此时的事务并未提交
#### 客户端2开启事务, 对相同的数据行做修改
```sql
mysql> select @@transaction_isolation;
+-------------------------+
| @@transaction_isolation |
+-------------------------+
| READ-UNCOMMITTED        |
+-------------------------+
1 row in set (0.00 sec)

mysql> begin;
Query OK, 0 rows affected (0.00 sec)

mysql> update test_transaction set user_name='钢铁侠-rym' where id=2;
ERROR 1205 (HY000): Lock wait timeout exceeded; try restarting transaction
mysql>
```
#### 在上面的过程, 在客户端2阻塞阶段, 你可以通过一个新的客户端来分析, 客户端2在锁等待的情况下的加锁情况和事务状态:
查看表的加锁情况:select * from sys.`innodb_lock_waits`;                                     
![read_uncommitted4](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/read_uncommitted4.png)                                     
事务状态:select * from information_schema.INNODB_TRX;                   
![read_uncommitted5](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/read_uncommitted5.png)                                                          
#### READ UNCOMMITTED 隔离级别下, 写操作是会加锁的, 而且是X排他锁, 直到客户端1事务完成, 锁才释放, 客户端2才能进行写操作
> "既然该隔离级别下事务在修改数据的时候加的是x锁, 并且是事务完成后才释放, 那之前的测试客户端2在事务中修改完数据之后, 为什么事务还没完成,
 也就是x锁还在, 结果客户端1却能读取到客户端2修改的数据"？这完全不符合排他锁的特性啊(要知道,排他锁会阻塞除当前事务之外的其他事务的读,写操作)
> 在READ UNCOMMITTED级别运行的事务不会发出共享锁来防止其他事务修改当前事务读取的数据, 既然不加共享锁了, 那么当前事务所读取的数据自然就可以被其他事务来修改。
  而且当前事务要读取其他事务未提交的修改, 也不会被排他锁阻止, 因为排他锁会阻止其他事务再对其锁定的数据加读写锁,事务在该隔离级别下去读数据的话根本什么锁都不加, 这就让排他锁无法排它了, 因为它连锁都没有。                  
  这就导致了事务可以读取未提交的修改, 称为脏读。                  
### READ UNCOMMITTED隔离级别下, 读不会加任何锁。而写会加排他锁，并到事务结束之后释放。
           
> 参考 https://segmentfault.com/a/1190000012654564