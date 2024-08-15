package ru.dunaf.ws.productmicroservice.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.dunaf.ws.productmicroservice.service.dto.CreateProductDto;
import ru.dunaf.ws.productmicroservice.service.event.ProductCreatedEvent;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public String createdProduct(CreateProductDto createProductDtoRequest) throws ExecutionException, InterruptedException {
        //TODO save DB
        String productId = UUID.randomUUID().toString();
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(productId,
                createProductDtoRequest.getTitle(),
                createProductDtoRequest.getPrice(),
                createProductDtoRequest.getQuantity());
//        //синхронно
//        kafkaTemplate.send("product-created-events-topic", productId, productCreatedEvent);
//        //асинхронно
//        CompletableFuture<SendResult<String, ProductCreatedEvent>> future = kafkaTemplate
//                .send("product-created-events-topic", productId, productCreatedEvent);
//        //отправляет ответ, когда приходит ответ от кафки
//        future.whenComplete(((result, exception) -> {
//            if (exception != null) {
//                log.error("Failed to send message {}", exception.getMessage());
//            } else {
//                 log.info("Message send successfuly {}", result.getRecordMetadata());
//            }
//        }));
//        //сидим ждем, когда ответит брокер, а потом пойдем к логу ниже
//        future.join();
        //если будет асинхронно, то мы не сможем узнать данные топика, партиции и т.д.
        SendResult<String, ProductCreatedEvent> result = kafkaTemplate
                .send("product-created-events-topic", productId, productCreatedEvent)
                .get();
        log.info("Return: partition {}", result.getRecordMetadata().partition());
        log.info("Return: topic {}", result.getRecordMetadata().topic());
        log.info("Return: timestamp {}", result.getRecordMetadata().timestamp());

        //если сначала напечатается данный лог, а потом log.info("Message send successfuly {}", result.getRecordMetadata())б
        //то значит асинхронный запрос
        log.info("Return: {}", productId);
        return productId;
    }
}
