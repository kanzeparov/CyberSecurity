package ru.mipt.cybersecurity.pqc.jcajce.provider.util;

import ru.mipt.cybersecurity.asn1.ASN1Encodable;
import ru.mipt.cybersecurity.asn1.ASN1Encoding;
import ru.mipt.cybersecurity.asn1.pkcs.PrivateKeyInfo;
import ru.mipt.cybersecurity.asn1.x509.AlgorithmIdentifier;
import ru.mipt.cybersecurity.asn1.x509.SubjectPublicKeyInfo;

public class KeyUtil
{
    public static byte[] getEncodedSubjectPublicKeyInfo(AlgorithmIdentifier algId, ASN1Encodable keyData)
    {
        try
        {
            return getEncodedSubjectPublicKeyInfo(new SubjectPublicKeyInfo(algId, keyData));
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static byte[] getEncodedSubjectPublicKeyInfo(AlgorithmIdentifier algId, byte[] keyData)
    {
        try
        {
            return getEncodedSubjectPublicKeyInfo(new SubjectPublicKeyInfo(algId, keyData));
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static byte[] getEncodedSubjectPublicKeyInfo(SubjectPublicKeyInfo info)
    {
         try
         {
             return info.getEncoded(ASN1Encoding.DER);
         }
         catch (Exception e)
         {
             return null;
         }
    }

    public static byte[] getEncodedPrivateKeyInfo(AlgorithmIdentifier algId, ASN1Encodable privKey)
    {
         try
         {
             PrivateKeyInfo info = new PrivateKeyInfo(algId, privKey.toASN1Primitive());

             return getEncodedPrivateKeyInfo(info);
         }
         catch (Exception e)
         {
             return null;
         }
    }

    public static byte[] getEncodedPrivateKeyInfo(PrivateKeyInfo info)
    {
         try
         {
             return info.getEncoded(ASN1Encoding.DER);
         }
         catch (Exception e)
         {
             return null;
         }
    }
}
