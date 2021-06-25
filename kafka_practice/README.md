kafka消息中间件
======

# 一.kafka中的基本概念
消息中间件的作用:解耦、削峰、异步。      
kafka是消息中间件的一种，是一种分布式流平台，是用于构建实时数据管道和流应用程序。
## 1.名词解释
### (1).生产者(producer):生产者用于发布(send)消息       
### (2).消费者(consumer):消费者用于订阅(subscribe)消息      
### (3).消息记录(record)        
由一个key，一个value和一个时间戳构成，消息最终存储在主题下的分区中, 记录在生产者中的称为生产者记录(ProducerRecord),
记录在消费者中的称为消费者记录(ConsumerRecord)，Kafka集群保持所有的消息，直到它们过期，无论消息是否被消费了，在一个可配置的时间段内，
Kafka集群保留所有发布的消息，不管这些消息有没有被消费。比如，如果消息的保存策略被设置为2天，那么在一个消息被发布的两天时间内，
它都是可以被消费的。之后它将被丢弃以释放空间。Kafka的性能是和数据量无关的常量级的，所以保留太多的数据并不是问题。     
### (4).消费者组(consumer group)        
相同的group.id的消费者将视为同一个消费者组, 每个消费者都需要设置一个组id, 每条消息只能被consumer group中的一个consumer消费,但可以被多个consumer group消费      
### (5).主题(topic)       
消息的一种逻辑分组，用于对消息分门别类，每一类消息称之为一个主题，相同主题的消息放在一个队列中          
### (6).分区(partition)       
消息的一种物理分组， 一个主题被拆成多个分区，每一个分区就是一个顺序的、不可变的消息队列，并且可以持续添加，分区中的每个消息都被分配了一个唯一的id，
称之为偏移量(offset),在每个分区中偏移量都是唯一的。每个分区对应一个逻辑log，有多个segment组成。       
### (7).偏移量(offset)     
分区中的每个消息都有一个唯一id，称之为偏移量，它代表已经消费的位置。可以自动或者手动提交偏移量(即自动或者手动控制一条消息是否已经被成功消费)        
### (8).消息代理(broker)        
一台kafka服务器称之为一个broker       
### (9).副本(replica)          
副本只是一个分区(partition)的备份。副本从不读取或写入数据。它们用于防止数据丢失。      
### (10).领导者(leader)              
Leader是负责给定分区的所有读取和写入的节点。每个分区都有一个服务器充当Leader，producer和consumer只跟leader交互        
### (11).追随者(follower)      
跟随领导者指令的节点被称为Follower。如果领导失败，一个追随者将自动成为新的领导者。 跟随者作为正常消费者，
拉取消息并更新其自己的数据存储。replica中的一个角色，从leader中复制数据。     
### (12).zookeeper      
Kafka消息代理是无状态的，所以他们使用ZooKeeper来维护它们的集群状态。ZooKeeper用于管理和协调Kafka代理。  
     
