### 6.MVCC
> READ UNCOMMITTED隔离级别下, 读不会加任何锁。而写会加排他锁，并到事务结束之后释放。                
  READ COMMITTED隔离级别下,写数据是使用排他锁, 读取数据不加锁而是使用了MVCC机制, 这样大大提高并发读写效率, 写不影响读, 因为读并未加锁, 读的是记录的镜像版本。                  
  MySQL在事务隔离级别Read committed 、Repeatable Read下，InnoDB存储引擎采用非锁定的一致性读。即读取数据不用加锁，即采用的是MVCC中一致性非锁定读模式, InnoDB的做法是: 读不影响写，写不影响读。                 
  读不影响写: 当数据正在执行读操作时，其他事务的写操作不会因此去等待当前事务行上S锁的释放，而是会去读取行的一个快照数据。                     
  写不影响读：当数据正在执行写操作时，其他事务的读操作不会因此去等待当前事务行上X锁的释放，而是会去读取行的一个快照数据。                                
>           
> MVCC多版本并发控制，是一种并发控制的方法，一般在数据库管理系统中实现对数据库的并发访问。            
> MVCC不用加任何锁，通过一定机制生成一个数据请求时间点的一致性数据快照（Snapshot)，并用这个快照来提供一定级别（语句级或事务级）的一致性读取。
> 从用户的角度来看，好像是数据库可以提供同一数据的多个版本，因此，这种技术叫做数据多版本并发控制。               
>                               
> 最早的数据库系统，只有读读之间可以并发，读写，写读，写写都要阻塞。引入多版本之后，只有写写之间相互阻塞，其他三种操作都可以并行，这样大幅度提高了InnoDB的并发度。               
  MVCC读取数据时通过一种类似快照的方式将数据保存下来，这样读锁就和写锁不冲突了，不同的事务session会看到自己特定版本的数据。当然快照是一种概念模型，不同的数据库可能用不同的方式来实现这种功能。              
  MVCC只在READ COMMITTED和REPEATABLE READ两个隔离级别下工作。其他两个隔离级别够和MVCC不兼容, 因为READ UNCOMMITTED总是读取最新的数据行,
> 而不是符合当前事务版本的数据行。而SERIALIZABLE READ则会对所有读取的行都加锁。                
>               
> MVCC多版本并发控制是通过undo log和read view实现的。              
  当数据库对数据做修改时，需要把数据页从磁盘读到buffer pool中，然后在buffer pool中进行修改。
> 这时buffer pool中的数据页就与磁盘上的数据页内容不一致，称buffer pool的数据页为脏页(dirty page)。也就是先拷贝一份数据，对拷贝的数据进行修改，修改完毕后再覆盖到原数据。                  
>                                            
> 当进行数据修改时会记录redo log和undo log。             
> redo log记录了数据操作在物理层面的修改，事务的持久性是通过redo log实现的。在事务提交前，只要将RedoLog持久化即可，不需要将数据持久化。
> 当系统崩溃时，虽然数据没有持久化，但是RedoLog已经持久化。系统可以根据Redo Log的内容，将所有数据恢复到最新的状态。                                                                     
> undo Log的原理很简单，为了满足事务的原子性和一致性 ，在操作任何数据之前，首先将数据备份到一个地方（这个存储数据备份的地方称为Undo Log）。
> 然后进行数据的修改。如果出现了错误或者用户执行了roll back语句，系统可以利用Undo Log中的备份将数据恢复到事务开始之前的状态。                    
> undo log用于数据的撤回操作，它记录了修改的反向操作，比如，插入对应删除，修改对应修改为原来的数据，通过undo log可以实现事务回滚，并且可以根据undo log回溯到某个特定的版本的数据，实现MVCC。                       
>               
> innodb引擎中通过B+树作为索引的数据结构，并且主键所在的索引为ClusterIndex(聚簇索引), ClusterIndex中的叶子节点中保存了对应的数据内容。
> 一个表只能有一个主键，所以只能有一个聚簇索引，如果表没有定义主键，则选择第一个非NULL唯一索引作为聚簇索引，如果没有非NULL唯一索引则生成一个隐藏id列作为聚簇索引。                 
  除了Cluster Index外的索引是Secondary Index(辅助索引)。辅助索引中的叶子节点保存的是聚簇索引的叶子节点的值。              
  InnoDB行记录中除保存rowid外，还有trx_id和db_roll_ptr, trx_id表示最近修改的事务的id,db_roll_ptr指向undo segment中的undo log。             
  新增一个事务时事务id会增加，trx_id能够表示事务开始的先后顺序。               
  undo log分为Insert和Update两种，delete可以看做是一种特殊的update，即在记录上修改删除标记。                 
  update undo log记录了数据之前的数据信息，通过这些信息可以原到之前版本的状态。            
  当进行插入操作时，生成的Insert undo log在事务提交后即可删除，因为其他事务不需要这个undo log。                
  进行删除修改操作时，会生成对应的undo log，并将当前数据记录中的db_roll_ptr指向新的undo log。  
>               
> 事务的机制是通过视图（read-view）来实现的并发版本控制（MVCC），不同的事务隔离级别创建读视图的时间点不同。       
> 读未提交是不创建，直接返回记录上的最新值。                            
  读已提交是每条SQL创建读视图，在每个SQL语句开始执行的时候创建的。隔离作用域仅限该条SQL语句。            
> 可重复读是每个事务重建读视图，整个事务存在期间都用这个视图。                               
> 串行化隔离级别下直接用加锁的方式来避免并行访问。                                  
  视图可以理解为数据副本，每次创建视图时，将当前已持久化的数据创建副本，后续直接从副本读取，从而达到数据隔离效果。                   
>               
> ReadView中主要就是有个列表来存储系统中当前活跃着的读写事务，也就是begin了还未提交的事务。通过这个列表来判断记录的某个版本是否对当前事务可见。             
  其中最主要的与可见性相关的属性如下：                
  up_limit_id：当前已经提交的事务号+1，事务号<up_limit_id ，对于当前Read View都是可见的。理解起来就是创建Read View视图的时候，之前已经提交的事务对于该事务肯定是可见的。             
  low_limit_id：当前最大的事务号+1，事务号>=low_limit_id，对于当前Read View都是不可见的。理解起来就是在创建Read View视图之后创建的事务对于该事务肯定是不可见的。                
  trx_ids：为活跃事务id列表，即Read View初始化时当前未提交的事务列表。所以当进行RR读的时候，trx_ids中的事务对于本事务是不可见的（除了自身事务，自身事务对于表的修改对于自己当然是可见的）。理解起来就是创建RV时，将当前活跃事务ID记录下来，后续即使他们提交对于本事务也是不可见的。                                          
>                       
> 参考 https://segmentfault.com/a/1190000012655091            
> https://segmentfault.com/a/1190000012659380               
> https://segmentfault.com/a/1190000018652117                   
> https://segmentfault.com/a/1190000021001384               
> https://segmentfault.com/a/1190000012650596     
