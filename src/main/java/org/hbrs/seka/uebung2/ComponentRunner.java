package org.hbrs.seka.uebung2;

public class ComponentRunner {

    public void startComponent( Component component ) {
        // Initialisierung des Threads
        Thread componentThread = new Thread(() -> {
            component.start_init();
        });

        // Start des Threads
        componentThread.start();
    }

}
