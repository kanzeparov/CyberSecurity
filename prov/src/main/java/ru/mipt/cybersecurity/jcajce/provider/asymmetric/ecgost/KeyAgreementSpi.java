package ru.mipt.cybersecurity.jcajce.provider.asymmetric.ecgost;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import ru.mipt.cybersecurity.asn1.x9.X9IntegerConverter;
import ru.mipt.cybersecurity.crypto.CipherParameters;
import ru.mipt.cybersecurity.crypto.DerivationFunction;
import ru.mipt.cybersecurity.crypto.agreement.ECVKOAgreement;
import ru.mipt.cybersecurity.crypto.digests.GOST3411Digest;
import ru.mipt.cybersecurity.crypto.params.AsymmetricKeyParameter;
import ru.mipt.cybersecurity.crypto.params.ECDomainParameters;
import ru.mipt.cybersecurity.crypto.params.ECPrivateKeyParameters;
import ru.mipt.cybersecurity.crypto.params.ParametersWithUKM;
import ru.mipt.cybersecurity.jcajce.provider.asymmetric.ec.BCECPublicKey;
import ru.mipt.cybersecurity.jcajce.provider.asymmetric.util.BaseAgreementSpi;
import ru.mipt.cybersecurity.jcajce.provider.asymmetric.util.ECUtil;
import ru.mipt.cybersecurity.jcajce.spec.UserKeyingMaterialSpec;
import ru.mipt.cybersecurity.jce.interfaces.ECPrivateKey;
import ru.mipt.cybersecurity.jce.interfaces.ECPublicKey;

public class KeyAgreementSpi
    extends BaseAgreementSpi
{
    private static final X9IntegerConverter converter = new X9IntegerConverter();

    private String                 kaAlgorithm;

    private ECDomainParameters     parameters;
    private ECVKOAgreement agreement;

    private byte[]             result;

    protected KeyAgreementSpi(
        String kaAlgorithm,
        ECVKOAgreement agreement,
        DerivationFunction kdf)
    {
        super(kaAlgorithm, kdf);

        this.kaAlgorithm = kaAlgorithm;
        this.agreement = agreement;
    }

    protected Key engineDoPhase(
        Key     key,
        boolean lastPhase) 
        throws InvalidKeyException, IllegalStateException
    {
        if (parameters == null)
        {
            throw new IllegalStateException(kaAlgorithm + " not initialised.");
        }

        if (!lastPhase)
        {
            throw new IllegalStateException(kaAlgorithm + " can only be between two parties.");
        }

        CipherParameters pubKey;
        {
            if (!(key instanceof PublicKey))
            {
                throw new InvalidKeyException(kaAlgorithm + " key agreement requires "
                    + getSimpleName(ECPublicKey.class) + " for doPhase");
            }

            pubKey = generatePublicKeyParameter((PublicKey)key);
        }

        try
        {
            result = agreement.calculateAgreement(pubKey);
        }
        catch (final Exception e)
        {
            throw new InvalidKeyException("calculation failed: " + e.getMessage())
            {
                public Throwable getCause()
                            {
                                return e;
                            }
            };
        }

        return null;
    }

    protected void engineInit(
        Key                     key,
        AlgorithmParameterSpec  params,
        SecureRandom            random) 
        throws InvalidKeyException, InvalidAlgorithmParameterException
    {
        if (params != null && !(params instanceof UserKeyingMaterialSpec))
        {
            throw new InvalidAlgorithmParameterException("No algorithm parameters supported");
        }

        initFromKey(key, params);
    }

    protected void engineInit(
        Key             key,
        SecureRandom    random) 
        throws InvalidKeyException
    {
        initFromKey(key, null);
    }

    private void initFromKey(Key key, AlgorithmParameterSpec parameterSpec)
        throws InvalidKeyException
    {
        {
            if (!(key instanceof PrivateKey))
            {
                throw new InvalidKeyException(kaAlgorithm + " key agreement requires "
                    + getSimpleName(ECPrivateKey.class) + " for initialisation");
            }

            ECPrivateKeyParameters privKey = (ECPrivateKeyParameters)ECUtil.generatePrivateKeyParameter((PrivateKey)key);
            this.parameters = privKey.getParameters();
            ukmParameters = (parameterSpec instanceof UserKeyingMaterialSpec) ? ((UserKeyingMaterialSpec)parameterSpec).getUserKeyingMaterial() : null;
            agreement.init(new ParametersWithUKM(privKey, ukmParameters));
        }
    }

    private static String getSimpleName(Class clazz)
    {
        String fullName = clazz.getName();

        return fullName.substring(fullName.lastIndexOf('.') + 1);
    }

    protected byte[] calcSecret()
    {
        return result;
    }

    static AsymmetricKeyParameter generatePublicKeyParameter(
            PublicKey key)
        throws InvalidKeyException
    {
        return (key instanceof BCECPublicKey) ? ((BCECGOST3410PublicKey)key).engineGetKeyParameters() : ECUtil.generatePublicKeyParameter(key);
    }

    public static class ECVKO
        extends KeyAgreementSpi
    {
        public ECVKO()
        {
            super("ECGOST3410", new ECVKOAgreement(new GOST3411Digest()), null);
        }
    }
}
