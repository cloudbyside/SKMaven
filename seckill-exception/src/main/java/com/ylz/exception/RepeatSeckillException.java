package com.ylz.exception;

import com.ylz.base.SeckillException;

/**
 * 重复秒杀异常
 * Created by liuburu on 2017/2/18.
 */
public class RepeatSeckillException extends SeckillException {
    public RepeatSeckillException(String message) {
        super(message);
    }

    public RepeatSeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
