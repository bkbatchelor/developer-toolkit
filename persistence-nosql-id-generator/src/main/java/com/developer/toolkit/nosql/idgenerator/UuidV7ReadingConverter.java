package com.developer.toolkit.nosql.idgenerator;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
public class UuidV7ReadingConverter implements Converter<Binary, String> {
    @Override
    public String convert(Binary source) {
        if (source.getType() == BsonBinarySubType.UUID_STANDARD.getValue()) {
            return Base16Codec.encode(source.getData());
        }
        return new String(source.getData()); // Fallback
    }
}
