import java.util.Scanner;
abstract class Animal {
    protected String name;
    protected int age;
    protected double weight;
    
    // Кол-во животных
    protected static int animalCount = 0;
    
    public Animal() {
        animalCount++;
    }
    
    public Animal(String name, int age, double weight) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        animalCount++;
    }
    
    public abstract void makeSound();
    public abstract void eat();
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public double getWeight() {
        return weight;
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    public static int getAnimalCount() {
        return animalCount;
    }
    
    public void displayInfo() {
        System.out.println("Имя: " + name);
        System.out.println("Возраст: " + age + " лет");
        System.out.println("Вес: " + weight + " кг");
    }
}

// Первый уровень наследования - Кошка
abstract class Cat extends Animal {
    protected boolean hasFur;
    protected String habitat;
    
    public Cat() {
        super();
    }
    
    public Cat(String name, int age, double weight, boolean hasFur, String habitat) {
        super(name, age, weight);
        this.hasFur = hasFur;
        this.habitat = habitat;
    }
    
    public abstract void nurse();
    
    public boolean hasFur() {
        return hasFur;
    }
    
    public void setHasFur(boolean hasFur) {
        this.hasFur = hasFur;
    }
    
    public String getHabitat() {
        return habitat;
    }
    
    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Имеет мех: " + (hasFur ? "Да" : "Нет"));
        System.out.println("Среда обитания: " + habitat);
    }
}

// Второй уровень наследования - Домашняя кошка (наследуется от Cat)
class DomesticCat extends Cat {
    private String breed;
    private boolean isVaccinated;
    private String favoriteToy;
    
    public DomesticCat() {
        super();
    }
    
    public DomesticCat(String name, int age, double weight, 
                      boolean hasFur, String habitat, String breed, boolean isVaccinated) {
        super(name, age, weight, hasFur, habitat);
        this.breed = breed;
        this.isVaccinated = isVaccinated;
        this.favoriteToy = "мышка";
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + " нежно мурлычет: Мррр-мяу!");
    }
    
    @Override
    public void eat() {
        System.out.println(name + " кушает премиальный кошачий корм");
        weight += 0.08;
    }
    
    @Override
    public void nurse() {
        System.out.println(name + " выкармливает котят молоком");
    }
    
    public void playWithToy() {
        System.out.println(name + " играет с любимой игрушкой: " + favoriteToy);
    }
    
    public void sleepOnCouch() {
        System.out.println(name + " сладко спит на диване");
    }
    
    public String getBreed() {
        return breed;
    }
    
    public void setBreed(String breed) {
        this.breed = breed;
    }
    
    public boolean isVaccinated() {
        return isVaccinated;
    }
    
    public void setVaccinated(boolean vaccinated) {
        isVaccinated = vaccinated;
    }
    
    public String getFavoriteToy() {
        return favoriteToy;
    }
    
    public void setFavoriteToy(String favoriteToy) {
        this.favoriteToy = favoriteToy;
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Порода: " + breed);
        System.out.println("Вакцинирована: " + (isVaccinated ? "Да" : "Нет"));
        System.out.println("Любимая игрушка: " + favoriteToy);
    }
}

// Второй уровень наследования - Дикая кошка (наследуется от Cat)
class WildCat extends Cat {
    private String huntingStyle;
    private boolean isEndangered;
    private double territorySize;
    
    public WildCat() {
        super();
    }
    
    public WildCat(String name, int age, double weight, 
                  boolean hasFur, String habitat, String huntingStyle, boolean isEndangered, double territorySize) {
        super(name, age, weight, hasFur, habitat);
        this.huntingStyle = huntingStyle;
        this.isEndangered = isEndangered;
        this.territorySize = territorySize;
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + " издает грозный рык: РРРРР!");
    }
    
    @Override
    public void eat() {
        System.out.println(name + " охотится и питается добычей");
        weight += 0.5;
    }
    
    @Override
    public void nurse() {
        System.out.println(name + " заботится о детенышах в логове");
    }
    
    public void hunt() {
        System.out.println(name + " охотится используя технику: " + huntingStyle);
    }
    
    public void markTerritory() {
        System.out.println(name + " метит территорию размером " + territorySize + " км²");
    }
    
    public String getHuntingStyle() {
        return huntingStyle;
    }
    
    public void setHuntingStyle(String huntingStyle) {
        this.huntingStyle = huntingStyle;
    }
    
    public boolean isEndangered() {
        return isEndangered;
    }
    
    public void setEndangered(boolean endangered) {
        isEndangered = endangered;
    }
    
    public double getTerritorySize() {
        return territorySize;
    }
    
    public void setTerritorySize(double territorySize) {
        this.territorySize = territorySize;
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Стиль охоты: " + huntingStyle);
        System.out.println("Исчезающий вид: " + (isEndangered ? "Да" : "Нет"));
        System.out.println("Размер территории: " + territorySize + " км²");
    }
}

//Попугай
class Parrot extends Animal {
    private String color;
    private boolean canTalk;
    private int vocabularySize;
    
    public Parrot() {
        super();
    }
    
    public Parrot(String name, int age, double weight, String color, boolean canTalk) {
        super(name, age, weight);
        this.color = color;
        this.canTalk = canTalk;
        this.vocabularySize = 0;
    }
    
