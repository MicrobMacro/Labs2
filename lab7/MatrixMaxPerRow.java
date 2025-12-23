import java.util.Random;

public class MatrixMaxPerRow {
    
    static class RowMaxFinder extends Thread {
        private final int[] row;
        private final int rowIndex;
        private int rowMax;
        
        public RowMaxFinder(int[] row, int rowIndex) {
            super("RowThread-" + rowIndex);
            this.row = row;
            this.rowIndex = rowIndex;
            this.rowMax = Integer.MIN_VALUE;
        }
        
        @Override
        public void run() {
            if (row.length == 0) {
                rowMax = Integer.MIN_VALUE;
                return;
            }
            
            rowMax = row[0];
            for (int value : row) {
                if (value > rowMax) {
                    rowMax = value;
                }
            }
            
            System.out.println(getName() + ": максимум в строке " + rowIndex + " = " + rowMax);
        }
        
        public int getRowMax() {
            return rowMax;
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Программа 2.1: Поиск максимума в матрице (поток на строку) ===\n");
        
        int rows = 10;
        int cols = 8;
        
        int[][] matrix = new int[rows][cols];
        Random random = new Random();
        
        System.out.println("Создание матрицы " + rows + "x" + cols + "...");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextInt(1000);
            }
        }
        
        System.out.println("\nМатрица:");
        for (int i = 0; i < rows; i++) {
            System.out.print("Строка " + i + ": ");
            for (int j = 0; j < cols; j++) {
                System.out.printf("%4d", matrix[i][j]);
            }
            System.out.println();
        }
        
        System.out.println("\nЗапуск потоков для поиска максимумов в строках...");
        RowMaxFinder[] threads = new RowMaxFinder[rows];
        
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < rows; i++) {
            threads[i] = new RowMaxFinder(matrix[i], i);
            threads[i].start();
        }
        
        for (int i = 0; i < rows; i++) {
            threads[i].join();
        }
        
        int globalMax = Integer.MIN_VALUE;
        for (int i = 0; i < rows; i++) {
            if (threads[i].getRowMax() > globalMax) {
                globalMax = threads[i].getRowMax();
            }
        }
        
        long endTime = System.currentTimeMillis();
        
        System.out.println("\n=== РЕЗУЛЬТАТЫ ===");
        System.out.println("Максимальный элемент в матрице: " + globalMax);
        System.out.println("Количество потоков: " + rows);
        System.out.println("Время выполнения: " + (endTime - startTime) + " мс");
    }
}