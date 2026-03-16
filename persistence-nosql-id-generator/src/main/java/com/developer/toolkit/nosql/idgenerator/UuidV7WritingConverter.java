package com.developer.toolkit.nosql.idgenerator;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
@WritingConverter
public class UuidV7WritingConverter implements GenericConverter {

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return new HashSet<>(Arrays.asList(
                new ConvertiblePair(String.class, Binary.class),
                new ConvertiblePair(String.class, String.class)
        ));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        System.out.println("DEBUG: UuidV7WritingConverter convert called with source=" + source + " and targetType=" + targetType.getType().getName());
        if (source == null) {
            return null;
        }
        String str = (String) source;

        // If Spring explicitly requests a Binary (e.g., during parameter binding for findById)
        if (targetType.getType() == Binary.class) {
            if (str.length() != 32 || !str.matches("^[0-9a-fA-F]{32}$")) {
                throw new IllegalArgumentException("Invalid Hexadecimal UUIDv7 String");
            }
            return new Binary(BsonBinarySubType.UUID_STANDARD, Base16Codec.decode(str));
        }

        // For save operations, if it perfectly matches a 32-character hex string, convert it to BinData
        if (str.length() == 32 && str.matches("^[0-9a-fA-F]{32}$")) {
            return new Binary(BsonBinarySubType.UUID_STANDARD, Base16Codec.decode(str));
        }

        // Otherwise, safely return the original string for fields like 'name', 'description', etc.
        return str;
    }
}
