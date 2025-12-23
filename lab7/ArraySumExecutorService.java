import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ArraySumExecutorService {
    
    static class ArraySumTask implements Callable<Long> {
        private final int[] array;
        private final int start;
        private final int end;
        private final int taskId;
        
        public ArraySumTask(int[] array, int start, int end, int taskId) {
            this.array = array;
            this.start = start;
            this.end = end;
            this.taskId = taskId;
        }
        
        @Override
        public Long call() {
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            
            System.out.println("Задача-" + taskId + " (" + Thread.currentThread().getName() + 
                             "): сумма элементов " + start + "-" + (end-1) + " = " + sum);
            return sum;
        }
    }
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("=== Программа 1.2: Сумма массива с использованием ExecutorService ===\n");
        
        int arraySize = 1000000;
        int numThreads = 4;
        
        int[] array = new int[arraySize];
        Random random = new Random();
        
        System.out.println("Создание массива из " + arraySize + " элементов...");
        for (int i = 0; i < arraySize; i++) {
            array[i] = random.nextInt(100);
        }
        
        System.out.println("\nСоздание пула из " + numThreads + " потоков...");
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        
        int chunkSize = arraySize / numThreads;
        List<Future<Long>> futures = new ArrayList<>();
        
        System.out.println("\nРаспределение задач по потокам:");
        
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = (i == numThreads - 1) ? arraySize : start + chunkSize;
            
            System.out.println("Задача-" + i + ": элементы " + start + "-" + (end-1));
            
            ArraySumTask task = new ArraySumTask(array, start, end, i);
            futures.add(executor.submit(task));
        }
        
        long totalSum = 0;
        for (int i = 0; i < futures.size(); i++) {
            long partialSum = futures.get(i).get();
            totalSum += partialSum;
            System.out.println("Получен результат от задачи-" + i + ": " + partialSum);
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
        System.out.println("Общая сумма элементов массива: " + totalSum);
        System.out.println("Количество использованных потоков: " + numThreads);
        System.out.println("Время выполнения: " + (endTime - startTime) + " мс");
    }
}