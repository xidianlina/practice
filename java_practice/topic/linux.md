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
### 19.xargs
### 20.df
### 21.du 

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
>搜索的结果被送到标准输出，不影响原文件内容。                            
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
>awk是一个强大的文本分析工具。awk就是把文件逐行的读入，以空格为默认分隔符将每行切片，切开的部分再进行各种分析处理。           
 使用方法  awk '{pattern + action}' {filenames}             
 其中 pattern 表示 AWK 在数据中查找的内容，而 action 是在找到匹配内容时所执行的一系列命令。花括号（{}）不需要在程序中始终出现，
>但它们用于根据特定的模式对一系列指令进行分组。 pattern就是要表示的正则表达式，用斜杠括起来。             
 awk语言的最基本功能是在文件或者字符串中基于指定规则浏览和抽取信息，awk抽取信息后，才能进行其他文本操作。完整的awk脚本通常用来格式化文本文件中的信息。                
 通常，awk是以文件的一行为处理单位的。awk每接收文件的一行，然后执行相应的命令，来处理文本。                   
>               
>有三种方式调用awk                         
 (1).命令行方式                      
 awk [-F  field-separator]  'commands'  input-file(s)                   
 其中，commands 是真正awk命令，[-F域分隔符]是可选的。 input-file(s) 是待处理的文件。                      
 在awk中，文件的每一行中，由域分隔符分开的每一项称为一个域。通常，在不指名-F域分隔符的情况下，默认的域分隔符是空格。               
>                   
>(2).shell脚本方式                  
 将所有的awk命令插入一个文件，并使awk程序可执行，然后awk命令解释器作为脚本的首行，一遍通过键入脚本名称来调用。                    
 相当于shell脚本首行的：#!/bin/sh                
 可以换成：#!/bin/awk                    
>               
>(3).将所有的awk命令插入一个单独文件，然后调用：                        
 awk -f awk-script-file input-file(s)                   
 其中，-f选项加载awk-script-file中的awk脚本，input-file(s)跟上面的是一样的。                 
>                   
>awk工作流程：读入有'\n'换行符分割的一条记录，然后将记录按指定的域分隔符划分，$0则表示所有域,$1表示第一个域,$n表示第n个域。默认域分隔符是"空白键" 或 "[tab]键"。              
>           
```shell script
cat /etc/passwd |awk  -F ':'  'BEGIN {print "name,shell"}  {print $1","$7} END {print "blue,/bin/nosh"}'
# awk工作流程是这样的：先执行BEGING，然后读取文件，读入有\n换行符分割的一条记录，然后将记录按指定的域分隔符划分域，填充域，$0则表示所有域,$1表示第一个域,$n表示第n个域,随后开始执行模式所对应的动作action。接着开始读入第二条记录······直到所有的记录都读完，最后执行END操作。

awk -F: '/root/' /etc/passwd    #awk搜索支持正则,匹配了pattern的行才会执行action,没有指定action，默认输出每行的内容。

#awk内置变量
ARGC               #命令行参数个数
ARGV               #命令行参数排列
ENVIRON            #支持队列中系统环境变量的使用
FILENAME           #awk浏览的文件名
FNR                #浏览文件的记录数
FS                 #设置输入域分隔符，等价于命令行 -F选项
NF                 #浏览记录的域的个数
NR                 #已读的记录数
OFS                #输出域分隔符
ORS                #输出记录分隔符
RS                 #控制记录分隔符

awk  -F ':'  '{print "filename:" FILENAME ",linenumber:" NR ",columns:" NF ",linecontent:"$0}' /etc/passwd

awk  -F ':'  '{printf("filename:%10s,linenumber:%s,columns:%s,linecontent:%s\n",FILENAME,NR,NF,$0)}' /etc/passwd

# 其中print函数的参数可以是变量、数值或者字符串。字符串必须用双引号引用，参数用逗号分隔。如果没有逗号，参数就串联在一起而无法区分。这里，逗号的作用与输出文件的分隔符的作用是一样的，只是后者是空格而已。

# printf函数，其用法和c语言中printf基本相似,可以格式化字符串,输出复杂时，printf更加好用，代码更易懂。

# awk编程
# 除了awk的内置变量，awk还可以自定义变量。
awk '{count++;print $0;} END{print "user count is ", count}' /etc/passwd

awk 'BEGIN {count=0;print "[start]user count is ", count} {count=count+1;print $0;} END{print "[end]user count is ", count}' /etc/passwd

awk -F ':' 'BEGIN {count=0;} {name[count] = $1;count++;}; END{for (i = 0; i < NR; i++) print i, name[i]}' /etc/passwd
# 因为awk中数组的下标可以是数字和字母，数组的下标通常被称为关键字(key)。值和关键字都存储在内部的一张针对key/value应用hash的表格里。由于hash不是顺序存储，因此在显示数组内容时会发现，它们并不是按照你预料的顺序显示出来的。数组和变量一样，都是在使用时自动创建的，awk也同样会自动判断其存储的是数字还是字符串。一般而言，awk中的数组用来从记录中收集信息，可以用于计算总和、统计单词以及跟踪模板被匹配的次数等等。
``` 
>参考 https://www.cnblogs.com/ggjucheng/archive/2013/01/13/2858470.html           
>https://www.cnblogs.com/emanlee/p/3327576.html         
>http://blog.sina.com.cn/s/blog_5a3640220100b7c8.html           
### 5.sed
>sed是一种流编辑器，能够完美的配合正则表达式使用。处理时，把当前处理的行存储在临时缓冲区中，称为“模式空间”（pattern space），
>接着用sed命令处理缓冲区中的内容，处理完成后，把缓冲区的内容送往屏幕。接着处理下一行，直到文件末尾。文件内容并没有 改变，
>除非你使用重定向存储输出。Sed主要用来自动编辑一个或多个文件；简化对文件的反复操作；编写转换程序等。                    
>       
```shell script
# 命令格式

sed [options] 'command' file(s)
sed [options] -f scriptfile file(s)

# sed命令的选项(option)：

# -n ：只打印模式匹配的行
# -i ：直接修改文件内容
# -e ：直接在命令行模式上进行sed动作编辑，此为默认选项
# -f ：将sed的动作写在一个文件内，用–f filename 执行filename内的sed动作
# -r ：支持扩展表达式

# sed命令
# a 在当前行下面插入文本。
# i 在当前行上面插入文本。
# c 把选定的行改为新的文本。
# d 删除，删除选择的行。
# D 删除模板块的第一行。
# s 替换指定字符。
# g 获得内存缓冲区的内容，并替代当前模板块中的文本。
# n 读取下一个输入行，用下一个命令处理新的行而不是用第一个命令。
# p 打印模板块的行。


# 选定行的范围用,（逗号）
```
>       
>参考 https://man.linuxde.net/sed         
>https://www.cnblogs.com/ctaixw/p/5860221.html          
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
### 19.xargs
### 20.df
### 21.du               