package com.test.demo.controller.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class CounterResponse {
   private List<Map<String,Integer>> counts;

   public CounterResponse(){
   }
}
