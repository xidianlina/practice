java基础题目
======
## 问题列表
## 问题答案
### 1.JDK和JRE有什么区别？
> JDK:Java Development Kit的简称，java开发工具包，提供了java的开发环境和运行环境。                         
  JRE:Java Runtime Environment的简称，java运行环境，为java的运行提供了所需环境。             
  JDK其实包含了JRE，同时还包含了编译java源码的编译器javac，还包含了很多java 程序调试和分析的工具。如果运行java程序，只需安装JRE就可以了，如果需要编写java程序，需要安装JDK。            
### 2.==和equals的区别是什么？   
> 对于基本类型和引用类型 == 的作用效果是不同的:             
  基本类型时，==比较的是值是否相同         
  引用类型时，==比较的是引用是否相同            
  代码示例:         
```java
package com.java.topic;

public class TestEquals {
    public static void main(String[] args) {
        String x = "string";
        String y = "string";
        String z = new String("string");
        System.out.println(x == y);        //true
        System.out.println(x == z);       //false
        System.out.println(x.equals(y));  //true
        System.out.println(x.equals(z));  //true
    }
}
```
> 因为 x 和 y 指向的是同一个引用，所以 == 也是 true，而 new String()方法则重写开辟了内存空间，所以 == 结果为 false，而 equals 比较的一直是值，所以结果都为 true。             
>                   
> equals本质上就是==，只不过String和Integer等重写了equals方法，把它变成了值比较。             
> 默认情况下 equals 比较一个有相同值的对象，代码如下：         
```java
package com.java.topic;

public class TestEquals {
    public static void main(String[] args) {
        Cat c1 = new Cat("cat");
        Cat c2 = new Cat("cat");
        System.out.println(c1.equals(c2)); // false
    }
}

class Cat {
    private String name;

    public Cat(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```           
equals源码如下：
```java
public class Object {
    public boolean equals(Object obj) {
            return (this == obj);
    }
}
```
equals 本质上就是 ==             
如果是两个值相同的String对象，则返回true
```java
public class TestEquals {
    public static void main(String[] args) {
        String s1 = new String("test");
        String s2 = new String("test");
        System.out.println(s1.equals(s2)); //true
    }
}
```
String的equals方法源码如下：
```java
public final class String implements java.io.Serializable, Comparable<String>, CharSequence {
    public boolean equals(Object anObject) {
        if (this == anObject) {
            return true;
        }
        if (anObject instanceof String) {
            String anotherString = (String)anObject;
            int n = value.length;
            if (n == anotherString.value.length) {
                char v1[] = value;
                char v2[] = anotherString.value;
                int i = 0;
                while (n-- != 0) {
                    if (v1[i] != v2[i])
                        return false;
                    i++;
                }
                return true;
            }
        }
        return false;
    }
}
```     
> String重写了Object的equals方法，把引用比较改成了值比较。             
>                     
> ==对于基本类型来说是值比较，对于引用类型来说是比较的是引用；而equals默认情况下是引用比较，只是很多类重新了equals方法，比如String、Integer等把它变成了值比较，所以一般情况下equals比较的是值是否相等。            