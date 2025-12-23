import java.util.Random;

public class ArraySumTwoThreads {
    
    static class SumResult {
        private long sum;
        
        public synchronized void add(long value) {
            sum += value;
        }
        
        public synchronized long getSum() {
            return sum;
        }
    }
    
    static class SumCalculator extends Thread {
        private final int[] array;
        private final int start;
        private final int end;
        private final SumResult result;
        private final String threadName;
        
        public SumCalculator(String name, int[] array, int start, int end, SumResult result) {
            super(name);
            this.threadName = name;
            this.array = array;
            this.start = start;
            this.end = end;
            this.result = result;
        }
        
        @Override
        public void run() {
            long partialSum = 0;
            for (int i = start; i < end; i++) {
                partialSum += array[i];
            }
            
            result.add(partialSum);
            
            System.out.println(threadName + ": вычислил сумму элементов с " + 
                             start + " по " + (end-1) + " = " + partialSum);
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Программа 1.1: Сумма массива с использованием двух потоков (Thread) ===\n");
        
        int arraySize = 1000000;
        int[] array = new int[arraySize];
        Random random = new Random();
        
        System.out.println("Создание массива из " + arraySize + " элементов...");
        for (int i = 0; i < arraySize; i++) {
            array[i] = random.nextInt(100);
        }
        
        SumResult totalResult = new SumResult();
        
        int mid = arraySize / 2;
        
        System.out.println("\nЗапуск двух потоков для вычисления суммы...");
        System.out.println("Поток 1 будет обрабатывать элементы 0-" + (mid-1));
        System.out.println("Поток 2 будет обрабатывать элементы " + mid + "-" + (arraySize-1));
        
        SumCalculator thread1 = new SumCalculator("Поток-1", array, 0, mid, totalResult);
        SumCalculator thread2 = new SumCalculator("Поток-2", array, mid, arraySize, totalResult);
        
        long startTime = System.currentTimeMillis();
        
        thread1.start();
        thread2.start();
        
        thread1.join();
        thread2.join();
        
        long endTime = System.currentTimeMillis();
        
        System.out.println("Общая сумма элементов массива: " + totalResult.getSum());
        System.out.println("Время выполнения: " + (endTime - startTime) + " мс");
        }
    }