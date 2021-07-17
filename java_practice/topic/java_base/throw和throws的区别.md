### 36.throw和throws的区别？
> throws是声明方法可能抛出异常,用在声明方法的时候，表示该方法可能要抛出异常                  
  语法：修饰符 返回值类型 方法名  参数类型  throws异常类             
  public void test throws Exception1,Exception2(){}             
  throw用于抛出异常对象，后面跟的是异常对象。是语句抛出一个异常。多用在函数内。              
  语法：throw(异常对象)            
        throw e;                
>                   
> 区别:               
  (1).throws用于方法声明上;throw用于方法内部             
  (2).throws后跟异常类型;throw后跟异常对象                  
  (3).throws后可以一次声明多种异常类型;throw后只能跟一个异常对象                   
