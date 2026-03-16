package com.developer.toolkit.nosql.idgenerator;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TestApplication {

    @Bean
    public MongoCustomConversions customConversions(List<Converter<?, ?>> converters, List<GenericConverter> genericConverters) {
        List<Object> allConverters = new ArrayList<>(converters);
        allConverters.addAll(genericConverters);
        return new MongoCustomConversions(allConverters);
    }
}
