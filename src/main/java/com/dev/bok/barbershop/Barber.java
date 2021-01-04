package com.dev.bok.barbershop;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Barber implements Runnable {
    private Semaphore barberLock;
    private Semaphore customerLock;

    public Barber(Semaphore barberLock, Semaphore customerLock) {
        this.barberLock = barberLock;
        this.customerLock = customerLock;
    }

    @Override
    public void run() {
        while (true) {
            try {
                //got to sleep, customer will wake me up once he gets the chair
                barberLock.acquire();

                // customer came to seat and calling me from sleep
                System.out.println("Waking up : " + barberLock.availablePermits());

                // I'll be doing haircut.
                doHairCut();

                //haircut over, inform customer to do the payment and leave.
                customerLock.release();


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
