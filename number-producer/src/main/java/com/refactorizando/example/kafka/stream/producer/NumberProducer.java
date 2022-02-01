package com.refactorizando.example.kafka.stream.producer;

import com.refactorizando.example.kafka.stream.producer.domain.Number;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@Slf4j
@Configuration
public class NumberProducer {

  Random r = new Random();
  int lowerBound = 1;
  int upperBound = 100000000;
  
  private static long id = 0;
  LinkedList<Number> numbers = new LinkedList<>
      (List.of(new Number(++id, r.nextInt(upperBound - lowerBound) + lowerBound),
          new Number(++id, r.nextInt(upperBound - lowerBound) + lowerBound),
          new Number(++id, r.nextInt(upperBound - lowerBound) + lowerBound),
          new Number(++id, r.nextInt(upperBound - lowerBound) + lowerBound),
          new Number(++id, r.nextInt(upperBound - lowerBound) + lowerBound),
          new Number(++id, r.nextInt(upperBound - lowerBound) + lowerBound),
          new Number(++id, r.nextInt(upperBound - lowerBound) + lowerBound),
          new Number(++id, r.nextInt(upperBound - lowerBound) + lowerBound),
          new Number(++id, r.nextInt(upperBound - lowerBound) + lowerBound),
          new Number(++id, r.nextInt(upperBound - lowerBound) + lowerBound),
          new Number(++id, r.nextInt(upperBound - lowerBound) + lowerBound),
          new Number(++id, r.nextInt(upperBound - lowerBound) + lowerBound)
      ));


  @Bean
  public Supplier<Message<Number>> sendNumber() {
    return () -> {
      if (numbers.peek() != null) {
        log.info("Number to send {} ", numbers.peek().getNumber());
        Message<Number> listNumber = MessageBuilder
            .withPayload(numbers.peek())
            .setHeader(KafkaHeaders.MESSAGE_KEY, Objects.requireNonNull(numbers.poll()).getId())
            .build();
        log.info("Order event sent with number: {}", listNumber.toString());
        return listNumber;
      } else {
        return null;
      }
    };
  }

}
