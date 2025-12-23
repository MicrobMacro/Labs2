import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomEmailFormatException extends Exception {
    
    public CustomEmailFormatException(String message) {
        super(message);
    }
}
class EmailValidator {
    private static final String LOG_FILE = "task2/variant1/email_errors.log";
    public static void main(String[] args) {
        String[] emails = {
            "test@example.com",
            "invalid-email",
            "another@test.ru",
            "wrong.format@",
            "valid.email@domain.org"
        };
        for (String email : emails) {
            try {
                validateEmailFormat(email);
                System.out.println("Email address correct: " + email);
            } catch (CustomEmailFormatException e) {
                System.err.println("an error occures email '" + email + "': " + e.getMessage());
                logErrorToFile(email, e.getMessage());
            }
        }
    }
    public static void validateEmailFormat(String email) throws CustomEmailFormatException {
        try {
            if (email == null || email.trim().isEmpty()) {
                throw new CustomEmailFormatException("Email cant be blank");
            }
            if (!email.contains("@")) {
                throw new CustomEmailFormatException("Email must contain '@'");
            }
            String[] parts = email.split("@");
            if (parts.length != 2) {
                throw new CustomEmailFormatException("too much '@' in email adress or no local/domain part is present");
            }
            String localPart = parts[0];
            String domainPart = parts[1];
            if (localPart.isEmpty()) {
                throw new CustomEmailFormatException("local part of email adress (before '@') cant be blank");
            }
            if (domainPart.isEmpty()) {
                throw new CustomEmailFormatException("domain part of email adress (before '@') cant be blank");
            }
            if (!domainPart.contains(".")) {
                throw new CustomEmailFormatException("domain part of email dsnt contains dot");
            }
            System.out.println("Проверка формата email адреса завершена успешно: " + email);
        } catch (CustomEmailFormatException e) {
            System.err.println("error validating '" + email + "': " + e.getMessage());
            throw e;
        }
    }
     public static void logErrorToFile(String email, String errorMessage) {
        
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter(LOG_FILE, true));
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            writer.println("[" + timestamp + "] error validating email: '" + email + "' - " + errorMessage);
            System.out.println("Ошибка записана в лог-файл: " + LOG_FILE);
        } catch (IOException e) {
            System.err.println("Ошибка при записи в лог-файл: " + e.getMessage());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}