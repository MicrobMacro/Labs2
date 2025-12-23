import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
public class FileCopyBothVariants {
    public static void main(String[] args) {
        String sourceFile = "task2/variant1/copy_from.txt";
        String destinationFile = "task2/variant1/copy_to.txt";
        System.out.println("=== Вариант 1: Обработка ошибок открытия/закрытия файлов ===");
        copyFileWithOpenCloseHandling(sourceFile, destinationFile);
        System.out.println("\n=== Вариант 2: Обработка ошибок чтения/записи файлов ===");
        copyFileWithReadWriteHandling(sourceFile, destinationFile.replace(".txt", "_v2.txt"));
    }
    public static void copyFileWithOpenCloseHandling(String sourcePath, String destPath) {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        
        try {
            try {
                inputStream = new FileInputStream(sourcePath);
                System.out.println("Исходный файл успешно открыт: " + sourcePath);
            } catch (IOException e) {
                System.err.println("Ошибка при открытии исходного файла '" + sourcePath + "': " + e.getMessage());
                throw e;
            }
            try {
                outputStream = new FileOutputStream(destPath);
                System.out.println("Целевой файл успешно открыт: " + destPath);
            } catch (IOException e) {
                System.err.println("Ошибка при открытии целевого файла '" + destPath + "': " + e.getMessage());
                throw e;
            }
            byte[] buffer = new byte[1024];
            int totalBytesCopied = 0;
            int bytesRead;
            inputStream.close();
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                totalBytesCopied += bytesRead;
            }
            System.out.println("Копирование завершено успешно");
            System.out.println("Скопировано байт: " + totalBytesCopied);
        } catch (IOException e) {
            System.err.println("Произошла ошибка в процессе копирования: " + e.getMessage());
            System.err.println("Копирование прервано из-за ошибки");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                    System.out.println("Исходный файл успешно закрыт");
                } catch (IOException e) {
                    System.err.println("Ошибка при закрытии исходного файла: " + e.getMessage());
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                    System.out.println("Целевой файл успешно закрыт");
                } catch (IOException e) {
                    System.err.println("Ошибка при закрытии целевого файла: " + e.getMessage());
                }
            }
        }
    }
    public static void copyFileWithReadWriteHandling(String sourcePath, String destPath) {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(sourcePath);
            outputStream = new FileOutputStream(destPath);
            System.out.println("Файлы успешно открыты для копирования");
            byte[] buffer = new byte[1024];
            int totalBytesCopied = 0;
            int bytesRead;
            int readAttempts = 0;
            int writeAttempts = 0;
            while (true) {
                try {
                    bytesRead = inputStream.read(buffer);
                    readAttempts++;
                    if (bytesRead == -1) {
                        break;
                    }
                    System.out.println("Успешно прочитано байт: " + bytesRead + " (попытка чтения #" + readAttempts + ")");
                } catch (IOException e) {
                    System.err.println("Ошибка чтения из файла на попытке #" + (readAttempts + 1) + ": " + e.getMessage());
                    System.err.println("Пропускаем проблемный блок данных и продолжаем...");
                    continue;
                }
                try {
                    outputStream.write(buffer, 0, bytesRead);
                    writeAttempts++;
                    totalBytesCopied += bytesRead;
                    System.out.println("Успешно записано байт: " + bytesRead + " (попытка записи #" + writeAttempts + ")");
                } catch (IOException e) {
                    System.err.println("Ошибка записи в файл на попытке #" + (writeAttempts + 1) + ": " + e.getMessage());
                    System.err.println("Пропускаем проблемный блок данных и продолжаем...");
                }
            }
            System.out.println("Копирование завершено");
            System.out.println("Всего попыток чтения: " + readAttempts);
            System.out.println("Всего попыток записи: " + writeAttempts);
            System.out.println("Успешно скопировано байт: " + totalBytesCopied);
        } catch (IOException e) {
            System.err.println("Критическая ошибка при открытии файлов: " + e.getMessage());
            System.err.println("Копирование невозможно");
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                    System.out.println("Исходный файл закрыт");
                }
                if (outputStream != null) {
                    outputStream.close();
                    System.out.println("Целевой файл закрыт");
                }
            } catch (IOException e) {
                System.err.println("Ошибка при закрытии файлов: " + e.getMessage());
            }
        }
    }
}