## 2.分区模型    
> Kafka集群由多个消息代理服务器（broker server）组成，发布至Kafka集群的每条消息都有一个类别，用主题（topic ）来表示。
不同应用产生不同类型的数据，可以设置不同的主题。一个主题一般会有多个消息的订阅者，当生产者发布消息到某个主题时，
订阅了这个主题的消费者都可以接收到生产者写人的新消息。     
> Kafka集群为每个主题维护了分布式的分区（partition）日志文件，物理意义上可以把主题看作分区的日志文件（partitioned log ）。
每个分区都是一个有序的、不可变的记录序列，新的消息会不断追加到提交日志（commit log ）。分区中的每条消息都会按照时间顺序分配到一
个单调递增的顺序编号， 叫作偏移量（offset），这个偏移量能够唯一地定位当前分区中的每一条消息。
  ![partition](http://github.com/xidianlina/practice/raw/master//kafka_practice/picture/partition.jpg)
> 上面的主题有3个分区，每个分区的偏移量都从0开始，不同分区之间的偏移量都是独立的，不会互相影响。发布到Kafka主题的每条消息包括键值和时间戳。
  消息到达服务端的指定分区后，都会分配到一个向增的偏移量。原始的消息内容和分配的偏移量以及其他一些数据信息最后都会存储到分区日志文件中。
  消息的键也可以不用设置，这种情况下消息会均衡地分布到不同的分区。      
> 如何保证消息的顺序消费？      
  传统消息系统在服务端保持消息的顺序， 如果有多个消费者消费同一个消息队列，服务端会以消息存储的顺序依次发送给消费者。但由于消息是异步发送给消费者的，
  消息到达消费者的顺序可能是无序的，这就意味着在并行消费时，传统消息系统无法很好地保证消息被顺序处理。虽然可以设置一个专用的消费者只消费一个队列，
  以此来解决消息顺序的问题，但是这就使得消费处理无法真正执行。        
  Kafka比传统消息系统有更强的顺序性保证，它使用主题的分区作为消息处理的并行单元。Kafka以分区作为最小的粒度，将每个分区分配给消费组中不同的而且是唯一的消费者，
  并确保一个分区只属于一个消费者， 即这个消费者就是这个分区的唯一读取线程。那么，只要分区的消息是有序的，消费者处理的消息顺序就有保证。每个主题有多个分区，
  不同的消费者处理不同的分区，所以Kafka不仅保证了消息的有序性，也做到了消费者的负载均衡。 
## 3.分布式模型
> Kafka每个主题的多个分区日志分布式地存储在Kafka集群上，同时为了故障容错，每个分区都会以副本的方式复制到多个消息代理节点上。
  其中一个节点会作为主副本（ Leader ），其他节点作为备份副本（ Follower ，也叫作从副本）。主副本会负责所有的客户端读写操作，
  备份副本仅仅从主副本同步数据。当主副本出现故障时，备份副本中的一个副本会被选择为新的主副本。因为每个分区的副本中只有主副本接
  受读写，所以每个服务端都会作为某些分区的主副本，以及另外一些分区的备份副本，这样Kafka集群的所有服务端整体上对客户端是负载均衡的。       
  Kafka的生产者和消费者相对于服务端而言都是客户端，生产者客户端发布消息到服务端的指定主题，会指定消息所属的分区。生产者发布消息时
  根据消息是否有键，采用不同的分区策略。消息没有键时，通过轮询方式进行客户端负载均衡。消息有键时，根据分区语义确保相同键的消息总是发送到同一个分区。     
  Kafka的消费者通过订阅主题来消费消息并且每个消费者都会设置一个消费组名称。因为生产者发布到主题的每一条消息都只会发送给消费组的一个消费者。
  所以，如果要实现传统消息系统的“队列”模型，可以让每个消费者都拥有相同的消费组名称，这样消息就会负载均衡到所有的消费者；如果要实现“发布－订阅”模型，
  则每个消费者的消费组名称都不相同，这样每条消息就会广播给所有的消费者。 
  ![product_consume](http://github.com/xidianlina/practice/raw/master//kafka_practice/picture/product_consume.jpg)
> 分区是消费者线程模型的最小并行单位。如上图，生产者发布消息到一台服务器的3个分区时， 图（左）只有一个消费者消费所有的3个分区。
  3个分区分布在3台服务器上，同时有3个消费者分别消费不同的分区。假设每个服务器的吞吐量是300MB ，在图中分摊到每个分区只有100MB，
  而在图（右）中集群整体的吞吐量有900MB 。可以看到，增加服务器节点会提升集群的性能，增加消费者数量会提升处理性能。       
  同一个消费组下多个消费者互相协调消费工作， Kafka会将所有的分区平均地分配给所有的消费者实例，这样每个消费者都可以分配到数量均等的分区。
  Kafka的消费组管理协议会动态地维护消费组的成员列表，当一个新消费者加入消费组，或者有消费者离开消费组，都会触发再平衡操作。       
  Kafka的消费者消费消息时，只保证在一个分区内消息的完全有序性，并不保证同一个主题中多个分区的消息顺序。消费者读取一个分区消
  息的顺序和生产者写入到这个分区的顺序是一致的。如果业务上需要保证所有消息完全一致，只能通过设置一个分区完成，但这种做法的缺点
  是最多只能有一个消费者进行消费。一般来说，只需要保证每个分区的有序性，再对消息加上键来保证相同键的所有消息落入同一个分区，就可以满足绝大多数的应用。             
## 4.消费模型
> 消息由生产者发布到Kafka集群后，会被消费者消费。消息的消费模型有两种：推送模型（push )和拉取模型（pull）。基于推送模型的消息系统，由消息代理
  记录消费者的消费状态。消息代理在将消息推送到消费者后，标记这条消息为已消费，但这种方式无法很好地保证消息的处理语义。比如，消息代理把消息发送出
  去后，当消费进程挂掉或者由于网络原因没有收到这条消息时，就有可能造成消息丢失（因为消息代理已经把这条消息标记为己消费了，但实际上这条消息并没有
  被实际处理）。如果要保证消息的处理语义，消息代理发送完消息后，要设置状态为“已发送”，只有收到消费者的确认请求后才更新为“已消费”，这就需要在消息
  代理中记录所有消息的消费状态，这种做法也是不可取的。        
  Kafka采用拉取模型，由消费者自己记录消费状态，每个消费者互相独立地顺序读取每个分区的消息。消费者拉取的最大上限通过最高水位（watermark）控制，
  生产者最新写入的消息如果还没有达到备份数量，对消费者是不可见的。这种由消费者控制偏移量的优点是：消费者可以按照任意的顺序消费消息。比如，消费者
  可以重置到旧的偏移量，重新处理之前已经消费过的消息；或者直接跳到最近的位置，从当前时刻开始消费。      
  在一些消息系统中，消息代理会在消息被消费之后立即删除消息。如果有不同类型的消费者订阅同一个主题，消息代理可能需要冗余地存储同一条消息；
  或者等所有消费者都消费完才删除，这就需要消息代理跟踪每个消费者的消费状态，这种设计很大程度上限制了消息系统的整体吞吐量和处理延迟。Kafka
  的做法是生产者发布的所有消息会一直保存在Kafka集群中，不管消息有没有被消费。用户可以通过设置保留时间来清理过期的数据，那么在消息发布之后，
  它可以被不同的消费者消费，消息过期之后就会自动清理掉。
## 5.特点
### (1).具备数据注入功能
类似消息系统，提供事件流的发布和订阅。         
消息系统也叫作消息队列，主要有两种消息模型：队列和发布订阅。Kafka使用消费组（consumer group）统一了这两种消息模型。Kafka使用队列模型时，
它可以将处理工作平均分配给消费组中的消费者成员,consumers可以同时从服务端读取消息，每个消息只被其中一个consumer读到；使用发布订阅模式时，
它可以将消息广播给多个消费组。采用多个消费组结合多个消费者，既可以线性扩
展消息的处理能力，也允许消息被多个消费组订阅。
队列模式也叫作点对点模式。多个消费者读取消息队列，每条消息只发送给一个消费者。
发布－订阅模式（pub/sub）。多个消费者订阅主题，主题的每条记录会发布给所有的消费者。       
如果所有的consumer都在一个组中，这就成为了传统的队列模式，在各consumer中实现负载均衡。如果所有的consumer都不在不同的组中，这就成为了发布-订阅模式，所有的消息都被分发到所有的consumer中。
### (2).具备数据存储功能
存储事件流数据的节点具有故障容错的特点。
任何消息队列要做到“发布消息”和“消费消息”的解耦合,实际上都要扮演一个存储系统的角色，负责保存还没有被消费的消息。否则，如果消息只是在内存中，
一旦机器宕机或进程重启，内存中的消息就会全部丢失。Kafka也不例外，数据写入至Kafka集群的服务器节点时，还会复制多份来保证出现故障时仍能可用。
为了保证消息的可靠存储， Kafka还允许生产者的生产请求在收到应答结果之前，阻塞式地等待一条消息，直到它完全地复制到多个节点上，才认为这条消息写入成功。
### (3).具备流处理功能
能够对实时的事件流进行流式地处理和分析。
流式数据平台仅仅有消息的读取和写入、存储消息流是不够的，还需要有实时的流式数据处理能力。对于简单的处理，可以直接使用Kafka的生产者和消费者API来完成；
但对于复杂的业务逻辑处理，直接操作原始的API需要做的工作非常多。Kafka流处理（KafkaStreams）为开发者提供了完整的流处理API ，比如流的聚合、连接、各种转换操作。
同时，Kafka流处理框架内部解决很多流处理应用程序都会面临的问题：处理乱序或迟来的数据、重新处理输入数据、窗口和状态操作等。     
将消息系统、存储存储、流处理系统组合在一起：传统消息系统的流处理通常只会处理订阅动作发生之后才到达的新消息，无法处理订阅之前的历史数据。
分布式文件存储系统一般存储静态的历史数据，对历史数据的处理一般采用批处理的方式。现有的开源系统很难将这些系统无缝地整合起来， Kafka则将消息系统、
存储系统、流处理系统都组合在一起，构成了以Kafka为中心的流式数据处理平台。它既能处理最新的实时数据，也能处理过去的历史数据。
## 6.kafka的4种核心API
  ![api](http://github.com/xidianlina/practice/raw/master//kafka_practice/picture/api.jpg)
生产者（producer）应用程序发布事件流到Kafka的一个或多个主题。       
消费者（consumer）应用程序订阅Kafka的一个或多个主题，并处理事件流。        
连接器（connector）将Kafka主题和已有数据源进行连接，数据可以互相导人和导出。       
流处理（processor ) Kafka主题消费输入流，经过处理后，产生输出流到输出主题。
## 7.kafka设计与实现   
### (1).文件系统的持久化与数据传输效率
> 现代操作系统针对磁盘的读写做了一些优化方案来加快磁盘的访问速度。预读（read-ahead）会提前将一个比较大的磁盘块读人内存。后写（write-behind）
会将很多小的逻辑写操作合并起来组合成一个大的物理写操作。并且，操作系统还会将主内存剩余的所有空闲内存空间都用作磁盘缓存（disk cache/page cache),
所有的磁盘读写操作都会经过统一的磁盘缓存（除了直接νo会绕过磁盘缓存）。综合这几点优化特点，如果是针对磁盘的顺序访问，某些情况下它可能比随机的内存访
问都要快，甚至可以和网络的速度相差无几。
  ![mem](http://github.com/xidianlina/practice/raw/master//kafka_practice/picture/mem.jpg)
> 如上图，应用程序写人数据到文件系统的一般做法是：在内存中保存尽可能多的数据，并在需要时将这些数据刷新到文件系统。但这里要做完全相反的事情，
  所有的数据都立即写入文件系统的持久化日志文件，但不进行刷新数据的任何调用。数据会首先被传输到磁盘缓存，操作系统随后会将这些数据定期自动刷新
  到物理磁盘。            
> 消息系统内的消息从生产者保存到服务端，消费者再从服务端读取出来，数据的传输效率决定了生产者和消费者的性能。生产者如果每发送一条消息都直接通过网
  络发送到服务端，势必会造成过多的网络请求。如果能够将多条消息按照分区进行分组，并采用批量的方式一次发送一个消息集，并且对消息集进行压缩，就可以
  减少网络传输的带宽，进一步提高数据的传输效率。  
  ![mem2](http://github.com/xidianlina/practice/raw/master//kafka_practice/picture/mem2.jpg)
> 消费者要读取服务端的数据，需要将服务端的磁盘文件通过网络发送到消费者进程，而网络发送通常涉及不同的网络节点。如上图所示，传统读取磁盘文件的数据
  在每次发送到网络时，都需要将页面缓存先保存到用户缓存，然后在读取消息时再将其复制到内核空间，具体步骤如下：     
  （1）操作系统将数据从磁盘中读取到内核空间里的页面缓存       
  （2）应用程序将数据从内核空间读人用户空间的缓冲区     
  （3）应用程序将读到的数据写回内核空间并放入socket缓冲区        
  （4）操作系统将数据从socket缓冲区复制到网卡接口，此时数据才能通过网络发送出去                
  结合Kafka的消息有多个订阅者的使用场景，生产者发布的消息一般会被不同的消费者消费多次。如下图（右）所示，使用“零拷贝技术”（ zero-copy ）只需
  将磁盘文件的数据复制到页面缓存中一次，然后将数据从页面缓存直接发送到网络中（发送给不同的使用者时，都可以重复使用同一个页面缓存），避免了重复的
  复制操作。这样，消息使用的速度基本上等同于网络连接的速度了。     
  ![mem3](http://github.com/xidianlina/practice/raw/master//kafka_practice/picture/mem3.jpg)   
> 假设有10个消费者，传统复制方式的数据复制次数是4 × 10=40 次，而“零拷贝技术”只需1 + 10 = 11 次（一次表示从磁盘复制到页面缓存，另外10 次表示
  10个消费者各自读取一次页面缓存）。显然，“零拷贝技术”比传统复制方式需要的复制次数更少。越少的数据复制，就越能更快地读取到数据；延迟越少，消费者的性能就越好。
### (2).生产者与消费者
> Kafka的生产者将消息直接发送给分区主副本所在的消息代理节点，并不需要经过任何的中间路由层。为了做到这一点，所有消息代理节点都会保存一份相同的元数据，
这份元数据记录了每个主题分区对应的主副本节点。生产者客户端在发送消息之前，会向任意一个代理节点请求元数据，井确定每条消息对应的目标节点然后把消息直
接发送给对应的目标节点。        
> 生产者客户端有两种方式决定发布的消息归属于哪个分区：通过随机方式将请求负载到不同的消息代理节点，或者使用“分区语义函数”将相同键的所有消息发布到
  同一个分区。对于分区语义， Kafka暴露了一个接口，允许用户指定消息的键如何参与分区。      
> 生产者采用批量发送消息集的方式解决了网络请求过多的问题。生产者会尝试在内存中收集足够数据，并在一个请求中一次性发送一批数据。另外，还可以为生产
  者客户端设置“在指定的时间内收集不超过指定数量的消息” 。比如，设置消息大小上限等于64 字节，延迟时间等于100毫秒，表示在100毫秒内消息大小达到
  64字节要立即发送；如果在100 毫秒时还没达到64字节，也要把已经收集的消息发送出去。客户端采用这种缓冲机制，在发送消息前会收集尽可能多的数据，
  通过每次牺牲一点点额外的延迟来换取更高的吞吐量。相应地，服务端的I/O消耗也会大大降低。
### (3).副本机制和容错处理
> Kafka的副本机制会在多个服务端节点（简称节点即消息代理节点）上对每个主题分区的日志进行复制。当集群中的某个节点出现故障时，访问故障节点的请求会
  被转移到其他正常节点的副本上。副本的单位是主题的分区，Kafka每个主题的每个分区都有一个主副本以及0个或多个备份副本。备份副本会保持和主副本的数
  据同步，用来在主副本失效时替换为主副本。
  ![replica](http://github.com/xidianlina/practice/raw/master//kafka_practice/picture/replica.jpg)   
> 如上图，所有的读写请求总是路由到分区的主副本。虽然生产者可以通过负载均衡策略将消息分配到不同的分区，但如果这些分区的主副本都在同一个服务器上（左图），
  就会存在数据热点问题。因此，分区的主副本应该均匀地分配到各个服务器上（见右图）。通常，分区的数量要比服务器多很多，所以每个服务器都可以成为一些分区
  的主副本，也能同时成为一些分区的备份副本。     
> 备份副本始终尽量保持与主副本的数据同步。备份副本的日志文件和主副本的日志总是相同的，它们都有相同的偏移量和相同顺序的消息。备份副本从主副本
  消费消息的方式和普通的消费者一样，只不过备份副本会将消息运用到自己的本地日志文件（备份副本和主副本都在服务端，它们都会将收到的分区数据持久
  化成日志文件）。普通的消费者客户端拉取到消息后并不会持久化，而是直接处理。     
  分布式系统故障容错时，需要明确的定义节点是否处于存活状态。Kafka对节点的存活状态有两个定义：      
  1.节点必须和ZK保持会话；        
  2.如果这个节点是某个分区的备份副本，它必须对分区主副本的写操作进行复制，并且复制的进度不能落后太多。       
  满足这两个条件，叫作“正在同步中”（ in-sync）。每个分区的主副本会跟踪正在同步中的备份副本节点（In Sync Replicas，即ISR）。如果一个备份副本
  挂掉、没有响应或者落后太多，主副本就会将其从同步副本集合中移除。反之，如果备份副本重新赶上主副本，它就会加入到主副本的同步集合中。     
  在Kafka中，一条消息只有被ISR集合的所有副本都运用到本地的日志文件，才会认为消息被成功提交了。任何时刻，只要ISR至少有一个副本是存活的， 
  Kafka就可以保证“一条消息一旦被提交，就不会丢失” 。只有已经提交的消息才能被消费者消费，因此消费者不用担心会看到因为主副本失败而丢失的消息。
# 二.命令行操作kafka
## 1.安装
brew install kafka      
会一起安装zookeeper和Kafka两个插件             
  ![kafka](http://github.com/xidianlina/practice/raw/master//kafka_practice/picture/kafka.jpg)   
## 2.启动
kafka依赖zookeeper，而kafka中默认已有一个单机版的zookeeper供使用（实际生产环境中不推荐使用默认的zookeeper）        
/usr/local/etc/kafka/zookeeper.properties   是zookeeper的配置文件     
启动zookeeper:        
cd到kafka的安装目录下            
cd /usr/local/Cellar/kafka/2.7.0        
执行脚本启动zookeeper     
bin/zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties        
启动成功如下图:        
  ![kafka2](http://github.com/xidianlina/practice/raw/master//kafka_practice/picture/kafka2.jpg)   
再打开一个控制窗口，cd到kafka的安装目录下        
cd /usr/local/Cellar/kafka/2.7.0        
执行脚本启动kafka     
bin/kafka-server-start /usr/local/etc/kafka/server.properties
## 3.创建topic
打开一个控制窗口，cd到kafka的安装目录下             
cd /usr/local/Cellar/kafka/2.7.0          
执行脚本创建topic         
bin/kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test_topic       
–create 创建主题命令      
–zookeeper localhost:2181 指定zookeeper       
–replication-factor 1 指定副本个数        
–partitions 1 指定分区个数        
–topic test_topic 主题名称为“test_topic”     
## 4.查看topic
bin/kafka-topics --list --zookeeper localhost:2181      
## 5.向topic发送消息
bin/kafka-console-producer --broker-list localhost:9092 --topic test_topic      
## 6.获取topic的消息
bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic test_topic --from-beginning        
## 7.创建一个多broker的集群
### (1).拷贝配置文件
cp /usr/local/etc/kafka/server.properties /usr/local/etc/kafka/server-1.properties          
cp /usr/local/etc/kafka/server.properties /usr/local/etc/kafka/server-2.properties
### (2).修改配置文件
/usr/local/etc/kafka/server-1.properties        
          broker.id=1       
          listeners=PLAINTEXT://:9093        
          log.dirs=/usr/local/var/lib/kafka-logs-1             
/usr/local/etc/kafka/server-2.properties        
           broker.id=2       
           listeners=PLAINTEXT://:9094        
           log.dirs=/usr/local/var/lib/kafka-logs-2  
### (3).启动kafka服务端
bin/kafka-server-start /usr/local/etc/kafka/server.properties &     
bin/kafka-server-start /usr/local/etc/kafka/server-1.properties &       
bin/kafka-server-start /usr/local/etc/kafka/server-2.properties &       
### (4).在多broker集群中创建一个新的topic      
bin/kafka-topics --create --zookeeper localhost:2181 --replication-factor 3 --partitions 1 --topic my-replicated-topic       
### (5).查看多broker集群中topic在哪个运行中的broker
bin/kafka-topics --describe --zookeeper localhost:2181 --topic my-replicated-topic      
bin/kafka-topics --describe --zookeeper localhost:2181 --topic test_topic

### 参考文档        
https://kafka.apachecn.org/         
https://blog.csdn.net/tangdong3415/article/details/53432166  

# 三.面试题总结
## 1.题目列表
### (1).kafka是什么？主要应用场景有哪些？
### (2).和其他消息队列相比,kafka的优势在哪里？
### (3).队列模型了解吗？Kafka的消息模型知道吗？
### (4).什么是Producer、Consumer、Broker、Topic、Partition？
### (5).Kafka的多副本机制了解吗？带来了什么好处？
### (6).Zookeeper在Kafka中的作用知道吗？
### (7).Kafka如何保证消息的消费顺序？
### (8).Kafka如何保证消息不丢失?
### (9).Kafka如何保证消息不重复消费？
### (10).kafka特性?
### (11).分区和代理节点的关系？
### (12).什么是记录(Record)?
### (13).kafka适合哪些场景？
### (14).kafka压缩消息可能发生的地方？
### (15).数据传输的事务定义有哪三种？
### (16).Kafka判断一个节点是否还活着有那两个条件？
### (17).Kafka与传统消息系统之间有三个关键区别
### (18).什么是ISR机制？
### (19).kafka的ack机制
### (20).kafka可以脱离zookeeper单独使用吗？为什么？
### (21).kafka有几种数据保留的策略？          
### (22).什么情况会导致kafka运行变慢？          
### (23).使用kafka集群需要注意什么？

## 2.题目答案
### (1).kafka是什么？主要应用场景有哪些？
> Kafka是一个分布式流式处理平台。        
  流平台具有三个关键功能:      
  消息队列:发布和订阅消息流，这个功能类似于消息队列，这也是Kafka也被归类为消息队列的原因。       
  容错的持久方式存储记录消息流:kafka会把消息持久化到磁盘，有效避免了消息丢失的风险。           
  流式处理平台:在消息发布的时候进行处理，kafka提供了一个完整的流式处理类库。     
  Kafka主要有两大应用场景：      
  消息队列:建立实时流数据管道，以可靠地在系统或应用程序之间获取数据。        
  数据处理:构建实时的流数据处理程序来转换或处理数据流。       
### (2).和其他消息队列相比,kafka的优势在哪里？
> Kafka相比其他消息队列主要的优势如下：        
  极致的性能:基于Scala和Java语言开发，设计中大量使用了批量处理和异步的思想，最高可以每秒处理千万级别的消息。Kafka的性能是和数据量无关的常量级的。       
  生态系统兼容性无可匹敌:Kafka与周边生态系统的兼容性是最好的,没有之一,尤其在大数据和流计算领域。       
### (3).队列模型了解吗？Kafka的消息模型知道吗？
> 队列模型：早期的消息模型        
  ![queue](http://github.com/xidianlina/practice/raw/master//kafka_practice/picture/queue.jpg)   
  使用队列（Queue）作为消息通信载体，满足生产者与消费者模式，一条消息只能被一个消费者使用，未被消费的消息在队列中保留直到被消费或超时。
  比如：生产者发送100条消息的话，两个消费者来消费一般情况下两个消费者会按照消息发送的顺序各自消费一半（也就是你一个我一个的消费。）        
  队列模型存在的问题：                
  假如存在这样一种情况:需要将生产者产生的消息分发给多个消费者，并且每个消费者都能接收到完成的消息内容。这种情况，队列模型就不好解决了。
  (可以为每个消费者创建一个单独的队列，让生产者发送多份。这是一种非常愚蠢的做法，浪费资源不说，还违背了使用消息队列的目的。)        
  发布-订阅模型:Kafka消息模型         
  发布-订阅模型主要是为了解决队列模型存在的问题。      
  ![queue2](http://github.com/xidianlina/practice/raw/master//kafka_practice/picture/queue2.jpg)   
  发布订阅模型（Pub-Sub）使用主题（Topic）作为消息通信载体，类似于广播模式；发布者发布一条消息，该消息通过主题传递给所有的订阅者，
  在一条消息广播之后才订阅的用户则是收不到该条消息的。                
  在发布-订阅模型中，如果只有一个订阅者，那它和队列模型就基本是一样的了。所以说，发布-订阅模型在功能层面上是可以兼容队列模型的。      
  Kafka采用的就是发布-订阅模型。        
  RocketMQ的消息模型和Kafka基本是完全一样的。唯一的区别是Kafka中没有队列这个概念，与之对应的是Partition（分区）。
### (4).什么是Producer、Consumer、Broker、Topic、Partition？
> Kafka将生产者发布的消息发送到Topic(主题)中，需要这些消息的消费者可以订阅这些Topic(主题)，如下图所示：
  ![cluster](http://github.com/xidianlina/practice/raw/master//kafka_practice/picture/cluster.jpg)   
  Producer（生产者）:产生消息的一方。                
  Consumer（消费者）:消费消息的一方。                
  Broker（代理）: 可以看作是一个独立的Kafka实例。多个Kafka Broker组成一个Kafka Cluster。                
  每个Broker中又包含了Topic以及Partition这两个重要的概念：               
  Topic（主题）: Producer将消息发送到特定的主题，Consumer通过订阅特定的 Topic(主题) 来消费消息。       
  Partition（分区）: Partition属于Topic 的一部分。一个Topic可以有多个Partition ，并且同一Topic下的Partition可以分布在不同的Broker上，这也就表明一个Topic可以横跨多个Broker。     
  Kafka中的Partition（分区）实际上可以对应成为消息队列中的队列。        
### (5).Kafka的多副本机制了解吗？带来了什么好处？
> Kafka为分区（Partition）引入了多副本（Replica）机制。分区（Partition）中的多个副本之间会有一个充当主副本leader，其他副本称为follower。发送的消息会被发送到leader副本，然后follower副本从leader副本中拉取消息进行同步。
  生产者和消费者只与leader副本交互。其他副本只是leader副本的拷贝，它们的存在只是为了保证消息存储的安全性。当leader副本发生故障时会从follower中选举出一个leader,但是follower中如果有和leader同步程度达不到要求的参加不了leader的竞选。      
  副本的单位是主题的分区，Kafka每个主题的每个分区都有一个主副本以及0个或多个备份副本。备份副本会保持和主副本的数据同步，用来在主副本失效时替换为主副本。            
  分区的主副本均匀地分配到各个服务器上。通常，分区的数量要比服务器多很多，所以每个服务器都可以成为一些分区的主副本，也能同时成为一些分区的备份副本。         
  Kafka通过给特定Topic指定多个Partition, 而各个Partition可以分布在不同的Broker上, 这样便能提供比较好的并发能力（负载均衡）。
  Partition可以指定对应的Replica数, 这也极大地提高了消息存储的安全性, 提高了容灾能力，不过也相应的增加了所需要的存储空间。       
### (6).Zookeeper在Kafka中的作用知道吗？
  ![zk](http://github.com/xidianlina/practice/raw/master//kafka_practice/picture/zk.jpg)   
> zooKeeper主要为kafka提供元数据的管理的功能。     
  Ⅰ.Broker注册              
  Broker是分布式部署并且相互之间相互独立，但是需要有一个注册系统能够将整个集群中的Broker管理起来，此时就使用到了Zookeeper。在Zookeeper上会有一个专门用来进行Broker服务器列表记录的节点：/brokers/ids     
  每个Broker在启动时，都会到Zookeeper上进行注册，即到/brokers/ids下创建属于自己的节点，如/brokers/ids/[0...N]。        
  Kafka使用了全局唯一的数字来指代每个Broker服务器，不同的Broker必须使用不同的BrokerID进行注册，创建完节点后，每个Broker就会将自己的IP地址和端口信息记录到该节点中去。其中，Broker创建的节点类型是临时节点，一旦Broker宕机，则对应的临时节点也会被自动删除。       
  Ⅱ.Topic注册        
  在Kafka中，同一个Topic的消息会被分成多个分区并将其分布在多个Broker上，这些分区信息及与Broker的对应关系也都是由Zookeeper在维护，由专门的节点来记录，如：/borkers/topics        
  Kafka中每个Topic都会以/brokers/topics/[topic]的形式被记录，如/brokers/topics/login和/brokers/topics/search等。Broker服务器启动后，会到对应Topic节点（/brokers/topics）上注册自己的BrokerID并写入针对该Topic的分区总数，如/brokers/topics/login/3->2，这个节点表示BrokerID为3的一个Broker服务器，对于"login"这个Topic的消息，提供了2个分区进行消息存储，同样，这个分区节点也是临时节点。      
  Ⅲ.生产者负载均衡     
  由于同一个Topic消息会被分区并将其分布在多个Broker上，因此，生产者需要将消息合理地发送到这些分布式的Broker上，那么如何实现生产者的负载均衡，Kafka支持传统的四层负载均衡，也支持Zookeeper方式实现负载均衡。      
  四层负载均衡:根据生产者的IP地址和端口来为其确定一个相关联的Broker。通常，一个生产者只会对应单个Broker，然后该生产者产生的消息都发往该Broker。这种方式逻辑简单，每个生产者不需要同其他系统建立额外的TCP连接，只需要和Broker维护单个TCP连接即可。但是，其无法做到真正的负载均衡，因为实际系统中的每个生产者产生的消息量及每个Broker的消息存储量都是不一样的，如果有些生产者产生的消息远多于其他生产者的话，那么会导致不同的Broker接收到的消息总数差异巨大，同时，生产者也无法实时感知到Broker的新增和删除。        
  使用Zookeeper进行负载均衡:由于每个Broker启动时，都会完成Broker注册过程，生产者会通过该节点的变化来动态地感知到Broker服务器列表的变更，这样就可以实现动态的负载均衡机制。        
  Ⅳ.消费者注册              
  消费组(Consumer Group):      	
  consumer group下有多个 Consumer（消费者）。         
  对于每个消费者组 (Consumer Group)，Kafka都会为其分配一个全局唯一的Group ID，Group内部的所有消费者共享该ID。订阅的topic下的每个分区只能分配给某个group下的一个consumer(当然该分区还可以被分配给其他group)。
  同时，Kafka为每个消费者分配一个ConsumerID，通常采用"Hostname:UUID"形式表示。     
  在Kafka中，规定了每个消息分区只能被同组的一个消费者进行消费，因此，需要在Zookeeper上记录消息分区与Consumer之间的关系，每个消费者一旦确定了对一个消息分区的消费权力，需要将其ConsumerID写入到 Zookeeper对应消息分区的临时节点上，例如：/consumers/[group_id]/owners/[topic]/[broker_id-partition_id]
  其中，[broker_id-partition_id]就是一个消息分区的标识，节点内容就是该消息分区上消费者的ConsumerID。      
  消息消费进度Offset记录:       
  在消费者对指定消息分区进行消息消费的过程中，需要定时地将分区消息的消费进度Offset记录到Zookeeper上，以便在该消费者进行重启或者其他消费者重新接管该消息分区的消息消费后，能够从之前的进度开始继续进行消息消费。Offset在Zookeeper中由一个专门节点进行记录，其节点路径为:/consumers/[group_id]/offsets/[topic]/[broker_id-partition_id],节点内容就是Offset的值。                          
  消费者服务器在初始化启动时加入消费者分组的步骤如下:      
  注册到消费者分组。每个消费者服务器启动时，都会到Zookeeper的指定节点下创建一个属于自己的消费者节点，例如/consumers/[group_id]/ids/[consumer_id]，完成节点创建后，消费者就会将自己订阅的Topic信息写入该临时节点。
  对消费者分组中的消费者的变化注册监听。每个消费者都需要关注所属消费者分组中其他消费者服务器的变化情况，即对/consumers/[group_id]/ids节点注册子节点变化的Watcher监听，一旦发现消费者新增或减少，就触发消费者的负载均衡。
  对Broker服务器变化注册监听。消费者需要对/broker/ids/[0-N]中的节点进行监听，如果发现Broker服务器列表发生变化，那么就根据具体情况来决定是否需要进行消费者负载均衡。       
  为了让同一个Topic下不同分区的消息尽量均衡地被多个消费者消费而进行消费者与消息分区分配的过程，通常，对于一个消费者分组，如果组内的消费者服务器发生变更或Broker服务器发生变更，会发出消费者负载均衡。       
  以下是kafka在zookeep中的详细存储结构图：        
  ![kafka_zk](http://github.com/xidianlina/practice/raw/master//kafka_practice/picture/kafka_zk.jpg)   
  补充:早期版本的kafka用zk做meta信息存储，consumer的消费状态，group的管理以及offse t的值。考虑到zk本身的一些因素以及整个架构较大概率存在单点问题，新版本中逐渐弱化了zookeeper的作用。
>新的consumer使用了kafka内部的group coordination协议，也减少了对zookeeper的依赖。                       
  Ⅴ.消费者负载均衡          
  Kafka中的消费者需要进行负载均衡来实现多个消费者合理地从对应的Broker服务器上接收消息，每个消费者分组包含若干消费者，每条消息都只会发送给分组中的一个消费者，不同的消费者分组消费自己特定的Topic下面的消息，互不干扰。        
### (7).Kafka如何保证消息的消费顺序？
> 在使用消息队列的过程中经常有业务场景需要严格保证消息的消费顺序，比如同时发了2个消息，这2个消息对应的操作分别对应的数据库操作是：更改用户会员等级、根据会员等级计算订单价格。假如这两条消息的消费顺序不一样造成的最终结果就会截然不同。
  Kafka中Partition(分区)是真正保存消息的地方，发送的消息都被放在了Partition。而Partition(分区)又存在于Topic(主题)这个概念中，并且可以给特定Topic指定多个Partition。
  ![partition2](http://github.com/xidianlina/practice/raw/master//kafka_practice/picture/partition2.jpg)   
  每次添加消息到Partition(分区)的时候都会采用尾加法。Kafka只能为保证Partition(分区)中的消息有序，而不能保证Topic(主题)中的Partition(分区)的有序。        
  消息在被追加到Partition(分区)的时候都会分配一个特定的偏移量（offset）。Kafka通过偏移量（offset）来保证消息在分区内的顺序性。          
  所以，有一种很简单的保证消息消费顺序的方法:1个Topic只对应一个Partition。这样当然可以解决问题，但是破坏了Kafka的设计初衷。           
  Kafka中发送1条消息的时候，可以指定topic, partition, key,data（数据） 4个参数。如果发送消息的时候指定了Partition 的话，所有消息都会被发送到指定的Partition。并且，同一个key的消息可以保证只发送到同一个 partition，这个可以采用表/对象的id来作为key。        
  对于如何保证Kafka中消息消费的顺序，有两种方法：        
  1个Topic只对应一个Partition。        
  （推荐）发送消息的时候指定key/Partition。       
### (8).Kafka如何保证消息不丢失?
> Ⅰ.生产者丢失消息的情况        
  生产者(Producer)调用send方法发送消息之后，消息可能因为网络问题并没有发送过去。        
  所以，不能默认在调用send方法发送消息之后消息消息发送成功了。为了确定消息是发送成功，要判断消息发送的结果。但是要注意的是Kafka生产者(Producer)使用send方法发送消息实际上是异步的操作，可以通过 get()方法获取调用结果，但是这样也让它变为了同步操作，示例代码如下：       
  SendResult<String, Object> sendResult = kafkaTemplate.send(topic, o).get();       
  if (sendResult.getRecordMetadata() != null) {     
    logger.info("生产者成功发送消息到" + sendResult.getProducerRecord().topic() + "-> " + sendResult.getProducerRecord().value().toString());         
  }     
  但是一般不推荐这么做！可以采用为其添加回调函数的形式，示例代码如下：        
  ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, o);       
    future.addCallback(result -> logger.info("生产者成功发送消息到topic:{} partition:{}的消息", result.getRecordMetadata().topic(), result.getRecordMetadata().partition()),         
                  ex -> logger.error("生产者发送消失败，原因：{}", ex.getMessage()));       
  如果消息发送失败的话，检查失败的原因之后重新发送即可！       
  另外推荐为Producer的retries（重试次数）设置一个比较合理的值，一般是3 ，但是为了保证消息不丢失的话一般会设置比较大一点。设置完成之后，当出现网络问题之后能够自动重试消息发送，避免消息丢失。另外，建议还要设置重试间隔，因为间隔太小的话重试的效果就不明显了，网络波动一次你3次一下子就重试完了。            
  Ⅱ.消费者丢失消息的情况          
  消息在被追加到Partition(分区)的时候都会分配一个特定的偏移量（offset）。偏移量（offset)表示Consumer当前消费到的Partition(分区)的所在的位置。Kafka通过偏移量（offset）可以保证消息在分区内的顺序性。      
  当消费者拉取到了分区的某个消息之后，消费者会自动提交了offset。自动提交的话会有一个问题，试想一下，当消费者刚拿到这个消息准备进行真正消费的时候，突然挂掉了，消息实际上并没有被消费，但是offset却被自动提交了。
  解决办法也比较粗暴，手动关闭闭自动提交offset，每次在真正消费完消息之后之后再自己手动提交offset。但是，这样会带来消息被重新消费的问题。比如刚刚消费完消息之后，还没提交offset，结果自己挂掉了，那么这个消息理论上就会被消费两次。              
  Ⅲ.Kafka弄丢了消息      
  Kafka为分区（Partition）引入了多副本（Replica）机制。分区（Partition）中的多个副本之间会有一个叫做leader，其他副本称为follower。发送的消息会被发送到leader副本，然后follower副本才能从leader 副本中拉取消息进行同步。生产者和消费者只与leader副本交互。其他副本只是leader副本的拷贝，它们的存在只是为了保证消息存储的安全性。     
  试想一种情况：假如leader副本所在的broker突然挂掉，那么就要从follower副本重新选出一个leader ，但是leader的数据还有一些没有被follower副本的同步的话，就会造成消息丢失。       
  设置acks = all          
  解决办法就是设置acks = all。acks是Kafka生产者(Producer) 很重要的一个参数。                
  acks的默认值即为1，代表消息被leader副本接收之后就算被成功发送。当配置acks = all代表所有副本都要接收到该消息之后该消息才算真正成功被发送。           
  设置replication.factor >= 3             
  为了保证leader副本能有follower副本能同步消息，一般会为topic设置replication.factor >= 3。这样就可以保证每个分区(partition) 至少有3个副本。虽然造成了数据冗余，但是带来了数据的安全性。            
  设置min.insync.replicas > 1     
  一般情况下还需要设置min.insync.replicas> 1 ，这样配置代表消息至少要被写入到2个副本才算是被成功发送。min.insync.replicas的默认值为 1 ，在实际生产中应尽量避免默认值 1。           
  但是，为了保证整个Kafka服务的高可用性，需要确保replication.factor > min.insync.replicas 。为什么呢？设想一下假如两者相等的话，只要是有一个副本挂掉，整个分区就无法正常工作了。这明显违反高可用性！一般推荐设置成 replication.factor = min.insync.replicas + 1。         
  设置unclean.leader.election.enable = false          
  Kafka 0.11.0.0版本开始 unclean.leader.election.enable 参数的默认值由原来的true 改为false      
  发送的消息会被发送到leader副本，然后follower副本才能从leader副本中拉取消息进行同步。多个follower副本之间的消息同步情况不一样，当配置了unclean.leader.election.enable = false的话，当leader副本发生故障时就不会从follower 副本中和leader同步程度达不到要求的副本中选择出leader，这样降低了消息丢失的可能性。
  总结：
  a.不要使用 producer.send(msg)，而要使用 producer.send(msg, callback)。
  b.设置 acks = all。
  c.设置 retries 为一个较大的值。
  d.设置 unclean.leader.election.enable = false。
  e.设置 replication.factor >= 3。
  g.设置 min.insync.replicas > 1。
  h.确保 replication.factor > min.insync.replicas。
  i.确保消息消费完成再提交。                            
### (9).Kafka如何保证消息不重复消费？
> RabbitMQ、RocketMQ、Kafka都有可能出现重复消费的问题，导致重复消费的原因可能出现在生产者，也可能出现在MQ或消费者。这里说的重复消费问题是指同一个数据被执行了两次，不单单指MQ中一条消息被消费了两次，也可能是MQ 中存在两条一模一样的消费。        
  生产者：生产者可能会重复推送一条数据到MQ中，为什么会出现这种情况呢？也许是一个Controller接口被重复调用了2次，没有做接口幂等性导致的；也可能是推送消息到MQ时响应比较慢，生产者的重试机制导致再次推送了一次消息。       
  MQ：在消费者消费完一条数据响应ack信号消费成功时，MQ突然挂了，导致MQ以为消费者还未消费该条数据，MQ恢复后再次推送了该条消息，导致了重复消费。       
  消费者：消费者已经消费完了一条消息，正准备但是还未给MQ发送ack信号时，此时消费者挂了，服务重启后 MQ 以为消费者还没有消费该消息，再次推送了该条消息。        
  消息的重复消费问题实际上涉及到消息者消费消息的幂等性问题。重复消费问题通常在消费者端解决，当然生产者端也最好简单控制下不要生产重复数据，但是一般情况下MQ是允许存在多条一样的数据的，只是消费端就不允许消费两条一样的数据，所以幂等性保障通常都是在消费者端实现。         
  那么消费者怎么解决重复消费问题呢？这个问题解决起来也比较简单，这里提供两种方法           
  状态判断法：消费者消费数据后把消费数据记录在 redis 中，下次消费时先到 redis 中查看是否存在该消息，存在则表示消息已经消费过，直接丢弃消息。              
  业务判断法：通常数据消费后都需要插入到数据库中，使用数据库的唯一性约束防止重复消费。每次消费直接尝试插入数据，如果提示唯一性字段重复，则直接丢失消息。一般都是通过这个业务判断的方法就可以简单高效地避免消息的重复处理了。                      
  消费消息时应注意以下几点：             
  数据落库前先查询，如果已经存在，则放弃新增做更新操作；           
  redis不需做幂等处理，天然幂等性；               
  生产者可以在消息中添加一个唯一ID，消费消息后将该ID存入redis，每次消费之前先查询下redis是否存在该ID，若存在则说明已消费；                  
  可以利用数据库主键约束，避免重复插入数据；             
### (10).kafka特性?
> a.消息持久化
  b.高吞吐量
  c.扩展性
  d.多客户端支持
  e.Kafka Streams
  f.安全机制
  g.数据备份
  h.轻量级
  i.消息压缩
### (11).分区和代理节点的关系？
> 一个分区只对应一个Broker,一个Broker可以管理多个分区。
### (12).什么是记录(Record)?
> 实际写入到kafka集群并且可以被消费者读取的数据。
  每条记录包含一个键、值和时间戳。
### (13).kafka适合哪些场景？
> 日志收集、消息系统、活动追踪、运营指标、流式处理、时间源等。
### (14).kafka压缩消息可能发生的地方？
> Producer 、Broker。
### (15).数据传输的事务定义有哪三种？
> 数据传输的事务定义通常有以下三种级别：
  a.最多一次: 消息不会被重复发送，最多被传输一次，但也有可能一次不传输;
  b.最少一次: 消息不会被漏发送，最少被传输一次，但也有可能被重复传输;
  c.精确的一次（Exactly once）: 不会漏传输也不会重复传输,每个消息都传输被一次而且仅仅被传输一次，这是大家所期望的.
### (16).Kafka判断一个节点是否还活着有那两个条件？
> a.节点必须可以维护和 ZooKeeper 的连接，Zookeeper通过心跳机制检查每个节点的连接
  b.如果节点是个follower,他必须能及时的同步leader的写操作，延时不能太久
### (17).Kafka与传统消息系统之间有三个关键区别
> a.Kafka 持久化日志，这些日志可以被重复读取和无限期保留
  b.Kafka 是一个分布式系统：它以集群的方式运行，可以灵活伸缩，在内部通过复制数据提升容错能力和高可用性
  c.Kafka 支持实时的流式处理
### (18).什么是ISR机制？
> 每个topic的分区可以设置若干个副本（Leader、Follower），其中Follower同步Leader的数据形成副本。为了保证生产者发送的数据，能可靠的发送到指定的topic，topic的每个分区收到生产者发送的数据后，都需要向生产者发送应答 ack（acknowledgement），如果生产者收到ack，就会进行下一轮的发送，否则重新发送数据。        
  在Kafka中必须要所有Follower都完成同步时才会发送ack，这样的好处是当重新选举Leader时，只需要有n+1个副本即可容忍n台节点故障，但缺点也很明显就是延迟很高，因为必须等待所有follower都完成同步才行。      
  但这样又会带来新的问题，如果其中有一个follower由于网络延迟或者某种原因，迟迟不能完成数据同步，Leader就会一直阻塞等待直到该follower完成同步，这非常的影响性能         
  于是Kafka引入了一个新的机制：ISR(In-Sync Replica)，副本数对Kafka的吞吐率是有一定的影响，但极大的增强了可用性，所以说kafka的数据同步方式不是完全同步的，也不是完全异步的，而是这种基于ISR的同步机制。         
  ISR:      
  和Leader保持同步的follower集合，Leader不需要等待所有Follower都完成同步，只要在ISR中的Follower完成数据同步就可以发送ack给生产者          
  如果ISR集合里的follower的延迟时间超过配置的参数（replica.lag.time.max.ms）就会从ISR内剔除，只需要保证Leader数据能够发送到这些ISR集合里的follower即可。一旦Leader发生故障，就会从ISR集合里选举一个Follower作为新的Leader。           
  AR:           
  所有的副本（replicas）统称为Assigned Replicas，即AR。                
  ISR是AR中的一个子集，每个Partition都会有一个ISR，而且是由leader动态维护，follower从leader同步数据有一些延迟（包括延迟时间replica.lag.time.max.ms和延迟条数replica.lag.max.messages两个维度)          
  任意一个超过阈值都会把follower剔除出ISR,存入OSR（Outof-Sync Replicas）列表，新加入的follower也会先存放在OSR中。AR=ISR+OSR。                  
  特性:           
  追随者副本不提供服务，只是定期地异步拉取领导者副本中的数据而已。既然是异步的，就存在着不可能与Leader实时同步的风险。             
  leader会维护一个与其基本保持同步的Replica列表，该列表称为ISR(in-syncReplica)，每个Partition都会有一个ISR，而且是由leader动态维护         
  Leader副本天然就在ISR中。也就是说，ISR不只是追随者副本集合，它必然包括Leader副本。甚至在某些情况下，ISR只有Leader这一个副本           
  如果一个flower比一个leader落后太多，或者超过一定时间未发起数据复制请求，则leader将其重ISR中移除            
  当ISR中所有Replica都向Leader发送ACK时，leader才commit            
  判断标准(replica.lag.time.max.ms)             
  这个标准就是Broker端参数replica.lag.time.max.ms 参数值。               
  这个参数的含义是Follower副本能够落后Leader副本的最长时间间隔，当前默认值是10秒。          
  这就是说，只要一个Follower副本落后Leader副本的时间不连续超过10秒，那么Kafka就认为该Follower副本与Leader是同步的，即使此时Follower副本中保存的消息明显少于Leader副本中的消息。               
  Follower副本唯一的工作就是不断地从Leader副本拉取消息，然后写入到自己的提交日志中。如果这个同步过程的速度持续慢于Leader副本的消息写入速度，那么在replica.lag.time.max.ms时间后，此Follower副本就会被认为是与Leader 副本不同步的，因此不能再放入ISR中。此时，Kafka会自动收缩ISR集合，将该副本“踢出”ISR。              
  倘若该副本后面慢慢地追上了Leader的进度，那么它是能够重新被加回ISR的。这也表明，ISR是一个动态调整的集合，而非静态不变的             
  在版本0.90以前还有一个参数，根据follower和leader之间相差的数据量来控制是否在ISR集合内，大于配置的阈值则踢出ISR集合。但是这个有一个很明显的问题，那就是如果流量增加了，可能就是使得正常同步的副本被剔除ISR了               
### (19).kafka的ack机制 
> request.required.acks有三个值 0 1 -1
  0:生产者不会等待broker的ack，这个延迟最低但是存储的保证最弱当server挂掉的时候就会丢数据
  1:服务端会等待ack值leader副本确认接收到消息后发送ack,但是如果leader挂掉后他不确保是否复制完成新leader,也会导致数据丢失
  -1:在1的基础上服务端会等所有的follower的副本收到数据后才会收到leader发出的ack，这样数据不会丢失
### (20).kafka可以脱离zookeeper单独使用吗？为什么？
>kafka不能脱离zookeeper单独使用，因为kafka使用zookeeper管理和协调kafka的节点服务器。
### (21).kafka有几种数据保留的策略？
>kafka有两种数据保存策略：按照过期时间保留和按照存储的消息大小保留。               
>           
>kafka同时设置了7天和10G清除数据，到第五天的时候消息达到了10G，这个时候kafka将如何处理？                          
>kafka 会执行数据清除工作，时间和大小不论那个满足条件，都会清空数据。          
### (22).什么情况会导致kafka运行变慢？
>cpu性能瓶颈        
 磁盘读写瓶颈     
 网络瓶颈           
### (23).使用kafka集群需要注意什么？
>集群的数量不是越多越好，最好不要超过7个，因为节点越多，消息复制需要的时间就越长，整个群组的吞吐量就越低。              
 集群数量最好是单数，因为超过一半故障集群就不能用了，设置为单数容错率更高。                      

## 常见面试题目
https://github.com/Snailclimb/JavaGuide/blob/master/docs/system-design/distributed-system/message-queue/Kafka%E5%B8%B8%E8%A7%81%E9%9D%A2%E8%AF%95%E9%A2%98%E6%80%BB%E7%BB%93.md         
https://juejin.cn/post/6937212743061618719  