import java.util.Scanner;
import java.util.regex.*;
public class WordFinder {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = "Java is a popular programming language. JavaScript is also widely used. " +
                     "Many developers enjoy programming in Python and Java.";
        try {
            System.out.println("Текст для поиска: " + text);
            System.out.print("Введите букву для поиска слов: ");
            String letter = scanner.nextLine();
            if (letter.length() != 1 || !Character.isLetter(letter.charAt(0))) {
                System.out.println("Ошибка: введите одну букву!");
                return;
            }
            String regex = "\\b" + Pattern.quote(letter) + "[a-zA-Z]*\\b";
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(text);
            System.out.println("Слова, начинающиеся с буквы '" + letter + "':");
            boolean found = false;
            while (matcher.find()) {
                System.out.println(matcher.group());
                found = true;
            }
            if (!found) {
                System.out.println("Слов, начинающихся с буквы '" + letter + "', не найдено.");
            }
        } catch (PatternSyntaxException e) {
            System.err.println("Ошибка в регулярном выражении: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}