### 23.sql中drop、delete和truncate的区别
> drop删除表结构和表的数据内容，并释放空间;               
  truncate只删除表的数据内容，释放空间，但不删除表结构。               
  带有where子句的delete删除指定的数据行，不带where子句的delete仅删除表的所有数据内容，不释放空间，不删除表结构。        
>           
> drop语句将表所占用的空间全释放掉；truncate语句将表和索引所占用的空间会恢复到初始大小；delete语句不会减少表或索引所占用的空间。                  
  在速度上，一般来说，drop> truncate > delete。                    
  应用范围上。drop和truncate只能对table；delete可以是table和view。              
  delete语句为dml(data maintain Language),这个操作会被放到rollback segment中,事务提交后才生效。如果有相应的tigger,执行的时候将被触发。
  delete语句执行删除的过程是每次从表中删除一行，并且同时将该行的删除操作作为事务记录在日志中保存以便进行进行回滚操作。
  truncate、drop是ddl(data define language),操作立即生效，原数据不放到rollback segment中，不能回滚。并且在删除的过程中不会激活与表有关的删除触发器。执行速度快。            
>           
> truncate table 表名 速度快,而且效率高,因为truncate table在功能上与不带where子句的delete语句相同:二者均删除表中的全部行。
  但truncate table比delete速度快，且使用的系统和事务日志资源少。delete语句每次删除一行，并在事务日志中为所删除的每行记录一项。
> truncate table通过释放存储表数据所用的数据页来删除数据，并且只在事务日志中记录页的释放。                 
  truncate table删除表中的所有行，但表结构及其列、约束、索引等保持不变。新行标识所用的计数值重置为该列的种子。如果想保留标识计数值，请改用delete。
> 如果要删除表定义及其数据，请使用drop table语句。                
  对于由foreign key约束引用的表，不能使用truncate table，而应使用不带where子句的delete语句。由于truncate table不记录在日志中，所以它不能激活触发器。                            
