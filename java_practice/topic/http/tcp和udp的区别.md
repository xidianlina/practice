### 4.简述tcp和udp的区别？
>(1).TCP是面向连接的运输层协议;UDP是无连接的，即发送数据之前不需要建立连接             
 (2).TCP提供可靠交付的服务;UDP使用尽最大努力交付，即不保证可靠交付             
 (3).TCP是面向字节流;UDP是面向报文的                
 (4).TCP首部最低20个字节;UDP的首部开销小，只有8个字节              
 (5).每一条TCP连接只能是点到点的;UDP支持一对一，一对多，多对一和多对多的交互通信              
 (6).TCP的逻辑通信信道是全双工的可靠信道，UDP则是不可靠信道         
>(7).TCP对系统资源要求较多，UDP对系统资源要求较少                                             
