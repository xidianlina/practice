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

