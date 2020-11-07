package com.dev.bok;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable{
    private EventStorage eventStorage;

    public Consumer(EventStorage eventStorage) {
        this.eventStorage = eventStorage;
    }

    public void run() {
        while(true){
            Date message = eventStorage.getMessage();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
