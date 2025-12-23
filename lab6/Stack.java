package lab6;
public class Stack<T> {
    private T[] data;
    private int size;
    public Stack(int capacity) {
        data = (T[]) new Object[capacity];
        size = 0;
    }
    public void push(T element) {
        if (size == data.length) {
            throw new RuntimeException("Стек переполнен");
        }
        data[size] = element;
        size++;
    }
    public T pop() {
        if (size == 0) {
            throw new RuntimeException("Стек пуст");
        }
        T element = data[size - 1];
        data[size - 1] = null;
        size--;
        return element;
    }
    public T peek() {
        if (size == 0) {
            throw new RuntimeException("Стек пуст");
        }
        return data[size - 1];
    }
}
