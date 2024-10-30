package com.rrodrigo.kafka;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_PARTITION;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rrodrigo.model.dto.AlumnoDTO;
import com.rrodrigo.service.AlumnoService;

@Service
public class Consumer {
    
    private static final Logger log = LoggerFactory.getLogger(Consumer.class);

    @Autowired
    private AlumnoService alumnoService;

    @KafkaListener(topics = "${app.topic.notas}", containerFactory = "evaluacionListener")
    @Transactional(transactionManager = "transactionManager")
    public void evaluacion(
        @Payload AlumnoDTO payload, 
        @Header(RECEIVED_PARTITION) int partition,
        @Header(KafkaHeaders.OFFSET) int offset,
        Acknowledgment ack
        ) throws InterruptedException, ExecutionException, TimeoutException {
        this.alumnoService.saveEvaluacion(payload);
        log.info("notas received from partition-{} with offset-{}", partition, offset);
        ack.acknowledge();
    }
}
