package ru.mipt.cybersecurity.interf;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class AbstractSymmetricCipher extends EngineCipher{

    protected AbstractSymmetricCipher(String algorithmName) {
        super(algorithmName);
    }

    /**
     * Generates a new {@link java.security.Key Key} suitable for this CipherService's {@link #getAlgorithmName() algorithm}
     * by calling {@link #generateNewKey(int) generateNewKey(128)} (uses a 128 bit size by default).
     *
     * @return a new {@link java.security.Key Key}, 128 bits in length.
     */
    public Key generateNewKey() {
        return generateNewKey(getKeySize());
    }

    /**
     * Generates a new {@link Key Key} of the specified size suitable for this CipherService
     * (based on the {@link #getAlgorithmName() algorithmName} using the JDK {@link javax.crypto.KeyGenerator KeyGenerator}.
     *
     * @param keyBitSize the bit size of the key to create
     * @return the created key suitable for use with this CipherService
     */
    public Key generateNewKey(int keyBitSize) {
        KeyGenerator kg;
        try {
            kg = KeyGenerator.getInstance(getAlgorithmName());
        } catch (NoSuchAlgorithmException e) {
            String msg = "Unable to acquire " + getAlgorithmName() + " algorithm.  This is required to function.";
            throw new IllegalStateException(msg, e);
        }
        kg.init(keyBitSize);
        return kg.generateKey();
    }
}
