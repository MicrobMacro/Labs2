import java.util.regex.*;
public class NumberFinder {
    public static void main(String[] args) {
        String text = "уга буга 11 бананов упа1ло на голову 15.5 макакам и -9 людям";
        
        try {
            Pattern pattern = Pattern.compile("-?\\d+(.\\d+)?");
            Matcher matcher = pattern.matcher(text);
            
            System.out.println("Найденные числа:");
            while (matcher.find()) {
                System.out.println(matcher.group());
            }
            
        } catch (PatternSyntaxException e) {
            System.err.println("Ошибка в регулярном выражении: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
        }
    }
}