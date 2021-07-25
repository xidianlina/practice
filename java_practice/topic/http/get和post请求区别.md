### 3.get和post请求有哪些区别？
>GET和POST是HTTP协议中定义的两种发送请求的方法。HTTP是基于TCP/IP的关于数据如何在万维网中如何通信的协议。
>GET和POST本质上就是TCP链接，并无差别。但是由于HTTP的规定和浏览器/服务器的限制，导致他们在应用过程中体现出一些不同：              
 (1).GET是从指定的服务器中获取数据;POST是提交数据给指定的服务器处理                
 (2).GET请求的数据会附在URL之后，以?分割URL和传输数据，多个参数之间以&相连;
>POST方式将表单内各个字段和内容放置在HTML HEADER中一起传送到Action属性所指定的URL地址，用户是看不到这个过程的             
 (3).GET方式提交的数据最多只能是1024字节，理论上POST没有限制，可传较大量的数据             
 (4).服务端获取GET请求参数用Request.QueryString，获取POST请求参数用Request.Form               
 (5).GET请求会被浏览器主动cache，而POST不会，除非手动设置。              
 (6).对参数的数据类型，GET只接受ASCII字符，而POST没有限制。              
 (7).POST的安全性要比GET的安全性高。                
 (8).GET和POST一个重大区别：GET产生一个TCP数据包；POST产生两个TCP数据包。对于GET方式的请求，浏览器会把http header和data一并发送出去，
>服务器响应200(返回数据)；而对于POST，浏览器先发送http header，服务器响应100之后浏览器再发送data，服务器响应200(返回数据)。
>但是并不是所有浏览器都会在POST中发送两次包，Firefox就只发送一次。                 
