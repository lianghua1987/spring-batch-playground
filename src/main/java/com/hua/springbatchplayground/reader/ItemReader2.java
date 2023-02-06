package com.hua.springbatchplayground.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class ItemReader2 implements ItemReader<Integer> {

    List<Integer> list = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
    int index = 0;

    @Override
    public Integer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        System.out.println("ItemReader1: read() gets called ");
        if (index < list.size()) {
            return list.get(index++);
        }
        index = 0;
        return null;
    }
}
