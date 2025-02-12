import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class MyThread implements Callable<ArrayList<Map.Entry<String,Integer>>>{
    List<String> part;
    ArrayList<Map.Entry<String,Integer>> listEntry = new ArrayList<>();

    public MyThread(List<String> part){
        this.part = part;
    }


    @Override
    public ArrayList<Map.Entry<String, Integer>> call() throws Exception {
        Map<String, Integer> wordCount = new HashMap<>();

        for (String word : part)
            if (!word.isEmpty()) {  // Ignore empty strings
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }

        // Sort words by frequency and get the top 10
        List<Map.Entry<String, Integer>> sortedWords = new ArrayList<>(wordCount.entrySet());
        sortedWords.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Collect the top 10 most frequent words
        ArrayList<Map.Entry<String, Integer>> listEntry = new ArrayList<>();
        for (int i = 0; i < Math.min(10, sortedWords.size()); i++) {
            listEntry.add(sortedWords.get(i));
        }
        return listEntry;  // Return the list of top 10 entries

    }
}
