### 23.如何实现数组和List之间的转换？
> 数组转List ，使用JDK中java.util.Arrays工具类的asList方法               
```java
package com.java.topic;

import java.util.Arrays;
import java.util.List;

public class ArrayToList {
    public static void main(String[] args) {
        String[] strArray = new String[]{"aaa", "bbb", "ccc"};
        List<String> list = Arrays.asList(strArray);
        for (String s : list) {
            System.out.println(s);
        }
    }
}
```
>                           
> List转数组，使用 List 的toArray方法。（无参toArray方法返回Object数组，传入初始化长度的数组对象，返回该对象数组）               
```java
package com.java.topic;

import java.util.Arrays;
import java.util.List;

public class ListToArray {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("a", "b", "cd", "dfdd");
        String[] strArray = list.toArray(new String[list.size()]);
        for (String s : strArray) {
            System.out.println(s);
        }
    }
}
```
