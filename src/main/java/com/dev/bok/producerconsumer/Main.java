package com.dev.bok.producerconsumer;

public class Main {

    public static void main(String[] args) {

        EventStorage store = new EventStorage(10);

        Producer producer = new Producer(store);
        Consumer consumer = new Consumer(store);

        for (int i = 0; i < 4; i++) {
            new Thread(producer).start();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(consumer).start();
        }

    }
}
