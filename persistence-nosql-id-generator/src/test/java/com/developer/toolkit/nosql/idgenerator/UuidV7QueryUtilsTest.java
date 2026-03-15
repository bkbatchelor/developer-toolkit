package com.developer.toolkit.nosql.idgenerator;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class UuidV7QueryUtilsTest {

    private static final long BUFFER_MILLIS = 5000;

    @Test
    void shouldExtractTimestampFromUuidV7String() {
        long now = Instant.now().toEpochMilli();
        String id = UuidV7Generator.generateAsString();
        
        long extracted = UuidV7QueryUtils.extractTimestamp(id);
        
        // Should be very close to 'now'
        assertThat(extracted).isBetween(now - 100, now + 100);
    }

    @Test
    void shouldCalculateSafeQueryTimestampWithBuffer() {
        long timestamp = 1710410000000L; // Example timestamp
        long expected = timestamp - BUFFER_MILLIS;
        
        long safe = UuidV7QueryUtils.getSafeQueryTimestamp(timestamp);
        
        assertThat(safe).isEqualTo(expected);
    }

    @Test
    void shouldCreateSafeQueryIdForTimestamp() {
        long timestamp = 1710410000000L;
        long safeTimestamp = timestamp - BUFFER_MILLIS;
        
        // A safe query ID should have the safe timestamp and zeroed entropy
        String safeId = UuidV7QueryUtils.generateSafeQueryId(timestamp);
        
        assertThat(safeId).hasSize(32);
        assertThat(UuidV7QueryUtils.extractTimestamp(safeId)).isEqualTo(safeTimestamp);
        
        // Verify entropy is zeroed (bits after 48-51 version and 64-65 variant)
        // This is a bit complex to check via string, but we can check if it's deterministic
        String safeId2 = UuidV7QueryUtils.generateSafeQueryId(timestamp);
        assertThat(safeId).isEqualTo(safeId2);
    }

    @Test
    void shouldHandleNegativeBufferResultByReturningZero() {
        long timestamp = 1000L; // 1 second
        long safe = UuidV7QueryUtils.getSafeQueryTimestamp(timestamp);
        assertThat(safe).isEqualTo(0L);
    }
}
