package com.test.demo;

import com.test.entity.ClassOne;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Lixueyuan
 * @date 2019/10/7 14:11
 */
@Slf4j
@Component
public class CatchDemo {
    @Autowired
    GridDoRepository gridRepository;

    public void throwShow () throws RuntimeException {
        System.out.println("throwshow");
        throw new RuntimeException();
    }

    public void setStu() {
        ClassOne o = new ClassOne();
        o.setStudentNo(14);
        o.setName("catchDemo");
        o.setSex(1);
        log.info("save student:id:{},student_no:{},name:{}", o.getId(), o.getStudentNo(), o.getName());
//        gridRepository.save(o);
    }

    public void test() {
        System.out.println("something happen.");
    }
}
