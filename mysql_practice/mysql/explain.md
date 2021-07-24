### 21.explain
> MySQL提供了一个explain命令, 它可以对select语句进行分析, 并输出select执行的详细信息, 以供开发人员针对性优化。     
> explain的用途            
> (1).表的读取顺序如何                  
  (2).数据读取操作有哪些操作类型             
  (3).哪些索引可以使用              
  (4).哪些索引被实际使用             
  (5).表之间是如何引用              
  (6).每张表有多少行被优化器查询             
>           
> 各列的含义如下:          
  (1).id        
  select查询的标识符，包含一组数字，表示查询中执行select子句或操作表的顺序            
  id相同:id相同时执行顺序从上至下            
  id不同:如果是子查询，id的序号会递增，id的值越大优先级越高，越先被执行            
  id相同又不同:id如果相同，可以认为是一组，从上往下顺序执行;在所有组中，id值越大，优先级越高，越先执行   
>                        
> (2).select_type查询类型           
  SIMPLE:简单查询，不包含子查询或Union查询            
  PRIMARY:查询中若包含任何复杂的子部分，最外层查询则被标记为主查询          
  SUBQUERY:在select或where中包含子查询。包含的子查询语句无法转换为半连接，并且为不相关子查询，查询优化器采用物化方案执行该子查询，该子查询的第一个select就会SUBQUERY。该查询由于被物化，只需要执行一次。                            
> 子查询语句中的子查询结果集中的记录保存到临时表的过程称之为物化。存储子查询结果集的临时表称之为物化表。           
> 因为物化表的记录都建立了索引（基于内存的物化表有哈希索引，基于磁盘的有B+树索引），因此通过 IN 语句判断某个操作数在不在子查询的结果集中变得很快，从而提升语句的性能。                 
> 半连接跟IN语句子查询有关。                
  SELECT ... FROM outer_tables WHERE expr IN (SELECT ... FROM inner_tables ...) AND ...             
  outer_tables表对inner_tables半连接的意思:对于outer_tables的某条记录来说，仅关心在inner_tables表中是否存在匹配的记录，而不用关心具体有多少条记录与之匹配，最终结果只保留outer_tables表的记录。                                    
  DERIVED:在FROM列表中包含的子查询被标记为DERIVED（衍生），MySQL会递归执行这些子查询，把结果放在临时表中。MySQL5.7+ 进行优化了，增加了derived_merge（派生合并），默认开启，可加快查询效率。
>对于采用物化形式执行的包含派生表的查询，该派生表的对应的子查询为DERIVED。                                
  UNION:若第二个select出现在uion之后，则被标记为UNION          
  DEPENDENT UNION:UNION 中的第二个或后面的查询语句, 取决于外面的查询         
  UNION RESULT:从UNION表获取结果的select,MySQL选择使用临时表完成UNION查询的去重工作。               
>                                  
> (3).table查询的是哪个表。这个值可能是表名、表的别名或者一个为查询产生临时表的标识符，如派生表、子查询或集合。                                        
>                   
> (4).partitions匹配的分区
>                              
> (5).type访问的类型,它提供了判断查询是否高效的重要依据         
  NULL:MySQL能够在优化阶段分解查询语句，在执行阶段用不着再访问表或索引           
  system:表只有一行记录（等于系统表），这是const类型的特列，平时不大会出现，可以忽略           
  const:表示通过索引一次就找到了，const用于比较primary key或uique索引，因为只匹配一行数据，所以很快，如主键置于where列表中，MySQL就能将该查询转换为一个常量           
  eq_ref:唯一性索引扫描，对于每个索引键，表中只有一条记录与之匹配，常见于主键或唯一索引扫描              
  ref:非唯一性索引扫描，返回匹配某个单独值的所有行。本质上也是一种索引访问，返回所有匹配某个单独值的行,然而可能会找到多个符合条件的行，应该属于查找和扫描的混合体            
  ref_or_null:类似ref，但是可以搜索值为NULL的行          
  index_merge:表示使用了索引合并的优化方法            
  range:只检索给定范围的行，使用一个索引来选择行，key列显示使用了哪个索引.一般就是在你的where语句中出现between、<>、in等的查询。          
  index:从索引中读取              
  ALL:将遍历全表以找到匹配行           
  index只遍历索引树，通常比All快,因为索引文件通常比数据文件小，也就是虽然all和index都是读全表，但index是从索引中读取的，而all是从硬盘读的。             
  不同的type类型的性能关系如下:         
  NULL>system>const>eq_ref>ref>fulltext>ref_or_null>index_merge>unique_subquery>index_subquery>range>index>ALL //最好到最差              
  备注：掌握以下10种常见的即可           
  NULL>system>const>eq_ref>ref>ref_or_null>index_merge>range>index>ALL 
