package org.hbrs.seka.uebung2;

public class ConcurrentClass extends Thread {

    @Override
    public void run() {
        // Thread execution logic
        System.out.println("ConcurrentClass is running");
    }

    public static void main(String[] args) {
        ConcurrentClass concurrentClass = new ConcurrentClass();
        concurrentClass.start();
    }
}
