package com.ylz.test;

/**
 * Created by liuburu on 2017/3/7.
 */
public class TestB {

    private void f() {
        System.out.println("TestB");
    }

    public void g(){
        System.out.println("父类方法");
    }

    public static void main(String[] args) {
        TestB b = new ExtendsTestB();
   //     b.f();
        b.g();
    }
}

class ExtendsTestB extends TestB {
    public void f() {
        System.out.println("extendsTB");
    }

    @Override
    public void g() {
        super.g();
        System.out.println("子类方法");
    }
}
