package com.dev.bok.barbershop;

import java.util.Date;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Saloon {

    private AtomicInteger waitingCustomerCount;
    private int noOfWaitingChairs;
    private Lock lock;
    private Semaphore barberChair;
    private Semaphore barberLock;
    private Semaphore saloonLock;

    public Saloon(int noOfWaitingChairs, Semaphore barberLock, Semaphore saloonLock) {
        this.waitingCustomerCount = new AtomicInteger(0);
        this.noOfWaitingChairs = noOfWaitingChairs;
        this.lock = new ReentrantLock();
        this.barberChair = new Semaphore(1);
        this.barberLock = barberLock;
        this.saloonLock = saloonLock;
    }

    public void acceptCustomer() throws InterruptedException {
        lock.lock();
        if (isFull()) {
            System.out.println(Thread.currentThread().getName() + " : Saloon Full Customer walks out at " + new Date());
            lock.unlock();
            return;
        }
        lock.unlock();

        waitingCustomerCount.incrementAndGet();

        try {
            barberChair.acquire();

            // decrement no of waiting customers
            waitingCustomerCount.decrementAndGet();

            // wake up barber
            barberLock.release();

            System.out.println(Thread.currentThread().getName() + " "+  new Date() + " "+ "Starting HairCut ");

            // wait till barber to finish haircut
            saloonLock.acquire();

            //hair cut over got notify call form barber, release the chair
            System.out.println(Thread.currentThread().getName() + " "+  new Date() + " "+ "Completed HairCut ");
        } finally {
            //Customer leaves
            barberChair.release();
        }

    }

    private boolean isFull() {
        return waitingCustomerCount.get() == noOfWaitingChairs;
    }
}
