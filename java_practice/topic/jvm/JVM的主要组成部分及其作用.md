### 2.JVM的主要组成部分？及其作用？
>类加载器（ClassLoader）                  
 运行时数据区（Runtime Data Area）                  
 执行引擎（Execution Engine）                     
 本地库接口（Native Interface）            
>组件的作用:首先通过类加载器（ClassLoader）会把Java代码转换成字节码，运行时数据区（Runtime Data Area）再把字节码加载到内存中，
>而字节码文件只是JVM的一套指令集规范，并不能直接交个底层操作系统去执行，因此需要特定的命令解析器执行引擎（Execution Engine）
>将字节码翻译成底层系统指令再交由CPU去执行，而这个过程中需要调用其他语言的本地库接口（Native Interface）来实现整个程序的功能。                      
> ![jvm_component](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/jvm_component.png)                                                  
