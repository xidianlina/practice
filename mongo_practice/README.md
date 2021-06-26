mongoDb
======
        
# 问题列表
## 1.什么是MongoDB？
## 2.MongoDB的优势有哪些？
## 3.MongoDB 支持哪些数据类型?
## 4.什么是集合Collection、文档Document,以及与关系型数据库术语类比?
## 5.MySQL和mongodb的区别?
## 6.mongodb和redis区别以及选择原因?
## 7.更新操作会立刻fsync到磁盘?
## 8.索引类型有哪些？
## 9.MongoDB在A:{B,C}上建立索引，查询A:{B,C}和A:{C,B}都会使用索引吗？
## 10.什么是聚合
## 11.monogodb中的分片sharding
## 12.数据在什么时候才会扩展到多个分片(Shard)里?
## 13.更新一个正在被迁移的块（Chunk）上的文档时会发生什么？
## 14.如果块移动操作(moveChunk)失败了，需要手动清除部分转移的文档吗?可以把movechunk目录里的旧文件删除吗?
## 15.如果一个分片（Shard）停止或很慢的时候，发起一个查询会怎样？
## 16.MongoDB副本集实现高可用的原理
## 17.什么是master或primary？什么是Slave或Secondary？什么是Arbiter？
## 18.复制集节点类型有哪些？
## 19.启用备份故障恢复需要多久?
## 20.raft选举过程，投票规则？
## 21.在哪些场景使用MongoDB?
## 22.应该启动一个集群分片(sharded)还是一个非集群分片的mongodb环境?
## 23.MongoDB中的命名空间是什么意思?
## 24.为什么要在MongoDB中使用分析器
## 25.MongoDB支持存储过程吗？如果支持的话，怎么用？
## 26.如何理解MongoDB中的GridFS机制，MongoDB为何使用GridFS来存储文件？
## 27.为什么MongoDB的数据文件很大？
## 28.MongoDB允许添加空值null吗?

# 问题答案
## 1.什么是MongoDB？
>MongoDB是一个文档数据库，提供好的性能，领先的非关系型数据库。采用BSON存储文档数据。                    
 BSON是一种类json的一种二进制形式的存储格式，简称Binary JSON。相对于json多了date类型和二进制数组。                 
## 2.MongoDB的优势有哪些？
>a.面向集合(Collection)和文档(document)的存储，以JSON格式的文档保存数据                                   
 b.高性能，支持Document中嵌入Document减少了数据库系统上的I/O操作以及具有完整的索引支持，支持快速查询                   
 c.高效的传统存储方式：支持二进制数据及大型对象                   
 d.高可用性，数据复制集，MongoDB 数据库支持服务器之间的数据复制来提供自动故障转移（automatic failover）                  
 e.高可扩展性，分片(sharding)将数据分布在多个数据中心,MongoDB支持基于分片键创建数据区域                      
 f.丰富的查询功能, 聚合管道(Aggregation Pipeline)、全文搜索(Text Search)以及地理空间查询(Geospatial Queries)                    
 g.支持多个存储引擎,WiredTiger存储引、In-Memory存储引擎                                             
