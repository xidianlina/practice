mongoDb
======
>MongoDB是由C++语言编写的，是一个基于分布式文件存储的开源数据库系统。                
 在高负载的情况下，添加更多的节点，可以保证服务器性能。                
 MongoDB旨在为WEB应用提供可扩展的高性能数据存储解决方案。              
 MongoDB将数据存储为一个文档，数据结构由键值(key=>value)对组成。MongoDB文档类似于JSON对象。字段值可以包含其他文档，数组及文档数组。               
> ![mongodb](http://github.com/xidianlina/practice/raw/master//mongo_practice/picture/mongodb.png)          
>                                         
>文档是一组键值(key-value)对(即BSON)。MongoDB的文档不需要设置相同的字段，并且相同的字段不需要相同的数据类型，这与关系型数据库有很大的区别，也是MongoDB非常突出的特点。                     
>a.文档中的键/值对是有序的。                    
 b.文档中的值不仅可以是在双引号里面的字符串，还可以是其他几种数据类型（甚至可以是整个嵌入的文档)。             
 c.MongoDB区分类型和大小写。             
 d.MongoDB的文档不能有重复的键。                   
 e.文档的键是字符串。                    
>                       
>集合就是MongoDB文档组，类似于RDBMS中的表格table。                              
 集合存在于数据库中，集合没有固定的结构，这意味着在对集合可以插入不同格式和类型的数据，但通常情况下插入集合的数据都会有一定的关联性。                     
 当第一个文档插入时，集合就会被创建。                         
