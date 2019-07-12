package com.enorth.dns.dnshosts.Jasypt;/*
 * @author  Administrator
 * @description:
 * @date 2019/7/12
 * @Decription:  加解密测试
 * */

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentPBEConfig;
import org.junit.Test;

public class JasyptTest {

    @Test/*加密*/
    public void testEncrypt() throws Exception{
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        EnvironmentPBEConfig config=new EnvironmentPBEConfig();

        config.setAlgorithm("PBEWithMD5AndDES");
        config.setPassword("ljk");
        standardPBEStringEncryptor.setConfig(config);
        String plainText = "root";
        String encrytedText = standardPBEStringEncryptor.encrypt(plainText);
        System.out.println(encrytedText);
    }

    @Test/*解密*/
    public void testDe() throws Exception{
       StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
       EnvironmentPBEConfig config = new EnvironmentPBEConfig();

       config.setAlgorithm("PBEWithMD5AndDES");
       config.setPassword("ljk");
       standardPBEStringEncryptor.setConfig(config);
       String encryptedText = "vEvXhHtMtKmJbsdQtTC4Pg==";
       String plainText = standardPBEStringEncryptor.decrypt(encryptedText);
       System.out.println(plainText);
    }
}
