package com.dev.bok.barbershop;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Barber implements Runnable {
    private Semaphore barberLock;
    private Semaphore saloonLock;

    public Barber(Semaphore barberLock, Semaphore saloonLock) {
        this.barberLock = barberLock;
        this.saloonLock = saloonLock;
    }

    @Override
    public void run() {
        while (true) {
            try {
                //sleep, customer will wake me up once he gets the chair
                barberLock.acquire();

                // customer came to seat and calling me from sleep
                System.out.println("Waking up : " + barberLock.availablePermits());

                // I'll be doing haircut.
                doHairCut();

                //haircut over, inform saloon to pick next customer
                saloonLock.release();


            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    private void doHairCut() {
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(5));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
