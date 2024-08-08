package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int threadCount = 10;
        List<Thread> threadList = new ArrayList<>();
        Random random = new Random();
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < threadCount; i++) {
            System.out.println(i);


            int randomDelay = random.nextInt(20) + 1;
            try {
                Thread.sleep(randomDelay * 1000);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
            }

            Thread newThread = new Thread(new MyRunnable());
            threadList.add(newThread);
        }

        for (Thread thread : threadList) {
            thread.start();
        }
        for (Thread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
            }
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("Total execution time: " + executionTime / 1000 + " seconds");
        System.out.println("All threads have finished.");
    }
}
