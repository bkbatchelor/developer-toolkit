package com.developer.toolkit.nosql.idgenerator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Base16CodecTest {

    @Test
    void shouldEncodeBytesToHex() {
        byte[] bytes = new byte[]{0x00, (byte) 0xFF, 0x12, 0x34, (byte) 0xAB, (byte) 0xCD};
        String hex = Base16Codec.encode(bytes);
        assertThat(hex).isEqualTo("00ff1234abcd");
    }

    @Test
    void shouldDecodeHexToBytes() {
        String hex = "00ff1234abcd";
        byte[] bytes = Base16Codec.decode(hex);
        assertThat(bytes).containsExactly(0x00, (byte) 0xFF, 0x12, 0x34, (byte) 0xAB, (byte) 0xCD);
    }

    @Test
    void shouldDecodeUppercaseHexToBytes() {
        String hex = "00FF1234ABCD";
        byte[] bytes = Base16Codec.decode(hex);
        assertThat(bytes).containsExactly(0x00, (byte) 0xFF, 0x12, 0x34, (byte) 0xAB, (byte) 0xCD);
    }

    @Test
    void shouldThrowExceptionWhenDecodingOddLengthString() {
        assertThatThrownBy(() -> Base16Codec.decode("123"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("length");
    }

    @Test
    void shouldThrowExceptionWhenDecodingInvalidCharacters() {
        assertThatThrownBy(() -> Base16Codec.decode("123g"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldReturnEmptyStringForEmptyByteArray() {
        assertThat(Base16Codec.encode(new byte[0])).isEmpty();
    }

    @Test
    void shouldReturnEmptyByteArrayForEmptyString() {
        assertThat(Base16Codec.decode("")).isEmpty();
    }
}
