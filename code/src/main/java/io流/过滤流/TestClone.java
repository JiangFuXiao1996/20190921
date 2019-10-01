package io流.过滤流;

import java.io.*;

/**
 * Created by 拂晓 on 2019/9/22:13:58
 */
public class TestClone {

    public static void main(String[] args)throws Exception {
        FileOutputStream fos = new FileOutputStream("文件路径");
        ObjectOutputStream out = new ObjectOutputStream(fos);

        Worker aa = new Worker("aa", 20);
        out.writeObject(aa);
        aa.age=30;
        Object clone = aa.clone();
        Worker newaa=(Worker)clone;
        out.writeObject(newaa);
        out.close();

        FileInputStream fis = new FileInputStream("文件路径");
        ObjectInputStream in = new ObjectInputStream(fis);
        Object o1 = in.readObject();
        Object o2 = in.readObject();
        System.out.println(o1);
        System.out.println(o2);
        System.out.println(o1==o2);//true  两次读入是相同的对象
        //那么如何保证对象的内容相同但他们又是不同的对象(地址不同)
        //可以调用object.clone方法

        in.close();


    }
}

class Worker implements Serializable,Cloneable{//一个对象要想clone需要实现Cloneable接口
    String name;
    int age ;

    public Worker(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    //覆盖clone方法
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}