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
>创建数据库:         
>use DATABASE_NAME                      
 如果数据库不存在，则创建数据库，否则切换到指定数据库             
 刚创建的数据库并不在数据库的列表中， 要显示它，需要向数据库插入一些数据           
>           
>删除数据库:              
 db.dropDatabase()                                        
>删除当前数据库                                                
>                   
>创建集合:                                   
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
>                                                 
>>删除集合:                                                      
  db.collection.drop()          
>                       
>                                    
             
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