package ru.mipt.cybersecurity.crypto.modes.kgcm;

public interface KGCMMultiplier
{
    void init(long[] H);
    void multiplyH(long[] z);
}
