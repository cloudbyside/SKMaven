package com.ylz.exception;

import com.ylz.base.SeckillException;

/**
 * 查无秒杀产品异常
 * Created by liuburu on 2017/2/18.
 */
public class NoSuchSeckillException extends SeckillException {


    public NoSuchSeckillException(String message) {
        super(message);
    }

    public NoSuchSeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
