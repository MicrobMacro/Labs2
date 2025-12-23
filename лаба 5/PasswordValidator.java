import java.util.Scanner;
import java.util.regex.*;
public class PasswordValidator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,16}$");
            System.out.print("Введите пароль: ");
            String password = scanner.nextLine();
            Matcher matcher = pattern.matcher(password);
            if (matcher.matches()) {
                System.out.println("Пароль корректен!");
            } else {
                System.out.println("Пароль не соответствует требованиям:");
                System.out.println("- Длина должна быть от 8 до 16 символов");
                System.out.println("- Только латинские буквы и цифры");
                System.out.println("- Хотя бы одна заглавная буква");
                System.out.println("- Хотя бы одна цифра");
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