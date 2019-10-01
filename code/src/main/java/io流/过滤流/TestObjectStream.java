package io流.过滤流;

import java.io.*;

/**
 * Created by 拂晓 on 2019/9/22:10:53
 */
public class TestObjectStream {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Student jj = new Student("jj", 18, 60.5);
        Student aa = new Student("aa", 18, 60.5);
        Student bb = new Student("bb", 18, 60.5);

        //输出对象
        FileOutputStream fos = new FileOutputStream("文件路径");
        ObjectOutputStream out = new ObjectOutputStream(fos);

        out.writeObject(jj);
        out.writeObject(aa);

        out.close();

        //读入对象
        FileInputStream fis = new FileInputStream("文件路径");
        ObjectInputStream in = new ObjectInputStream(fis);

        while (true) {
            Student s1 = (Student) in.readObject();
            Student s2 = (Student) in.readObject();

            in.close();
            System.out.println(s1);
            System.out.println(s2);
        }

    }
}

class Student implements Externalizable{

    String name ;
    int age;
    double score;

    public Student() {
    }

    public Student(String name, int age, double score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", score=" + score +
                '}';
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(name);
        out.writeInt(age);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
         name = in.readUTF();
         age = in.readInt();
    }
}