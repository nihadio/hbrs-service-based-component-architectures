package org.hbrs.seka.uebung2;

public class ConcurrentClassWithAnonymousClass {
    public static void main(String[] args) {
        // Erstellen eines Threads mit einer anonymen Klasse
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Logik für den Thread
                System.out.println("Thread mit anonymer Klasse läuft");
            }
        });

        // Starten des Threads
        thread.start();
    }



    public void startWithLambda() {
        // Erstellen eines Threads mit einer Lambda-Ausdruck
        Thread thread = new Thread(() -> {
            // Logik für den Thread
            System.out.println("Thread mit Lambda-Ausdruck läuft");
        });

        // Starten des Threads
        thread.start();
    }
}
