package com.developer.toolkit.nosql.idgenerator;

public class Base16Codec {

    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    public static String encode(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] decode(String hex) {
        if (hex == null || hex.isEmpty()) {
            return new byte[0];
        }
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException("Hex string must have an even length");
        }
        
        // Convert to lowercase to handle uppercase inputs consistently
        hex = hex.toLowerCase();
        
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            int high = Character.digit(hex.charAt(i), 16);
            int low = Character.digit(hex.charAt(i + 1), 16);
            if (high == -1 || low == -1) {
                throw new IllegalArgumentException("Invalid hexadecimal character found");
            }
            bytes[i / 2] = (byte) ((high << 4) + low);
        }
        return bytes;
    }
}
