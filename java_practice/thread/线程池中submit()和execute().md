### 13.线程池中submit()和execute()方法有什么区别？
> public interface Executor {                   
      void execute(Runnable command);               
  }                     
>                       
> ![thread_pool_submit](http://github.com/xidianlina/practice/raw/master//java_practice/topic/picture/thread_pool_submit.png)               
> submit()和 execute()接收的参数不一样           
> submit有返回值，而execute没有                 
> submit方便Exception处理。execute直接抛出异常之后线程就死掉了，submit保存异常线程没有死掉，因此execute的线程池可能会出现没有意义的情况，因为线程没有得到重用。而submit不会出现这种情况。                                                   
