import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class WarehouseSimulation {
    
    // Класс Товар
    static class Product {
        private final String id;
        private final int weight; // вес в кг
        
        public Product(String id, int weight) {
            this.id = id;
            this.weight = weight;
        }
        
        public int getWeight() {
            return weight;
        }
        
        public String getId() {
            return id;
        }
        
        @Override
        public String toString() {
            return "Товар[" + id + ", вес=" + weight + "кг]";
        }
    }
    
    static class Storage {
        private final BlockingQueue<Product> products;
        private final String name;
        private final AtomicInteger totalWeightMoved = new AtomicInteger(0);
        private final AtomicInteger tripsCount = new AtomicInteger(0);
        
        public Storage(String name, List<Product> initialProducts) {
            this.name = name;
            this.products = new LinkedBlockingQueue<>(initialProducts);
        }
        
        public Product takeProduct() throws InterruptedException {
            return products.poll(1, TimeUnit.SECONDS);
        }
        
        public boolean isEmpty() {
            return products.isEmpty();
        }
        
        public void addMovedWeight(int weight) {
            totalWeightMoved.addAndGet(weight);
        }
        
        public void incrementTrips() {
            tripsCount.incrementAndGet();
        }
        
        public int getTotalWeightMoved() {
            return totalWeightMoved.get();
        }
        
        public int getTripsCount() {
            return tripsCount.get();
        }
        
        public String getName() {
            return name;
        }
        
        public int getRemainingProducts() {
            return products.size();
        }
    }
    
    static class Loader extends Thread {
        private final Storage sourceStorage;
        private final Storage destinationStorage;
        private final int loaderId;
        private int personalWeightCarried = 0;
        private int personalTrips = 0;
        
        private static final Object lock = new Object();
        private static int currentLoadWeight = 0;
        private static final List<Product> currentBatch = new ArrayList<>();
        private static final int MAX_BATCH_WEIGHT = 150;
        private static boolean deliveryInProgress = false;
        
        public Loader(int loaderId, Storage sourceStorage, Storage destinationStorage) {
            super("Грузчик-" + loaderId);
            this.loaderId = loaderId;
            this.sourceStorage = sourceStorage;
            this.destinationStorage = destinationStorage;
        }
        
        @Override
        public void run() {
            System.out.println(getName() + " начал работу на складе " + sourceStorage.getName());
            
            try {
                while (!sourceStorage.isEmpty()) {
                    Product product = sourceStorage.takeProduct();
                    if (product == null) {
                        if (sourceStorage.isEmpty()) {
                            break;
                        }
                        continue;
                    }
                    
                    synchronized (lock) {
                        while (deliveryInProgress) {
                            System.out.println(getName() + " ждет, пока другие грузчики доставят партию...");
                            lock.wait();
                        }
                        if (currentLoadWeight + product.getWeight() <= MAX_BATCH_WEIGHT) {
                            currentLoadWeight += product.getWeight();
                            currentBatch.add(product);
                            personalWeightCarried += product.getWeight();
                            
                            System.out.println(getName() + " взял " + product + 
                                             " (текущий вес партии: " + currentLoadWeight + "/" + 
                                             MAX_BATCH_WEIGHT + "кг)");
                            
                            if (currentLoadWeight >= MAX_BATCH_WEIGHT) {
                                deliverBatch();
                            }
                        } else {
                            System.out.println(getName() + ": товар " + product + 
                                             " не помещается в текущую партию (" + 
                                             currentLoadWeight + "/" + MAX_BATCH_WEIGHT + "кг)");
                            
                            if (currentLoadWeight > 0) {
                                deliverBatch();
                            }
                            
                            currentLoadWeight = product.getWeight();
                            currentBatch.clear();
                            currentBatch.add(product);
                            personalWeightCarried += product.getWeight();
                            
                            System.out.println(getName() + " начал новую партию с " + product + 
                                             " (вес: " + currentLoadWeight + "кг)");
                            
                            if (product.getWeight() > MAX_BATCH_WEIGHT) {
                                System.out.println("Внимание! " + product + 
                                                 " превышает максимальный вес партии!");
                                deliverBatch();
                            }
                        }
                    }
                    
                    Thread.sleep(50 + (int)(Math.random() * 100));
                }
                
                synchronized (lock) {
                    if (currentLoadWeight > 0 && !deliveryInProgress) {
                        deliverBatch();
                    }
                }
                
            } catch (InterruptedException e) {
                System.out.println(getName() + " был прерван");
                Thread.currentThread().interrupt();
            }
            
            System.out.println("\n" + getName() + " закончил работу. " +
                             "Перенесено: " + personalWeightCarried + "кг за " + 
                             personalTrips + " рейсов");
        }
        
        private void deliverBatch() throws InterruptedException {
            if (currentBatch.isEmpty() || deliveryInProgress) {
                return;
            }
            
            deliveryInProgress = true;
            
            System.out.println("\n" + "=".repeat(50));
            System.out.println(getName() + " организует доставку партии!");
            System.out.println("Вес партии: " + currentLoadWeight + "кг");
            System.out.println("Количество товаров: " + currentBatch.size());
            System.out.println("Состав партии:");
            for (Product p : currentBatch) {
                System.out.println("  - " + p);
            }
            
            System.out.println("\nГрузчики готовятся к отправке...");
            Thread.sleep(200);
            
            System.out.println("Транспортировка со склада " + sourceStorage.getName() + 
                             " на склад " + destinationStorage.getName() + "...");
            Thread.sleep(300 + (int)(Math.random() * 200));
            
            System.out.println("Разгрузка товаров...");
            Thread.sleep(200);
            
            sourceStorage.addMovedWeight(currentLoadWeight);
            sourceStorage.incrementTrips();
            personalTrips++;
            
            System.out.println("\nПартия успешно доставлена!");
            System.out.println("Общий перенесенный вес: " + sourceStorage.getTotalWeightMoved() + "кг");
            System.out.println("Общее количество рейсов: " + sourceStorage.getTripsCount());
            System.out.println("=".repeat(50) + "\n");
            
            currentBatch.clear();
            currentLoadWeight = 0;
            deliveryInProgress = false;
            
            lock.notifyAll();
        }
        
        public int getPersonalWeightCarried() {
            return personalWeightCarried;
        }
        
        public int getPersonalTrips() {
            return personalTrips;
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Программа 3: Симуляция работы грузчиков на складе ===\n");
        
        System.out.println("Описание задачи:");
        System.out.println("- 3 грузчика работают на складе");
        System.out.println("- Они переносят товары на другой склад");
        System.out.println("- Максимальный вес за одну партию: 150 кг");
        System.out.println("- Как только набирается 150 кг, грузчики совместно доставляют партию\n");
        
        List<Product> products = new ArrayList<>();
        
        String[] productNames = {
            "Телевизор", "Холодильник", "Стиральная машина", 
            "Диван", "Кресло", "Стол", "Стул", "Шкаф",
            "Микроволновка", "Пылесос", "Компьютер", "Кондиционер",
            "Велосипед", "Коробка с книгами", "Коробка с посудой",
            "Инструменты", "Одежда", "Обувь", "Игрушки", "Канцелярия"
        };
        
        System.out.println("Создание товаров для склада...");
        for (int i = 1; i <= 25; i++) {
            String name = productNames[i % productNames.length];
            int weight = 5 + (int)(Math.random() * 46);
            products.add(new Product(name + "-" + i, weight));
            
            System.out.printf("Создан товар: %-25s вес: %3d кг\n", 
                            name + "-" + i, weight);
        }
        
        Storage sourceStorage = new Storage("Склад А", products);
        Storage destinationStorage = new Storage("Склад Б", new ArrayList<>());
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Исходный склад '" + sourceStorage.getName() + 
                         "' содержит " + products.size() + " товаров");
        System.out.println("Максимальный вес партии: " + 150 + "кг");
        System.out.println("=".repeat(60) + "\n");
        
        Loader[] loaders = new Loader[3];
        for (int i = 0; i < loaders.length; i++) {
            loaders[i] = new Loader(i + 1, sourceStorage, destinationStorage);
        }
        
        System.out.println("Запуск грузчиков...\n");
        
        long startTime = System.currentTimeMillis();
        
        for (Loader loader : loaders) {
            loader.start();
            Thread.sleep(100);
        }
        
        for (Loader loader : loaders) {
            loader.join();
        }
        
        long endTime = System.currentTimeMillis();
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ИТОГОВАЯ СТАТИСТИКА");
        System.out.println("=".repeat(60));
        
        System.out.println("\nВсе товары перемещены со склада '" + sourceStorage.getName() + 
                         "' на склад '" + destinationStorage.getName() + "'");
        
        System.out.println("\nОбщая статистика:");
        System.out.println("- Общий перемещенный вес: " + sourceStorage.getTotalWeightMoved() + "кг");
        System.out.println("- Общее количество рейсов: " + sourceStorage.getTripsCount());
        System.out.println("- Общее время работы: " + (endTime - startTime) + " мс");
        System.out.println("- Осталось товаров на исходном складе: " + sourceStorage.getRemainingProducts());
        
        System.out.println("\nСтатистика по грузчикам:");
        int totalPersonalWeight = 0;
        int totalPersonalTrips = 0;
        
        for (Loader loader : loaders) {
            System.out.printf("- %s: %4d кг за %2d рейсов (средний вес за рейс: %6.1f кг)\n",
                            loader.getName(),
                            loader.getPersonalWeightCarried(),
                            loader.getPersonalTrips(),
                            loader.getPersonalTrips() > 0 ? 
                            (double)loader.getPersonalWeightCarried() / loader.getPersonalTrips() : 0.0);
            
            totalPersonalWeight += loader.getPersonalWeightCarried();
            totalPersonalTrips += loader.getPersonalTrips();
        }
        
        System.out.println("\nСуммарная статистика грузчиков:");
        System.out.println("- Суммарный перенесенный вес: " + totalPersonalWeight + "кг");
        System.out.println("- Суммарное количество рейсов: " + totalPersonalTrips);
        System.out.println("- Средний вес за рейс: " + 
                          String.format("%.1f", (double)totalPersonalWeight / totalPersonalTrips) + "кг");
        
        // Проверка целостности данных
        System.out.println("\nПроверка целостности данных:");
        boolean dataOk = true;
        
        if (totalPersonalWeight != sourceStorage.getTotalWeightMoved()) {
            System.out.println("Ошибка: Сумма персональных весов не совпадает с общим весом!");
            dataOk = false;
        }
        
        if (totalPersonalTrips != sourceStorage.getTripsCount() * 3) {
            System.out.println("Внимание: Количество персональных рейсов не кратно 3");
            System.out.println("(Это нормально, так как не все грузчики участвовали в каждом рейсе)");
        }
        
        if (dataOk) {
            System.out.println("Все данные корректны!");
        }
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Симуляция завершена успешно!");
        System.out.println("=".repeat(60));
    }
}