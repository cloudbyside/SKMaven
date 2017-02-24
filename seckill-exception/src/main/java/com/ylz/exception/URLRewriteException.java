package com.ylz.exception;

import com.ylz.base.SeckillException;

/**
 * Created by liuburu on 2017/2/18.
 */
public class URLRewriteException extends SeckillException {
    public URLRewriteException(String message) {
        super(message);
    }

    public URLRewriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
