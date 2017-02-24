package com.ylz.exception;

import com.ylz.base.SeckillException;

/**
 * 秒杀已经结束异常
 * Created by liuburu on 2017/2/18.
 */
public class SeckillEndException extends SeckillException {
    public SeckillEndException(String message) {
        super(message);
    }

    public SeckillEndException(String message, Throwable cause) {
        super(message, cause);
    }
}
