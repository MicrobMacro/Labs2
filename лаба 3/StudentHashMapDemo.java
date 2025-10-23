import java.util.HashMap;
import java.util.Map;

class Student {
    private String firstName;
    private String lastName;
    private int age;
    private double averageGrade;

    public Student(String firstName, String lastName, int age, double averageGrade) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.averageGrade = averageGrade;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    @Override
    public String toString() {
        return String.format("Студент: %s %s, Возраст: %d, Средний балл: %.2f", 
                           firstName, lastName, age, averageGrade);
    }
}
class StudentHashMap {
    private Map<String, Student> studentMap;

    public StudentHashMap() {
        this.studentMap = new HashMap<>();
    }
    public void insertStudent(String recordBookNumber, Student student) {
        if (recordBookNumber == null || recordBookNumber.trim().isEmpty()) {
            System.out.println("Ошибка: номер зачетной книжки не может быть пустым");
            return;
        }
        
        if (student == null) {
            System.out.println("Ошибка: объект студента не может быть null");
            return;
        }

        studentMap.put(recordBookNumber, student);
        System.out.println("Студент успешно добавлен/обновлен с номером зачетки: " + recordBookNumber);
    }
    public Student findStudent(String recordBookNumber) {
        if (recordBookNumber == null || recordBookNumber.trim().isEmpty()) {
            System.out.println("Ошибка: номер зачетной книжки не может быть пустым");
            return null;
        }

        Student student = studentMap.get(recordBookNumber);
        if (student == null) {
            System.out.println("Студент с номером зачетки '" + recordBookNumber + "' не найден");
        } else {
            System.out.println("Найден студент: " + student);
        }
        
        return student;
    }
    public boolean removeStudent(String recordBookNumber) {
        if (recordBookNumber == null || recordBookNumber.trim().isEmpty()) {
            System.out.println("Ошибка: номер зачетной книжки не может быть пустым");
            return false;
        }

        Student removedStudent = studentMap.remove(recordBookNumber);
        if (removedStudent != null) {
            System.out.println("Студент с номером зачетки '" + recordBookNumber + "' успешно удален");
            return true;
        } else {
            System.out.println("Студент с номером зачетки '" + recordBookNumber + "' не найден");
            return false;
        }
    }

    public void displayAllStudents() {
        if (studentMap.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Student> entry : studentMap.entrySet()) {
            System.out.println("Зачетка: " + entry.getKey() + " -> " + entry.getValue());
        }
        System.out.println("Общее количество студентов: " + studentMap.size());
    }
    public boolean containsStudent(String recordBookNumber) {
        return studentMap.containsKey(recordBookNumber);
    }
    public int getStudentCount() {
        return studentMap.size();
    }
}
public class StudentHashMapDemo {
    public static void main(String[] args) {
        StudentHashMap studentDatabase = new StudentHashMap();
        Student student1 = new Student("Иван", "Иванов", 20, 4.5);
        Student student2 = new Student("Петр", "Петров", 21, 4.2);
        Student student3 = new Student("Мария", "Сидорова", 19, 4.8);
        Student student4 = new Student("Анна", "Кузнецова", 22, 4.1);
        studentDatabase.insertStudent("2025001", student1);
        studentDatabase.insertStudent("2025002", student2);
        studentDatabase.insertStudent("2025003", student3);
        studentDatabase.insertStudent("2025004", student4);
        Student updatedStudent = new Student("Иван", "Иванов", 20, 4.7);
        studentDatabase.insertStudent("2025001", updatedStudent);
        studentDatabase.findStudent("2025002"); // Существующий
        studentDatabase.findStudent("2025999"); // Несуществующий
        studentDatabase.displayAllStudents();
        studentDatabase.removeStudent("2025003"); // Существующий
        studentDatabase.removeStudent("2025999"); // Несуществующий
        System.out.println("Студент с зачеткой '2025001' существует: " + 
                          studentDatabase.containsStudent("2025001"));
        System.out.println("Студент с зачеткой '2025003' существует: " + 
                          studentDatabase.containsStudent("2025003"));
        studentDatabase.displayAllStudents();
        System.out.println("Общее количество студентов: " + studentDatabase.getStudentCount());
    }
}