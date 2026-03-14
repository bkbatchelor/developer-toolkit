package com.developer.toolkit.nosql.idgenerator;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class UuidV7WritingConverter implements Converter<String, Binary> {
    @Override
    public Binary convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        if (source.length() != 32 || !source.matches("^[0-9a-fA-F]{32}$")) {
            throw new IllegalArgumentException("Invalid Hexadecimal UUIDv7 String");
        }
        byte[] decoded = Base16Codec.decode(source);
        return new Binary(BsonBinarySubType.UUID_STANDARD, decoded);
    }
}
