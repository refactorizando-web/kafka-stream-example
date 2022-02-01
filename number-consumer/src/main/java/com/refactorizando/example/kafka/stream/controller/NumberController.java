package com.refactorizando.example.kafka.stream.controller;

import com.refactorizando.example.kafka.stream.consumer.domain.Total;
import lombok.AllArgsConstructor;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/numbers")
@AllArgsConstructor
public class NumberController {

  private final InteractiveQueryService queryService;


  @GetMapping("/all")
  public KeyValueIterator<Integer, Total> getAllTransactionsSummary() {
    ReadOnlyKeyValueStore<Integer, Total> keyValueStore =
        queryService.getQueryableStore("all-transactions-store",
            QueryableStoreTypes.keyValueStore());
    return keyValueStore.all();
  }
}
