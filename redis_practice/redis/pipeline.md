## 11.Pipeline有什么好处，为什么要用pipeline？
>pipeline的作用是将一批命令进行打包，然后发送给服务器，服务器执行完按顺序打包返回。          
 通过pipeline，一次pipeline（n条命令）=一次网络时间 + n次命令时间            
 pipeline可以将多次IO往返的时间缩减为一次，前提是pipeline执行的指令之间没有因果相关性。进行压测的时候可以发现影响redis的QPS峰值的一个重要因素是pipeline批次指令的数目。               
 pipeline注意事项:              
 每次pipeline携带数量不推荐过大，否则会影响网络性能              
 pipeline每次只能作用在一个Redis节点上              
