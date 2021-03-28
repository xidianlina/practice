mysql练习
======
# 一.基础知识
## 问题列表
### 1.数据库事务
### 2.数据库事务特性
### 3.事务隔离级别
### 4.mysql数据库的基本架构
### 5.数据库索引的优缺点
### 6.数据库索引失效
### 7.数据库引擎
### 8.索引分类
### 9.索引的底层实现
### 10.最左匹配原则
### 11.关系型数据库和非关系型数据库区别
### 12.sql中drop、delete和truncate的区别
### 13.数据库范式
## 问题答案
### 1.数据库事务
> 数据库事务(transaction)是访问并可能操作各种数据项的一个数据库操作序列，                
> 这些操作要么全部执行,要么全部不执行，是一个不可分割的工作单位。              
> 事务由事务开始与事务结束之间执行的全部数据库操作组成。           
### 2.数据库事务特性
> ACID四个特性：             
  1.A (Atomicity) 原子性               
  事务里的所有操作要么全部做完，要么都不做，事务成功的条件是事务里的所有操作都成功，只要有一个操作失败，整个事务就失败，需要回滚。              
  比如银行转账，从A账户转100元至B账户，分为两个步骤：1）从A账户取100元；2）存入100元至B账户。这两步要么一起完成，要么一起不完成，如果只完成第一步，第二步失败，钱会莫名其妙少了100元。               
  2.C (Consistency) 一致性             
  数据库要一直处于一致的状态，事务的运行不会改变数据库原本的一致性约束。               
  例如现有完整性约束a+b=10，如果一个事务改变了a，那么必须得改变b，使得事务结束后依然满足a+b=10，否则事务失败。             
  3.I (Isolation) 隔离性               
  隔离性是指并发的事务之间不会互相影响，如果一个事务要访问的数据正在被另外一个事务修改，只要另外一个事务未提交，它所访问的数据就不受未提交事务的影响。            
  比如现在有个交易是从A账户转100元至B账户，在这个交易还未完成的情况下，如果此时B查询自己的账户，是看不到新增加的100元的。          
  4.D (Durability) 持久性              
  持久性是指一旦事务提交后，它所做的修改将会永久的保存在数据库上，即使出现宕机也不会丢失。          
>               
> 事务的ACID是通过InnoDB日志和锁来保证。                            
> 事务的隔离性是通过数据库锁的机制实现的。              
> 持久性通过redo log（重做日志）来实现。           
> 原子性和一致性通过Undo log（回撤日志）来实现。               
> Undo Log的原理很简单，为了满足事务的原子性，在操作任何数据之前，首先将数据备份到一个地方（这个存储数据备份的地方称为Undo Log）。然后进行数据的修改。如果出现了错误或者用户执行了roll back语句，系统可以利用Undo Log中的备份将数据恢复到事务开始之前的状态。                    
> 和Undo Log相反，Redo Log记录的是新数据的备份。在事务提交前，只要将RedoLog持久化即可，不需要将数据持久化。当系统崩溃时，虽然数据没有持久化，但是RedoLog已经持久化。系统可以根据Redo Log的内容，将所有数据恢复到最新的状态。                            
### 3.事务隔离级别
> 第一种隔离级别：Read uncommitted(读未提交)            
> 如果一个事务已经开始写数据，则另外一个事务不允许同时进行写操作，但允许其他事务读此行数据，该隔离级别可以通过“排他写锁”，但是不排斥读线程实现。
> 这样就避免了更新丢失，却可能出现脏读，也就是说事务B读取到了事务A未提交的数据。          
> 读未提交解决了数据更新丢失，但可能会出现脏读。脏读就是一个事务在处理过程中读取了另外一个事务未提交的数据。             
  这种未提交的数据称之为脏数据。依据脏数据所做的操作肯能是不正确的。  
>                         
> 第二种隔离级别：Read committed(读已提交)           
> 如果是一个读事务(线程)，则允许其他事务读写，如果是写事务将会禁止其他事务访问该行数据，该隔离级别避免了脏读，但是可能出现不可重复读。
> 事务A事先读取了数据，事务B紧接着更新了数据，并提交了事务，而事务A再次读取该数据时，数据已经发生了改变。         
> 解决了更新丢失和脏读问题,但可能会出现不可重复读。不可重复读是指一个事务范围内，多次查询某个数据，却得到不同的结果。
> 在第一个事务中的两次读取数据之间，由于第二个事务的修改，第一个事务两次读到的数据可能就是不一样的。
>                        
> 第三种隔离级别：Repeatable read(可重复读取)            
> 可重复读取是指在一个事务内，多次读同一个数据，在这个事务还没结束时，其他事务不能访问该数据(包括了读写)，
> 这样就可以在同一个事务内两次读到的数据是一样的。读取数据的事务将会禁止写事务(但允许读事务)，写事务则禁止任何其他事务(包括了读写)。               
> 解决了更新丢失、脏读问题和不可重复读，但可能会出现幻读。          
> 幻读是事务非独立执行时发生的一种现象。               
  例如事务A对一个表中所有的行的某个数据项做了从“1”修改为“2”的操作，这时事务B又对这个表中插入了一行数据项为“1”的数据，并且提交给数据库。
  而操作事务A的用户如果再查看刚刚修改的数据，会发现数据怎么还是1？其实这行是从事务B中添加的，就好像产生幻觉一样，这就是发生了幻读。 
>                                      
> 第四种隔离级别：Serializable(可序化)         
> 提供严格的事务隔离，它要求事务序列化执行，事务只能一个接着一个地执行，但不能并发执行，如果仅仅通过“行级锁”是无法实现序列化的，
> 必须通过其他机制保证新插入的数据不会被执行查询操作的事务访问到。序列化是最高的事务隔离级别，同时代价也是最高的，性能很低，一般很少使用，
> 在该级别下，事务顺序执行，不仅可以避免脏读、不可重复读，还避免了幻读。  
  ![mysql1](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/mysql1.png)        
> 脏读、不可重复读和幻读，其实都是数据库读一致性问题，必须由数据库提供一定的事务隔离机制来解决。               
> 数据库实现事务隔离的方式，基本上可分为以下两种：              
  一种是在读取数据前，对其加锁，阻止其他事务对数据进行修改。             
  另一种是不用加任何锁，通过一定机制生成一个数据请求时间点的一致性数据快照（Snapshot)，并用这个快照来提供一定级别（语句级或事务级）的一致性读取。从用户的角度来看，好像是数据库可以提供同一数据的多个版本，因此，这种技术叫做数据多版本并发控制（MultiVersion Concurrency Control，简称MVCC或MCC），也经常称为多版本数据库。             
  数据库的事务隔离越严格，并发副作用越小，但付出的代价也就越大，因为事务隔离实质上就是使事务在一定程度上 “串行化”进行，这显然与“并发”是矛盾的。同时，不同的应用对读一致性和事务隔离程度的要求也是不同的，比如许多应用对“不可重复读”和“幻读”并不敏感，可能更关心数据并发访问的能力。          
>               
> 脏读是某一事务读取了另外一个事务未提交的数据，不可重复读是读取了其他事务提交的数据。            
  幻读和不可重复读都是读取了另一条已经提交的事务（这点就脏读不同），所不同的是不可重复读查询的都是同一个数据项，而幻读针对的是一批数据整体（比如数据的个数）。                
  不可重复读重点在于update和delete，而幻读的重点在于insert。避免不可重复读需要锁行（某一行在select操作时，不允许update与delete）就行，避免幻读则需要锁表。
> 如果使用锁机制来实现这两种隔离级别，在可重复读中，该sql第一次读取到数据后，就将这些数据加锁，其它事务无法修改这些数据，就可以实现可重复读了。但这种方法却无法锁住insert的数据，所以当事务A先前读取了数据，或者修改了全部数据，事务B还是可以insert数据提交，这时事务A就会发现莫名其妙多了一条之前没有的数据，幻读不能通过行锁来避免，需要Serializable隔离级别 ，读用读锁，写用写锁，读锁和写锁互斥，这么做可以有效的避免幻读、不可重复读、脏读等问题，但会极大的降低数据库的并发能力。所以说不可重复读和幻读最大的区别，就在于如何通过锁机制来解决他们产生的问题。          
>                   
> 在MYSQL数据库中，支持上面四种隔离级别，默认的为Repeatable read(可重复读)。              
> 隔离级别越高，越能保证数据的完整性和统一性，但是执行效率就越低，对并发性能的影响也越大。像Serializable这样的级别，就是以锁表的方式（类似于Java多线程中的锁）使得其他的线程只能在锁外等待，所以平时选用何种隔离级别应该根据实际情况。                    
  Oracle仅仅实现了RC和SERIALIZABLE隔离级别。默认采用RC隔离级别，解决了脏读。但是允许不可重复读和幻读。其SERIALIZABLE则解决了脏读、不可重复读、幻读。                
> MySQL支持全部4个隔离级别，但在具体实现时，有一些特点，比如在一些隔离级别下是采用MVCC一致性读，但某些情况下又不是。MySQL默认采用RR隔离级别，SQL标准是要求RR解决不可重复读的问题，但是因为MySQL通过nex-key lock在RR隔离级别下解决了幻读的问题。MySQL的SERIALIZABLE采用了经典的实现方式，对读和写都加锁。              
>                       
> 隔离级别的实现               
> 事务的机制是通过视图（read-view）来实现的并发版本控制（MVCC），不同的事务隔离级别创建读视图的时间点不同。               
  可重复读是每个事务重建读视图，整个事务存在期间都用这个视图。                
  读已提交是每条SQL创建读视图，在每个SQL语句开始执行的时候创建的。隔离作用域仅限该条SQL语句。            
  读未提交是不创建，直接返回记录上的最新值。         
  串行化隔离级别下直接用加锁的方式来避免并行访问。              
  视图可以理解为数据副本，每次创建视图时，将当前已持久化的数据创建副本，后续直接从副本读取，从而达到数据隔离效果。   
> ![mysql2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/mysql2.png)            
>           
> 参考 http://blog.itpub.net/31559358/viewspace-2221403/                                   
### 4.mysql数据库的基本架构
![mysql3](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/mysql3.png)        
![mysql4](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/mysql4.png)        
### 5.数据库索引的优缺点
### 6.数据库索引失效
### 7.数据库引擎
### 8.索引分类
### 9.索引的底层实现
### 10.最左匹配原则
### 11.关系型数据库和非关系型数据库区别
### 12.sql中drop、delete和truncate的区别
### 13.数据库范式
# 二.题目练习
## 1.查找最晚入职员工的所有信息
题目描述            
有一个员工employees表简况如下:
![sql1](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql1.jpg)
建表语句如下:     
CREATE TABLE `employees` (      
`emp_no` int(11) NOT NULL,          
`birth_date` date NOT NULL,         
`first_name` varchar(14) NOT NULL,          
`last_name` varchar(16) NOT NULL,               
`gender` char(1) NOT NULL,          
`hire_date` date NOT NULL,          
PRIMARY KEY (`emp_no`));                
请你查找employees里最晚入职员工的所有信息，以上例子输出如下:         
![sql1_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql1_2.jpg)
### solution:
> select * from employees where hire_date = (select max(hire_date) from employees);             
  select * from employees order by hire_date desc limit 1;          
  select * from employees order by hire_date desc limit 0,1;        
  select * from employees order by hire_date desc limit 1 offset 0;       
## 2.查找入职员工时间排名倒数第三的员工所有信息
题目描述        
有一个员工employees表简况如下:        
![sql2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql2.jpg)
建表语句如下:     
CREATE TABLE `employees` (      
`emp_no` int(11) NOT NULL,      
`birth_date` date NOT NULL,       
`first_name` varchar(14) NOT NULL,        
`last_name` varchar(16) NOT NULL,       
`gender` char(1) NOT NULL,        
`hire_date` date NOT NULL,        
PRIMARY KEY (`emp_no`));      
请你查找employees里入职员工时间排名倒数第三的员工所有信息，以上例子输出如下:   
![sql2_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql2_2.jpg)
### solution:
>  select * from employees order by hire_date desc limit 2,1;    
  select * from employees order by hire_date desc limit 1 offset 2;       
  select * from employees where hire_date =( select distinct hire_date from employees order by hire_date desc limit 1 offset 2);    
## 3.查找各个部门当前领导当前薪水详情以及其对应部门编号dept_no
题目描述    
有一个全部员工的薪水表salaries和一个各个部门的领导表dept_manager，     
建表语句如下:     
CREATE TABLE `salaries` (       
`emp_no` int(11) NOT NULL,        
`salary` int(11) NOT NULL,        
`from_date` date NOT NULL,        
`to_date` date NOT NULL,      
PRIMARY KEY (`emp_no`,`from_date`));        
          
CREATE TABLE `dept_manager` (       
`dept_no` char(4) NOT NULL,         
`emp_no` int(11) NOT NULL,        
`to_date` date NOT NULL,        
PRIMARY KEY (`emp_no`,`dept_no`));        
请你查找各个部门领导薪水详情以及其对应部门编号dept_no，输出结果以salaries.emp_no升序排序，并且请注意输出结果里面dept_no列是最后一列，以上例子输入如下:      
![sql3](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql3.jpg)
### solution:
> select salaries.*,dept_manager.dept_no from salaries inner join dept_manager on dept_manager.emp_no=salaries.emp_no where dept_manager.to_date='9999-01-01' and salaries.to_date='9999-01-01';    
> select salaries.*,dept_manager.dept_no from salaries , dept_manager where dept_manager.emp_no=salaries.emp_no and dept_manager.to_date='9999-01-01' and salaries.to_date='9999-01-01';
## 4.查找所有已经分配部门的员工的last_name和first_name
题目描述      
有一个员工表employees和部门表dept_emp，      
建表语句如下:     
CREATE TABLE `employees` (      
`emp_no` int(11) NOT NULL,        
`birth_date` date NOT NULL,       
`first_name` varchar(14) NOT NULL,        
`last_name` varchar(16) NOT NULL,       
`gender` char(1) NOT NULL,        
`hire_date` date NOT NULL,        
PRIMARY KEY (`emp_no`));        
      
