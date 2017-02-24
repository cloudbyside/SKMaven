package com.ylz.exception;

import com.ylz.base.SeckillException;

/**
 * 秒杀还未开始异常
 * Created by liuburu on 2017/2/18.
 */
public class SeckillNoStartException extends SeckillException {

    public SeckillNoStartException(String message) {
        super(message);
    }

    public SeckillNoStartException(String message, Throwable cause) {
        super(message, cause);
    }
}
