linux
======

## 问题列表
### 1.wc
### 2.find
### 3.grep
### 4.awk
### 5.sed
### 6.sort
### 7.uniq
### 8.touch
### 9.chown
### 10.chmod
### 11.ps
### 12.top
### 13.netstat
### 14.ifconfig
### 15.kill
### 16.tr
### 17.cut
### 18.ulimit
### 19.
### 20.

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
>find . -type f -user root -exec chown tom {} \;                 
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
>文本搜索工具，它能使用正则表达式搜索文本，并把匹配的行打印出来。           
```shell script
grep match_pattern file_name   #在文件中搜索"match_pattern"，返回一个包含“match_pattern”的文本行             

grep "match_pattern" file_1 file_2 file_3 ...    #在多个文件中查找

grep -v "match_pattern" file_name    #输出除匹配行之外的所有行 -v 选项

grep -E "[1-9]+"   或       egrep "[1-9]+"          #使用正则表达式 -E 选项

echo this is a test line. | grep -o -E "[a-z]+\."     #只输出文件中匹配到的部分 -o 选项

grep -c "text" file_name    #统计文件或者文本中包含匹配字符串的行数 -c 选项

grep "text" -n file_name  #输出包含匹配字符串的行号和文本 -n 选项

echo gun is not unix | grep -b -o "not"   #打印样式匹配所位于的字符或字节偏移 -b 选项

grep -l "text" file1 file2 file3...   #列出文件内容符合指定的范本样式的文件名称 -l

grep "text" . -r -n    #在多级目录中对文本进行递归搜索

echo "hello world" | grep -i "HELLO"    #忽略匹配样式中的字符大小写

echo this is a text line | grep -e "is" -e "line" -o     #选项 -e 制动多个匹配样式

grep "main()" . -r --include *.{php,html}  #只在目录中所有的.php和.html文件中递归搜索字符"main()"

grep "main()" . -r --exclude "README"     #在搜索结果中排除所有README文件

grep "main()" . -r --exclude-from filelist    #在搜索结果中排除filelist文件列表里的文件

rep -q "test" filename   #不会输出任何信息，如果命令运行成功返回0，失败则返回非0值。一般用于条件测试

seq 10 | grep "5" -A 3    #显示匹配某个结果之后的3行，使用 -A 选项

seq 10 | grep "5" -B 3    #显示匹配某个结果之前的3行，使用 -B 选项

seq 10 | grep "5" -C 3    #显示匹配某个结果的前三行和后三行，使用 -C 选项

#测试文件：
echo "aaa" > file1
echo "bbb" > file2
echo "aaa" > file3

grep "aaa" file* -lZ | xargs -0 rm

#执行后会删除file1和file3，grep输出用-Z选项来指定以0值字节作为终结符文件名（\0），xargs -0 读取输入并用0值字节终结符分隔文件名，然后删除匹配文件，-Z通常和-l结合使用。
```           
>           
>参考 https://man.linuxde.net/grep            
>https://www.cnblogs.com/forestwolf/p/6413916.html          
### 4.awk
### 5.sed
### 6.sort
### 7.uniq              