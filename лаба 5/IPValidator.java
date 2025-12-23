import java.util.Scanner;
import java.util.regex.*;
public class IPValidator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            String ipPattern = 
                "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][1-9]?)\\.){3}" +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
            Pattern pattern = Pattern.compile(ipPattern);
            System.out.print("Введите IP-адрес: ");
            String ipAddress = scanner.nextLine();
            Matcher matcher = pattern.matcher(ipAddress);
            if (matcher.matches()) {
                System.out.println("IP-адрес корректен!");
                }
             else {
                System.out.println("IP-адрес некорректен!");
                System.out.println("Требования:");
                System.out.println("- 4 числа, разделенные точками");
                System.out.println("- Каждое число от 0 до 255");
                System.out.println("- Пример: 192.168.1.1");
            }
        } catch (PatternSyntaxException e) {
            System.err.println("Ошибка в регулярном выражении: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Ошибка преобразования числа: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}