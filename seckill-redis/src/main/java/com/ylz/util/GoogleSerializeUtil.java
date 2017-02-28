package com.ylz.util;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * Created by liuburu on 2017/2/27.
 */
public class GoogleSerializeUtil {
    /**
     * 序列化对象
     * @param object
     * @return
     */
    public static byte[] serialize(Object object,Class objClass) {
        RuntimeSchema<Object> schema = RuntimeSchema.createFrom(objClass);
        byte[] bytes = ProtostuffIOUtil.toByteArray(object, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
        return bytes;
    }

    /**
     * 反序列化对象
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes,Class objClass) {
        RuntimeSchema<Object> schema = RuntimeSchema.createFrom(objClass);
        Object object = schema.newMessage();   //空对象
        ProtostuffIOUtil.mergeFrom(bytes,object,schema);   //seckill被反序列
        return object;
    }
}
