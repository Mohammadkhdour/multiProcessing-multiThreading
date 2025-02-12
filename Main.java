// Name: Mohammad Khdour
// ID : 1212517

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException, ExecutionException {
                String filePath = "src/text8.txt";

          List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words = List.of(line.toLowerCase().split("\\W+"));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        List <Integer> numOFThreadAndChild = new ArrayList<>(Arrays.asList(2,4,6,8));
        naiveApproach(words);

        for (Integer i : numOFThreadAndChild){
            long time = multiThreading(words,i);
            System.out.println("execution Time by multithreading use " +i+ " thread = "+time+" ms");
        }

          for (Integer i : numOFThreadAndChild){
            long time = multiProcessing(words,4);
            System.out.println("execution Time by multiProcessing use " +i+ " child = "+time+ " ms");
        }

    }

    static void naiveApproach(List<String> list){
        long startTime = System.currentTimeMillis();  // Start time

            Map<String, Integer> wordCount = new HashMap<>();


              for (String word : list) {
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }


        ArrayList<Map.Entry<String, Integer>> sortedWords = new ArrayList<>(wordCount.entrySet());
        sortedWords.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // print the top 10 most frequent words

        top10Words(sortedWords);
     long endTime = System.currentTimeMillis();  // End time
        long executionTime = endTime - startTime;   // Calculate elapsed time
                System.out.println("Execution time by naive approach = " + executionTime + " ms");

    }

    static long multiThreading(List<String> list, int numOfThread) throws InterruptedException {
        long startTime = System.currentTimeMillis();
       List <List<String>> parts = splitFileIntoParts(list,numOfThread);

             // Create a thread pool with n threads
        ExecutorService executor = Executors.newFixedThreadPool(numOfThread);

        List<Future<ArrayList<Map.Entry<String, Integer>>>> futures = new ArrayList<>();

        // Submit each part to a separate thread
        for (int i = 0; i < parts.size(); i++) {
            MyThread myThread = new MyThread(parts.get(i));
            Future<ArrayList<Map.Entry<String, Integer>>> future = executor.submit(myThread);
            futures.add(future);
        }

        // Collect results from each thread
        ArrayList<Map.Entry<String, Integer>> allResults = new ArrayList<>();
        for (Future<ArrayList<Map.Entry<String, Integer>>> future : futures) {
            try {
                allResults.addAll(future.get());  // Retrieve and add each thread's results
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // Shut down the executor
        executor.shutdown();

     // top10Words(allResults);

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
       // System.out.println("Execution Time = " +executionTime+" ms");
        return executionTime;


    }



    public static long multiProcessing(List<String> list,int childrenNum){
        long startTime = System.currentTimeMillis();

        List<List<String>> parts = splitFileIntoParts(list,childrenNum);
        List<MyProcess> childProcess = new ArrayList<>(List.of());

        for (int i = 0; i < parts.size(); i++) {
             childProcess.add(new MyProcess(parts.get(i)));
            childProcess.get(i).fork();

        }
        ArrayList<Map.Entry<String, Integer>> allResults = new ArrayList<>();
          for (int i = 0; i < parts.size(); i++) {
              allResults.addAll(childProcess.get(i).join());

        }

        //  top10Words(allResults);

          long endTime = System.currentTimeMillis();
          long elapsedTime = endTime - startTime;
         // System.out.println(elapsedTime);
          return elapsedTime;
    }

    public static void top10Words(ArrayList<Map.Entry<String, Integer>> allResults){
         // Combine all results and get the top 10 most frequent words overall
        Map<String, Integer> combinedWordCount = new HashMap<>();
        for (Map.Entry<String, Integer> entry : allResults) {
            combinedWordCount.put(entry.getKey(), combinedWordCount.getOrDefault(entry.getKey(), 0) + entry.getValue());
        }

        // Sort combined results and print the top 10
        List<Map.Entry<String, Integer>> combinedSortedWords = new ArrayList<>(combinedWordCount.entrySet());
        combinedSortedWords.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        System.out.println("Top 10 most frequent words overall:");
        for (int i = 0; i < Math.min(10, combinedSortedWords.size()); i++) {
            Map.Entry<String, Integer> entry = combinedSortedWords.get(i);
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }


        // Function to read the file and split it into equal parts
    public static List<List<String>> splitFileIntoParts(List<String> words, int numParts) {


        int partSize = (int) Math.ceil(words.size() / (double) numParts);
        List<List<String>> parts = new ArrayList<>();
        for (int i = 0; i < numParts; i++) {
            int start = i * partSize;
            int end = Math.min(start + partSize, words.size());
            parts.add(words.subList(start, end));
        }
        return parts;
    }

}