CREATE TABLE `dept_emp` (       
`emp_no` int(11) NOT NULL,        
`dept_no` char(4) NOT NULL,         
`from_date` date NOT NULL,        
`to_date` date NOT NULL,        
PRIMARY KEY (`emp_no`,`dept_no`));        
请查找所有已经分配部门的员工的last_name和first_name以及dept_no，未分配的部门的员工不显示，以上例子如下:       
![sql4](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql4.jpg)
### solution:
> select e.last_name,e.first_name,d.dept_no from dept_emp d natural join employees e;   
> select e.last_name,e.first_name,d.dept_no from employees as e inner join dept_emp as d on e.emp_no = d.emp_no;
### 知识点
> Join操作基本分为3大类：外连接(细分为：左连接、右连接、全连接)、自然连接、内连接   
> Join操作的共性：第一步均为将所有参与操作的表进行了一个笛卡儿积，然后才依据各连接条件进行记录的筛选     
> 自然连接是通过对参与表关系中所有同名的属性对取等（即相等比较）来完成的，故无须自己添加连接条件
与外连接的区别在于对于无法匹配的记录外连接会虚拟一条与之匹配的记录来保全连接表中的所有记录，但自然连接不会       
内连接与自然连接基本相同，不同之处在于自然连接只能是同名属性的等值连接，而内连接可以使用using或on子句来指定连接条件，
连接条件中指出某两字段相等（可以不同名）。     
> 条件连接就是在多个表的笛卡尔积中选取满足条件的行的连接，例如  select * from A,B where A.a > A.b  之类的有条件的查询。     
等值连接就是特殊的条件连接，当条件为某字段=某字段时，即为等值连接。如SELECT ename,sal,dname FROM emp,dept WHERE emp.deptno=dept.deptno;       
自然连接是一种特殊的等值连接，他要求多个表有相同的属性字段，然后条件为相同的属性字段值相等，最后再将表中重复的属性字段去掉，
即为自然连接。如A中a,b,c字段，B中有c,d字段，则select * from A natural join B  相当于 select A.a,A.b,A.c,B.d from A.c = B.c  。      
> 内连接与等值连接的区别：      
内连接：两个表（或连接）中某一数据项相等的连接称为内连接。等值连接一般用where字句设置条件，内连接一般用on字句设置条件，
但内连接与等值连接效果是相同的
## 5.查找所有员工的last_name和first_name以及对应部门编号dept_no
题目描述        
有一个员工表employees和一个部门表dept_emp，      
建表语句如下:             
CREATE TABLE `employees` (              
`emp_no` int(11) NOT NULL,              
`birth_date` date NOT NULL,             
`first_name` varchar(14) NOT NULL,              
`last_name` varchar(16) NOT NULL,               
`gender` char(1) NOT NULL,              
`hire_date` date NOT NULL,              
PRIMARY KEY (`emp_no`));                
            
CREATE TABLE `dept_emp` (               
`emp_no` int(11) NOT NULL,              
`dept_no` char(4) NOT NULL,             
`from_date` date NOT NULL,              
`to_date` date NOT NULL,                
PRIMARY KEY (`emp_no`,`dept_no`));          
请你查找所有已经分配部门的员工的last_name和first_name以及dept_no，也包括暂时没有分配具体部门的员工，以上例子如下:          
![sql5](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql5.jpg)
### solution
> select e.last_name,e.first_name,d.dept_no from employees as e left join dept_emp as d on e.emp_no = d.emp_no;             
                
>INNER JOIN 两边表同时有对应的数据，即任何一边缺失数据就不显示。              
 LEFT JOIN 会读取左边数据表的全部数据，即便右边表无对应数据。                
 RIGHT JOIN 会读取右边数据表的全部数据，即便左边表无对应数据。               
 on与where有什么区别，两个表连接时用on，在使用left  jion时，on和where条件的区别如下：                
 1、on条件是在生成临时表时使用的条件，它不管on中的条件是否为真，都会返回左边表中的记录。             
 2、where条件是在临时表生成好后，再对临时表进行过滤的条件。这时已经没有left join的含义(必须返回左边表的记录)了，条件不为真的就全部过滤掉。                   
## 6.查找所有员工入职时候的薪水情况
题目描述:查找所有员工入职时候的薪水情况，给出emp_no以及salary， 并按照emp_no进行逆序        
CREATE TABLE `employees` (          
`emp_no` int(11) NOT NULL,          
`birth_date` date NOT NULL,         
`first_name` varchar(14) NOT NULL,          
`last_name` varchar(16) NOT NULL,
`gender` char(1) NOT NULL,              
`hire_date` date NOT NULL,          
PRIMARY KEY (`emp_no`));            
            
CREATE TABLE `salaries` (           
`emp_no` int(11) NOT NULL,          
`salary` int(11) NOT NULL,              
`from_date` date NOT NULL,              
`to_date` date NOT NULL,            
PRIMARY KEY (`emp_no`,`from_date`));               
### solution
> select e.emp_no, s.salary from employees as e inner join salaries as s on e.emp_no = s.emp_no and e.hire_date = s.from_date order by e.emp_no desc            
  select e.emp_no, s.salary from employees e,salaries s where e.emp_no = s.emp_no and e.hire_date = s.from_date order by e.emp_no desc          
  select emp_no,salary from salaries group by emp_no having min(from_date) order by emp_no desc             
## 7.查找薪水涨幅超过15次的员工号emp_no以及其对应的涨幅次数t
题目描述        
有一个薪水表，salaries,            
建表语句如下:         
CREATE TABLE `salaries` (               
`emp_no` int(11) NOT NULL,              
`salary` int(11) NOT NULL,              
`from_date` date NOT NULL,              
`to_date` date NOT NULL,                
PRIMARY KEY (`emp_no`,`from_date`));                
请你查找薪水记录超过15次的员工号emp_no以及其对应的记录次数t，以上例子输出如下:            
![sql7](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql7.jpg)
### solution
> select emp_no,count(emp_no) t from salaries group by emp_no having t>15;
## 8.找出所有员工当前具体的薪水salary情况
题目描述            
有一个薪水表salaries,             
建表语句如下:             
CREATE TABLE `salaries` (               
`emp_no` int(11) NOT NULL,              
`salary` int(11) NOT NULL,              
`from_date` date NOT NULL,              
`to_date` date NOT NULL,                    
PRIMARY KEY (`emp_no`,`from_date`));                    
请你找出所有员工具体的薪水salary情况，对于相同的薪水只显示一次,并按照逆序显示，以上例子输出如下:           
![res4](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/res4.jpg)
### solution
> select distinct salary from salaries order by salary desc;            
> select salary from salaries group by salary order by salary desc;
## 9.获取所有部门当前manager的当前薪水情况
题目描述
获取所有部门当前manager的当前薪水情况，给出dept_no, emp_no以及salary，当前表示to_date='9999-01-01'           
CREATE TABLE `dept_manager` (               
`dept_no` char(4) NOT NULL,             
`emp_no` int(11) NOT NULL,              
`from_date` date NOT NULL,                  
`to_date` date NOT NULL,                    
PRIMARY KEY (`emp_no`,`dept_no`));                  
                
CREATE TABLE `salaries` (               
`emp_no` int(11) NOT NULL,                  
`salary` int(11) NOT NULL,                      
`from_date` date NOT NULL,                      
`to_date` date NOT NULL,                
PRIMARY KEY (`emp_no`,`from_date`));                
### solution
> select d.dept_no,d.emp_no,s.salary from salaries as s inner join demp_manager as d on d.emp_no=s.emp_no and d.to_date='9999-01-01' and s.to_date=9999-01-01'';                
> select dept_no,dept_manager.emp_no,salary from salaries,demp_manager where salaries.emp_no=dept_manager.emp_no and salaries.to_date='9999-01-01' and dept_manager.to_date='9999-01-01';
## 10.获取所有非manager的员工emp_no
题目描述            
有一个员工表employees和一个部门领导表dept_manager，            
建表语句如下:             
CREATE TABLE `employees` (                  
`emp_no` int(11) NOT NULL,              
`birth_date` date NOT NULL,                 
`first_name` varchar(14) NOT NULL,                  
`last_name` varchar(16) NOT NULL,                   
`gender` char(1) NOT NULL,                  
`hire_date` date NOT NULL,                  
PRIMARY KEY (`emp_no`));                    
                    
CREATE TABLE `dept_manager` (                   
`dept_no` char(4) NOT NULL,                     
`emp_no` int(11) NOT NULL,                  
`from_date` date NOT NULL,              
`to_date` date NOT NULL,                    
PRIMARY KEY (`emp_no`,`dept_no`));                  
请你找出所有非部门领导的员工emp_no，以上例子输出:                                       
![res5](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/res5.jpg)
### solution
> select emp_no from employees where emp_no not in (select emp_no from dept_manager);               
> select employees.emp_no from employees left join dept_manager on employees.emp_no = dept_manager.emp_no where dept_no is null;            
> select em.emp_no from employees em where not exists(select distinct dm.emp_no from dept_manager dm where em.emp_no=dm.emp_no);            
> 使用not in时,如果子查询中返回的任意一条记录包含空值，则查询不返回任何记录，而且not in会对内外表进行全表扫描，没有用到索引。而not exists子查询仍然会用到索引，所以无论那个表大，not exists都会比not in快。另外，如果子查询表大适合用exists，表小适合用in。
## 11.获取所有员工当前的manager
题目描述                
有一个员工表dept_emp简况如下:                             
![dept_emp](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/dept_emp.jpg)                  
有一个部门经理表dept_manager简况如下:                                  
![dept_manager](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/dept_manager.jpg)                  
获取所有的员工和员工对应的经理，如果员工本身是经理的话则不显示，以上例子如下:                                 
![emp_manager](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/emp_manager.jpg)                    
### solution
> select de.emp_no, dm.emp_no as manager_no from dept_emp as de inner join dept_manager as dm on de.dept_no = dm.dept_no where dm.to_date = '9999-01-01' and de.to_date = '9999-01-01' and de.emp_no <> dm.emp_no;             
> select e.emp_no,m.emp_no manager_no from dept_emp e,dept_manager m where e.dept_no=m.dept_no and e.to_date='9999-01-01' and m.to_date='9999-01-01' and e.emp_no!=m.emp_no;        
## 12.获取所有部门中当前员工薪水最高的相关信息
题目描述            
有一个员工表dept_emp简况如下:                
![sql12](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql12.jpg)                
有一个薪水表salaries简况如下:                     
![sql12_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql12_2.jpg)                
获取所有部门中员工薪水最高的相关信息，给出dept_no, emp_no以及其对应的salary，按照部门编号升序排列，以上例子输出如下:                   
![sql12_3](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql12_3.jpg)                
### solution
> select de.dept_no,de.emp_no,max(s.salary) from dept_emp de inner join salaries s on de.emp_no=s.emp_no where de.to_date = '9999-01-01' and s.to_date = '9999-01-01' group by de.dept_no order by de.dept_no;              
> select e.dept_no, e.emp_no, s.salary from dept_emp as e inner join  salaries as s on e.emp_no = s.emp_no where e.to_date = '9999-01-01' and s.to_date = '9999-01-01' group by e.dept_no having s.salary = max(s.salary) order by e.dept_no;           
## 13.从titles表获取按照title进行分组
题目描述                
从titles表获取按照title进行分组，每组个数大于等于2，给出title以及对应的数目t。            
CREATE TABLE IF NOT EXISTS "titles" (               
`emp_no` int(11) NOT NULL,              
`title` varchar(50) NOT NULL,               
`from_date` date NOT NULL,              
`to_date` date DEFAULT NULL);               
### solution
> select title,count(title) t from titles group by title having t>=2;
## 14.从titles表获取按照title进行分组
题目描述                    
从titles表获取按照title进行分组，每组个数大于等于2，给出title以及对应的数目t。                
注意对于重复的emp_no进行忽略。              
CREATE TABLE IF NOT EXISTS "titles" (               
`emp_no` int(11) NOT NULL,              
`title` varchar(50) NOT NULL,               
`from_date` date NOT NULL,                  
`to_date` date DEFAULT NULL);      
### solution         
> select title,count(distinct emp_no) as t from titles group by title having t>=2;
> select title,count(emp_no) as t from (select distinct emp_no,title from titles) group by title having t>=2;
## 15.查找employees表所有emp_no为奇数
题目描述            
有一个员工表employees简况如下:            
![sql15](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql15.jpg)
建表语句如下:             
CREATE TABLE `employees` (          
`emp_no` int(11) NOT NULL,              
`birth_date` date NOT NULL,                 
`first_name` varchar(14) NOT NULL,                  
`last_name` varchar(16) NOT NULL,               
`gender` char(1) NOT NULL,              
`hire_date` date NOT NULL,              
PRIMARY KEY (`emp_no`));                    
请你查找employees表所有emp_no为奇数，且last_name不为Mary的员工信息，并按照hire_date逆序排列，以上例子查询结果如下:                              
![sql15_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql15_2.jpg)
### solution
> select * from employees where emp_no%2=1 and last_name <> 'Mary' order by hire_date desc;         
> select * from employees where emp_no%2!=0 and last_name != 'Mary' order by hire_date desc;                
> select * from employees where emp_no%2!=0 and last_name not in ('Mary') order by hire_date desc;              
> select * from employees where emp_no&1 and last_name <> 'Mary' order by hire_date desc;           
> select * from employees where emp_no%2 and last_name != 'Mary' order by hire_date desc;
## 16.统计出当前各个title类型对应的员工当前薪水对应的平均工资
题目描述            
有一个员工职称表titles简况如下:                                 
![sql16](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql16.jpg)                
有一个薪水表salaries简况如下:                             
![sql16_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql16_2.jpg)                
建表语句如下:                           
CREATE TABLE titles (               
`emp_no` int(11) NOT NULL,              
`title` varchar(50) NOT NULL,               
`from_date` date NOT NULL,              
`to_date` date DEFAULT NULL);           
                
CREATE TABLE `salaries` (               
`emp_no` int(11) NOT NULL,                  
`salary` int(11) NOT NULL,                  
`from_date` date NOT NULL,              
`to_date` date NOT NULL,                
PRIMARY KEY (`emp_no`,`from_date`));                                 
请你统计出各个title类型对应的员工薪水对应的平均工资avg。结果给出title以及平均工资avg，并且以avg升序排序，以上例子输出如下:                           
![sql16_3](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql16_3.jpg)                
### solution
> select t.title,avg(s.salary) from salaries s inner join titles t on t.emp_no=s.emp_no and s.to_date='9999-01-01' and t.to_date='9999-01-01' group by t.title;         
> select t.title,avg(s.salary) from salaries s , titles t where t.emp_no=s.emp_no and s.to_date='9999-01-01' and t.to_date='9999-01-01' group by t.title;
## 17.获取当前薪水第二多的员工的emp_no以及其对应的薪水salary
题目描述                                
有一个薪水表salaries简况如下:                                            
![sql17](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql17.jpg)                
请你获取薪水第二多的员工的emp_no以及其对应的薪水salary                                      
![sql17_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql17_2.jpg)                
### solution
> select emp_no,salary from salaries where to_date='9999-01-01' order by salary desc limit 1,1;     
> select emp_no,max(salary) from salaries where salary < (select max(salary) from salaries);            
> select emp_no,salary from salaries where to_date='9999-01-01' and salary = (select distinct salary from salaries order by salary desc limit 1,1);     
> select emp_no,salary from salaries where to_date='9999-01-01' and salary = (select salary from salaries group by salary order by salary desc limit 1,1);
## 18.查找当前薪水排名第二多的员工编号emp_no
题目描述                                    
有一个员工表employees简况如下:                                      
![sql18](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql18.jpg)                
有一个薪水表salaries简况如下:                        
![sql18_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql18_2.jpg)                
请你查找薪水排名第二多的员工编号emp_no、薪水salary、last_name以及first_name，不能使用order by完成，以上例子输出为:                                    
![sql18_3](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql18_3.jpg)                
### solution
> select e.emp_no,max(s.salary),e.last_name,e.first_name from salaries s,employees e where s.to_date='9999-01-01' and e.emp_no=s.emp_no and s.salary < (select max(salary) from salaries);              
                                   
