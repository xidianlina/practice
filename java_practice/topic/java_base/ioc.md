### 49.ioc
>IOC，控制反转,也称为依赖注入，是一种设计思想。传统应用程序是程序在类内部主动创建依赖对象，程序自己去new自己需要的对象，使类与类之间的耦合度高、复用性底。               
>IOC的设计思想是专门有一个容器负责创建对象，把创建对象的控制权交给了容器，由容器创建对象，注入到程序中，从而使对象与对象之间耦合度降低、复用性提高。                
>之所以又称为依赖注入，是从容器的角度出发的，组件之间依赖关系由容器在运行期决定，容器动态的将依赖关系注入到组件之中。                     
>依赖注入的目的是为了提高组件复用性。通过依赖注入机制，只需要通过配置，无需任何代码就可指定目标需要的资源，完成自身的业务逻辑。                
>IOC可以降低耦合度、提高复用性的同时也是有缺点的。IOC容器生成对象是通过反射方式，在运行效率上有一定的损耗。需要进行大量的配制工作，比较繁琐。                          
