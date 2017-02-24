package com.ylz.service.util;

import org.junit.Test;
import org.springframework.util.DigestUtils;

/**
 * Created by liuburu on 2017/2/17.
 */
public class MD5Utils {

    private static String salt = "^&^((*&*(&$#*)(&$#()&$*()&$)";//盐值

    /**
     * 对ID数字进行加密
     * @param source
     * @return
     */
    public static String toMD5Code(int source){
        String base = source+"/"+salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

}