> select e.emp_no,max(s.salary),e.last_name,e.first_name from salaries s inner join employees e on e.emp_no=s.emp_no where s.to_date='9999-01-01' and s.salary < (select max(salary) from salaries);        
## 19.查找所有员工的last_name和first_name以及对应的dept_name
题目描述                                     
有一个员工表employees简况如下:                                         
![sql19](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql19.jpg)                
有一个部门表departments表简况如下:                             
![sql19_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql19_2.jpg)                
有一个，部门员工关系表dept_emp简况如下:                          
![sql19_3](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql19_3.jpg)                
请你查找所有员工的last_name和first_name以及对应的dept_name，也包括暂时没有分配部门的员工，以上例子输出如下:                             
![sql19_4](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql19_4.jpg)                                 
### solution
> select em.last_name,em.first_name,dp.dept_name
from (employees as em left join dept_emp as de on em.emp_no = de.emp_no)
left join departments as dp on de.dept_no = dp.dept_no;
## 20.查找员工编号emp_no为10001其自入职以来的薪水salary涨幅值growth
题目描述                
查找员工编号emp_no为10001其自入职以来的薪水salary涨幅值growth              
CREATE TABLE `salaries` (           
`emp_no` int(11) NOT NULL,              
`salary` int(11) NOT NULL,              
`from_date` date NOT NULL,              
`to_date` date NOT NULL,                
PRIMARY KEY (`emp_no`,`from_date`));                
### solution
> select (
(select salary from salaries where emp_no=10001 order by to_date desc limit 1) -
(select salary from salaries where emp_no=10001 order by to_date asc limit 1)
) as growth;                    
                                   
> select (max(salary)-min(salary)) as growth from salaries where emp_no=10001;
## 21.查找所有员工自入职以来的薪水涨幅情况
题目描述                    
有一个员工表employees简况如下:                
![sql21](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql21.jpg)                    
有一个薪水表salaries简况如下:                                       
![sql21_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql21_2.jpg)        
请你查找所有员工自入职以来的薪水涨幅情况，给出员工编号emp_no以及其对应的薪水涨幅growth，并按照growth进行升序，以上例子输出为             
（注:可能有employees表和salaries表里存在记录的员工，有对应的员工编号和涨薪记录，但是已经离职了，离职的员工salaries表的最新的to_date!='9999-01-01'，这样的数据不显示在查找结果里面，以上emp_no为2的就是这样的）                                                
![sql21_3](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql21_3.jpg)                                                     
### solution
> select cur.emp_no,(cur.salary-start.salary) as growth
  from (select s.emp_no,s.salary from employees e left join salaries s on e.emp_no=s.emp_no where s.to_date='9999-01-01') as cur
  inner join (select s.emp_no,s.salary from employees e left join salaries s on e.emp_no=s.emp_no where s.from_date=e.hire_date) as start
  on cur.emp_no=start.emp_no order by growth;               
                    
> select cur.emp_no,(cur.salary-start.salary) as growth
  from (select s.emp_no,s.salary from employees e , salaries s where e.emp_no=s.emp_no and s.to_date='9999-01-01') as cur
  , (select s.emp_no,s.salary from employees e , salaries s where e.emp_no=s.emp_no and s.from_date=e.hire_date) as start
  where cur.emp_no=start.emp_no order by growth;
## 22.统计各个部门的工资记录数
题目描述                
有一个部门表departments简况如下:                            
![sql22](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql22.jpg)            
有一个，部门员工关系表dept_emp简况如下:                        
![sql22_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql22_2.jpg)                
有一个薪水表salaries简况如下:                             
![sql22_3](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql22_3.jpg)                
请你统计各个部门的工资记录数，给出部门编码dept_no、部门名称dept_name以及部门在salaries表里面有多少条记录sum，按照dept_no升序排序，以上例子输出如下:                                     
![sql22_4](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql22_4.jpg)                    
### solution
> SELECT de.dept_no, dp.dept_name, COUNT(s.salary) AS sum 
  FROM (dept_emp AS de INNER JOIN salaries AS s ON de.emp_no = s.emp_no) 
  INNER JOIN departments AS dp ON de.dept_no = dp.dept_no 
  GROUP BY de.dept_no;              
>               
>select de.dept_no,dp.dept_name,count(s.salary) as sum
 from dept_emp as de , salaries as s , departments as dp
 where de.emp_no = s.emp_no and de.dept_no=dp.dept_no
 group by de.dept_no;
## 23.对所有员工的当前薪水按照salary进行按照1-N的排名
题目描述                
有一个薪水表salaries简况如下:             
![sql23](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql23.jpg)                           
对所有员工的薪水按照salary进行按照1-N的排名，相同salary并列且按照emp_no升序排列：                 
![sql23_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql23_2.jpg)                           
### solution
> select s1.emp_no,s1.salary,count(distinct s2.salary)
  from salaries s1,salaries s2
  where s1.to_date='9999-01-01' and s2.to_date='9999-01-01' and s1.salary<= s2.salary
  group by s1.emp_no
  order by s1.salary desc,s1.emp_no asc;
## 24.获取所有非manager员工当前的薪水情况
题目描述                    
有一个员工表employees简况如下:                    
![sql24](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql24.jpg)                                             
有一个，部门员工关系表dept_emp简况如下:                    
![sql24_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql24_2.jpg)                                             
有一个部门经理表dept_manager简况如下:               
![sql24_3](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql24_3.jpg)                                             
有一个薪水表salaries简况如下:             
![sql24_4](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql24_4.jpg)                                             
获取所有非manager员工薪水情况，给出dept_no、emp_no以及salary，以上例子输出:             
![sql24_5](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql24_5.jpg)                                             
### solution
> select de.dept_no, s.emp_no, s.salary 
  from (employees as e inner join salaries as s on s.emp_no = e.emp_no and s.to_date = '9999-01-01')
  inner join dept_emp as de on e.emp_no = de.emp_no
  where de.emp_no not in (select emp_no from dept_manager where to_date = '9999-01-01');
>       
>select de.dept_no, s.emp_no, s.salary 
 from dept_emp as de inner join salaries as s on s.emp_no = de.emp_no and s.to_date = '9999-01-01'
 where de.emp_no not in (select emp_no from dept_manager where to_date = '9999-01-01');     
>           
>select de.dept_no,de.emp_no,s.salary
 from dept_emp de,employees em,salaries s
 where de.emp_no=em.emp_no and de.emp_no=s.emp_no and de.to_date='9999-01-01' and s.to_date='9999-01-01'
 and de.emp_no not in (select emp_no from dept_manager);                     
## 25.获取员工其当前的薪水比其manager当前薪水还高的相关信息
题目描述                
有一个，部门关系表dept_emp简况如下:              
![sql25](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql25.png)                                                                
有一个部门经理表dept_manager简况如下:                   
![sql25_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql25_2.png)                                                                
有一个薪水表salaries简况如下:             
![sql25_3](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql25_3.png)                                                                
获取员工其当前的薪水比其manager当前薪水还高的相关信息，             
第一列给出员工的emp_no，             
第二列给出其manager的manager_no，                   
第三列给出该员工当前的薪水emp_salary,                    
第四列给该员工对应的manager当前的薪水manager_salary                
以上例子输出如下:                   
![sql25_4](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql25_4.png)            
### solution
> select sem.emp_no as emp_no, sdm.emp_no as manager_no, sem.salary as emp_salary, sdm.salary as manager_salary
  from (select s.salary, s.emp_no, de.dept_no from salaries s inner join dept_emp de
  on s.emp_no = de.emp_no and s.to_date = '9999-01-01' ) as sem, 
  (select s.salary, s.emp_no, dm.dept_no from salaries s inner join dept_manager dm
  on s.emp_no = dm.emp_no and s.to_date = '9999-01-01' ) as sdm
  where sem.dept_no = sdm.dept_no and sem.salary > sdm.salary;          
>           
> select sem.emp_no as emp_no, sdm.emp_no as manager_no, sem.salary as emp_salary, sdm.salary as manager_salary
  from (select s.salary, s.emp_no, de.dept_no from salaries s inner join dept_emp de
  on s.emp_no = de.emp_no and s.to_date = '9999-01-01' ) as sem
  inner join 
  (select s.salary, s.emp_no, dm.dept_no from salaries s inner join dept_manager dm
  on s.emp_no = dm.emp_no and s.to_date = '9999-01-01' ) as sdm
  on sem.dept_no = sdm.dept_no where sem.salary > sdm.salary;                                                              
## 26.汇总各个部门当前员工的title类型的分配数目
题目描述                
有一个部门表departments简况如下:          
![sql26](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql26.png)                                                                               
有一个，部门员工关系表dept_emp简况如下:                
![sql26_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql26_2.png)                                                                               
有一个职称表titles简况如下:               
![sql26_3](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql26_3.png)                                                                               
汇总各个部门当前员工的title类型的分配数目，即结果给出部门编号dept_no、dept_name、其部门下所有的员工的title以及该类型title对应的数目count，结果按照dept_no升序排序              
![sql26_4](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql26_4.png)                                                                               
### solution
> select de.dept_no, dp.dept_name, t.title, count(t.title) as count
  from titles as t inner join dept_emp as de 
  on t.emp_no = de.emp_no and de.to_date = '9999-01-01' and t.to_date = '9999-01-01'
  inner join departments as dp on de.dept_no = dp.dept_no
  group by de.dept_no, t.title order by de.dept_no;             
>           
>select de.dept_no, dp.dept_name, t.title, count(t.title)
 from titles as t , dept_emp as de ,departments as dp
 where t.emp_no = de.emp_no and de.dept_no = dp.dept_no and de.to_date = '9999-01-01' and t.to_date = '9999-01-01'
 group by de.dept_no, t.title order by de.dept_no;
## 27.给出每年薪水涨幅超过5000的员工信息
题目描述                
给出每年薪水涨幅超过5000的员工编号emp_no和薪水涨幅值salary_growth，并按照salary_growth逆序排列。               
CREATE TABLE `salaries` (                   
`emp_no` int(11) NOT NULL,                  
`salary` int(11) NOT NULL,                  
`from_date` date NOT NULL,                  
`to_date` date NOT NULL,                    
PRIMARY KEY (`emp_no`,`from_date`));                               
### solution
> select s2.emp_no, (s2.salary - s1.salary) as salary_growth
  from salaries as s1, salaries as s2 where s1.emp_no = s2.emp_no and salary_growth > 5000
  order by salary_growth desc;
## 28.查找描述信息中包括robot的电影对应的分类名称以及电影数目
题目描述            
有一个film表，一个category表和一个film_category表，建表语句如下:           
CREATE TABLE IF NOT EXISTS film (               
film_id smallint(5)  NOT NULL DEFAULT '0',              
title varchar(255) NOT NULL,                
description text,               
PRIMARY KEY (film_id));             

CREATE TABLE category  (                
category_id  tinyint(3)  NOT NULL ,             
name  varchar(25) NOT NULL, `last_update` timestamp,                
PRIMARY KEY ( category_id ));               

CREATE TABLE film_category  (                   
film_id  smallint(5)  NOT NULL,                 
category_id  tinyint(3)  NOT NULL, `last_update` timestamp);            
查找描述信息(film.description)中包含robot的电影对应的分类名称(category.name)以及电影数目(count(film.film_id))，而且还需要该分类包含电影总数量(count(film_category.category_id))>=5部 
### solution
> select c.name, count(fc.film_id) 
  from (select category_id, count(film_id) as category_num from film_category  group by category_id having category_num>=5) as cc,
  film as f, film_category as fc, category as c
  where  f.description like '%robot%' and f.film_id = fc.film_id
  and c.category_id = fc.category_id and c.category_id=cc.category_id;
>       
> select name,count(name) from film,film_category,category
  where film.description like '%robot%' and film.film_id=film_category.film_id and film_category.category_id=category.category_id
  and category.category_id in(select category_id from film_category group by category_id having count(film_id)>=5);              
## 29.使用join查询方式找出没有分类的电影id以及名称
题目描述            
有一个film表，一个category表和一个film_category表，建表语句如下:           
CREATE TABLE IF NOT EXISTS film (               
film_id smallint(5)  NOT NULL DEFAULT '0',              
title varchar(255) NOT NULL,                
description text,               
PRIMARY KEY (film_id));             

CREATE TABLE category  (                
category_id  tinyint(3)  NOT NULL ,             
name  varchar(25) NOT NULL, `last_update` timestamp,                
PRIMARY KEY ( category_id ));               

CREATE TABLE film_category  (                   
film_id  smallint(5)  NOT NULL,                 
category_id  tinyint(3)  NOT NULL, `last_update` timestamp); 
使用join查询方式找出没有分类的电影id以及名称
### solution
> select f.film_id, f.title from film f left join film_category fc
  on f.film_id = fc.film_id where fc.category_id is null;
>               
> select film.film_id,film.title from film
  where film.film_id not in (select fc.film_id from film_category fc);
## 30.使用子查询的方式找出属于Action分类的所有电影对应的title,description
题目描述            
有一个film表，一个category表和一个film_category表，建表语句如下:           
CREATE TABLE IF NOT EXISTS film (               
film_id smallint(5)  NOT NULL DEFAULT '0',              
title varchar(255) NOT NULL,                
description text,               
PRIMARY KEY (film_id));             

CREATE TABLE category  (                
category_id  tinyint(3)  NOT NULL ,             
name  varchar(25) NOT NULL, `last_update` timestamp,                
PRIMARY KEY ( category_id ));               

CREATE TABLE film_category  (                   
film_id  smallint(5)  NOT NULL,                 
category_id  tinyint(3)  NOT NULL, `last_update` timestamp);            
你能使用子查询的方式找出属于Action分类的所有电影对应的title,description吗?
### solution
> select f.title,f.description from film f,
  (select category_id from category where name='Action') as c,film_category fc
  where fc.category_id=c.category_id and fc.film_id=f.film_id;          
>               
> select title,description from film where film_id in
  (select film_id from film_category where category_id in 
  (select category_id from category where name like 'Action'));
