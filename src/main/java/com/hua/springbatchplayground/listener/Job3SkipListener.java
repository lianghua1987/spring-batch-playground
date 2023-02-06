package com.hua.springbatchplayground.listener;

import com.hua.springbatchplayground.model.Student;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.stereotype.Component;

@Component
public class Job3SkipListener implements SkipListener<Student, Student> {

    public void onSkipInRead(Throwable th) {
        if (th instanceof FlatFileParseException) {
            System.out.println("onSkipInRead:" + ((FlatFileParseException) th).getInput());
        }
    }

    public void onSkipInProcess(Student student, Throwable th) {
        System.out.println("onSkipInProcess: " + student.toString());
    }

    public void onSkipInWrite(Student student, Throwable th) {
        System.out.println("onSkipInWrite: " + student.toString());
    }
}
