package com.ylz.exception;

import com.ylz.base.SeckillException;

/**
 * 无库存异常
 * Created by liuburu on 2017/2/18.
 */
public class StoreEmptyException extends SeckillException {
    public StoreEmptyException(String message) {
        super(message);
    }

    public StoreEmptyException(String message, Throwable cause) {
        super(message, cause);
    }
}
