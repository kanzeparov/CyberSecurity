package ru.mipt.cybersecurity.jcajce.provider.asymmetric.ec;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import ru.mipt.cybersecurity.crypto.*;
import ru.mipt.cybersecurity.crypto.CipherParameters;
import ru.mipt.cybersecurity.crypto.params.ParametersWithID;
import ru.mipt.cybersecurity.crypto.params.ParametersWithRandom;
import ru.mipt.cybersecurity.crypto.signers.SM2Signer;
import ru.mipt.cybersecurity.jcajce.provider.asymmetric.util.ECUtil;
import ru.mipt.cybersecurity.jcajce.spec.SM2ParameterSpec;
import ru.mipt.cybersecurity.jcajce.util.BCJcaJceHelper;
import ru.mipt.cybersecurity.jcajce.util.JcaJceHelper;

public class GMSignatureSpi
    extends java.security.SignatureSpi
{
    private final JcaJceHelper helper = new BCJcaJceHelper();

    private AlgorithmParameters engineParams;
    private SM2ParameterSpec paramSpec;

    private final SM2Signer signer;

    GMSignatureSpi(SM2Signer signer)
    {
        this.signer = signer;
    }

    protected void engineInitVerify(PublicKey publicKey)
        throws InvalidKeyException
    {
        CipherParameters param = ECUtils.generatePublicKeyParameter(publicKey);

        if (paramSpec != null)
        {
            param = new ParametersWithID(param, paramSpec.getID());
        }

        signer.init(false, param);
    }

    protected void engineInitSign(
        PrivateKey privateKey)
        throws InvalidKeyException
    {
        CipherParameters param = ECUtil.generatePrivateKeyParameter(privateKey);

        if (appRandom != null)
        {
            param = new ParametersWithRandom(param, appRandom);
        }

        if (paramSpec != null)
        {
            signer.init(true, new ParametersWithID(param, paramSpec.getID()));
        }
        else
        {
            signer.init(true, param);
        }
    }

    protected void engineUpdate(byte b)
        throws SignatureException
    {
        signer.update(b);
    }

    protected void engineUpdate(byte[] bytes, int off, int length)
        throws SignatureException
    {
        signer.update(bytes, off, length);
    }

    protected byte[] engineSign()
        throws SignatureException
    {
        try
        {
            return signer.generateSignature();
        }
        catch (CryptoException e)
        {
            throw new SignatureException("unable to create signature: " + e.getMessage());
        }
    }

    protected boolean engineVerify(byte[] bytes)
        throws SignatureException
    {
        return signer.verifySignature(bytes);
    }

    protected void engineSetParameter(
        AlgorithmParameterSpec params)
        throws InvalidAlgorithmParameterException
    {
        if (params instanceof SM2ParameterSpec)
        {
            paramSpec = (SM2ParameterSpec)params;
        }
        else
        {
            throw new InvalidAlgorithmParameterException("only SM2ParameterSpec supported");
        }
    }

    protected AlgorithmParameters engineGetParameters()
    {
        if (engineParams == null)
        {
            if (paramSpec != null)
            {
                try
                {
                    engineParams = helper.createAlgorithmParameters("PSS");
                    engineParams.init(paramSpec);
                }
                catch (Exception e)
                {
                    throw new RuntimeException(e.toString());
                }
            }
        }

        return engineParams;
    }

    protected void engineSetParameter(
        String param,
        Object value)
    {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    protected Object engineGetParameter(
        String param)
    {
        throw new UnsupportedOperationException("engineGetParameter unsupported");
    }

    static public class sm3WithSM2
        extends GMSignatureSpi
    {
        public sm3WithSM2()
        {
            super(new SM2Signer());
        }
    }
}