package com.example.todospringapp.config;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JasyptConfigAESTest {

    @Test
    public void checkEncrypt(){
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setProvider(new BouncyCastleProvider());
        encryptor.setPoolSize(2);
        encryptor.setPassword("MySecretKey");
        encryptor.setAlgorithm("PBEWithSHA256And128BitAES-CBC-BC");

        String plainText = "root"; // 암호화 할 내용
        String encryptedText = encryptor.encrypt(plainText); // 암호화
        String decryptedText = encryptor.decrypt(encryptedText); // 복호화
        System.out.println("Enc:"+encryptedText+", Dec:"+decryptedText);
    }
}