## 31.获取执行计划
题目描述                
获取select * from employees对应的执行计划                
### solution
> explain select * from employees;
## 32.将employees表的所有员工的last_name和first_name拼接起来作为Name，中间以一个空格区分
题目描述                
将employees表的所有员工的last_name和first_name拼接起来作为Name，中间以一个空格区分               
(注：sqllite,字符串拼接为 || 符号，不支持concat函数，mysql支持concat函数)                
CREATE TABLE `employees`                        
( `emp_no` int(11) NOT NULL,                                          
`birth_date` date NOT NULL,                     
`first_name` varchar(14) NOT NULL,                      
`last_name` varchar(16) NOT NULL,                       
`gender` char(1) NOT NULL,                      
`hire_date` date NOT NULL,                  
PRIMARY KEY (`emp_no`));                    
### solution
> select concat(last_name," ",first_name) as name from employees;               
>           
> select concat(concat(last_name," "),first_name) as name from employees;
## 33.创建一个actor表
题目描述            
创建一个actor表，包含如下列信息:             
![sql33](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql33.png)  
### solution
> create table if not exists `actor`(
      actor_id smallint(5) primary key not null comment '主键id',
      first_name varchar(45) not null comment '名字',
      last_name varchar(45) not null comment '姓氏',
      last_update date not null comment '日期'
  )engine=innodb default charset=utf8;              
>           
> create table if not exists `actor`(
      actor_id smallint(5) not null comment '主键id',
      first_name varchar(45) not null comment '名字',
      last_name varchar(45) not null comment '姓氏',
      last_update date not null comment '日期',
      primary key (actor_id)
  )engine=innodb default charset=utf8;                                                                            
## 34.批量插入数据
题目描述                
题目已经先执行了如下语句:               
drop table if exists actor;             
CREATE TABLE actor (                
   actor_id  smallint(5)  NOT NULL PRIMARY KEY,             
   first_name  varchar(45) NOT NULL,                
   last_name  varchar(45) NOT NULL,                 
   last_update  DATETIME NOT NULL)              
请你对于表actor批量插入如下数据(不能有2条insert语句哦!)             
![sql34](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql34.png)  
### solution
> insert into actor values (1, 'PENELOPE', 'GUINESS', '2006-02-15 12:34:33'),(2, 'NICK', 'WAHLBERG', '2006-02-15 12:34:33');                
>           
> insert into actor select 1, 'PENELOPE', 'GUINESS', '2006-02-15 12:34:33' union select 2, 'NICK', 'WAHLBERG', '2006-02-15 12:34:33';
## 35.批量插入数据,如果数据已经存在，请忽略，不使用replace操作
题目描述                
题目已经先执行了如下语句:               
drop table if exists actor;                 
CREATE TABLE actor (                    
   actor_id  smallint(5)  NOT NULL PRIMARY KEY,             
   first_name  varchar(45) NOT NULL,                    
   last_name  varchar(45) NOT NULL,                 
   last_update  DATETIME NOT NULL);                     
insert into actor values ('3', 'WD', 'GUINESS', '2006-02-15 12:34:33');                 
对于表actor插入如下数据,如果数据已经存在，请忽略(不支持使用replace操作)             
![sql35](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql35.png)            
### solution
> insert ignore into actor values (3, 'ED', 'CHASE', '2006-02-15 12:34:33');            
>       
> insert ignore 来插入记录，或忽略插入与表内unique字段都相同的记录                    
  insert replace 来插入记录，或更新替代与表内unique字段都相同的记录                   
  如果某字段设置了UNIQUE，则表中该字段值不允许重复，即只能唯一。PRIMARY KEY也具有UNIQUE属性。                 
## 36.创建一个actor_name表，将actor表中的所有first_name以及last_name导入改表
题目描述            
对于如下表actor，其对应的数据为:                 
![sql36](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql36.png)            
请你创建一个actor_name表，并且将actor表中的所有first_name以及last_name导入该表。actor_name表结构如下：           
![sql36_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql36_2.png)
### solution
> create table if not exists `actor_name`(
first_name varchar(45) not null,
last_name varchar(45) not null
);
insert into actor_name select first_name,last_name from actor;
>       
> create table if not exists `actor_name` as select first_name,last_name from actor;            
>       
> create table if not exists `actor_name` select first_name,last_name from actor;
## 37.对first_name创建唯一索引uniq_idx_firstname，对last_name创建普通索引idx_lastname
题目描述                
针对如下表actor结构创建索引：               
(注:在SQLite中,除了重命名表和在已有的表中添加列,ALTER TABLE命令不支持其他操作，mysql支持ALTER TABLE创建索引)                                    
CREATE TABLE actor  (                   
actor_id  smallint(5)  NOT NULL PRIMARY KEY,                
first_name  varchar(45) NOT NULL,               
last_name  varchar(45) NOT NULL,            
last_update  datetime NOT NULL);                
对first_name创建唯一索引uniq_idx_firstname，对last_name创建普通索引idx_lastname
### solution
> create unique index uniq_idx_firstname on actor(first_name);              
  create index idx_lastname on actor(last_name);            
>                   
> 创建唯一索引:create unique index 'index_name' on table_name(column)                 
  创建普通索引:create index 'index_name' on table_name(column)        
>               
> alter table actor add unique index uniq_idx_firstname(first_name);                    
  alter table actor add index idx_lastname(last_name);
## 38.针对actor表创建视图actor_name_view
题目描述                
针对actor表创建视图actor_name_view，只包含first_name以及last_name两列，并对这两列重新命名，first_name为first_name_v，last_name修改为last_name_v：                   
CREATE TABLE  actor  (              
actor_id  smallint(5)  NOT NULL PRIMARY KEY,                
first_name  varchar(45) NOT NULL,               
last_name  varchar(45) NOT NULL,                
last_update datetime NOT NULL);             
### solution
> create view actor_name_view as                
  select first_name as first_name_v,last_name as last_name_v from actor;                    
>           
> create view actor_name_view(first_name_v,last_name_v) as              
  select first_name,last_name from actor;               
>               
> 视图（View）是一种虚拟存在的表。其内容与真实的表相似，包含一系列带有名称的列和行数据。         
但是视图并不在数据库中以存储的数据的形式存在。             
行和列的数据来自定义视图时查询所引用的基本表，并且在具体引用视图时动态生成。          
视图的特点如下：            
1.视图的列可以来自不同的表，是表的抽象和在逻辑意义上建立的新关系；              
2.视图是由基本表（实表）产生的表（虚表）；          
3.视图的建立和删除不影响基本表；               
4.对视图内容的更新（增删改）直接影响基本表；             
5.当视图来自多个基本表时，不允许添加和删除数据        
> 创建视图的语句:                     
  CREATE                
  [OR REPLACE]              
  [ALGORITHM = {UNDEFINED | MERGE | TEMPTABLE}]                 
  [DEFINER = { user | CURRENT_USER }]               
  [SQL SECURITY { DEFINER | INVOKER }]              
  VIEW view_name [(column_list)]                
  AS select_statement               
  [WITH [CASCADED | LOCAL] CHECK OPTION]                
  如果在创建视图的时候制定了“WITH CHECK OPTION”，那么更新数据时不能插入或更新不符合视图限制条件的记录。
## 39.针对上面的salaries表emp_no字段创建索引idx_emp_no，查询emp_no为10005
题目描述            
针对salaries表emp_no字段创建索引idx_emp_no，查询emp_no为10005, 使用强制索引。               
CREATE TABLE `salaries` (           
`emp_no` int(11) NOT NULL,          
`salary` int(11) NOT NULL,          
`from_date` date NOT NULL,      
`to_date` date NOT NULL,        
PRIMARY KEY (`emp_no`,`from_date`));        
create index idx_emp_no on salaries(emp_no);            
### solution
> select * from salaries force index(idx_emp_no) where emp_no=10005;
>       
> MYSQL中强制索引查询使用：FORCE INDEX(indexname);
## 40.在last_update后面新增加一列名字为create_date
题目描述            
存在actor表，包含如下列信息：           
CREATE TABLE  actor  (          
actor_id  smallint(5)  NOT NULL PRIMARY KEY,        
first_name  varchar(45) NOT NULL,       
last_name  varchar(45) NOT NULL,        
last_update  datetime NOT NULL);        
现在在last_update后面新增加一列名字为create_date, 类型为datetime, NOT NULL，默认值为'2020-10-01 00:00:00'        
### solution
> alter table actor add column `create_date` datetime not null default '2020-10-01 00:00:00';       
>       
> alter table actor add `create_date` datetime default '2020-10-01 00:00:00' not null;          
>       
> 用ALTER TABLE ... ADD ... 语句可以向已存在的表插入新字段，并且能够与创建表时一样，在字段名和数据类型后加入NOT NULL、DEFAULT等限定。
  其中ADD后的COLUMN可省略，NOT NULL和DEFAULT '0000-00-00 00:00:00' 可交换。
## 41.构造一个触发器audit_log，在向employees表中插入一条数据的时候，触发插入相关的数据到audit中
题目描述        
构造一个触发器audit_log，在向employees_test表中插入一条数据的时候，触发插入相关的数据到audit中。      
CREATE TABLE employees_test(        
ID INT PRIMARY KEY NOT NULL,        
NAME TEXT NOT NULL,     
AGE INT NOT NULL,       
ADDRESS CHAR(50),       
SALARY REAL         
);          
CREATE TABLE audit(     
EMP_no INT NOT NULL,        
NAME TEXT NOT NULL      
);      
### solution
> create trigger audit_log after insert on employees_test               
for each row            
begin               
insert into audit values(new.id,new.name);          
end;            
>           
> 创建触发器的基本语法:       
CREATE TRIGGER trigger_name [BEFORE|AFTER] event_name ON table_name         
FOR EACH ROW        
BEGIN           
   -- Trigger logic goes here ;                 
END;            
>1.create trigger ：创建触发器                  
2.触发器要说明是在after 还是before事务发生时触发                    
3.要指明是insert 、delete、update操作                  
4.on 表名                     
5.begin和end之间写触发的动作         
6.new 关键字表示更新后的表的字段 ，old表示更新前的表的字段              
## 42.删除emp_no重复的记录，只保留最小的id对应的记录。
题目描述            
删除emp_no重复的记录，只保留最小的id对应的记录。            
CREATE TABLE IF NOT EXISTS titles_test (            
id int(11) not null primary key,                
emp_no int(11) NOT NULL,            
title varchar(50) NOT NULL,             
from_date date NOT NULL,            
to_date date DEFAULT NULL);         
                
insert into titles_test values ('1', '10001', 'Senior Engineer', '1986-06-26', '9999-01-01'),               
('2', '10002', 'Staff', '1996-08-03', '9999-01-01'),                
('3', '10003', 'Senior Engineer', '1995-12-03', '9999-01-01'),              
('4', '10004', 'Senior Engineer', '1995-12-03', '9999-01-01'),              
('5', '10001', 'Senior Engineer', '1986-06-26', '9999-01-01'),              
('6', '10002', 'Staff', '1996-08-03', '9999-01-01'),                
('7', '10003', 'Senior Engineer', '1995-12-03', '9999-01-01');
删除后titles_test表为                
![sql42](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql42.png)
### solution
> delete from titles_test where id not in( select min_id from (select min(id) as min_id from titles_test group by emp_no) t);           
## 43.将所有to_date为9999-01-01的全部更新为NULL,且from_date更新为2001-01-01。
题目描述            
将所有to_date为9999-01-01的全部更新为NULL,且from_date更新为2001-01-01。           
CREATE TABLE IF NOT EXISTS titles_test (            
id int(11) not null primary key,            
emp_no int(11) NOT NULL,            
title varchar(50) NOT NULL,         
from_date date NOT NULL,            
to_date date DEFAULT NULL);             
                
insert into titles_test values ('1', '10001', 'Senior Engineer', '1986-06-26', '9999-01-01'),               
('2', '10002', 'Staff', '1996-08-03', '9999-01-01'),                
('3', '10003', 'Senior Engineer', '1995-12-03', '9999-01-01'),              
('4', '10004', 'Senior Engineer', '1995-12-03', '9999-01-01'),              
('5', '10001', 'Senior Engineer', '1986-06-26', '9999-01-01'),                  
('6', '10002', 'Staff', '1996-08-03', '9999-01-01'),                
('7', '10003', 'Senior Engineer', '1995-12-03', '9999-01-01');              
更新后titles_test表的值：              
![sql43](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql43.png)
### solution
> update titles_test set to_date=NULL,from_date='2001-01-01' where to_date='9999-01-01';        
>       
> 注意若干列 to_date = NULL 和 from_date = '2001-01-01' 之间只能用逗号连接，切勿用 AND 连接。
## 44.将id=5以及emp_no=10001的行数据替换成id=5以及emp_no=10005,其他数据保持不变，使用replace实现。
题目描述            
将id=5以及emp_no=10001的行数据替换成id=5以及emp_no=10005,其他数据保持不变，使用replace实现，直接使用update会报错。            
CREATE TABLE titles_test (              
id int(11) not null primary key,                
emp_no  int(11) NOT NULL,               
title  varchar(50) NOT NULL,                
from_date  date NOT NULL,           
to_date  date DEFAULT NULL);            
                
insert into titles_test values              
('1', '10001', 'Senior Engineer', '1986-06-26', '9999-01-01'),              
('2', '10002', 'Staff', '1996-08-03', '9999-01-01'),                
('3', '10003', 'Senior Engineer', '1995-12-03', '9999-01-01'),              
('4', '10004', 'Senior Engineer', '1995-12-03', '9999-01-01'),          
('5', '10001', 'Senior Engineer', '1986-06-26', '9999-01-01'),          
('6', '10002', 'Staff', '1996-08-03', '9999-01-01'),            
('7', '10003', 'Senior Engineer', '1995-12-03', '9999-01-01');
### solution
> update titles_test set emp_no = replace(emp_no,10001,10005) where id = 5;         
>       
> 运用REPLACE(X,Y,Z)函数。其中X是要处理的字符串，Y是X中将要被替换的字符串，Z是用来替换Y的字符串，最终返回替换后的字符串。     
>       
> replace into titles_test values (5, 10005, 'Senior Engineer', '1986-06-26', '9999-01-01');        
>           
> 全字段更新替换。由于REPLACE的新记录中id=5，与表中的主键id=5冲突，故会替换掉表中id=5的记录，
否则会插入一条新记录（例如新插入的记录id = 10）。并且要将所有字段的值写出，否则将置为空。
## 45.将titles_test表名修改为titles_2017
题目描述                
将titles_test表名修改为titles_2017。                         
CREATE TABLE IF NOT EXISTS titles_test (                
id int(11) not null primary key,            
emp_no int(11) NOT NULL,                
title varchar(50) NOT NULL,             
from_date date NOT NULL,                
to_date date DEFAULT NULL);             
                
