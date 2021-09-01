package br.com.zupacademy.joao.propostas.controller.proposta.utils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.NotNull;
import java.security.*;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

public class DocumentoEncode {

    private final String chaveEncript = "1234567890123456";
    private IvParameterSpec parameterSpec;
    private Cipher cipher;
    private SecretKeySpec key;

    public DocumentoEncode() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException{
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        key = new SecretKeySpec(chaveEncript.getBytes(UTF_8), "AES");
        parameterSpec = parameterSpec = new IvParameterSpec("1234567890123456".getBytes(UTF_8));
    }

    public String encodar(@NotNull String documento) throws InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);
        byte[] documentoEncript = cipher.doFinal(documento.getBytes(UTF_8));
        String documentoEncriptToString = Base64.getEncoder().encodeToString(documentoEncript);
        return documentoEncriptToString;
    }

    public String desencodar(@NotNull String documentoCript) throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);
        String documentoPuro = new String(cipher.doFinal(Base64.getDecoder().decode(documentoCript)), UTF_8);
        return documentoPuro;
    }

    public String gerarHash(@NotNull String documento) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(documento.getBytes(UTF_8));
        byte[] documentoHash = messageDigest.digest();
        String documentoToHashString = documentoHash.toString();
        return documentoToHashString;
    }
}
