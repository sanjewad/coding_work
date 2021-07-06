package com.test.demo.controller.dto;

import lombok.*;

import java.util.List;
@AllArgsConstructor
@Getter
public class CounterSearchRequest {
    private List<String> searchText;

    public CounterSearchRequest(){
    }
}