    @Override
    public void makeSound() {
        if (canTalk) {
            System.out.println(name + " говорит: Привет! Хорошего дня!");
        } else {
            System.out.println(name + " издает звуки: Чик-чирик!");
        }
    }
    
    @Override
    public void eat() {
        System.out.println(name + " кушает зерно и фрукты");
        weight += 0.05;
    }
    
    public void learnWord(String word) {
        vocabularySize++;
        System.out.println(name + " выучил новое слово: '" + word + "'");
    }
    
    public void fly() {
        System.out.println(name + " летает по комнате");
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public boolean canTalk() {
        return canTalk;
    }
    
    public void setCanTalk(boolean canTalk) {
        this.canTalk = canTalk;
    }
    
    public int getVocabularySize() {
        return vocabularySize;
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Цвет: " + color);
        System.out.println("Умеет говорить: " + (canTalk ? "Да" : "Нет"));
        System.out.println("Размер словарного запаса: " + vocabularySize + " слов");
    }
}

// Рыбка
class Fish extends Animal {
    private String waterType;
    private double tankSize;
    private int swimSpeed;
    
    public Fish() {
        super();
    }
    
    public Fish(String name, int age, double weight, String waterType, double tankSize) {
        super(name, age, weight);
        this.waterType = waterType;
        this.tankSize = tankSize;
        this.swimSpeed = 2;
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + " издает звуки: ... (рыбы не издают звуков)");
    }
    
    @Override
    public void eat() {
        System.out.println(name + " кушает рыбный корм");
        weight += 0.02;
    }
    
    public void swim() {
        System.out.println(name + " плавает со скоростью " + swimSpeed + " м/с");
    }
    
    public void blowBubbles() {
        System.out.println(name + " пускает пузырики");
    }
    
    public String getWaterType() {
        return waterType;
    }
    
    public void setWaterType(String waterType) {
        this.waterType = waterType;
    }
    
    public double getTankSize() {
        return tankSize;
    }
    
    public void setTankSize(double tankSize) {
        this.tankSize = tankSize;
    }
    
    public int getSwimSpeed() {
        return swimSpeed;
    }
    
    public void setSwimSpeed(int swimSpeed) {
        this.swimSpeed = swimSpeed;
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Тип воды: " + waterType);
        System.out.println("Размер аквариума: " + tankSize + " литров");
        System.out.println("Скорость плавания: " + swimSpeed + " м/с");
    }
}

// Демонстрационный класс
public class AnimalHierarchyDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Демонстрация полной иерархии классов Животные ===\n");
        Animal[] animals = new Animal[5];
        System.out.println("=== ПЕРВЫЙ УРОВЕНЬ НАСЛЕДОВАНИЯ ===");
        
        // Попугай (первый уровень)
        System.out.println("\nСоздание попугая (уровень 1):");
        Parrot parrot = new Parrot("Кеша", 2, 0.3, "Зеленый", true);
        parrot.displayInfo();
        parrot.makeSound();
        animals[0] = parrot;
        
        // Рыбка (первый уровень)
        System.out.println("\nСоздание рыбки (уровень 1):");
        Fish fish = new Fish("Немо", 1, 0.1, "Морская", 50.0);
        fish.displayInfo();
        fish.makeSound();
        animals[1] = fish;
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        System.out.println("=== ВТОРОЙ УРОВЕНЬ НАСЛЕДОВАНИЯ ===");
        
        // Домашняя кошка (второй уровень)
        System.out.println("\nСоздание домашней кошки (уровень 2):");
        DomesticCat domesticCat = new DomesticCat("Барсик", 4, 5.2, true, "дом", "Британская", true);
        domesticCat.displayInfo();
        domesticCat.makeSound();
        domesticCat.eat();
        domesticCat.nurse();
        domesticCat.playWithToy();
        animals[2] = domesticCat;
        
        // Дикая кошка (второй уровень)
        System.out.println("\nСоздание дикой кошки (уровень 2):");
        WildCat wildCat = new WildCat("Багира", 6, 45.0, true, "джунгли", "засада", false, 25.5);
        wildCat.displayInfo();
        wildCat.makeSound();
        wildCat.eat();
        wildCat.nurse();
        wildCat.hunt();
        animals[3] = wildCat;
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        System.out.println("=== ДЕМОНСТРАЦИЯ ПОЛИМОРФИЗМА ===");
        for (Animal animal : animals) {
            if (animal != null) {
                System.out.println("\n--- " + animal.getClass().getSimpleName() + ": " + animal.getName() + " ---");
                animal.makeSound();
                animal.eat();
                
                if (animal instanceof Cat) {
                    ((Cat) animal).nurse();
                }
            }
        }
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        System.out.println("Всего создано животных: " + Animal.getAnimalCount());
        
        // Демонстрация инкапсуляции
        System.out.println("\nДемонстрация инкапсуляции:");
        domesticCat.setFavoriteToy("перьевая удочка");
        System.out.println("Новая любимая игрушка домашней кошки: " + domesticCat.getFavoriteToy());
        wildCat.setTerritorySize(30.2);
        System.out.println("Новый размер территории дикой кошки: " + wildCat.getTerritorySize() + " км²");
        
        scanner.close();
    }
}