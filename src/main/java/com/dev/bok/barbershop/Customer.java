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
            saloon.acceptWalkInCustomer();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sleepSomeTime() {
        int duration = new Random().nextInt(10);
        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s arriving  after %d seconds \n", Thread.currentThread().getName(), duration);
    }
}
