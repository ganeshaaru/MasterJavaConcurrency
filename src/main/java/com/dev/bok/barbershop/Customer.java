package com.dev.bok.barbershop;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Customer implements Runnable{

    private Saloon saloon;

    public Customer(Saloon saloon) {
        this.saloon = saloon;
    }

    @Override
    public void run() {
        sleepSomeTime();
        walkIntoSaloon();
    }

    private void walkIntoSaloon() {
        try {
            saloon.acceptCustomer();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sleepSomeTime() {
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
