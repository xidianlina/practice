git知识点总结
======
> https://www.cnblogs.com/xidian2014/p/10426553.html                    
> https://www.cnblogs.com/xidian2014/p/9598657.html                                              
>                            
### 1.git reset 和 git revert区别
```shell script
# git reset和git revert是进行版本回退的命令。
# 
# git reset命令是回到某次提交，提交之前的commit都会被保留，但是此次之后的修改都会被退回到暂存区
# 
# git reset --soft 回退至某个版本,只回退commit信息
# git reset --mixed 回退commit,保留源码,默认方式.
# git reset --hard 彻底回退至某个版本
# 
# 使用git rest做版本回退时，本地库的HEAD指针改变了，远程仓库的HEAD指针依然不变。
# 
# 如果直接使用git push命令无法将更改推到远程仓库。只能使用-f选项将提交强制推到远程仓库：git push -f
# 
# git revert命令是撤销某次操作，此次操作之前的commit都会被保留。
# git revert的作用通过反向做用创建一个新的版本，这个版本的内容与要回退到的目标版本一样，但是HEAD指针是指向这个新生成的版本，而不是目标版本。
# 
# git revert撤销操作后， HEAD指针是往后移动的，可以直接使用git push命令推送到远程仓库里。
# 
# reset与revert的区别:
# (1).git revert是用一次新的commit来回滚之前的commit，git reset是直接删除指定的commit。
# (2).在回滚这一操作上看，效果差不多。但是在日后继续merge以前的老版本时有区别。因为git revert是用一次逆向的commit“中和”之前的提交，因此日后合并老的branch时，导致这部分改变不会再次出现，但是git reset是把某些commit在某个branch上删除，因而和老的branch再次merge时，这些被回滚的commit应该还会被引入。
# (3).git reset 是把HEAD向后移动了一下，而git revert是HEAD继续前进，只是新的commit的内容和要revert的内容正好相反，能够抵消要被revert的内容。
```
> 参考 https://blog.csdn.net/fly910905/article/details/88635673               