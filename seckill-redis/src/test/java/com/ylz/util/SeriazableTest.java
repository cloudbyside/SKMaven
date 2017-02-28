package com.ylz.util;

import org.junit.Test;

/**
 * Created by liuburu on 2017/2/27.
 */
public class SeriazableTest {
    /**
     * 1.测试默认的jDK序列化与反序列化
     * length = 93 time = 14ms
     */
    @Test
    public void testDefalutSerializable() {
        long begin = System.currentTimeMillis();
        Apple apple = new Apple(1006, "天地一号");
        byte[] appleBytes = DefaultSerializeUtil.serialize(apple);
        Object resultApple = DefaultSerializeUtil.unserialize(appleBytes);
        long end = System.currentTimeMillis();
        System.out.println("序列化字节时间："+(end-begin));
        System.out.println("序列化字节长度："+appleBytes.length);
        System.out.println("序列化字节结果："+resultApple);
        System.out.println(apple.getName());
    }

    /**
     * 2.测试谷歌序列化机制
     * length = 17 time = 114ms
     */
    @Test
    public void testGooleSerializable() {
        long begin = System.currentTimeMillis();
        Apple apple = new Apple(1006, "天地一号");
        byte[] appleBytes = GoogleSerializeUtil.serialize(apple,Apple.class);
        Object resultApple = GoogleSerializeUtil.unserialize(appleBytes,Apple.class);
        long end = System.currentTimeMillis();
        System.out.println("序列化字节时间："+(end-begin));
        System.out.println("序列化字节长度："+appleBytes.length);
        System.out.println("序列化字节结果："+resultApple);

    }
}
