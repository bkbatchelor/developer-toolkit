package com.developer.toolkit.nosql.idgenerator;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.time.Instant;

public class UuidV7Generator {

    private static final SecureRandom RANDOM = new SecureRandom();

    public static byte[] generate() {
        long timestamp = Instant.now().toEpochMilli();
        byte[] entropy = new byte[10];
        RANDOM.nextBytes(entropy);

        ByteBuffer buffer = ByteBuffer.allocate(16);
        
        // Write 48-bit timestamp. A long is 64 bits. We take the lowest 48 bits.
        buffer.putShort((short) (timestamp >>> 32));
        buffer.putInt((int) (timestamp & 0xFFFFFFFFL));
        
        // Write 80-bit entropy
        buffer.put(entropy);
        
        byte[] id = buffer.array();
        
        // Set Version 7 (0111) in bits 48-51
        id[6] = (byte) ((id[6] & 0x0F) | 0x70);
        
        // Set Variant (10) in bits 64-65
        id[8] = (byte) ((id[8] & 0x3F) | 0x80);

        return id;
    }
}