> 一般来说，得保证查询达到range级别，最好达到ref               
>                                                  
> (6).possible_keys显示可能应用在这张表中的索引，一个或多个，但不一定实际使用到
>                       
>(7).key实际使用到的索引，如果为NULL，则没有使用索引。查询中若使用了覆盖索引（查询的列刚好是索引），则该索引仅出现在key列表           
>                                 
> (8).key_len表示索引中使用的字节数，可通过该列计算查询中使用的索引的长度             
  在不损失精确度的情况下，key_len长度越短越好，key_len显示的值为索引字段最大的可能长度，并非实际使用长度，即key_len是根据定义计算而得，不是通过表内检索出的               
  key_len 的计算规则如下:          
  字符串       
  char(n): n字节长度            
  varchar(n): 如果是utf8编码, 则是3n+2字节; 如果是utf8mb4编码, 则是4n+2字节.          
  数值类型:         
  TINYINT: 1字节          
  SMALLINT: 2字节         
  MEDIUMINT: 3字节        
  INT: 4字节          
  BIGINT: 8字节           
  时间类型          
  DATE: 3字节         
  TIMESTAMP: 4字节            
  DATETIME: 8字节             
  字段属性: NULL 属性 占用一个字节. 如果一个字段是 NOT NULL 的, 则没有此属性. 
>                           
> (9).ref显示索引的哪一列被使用了，如果可能的话，是一个常数，哪些列或常量被用于查找索引列上的值 
>                              
> (10).rows根据表统计信息及索引选用情况，大致估算出找到所需的记录所需读取的行数 
> 如果查询优化器使用全表扫描查询，rows 列代表预计的需要扫码的行数                          
  如果查询优化器使用索引执行查询，rows 列代表预计扫描的索引记录行数                           
>                         
> (11).filtered查询的表行占表的百分比              
> 对于单表来说意义不大，主要用于连接查询中。                 
  前文中也已提到 filtered 列，是一个百分比的值，对于连接查询来说，主要看驱动表的 filtered列的值 ，通过 rows * filtered/100 计算可以估算出被驱动表还需要执行的查询次数。                           
>                         
> (12).Extra包含不适合在其它列中显示但十分重要的额外信息          
  Using filesort            
  说明MySQL会对数据使用一个外部的索引排序，而不是按照表内的索引顺序进行读取，MySQL中无法利用索引完成的排序操作称为“文件排序”           
  Using temporary           
  使用了临时表保存中间结果，MySQL在对结果排序时使用临时表，常见于排序order by 和分组查询group by            
  Using index           
  表示相应的select操作中使用了覆盖索引（Covering Index）,避免访问了表的数据行，效率不错！            
  如果同时出现using where，表明索引被用来执行索引键值的查找            
  如果没有同时出现using where，表明索引用来读取数据而非执行查找动作            
  Using where           
  使用了where条件            
  Using join buffer         
  使用了连接缓存           
  distinct          
  一旦mysql找到了与行相联合匹配的行，就不再搜索了                
  Select tables optimized away          
  SELECT操作已经优化到不能再优化了（MySQL根本没有遍历表或索引就返回数据了）                
  impossible where                  
  where子句的值总是false，不能用来获取任何元组                   
>                             
> 参考 https://segmentfault.com/a/1190000021458117                
> https://segmentfault.com/a/1190000008131735               
> https://segmentfault.com/a/1190000021815758 
