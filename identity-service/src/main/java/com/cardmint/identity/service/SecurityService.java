
package com.cardmint.identity.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

@Service
public class SecurityService {

    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final String AES_ALGORITHM = "AES/GCM/NoPadding";
    private static final String VERSION = "v1:";

    private static final int NONCE_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 128;
    private static final int KEY_LENGTH = 32;

    private final SecretKeySpec hmacKey;
    private final SecretKeySpec encryptionKey;

    private final SecureRandom secureRandom = new SecureRandom();

    public SecurityService(
            @Value("${cardmint.security.pan.hmac-key}")
            String hmacSecret,

            @Value("${cardmint.security.pan.encryption-key}")
            String encryptionSecret) {

        byte[] hmacBytes = Base64.getDecoder().decode(hmacSecret);
        byte[] encryptionBytes = Base64.getDecoder().decode(encryptionSecret);

        if (hmacBytes.length != KEY_LENGTH ||
                encryptionBytes.length != KEY_LENGTH) {
            throw new IllegalArgumentException(
                    "Security keys must be exactly 32 bytes");
        }

        this.hmacKey = new SecretKeySpec(hmacBytes, HMAC_ALGORITHM);
        this.encryptionKey = new SecretKeySpec(
                encryptionBytes, "AES");
    }

    // Normalize PAN before cryptographic operations
    private String normalizePan(String pan) {

        if (pan == null || pan.isBlank()) {
            throw new IllegalArgumentException("PAN cannot be empty");
        }

        return pan.trim()
                .replaceAll("\\s+", "")
                .toUpperCase();
    }

    // Generate deterministic HMAC-SHA256 hash
    public String hashPan(String pan) {

        try {
            String normalizedPan = normalizePan(pan);

            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(hmacKey);

            byte[] hash = mac.doFinal(
                    normalizedPan.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(hash);

        } catch (GeneralSecurityException ex) {
            throw new IllegalStateException("PAN hashing failed", ex);
        }
    }

    // Encrypt PAN using AES-256-GCM
    public String encryptPan(String pan) {

        try {
            String normalizedPan = normalizePan(pan);

            byte[] nonce = new byte[NONCE_LENGTH];
            secureRandom.nextBytes(nonce);

            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);

            GCMParameterSpec parameterSpec =
                    new GCMParameterSpec(GCM_TAG_LENGTH, nonce);

            cipher.init(Cipher.ENCRYPT_MODE, encryptionKey, parameterSpec);

            byte[] encrypted = cipher.doFinal(
                    normalizedPan.getBytes(StandardCharsets.UTF_8));

            // Store nonce + encrypted data together
            byte[] combined = new byte[nonce.length + encrypted.length];

            System.arraycopy(nonce, 0, combined, 0, nonce.length);
            System.arraycopy(encrypted, 0, combined,
                    nonce.length, encrypted.length);

            return VERSION + Base64.getEncoder()
                    .encodeToString(combined);

        } catch (GeneralSecurityException ex) {
            throw new IllegalStateException("PAN encryption failed", ex);
        }
    }

    // Decrypt PAN when authorized access is required
    public String decryptPan(String encryptedPan) {

        try {
            if (encryptedPan == null || !encryptedPan.startsWith(VERSION)) {
                throw new IllegalArgumentException(
                        "Invalid encrypted PAN format");
            }

            byte[] combined = Base64.getDecoder().decode(
                    encryptedPan.substring(VERSION.length()));

            if (combined.length <= NONCE_LENGTH) {
                throw new IllegalArgumentException(
                        "Invalid encrypted PAN data");
            }

            byte[] nonce = Arrays.copyOfRange(
                    combined, 0, NONCE_LENGTH);

            byte[] encrypted = Arrays.copyOfRange(
                    combined, NONCE_LENGTH, combined.length);

            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);

            GCMParameterSpec parameterSpec =
                    new GCMParameterSpec(GCM_TAG_LENGTH, nonce);

            cipher.init(Cipher.DECRYPT_MODE, encryptionKey, parameterSpec);

            byte[] decrypted = cipher.doFinal(encrypted);

            return new String(decrypted, StandardCharsets.UTF_8);

        } catch (GeneralSecurityException ex) {
            throw new IllegalStateException(
                    "PAN decryption failed or data was tampered with", ex);
        }
    }
}
