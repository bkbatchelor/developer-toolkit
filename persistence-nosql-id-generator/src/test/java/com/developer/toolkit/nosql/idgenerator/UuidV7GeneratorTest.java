package com.developer.toolkit.nosql.idgenerator;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

class UuidV7GeneratorTest {

    @Test
    void shouldGenerate16ByteId() {
        byte[] id = UuidV7Generator.generate();
        assertThat(id).hasSize(16);
    }

    @Test
    void shouldContainCurrentTimestampInFirst48Bits() {
        long before = Instant.now().toEpochMilli();
        byte[] id = UuidV7Generator.generate();
        long after = Instant.now().toEpochMilli();

        ByteBuffer buffer = ByteBuffer.wrap(id);
        long timestamp = buffer.getLong() >>> 16; // Top 48 bits

        assertThat(timestamp).isBetween(before, after);
    }

    @Test
    void shouldBeTimeOrdered() throws InterruptedException {
        byte[] id1 = UuidV7Generator.generate();
        Thread.sleep(2); // Ensure clock ticks
        byte[] id2 = UuidV7Generator.generate();

        ByteBuffer buffer1 = ByteBuffer.wrap(id1);
        long timestamp1 = buffer1.getLong() >>> 16;

        ByteBuffer buffer2 = ByteBuffer.wrap(id2);
        long timestamp2 = buffer2.getLong() >>> 16;

        assertThat(timestamp2).isGreaterThan(timestamp1);
    }

    @Test
    void shouldBeUniqueWhenGeneratedInTheSameMillisecond() {
        byte[] id1 = UuidV7Generator.generate();
        byte[] id2 = UuidV7Generator.generate();
        
        assertThat(id1).isNotEqualTo(id2);
    }

    @Test
    void shouldBeThreadSafeUnderConcurrentLoad() throws InterruptedException {
        int threadCount = 100;
        int idsPerThread = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        Set<String> generatedIds = ConcurrentHashMap.newKeySet();

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                for (int j = 0; j < idsPerThread; j++) {
                    byte[] id = UuidV7Generator.generate();
                    StringBuilder sb = new StringBuilder();
                    for (byte b : id) {
                        sb.append(String.format("%02x", b));
                    }
                    generatedIds.add(sb.toString());
                }
                latch.countDown();
            });
        }

        latch.await();
        executorService.shutdown();

        assertThat(generatedIds).hasSize(threadCount * idsPerThread);
    }

    @Test
    void shouldGenerate32CharacterHexId() {
        String idString = UuidV7Generator.generateAsString();
        assertThat(idString).hasSize(32);
        assertThat(idString).matches("^[0-9a-f]{32}$");
    }
}
