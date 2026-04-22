package org.hbrs.seka.uebung2;

public class ConcurrentClassWithInterface implements Runnable {
    @Override
    public void run() {
        // Thread execution logic
        System.out.println("ConcurrentClassWithInterface is running");
    }

    public static void main(String[] args) {
        ConcurrentClassWithInterface concurrentClass = new ConcurrentClassWithInterface();
        Thread thread = new Thread(concurrentClass);
        thread.start();
    }
}
