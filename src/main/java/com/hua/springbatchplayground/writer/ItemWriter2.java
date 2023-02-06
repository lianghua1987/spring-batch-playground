package com.hua.springbatchplayground.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class ItemWriter2 implements ItemWriter<Integer> {

    @Override
    public void write(Chunk<? extends Integer> chunk) throws Exception {
        System.out.println("ItemWriter1: write() gets called ");
        chunk.forEach(System.out::println);
    }
}
