package br.com.criptografia;

import android.util.Base64;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Rafael Antonio Lucio on 02/06/16.
 */
public class CryptoUtil {
    private static final String vetorInicializacao = "1234567812345678";
    private static final String salt = "1234567812345678";
    private static final int girosChave = 2;
    private static final int tamanhoChave = 256;

    public static String Proteger(String desprotegido, String senha) throws Exception {
        byte[] bytesSalt = salt.getBytes("UTF-8");
        byte[] bytesIV = vetorInicializacao.getBytes("UTF-8");

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec especificaocaoChave = new PBEKeySpec(senha.toCharArray(), bytesSalt, girosChave, tamanhoChave);
        SecretKey chave = factory.generateSecret(especificaocaoChave);
        SecretKeySpec ferrolho = new SecretKeySpec(chave.getEncoded(), "AES");
        Cipher cifrador = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cifrador.init(Cipher.ENCRYPT_MODE, ferrolho, new IvParameterSpec(bytesIV));

        byte[] resultadoBinario = cifrador.doFinal(desprotegido.getBytes("UTF-8"));
        return Base64.encodeToString(resultadoBinario, 0);
    }

    public static String Desproteger(String protegido, String senha) throws Exception {
        byte[] bytesSalt = salt.getBytes("UTF-8");
        byte[] bytesIV = vetorInicializacao.getBytes("UTF-8");
        byte[] bytesTextoProtegido = Base64.decode(protegido, 0);

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec especificaocaoChave = new PBEKeySpec(senha.toCharArray(), bytesSalt, girosChave, tamanhoChave);
        SecretKey chave = factory.generateSecret(especificaocaoChave);
        SecretKeySpec ferrolho = new SecretKeySpec(chave.getEncoded(), "AES");
        Cipher cifrador = Cipher.getInstance("AES/CBC/PKCS5Padding");

        cifrador.init(Cipher.DECRYPT_MODE, ferrolho, new IvParameterSpec(bytesIV));

        byte[] binarioDesprotegido = cifrador.doFinal(bytesTextoProtegido);

        return new String(binarioDesprotegido);
    }
}
