package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        String fileName = "C:/Users/yusib/IdeaProjects/HomeWorkCallable/src/main/java/file.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 1;
            int totalWordCount = 0;
            while ((line = br.readLine()) != null) {

                Callable<Integer> wordCounter = new WordCounter(line);
                Future<Integer> future = executor.submit(wordCounter);
                int wordCount = future.get();
                totalWordCount += wordCount;
                System.out.println("Line " + lineNumber + ": " + line + " - Word count: " + wordCount);
                lineNumber++;
            }
            System.out.println("Total word count: " + totalWordCount);
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    static class WordCounter implements Callable<Integer> {
        private final String line;

        WordCounter(String line) {
            this.line = line;
        }

        @Override
        public Integer call() {
            String[] words = line.trim().split("\\s+");
            return words.length;
        }
    }
}
