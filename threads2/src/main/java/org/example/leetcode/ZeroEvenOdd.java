package org.example.leetcode;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

class ZeroEvenOdd {
    public static void main(String[] args) {
        int n = 10;
        ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(n);


        IntConsumer printNumber = x -> System.out.print(x);

        Thread t1 = new Thread(() -> {
            try {
                zeroEvenOdd.zero(printNumber);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                zeroEvenOdd.even(printNumber);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t3 = new Thread(() -> {
            try {
                zeroEvenOdd.odd(printNumber);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }

    private int n;
    Semaphore s0, sEven, sOdd;

    public ZeroEvenOdd(int n) {
        this.n = n;
        s0 = new Semaphore(1);
        sEven = new Semaphore(0);
        sOdd = new Semaphore(0);
    }

    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= this.n; i++) {
            s0.acquire();
            printNumber.accept(0);
            if (i % 2 == 0) {
                sEven.release();
            }else {
                sOdd.release();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= this.n; i += 2) {
            sEven.acquire();
            printNumber.accept(i);
            s0.release();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= this.n; i += 2) {
            sOdd.acquire();
            printNumber.accept(i);
            s0.release();
        }
    }
}