package lab6;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class TopWords {
    public static void main(String[] args) {
        String filepath = "C:/Новая папка/lab6/text.txt";
        File file = new File(filepath);
        Scanner skakner = null;
        try {
            skakner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден, возможно указан неверный путь");
            return;
        }
        Map<String, Integer> wordCounter = new HashMap<>();
        while (skakner.hasNext()) {
            String word = skakner.next().toLowerCase();
            word = word.replaceAll("[^a-zа-яё]", "");
            if (word.isEmpty()) {
                continue;
            }
            wordCounter.put(word, wordCounter.getOrDefault(word, 0) + 1);
        }
        skakner.close();
        List<Map.Entry<String, Integer>> list = new ArrayList<>(wordCounter.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> in1,
                                Map.Entry<String, Integer> in2) {
                return in2.getValue().compareTo(in1.getValue());                    
            }
        });
        System.out.println("Топ 10 самых часто встречаемых слов");
        int count = 0;
        for (Map.Entry<String, Integer> entry : list) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
            count++;
            if (count == 10) {
                break;
            }
        }
    }
}