package com.layermark.layermark_sarismet.service;

import com.google.gson.Gson;
import com.layermark.layermark_sarismet.model.CustomToken;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

@Service
public class CryptoService {
    private String key = "layermarksarismt"; // 128 bit key
    Key aesKey;
    Cipher cipher;

    public CryptoService() {
        init();
    }

    public void init() {
        try {
            this.aesKey = new SecretKeySpec(key.getBytes(), "AES");
            this.cipher = Cipher.getInstance("AES");

        } catch (Exception e) {

        }
    }

    // REFERENCE:
    // https://stackoverflow.com/questions/23561104/how-to-encrypt-and-decrypt-string-with-my-passphrase-in-java-pc-not-mobile-plat
    public String encrypt(CustomToken customToken) {
        String encryptedText = "{}";
        try {
            if (this.aesKey != null && this.cipher != null) {
                this.cipher.init(Cipher.ENCRYPT_MODE, aesKey);
                Gson gson = new Gson();
                String text = gson.toJson(customToken);
                byte[] encrypted = this.cipher.doFinal(text.getBytes());
                encryptedText = Base64.getEncoder().encodeToString(encrypted);
            }
        } catch (Exception e) {
            return encryptedText;
        }
        return encryptedText;
    }

    public CustomToken decryptText(String text) {
        CustomToken customToken = null;
        try {
            if (this.aesKey != null && this.cipher != null) {
                Gson gson = new Gson();
                cipher.init(Cipher.DECRYPT_MODE, aesKey);
                String decryptedText = new String(cipher.doFinal(Base64.getDecoder().decode(text.getBytes())));
                customToken = gson.fromJson(decryptedText, CustomToken.class);
            }
        } catch (Exception e) {
            return null;
        }
        return customToken;
    }

}