insert into titles_test values ('1', '10001', 'Senior Engineer', '1986-06-26', '9999-01-01'),           
('2', '10002', 'Staff', '1996-08-03', '9999-01-01'),                
('3', '10003', 'Senior Engineer', '1995-12-03', '9999-01-01'),              
('4', '10004', 'Senior Engineer', '1995-12-03', '9999-01-01'),          
('5', '10001', 'Senior Engineer', '1986-06-26', '9999-01-01'),                        
('6', '10002', 'Staff', '1996-08-03', '9999-01-01'),                
('7', '10003', 'Senior Engineer', '1995-12-03', '9999-01-01');          
### solution
> alter table titles_test rename titles_2017;       
## 46.在audit表上创建外键约束，其emp_no对应employees_test表的主键id
题目描述            
在audit表上创建外键约束，其emp_no对应employees_test表的主键id。           
CREATE TABLE employees_test(            
ID INT PRIMARY KEY NOT NULL,            
NAME TEXT NOT NULL,         
AGE INT NOT NULL,           
ADDRESS CHAR(50),               
SALARY REAL         
);              
                    
CREATE TABLE audit(         
EMP_NO INT NOT NULL,            
CREATE_DATE datetime NOT NULL           
);                
### solution
> drop table audit;             
  create table audit(               
      emp_no int NOT NULL,              
      create_date datetime NOT NULL,                
      foreign key(emp_no) references employees_test(ID));               
>       
> alter table audit add foreign key(emp_no) references employees_test(id);      
## 47.如何获取emp_v和employees有相同的数据？
题目描述        
存在如下的视图：        
create view emp_v as select * from employees where emp_no >10005;       
如何获取emp_v和employees有相同的数据？          
CREATE TABLE `employees` (          
`emp_no` int(11) NOT NULL,              
`birth_date` date NOT NULL,         
`first_name` varchar(14) NOT NULL,          
`last_name` varchar(16) NOT NULL,           
`gender` char(1) NOT NULL,          
`hire_date` date NOT NULL,              
PRIMARY KEY (`emp_no`));            
### solution
> select em.* from employees as em,emp_v as ev where em.emp_no=ev.emp_no;           
>           
> select em.* from employees em inner join (select * from emp_v) ev on em.emp_no=ev.emp_no;         
>           
> select * from employees intersect select * from emp_v;        
>       
> 错误方法:用以下方法直接输出 *，会得到两张表中符合条件的重复记录，因此不合题意，必须在 * 前加表名作限定                
  select * from employees, emp_v where employees.emp_no = emp_v.emp_no          
## 48.将所有获取奖金的员工当前的薪水增加10%
题目描述            
请你写出更新语句，将所有获取奖金的员工当前的(salaries.to_date='9999-01-01')薪水增加10%。(emp_bonus里面的emp_no都是当前获奖的所有员工)            
create table emp_bonus(             
emp_no int not null,            
btype smallint not null);           
            
