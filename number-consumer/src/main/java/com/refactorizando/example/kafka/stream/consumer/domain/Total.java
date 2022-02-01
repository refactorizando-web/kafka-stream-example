package com.refactorizando.example.kafka.stream.consumer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Total {

  private Long totalOdd = 0L;

  private Long totalEven = 0L;



}

