package com.hua.springbatchplayground.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ItemProcessor2 implements ItemProcessor<Integer, Integer> {

    @Override
    public Integer process(Integer integer) throws Exception {
        System.out.println("ItemProcessor1: process() gets called ");
        int result = integer * integer;
        System.out.println(String.format("Before process: %d, after process: %d", integer, result));
        return result;
    }
}
