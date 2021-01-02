package com.dev.bok.barbershop;

import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int noOfCustomers=5;
        int noOfWaitingChairs=1;
        Semaphore barberLock =  new Semaphore(0);
        Semaphore saloonLock =  new Semaphore(0);

        Saloon saloon = new Saloon(noOfWaitingChairs, barberLock, saloonLock);
        Barber barber = new Barber(barberLock, saloonLock);

        Thread barberThread = new Thread(barber);

        Thread[] threads = new Thread[noOfCustomers];

        //Create customers
        for (int i = 0; i < noOfCustomers; i++) {
            Thread thread = new Thread(new Customer(saloon));
            thread.setName("Customer-"+ i);
            threads[i]=thread ;
        }

        //Barber
        barberThread.start();

        for (int i = 0; i <noOfCustomers; i++) {
            threads[i].start();
        }

        //Wait for all customers
        for (int i = 0; i <noOfCustomers; i++) {
            threads[i].join();
        }

        // Cancel Barber Thread
        barberThread.interrupt();
    }
}
