
package com.project.pk48.inventoryms_springboot_api.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@ConfigurationProperties(prefix = "rsa") // <--- Only this annotation!
public class RsaKeyProperties {
    private Resource publicKey;
    private Resource privateKey;

    private RSAPublicKey parsedPublicKey;
    private RSAPrivateKey parsedPrivateKey;

    public RSAPublicKey getPublicKey() {
        if (parsedPublicKey == null) {
            parsedPublicKey = parseRsaPublicKeyFromPem(publicKey);
        }
        return parsedPublicKey;
    }

    public RSAPrivateKey getPrivateKey() {
        if (parsedPrivateKey == null) {
            parsedPrivateKey = parseRsaPrivateKeyFromPem(privateKey);
        }
        return parsedPrivateKey;
    }

    public void setPublicKey(Resource publicKey) { this.publicKey = publicKey; }
    public void setPrivateKey(Resource privateKey) { this.privateKey = privateKey; }

    private RSAPublicKey parseRsaPublicKeyFromPem(Resource resource) {
        try (InputStream is = resource.getInputStream()) {
            String key = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            String publicKeyPEM = key.replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");
            byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) kf.generatePublic(keySpec);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load RSA public key", e);
        }
    }

    private RSAPrivateKey parseRsaPrivateKeyFromPem(Resource resource) {
        try (InputStream is = resource.getInputStream()) {
            String key = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            String privateKeyPEM = key.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");
            byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) kf.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load RSA private key", e);
        }
    }
}





/*

package com.project.pk48.inventoryms_springboot_api.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "rsa")
public record RsaKeyProperties(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
}*/