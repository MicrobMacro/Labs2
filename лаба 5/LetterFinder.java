import java.util.regex.*;
public class LetterFinder {
    public static void main(String[] args) {
        String text = "helloWorld This is a TestString with multipleCases like javaProgramming.";
        try {
            Pattern pattern = Pattern.compile("([a-z])([A-Z])");
            Matcher matcher = pattern.matcher(text);
            StringBuffer result = new StringBuffer();
            while (matcher.find()) {
                matcher.appendReplacement(result, "!" + matcher.group(1) + matcher.group(2) + "!");
            }
            matcher.appendTail(result);
            System.out.println("Исходный текст: " + text);
            System.out.println("Обработанный текст: " + result.toString());
        } catch (PatternSyntaxException e) {
            System.err.println("Ошибка в регулярном выражении: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
        }
    }
}