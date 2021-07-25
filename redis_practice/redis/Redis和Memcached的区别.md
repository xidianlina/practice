## 17.Redis和Memcached的区别？
>Redis和Memcache都是将数据存放在内存中，都是内存数据库。                             
 两者的主要区别:                       
 (1).支持的数据类型不同                  
 Redis支持的数据类型丰富,不仅支持简单的k/v类型的数据，同时还提供String，List,Set,Hash,Sorted Set,pub/sub,Transactions数据结构的存储。                       
 memcache支持简单数据类型，需要客户端自己处理复杂对象。不过memcache还可用于缓存图片、视频等其他东西。                         
 (2).对持久性的支持不同                  
 redis支持数据落地持久化存储,可以将内存中的数据保持在磁盘中，重启的时候可以再次加载进行使用。                  
 memcache不支持数据持久存储。                 
 (3).扩展性的实现方式(分布式存储)不同                  
 redis支持master-slave复制模式                        
 memcached本身并不支持分布式，只能在客户端通过像一致性哈希这样的分布式算法来实现memcached的分布式存储。               
 (4).性能不同               
 由于Redis只使用单核，而Memcached可以使用多核，所以平均每一个核上Redis在存储小数据时比Memcached性能更高。而在100k以上的数据中，
>Memcached性能要高于Redis，虽然Redis也在存储大数据的性能上进行了优化，但是比起Memcached，还是稍有逊色。                  
 (5).存储数据安全性不同                      
 redis中的数据可以定期保存到磁盘（持久化，redis挂掉后，数据可以通过aof、rdb恢复                        
 memcache挂掉后，数据没了，数据不可恢复                    
 (6).过期策略不同                     
 redis在set的时候可以不指定，然后通过expire设定，例如expire name 10                        
 memcache在set时就指定，例如set key1 0 0 8,即永不过期                        