CREATE TABLE `salaries` (           
`emp_no` int(11) NOT NULL,              
`salary` int(11) NOT NULL,              
`from_date` date NOT NULL,              
`to_date` date NOT NULL, PRIMARY KEY (`emp_no`,`from_date`));           
如：              
INSERT INTO emp_bonus VALUES (10001,1);                 
INSERT INTO salaries VALUES(10001,85097,'2001-06-22','2002-06-22');             
INSERT INTO salaries VALUES(10001,88958,'2002-06-22','9999-01-01');             
更新后的结果salaries:                 
![sql48](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql48.png)                    
### solution
> update salaries set salary=salary*1.1 where emp_no in (select emp_no from emp_bonus) and to_date='9999-01-01';                
## 50.将employees表中的所有员工的last_name和first_name通过(')连接起来。
题目描述                
将employees表中的所有员工的last_name和first_name通过(')连接起来。(sqlite不支持concat，请用||实现，mysql支持concat)              
CREATE TABLE `employees` (          
`emp_no` int(11) NOT NULL,          
`birth_date` date NOT NULL,             
`first_name` varchar(14) NOT NULL,              
`last_name` varchar(16) NOT NULL,           
`gender` char(1) NOT NULL,          
`hire_date` date NOT NULL,              
PRIMARY KEY (`emp_no`));                
输出格式:           
![sql50](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql50.png)                    
### solution
> select concat(last_name,"'",first_name) from employees;       
## 51.查找字符串'10,A,B'
题目描述            
查找字符串'10,A,B' 中逗号','出现的次数cnt。               
### solution    
> select (length("10,A,B")-length(replace("10,A,B",",","")))/length(",") as cnt;
## 52.获取Employees中的first_name，查询按照first_name最后两个字母，按照升序进行排列
题目描述                
获取Employees中的first_name，查询按照first_name最后两个字母，按照升序进行排列               
CREATE TABLE `employees` (              
`emp_no` int(11) NOT NULL,          
`birth_date` date NOT NULL,             
`first_name` varchar(14) NOT NULL,              
`last_name` varchar(16) NOT NULL,               
`gender` char(1) NOT NULL,              
`hire_date` date NOT NULL,                  
PRIMARY KEY (`emp_no`));                
输出格式：               
![sql52](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql52.png)                    
### solution
> select first_name from employees order by substr(first_name,length(first_name)-1);            
>           
> select first_name from employees order by substr(first_name,-2);          
>               
> substr(X,Y,Z) 或 substr(X,Y) 函数。其中X是要截取的字符串。Y是字符串的起始位置（注意第一个字符的位置为1，而不为0），             
  取值范围是±(1~length(X))，当Y等于length(X)时，则截取最后一个字符；当Y等于负整数-n时，则从倒数第n个字符处截取。                 
  Z是要截取字符串的长度，取值范围是正整数，若Z省略，则从Y处一直截取到字符串末尾；若Z大于剩下的字符串长度，也是截取到字符串末尾为止。                
## 53.按照dept_no进行汇总，属于同一个部门的emp_no按照逗号进行连接，结果给出dept_no以及连接出的结果employees
题目描述                
按照dept_no进行汇总，属于同一个部门的emp_no按照逗号进行连接，结果给出dept_no以及连接出的结果employees               
CREATE TABLE `dept_emp` (               
`emp_no` int(11) NOT NULL,              
`dept_no` char(4) NOT NULL,             
`from_date` date NOT NULL,                  
`to_date` date NOT NULL,                
PRIMARY KEY (`emp_no`,`dept_no`));              
输出格式:               
![sql53](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql53.png)                    
### solution
> select dept_no,group_concat(emp_no) from dept_emp group by dept_no;           
>           
> group_concat()函数返回X的非null值的连接后的字符串。如果给出了参数Y，将会在每个X之间用Y作为分隔符。如果省略了Y，“，”将作为默认的分隔符。每个元素连接的顺序是随机的。此函数必须与GROUP BY配合使用。
## 54.查找排除当前最大、最小salary之后的员工的平均工资avg_salary
题目描述                
查找排除最大、最小salary之后的当前(to_date = '9999-01-01' )员工的平均工资avg_salary。             
CREATE TABLE `salaries` (                   
`emp_no` int(11) NOT NULL,                  
`salary` int(11) NOT NULL,                  
`from_date` date NOT NULL,              
`to_date` date NOT NULL,                
PRIMARY KEY (`emp_no`,`from_date`));                
如：                  
INSERT INTO salaries VALUES(10001,85097,'2001-06-22','2002-06-22');                 
INSERT INTO salaries VALUES(10001,88958,'2002-06-22','9999-01-01');             
INSERT INTO salaries VALUES(10002,72527,'2001-08-02','9999-01-01');             
INSERT INTO salaries VALUES(10003,43699,'2000-12-01','2001-12-01');                 
INSERT INTO salaries VALUES(10003,43311,'2001-12-01','9999-01-01');             
INSERT INTO salaries VALUES(10004,70698,'2000-11-27','2001-11-27');                 
INSERT INTO salaries VALUES(10004,74057,'2001-11-27','9999-01-01');             
输出格式:                   
![sql54](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql54.png)                    
### solution
> select avg(salary) as avg_salary from salaries                    
  where salary not in (select max(salary) from salaries where to_date = '9999-01-01')               
  and salary not in (select min(salary) from salaries where to_date = '9999-01-01')             
  and to_date = '9999-01-01';               
## 55.分页查询employees表，每5行一页，返回第2页的数据
题目描述                
分页查询employees表，每5行一页，返回第2页的数据               
CREATE TABLE `employees` (              
`emp_no` int(11) NOT NULL,              
`birth_date` date NOT NULL,                 
`first_name` varchar(14) NOT NULL,                  
`last_name` varchar(16) NOT NULL,               
`gender` char(1) NOT NULL,              
`hire_date` date NOT NULL,              
PRIMARY KEY (`emp_no`));                
### solution
> select * from employees limit 5 offset 5;         
>           
> 利用 LIMIT 和 OFFSET 关键字。LIMIT 后的数字代表返回几条记录，OFFSET 后的数字代表从第几条记录开始返回（第一条记录序号为0），也可理解为跳过多少条记录后开始返回。                
>               
> select * from employees limit 5,5;            
>           
> 只利用LIMIT关键字。注意：在 LIMIT X,Y 中，Y代表返回几条记录，X代表从第几条记录开始返回（第一条记录序号为0），切勿记反。
## 56.获取所有员工的emp_no、部门编号dept_no以及对应的bonus类型btype和recevied
题目描述            
获取所有员工的emp_no、部门编号dept_no以及对应的bonus类型btype和recevied，没有分配具体的员工不显示            
CREATE TABLE `dept_emp` (                   
`emp_no` int(11) NOT NULL,                         
`dept_no` char(4) NOT NULL,                 
`from_date` date NOT NULL,                  
`to_date` date NOT NULL,                
PRIMARY KEY (`emp_no`,`dept_no`));              
                
CREATE TABLE `dept_manager` (               
`dept_no` char(4) NOT NULL,                 
`emp_no` int(11) NOT NULL,              
`from_date` date NOT NULL,              
`to_date` date NOT NULL,                
PRIMARY KEY (`emp_no`,`dept_no`));              
                
CREATE TABLE `employees` (              
`emp_no` int(11) NOT NULL,              
`birth_date` date NOT NULL,             
`first_name` varchar(14) NOT NULL,              
`last_name` varchar(16) NOT NULL,               
`gender` char(1) NOT NULL,              
`hire_date` date NOT NULL,              
PRIMARY KEY (`emp_no`));            
            
CREATE TABLE `salaries` (               
`emp_no` int(11) NOT NULL,              
`salary` int(11) NOT NULL,              
`from_date` date NOT NULL,              
`to_date` date NOT NULL,                
PRIMARY KEY (`emp_no`,`from_date`));                
### solution
> select em.emp_no, de.dept_no, eb.btype, eb.recevied               
  from employees as em inner join dept_emp as de                
  on em.emp_no = de.emp_no left join emp_bonus as eb                        
  on de.emp_no = eb.emp_no              
## 57.使用含有关键字exists查找未分配具体部门的员工的所有信息。
题目描述            
使用含有关键字exists查找未分配具体部门的员工的所有信息。                 
CREATE TABLE `employees` (              
`emp_no` int(11) NOT NULL,              
`birth_date` date NOT NULL,                 
`first_name` varchar(14) NOT NULL,              
`last_name` varchar(16) NOT NULL,               
`gender` char(1) NOT NULL,              
`hire_date` date NOT NULL,              
PRIMARY KEY (`emp_no`));                
            
CREATE TABLE `dept_emp` (               
`emp_no` int(11) NOT NULL,              
`dept_no` char(4) NOT NULL,             
`from_date` date NOT NULL,              
`to_date` date NOT NULL,                
PRIMARY KEY (`emp_no`,`dept_no`));              
输出格式:                         
![sql57](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql57.png)                    
### solution
> select * from employees where not exists (select * from dept_emp where dept_emp.emp_no=employees.emp_no);         
## 58.获取employees中的行数据，且这些行也存在于emp_v中
题目描述                
存在如下的视图：            
create view emp_v as select * from employees where emp_no >10005;               
CREATE TABLE `employees` (              
`emp_no` int(11) NOT NULL,              
`birth_date` date NOT NULL,             
`first_name` varchar(14) NOT NULL,          
`last_name` varchar(16) NOT NULL,           
`gender` char(1) NOT NULL,              
`hire_date` date NOT NULL,              
PRIMARY KEY (`emp_no`));                
获取employees中的行数据，且这些行也存在于emp_v中。注意不能使用intersect关键字。                 
### solution
> select em.* from employees as em,emp_v as ev where em.emp_no=ev.emp_no;
## 59.获取有奖金的员工相关信息。
题目描述            
获取有奖金的员工相关信息。               
CREATE TABLE `employees` (              
`emp_no` int(11) NOT NULL,              
`birth_date` date NOT NULL,             
`first_name` varchar(14) NOT NULL,              
`last_name` varchar(16) NOT NULL,               
`gender` char(1) NOT NULL,          
`hire_date` date NOT NULL,              
PRIMARY KEY (`emp_no`));                
            
CREATE TABLE `dept_emp` (               
`emp_no` int(11) NOT NULL,              
`dept_no` char(4) NOT NULL,             
`from_date` date NOT NULL,              
`to_date` date NOT NULL,                
PRIMARY KEY (`emp_no`,`dept_no`));              
                
create table emp_bonus(             
emp_no int not null,                
received datetime not null,             
btype smallint not null);               
                
CREATE TABLE `salaries` (               
`emp_no` int(11) NOT NULL,          
`salary` int(11) NOT NULL,              
`from_date` date NOT NULL,          
`to_date` date NOT NULL,                
PRIMARY KEY (`emp_no`,`from_date`));                
给出emp_no、first_name、last_name、奖金类型btype、对应的当前薪水情况salary以及奖金金额bonus。                 
bonus类型btype为1其奖金为薪水salary的10%，btype为2其奖金为薪水的20%，其他类型均为薪水的30%。 当前薪水表示to_date='9999-01-01'           
输出格式:
![sql59](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql57.png)                    
### solution
> select e.emp_no,e.first_name,e.last_name,b.btype,s.salary,                
  (case b.btype             
   when 1 then s.salary*0.1             
   when 2 then s.salary*0.2             
   else s.salary*0.3 end                
  ) as bonus                
  from employees as e inner join emp_bonus as b on e.emp_no=b.emp_no                    
  inner join salaries as s on e.emp_no=s.emp_no and s.to_date='9999-01-01';             
>           
> select employees.emp_no,employees.first_name,employees.last_name,emp_bonus.btype,salaries.salary,             
  (case when emp_bonus.btype=1 then salaries.salary*0.1             
  when emp_bonus.btype=2 then salaries.salary*0.2               
  else salaries.salary*0.3 end) as bonus                
  from employees,salaries,emp_bonus             
  where employees.emp_no=salaries.emp_no                
  and employees.emp_no=emp_bonus.emp_no             
  and salaries.to_date='9999-01-01';            
## 60.统计salary的累计和running_total
题目描述            
按照salary的累计和running_total，其中running_total为前N个当前( to_date = '9999-01-01')员工的salary累计和，其他以此类推。            
CREATE TABLE `salaries` (               
`emp_no` int(11) NOT NULL,              
`salary` int(11) NOT NULL,          
`from_date` date NOT NULL,              
`to_date` date NOT NULL,                    
PRIMARY KEY (`emp_no`,`from_date`));                
输出格式:              
![sql60](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql60.png)                    
### solution
> select s1.emp_no, s1.salary,(select sum(s2.salary) from salaries as s2                 
  where s2.emp_no <= s1.emp_no and s2.to_date = '9999-01-01') as running_total                  
  from salaries as s1 where s1.to_date = '9999-01-01' order by s1.emp_no;               
>               
> select s2.emp_no,s2.salary,sum(s1.salary) as running_total                
  from salaries as s1 inner join salaries as s2 on s1.emp_no <= s2.emp_no               
  where s1.to_date = "9999-01-01" and s2.to_date = "9999-01-01" group by s2.emp_no;             
> 把所有小于等于当前编号的表s1和当前编号表s2联立起来，然后按照当前编号分组，计算出所有小于等于当前标号的工资总数         
>           
> select emp_no,salary,sum(salary) over(order by emp_no) as running_total           
  from salaries where to_date= '9999-01-01';            
> 把sum聚合函数作为窗口函数使用，所有聚合函数都能用做窗口函数，其语法和专用窗口函数完全相同。           
  sum(<汇总列>) over(<排序列>) as 别名；             
> 窗口函数对一组查询行执行类似于聚合函数的操作。但是，聚合函数是将查询行聚合到单个结果行中，而窗口函数为每个查询行生成一个结果。               
## 61.对于employees表中，给出奇数行的first_name
题目描述            
对于employees表中，输出first_name排名(按first_name升序排序)为奇数的first_name             
CREATE TABLE `employees` (              
`emp_no` int(11) NOT NULL,              
`birth_date` date NOT NULL,             
`first_name` varchar(14) NOT NULL,              
`last_name` varchar(16) NOT NULL,               
`gender` char(1) NOT NULL,          
`hire_date` date NOT NULL,              
PRIMARY KEY (`emp_no`));                
如，输入为：              
INSERT INTO employees VALUES(10001,'1953-09-02','Georgi','Facello','M','1986-06-26');           
INSERT INTO employees VALUES(10002,'1964-06-02','Bezalel','Simmel','F','1985-11-21');           
INSERT INTO employees VALUES(10005,'1955-01-21','Kyoichi','Maliniak','M','1989-09-12');             
INSERT INTO employees VALUES(10006,'1953-04-20','Anneke','Preusig','F','1989-06-02');               
输出格式:           
![sql61](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql61.png)                
因为Georgi按first_name排名为3，Anneke按first_name排名为1，所以会输出这2个，且输出时不需排序。                          
### solution
> select e1.first_name from                 
  (select e2.first_name,(select count(*) from employees as e3 where e3.first_name<=e2.first_name) as rowid from employees as e2)                
  as e1 where e1.rowid%2=1;    
>           
> select e.first_name from employees e inner join               
  (select first_name, row_number() over(order by first_name asc) as row_num from employees)             
  as t on e.first_name = t.first_name where t.row_num % 2 = 1;                       
## 62.出现三次以上相同积分的情况
题目描述            
在牛客刷题的小伙伴们都有着牛客积分，积分(grade)表简化可以如下:         
![sql62](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql62.png)            
id为用户主键id，number代表积分情况，让你写一个sql查询，积分表里面出现三次以及三次以上的积分，查询结果如下:                         
![sql62_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql62_2.png)                        
### solution
> select number from grade group by number having count(number)>=3;
>           
> where与having的用法：              
![sql62_3](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql62_3.png)                        
> 1.where条件后面是不能跟聚合函数的，因为where执行顺序大于聚合函数，如果需要用聚合函数作为过滤条件则用having            
> 2.having通常是对分组以后的数据进行筛选，所以一般都是在使用group by或者聚合函数后使用，而where是在分组前对数据进行过滤                    
## 63.刷题通过的题目排名
题目描述                
在牛客刷题有一个通过题目个数的(passing_number)表，id是主键，简化如下:            
![sql63](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql63.png)            
第1行表示id为1的用户通过了4个题目;            
.....                   
第6行表示id为6的用户通过了4个题目;                          
请你根据上表，输出通过的题目的排名，通过题目个数相同的，排名相同，此时按照id升序排列，数据如下:                                      
![sql63_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql63_2.png)            
id为5的用户通过了5个排名第1，               
id为1和id为6的都通过了2个，并列第2                         
### solution
> select id, number,dense_rank() over (order by number desc) t_rank from passing_number;  
> row_number对应唯一排序：1、2、3、4              
  dense_rank对应相同次序可重复，但不跳过下一个次序值：1、2、2、3                
  rank对应相同次序可重复，并且跳过下一个次序值：1、2、2、4          
>               
> select a.id, a.number, count(distinct b.number) as t_rank         
  from passing_number as a              
  inner join passing_number as b            
  on a.number <= b.number           
  group by a.id, a.number               
  order by t_rank asc;                 
## 64.找到每个人的任务
题目描述                
有一个person表，主键是id，如下:            
![sql64](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql64.png)                
有一个任务(task)表如下，主键也是id，如下:                         
![sql64_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql64_2.png)                    
请你找到每个人的任务情况，并且输出出来，没有任务的也要输出，而且输出结果按照person的id升序排序，输出情况如下:                      
![sql64_3](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql64_3.png)                       
### solution
> select person.id,person.name,task.content from person left join task on person.id=task.person_id;     
>       
> select person.id,person.name,task.content from task right join person on person.id=task.person_id;            
## 65.异常的邮件概率
题目描述            
现在有一个需求，让你统计正常用户发送给正常用户邮件失败的概率:             
有一个邮件(email)表，id为主键，type是枚举类型，枚举成员为(completed，no_completed)，completed代表邮件发送是成功的，no_completed代表邮件是发送失败的。简况如下:            
![sql65](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql65.png)                
第1行表示为id为2的用户在2020-01-11成功发送了一封邮件给了id为3的用户;                 
...             
第3行表示为id为1的用户在2020-01-11没有成功发送一封邮件给了id为4的用户;                
...         
第6行表示为id为4的用户在2020-01-12成功发送了一封邮件给了id为1的用户;             
下面是一个用户(user)表，id为主键，is_blacklist为0代表为正常用户，is_blacklist为1代表为黑名单用户，简况如下:             
![sql65_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql65_2.png)                
第1行表示id为1的是正常用户;                
第2行表示id为2的不是正常用户，是黑名单用户，如果发送大量邮件或者出现各种情况就会容易发送邮件失败的用户               
...             
第4行表示id为4的是正常用户             
现在让你写一个sql查询，每一个日期里面，正常用户发送给正常用户邮件失败的概率是多少，结果保留到小数点后面3位(3位之后的四舍五入)，并且按照日期升序排序，上面例子查询结果如下:                                          
![sql65_3](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql65_3.png)                
结果表示:                   
2020-01-11失败的概率为0.500，因为email的第1条数据，发送的用户id为2是黑名单用户，所以不计入统计，正常用户发正常用户总共2次，但是失败了1次，所以概率是0.500;               
2020-01-12没有失败的情况，所以概率为0.000.                   
(注意: sqlite 1/2得到的不是0.5，得到的是0，只有1*1.0/2才会得到0.5，sqlite四舍五入的函数为round)             
### solution
> select email.date, round(             
  sum(case email.type when'completed' then 0 else 1 end)*1.0/count(email.type),3            
  ) as p                
  from email            
  join user as u1 on (email.send_id=u1.id and u1.is_blacklist=0)            
  join user as u2 on (email.receive_id=u2.id and u2.is_blacklist=0)             
  group by email.date order by email.date;                      
## 66.每个人最近的登录日期(一)
题目描述            
牛客每天有很多人登录，请你统计一下牛客每个用户最近登录是哪一天。            
有一个登录(login)记录表，简况如下:           
![sql66](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql66.png)            
第1行表示user_id为2的用户在2020-10-12使用了客户端id为1的设备登录了牛客网             
...             
第4行表示user_id为3的用户在2020-10-13使用了客户端id为2的设备登录了牛客网             
请你写出一个sql语句查询每个用户最近一天登录的日子，并且按照user_id升序排序，上面的例子查询结果如下:             
![sql66_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql66_2.png)                         
### solution
> select user_id,max(date) from login group by user_id order by user_id;        
## 67.每个人最近的登录日期(二)
题目描述                
牛客每天有很多人登录，请你统计一下牛客每个用户最近登录是哪一天，用的是什么设备.            
有一个登录(login)记录表，简况如下:           
![sql67](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql67.png)                
第1行表示user_id为2的用户在2020-10-12使用了客户端id为1的设备登录了牛客网         
...             
第4行表示user_id为3的用户在2020-10-13使用了客户端id为2的设备登录了牛客网             
还有一个用户(user)表，简况如下:                                  
![sql67_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql67_2.png)                
还有一个客户端(client)表，简况如下:                         
![sql67_3](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql67_3.png)            
请你写出一个sql语句查询每个用户最近一天登录的日子，用户的名字，以及用户用的设备的名字，并且查询结果按照user的name升序排序，上面的例子查询结果如下:                         
![sql67_4](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql67_4.png)            
查询结果表明:                 
fh最近的登录日期在2020-10-13，而且是使用pc登录的             
wangchao最近的登录日期也是2020-10-13，而且是使用ios登录的                                 
### solution
> select user.name as u_n, client.name as c_n,login.date            
  from login            
  inner join user on login.user_id=user.id          
  inner join client on login.client_id=client.id            
  where (login.user_id,login.date) in           
  (select user_id,max(date) from login group by login.user_id )             
  order by user.name;           
>           
> select u.name as u_n, c.name as c_n, l.date as d          
  from login as l           
  inner join user as u          
  on l.user_id=u.id         
  inner join client as c        
  on l.client_id=c.id       
  inner join (          
      select user_id, max(date)  as date            
      from login            
      group by user_id          
  ) a               
  on l.user_id=a.user_id                
  and l.date=a.date         
  order by u.name asc;              
>           
> /*窗口函数分组求最大时间，然后用子查询筛选*/          
  select n.user_name u_n, n.client_name c_n,n.d             
  from(             
  select u.name user_name, c.name client_name, l.date,              
  (max(l.date) over(partition by l.user_id)) d          
  from login l,user u,client c              
  where l.user_id=u.id and c.id=l.client_id         
  ) as n            
  where n.date=n.d          
  order by n.user_name;             
## 68.每个人最近的登录日期(三)
题目描述            
牛客每天有很多人登录，请你统计一下牛客新登录用户的次日成功的留存率，          
有一个登录(login)记录表，简况如下:           
![sql68](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql68.png)                            
第1行表示user_id为2的用户在2020-10-12使用了客户端id为1的设备第一次新登录了牛客网             
...         
第4行表示user_id为3的用户在2020-10-12使用了客户端id为2的设备登录了牛客网         
...             
最后1行表示user_id为1的用户在2020-10-14使用了客户端id为2的设备登录了牛客网            
请你写出一个sql语句查询新登录用户次日成功的留存率，即第1天登陆之后，第2天再次登陆的概率,保存小数点后面3位(3位之后的四舍五入)，上面的例子查询结果如下:                
![sql68_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql68_2.png)                            
查询结果表明:         
user_id为1的用户在2020-10-12第一次新登录了，在2020-10-13又登录了，算是成功的留存              
user_id为2的用户在2020-10-12第一次新登录了，在2020-10-13又登录了，算是成功的留存              
user_id为3的用户在2020-10-12第一次新登录了，在2020-10-13没登录了，算是失败的留存              
user_id为4的用户在2020-10-13第一次新登录了，在2020-10-14没登录了，算是失败的留存          
固次日成功的留存率为 2/4=0.5          
(sqlite里查找某一天的后一天的用法是:date(yyyy-mm-dd, '+1 day')，四舍五入的函数为round，sqlite 1/2得到的不是0.5，得到的是0，只有1*1.0/2才会得到0.5            
mysql里查找某一天的后一天的用法是:DATE_ADD(yyyy-mm-dd,INTERVAL 1 DAY)，四舍五入的函数为round)          
### solution
> select round(count(distinct a.user_id)/(select count(distinct user_id) from login),3)             
  from              
  (select *,min(date) over(partition by user_id) firstday from login) a             
  where datediff(date,firstday)=1;          
> 1.首先建立窗口函数，算出每一行对应的首次登陆时间     
  2.把该表设为a表，查找a表中登陆时间和首次登陆时间之差为1的数据，统计用户id出现的次数         
  3.算得第二日留存的用户数，用这个数除以总用户数，并保留三位小数即可
>           
> select                
  	round(              
  		(               
  		select              
  			count(distinct log2.user_id )               
  		from            
  			login log1              
  			inner join login log2 on log1.user_id = log2.user_id                
  		where               
  			log2.date = date_add( log1.date, interval 1 day )               
  		)* 1.0 / count( distinct user_id ),3                                    
  	) as p              
  from              
  	login;                  
## 69.每个人最近的登录日期(四)
题目描述        
牛客每天有很多人登录，请你统计一下牛客每个日期登录新用户个数，         
有一个登录(login)记录表，简况如下:           
![sql69](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql69.png)                
第1行表示user_id为2的用户在2020-10-12使用了客户端id为1的设备登录了牛客网，因为是第1次登录，所以是新用户             
...                 
第4行表示user_id为2的用户在2020-10-13使用了客户端id为2的设备登录了牛客网，因为是第2次登录，所以是老用户             
...                 
最后1行表示user_id为4的用户在2020-10-15使用了客户端id为1的设备登录了牛客网，因为是第2次登录，所以是老用户            
请你写出一个sql语句查询每个日期登录新用户个数，并且查询结果按照日期升序排序，上面的例子查询结果如下:                                                         
![sql69_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql69_2.png)                                        
查询结果表明:             
2020-10-12，有3个新用户(user_id为2，3，1)登录              
2020-10-13，没有新用户登录              
2020-10-14，有1个新用户(user_id为4)登录              
2020-10-15，没有新用户登录              
### solution
> select a.all_date as date, ifnull(count(b.user_id),0) as new              
  from          
      (select distinct(date) as all_date from login) as a           
  left join             
      (select user_id, min(date) as reg_date from login group by user_id) as b                            
  on a.all_date = b.reg_date group by a.all_date;           
> ifnull() 、函数用于判断第一个表达式是否为null，如果为null则返回第二个参数的值，如果不为null则返回第一个参数的值。           
>           
> select date,sum(case rn when 1 then 1 else 0 end )as new          
  from (select date,row_number() over(partition by user_id order by date) as rn from login) x           
  group by date order by date;                     
## 70.每个人最近的登录日期(五)
题目描述            
牛客每天有很多人登录，请你统计一下牛客每个日期新用户的次日留存率。           
有一个登录(login)记录表，简况如下:           
![sql70](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql70.png)        
第1行表示user_id为2的用户在2020-10-12使用了客户端id为1的设备登录了牛客网，因为是第1次登录，所以是新用户             
...                 
第4行表示user_id为2的用户在2020-10-13使用了客户端id为2的设备登录了牛客网，因为是第2次登录，所以是老用户                 
...             
最后1行表示user_id为4的用户在2020-10-15使用了客户端id为1的设备登录了牛客网，因为是第2次登录，所以是老用户                
请你写出一个sql语句查询每个日期新用户的次日留存率，结果保留小数点后面3位数(3位之后的四舍五入)，并且查询结果按照日期升序排序，上面的例子查询结果如下:                                   
![sql70_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql70_2.png)                
查询结果表明:                 
2020-10-12登录了3个(user_id为2，3，1)新用户，2020-10-13，只有2个(id为2,1)登录，故2020-10-12新用户次日留存率为2/3=0.667;              
2020-10-13没有新用户登录，输出0.000;              
2020-10-14登录了1个(user_id为4)新用户，2020-10-15，user_id为4的用户登录，故2020-10-14新用户次日留存率为1/1=1.000;              
2020-10-15没有新用户登录，输出0.000;              
(注意:sqlite里查找某一天的后一天的用法是:date(yyyy-mm-dd, '+1 day')，sqlite里1/2得到的不是0.5，得到的是0，只有1*1.0/2才会得到0.5)                   
### solution
> select b.date, round(count(l2.date)/count(b.date), 3) as p            
  from (                
      select l1.user_id, min(l1.date) first_date, date_add(min(l1.date), interval 1 day) next_date              
      from login as l1          
      group by l1.user_id           
  ) as a            
  left join login l2            
  on a.user_id=l2.user_id and a.next_date=l2.date           
  right join (              
      select distinct date              
      from login            
  ) as b            
  on a.first_date=b.date            
  group by b.date           
  order by b.date asc               
## 71.每个人最近的登录日期(六)
### solution
> select name as u_n,date,              
  sum(number) over(partition by user_id order by date) as ps_num            
  from passing_number p             
  left join user u              
  on p.user_id=u.id             
  order by date,u_n；                
>           
> select u.name u_n, a.date, sum(b.number) ps_num           
  from passing_number as a              
  inner join passing_number as b            
  on a.user_id=b.user_id and a.date>=b.date             
  inner join user as u              
  on a.user_id=u.id                 
  group by u_n, a.date              
  order by a.date asc, u_n asc;             
## 72.考试分数(一)
题目描述                
牛客每次考试完，都会有一个成绩表(grade)，如下:             
![sql72](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql72.png)            
第1行表示用户id为1的用户选择了C++岗位并且考了11001分                
...         
第8行表示用户id为8的用户选择了JS岗位并且考了9999分              
请你写一个sql语句查询各个岗位分数的平均数，并且按照分数降序排序，结果保留小数点后面3位(3位之后四舍五入):                    
![sql72_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql72_2.png)            
(注意: sqlite 1/2得到的不是0.5，得到的是0，只有1*1.0/2才会得到0.5，sqlite四舍五入的函数为round)                      
### solution
> select job,round(avg(score),3) as score_avg from grade group by job order by score_avg desc;
## 73.考试分数(二)
题目描述            
牛客每次考试完，都会有一个成绩表(grade)，如下:                 
![sql73](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql73.png)            
第1行表示用户id为1的用户选择了C++岗位并且考了11001分        
...         
第8行表示用户id为8的用户选择了前端岗位并且考了9999分          
请你写一个sql语句查询用户分数大于其所在工作(job)分数的平均分的所有grade的属性，并且以id的升序排序，如下:                    
![sql73_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql73_2.png)        
(注意: sqlite1/2得到的不是0.5，得到的是0，只有1*1.0/2才会得到0.5，sqlite四舍五入的函数为round)           
### solution
> select grade.* from grade              
  inner join                
  (select job,round(avg(score),3) as score_avg from grade group by job) as g            
  on grade.job=g.job where grade.score> g.score_avg;            
## 74.考试分数(三)
题目描述                
牛客每次举办企业笔试的时候，企业一般都会有不同的语言岗位，比如C++工程师，JAVA工程师，Python工程师，每个用户笔试完有不同的分数，现在有一个分数(grade)表简化如下:          
![sql74](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql74.png)            
第1行表示用户id为1的选择了language_id为1岗位的最后考试完的分数为12000，              
...                 
第7行表示用户id为7的选择了language_id为2岗位的最后考试完的分数为11000，                            
不同的语言岗位(language)表简化如下:                            
![sql74_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql74_2.png)                
请你找出每个岗位分数排名前2名的用户，得到的结果先按照language的name升序排序，再按照积分降序排序，最后按照grade的id升序排序，得到结果如下:                         
![sql74_3](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql74_3.png)            
### solution
> select g.id, l.name, g.score from                 
  (select *,dense_rank() over(partition by language_id order by score desc) r from grade) g,                
  language l            
  where g.language_id = l.id                
  and g.r <= 2                  
  order by l.name, g.score desc, g.id;              
>               
> select g1.id,l.name,g1.score from grade as g1                 
  inner join grade as g2 on g1.language_id=g2.language_id               
  inner join language as l on g1.language_id=l.id               
  where g1.score<=g2.score              
  group by g1.id,l.name,g1.score                
  having count(distinct g2.score)<=2                
  order by l.name asc,g1.score desc,g1.id asc;                                  
## 75.考试分数(四)
题目描述            
牛客每次考试完，都会有一个成绩表(grade)，如下:             
![sql75](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql75.png)            
第1行表示用户id为1的用户选择了C++岗位并且考了11001分            
...
第8行表示用户id为8的用户选择了B语言岗位并且考了9999分             
请你写一个sql语句查询各个岗位分数升序排列之后的中位数位置的范围，并且按job升序排序，结果如下:                          
![sql75_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql75_2.png)            
解释:             
第1行表示C++岗位的中位数位置范围为[2,2]，也就是2。因为C++岗位总共3个人，是奇数，所以中位数位置为2是正确的(即位置为2的10000是中位数)               
第2行表示Java岗位的中位数位置范围为[1,2]。因为Java岗位总共2个人，是偶数，所以要知道中位数，需要知道2个位置的数字，而因为只有2个人，所以中位数位置为[1,2]是正确的(即需要知道位置为1的12000与位置为2的13000才能计算出中位数为12500)               
第3行表示前端岗位的中位数位置范围为[2,2]，也就是2。因为B语言岗位总共3个人，是奇数，所以中位数位置为2是正确的(即位置为2的11000是中位数)            
(注意: sqlite 1/2得到的不是0.5，得到的是0，只有1*1.0/2才会得到0.5，sqlite四舍五入的函数为round，sqlite不支持floor函数，支持cast(x as integer) 函数，不支持if函数，支持case when ...then ...else ..end函数)                          
### solution
> select a.job, round(count(a.id)/2), round((count(a.id)+1)/2)          
  from grade a              
  group by a.job                
  order by job;         
>           
> select job,floor((count(*)+1)/2) as `start`,              
  floor((count(*)+1)/2)+if(count(*) % 2=1,0,1) as `end`                 
  from grade  group by job order by job;                             
## 76.考试分数(五)
题目描述                
牛客每次考试完，都会有一个成绩表(grade)，如下:             
![sql76](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql76.png)            
第1行表示用户id为1的用户选择了C++岗位并且考了11001分            
...
第8行表示用户id为8的用户选择了B语言岗位并且考了9999分             
请你写一个sql语句查询各个岗位分数的中位数位置上的所有grade信息，并且按id升序排序，结果如下:                         
![sql76_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql76_2.png)            
解释：         
第1行表示C++岗位的中位数位置上的为用户id为2，分数为10000，在C++岗位里面排名是第2            
第2，3行表示Java岗位的中位数位置上的为用户id为4,5，分数为12000,13000，在Java岗位里面排名是第2,1              
第4行表示B语言岗位的中位数位置上的为用户id为7，分数为11000，在前端岗位里面排名是第2                 
(注意: sqlite 1/2得到的不是0.5，得到的是0，只有1*1.0/2才会得到0.5，sqlite四舍五入的函数为round，sqlite不支持floor函数，支持cast(x as integer) 函数，不支持if函数，支持case when ...then ...else ..end函数，sqlite不支持自定义变量)                                   
### solution
> select id,job,score,s_rank from                
  (select *,(row_number() over(partition by job order by score desc))as s_rank,         
  (count(score) over(partition by job))as num from grade) t         
  where abs(t.s_rank-(t.num+1)/2)<1         
  order by id;              
> 无论奇偶，中位数的位置距离（个数+1）/2 小于1。
## 77.课程订单分析(一)
题目描述                
有很多同学在牛客购买课程来学习，购买会产生订单存到数据库里。              
有一个订单信息表(order_info)，简况如下:              
![sql77](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql77.png)            
第1行表示user_id为557336的用户在2025-10-10的时候使用了client_id为1的客户端下了C++课程的订单，但是状态为没有购买成功。           
第2行表示user_id为230173543的用户在2025-10-12的时候使用了client_id为2的客户端下了Python课程的订单，状态为购买成功。             
...             
最后1行表示user_id为557336的用户在2025-10-24的时候使用了client_id为1的客户端下了Python课程的订单，状态为没有购买成功。             
请你写出一个sql语句查询在2025-10-15以后状态为购买成功的C++课程或者Java课程或者Python的订单，并且按照order_info的id升序排序，以上例子查询结果如下:                            
![sql77_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql77_2.png)            
### solution
> select * from order_info                  
  where datediff(`date`,'2025-10-15')>0             
  and product_name in ('C++','Java','Python')                   
  and status = 'completed'              
  order by id asc;              
>               
> select * from order_info              
  where date >'2025-10-15'              
  and product_name in ('C++','Java','Python')               
  and status = 'completed'              
  order by id asc;              
## 78.课程订单分析(二)
题目描述            
有很多同学在牛客购买课程来学习，购买会产生订单存到数据库里。                            
有一个订单信息表(order_info)，简况如下:                            
![sql78](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql78.png)            
第1行表示user_id为557336的用户在2025-10-10的时候使用了client_id为1的客户端下了C++课程的订单，但是状态为没有购买成功。           
第2行表示user_id为230173543的用户在2025-10-12的时候使用了client_id为2的客户端下了Python课程的订单，状态为购买成功。             
...
最后1行表示user_id为557336的用户在2025-10-25的时候使用了client_id为1的客户端下了C++课程的订单，状态为购买成功。          
请你写出一个sql语句查询在2025-10-15以后，同一个用户下单2个以及2个以上状态为购买成功的C++课程或Java课程或Python课程的user_id，并且按照user_id升序排序，以上例子查询结果如下:                                
![sql78_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql78_2.png)            
解析:             
id为4，6的订单满足以上条件，输出对应的user_id为57;            
id为5，7的订单满足以上条件，输出对应的user_id为557336;            
按照user_id升序排序。                                      
### solution
> select user_id from order_info                    
  where date >'2025-10-15'                                         
  and product_name in ('C++','Java','Python')                
  and status = 'completed'              
  group by user_id having count(user_id)>=2             
  order by user_id asc;                                      
## 79.课程订单分析(三)
题目描述                
有很多同学在牛客购买课程来学习，购买会产生订单存到数据库里。              
有一个订单信息表(order_info)，简况如下:                  
![sql79](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql79.png)            
第1行表示user_id为557336的用户在2025-10-10的时候使用了client_id为1的客户端下了C++课程的订单，但是状态为没有购买成功。           
第2行表示user_id为230173543的用户在2025-10-12的时候使用了client_id为2的客户端下了Python课程的订单，状态为购买成功。             
...
最后1行表示user_id为557336的用户在2025-10-25的时候使用了client_id为1的客户端下了C++课程的订单，状态为购买成功。              
请你写出一个sql语句查询在2025-10-15以后，同一个用户下单2个以及2个以上状态为购买成功的C++课程或Java课程或Python课程的订单信息，并且按照order_info的id升序排序，以上例子查询结果如下:                              
![sql79_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql79_2.png)                
解析:             
id为4，6的订单满足以上条件，输出它们的对应的信息;             
id为5，7的订单满足以上条件，输出它们的对应的信息;                 
按照id升序排序                                
### solution
> select * from order_info          
  where date>'2025-10-15'           
  and product_name in('C++','Java','Python')                
  and status='completed'                
  and user_id in            
  (select user_id from order_info               
  where date>'2025-10-15'               
  and product_name in('C++','Java','Python')            
  and status='completed'            
  group by user_id              
  having count(product_name)>=2         
  )             
  order by id;                      
>           
> select id,user_id,product_name,status,client_id,date              
  from          
  (select *,count(*)over(partition by user_id) as count_num             
  from order_info               
   where product_name in ('C++','Java','Python')                
  and status='completed'            
  and date >'2025-10-15'            
  ) as r                
  where r.count_num >=2                 
  order by id asc;                                
## 80.课程订单分析(四)
题目描述                
有很多同学在牛客购买课程来学习，购买会产生订单存到数据库里。              
有一个订单信息表(order_info)，简况如下:              
![sql80](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql80.png)                                
第1行表示user_id为557336的用户在2025-10-10的时候使用了client_id为1的客户端下了C++课程的订单，但是状态为没有购买成功。               
第2行表示user_id为230173543的用户在2025-10-12的时候使用了client_id为2的客户端下了Python课程的订单，状态为购买成功。             
最后1行表示user_id为557336的用户在2025-10-25的时候使用了client_id为1的客户端下了Python课程的订单，状态为购买成功。                   
请你写出一个sql语句查询在2025-10-15以后，如果有一个用户下单2个以及2个以上状态为购买成功的C++课程或Java课程或Python课程，那么输出这个用户的user_id，以及满足前面条件的第一次购买成功的C++课程或Java课程或Python课程的日期first_buy_date，以及购买成功的C++课程或Java课程或Python课程的次数cnt，并且输出结果按照user_id升序排序，以上例子查询结果如下:                            
![sql80_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql80_2.png)                            
解析:             
id为4，6的订单满足以上条件，输出57，id为4的订单为第一次购买成功，输出first_buy_date为2025-10-23，总共成功购买了2次;             
id为5，7，8的订单满足以上条件，输出557336，id为5的订单为第一次购买成功，输出first_buy_date为2025-10-23，总共成功购买了3次;               
### solution
> select user_id,min(date) first_buy_date,count(user_id) cnt                
  from order_info               
  where date > '2025-10-15'             
  and product_name in ('C++','Java','Python')               
  and status = 'completed'              
  group by user_id              
  having count(user_id) >= 2                
  order by user_id;                 
## 81.课程订单分析(五)
题目描述            
有很多同学在牛客购买课程来学习，购买会产生订单存到数据库里。              
有一个订单信息表(order_info)，简况如下:                
![sql81](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql81.png)            
第1行表示user_id为557336的用户在2025-10-10的时候使用了client_id为1的客户端下了C++课程的订单，但是状态为没有购买成功。               
第2行表示user_id为230173543的用户在2025-10-12的时候使用了client_id为2的客户端下了Python课程的订单，状态为购买成功。             
...             
最后1行表示user_id为557336的用户在2025-10-26的时候使用了client_id为1的客户端下了Python课程的订单，状态为购买成功。               
请你写出一个sql语句查询在2025-10-15以后，如果有一个用户下单2个以及2个以上状态为购买成功的C++课程或Java课程或Python课程，
那么输出这个用户的user_id，以及满足前面条件的第一次购买成功的C++课程或Java课程或Python课程的日期first_buy_date，
以及满足前面条件的第二次购买成功的C++课程或Java课程或Python课程的日期second_buy_date，以及购买成功的C++课程或Java课程或Python课程的次数cnt，
并且输出结果按照user_id升序排序，以上例子查询结果如下:                                                     
![sql81_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql81_2.png)                                               
解析:             
id为4，6的订单满足以上条件，输出57，id为4的订单为第一次购买成功，输出first_buy_date为2025-10-23，id为6的订单为第二次购买，输出second_buy_date为2025-10-24，总共成功购买了2次;              
id为5，7，8的订单满足以上条件，输出557336，id为5的订单为第一次购买成功，输出first_buy_date为2025-10-23，id为7的订单为第二次购买，输出second_buy_date为2025-10-25，总共成功购买了3次;            
### solution
> select user_id,min(date) as first_buy_date,second_buy_date,count(*)as cnt             
  from              
  (select *,count(*) over(partition by user_id)as cnt,          
  lead(date,1,0) over(partition by user_id order by date) as second_buy_date             
  from order_info where product_name in('Java','C++','Python')              
  and status='completed' and date>'2025-10-15') t               
  where t.cnt >=2 group by user_id order by user_id;       
> lead(<expression>[,offset[, default_value]])函数，该函数作用是：获取某顺序字段当前记录的下offset条记录, 如果记录不存在则返回default_value              
## 82.课程订单分析(六)
题目描述                
有很多同学在牛客购买课程来学习，购买会产生订单存到数据库里。              
有一个订单信息表(order_info)，简况如下:                        
![sql82](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql82.png)                
第1行表示user_id为557336的用户在2025-10-10的时候使用了client_id为1的客户端下了C++课程的非拼团(is_group_buy为No)订单，但是状态为没有购买成功。               
第2行表示user_id为230173543的用户在2025-10-12的时候使用了client_id为2的客户端下了Python课程的非拼团(is_group_buy为No)订单，状态为购买成功。             
...             
最后1行表示user_id为557336的用户在2025-10-25的时候使用了下了C++课程的拼团(is_group_buy为Yes)订单，拼团不统计客户端，所以client_id所以为0，状态为购买成功。                
有一个客户端表(client)，简况如下:                           
![sql82_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql82_2.png)                
请你写出一个sql语句查询在2025-10-15以后，同一个用户下单2个以及2个以上状态为购买成功的C++课程或Java课程或Python课程的订单id，
是否拼团以及客户端名字信息，最后一列如果是非拼团订单，则显示对应客户端名字，如果是拼团订单，则显示NULL，并且按照order_info的id升序排序，以上例子查询结果如下:                            
![sql82_3](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql82_3.png)            
解析:                 
id为4，6的订单满足以上条件，且因为4是通过IOS下单的非拼团订单，输出对应的信息，6是通过PC下单的非拼团订单，输出对应的信息以及客户端名字;               
id为5，7的订单满足以上条件，且因为5与7都是拼团订单，输出对应的信息以及NULL;按照id升序排序             
### solution
> select a.id,is_group_buy,name                 
  from                  
  (select *,count(*) over(partition by user_id) as count_num                
  from order_info where product_name in ('C++','Java','Python')             
  and status='completed' and date >'2025-10-15') a              
  left join client b            
  on a.client_id = b.id         
  where a.count_num >=2             
  order by id asc;                      
>           
> select o_in.id,o_in.is_group_buy,client.name              
  from ( # 将满足条件的订单全部找出来，该查询结果相当于题 牛客的订单分析（三）               
      select id,client_id,is_group_buy              
      from order_info           
      where user_id in (                
          select user_id            
          from order_info               
          where product_name in ('C++','Java','Python')             
          and status = 'completed'          
          and datediff(date,'2025-10-15') > 0           
          group by user_id          
          having count(*)>= 2           
      )             
      and product_name in ('C++','Java','Python')           
      and status = 'completed'          
      and datediff(date,'2025-10-15') > 0           
  ) o_in left join client           
      on o_in.client_id = client.id         
  order by o_in.id;         
## 83.课程订单分析(七)
题目描述                
有很多同学在牛客购买课程来学习，购买会产生订单存到数据库里。              
有一个订单信息表(order_info)，简况如下:          
![sql83](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql83.png)            
第1行表示user_id为557336的用户在2025-10-10的时候使用了client_id为1的客户端下了C++课程的非拼团(is_group_buy为No)订单，但是状态为没有购买成功。           
第2行表示user_id为230173543的用户在2025-10-12的时候使用了client_id为2的客户端下了Python课程的非拼团(is_group_buy为No)订单，状态为购买成功。             
...                 
最后1行表示user_id为557336的用户在2025-10-25的时候使用了下了C++课程的拼团(is_group_buy为Yes)订单，拼团不统计客户端，所以client_id所以为0，状态为购买成功。                
有一个客户端表(client)，简况如下:                              
![sql83_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql83_2.png)            
请你写出一个sql语句查询在2025-10-15以后，同一个用户下单2个以及2个以上状态为购买成功的C++课程或Java课程或Python课程的来源信息，
第一列是显示的是客户端名字，如果是拼团订单则显示GroupBuy，第二列显示这个客户端(或者是拼团订单)有多少订单，最后结果按照第一列(source)升序排序，
以上例子查询结果如下:                                             
![sql83_3](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql83_3.png)                                   
解析:             
id为4，6的订单满足以上条件，且因为4是通过IOS下单的非拼团订单，则记: IOS 1，6是通过PC下单的非拼团订单，则记: PC 1;               
id为5，7的订单满足以上条件，且因为5与7都是拼团订单，则记: GroupBuy 2;                
最后按照source升序排序。             
### solution
> select                
  if(name is null,'GroupBuy',name) as source,count(1) as cnt                
  from              
  (select user_id,is_group_buy,name,count(user_id) over(partition by user_id) as cnt                
  from order_info           
  left join client                
  on order_info.client_id = client.id               
  where date>'2025-10-15' and status = 'completed' and product_name in ('C++','Java','Python')              
  ) as t             
  where cnt >=2         
  group by name             
  order by source;          
>           
> select                    
  (case when client_id=0 then 'GroupBuy' else (select name from client c where c.id = nt.client_id ) end)               
  as source,count(*)            
  from          
  (select *, count(id) over(partition by user_id) cnt           
  from order_info               
  where date > '2025-10-15'         
  and status = 'completed'          
  and product_name in ('Java', 'Python', 'C++')         
  ) as nt           
  where cnt >= 2            
  group by source               
  order by source                         
## 84.实习广场投递简历分析(一)
题目描述                
在牛客实习广场有很多公司开放职位给同学们投递，同学投递完就会把简历信息存到数据库里。          
现在有简历信息表(resume_info)，部分信息简况如下:                 
![sql84](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql84.png)            
第1行表示，在2025年1月2号，C++岗位收到了53封简历          
...                 
最后1行表示，在2026年1月4号，Java岗位收到了230封简历               
请你写出SQL语句查询在2025年内投递简历的岗位和数量，并且按数量降序排序，以上例子查询结果如下:                          
![sql84_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql84_2.png)           
### solution
> select job,sum(num) as cnt from resume_info           
  where date between '2025-01-01' and '2025-12-31'          
  group by job          
  order by cnt desc;            
>           
> select job,sum(num) as cnt from resume_info               
  where  year(date) = 2025          
  group by job              
  order by cnt desc;                
## 85.实习广场投递简历分析(二)
题目描述                                
在牛客实习广场有很多公司开放职位给同学们投递，同学投递完就会把简历信息存到数据库里。                                     
现在有简历信息表(resume_info)，部分信息简况如下:                          
![sql85](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql85.png)            
第1行表示，在2025年1月2号，C++岗位收到了53封简历                  
...                 
最后1行表示，在2026年2月6号，Java岗位收到了231封简历               
请你写出SQL语句查询在2025年内投递简历的每个岗位，每一个月内收到简历的数量，并且按先按月份降序排序，再按简历数目降序排序，以上例子查询结果如下:                                  
![sql85_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql85_2.png)                            
### solution
> select job, left(date,7) mon, sum(num) cnt                
  from resume_info              
  where year(date)=2025             
  group by job,mon              
  order by mon desc,cnt desc;           
>           
> select job, substr(date, 1,7) mon, sum(num) as cnt                
  from resume_info              
  where year(date) = 2025               
  group by job, mon             
  order by mon desc, cnt desc;          
>               
> select job,                               
         date_format(date,"%Y-%m") as mon,                        
         sum(num) as cnt                
  from resume_info              
  where left(date,4)="2025"             
  group by  job,mon             
  order by mon desc, cnt desc;               
>           
> select job, date_format(date,"%Y-%m") as mon,         
  sum(num) as cnt           
  from resume_info          
  where date_format(date,"%Y")="2025"           
  group by job,mon          
  order by mon desc, cnt desc;                    
## 86.实习广场投递简历分析(三)
题目描述            
在牛客实习广场有很多公司开放职位给同学们投递，同学投递完就会把简历信息存到数据库里。              
现在有简历信息表(resume_info)，部分信息简况如下:             
![sql86](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql86.png)            
第1行表示，在2025年1月2号，C++岗位收到了53封简历                        
...         
最后1行表示，在2027年2月6号，C++岗位收到了231封简历            
请你写出SQL语句查询在2025年投递简历的每个岗位，每一个月内收到简历的数量和，对应的2026年的同一个月同岗位，
收到简历的数量，最后的结果先按first_year_mon月份降序，再按job降序排序显示，以上例子查询结果如下:                               
![sql86_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql86_2.png)                
解析:             
第1行表示Python岗位在2025年2月收到了93份简历，在对应的2026年2月收到了846份简历              
...             
最后1行表示Python岗位在2025年1月收到了107份简历，在对应的2026年1月收到了470份简历                            
### solution
> select a.job,              
      date_format(d1, '%Y-%m') first_year_mon,           
      c1 first_year_cnt,            
      date_format(d2, '%Y-%m') second_year_mon,                 
      c2 second_yeat_cnt            
  from(             
      select job, date d1, sum(num) c1          
      from resume_info              
      where year(date) = 2025               
      group by month(date), job) a              
  join(             
      select job, date d2, sum(num) c2          
      from resume_info          
      where year(date) = 2026               
      group by month(date), job) b              
  on a.job = b.job          
  and month(d1) = month(d2)             
  order by first_year_mon desc, job desc;           
>           
> select a.job,                  
      substr(d1,1,7) first_year_mon,                    
      c1 first_year_cnt,                
      substr(d2, 1,7) second_year_mon,              
      c2 second_yeat_cnt                
  from(             
      select job, date d1, sum(num) c1              
      from resume_info              
      where year(date) = 2025               
      group by month(date), job) a              
  join(             
      select job, date d2, sum(num) c2              
      from resume_info              
      where year(date) = 2026               
      group by month(date), job) b              
  on a.job = b.job          
  and month(d1) = month(d2)                 
  order by first_year_mon desc, job desc;                             
## 87.最差是第几名(一)
题目描述                
SQL班每个人的综合成绩用A,B,C,D,E表示，90分以上都是A，80~90分都是B，60~70分为C，50~60为D，E为50分以下                
因为每个名次最多1个人，比如有2个A，那么必定有1个A是第1名，有1个A是第2名(综合成绩同分也会按照某一门的成绩分先后)。              
每次SQL考试完之后，老师会将班级成绩表展示给同学看。             
现在有班级成绩表(class_grade)如下:            
![sql87](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql87.png)            
第1行表示成绩为A的学生有2个             
.......             
最后1行表示成绩为B的学生有2个                
请你写出一个SQL查询，如果一个学生知道了自己综合成绩以后，最差是排第几名? 结果按照grade升序排序，以上例子查询如下:                          
![sql87_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql87_2.png)            
解析:             
第1行表示，学生成绩为A的知道自己最差为第2名         
第2行表示，学生成绩为B的知道自己最差为第4名         
第3行表示，学生成绩为C的知道自己最差为第6名         
第4行表示，学生成绩为D的知道自己最差为第7名         
### solution
> select grade, sum(number) over(order by grade) t_rank              
  from class_grade              
  order by grade;               
## 88.最差是第几名(二)
题目描述                                
SQL班每个人的综合成绩用A,B,C,D,E表示，90分以上都是A，80~90分都是B，60~70分为C，50~60为D，E为50分以下                           
因为每个名次最多1个人，比如有2个A，那么必定有1个A是第1名，有1个A是第2名(综合成绩同分也会按照某一门的成绩分先后)。                        
每次SQL考试完之后，老师会将班级成绩表展示给同学看。                          
现在有班级成绩表(class_grade)如下:            
![sql88](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql88.png)            
第1行表示成绩为A的学生有2个             
...         
最后1行表示成绩为D的学生有2个                
老师想知道学生们综合成绩的中位数是什么档位，请你写SQL帮忙查询一下，如果只有1个中位数，输出1个，如果有2个中位数，按grade升序输出，以上例子查询结果如下:                           
![sql88_2](http://github.com/xidianlina/practice/raw/master//mysql_practice/picture/sql88_2.png)                                    
解析:             
总体学生成绩排序如下:A, A, B, B, B, B, C, C, C, C, D, D，总共12个数，取中间的2个，取6，7为:B,C               
### solution
> select grade from             
  (select grade,                
  sum(number) over(order by grade) as a,                
  sum(number) over(order by grade desc) as b,               
  (select sum(number) from class_grade) as cnt          
  from class_grade          
  ) as t            
  where a>=cnt/2 and b>=cnt/2               
  order by grade;                   
> 当某一数的正序和逆序累计均大于整个序列的数字个数的一半即为中位数  