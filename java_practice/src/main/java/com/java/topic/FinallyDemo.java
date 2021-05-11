package com.java.topic;

/**
 * 如果catch里面有return语句，finally里面的代码还会执行吗？
 */
public class FinallyDemo {
    public static void main(String[] args) {
        //执行结果是 30
        System.out.println(getInt());

        //执行结果是 40
        System.out.println(getInt2());
    }

    public static int getInt() {
        int a = 10;
        try {
            System.out.println(a / 0);
            a = 20;
        } catch (ArithmeticException e) {
            a = 30;
            /*
             * return a 在程序执行到这一步的时候，这里不是return a 而是 return 30；这个返回路径就形成了
             * 但是呢，它发现后面还有finally，所以继续执行finally的内容，a=40
             * 再次回到以前的路径,继续走return 30，形成返回路径之后，这里的a就不是a变量了，而是常量30
             */
            return a;
        } finally {
            a = 40;
        }

        return a;
    }

    public static int getInt2() {
        int a = 10;
        try {
            System.out.println(a / 0);
            a = 20;
        } catch (ArithmeticException e) {
            a = 30;
            /*
             * return a 在程序执行到这一步的时候，这里不是return a 而是 return 30；这个返回路径就形成了
             * 但是呢，它发现后面还有finally，所以继续执行finally的内容，a=40
             * 再次回到以前的路径,继续走return 30，形成返回路径之后，这里的a就不是a变量了，而是常量30
             */
            return a;
        } finally {
            a = 40;
            //finally中有return就又重新形成了一条返回路径，由于只能通过1个return返回，所以这里直接返回40
            return a;
        }
    }
}
