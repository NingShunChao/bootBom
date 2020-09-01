package com.superman.common.utils;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

import org.activiti.engine.impl.cfg.IdGenerator;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * 封装各种生成唯一性ID算法的工具类.
 *
 * @version 2013-01-15
 */
public class IdGen implements IdGenerator, SessionIdGenerator {

    private static SecureRandom random = new SecureRandom();

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


    /**
     * 每天random出同一个随机数
     */
    public static String getRandomLetter() {
        StringBuffer sb = new StringBuffer();
        Calendar now = Calendar.getInstance();
        int i1 = now.get(Calendar.YEAR) + now.get(Calendar.MONTH) + now.get(Calendar.DAY_OF_MONTH);
        Random random = new Random(i1);
        for (int i = 0; i < 10; i++) {
            long round = Math.round(random.nextDouble() * 25 + 65);
            sb.append(String.valueOf((char) round));
        }
        return String.valueOf(sb);
    }

    /**
     * 使用SecureRandom随机生成Long.
     */
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }

    /**
     * 基于Base62编码的SecureRandom随机生成bytes.
     */
    public static String randomBase62(int length) {
        byte[] randomBytes = new byte[length];
        random.nextBytes(randomBytes);
        return Encodes.encodeBase62(randomBytes);
    }

    /**
     * Activiti ID 生成
     */
    @Override
    public String getNextId() {
        return IdGen.uuid();
    }

    @Override
    public Serializable generateId(Session session) {
        return IdGen.uuid();
    }

    public static void main(String[] args) {
        System.out.println(IdGen.uuid());
        System.out.println(IdGen.uuid().length());
        System.out.println(new IdGen().getNextId());
        for (int i = 0; i < 1000; i++) {
            System.out.println(IdGen.randomLong() + "  " + IdGen.randomBase62(5));
        }
    }

}
