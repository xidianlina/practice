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


