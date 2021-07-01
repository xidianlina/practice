linux
======

## 问题列表
### 1.wc
### 2.find
### 3.grep
### 4.awk
### 5.sed

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
### 2.find
>find命令用来在指定目录下查找文件。任何位于参数之前的字符串都将被视为欲查找的目录名。如果使用该命令时，不设置任何参数，则find命令将在当前目录
>下查找子目录与文件。并且将查找到的子目录和文件全部进行显示。                     
>语法:                
 find 选项  参数                    
>                           
>find .                             
 列出当前目录及子目录下所有文件和文件夹                    
>                   
>find /home -name "*.txt"                   
 在/home目录下查找以.txt结尾的文件名                             
>               
>find /home -iname "*.txt"                      
 在/home目录下查找以.txt结尾的文件名，但忽略大小写 
>               
>find .-type f -user root -exec chown tom {} \;                 
 找出当前目录下所有root的文件，并把所有权更改为用户tom                                                            
>{} 用于与-exec选项结合使用来匹配所有文件，然后会被替换为相应的文件名。                                              
>               
>find $HOME/. -name "*.txt" -ok rm {} \;                        
 找出自己家目录下所有的.txt文件并删除                           
>                   
>-ok和-exec行为一样，不过它会给出提示，是否执行相应的操作。                          
 find . -type f -mtime +30 -name "*.log" -exec cp {} old \;                             
 将30天前的.log文件移动到old目录中                                           
>                           
>参考  https://man.linuxde.net/find                                 
### 3.grep
>
### 4.awk
### 5.sed                