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
### 22.stat
### 23.cat
### 24.rev
### 25.paste
### 26.tail 
### 27.uptime             
### 28.                  
### 29.                  
### 30. 

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
>sort是用于对单个或多个文本文件内容或标准输出进行排序。sort命令默认以空格作为字段分隔符，将一行分割为多个关键字对文件进行排序。                 
 sort命令并不对文件内容进行实际的排序(即文件内容没有修改)，只是将文件内容按有序输出。除非你将输出重定向到文件中。        
>sort命令默认按照字典序排序               
>               
```shell script
# sort命令选项

# -r 文件内容进行逆序排序
# -n 对数值内容进行排序
# -k 以第几个列为标准进行排序
# -u 删除重复的行
# -t 指定列的分隔符，默认是空格
# -o<输出文件>   将排序后的结果存入指定的文件
```
>       
>参考 https://linux.cn/article-5372-1.html        
>https://www.cnblogs.com/hitwtx/archive/2011/12/03/2274592.html         
### 7.uniq
```shell script
# uniq命令用于报告或忽略文件中的重复行，一般与sort命令结合使用。
# 语法 uniq (选项) (参数)

# 注意:
# 1.对文本操作时，它一般会和sort命令进行组合使用，因为uniq 不会检查重复的行，除非它们是相邻的行。
# 2.对文本操作时，若域中为先空字符(通常包括空格以及制表符)，然后非空字符，域中字符前的空字符将被跳过

# 选项
# -c或——count    在每列旁边显示该行重复出现的次数
# -d或--repeated     仅显示重复出现的行
# -D    只输出重复的行，不过有几行输出几行
# -f<栏位>     忽略比较指定的栏位
# -s<字符位置>      忽略比较指定的字符
# -u或——unique       仅显示出一次的行列
# -w<字符位置N>      对每行第N个字符以后的内容不作对照

sort  uni.txt | uniq -c | sort -r
```
>           
>参考 https://man.linuxde.net/uniq       
>https://blog.csdn.net/xiaoyida11/article/details/51481499
### 8.touch
>linux的touch命令不常用，一般在使用make的时候可能会用到，用来修改文件时间戳，或者新建一个不存在的文件。         
> ![linux_touch](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/linux_touch.png)                   
### 9.chown
```shell script
# chown命令用于设置文件所有者和文件关联组的命令。

# user : 新的文件拥有者的使用者 ID
# group : 新的文件拥有者的使用者组(group)
# -c : 显示更改的部分的信息
# -f : 忽略错误信息
# -h :修复符号链接
# -v : 显示详细的处理信息
# -R : 处理指定目录以及其子目录下的所有文件
# --help : 显示辅助说明
# --version : 显示版本
```
### 10.chmod
```shell script
# chmod命令是控制用户对文件的权限的命令
# linux文件调用权限分为三级 : 文件所有者（Owner）、用户组（Group）、其它用户（Other Users）。
# rwx rwx rwx 读写可执行

# 用法 chmod [选项] [文件...]

# 文件及目录的权限范围:
# u：User，即文件或目录的拥有者；
# g：Group，即文件或目录的所属群组；
# o：Other，除了文件或目录拥有者或所属群组之外，其他用户皆属于这个范围；
# a：All，即全部的用户，包含拥有者、所属群组以及其他用户。

# 权限的代号:
# r：读取权限，数字代号为4；
# w：写入权限，数字代号为2；
# x：执行或切换权限，数字代号为1；
# -：不具任何权限，数字代号为0；
# s：当文件被执行时，根据who参数指定的用户类型设置文件的setuid或者setgid权限。

# 所有者 = rwx = 4+2+1 = 7
# 所属组 = rw- = 4+2 = 6
# 其他人 = r-x = 4+1 = 5


chmod u=rwx,go=rx .bashrc
chmod 777 .bashrc
chmod u+w .bashrc
chmod g-x .bashrc
chmod o=r .bashrc
```
### 11.ps
```shell script
# ps命令用来列出系统中当前运行的进程的状态。
# ps命令列出的是当前进程状态的快照，就是执行ps命令的那个时刻的进程的状态，如果想要动态的显示进程信息，就可以使用top命令。
# 使用该命令可以确定有哪些进程正在运行和运行的状态、进程是否结束、进程有没有僵死、哪些进程占用了过多的资源等等。

# linux上进程有5种状态: 
# 1.运行(正在运行或在运行队列中等待) 
# 2.中断(休眠中, 受阻, 在等待某个条件的形成或接受到信号) 
# 3.不可中断(收到信号不唤醒和不可运行, 进程必须等待直到有中断发生) 
# 4.僵死(进程已终止, 但进程描述符存在, 直到父进程调用wait4()系统调用后释放) 
# 5.停止(进程收到SIGSTOP, SIGSTP, SIGTIN, SIGTOU信号后停止运行运行) 

# ps工具标识进程的5种状态码: 
# D 不可中断 uninterruptible sleep (usually IO) 
# R 运行 runnable (on run queue) 
# S 中断 sleeping 
# T 停止 traced or stopped 
# Z 僵死 a defunct (”zombie”) process 

# 语法
# ps [options] [--help]

# 命令参数：
# -a 显示同一终端下的所有程序
# -A 显示所有进程
# -c  显示进程的真实名称
# -N 反向选择，低优先序的行程
# -e 等于“-A”
# -f  显示程序间的关系
# -H 显示树状结构
# -r 显示当前终端的进程
# -T  显示当前终端的所有程序
# -u  指定用户的所有进程
# -x  显示所有程序，不以终端机来区分。

# -C<命令> 列出指定命令的状况
# --lines<行数> 每页显示的行数
# --width<字符数> 每页显示的字符数
# --help 显示帮助信息
# --version 显示版本显示

# -au 显示较详细的资讯
# -aux 显示所有包含其他使用者的行程 

# ps -ef 显示所有进程信息，连同命令行
# ps -ef|grep ssh ps 与grep 常用组合用法，查找特定进程

# au(x) 输出格式: USER PID %CPU %MEM VSZ RSS TTY STAT START TIME COMMAND
# USER：该 process 属于那个使用者账号的
# PID ：该 process 的号码
# %CPU：该 process 使用掉的 CPU 资源百分比
# %MEM：该 process 所占用的物理内存百分比
# VSZ ：该 process 使用掉的虚拟内存量 (Kbytes)
# RSS ：该 process 占用的固定的内存量 (Kbytes)
# TTY ：该 process 是在那个终端机上面运作，若与终端机无关，则显示 ?，另外， tty1-tty6 是本机上面的登入者程序，若为 pts/0 等等的，则表示为由网络连接进主机的程序。
# STAT：该程序目前的状态，主要的状态有
#		R ：该程序目前正在运作，或者是可被运作
#		S ：该程序目前正在睡眠当中 (可说是 idle 状态)，但可被某些讯号 (signal) 唤醒。
#		T ：该程序目前正在侦测或者是停止了
#		Z ：该程序应该已经终止，但是其父程序却无法正常的终止他，造成 zombie (疆尸) 程序的状态
# START：该 process 被触发启动的时间
# TIME ：该 process 实际使用 CPU 运作的时间
# COMMAND：该程序的实际指令
```
>参考 https://www.cnblogs.com/peida/archive/2012/12/19/2824418.html   
### 12.top
```shell script
# top命令能够实时显示系统中各个进程的资源占用状况，类似于Windows的任务管理器。
# top是一个动态显示过程,可以通过用户按键来不断刷新当前状态。如果在前台执行该命令,它将独占前台,直到用户终止该程序为止。
# top命令提供了实时的对系统处理器的状态监视.它将显示系统中CPU最“敏感”的任务列表。该命令可以按CPU使用、内存使用和执行时间对任务进行排序。
# top命令的很多特性都可以通过交互式命令或者在个人定制文件中进行设定。
# 
# 命令格式：top [参数]
# 
# 命令参数：
# -b 批处理
# -c 显示完整的治命令
# -I 忽略失效过程
# -s 保密模式
# -S 累积模式
# -i<时间> 设置间隔时间
# -u<用户名> 指定用户名
# -p<进程号> 指定进程
# -n<次数> 循环显示的次数
```
>参考 https://www.cnblogs.com/peida/archive/2012/12/24/2831353.html
### 13.netstat
```shell script
# netstat命令用于显示与IP、TCP、UDP和ICMP协议相关的统计数据，一般用于检验本机各端口的网络连接情况。
# netstat是在内核中访问网络及相关信息的程序，它能提供TCP连接，TCP和UDP监听，进程内存管理的相关报告。
```
>参考 https://www.cnblogs.com/peida/archive/2013/03/08/2949194.html
### 14.ifconfig
```shell script
# 通常需要以root身份登录或使用sudo以便在linux机器上使用ifconfig工具。
# 依赖于ifconfig命令中使用一些选项属性，ifconfig工具不仅可以被用来简单地获取网络接口配置信息，还可以修改这些配置。
```
>参考 https://www.cnblogs.com/peida/archive/2013/02/27/2934525.html   
### 15.uptime
```shell script
# uptime命令能够打印系统总共运行了多长时间和系统的平均负载。
# uptime命令可以显示的信息显示依次为：现在时间、系统已经运行了多长时间、目前有多少登陆用户、系统在过去的1分钟、5分钟和15分钟内的平均负载。

# [root@LinServ-1 ~]# uptime
 15:31:30 up 127 days,  3:00,  1 user,  load average: 0.00, 0.00, 0.00

# 显示内容说明：

# 15:31:30             //系统当前时间
# up 127 days,  3:00   //主机已运行时间,时间越大，说明你的机器越稳定。
# 1 user               //用户连接数，是总连接数而不是用户数
# load average: 0.00, 0.00, 0.00         // 系统平均负载，统计最近1，5，15分钟的系统平均负载
```
>参考 https://man.linuxde.net/uptime          
### 16.stat
```shell
# stat命令主要用于显示文件或文件系统的详细信息
# 
# stat命令显示出来的文件其他信息：
# - File：显示文件名
# - Size：显示文件大小
# - Blocks：文件使用的数据块总数
# - IO Block：IO块大小
# - regular file：文件类型（常规文件）
# - Device：设备编号
# - Inode：Inode号
# - Links：链接数
# - Access：文件的权限
# - Gid、Uid：文件所有权的Gid和Uid。
# 
# Linux下的三个时间：
# Access Time：简写为atime，表示文件的访问时间。当文件内容被访问时，更新这个时间
# Modify Time：简写为mtime，表示文件内容的修改时间，当文件的数据内容被修改时，更新这个时间。
# Change Time：简写为ctime，表示文件的状态时间，当文件的状态被修改时，更新这个时间，例如文件的链接数，大小，权限，Blocks数。
```
>参考 https://www.cnblogs.com/klb561/p/9241228.html   
### 17.df
```shell
# df命令的功能是用来检查linux服务器的文件系统的磁盘空间占用情况。可以利用该命令来获取硬盘被占用了多少空间，目前还剩下多少空间等信息。
# 
# 命令参数：
# 必要参数：
# -a 全部文件系统列表
# -h 方便阅读方式显示
# -H 等于“-h”，但是计算式，1K=1000，而不是1K=1024
# -i 显示inode信息
# -k 区块为1024字节
# -l 只显示本地文件系统
# -m 区块为1048576字节
# --no-sync 忽略 sync 命令
# -P 输出格式为POSIX
# --sync 在取得磁盘信息前，先执行sync命令
# -T 文件系统类型
# 
# 选择参数：
# --block-size=<区块大小> 指定区块大小
# -t<文件系统类型> 只显示选定文件系统的磁盘信息
# -x<文件系统类型> 不显示选定文件系统的磁盘信息
# --help 显示帮助信息
# --version 显示版本信息
```
>参考 https://www.cnblogs.com/peida/archive/2012/12/07/2806483.html  
### 18.du 
```shell
# du命令是对文件和目录磁盘使用的空间的查看。
# 
# 命令格式：du [选项][文件]
# 
# 命令参数：
# -a或-all  显示目录中个别文件的大小。   
# -b或-bytes  显示目录或文件大小时，以byte为单位。   
# -c或--total  除了显示个别目录或文件的大小外，同时也显示所有目录或文件的总和。 
# -k或--kilobytes  以KB(1024bytes)为单位输出。
# -m或--megabytes  以MB为单位输出。   
# -s或--summarize  仅显示总计，只列出最后加总的值。
# -h或--human-readable  以K，M，G为单位，提高信息的可读性。
# -x或--one-file-xystem  以一开始处理时的文件系统为准，若遇上其它不同的文件系统目录则略过。 
# -L<符号链接>或--dereference<符号链接> 显示选项中所指定符号链接的源文件大小。   
# -S或--separate-dirs   显示个别目录的大小时，并不含其子目录的大小。 
# -X<文件>或--exclude-from=<文件>  在<文件>指定目录或文件。   
# --exclude=<目录或文件>         略过指定的目录或文件。    
# -D或--dereference-args   显示指定符号链接的源文件大小。   
# -H或--si  与-h参数相同，但是K，M，G是以1000为换算单位。   
# -l或--count-links   重复计算硬件链接的文件。
```
>参考 https://www.cnblogs.com/peida/archive/2012/12/10/2810755.html  
### 19.free
```shell
# du命令是对文件和目录磁盘使用的空间的查看。
# 
# 命令格式：du [选项][文件]
# 
# 命令参数：
# -a或-all  显示目录中个别文件的大小。   
# -b或-bytes  显示目录或文件大小时，以byte为单位。   
# -c或--total  除了显示个别目录或文件的大小外，同时也显示所有目录或文件的总和。 
# -k或--kilobytes  以KB(1024bytes)为单位输出。
# -m或--megabytes  以MB为单位输出。   
# -s或--summarize  仅显示总计，只列出最后加总的值。
# -h或--human-readable  以K，M，G为单位，提高信息的可读性。
# -x或--one-file-xystem  以一开始处理时的文件系统为准，若遇上其它不同的文件系统目录则略过。 
# -L<符号链接>或--dereference<符号链接> 显示选项中所指定符号链接的源文件大小。   
# -S或--separate-dirs   显示个别目录的大小时，并不含其子目录的大小。 
# -X<文件>或--exclude-from=<文件>  在<文件>指定目录或文件。   
# --exclude=<目录或文件>         略过指定的目录或文件。    
# -D或--dereference-args   显示指定符号链接的源文件大小。   
# -H或--si  与-h参数相同，但是K，M，G是以1000为换算单位。   
# -l或--count-links   重复计算硬件链接的文件。
```
>参考 https://www.cnblogs.com/peida/archive/2012/12/25/2831814.html  
### 20.ulimit
```shell
# ulimit命令用于限制shell启动进程所占用的资源。
# 支持以下各种类型的限制：所创建的内核文件的大小、进程数据块的大小、Shell 进程创建文件的大小、内存锁住的大小、常驻内存集的大小、打开文件描述符的数量、
# 分配堆栈的最大大小、CPU 时间、单个用户的最大线程数、Shell 进程所能使用的最大虚拟内存。同时，它支持硬资源和软资源的限制。
# 
# 命令常用参数
# -H 设置硬资源限制.
# -S 设置软资源限制.
# -a 显示当前所有的资源限制.
# -c size:设置core文件的最大值.单位:blocks
# -d size:设置数据段的最大值.单位:kbytes
# -f size:设置创建文件的最大值.单位:blocks
# -l size:设置在内存中锁定进程的最大值.单位:kbytes
# -m size:设置可以使用的常驻内存的最大值.单位:kbytes
# -n size:设置内核可以同时打开的文件描述符的最大值.单位:n
# -p size:设置管道缓冲区的最大值.单位:kbytes
# -s size:设置堆栈的最大值.单位:kbytes
# -t size:设置CPU使用时间的最大上限.单位:seconds
# -v size:设置虚拟内存的最大值.单位:kbytes
# -u <程序数目> 　用户最多可开启的程序数目
```
>参考 https://www.cnblogs.com/ftl1012/p/ulimit.html       
>https://www.cnblogs.com/wangkangluo1/archive/2012/06/06/2537677.html    
### 21.cut
```shell
# cut命令是一个选取命令，其功能是将文件中的每一行”字节” ”字符” ”字段” 进行剪切，选取需要的，并将这些选取好的数据输出至标准输出。
# 
# 命令格式：
# cut -[n]b file
# cut -c file
# cut -d[分隔符] -f[域] file
# 
# 命令参数：
# -b(bytes) ：以字节为单位进行分割。这些字节位置将忽略多字节字符边界，除非也指定了 -n 标志。
# -c(characters) ：以字符为单位进行分割。
# -d ：自定义分隔符，默认为制表符。
# -f(filed) ：与-d一起使用，指定显示哪个区域。
# -n ：取消分割多字节字符。仅和 -b 标志一起使用。如果字符的最后一个字节落在由 -b 标志的 List 参数指示的
# 范围之内，该字符将被写出；否则，该字符将被排除。
```
>参考 https://blog.csdn.net/u011003120/article/details/52190187           
### 22.tr
```shell
# tr命令从标准输入中替换、缩减或删除字符，并将结果写到标准输出。
# 使用tr时要转换两个字符串：字符串1用于查询，字符串2用于处理各种转换。tr刚执行时，字符串1中的字符被映射到字符串2中的字符，然后转换操作开始。
# 通过使用tr，可以非常容易地实现 sed 的许多最基本功能。
# 可以将 tr 看作为 sed 的（极其）简化的变体：它可以用一个字符来替换另一个字符，或者可以完全除去一些字符，也可以用它来除去重复字符。
# 
# 命令格式：
# tr -c -d -s ["string1_to_translate_from"] ["string2_to_translate_to"] < input-file
# 
# 
# 命令参数：
# -c 用字符串1中字符集的补集替换此字符集，要求字符集为ASCII。
# -d 删除字符串1中所有输入字符。
# -s 删除所有重复出现字符序列，只保留第一个；即将重复出现字符串压缩为一个字符串。
# -t 先将string1_to_translate_from的长度截为和string2_to_translate_to相等
# input-file是转换文件名。虽然可以使用其他格式输入，但这种格式最常用。
# 
# 使用实例：
# cat t.txt |tr "abc" "xyz" 	将文件file中出现的"abc"替换为"xyz"
# cat file|tr [a-z] [A-Z]		使用tr命令“统一”字母大小写
# cat file|tr -d "Snail"		删除文件file中出现的"Snail"字符
# cat file | tr -d "\n\t"		删除文件file中出现的换行'\n'、制表'\t'字符 
# echo $PATH | tr -s ":" "\n"		把路径变量中的冒号":"，替换成换行符"\n"
```
>参考 https://www.cnblogs.com/ginvip/p/6354440.html       
### 23.cat
```shell
# cat命令的用途是连接文件或标准输入并打印。这个命令常用来显示文件内容，或者将几个文件连接起来显示，或者从标准输入读取内容并显示，它常与重定向符号配合使用。 
# 
# tac命令是将cat反写过来，所以他的功能就跟cat相反， cat是由第一行到最后一行连续显示在萤幕上，而tac则是由最后一行到第一行反向在萤幕上显示出来！
# 
# 命令格式：
# cat [选项] [文件...]
# 
# 命令功能：
# cat主要有三大功能：
# 一次显示整个文件:cat filename
# 从键盘创建一个文件:cat > filename 只能创建新文件,不能编辑已有文件.
# 将几个文件合并为一个文件:cat file1 file2 > file
# 
# 命令参数：
# -b, --number-nonblank    对非空输出行编号
# -n, --number     对输出的所有行编号,由1开始对所有输出的行数编号
# 
# -v, --show-nonprinting   使用 ^ 和 M- 引用，除了 LFD 和 TAB 之外
# -E, --show-ends          在每行结束处显示 $
# -T, --show-tabs          将跳格字符显示为 ^I
# -e                       等价于 -vE
# -A, --show-all           等价于 -vET
# -t                       与 -vT 等价
# 
# -s, --squeeze-blank  有连续两行以上的空白行，就代换为一行的空白行
# 
# 使用实例：
# cat -n log2012.log log2013.log 
# cat -b log2012.log log2013.log log.log
# cat -n log2012.log > log.log
# cat >log.txt <<EOF
```
>参考 https://www.cnblogs.com/peida/archive/2012/10/30/2746968.html
### 24.rev
```shell
# rev命令将文件中的每行内容以字符为单位反序输出，即第一个字符最后输出，最后一个字符最先输出，依次类推。
```
>参考 http://lnmp.ailinux.net/rev         
### 25.head
```shell
# head用来显示开头或结尾某个数量的文字区块至标准输出中。 
# 
# 命令格式：
# head [参数]... [文件]...  
# 
# 命令功能：
# head用来显示档案的开头至标准输出中，默认head命令打印其相应文件的开头10行。 
# 
# 命令参数：
# -q 隐藏文件名
# -v 显示文件名
# 
# -c<字节> 显示字节数
# -n<行数> 显示的行数
# 
# 使用实例：
# head -n 5 log2014.log   显示文件的前5行
# head -c 20 log2014.log   显示文件前20个字节
# 
# head -c -32 log2014.log    显示文件的除了最后32个字节以外的内容 
# head -n -6 log2014.log     输出文件除了最后6行的全部内容
```
>参考 https://www.cnblogs.com/peida/archive/2012/11/06/2756278.html
### 26.tail 
```shell
# tail命令从指定点开始将文件写到标准输出.使用tail命令的-f选项可以方便的查阅正在改变的日志文件,
# tail -f filename会把filename里最尾部的内容显示在屏幕上,并且不但刷新,使你看到最新的文件内容. 
# 
# 命令格式：
# tail [参数] [文件]   
# 
# 命令参数：
# -f 循环读取
# -q 不显示处理信息
# -v 显示详细的处理信息
# -c<数目> 显示的字节数
# -n<行数> 显示行数
# --pid=PID 与-f合用,表示在进程ID,PID死掉之后结束. 
# -q, --quiet, --silent 从不输出给出文件名的首部 
# -s, --sleep-interval=S 与-f合用,表示在每次反复的间隔休眠S秒 
# 
# 使用实例：
# tail -n 5 log2014.log    显示文件末尾5行内容
# tail -f test.log     循环查看文件内容
# tail -n +5 log2014.log  	从第5行开始显示文件
```
>参考 https://www.cnblogs.com/peida/archive/2012/11/07/2758084.html           
### 27.kill  
```shell
# kill命令用来终止指定的进程的运行，是Linux下进程管理的常用命令。
# 通常，终止一个前台进程可以使用Ctrl+C键，但是，对于一个后台进程就须用kill命令来终止，
# 就需要先使用ps/pidof/pstreetop等工具获取进程PID，然后使用kill命令来杀掉该进程。
# kill命令是通过向进程发送指定的信号来结束相应进程的。
# 在默认情况下，采用编号为15的TERM信号。TERM信号将终止所有不能捕获该信号的进程。
# 对于那些可以捕获该信号的进程就要用编号为9的kill信号，强行“杀掉”该进程。 
# 
# 命令格式：
# kill [参数][进程号]
# 
# 常用的信号：
# HUP    1    终端断线
# INT    2    中断（同 Ctrl + C）
# QUIT   3    退出（同 Ctrl + \）
# TERM   15   终止
# KILL   9    强制终止
# CONT   18   继续（与STOP相反， fg/bg命令）
# STOP   19  暂停（同 Ctrl + Z）
# 
# 命令参数：
# -l  信号，若果不加信号的编号参数，则使用“-l”参数会列出全部的信号名称
# -a  当处理当前进程时，不限制命令名和进程号的对应关系
# -p  指定kill 命令只打印相关进程的进程号，而不发送任何信号
# -s  指定发送信号
# -u  指定用户 
# 
# 使用实例：
# kill -l      列出所有信号名称
# kill -l KILL    得到指定信号的数值
# kill –9 3268    彻底杀死进程
# kill -9 $(ps -ef | grep peidalinux)     kill -u peidalinux    杀死指定用户所有进程
```
>参考 https://www.cnblogs.com/peida/archive/2012/12/20/2825837.html       
### 28.rm   
```shell
# rm是常用的命令，该命令的功能为删除一个目录中的一个或多个文件或目录，它也可以将某个目录及其下的所有文件及子目录均删除。
# 对于链接文件，只是删除了链接，原有文件均保持不变。
# 
# 命令参数：
# -f, --force    忽略不存在的文件，从不给出提示。
# -i, --interactive 进行交互式删除
# -r, -R, --recursive   指示rm将参数中列出的全部目录和子目录均递归地删除。
# -v, --verbose    详细显示进行的步骤
# --help     显示此帮助信息并退出
# --version  输出版本信息并退出
```
>参考 https://www.cnblogs.com/peida/archive/2012/10/26/2740521.html
### 29.rmdir  
```shell
# rmdir命令是删除空目录，一个目录被删除之前必须是空的。
# 注意：rm - r dir命令可代替rmdir，但是有很大危险性。
# 删除某目录时也必须具有对父目录的写权限。

# -p 递归删除目录dirname，当子目录删除后其父目录为空时，也一同被删除。如果整个路径被删除或者由于某种原因保留部分路径，则系统在标准输出上显示相应的信息。 
# -v, --verbose  显示指令执行过程 
```
>参考 
### 30.ls
```shell
# rmdir命令是删除空目录，一个目录被删除之前必须是空的。
# 注意：rm - r dir命令可代替rmdir，但是有很大危险性。
# 删除某目录时也必须具有对父目录的写权限。
```
>参考 https://www.cnblogs.com/peida/archive/2012/10/23/2734829.html
### 31.cd
```shell
# cd命令切换当前目录至指定目录
# 命令格式：cd [目录名]
```
>参考 https://www.cnblogs.com/peida/archive/2012/10/24/2736501.html
### 32.pwd
```shell
# pwd命令查看”当前工作目录“的完整路径。
# 每当你在终端进行操作时，你都会有一个当前工作目录。在不太确定当前位置时，就会使用pwd来判定当前目录在文件系统内的确切位置。
# 
# 命令格式：pwd [选项]
# 常用参数：
# 一般情况下不带任何参数
# 如果目录是链接时：
# 格式：pwd -P  显示出实际路径，而非使用连接（link）路径。
```
>参考 https://www.cnblogs.com/peida/archive/2012/10/24/2737730.html
### 33.mv
```shell
# mv命令是move的缩写，可以用来移动文件或者将文件改名（move (rename) files），是Linux系统下常用的命令，经常用来备份文件或者目录。
# 
# 命令格式：
#  mv [选项] 源文件或目录 目标文件或目录
# 
# 命令功能：
# 视mv命令中第二个参数类型的不同（是目标文件还是目标目录），mv命令将文件重命名或将其移至一个新的目录中。当第二个参数类型是文件时，mv命令完成文件重命名，此时，源文件只能有一个（也可以是源目录名），它将所给的源文件或目录重命名为给定的目标文件名。当第二个参数是已存在的目录名称时，源文件或目录参数可以有多个，mv命令将各参数指定的源文件均移至目标目录中。在跨文件系统移动文件时，mv先拷贝，再将原有文件删除，而链至该文件的链接也将丢失。
# 
# 命令参数：
# -b ：若需覆盖文件，则覆盖前先行备份。 
# -f ：force 强制的意思，如果目标文件已经存在，不会询问而直接覆盖；
# -i ：若目标文件 (destination) 已经存在时，就会询问是否覆盖！
# -u ：若目标文件已经存在，且 source 比较新，才会更新(update)
# -t  ： --target-directory=DIRECTORY move all SOURCE arguments into DIRECTORY，即指定mv的目标目录，该选项适用于移动多个源文件到一个目录的情况，此时目标目录在前，源文件在后。
```
>参考 https://www.cnblogs.com/peida/archive/2012/10/27/2743022.html  
### 34.cp
```shell
# cp命令用来复制文件或者目录。
# 在命令行下复制文件时，如果目标文件已经存在，就会询问是否覆盖，不管你是否使用-i参数。
# 
# 命令功能：
# 将源文件复制至目标文件，或将多个源文件复制至目标目录。
# 
# 命令参数：
# -a, --archive 等于-dR --preserve=all --backup[=CONTROL    为每个已存在的目标文件创建备份
# -i, --interactive        覆盖前询问(使前面的 -n 选项失效)
# -n, --no-clobber   不要覆盖已存在的文件(使前面的 -i 选项失效)
# -f, --force        如果目标文件无法打开则将其移除并重试(当 -n 选项存在时则不需再选此项)
# -l, --link            链接文件而不复制
# -R, -r, --recursive  复制目录及目录内的所有项目
```
>参考 https://www.cnblogs.com/peida/archive/2012/10/29/2744185.html
### 35.xargs
```shell script
# xargs命令的作用，是将标准输入转为命令行参数。为不接受标准输入作为参数的命令提供命令行参数。
# 
# 命令格式：
# xargs [-options] [command]
# 
# 大多数情况，xargs命令都是跟管道一起使用的。但是，它也可以单独使用。xargs单独使用时，xargs后面的命令默认是echo。
# $ xargs
# # 等同于
# $ xargs echo
# 输入xargs按下回车以后，命令行就会等待用户输入，作为标准输入。你可以输入任意内容，然后按下Ctrl d，表示输入结束，这时echo命令就会把前面的输入打印出来。
# 
# 命令参数：
# -d	xargs将换行符和空格作为默认分隔符，-d参数可以更改分隔符。
# -p	参数打印出要执行的命令，询问用户是否要执行。
# -t	参数则是打印出最终要执行的命令，然后直接执行，不需要用户确认。
# -L  如果标准输入包含多行，-L参数指定多少行作为一个命令行参数。
# -n	指定每次将多少项，作为命令行参数。
# -I 如果xargs要将命令行参数传给多个命令，可以使用-I参数。-I指定每一项命令行参数的替代字符串。
# --max-procs xargs默认只用一个进程执行命令。如果命令要执行多次，必须等上一次执行完，才能执行下一次。
# --max-procs参数指定同时用多少个进程并行执行命令。--max-procs 2表示同时最多使用两个进程，--max-procs 0表示不限制进程数。
# 
# -0	表示用null当作分隔符。find命令有一个特别的参数-print0，指定输出的文件列表以null分隔。然后，xargs命令的-0参数表示用null当作分隔符。
# 
# 有些命令（比如rm）一旦参数过多会报错"参数列表过长"，而无法执行，改用xargs就没有这个问题，因为它对每个参数执行一次命令。
# find . -name "*.txt" | xargs grep "abc"
# 上面命令找出所有txt文件以后，对每个文件搜索一次是否包含字符串abc。
```
>参考 https://www.ruanyifeng.com/blog/2019/08/xargs-tutorial.html         
### 36.more
```shell
# more命令，功能类似 cat ，cat命令是整个文件的内容从上到下显示在屏幕上。 more会以一页一页的显示方便使用者逐页阅读，
# 而最基本的指令就是按空白键（space）就往下一页显示，按 b 键就会往回（back）一页显示，而且还有搜寻字串的功能 。
# more命令从前向后读取文件，因此在启动时就加载整个文件。
# 
# 命令格式：
# more [-dlfpcsu ] [-num ] [+/ pattern] [+ linenum] [file ... ] 
# 
# 命令功能：
# more命令和cat的功能一样都是查看文件里的内容，但有所不同的是more可以按页来查看文件的内容，还支持直接跳转行等功能。
# 
# 命令参数：
# +n      从笫n行开始显示
# -n       定义屏幕大小为n行
# 
# +/pattern 在每个档案显示前搜寻该字串（pattern），然后从该字串前两行之后开始显示  
# 
# -c       从顶部清屏，然后显示
# -l        忽略Ctrl+l（换页）字符
# -p       通过清除窗口而不是滚屏来对文件进行换页，与-c选项相似
# 
# -s       把连续的多个空行显示为一行
# -u       把文件内容中的下画线去掉
# 
# 常用操作命令：
# Enter    向下n行，需要定义。默认为1行
# Ctrl+F   向下滚动一屏
# 空格键  向下滚动一屏
# Ctrl+B  返回上一屏
# =       输出当前行的行号
# ：f     输出文件名和当前行的行号
# V      调用vi编辑器
# !命令   调用Shell，并执行命令 
# q       退出more
# 
# 命令实例：
# more +3 log2012.log      显示文件中从第3行起的内容
# more +/day3 log2012.log       从文件中查找第一个出现"day3"字符串的行，并从该处前两行开始显示输出 
# more -5 log2012.log          设定每屏显示行数
```
>参考 https://www.cnblogs.com/peida/archive/2012/11/02/2750588.html 
### 37.less
```shell
# less工具也是对文件或其它输出进行分页显示的工具，应该说是linux正统查看文件内容的工具，功能极其强大。less 的用法比起 more 更加的有弹性。
# 在 more 的时候，并没有办法向前面翻， 只能往后面看，但若使用了 less 时，就可以使用 [pageup] [pagedown] 等按键的功能来往前往后翻看文件，
# 更容易用来查看一个文件的内容！除此之外，在 less 里头可以拥有更多的搜索功能，不止可以向下搜，也可以向上搜。
# 
# 命令格式：
# less [参数]  文件 
# 
# 命令功能：
# less 与 more 类似，但使用 less 可以随意浏览文件，而 more 仅能向前移动，却不能向后移动，而且 less 在查看之前不会加载整个文件。
# 
# 命令参数：
# -b <缓冲区大小> 设置缓冲区的大小
# -e  当文件显示结束后，自动离开
# -f  强迫打开特殊文件，例如外围设备代号、目录和二进制文件
# -g  只标志最后搜索的关键词
# -i  忽略搜索时的大小写
# -m  显示类似more命令的百分比
# -N  显示每行的行号
# -o <文件名> 将less 输出的内容在指定文件中保存起来
# -s  显示连续空行为一行
# -S  行过长时间将超出部分舍弃
# 
# -x <数字> 将“tab”键显示为规定的数字空格
# 
# /字符串：向下搜索“字符串”的功能
# ?字符串：向上搜索“字符串”的功能
# 
# n：重复前一个搜索（与 / 或 ? 有关）
# N：反向重复前一个搜索（与 / 或 ? 有关）
# 
# b  向后翻一页
# d  向后翻半页
# 
# h  显示帮助界面
# 
# Q  退出less 命令
# 
# u  向前滚动半页
# y  向前滚动一行
# 空格键 滚动一行
# 回车键 滚动一页
# [pagedown]： 向下翻动一页
# [pageup]：   向上翻动一页
```
>参考 https://www.cnblogs.com/peida/archive/2012/11/05/2754477.html
### 38.paste
```shell
# paste命令用于合并文件的列。paste指令会把每个文件以列对列的方式，一列列地加以合并。
# 
# 命令格式：
# paste [-s][-d <间隔字符>][--help][--version][文件...]
# 
# 命令参数：
# -d, --delimiters=列表   改用指定列表里的字符替代制表分隔符
# -s, --serial              不使用平行的行目输出模式，而是每个文件占用一行
# 
# 使用实例：
# paste -s file    将一个文件中的多行数据合并为一行进行显示。将文件"file"中的3行数据合并为一行数据进行显示
# paste file1 file2 file3      将文件"file1"、"file2"、"file3"进行合并
```
>参考 https://www.cnblogs.com/jkin/p/10274731.html
### 39.which
```shell
# which命令的作用是，在PATH变量指定的路径中，搜索某个系统命令的位置，并且返回第一个搜索结果。
# 使用which命令，可以看到某个系统命令是否存在，以及执行的到底是哪一个位置的命令。 
# 
# 命令格式：
# which 可执行文件名称 
# 
# 命令参数：
# -n 　指定文件名长度，指定的长度必须大于或等于所有文件中最长的文件名。
# -p 　与-n参数相同，但此处的包括了文件的路径。
# -w 　指定输出时栏位的宽度。
# -V 　显示版本信息
```
>参考 https://www.cnblogs.com/peida/archive/2012/11/08/2759805.html
### 40.whereis
```shell
# whereis命令是定位可执行文件、源代码文件、帮助文件在文件系统中的位置。
# 这些文件的属性应属于原始代码，二进制文件，或是帮助文件。
# whereis 程序还具有搜索源代码、指定备用搜索路径和搜索不寻常项的能力。
# 
# 和find相比，whereis查找的速度非常快，这是因为linux系统会将 系统内的所有文件都记录在一个数据库文件中，
# 当使用whereis和locate时，会从数据库中查找数据，而不是像find命令那样，通过遍历硬盘来查找，效率自然会很高。 
# 数据库文件并不是实时更新，默认情况下时一星期更新一次，因此，在用whereis和locate查找文件时，
# 有时会找到已经被删除的数据，或者刚刚建立文件，却无法查找到，原因就是因为数据库文件没有被更新。 
# 
# 命令参数：
# -b   定位可执行文件。
# -m   定位帮助文件。
# -s   定位源代码文件。
# -u   搜索默认路径下除可执行文件、源代码文件、帮助文件以外的其它文件。
# -B   指定搜索可执行文件的路径。
# -M   指定搜索帮助文件的路径。
# -S   指定搜索源代码文件的路径。
```
>参考 https://www.cnblogs.com/peida/archive/2012/11/09/2761928.html           
### 41.ln
```shell

```
>参考
### 42.tar
```shell

```
>参考
### 43.wget
```shell 

```
>参考
### 44.scp
```shell

```
>参考
### 45.crontab
```shell

```
>参考 