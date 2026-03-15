package com.developer.toolkit.nosql.idgenerator;

import java.nio.ByteBuffer;

public class UuidV7QueryUtils {

    private static final long BUFFER_MILLIS = 5000;

    /**
     * Extracts the millisecond timestamp from a 32-character hexadecimal UUIDv7 string.
     */
    public static long extractTimestamp(String uuidV7Hex) {
        byte[] bytes = Base16Codec.decode(uuidV7Hex);
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        // Timestamp is the first 48 bits (6 bytes)
        return buffer.getLong() >>> 16;
    }

    /**
     * Returns a timestamp minus the safe query buffer (5 seconds).
     */
    public static long getSafeQueryTimestamp(long timestamp) {
        return Math.max(0, timestamp - BUFFER_MILLIS);
    }

    /**
     * Generates a deterministic UUIDv7-formatted string with a buffered timestamp and zeroed entropy.
     * This is useful for range queries (e.g., 'find all records created since this ID').
     */
    public static String generateSafeQueryId(long originalTimestamp) {
        long safeTimestamp = getSafeQueryTimestamp(originalTimestamp);
        
        ByteBuffer buffer = ByteBuffer.allocate(16);
        
        // Write 48-bit timestamp
        buffer.putShort((short) (safeTimestamp >>> 32));
        buffer.putInt((int) (safeTimestamp & 0xFFFFFFFFL));
        
        // Zero out entropy (10 bytes)
        buffer.put(new byte[10]);
        
        byte[] id = buffer.array();
        
        // Set Version 7 (0111)
        id[6] = (byte) ((id[6] & 0x0F) | 0x70);
        
        // Set Variant (10)
        id[8] = (byte) ((id[8] & 0x3F) | 0x80);

        return Base16Codec.encode(id);
    }
}
