package com.jiang.fzte.util;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Md5Encrypt {

    /**
     * MD5 + 盐值 + id 加密
     * @param waitingStr 原串
     * @param salt 盐值
     * @param Seed 种子
     * @return
     */
    public static String mdtEncrypt(String waitingStr, String salt, Long Seed) {
        char[] saltChars = salt.toCharArray();

        // 将 idSeed 作为种子置乱salt
        Random random = new Random();
        for (int i = 0; i < saltChars.length; ++i) {
            random.setSeed(Seed);
            int index = random.nextInt(salt.length());
            char temp = saltChars[index];
            saltChars[index] = saltChars[i];
            saltChars[i] = temp;
        }

        // 二次加密
        String once = DigestUtils.md5DigestAsHex((waitingStr + new String(saltChars)).getBytes(StandardCharsets.UTF_8));
        return DigestUtils.md5DigestAsHex(once.getBytes(StandardCharsets.UTF_8));
    }
}
