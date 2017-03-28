package com.daitobedai.nocode.util;

import com.alibaba.druid.filter.config.ConfigTools;

public class DruidCipherUtil {
    public static String encrypt(String plainText) {
        try {
            return ConfigTools.encrypt(plainText);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String cipherText) {
        try {
            return ConfigTools.decrypt(cipherText);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
