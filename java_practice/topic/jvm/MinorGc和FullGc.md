### 14.Minor GC和Full GC
>JVM的heap区域是分代的，分为年轻代和老年代，GC时新生代使用的是复制算法，老年代使用的是标记整理算法，新生代里有3个分区：Eden、To Survivor、From Survivor，默认占比是 8:1:1。                    
 JVM的堆对象分配的一般规则：                
 对象在新生代Eden区中分配。当Eden区没有足够的空间进行分配时，虚拟机将发起一次Minor GC。Java对象大多数都具有朝生夕灭的特性，多以Minor GC非常频繁，一般回收速度也比较快。              
 新生代GC是把Eden + From Survivor存活的对象放入To Survivor 区；                   
 清空Eden和From Survivor分区；                
 From Survivor和 To Survivor分区交换，From Survivor变To Survivor，To Survivor变From Survivor。                
 每次在From Survivor到To Survivor移动时都存活的对象年龄会+1，当年龄到达15（默认配置是15）时，升级为老年代。大对象也会直接进入老生代。                  
 当老年代空间占用到达某个值之后就会触发Full GC。Full GC的速度一般会比Minor GC慢10倍以上。                                  
 触发Full GC的情况主要有4种：                 
 (1).调用System.gc()方法。此方法的调用是建议JVM进行Full GC,虽然只是建议而非一定，但很多情况下它会触发 Full GC,从而增加Full GC的频率，
>也即增加了间歇性停顿的次数。建议能不使用此方法就别使用，让虚拟机自己去管理它的内存，可通过-XX:+ DisableExplicitGC来禁止RMI（Java远程方法调用）调用System.gc。                 
 (2).老年代空间不足。老年代空间只有在新生代对象转入及创建为大对象、大数组时才会出现不足的现象，当执行Full GC后空间仍然不足，
>则抛出错误：java.lang.OutOfMemoryError: Java heap space 。为避免以上两种状况引起的FullGC，调优时应尽量做到让对象在Minor GC阶段被回收、
>让对象在新生代多存活一段时间及不要创建过大的对象及数组。                       
 (3).在发生Minor GC时，虚拟机就会检测之前每次晋升到老年代的平均大小是否大于老年代的剩余空间，如果大于，则改为直接进行一次Full GC                  
 (4).在发生Minor GC时，如果允许分配担保，由Eden区、From Space区向To Space区复制时，对象大小大于To Space可用内存，则把该对象转存到老年代，且老年代的可用内存小于该对象大小。                 
>                   
>                        
> Minor GC和Full GC的区别                   
  新生代Minor GC:指发生在新生代的垃圾收集动作，因为Java对象大多数都具有朝生夕灭的特性，多以Minor GC非常频繁，一般回收速度也比较快。               
  老年代GC（Major GC/Full GC）:指发生在老年代的GC,出现了Major GC,经常都伴随着至少一次的Minor GC(但并非绝对的，在ParallelScavenge收集器的收集策略里就有直接进行Major GC的策略选择过程)。MajorGC的速度一般会比Minor GC慢10倍以上。                  
> 在运行通过-Xms20M、-Xmx20M和-Xmn10M这三个参数限制Java堆大小为20MB,切不可扩展，其中10MB分配给新生代剩下的10MB分配给老年代。-XX:SurvivorRatio=8决定了新生代中Eden区与一个Survivor区的空间比例是8比1，新生代总可用空间为9216KB（Eden区+1个Survivor区的总容量）。                            