## 3.MongoDB 支持哪些数据类型?
>![mongo5](http://github.com/xidianlina/practice/raw/master//mongo_practice/picture/mongo5.png)                                                
## 4.什么是集合Collection、文档Document,以及与关系型数据库术语类比?
>集合Collection位于单独的一个数据库MongoDB文档Document集合，它类似关系型数据库（RDBMS）中的表Table。
>一个集合Collection内的多个文档Document可以有多个不同的字段。通常情况下，集合Collection中的文档Document有着相同含义。                   
 文档Document由key-value构成。文档Document是动态模式,这说明同一集合里的文档不需要有相同的字段和结构。类似于关系型数据库中table中的每一条记录。                 
 与关系型数据库术语类比        
>![mongo6](http://github.com/xidianlina/practice/raw/master//mongo_practice/picture/mongo6.png)                                                                                              
## 5.MySQL和mongodb的区别?
>![mongo7](http://github.com/xidianlina/practice/raw/master//mongo_practice/picture/mongo7.png)                                                                                           
## 6.mongodb和redis区别以及选择原因?
>![mongo8](http://github.com/xidianlina/practice/raw/master//mongo_practice/picture/mongo8.png)         
>为什么用MOngoDB？           
>架构简单                   
 没有复杂的连接                
 深度查询能力,MongoDB支持动态查询               
 容易调试                   
 容易扩展           
 不需要转化/映射应用对象到数据库对象                  
 使用内部内存作为存储工作区,以便更快的存取数据                                                                                                                               
## 7.更新操作会立刻fsync到磁盘?
>不会,磁盘写操作默认是延迟执行的.写操作可能在两三秒(默认在60秒内)后到达磁盘，通过 syncPeriodSecs 启动参数，可以进行配置。            
>例如,如果一秒内数据库收到一千个对一个对象递增的操作,仅刷新磁盘一次。            
## 8.索引类型有哪些？
>单字段索引(Single Field Indexes)                
 复合索引(Compound Indexes)             
 多键索引(Multikey Indexes)             
 全文索引(text Indexes)             
 Hash 索引(Hash Indexes)              
 通配符索引(Wildcard Index)              
 2dsphere索引(2dsphere Indexes)                                 
## 9.MongoDB在A:{B,C}上建立索引，查询A:{B,C}和A:{C,B}都会使用索引吗？
>由于MongoDB索引使用B-tree树原理，只会在A:{B,C}上使用索引             
## 10.什么是聚合
>聚合操作能够处理数据记录并返回计算结果。聚合操作能将多个文档中的值组合起来，对成组数据执行各种操作，返回单一的结果。
>它相当于SQL中的count(*)组合group by。对于MongoDB中的聚合操作，应该使用aggregate()方法。  
> 参考 https://juejin.cn/post/6844903903000002574                   
## 11.monogodb中的分片sharding
>分片sharding是将数据水平切分到不同的物理节点。当应用数据越来越大的时候，数据量也会越来越大。当数据量增长时，
>单台机器有可能无法存储数据或可接受的读取写入吞吐量。利用分片技术可以添加更多的机器来应对数据量增加以及读写操作的要求。 
## 12.数据在什么时候才会扩展到多个分片(Shard)里?
>MongoDB 分片是基于区域(range)的。所以一个集合(collection)中的所有的对象都被存放到一个块(chunk)中,默认块的大小是 64Mb。
>当数据容量超过64 Mb，才有可能实施一个迁移，只有当存在不止一个块的时候，才会有多个分片获取数据的选项。      
## 13.更新一个正在被迁移的块（Chunk）上的文档时会发生什么？
>更新操作会立即发生在旧的块（Chunk）上，然后更改才会在所有权转移前复制到新的分片上。
## 14.如果块移动操作(moveChunk)失败了，需要手动清除部分转移的文档吗?可以把movechunk目录里的旧文件删除吗?
>不需要，移动操作是一致(consistent)并且是确定性的(deterministic)。                             
>一次失败后，移动操作会不断重试。当完成后，数据只会出现在新的分片里(shard)           
>               
>movechunk目录里的旧文件是在分片(shard)进行均衡操作(balancing)的时候产生的临时文件。一旦这些操作已经完成,相关的临时文件也应该被删除掉。
>但目前清理工作是需要手动的,所以请小心地考虑再释放这些文件的空间。           
## 15.如果一个分片（Shard）停止或很慢的时候，发起一个查询会怎样？
>如果一个分片停止了，除非查询设置了 “Partial” 选项，否则查询会返回一个错误。如果一个分片响应很慢，MongoDB 会等待它的响应。
## 16.MongoDB副本集实现高可用的原理
>MongoDB 使用了其复制(Replica Set)方案，实现自动容错机制为高可用提供了基础。目前，MongoDB 支持两种复制模式：               
 Master/Slave ，主从复制，角色包括 Master 和 Slave 。                   
 Replica Set ，复制集复制，角色包括 Primary 和 Secondary 以及 Arbiter 。(生产环境必选)                                    
## 17.什么是master或primary？什么是Slave或Secondary？什么是Arbiter？
>副本集只能有一个主节点能够确认写入操作来接收所有写操作，并记录其操作日志中的数据集的所有更改(记录在oplog中)。
>在集群中，当主节点（master）失效，Secondary节点会变为master。                  
>复制主节点的oplog并将oplog记录的操作应用于其数据集，如果主节点宕机了，将从符合条件的从节点选举选出新的主节点。           
>仲裁节点不维护数据集。仲裁节点的目的是通过响应其他副本集节点的心跳和选举请求来维护副本集中的仲裁。                                             
## 18.复制集节点类型有哪些？
>优先级0型(Priority 0)节点                
 隐藏型(Hidden)节点              
 延迟型(Delayed)节点             
 投票型(Vote)节点以及不可投票节点                                               
## 19.启用备份故障恢复需要多久?
>从备份数据库声明主数据库宕机到选出一个备份数据库作为新的主数据库将花费10到30秒时间。这期间在主数据库上的操作将会失败，
>包括写入和强一致性读取(strong consistent read)操作。即使在这段时间里还能在第二数据库上执行最终一致性查询(eventually consistent query)(在slaveok模式下)。                 
## 20.raft选举过程，投票规则？
>选举过程：              
 当系统启动好之后，初始选举后系统由1个Leader和若干个Follower角色组成。然后突然由于某个异常原因，Leader服务出现了异常，导致Follower角色检测
>到和Leader的上次RPC更新时间超过给定阈值时间时。此时Follower会认为Leader服务已出现异常，然后它将会发起一次新的Leader选举行为，同时将自身的
>状态从Follower切换为Candidate身份。随后请求其它Follower投票选择自己。                            
 投票规则：                      
>当一个候选人获得了同一个任期号内的大多数选票，就成为领导人。                       
 每个节点最多在一个任期内投出一张选票。并且按照先来先服务的原则。                     
 一旦候选人赢得选举，立刻成为领导，并发送心跳维持权威，同时阻止新领导人的诞生。        
## 21.在哪些场景使用MongoDB?
>规则：                
>如果业务中存在大量复杂的事务逻辑操作，则不要用MongoDB数据库；                                            
>在处理非结构化/半结构化的大数据使用MongoDB，操作的数据类型为动态时也使用MongoDB，                       
>比如：内容管理系统，切面数据、日志记录                    
 移动端Apps：O2O送快递骑手、快递商家的信息（包含位置信息）               
 数据管理，监控数据  
## 22.应该启动一个集群分片(sharded)还是一个非集群分片的mongodb环境?
>数据量大用集群分片,数据量小用非集群
>为开发便捷起见,建议以非集群分片(unsharded)方式开始一个mongodb环境,除非一台服务器不足以存放你的初始数据集。
>从非集群分片升级到集群分片(sharding)是无缝的,所以在数据集还不是很大的时候没必要考虑集群分片(sharding)。
## 23.MongoDB中的命名空间是什么意思?
>mongodb存储bson对象在集合(collection)中，数据库名字和集合名字以句点连结起来叫做名字空间(namespace)。                           
## 24.为什么要在MongoDB中使用分析器
>mongodb中包括了一个可以显示数据库中每个操作性能特点的数据库分析器.通过这个分析器可以找到比预期慢的查询(或写操作),利用这一信息,可以确定是否需要添加索引或者修改索引。                            
## 25.MongoDB支持存储过程吗？如果支持的话，怎么用？
>MongoDB支持存储过程，它是javascript写的，保存在db.system.js表中。                              
## 26.如何理解MongoDB中的GridFS机制，MongoDB为何使用GridFS来存储文件？
>GridFS是一种将大型文件存储在MongoDB中的文件规范。使用GridFS可以将大文件分隔成多个小文档存放，这样能够有效的保存大文档，而且解决了BSON对象有限制的问题。                              
## 27.为什么MongoDB的数据文件很大？
>MongoDB采用的预分配空间的方式来防止文件碎片。                           
## 28.MongoDB允许添加空值null吗? 
>对于对象成员而言,允许添加空值null。然而用户不能够添加空值(null)到数据库集合(collection)，因为空值不是对象。但是用户能够添加空对象{}。

参考文档:       
https://docs.mongoing.com/      
https://docs.mongoing.com/mongodb-crud-operations       
https://www.jinmuinfo.com/community/MongoDB/docs/04-crud/01-insert-documents/01-insert-methods.html     
https://docs.mongodb.com/manual/reference/method/db.collection.countDocuments/     

# MongoDB知识点                       
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
## 5.插入文档:              
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
>![mongo_insert](http://github.com/xidianlina/practice/raw/master//mongo_practice/picture/mongo_insert.png)                         
## 6.更新文档:              
>MongoDB使用update()和save()方法来更新集合中的文档。                   
 update()方法用于更新已存在的文档。语法格式如下：                   
>![mongo_update](http://github.com/xidianlina/practice/raw/master//mongo_practice/picture/mongo_update.png)                                          
>                               
>save()方法通过传入的文档来替换已有文档，_id 主键存在就更新，不存在就插入。语法格式如下：              
>![mongo_save](http://github.com/xidianlina/practice/raw/master//mongo_practice/picture/mongo_save.png)                                                  
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
>![mongo_remove](http://github.com/xidianlina/practice/raw/master//mongo_practice/picture/mongo_remove.png)                                                         
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
  