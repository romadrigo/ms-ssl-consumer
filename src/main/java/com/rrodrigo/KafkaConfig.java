package com.rrodrigo;

import java.io.IOException;
import java.io.Serializable;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rrodrigo.model.dto.AlumnoDTO;

@EnableKafka
@Configuration
public class KafkaConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    
    @Bean
    ConsumerFactory<String, AlumnoDTO> consumerFactoryEvaluacion() throws IOException {
        ObjectMapper om = new ObjectMapper();
        JavaType type = om.getTypeFactory().constructType(AlumnoDTO.class);
        return new DefaultKafkaConsumerFactory<>(
            kafkaProperties.buildConsumerProperties(),
            new StringDeserializer(),
            new JsonDeserializer<AlumnoDTO>(type, om, false)
            );
    }

    @Bean("evaluacionListener")
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, AlumnoDTO>> kafkaListenerContainerFactoryEvaluacion() throws IOException {
        ConcurrentKafkaListenerContainerFactory<String, AlumnoDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryEvaluacion());
        factory.getContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE);
        factory.setCommonErrorHandler(new DefaultErrorHandler(new FixedBackOff(0L, 0L))); // to deadletter topic, 0 retries | or without deadletter this can to controle kafkalistener
        // factory.afterPropertiesSet();
        return factory;
    }

    @Bean
    ProducerFactory<String, Object> producerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties());
    }

    @Bean
    KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
