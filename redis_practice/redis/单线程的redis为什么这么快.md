## 18.单线程的redis为什么这么快
>Redis到底有多快:                            
>redis性能卓越,作为key-value系统最大负载数量级为10W/s,set和get耗时数量级为10ms和5ms。使用流水线的方式可以提升redis操作的性能。                     
>                                   
>Redis为什么那么快:                                      
>内存存储：Redis是使用内存(in-memeroy)存储,没有磁盘IO上的开销                                  
 单线程实现：Redis使用单个线程处理请求，避免了多个线程之间线程切换和锁资源争用的开销                                  
 非阻塞IO：Redis使用多路复用IO技术，在poll，epool，kqueue选择最优IO实现                             
 优化的数据结构：Redis有诸多可以直接应用的优化数据结构的实现，应用层可以直接使用原生的数据结构提升性能                                        
>参考 https://segmentfault.com/a/1190000022088928             
