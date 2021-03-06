package ru.mipt.cybersecurity.asn1.test;


import java.security.SecureRandom;

import ru.mipt.cybersecurity.asn1.ASN1Encodable;
import ru.mipt.cybersecurity.asn1.ASN1Integer;
import ru.mipt.cybersecurity.asn1.ASN1ObjectIdentifier;
import ru.mipt.cybersecurity.asn1.DERNull;
import ru.mipt.cybersecurity.asn1.DERSequence;
import ru.mipt.cybersecurity.asn1.cmc.CMCPublicationInfo;
import ru.mipt.cybersecurity.asn1.crmf.PKIPublicationInfo;
import ru.mipt.cybersecurity.asn1.x509.AlgorithmIdentifier;
import ru.mipt.cybersecurity.util.test.SimpleTest;

public class CMCPublicationInfoTest
    extends SimpleTest
{

    public void performTest()
        throws Exception
    {
        SecureRandom secureRandom = new SecureRandom();

        //
        // Test encode and decode.
        //

        // Not a real AlgorithmIdentifier
        AlgorithmIdentifier testIA = new AlgorithmIdentifier(new ASN1ObjectIdentifier("1.1.2.3"), DERNull.INSTANCE);
        byte[][] hashes = new byte[5][64];
        for(int i =0; i<hashes.length; i++) {
            secureRandom.nextBytes(hashes[i]);
        }

        PKIPublicationInfo pinfo = PKIPublicationInfo.getInstance(
            new DERSequence(
                new ASN1Encodable[]{new ASN1Integer(1L),  new DERSequence(new ASN1Integer(0L)) })
        );

        CMCPublicationInfo cmcPublicationInfo = new CMCPublicationInfo(testIA,hashes,pinfo);
        byte[] b = cmcPublicationInfo.getEncoded();
        CMCPublicationInfo resCmcPublicationInfo = CMCPublicationInfo.getInstance(b);

        isEquals(resCmcPublicationInfo,cmcPublicationInfo);

        //
        // Test fail on small sequence.
        //

        try
        {
            CMCPublicationInfo.getInstance(new DERSequence(new ASN1Encodable[]{testIA}));
            fail("Expecting exception.");
        } catch (Exception t) {
            isEquals("Wrong exception: "+t.getMessage(), t.getClass(), IllegalArgumentException.class);
        }

    }

    public String getName()
    {
        return "CMCPublicationInfo";
    }

    public static void main(String[] args) {
        runTest(new CMCPublicationInfoTest());
    }

}
