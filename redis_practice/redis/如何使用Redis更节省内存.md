## 31.如何使用Redis更节省内存？
>(1).控制key的长度                   
 (2).避免存储bigkey                 
 (3).选择合适的数据类型              
 (4).把Redis当作缓存使用                   
 把Redis它当做缓存来使用，而不是数据库。应用写入到 Redis中的数据，尽可能地都设置过期时间                      
 (5).实例设置maxmemory + 淘汰策略               
 (6).数据压缩后写入Redis                               
 在业务应用中先将数据压缩，再写入到Redis中(例如采用 snappy、gzip 等压缩算法)。                          
 当然，压缩存储的数据，客户端在读取时还需要解压缩，在这期间会消耗更多CPU资源，你需要根据实际情况进行权衡。
