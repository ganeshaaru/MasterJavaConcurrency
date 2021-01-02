package com.dev.bok.producerconsumer;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

public class EventStorage {
    private int capacity;
    private Queue<Date> messageStore;


    public EventStorage(int capacity) {
        this.capacity = capacity;
        this.messageStore = new LinkedList<Date>();
    }

    public synchronized void pushMessage(Date message){
        while(isMessageStoreFull()){
            try {
                System.out.printf("Producer Thread : %d : going to wait state\n", Thread.currentThread().getId());
                wait();// Threads will be blocked from execution
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notify();// informs consumer threads to start pick the messages, if they are in waiting state
        System.out.printf("Producer Thread : %d : Storage size : %d : \n" , Thread.currentThread().getId(), messageStore.size());
        messageStore.offer(message);
    }


    public synchronized Date getMessage(){
        while (isMessageStoreEmpty()){
            try {
                System.out.printf("Consumer Thread : %d : going to wait state\n", Thread.currentThread().getId());
                wait();//Threads will be blocked from execution
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Date date = messageStore.poll();
        notify();// informs producer threads to start pushing the messages to the queue if they are waiting state because of full queue
        System.out.printf("Consumer Thread : %d : Storage size : %d : \n" , Thread.currentThread().getId(), messageStore.size());
        return date;
    }

    private boolean isMessageStoreEmpty() {
        return 0 == messageStore.size();
    }


    private boolean isMessageStoreFull() {
        return this.capacity == messageStore.size();
    }
}
