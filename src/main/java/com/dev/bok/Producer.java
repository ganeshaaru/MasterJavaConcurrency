package com.dev.bok;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Producer implements Runnable {
    private EventStorage eventStorage;

    public Producer(EventStorage eventStorage) {
        this.eventStorage = eventStorage;
    }

    public void run() {
        while (true){
            eventStorage.pushMessage(new Date());

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
