import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MatrixMaxExecutorService {
    static class MatrixChunkTask implements Callable<Integer> {
        private final int[][] matrix;
        private final int startRow;
        private final int endRow;
        private final int taskId;
        
        public MatrixChunkTask(int[][] matrix, int startRow, int endRow, int taskId) {
            this.matrix = matrix;
            this.startRow = startRow;
            this.endRow = endRow;
            this.taskId = taskId;
        }
        
        @Override
        public Integer call() {
            int max = Integer.MIN_VALUE;
            
            for (int i = startRow; i < endRow; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    if (matrix[i][j] > max) {
                        max = matrix[i][j];
                    }
                }
            }
            
            System.out.println("Задача-" + taskId + " (" + Thread.currentThread().getName() + 
                             "): максимум в строках " + startRow + "-" + (endRow-1) + " = " + max);
            return max;
        }
    }
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("=== Программа 2.2: Поиск максимума в матрице с использованием ExecutorService ===\n");
        
        int rows = 12;
        int cols = 10;
        int numThreads = 4;
        
        int[][] matrix = new int[rows][cols];
        Random random = new Random();
        
        System.out.println("Создание матрицы " + rows + "x" + cols + "...");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextInt(1000);
            }
        }
        
        System.out.println("\nПервые 5 строк матрицы:");
        for (int i = 0; i < Math.min(5, rows); i++) {
            System.out.print("Строка " + i + ": ");
            for (int j = 0; j < cols; j++) {
                System.out.printf("%4d", matrix[i][j]);
            }
            System.out.println();
        }
        if (rows > 5) {
            System.out.println("... и еще " + (rows - 5) + " строк");
        }
        
        System.out.println("\nСоздание пула из " + numThreads + " потоков...");
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        
        int rowsPerThread = rows / numThreads;
        List<Future<Integer>> futures = new ArrayList<>();
        
        System.out.println("\nРаспределение строк матрицы по задачам:");
        
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < numThreads; i++) {
            int startRow = i * rowsPerThread;
            int endRow = (i == numThreads - 1) ? rows : startRow + rowsPerThread;
            
            System.out.println("Задача-" + i + ": строки " + startRow + "-" + (endRow-1) + 
                             " (" + (endRow - startRow) + " строк)");
            
            MatrixChunkTask task = new MatrixChunkTask(matrix, startRow, endRow, i);
            futures.add(executor.submit(task));
        }
        
        int globalMax = Integer.MIN_VALUE;
        for (int i = 0; i < futures.size(); i++) {
            int chunkMax = futures.get(i).get();
            System.out.println("Получен результат от задачи-" + i + ": " + chunkMax);
            
            if (chunkMax > globalMax) {
                globalMax = chunkMax;
            }
        }
        
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
        
        long endTime = System.currentTimeMillis();
        
        System.out.println("\n=== РЕЗУЛЬТАТЫ ===");
        System.out.println("Максимальный элемент в матрице: " + globalMax);
        System.out.println("Количество потоков: " + numThreads);
        System.out.println("Количество строк на поток: " + rowsPerThread);
        System.out.println("Время выполнения: " + (endTime - startTime) + " мс");
    }
}