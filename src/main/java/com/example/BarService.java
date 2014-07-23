package com.example;

import com.yammer.metrics.annotation.Timed;

/**
 * @author berinle
 */
@AppComponent
public class BarService {

    @Timed
    public void doSomeWork() {
        System.out.println("FooService.doSomeWork");
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("done.");
    }
}
