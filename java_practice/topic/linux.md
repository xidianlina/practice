linux
======

## 问题列表
### 1.wc
### 2.
### 3.
### 4.
### 5.

## 问题答案
### 1.wc
>命令格式：                          
 wc [选项] 文件                     
>                               
>命令功能：                                    
 统计指定文件中的字节数、字数、行数，并将统计结果显示输出。如果没有给出文件名，则从标准输入读取。wc同时也给出所指定文件的总统计数。                         
>                           
>命令参数：              
 -c 统计字节数。                  
 -l 统计行数。                   
 -m 统计字符数。这个标志不能与 -c 标志一起使用。                    
 -w 统计字数。一个字被定义为由空白、跳格或换行字符分隔的字符串。          
 -L 打印最长行的长度。           
 --help 显示帮助信息              
 --version 显示版本信息                       
> ![linux_wc](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/linux_wc.png)                   
>管道命令只处理前一个命令正确输出，不处理错误输出。                  
 管道命令右边命令，必须能够接收标准输入流命令才行。      
>参考 https://www.cnblogs.com/luntai/p/6232824.html                                                                             
