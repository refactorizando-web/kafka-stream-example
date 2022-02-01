package com.refactorizando.example.kafka.stream.consumer;

import com.refactorizando.example.kafka.stream.consumer.domain.Num;
import com.refactorizando.example.kafka.stream.consumer.domain.Total;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

@Slf4j
@Configuration
public class OrderConsumer {

  private static final Serde<String> STRING_SERDE = Serdes.String();

  private static final Serde<Integer> INTEGER_SERDE = Serdes.Integer();

  private static final Serde<Long> LONG_SERDE = Serdes.Long();


  @Bean
  public Consumer<KStream<Long, Num>> total() {
    KeyValueBytesStoreSupplier storeSupplier = Stores.persistentKeyValueStore(
        "all-transactions-store");
    return transactions -> transactions
        .groupBy((k, v) -> v.getNumber(),
            Grouped.with(Serdes.Integer(), new JsonSerde<>(Num.class)))
        .aggregate(
            Total::new,
            (k, v, a) -> {
              a.setTotalEven((v.getNumber() % 2 == 0) ? a.getTotalEven() : a.getTotalOdd() + 1);
              a.setTotalOdd((v.getNumber() % 2 == 0) ? a.getTotalOdd() + 1 : a.getTotalOdd());
              return a;
            },
            Materialized.<Integer, Total>as(storeSupplier)
                .withKeySerde(Serdes.Integer())
                .withValueSerde(new JsonSerde<>(Total.class)))
        .toStream()
        .peek((k, v) -> log.info("Number value is {} ", v));


  }

}