>                   
> ![mongo](http://github.com/xidianlina/practice/raw/master//mongo_practice/picture/mongo.png)              
>                   
## 1.创建数据库:         
>use DATABASE_NAME                      
 如果数据库不存在，则创建数据库，否则切换到指定数据库             
 刚创建的数据库并不在数据库的列表中， 要显示它，需要向数据库插入一些数据                     
## 2.删除数据库:              
>db.dropDatabase()                                        
>删除当前数据库                                                                  
## 3.创建集合:                                   
>db.createCollection(name, options)                                    
 参数说明:              
 name:要创建的集合名称                      
 options:可选参数, 指定有关内存大小及索引的选项               
 db.createCollection("mycol", {capped:true, size:6142800, max:10000})                
 capped如果为true，则创建固定集合。固定集合是指有着固定大小的集合，当达到最大值时，它会自动覆盖最早的文档。当该值为true时，必须指定size参数。                
 size为固定集合指定一个最大值，即字节数。如果capped为true，也需要指定该字段。                  
 max数值(可选)指定固定集合中包含文档的最大数量。             
 在MongoDB中，不需要创建集合。当插入一些文档时，MongoDB会自动创建集合。                             
>查看已有集合使用show collections或show tables命令                                                                  
## 4.删除集合:                                                                    
>db.collection.drop()                                                      
##5.插入文档:              
>文档的数据结构和JSON基本一样。              
 所有存储在集合中的数据都是BSON格式。                   
 BSON是一种类似JSON的二进制形式的存储格式，是Binary JSON的简称。                  
>                       
>MongoDB使用insert()或save()方法向集合中插入文档，语法如下：                   
 db.COLLECTION_NAME.insert(document)或db.COLLECTION_NAME.save(document)                  
 save():如果_id主键存在则更新数据，如果不存在就插入数据。该方法新版本中已废弃，可以使用 db.collection.insertOne() 或 db.collection.replaceOne() 来代替。               
 insert(): 若插入的数据主键已经存在，则会抛 org.springframework.dao.DuplicateKeyException 异常，提示主键重复，不保存当前数据。                    
 3.2 版本之后新增了 db.collection.insertOne() 和 db.collection.insertMany()。                        
 db.collection.insertOne() 用于向集合插入一个新文档，语法格式如下：                 
 db.collection.insertOne(                           
    <document>,                         
    {                       
       writeConcern: <document>                 
    }                           
 )                      
 db.collection.insertMany() 用于向集合插入一个多个文档，语法格式如下：                   
 db.collection.insertMany(                  
    [ <document 1> , <document 2>, ... ],               
    {               
       writeConcern: <document>,                    
       ordered: <boolean>               
    }                   
 )                  
 参数说明：                  
 document：要写入的文档。                   
 writeConcern：写入策略，默认为 1，即要求确认写操作，0 是不要求。                       
 ordered：指定是否按顺序写入，默认 true，按顺序写入。                                                                 
## 6.更新文档:              
>MongoDB使用update()和save()方法来更新集合中的文档。                   
 update()方法用于更新已存在的文档。语法格式如下：                   
 db.collection.update(              
    <query>,                
    <update>,                   
    {               
      upsert: <boolean>,                
      multi: <boolean>,                 
      writeConcern: <document>                  
    }                   
 )              
 参数说明：                  
 query : update的查询条件，类似sql update查询内where后面的。                   
 update : update的对象和一些更新的操作符（如$,$inc,$set...）等，也可以理解为sql update查询内set后面的                                
 upsert : 可选，这个参数的意思是，如果不存在update的记录，是否插入objNew,true为插入，默认是false，不插入。                                  
 multi : 可选，mongodb 默认是false,只更新找到的第一条记录，如果这个参数为true,就把按条件查出来多条记录全部更新。                        
 writeConcern :可选，抛出异常的级别。                  
>                               
>save()方法通过传入的文档来替换已有文档，_id 主键存在就更新，不存在就插入。语法格式如下：              
 db.collection.save(                
    <document>,             
    {                   
      writeConcern: <document>              
    }               
 )                  
 参数说明：                  
 document : 文档数据。                   
 writeConcern :可选，抛出异常的级别。                          
>                               
## 7.查询文档:              
>MongoDB查询文档使用find()方法。             
 find()方法以非结构化的方式来显示所有文档。                                             
 MongoDB查询数据的语法格式如下：                    
 db.collection.find(query, projection)                                  
 query:可选，使用查询操作符指定查询条件                 
 projection:可选，使用投影操作符指定返回的键。查询时返回文档中所有键值， 只需省略该参数即可（默认省略）。             
 如果需要以易读的方式来读取数据，可以使用pretty()方法，语法格式如下：         
 db.col.find().pretty()                 
 pretty()方法以格式化的方式来显示所有文档。                      
>                       
>MongoDB AND条件                  
 MongoDB的find()方法可以传入多个键(key)，每个键(key)以逗号隔开，即常规SQL的AND条件。               
 语法格式如下：                        
 db.col.find({key1:value1, key2:value2}).pretty()                       
>               
>MongoDB OR条件                       
 MongoDB OR条件语句使用了关键字 $or,语法格式如下：                   
 db.col.find(               
    {               
       $or: [               
          {key1: value1}, {key2:value2}             
       ]                
    }               
 ).pretty()                                               
## 8.删除文档:                  
>remove()函数是用来移除集合中的数据。                 
 在执行remove()函数前先执行find()命令来判断执行的条件是否正确，这是一个比较好的习惯。          
 remove()方法的基本语法格式如下所示：             
 db.collection.remove(              
    <query>,            
    {           
      justOne: <boolean>,           
      writeConcern: <document>          
    }           
 )              
 参数说明：              
 query:（可选）删除的文档的条件。            
 justOne:（可选）如果设为true或1，则只删除一个文档，如果不设置该参数，或使用默认值false，则删除所有匹配条件的文档。                 
 writeConcern:（可选）抛出异常的级别。              
>                   
## 9.条件操作符:                                 
>$gt  greater than  >                   
 $gte  gt equal  >=                     
 $lt  less than  <                      
 $lte  lt equal  <=                     
 $ne  not equal  !=                     
 $eq    equal  = 
>$type操作符是基于BSON类型来检索集合中匹配的数据类型，并返回结果。                             
## 10.分页查询:                  
>limit()方法接受一个数字参数，该参数指定从MongoDB中读取的记录条数。                   
>skip()方法接受一个数字参数作为跳过的记录条数。                     
>db.COLLECTION_NAME.find().limit(NUMBER).skip(NUMBER)               
>                   
## 11.排序:                
>在MongoDB中使用sort()方法对数据进行排序，sort()方法可以通过参数指定排序的字段，并使用1和-1来指定排序的方式，其中1为升序排列，而-1是用于降序排列。                                   
## 12.索引:                        
>索引通常能够极大的提高查询的效率，如果没有索引，MongoDB在读取数据时必须扫描集合中的每个文件并选取那些符合查询条件的记录。这种扫描全集合的查询效率是非常低的。             
 索引是特殊的数据结构，索引存储在一个易于遍历读取的数据集合中，索引是对数据库表中一列或多列的值进行排序的一种结构。                  
 MongoDB使用createIndex()方法来创建索引。             
 createIndex()方法基本语法格式如下所示：                 
 db.collection.createIndex(keys, options)                   
 语法中Key值为要创建的索引字段，1为指定按升序创建索引，-1为指定按降序创建索引。                                                      
## 12.聚合:                    
>MongoDB中聚合(aggregate)主要用于处理数据(诸如统计平均值，求和等)，并返回计算后的数据结果。有点类似SQL语句中的count(*)。                    
 aggregate()方法的基本语法格式如下所示：              
 db.COLLECTION_NAME.aggregate(AGGREGATE_OPERATION)                  
>                                          
>MongoDB的聚合管道将MongoDB文档在一个管道处理完毕后将结果传递给下一个管道处理。管道操作是可以重复的。              
 聚合框架中常用的几个操作：                  
 $project：修改输入文档的结构。可以用来重命名、增加或删除域，也可以用于创建计算结果以及嵌套文档。               
 $match：用于过滤数据，只输出符合条件的文档。$match使用MongoDB的标准查询操作。                   
 $limit：用来限制MongoDB聚合管道返回的文档数。                  
 $skip：在聚合管道中跳过指定数量的文档，并返回余下的文档。                    
 $unwind：将文档中的某一个数组类型字段拆分成多条，每条包含数组中的一个值。                   
 $group：将集合中的文档分组，可用于统计结果。                          
 $sort：将输入文档排序后输出。                      
 $geoNear：输出接近某一地理位置的有序文档。                      
## 13.复制(副本集):
>MongoDB复制是将数据同步在多个服务器的过程。                      
 复制提供了数据的冗余备份,并在多个服务器上存储数据副本,提高了数据的可用性,并可以保证数据的安全性。                 
 复制还允许从硬件故障和服务中断中恢复数据。                  
 MongoDB中的复制集是一组维护相同数据集的mongod进程。副本集提供冗余性及和高可用，是所有生产部署的基础。复制集有多台MongoDB组成的一个集群，
>集群中有一个主节点(Primary)和N个副本节点(Secondary)等，它们有相同的数据库，假如主MongoDB服务器或者MongoDB实例宕机，其它的副本服务器可
>以继续提供服务，实现数据的高可用及可靠性。                          
>![mongo2](http://github.com/xidianlina/practice/raw/master//mongo_practice/picture/mongo2.png)                             
>a.所有的写操作或者更改操作都只能从主节点Primary中操作(复制集内的所有成员都可以接收读操作，但是，默认情况下，应用程序将其读操作指向主成员)，
>主节点上所有的更改及写操作都会记录到oplog日志中。                        
 b.从节点Secondary复制主节点Primary的oplog日志，通过异步的方式去执行oplog日志中的记录来和主节点达到数据一致性。              
 c.oplog作用主要是记录主节点的写入操作，充当复制源。                      
>d.如果主节点无故宕机，复制集集群会通过投票机制在从节点中选举一台升级为主节点。               
## 14.分片
>当MongoDB存储海量的数据时，一台机器可能不足以存储数据，也可能不足以提供可接受的读写吞吐量。这时可以通过在多台机器上分割数据，使得数据库系统能存储和处理更多的数据。              
>![mongo3](http://github.com/xidianlina/practice/raw/master//mongo_practice/picture/mongo3.png)                         
>mongos就是一个路由服务器，它会根据管理员设置的“片键”将数据分摊到自己管理的mongod集群，数据和片的对应关系以及相应的配置信息保存在"config服务器"上。                       
## 15.关系
>MongoDB的关系表示多个文档之间在逻辑上的相互联系。               
 文档间可以通过嵌入和引用来建立联系。             
 MongoDB中的关系可以是：                    
 1:1 (1对1)                      
 1: N (1对多)                 
 N: 1 (多对1)                 
 N: N (多对多)             
## 16.MongoDB数据库引用
>MongoDB引用有两种：                  
 手动引用（Manual References）                        
 DBRefs                     
 在不同的集合中(address_home, address_office, address_mailing等)存储不同的地址（住址，办公室地址，邮件地址等）。
>在调用不同地址时，也需要指定集合，一个文档从多个集合引用文档，应该使用DBRefs。                             
 使用DBRef的形式：                    
 { $ref : , $id : , $db :  }                    
 三个字段表示的意义为：                        
 $ref：集合名称                      
 $id：引用的id                  
 $db:数据库名称，可选参数 
## 17.覆盖索引查询
>覆盖查询是以下的查询：                
 所有的查询字段是索引的一部分                 
 所有的查询返回字段在同一个索引中               
 由于所有出现在查询中的字段是索引的一部分， MongoDB无需在整个数据文档中检索匹配查询条件和返回使用相同索引的查询结果。                         
 因为索引存在于RAM中，从索引中获取数据比通过扫描文档读取数据要快得多。           
## 18.查询分析
>MongoDB查询分析可以确保所建立的索引是否有效，是查询语句性能分析的重要工具。              
>使用explain()操作提供了查询信息，使用索引及查询统计等。有利于对索引的优化。                 
>使用hint()来强制MongoDB使用一个指定的索引。           
## 19.原子操作
>mongodb不支持事务，所以在项目中应用时，无论什么设计，都不要要求mongodb保证数据的完整性。            
 但是mongodb提供了许多原子操作，比如文档的保存，修改，删除等，都是原子操作。              
 所谓原子操作就是要么这个文档保存到Mongodb，要么没有保存到Mongodb，不会出现查询到的文档没有保存完整的情况。           
>                       
>原子操作常用命令:              
 $set                   
 用来指定一个键并更新键值，若键不存在并创建。                     
 { $set : { field : value } }                       
>                           
>$unset                 
 用来删除一个键。                       
 { $unset : { field : 1} }                          
>                           
>$inc                               
 $inc可以对文档的某个值为数字型（只能为满足要求的数字）的键进行增减的操作。                        
 { $inc : { field : value } }                               
>                           
>$push                          
 用法：                            
 { $push : { field : value } }                  
 把value追加到field里面去，field一定要是数组类型才行，如果field不存在，会新增一个数组类型加进去。                         
>                   
>$pushAll                           
 同$push,只是一次可以追加多个值到一个数组字段内。                        
 { $pushAll : { field : value_array } }                             
>                       
>$pull                      
 从数组field内删除一个等于value值。                     
 { $pull : { field : _value } }                             
>                           
>$addToSet                                  
 增加一个值到数组内，而且只有当这个值不在数组内才增加。                                
>                       
>$pop                       
 删除数组的第一个或最后一个元素                            
 { $pop : { field : 1 } }                               
>                       
>$rename                        
 修改字段名称                                 
 { $rename : { old_field_name : new_field_name } }                                      
>                       
>$bit                           
 位操作，integer类型                                  
 {$bit : { field : {and : 5}}}                              
## 20.高级索引
>索引数组字段                         
 在数组中创建索引，需要对数组中的每个字段依次建立索引。                        
 索引子文档字段                        
 假设需要通过city、state、pincode字段来检索文档，由于这些字段是子文档的字段，所以需要对子文档建立索引。                
 为子文档的三个字段创建索引，命令如下：                                
 db.users.ensureIndex({"address.city":1,"address.state":1,"address.pincode":1})                     
## 21.索引限制
>每个索引占据一定的存储空间，在进行插入，更新和删除操作时也需要对索引进行操作。所以，如果很少对集合进行读取操作，建议不使用索引。               
 由于索引是存储在内存(RAM)中,应该确保该索引的大小不超过内存的限制。如果索引的大小大于内存的限制，MongoDB会删除一些索引，这将导致性能下降。                
>               
>查询限制：                      
 索引不能被以下的查询使用：                      
 正则表达式及非操作符，如 $nin, $not, 等。                        
 算术运算符，如 $mod, 等。                   
 $where 子句                          
 检测语句是否使用索引是一个好的习惯，可以用explain来查看。                           
>                           
>最大范围：                      
 集合中索引不能超过64个                       
 索引名的长度不能超过128个字符                       
 一个复合索引最多可以有31个字段               
## 22.ObjectId
>ObjectId是一个12字节BSON类型数据，有以下格式：             
 前4个字节表示时间戳             
 接下来的3个字节是机器标识码         
 紧接的两个字节由进程id组成（PID）            
 最后三个字节是随机数。                        
 MongoDB中存储的文档必须有一个"_id"键。这个键的值可以是任何类型的，默认是个ObjectId对象。                     
 在一个集合里面，每个文档都有唯一的"_id"值，来确保集合里面每个文档都能被唯一标识。                
 MongoDB采用ObjectId，而不是其他比较常规的做法（比如自动增加的主键）的主要原因，因为在多个服务器上同步自动增加主键值既费力还费时。               
## 23.Map-Reduce
>Map-Reduce是一种计算模型，简单的说就是将大批量的工作（数据）分解（MAP）执行，然后再将结果合并成最终结果（REDUCE）。
>类似于关系型数据库中的group by，主要用于统计数据之用。                        
 MapReduce的基本语法：                            
 db.collection.mapReduce(                   
    function() {emit(key,value);},  //map 函数                    
    function(key,values) {return reduceFunction},   //reduce 函数                 
    {               
       out: collection,                 
       query: document,                         
       sort: document,                          
       limit: number                    
    }               
 )              
 参数说明:              
 map:映射函数 (生成键值对序列,作为 reduce 函数参数)。必须调用 emit(key, value) 返回键值对。             
 reduce:统计函数，reduce函数的任务就是将key-values变成key-value，也就是把values数组变成一个单一的值value。             
 out:统计结果存放集合 (不指定则使用临时集合,在客户端断开后自动删除)。             
 query:一个筛选条件，只有满足条件的文档才会调用map函数。（query。limit，sort可以随意组合）                   
 sort:和limit结合的sort排序参数（也是在发往map函数前给文档排序），可以优化分组机制              
 limit:发往map函数的文档数量的上限（要是没有limit，单独使用sort的用处不大）         
>![mongo4](http://github.com/xidianlina/practice/raw/master//mongo_practice/picture/mongo4.png)                                     
         
# 问题列表
# 问题答案
## 1.
## 2.
## 3.
## 4.
## 5.
## 6.
## 7.
## 8.
## 9.
## 10.


参考文档:       
https://docs.mongoing.com/      
https://docs.mongoing.com/mongodb-crud-operations       
https://www.jinmuinfo.com/community/MongoDB/docs/04-crud/01-insert-documents/01-insert-methods.html     
https://docs.mongodb.com/manual/reference/method/db.collection.countDocuments/      