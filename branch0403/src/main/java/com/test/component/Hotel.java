package com.test.component;

import com.test.component.motel.MotelHall;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Lixueyuan
 * @date 2019/9/14 1:22
 */
@Component("hotel")@Data
public class Hotel {
    private static final AtomicInteger counter = new AtomicInteger();
    private MotelHall motelHall;
    private Integer i;
    private int am;
    private int pm;
    public void countting() {
        System.out.println("Countting now, please wait...");
        System.out.println("result is "+counter);
        counter.incrementAndGet();
        System.out.println("Countting complete");
    }
    public void setDefault() {
        am = 1;
    }

    public static void main(String[] args) {
        Hotel h = new Hotel();
        Integer i = Integer.valueOf(null);
    }
}
