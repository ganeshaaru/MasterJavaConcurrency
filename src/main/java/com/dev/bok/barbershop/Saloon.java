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
    private Semaphore customerLock;

    public Saloon(int noOfWaitingChairs, Semaphore barberLock, Semaphore customerLock) {
        this.waitingCustomerCount = new AtomicInteger(0);
        this.noOfWaitingChairs = noOfWaitingChairs;
        this.lock = new ReentrantLock();
        this.barberChair = new Semaphore(1);
        this.barberLock = barberLock;
        this.customerLock = customerLock;
    }

    public void acceptWalkInCustomer() throws InterruptedException {

        //If block is accessed by diff threads, needs to be protected with help of Lock
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

            System.out.printf("%s having haircut at %s\n", Thread.currentThread().getName(), new Date());

            // wait till barber to finish haircut
            customerLock.acquire();

            //hair cut over got notify call form barber, release the chair
            System.out.printf("%s completed haircut at %s\n", Thread.currentThread().getName(), new Date());
        } finally {

            barberChair.release();
            //Customer pays and leaves the saloon
            doPayment();
        }

    }

    private void doPayment() {
        System.out.printf("%s doing payment at %s\n", Thread.currentThread().getName(), new Date());
    }

    private boolean isFull() {
        return waitingCustomerCount.get() == noOfWaitingChairs;
    }
}
