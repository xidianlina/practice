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
##
           
                    
             
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