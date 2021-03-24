mysql练习
======
# 一.基础知识
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
>           
> 1.create trigger ：创建触发器           
  2.触发器要说明是在after 还是before事务发生时触发           
  3.要指明是insert 、delete、update操作             
  4.on 表名               
  5.begin和end之间写触发的动作
> 6. new 关键字表示更新后的表的字段 ，old表示更新前的表的字段           
## 42.删除emp_no重复的记录，只保留最小的id对应的记录。
### solution
## 43.将所有to_date为9999-01-01的全部更新为NULL,且 from_date更新为2001-01-01。
### solution
## 44.将id=5以及emp_no=10001的行数据替换成id=5以及emp_no=10005,其他数据保持不变，使用replace实现。
### solution
## 45.将titles_test表名修改为titles_2017
### solution


