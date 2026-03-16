package com.developer.toolkit.nosql.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions customConversions(List<Converter<?, ?>> converters, List<org.springframework.core.convert.converter.GenericConverter> genericConverters) {
        java.util.List<Object> allConverters = new java.util.ArrayList<>(converters);
        allConverters.addAll(genericConverters);
        return new MongoCustomConversions(allConverters);
    }

    @Component
    @WritingConverter
    public static class BigDecimalToDecimal128Converter implements Converter<BigDecimal, org.bson.types.Decimal128> {
        @Override
        public org.bson.types.Decimal128 convert(BigDecimal source) {
            return new org.bson.types.Decimal128(source);
        }
    }

    @Component
    @ReadingConverter
    public static class Decimal128ToBigDecimalConverter implements Converter<org.bson.types.Decimal128, BigDecimal> {
        @Override
        public BigDecimal convert(org.bson.types.Decimal128 source) {
            return source.bigDecimalValue();
        }
    }
}
