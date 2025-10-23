import java.util.LinkedList;
public class HashTable<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private LinkedList<Entry<K, V>>[] table;
    private int size = 0;
    public HashTable() {
        table = new LinkedList[DEFAULT_CAPACITY];
    }
    private int hash(K key) {
        return Math.abs(key.hashCode()) % table.length;
    }
    public void put(K key, V value) {
        int index = hash(key);
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }
        for (Entry<K, V> entry : table[index]) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return;
            }
        }
        table[index].add(new Entry<>(key, value));
        size++;
    }
    public V get(K key) {
        int index = hash(key);
        if (table[index] == null) return null;
        for (Entry<K, V> entry : table[index]) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }
    public boolean remove(K key) {
        int index = hash(key);
        if (table[index] == null) return false;

        for (Entry<K, V> entry : table[index]) {
            if (entry.getKey().equals(key)) {
                table[index].remove(entry);
                size--;
                return true;
            }
        }
        return false;
    }
    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }
    private static class Entry<K, V> {
        private final K key;
        private V value;
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        public K getKey() { return key; }
        public V getValue() { return value; }
        public void setValue(V value) { this.value = value; }
    }
    public static void main(String[] args) {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("one", 1);
        table.put("two", 2);
        table.put("three", 3);
        System.out.println("two -> " + table.get("two"));
        table.remove("two");
        System.out.println("Contains 'two'? " + (table.get("two") != null));
        System.out.println("Size: " + table.size());
        System.out.println("Empty? " + table.isEmpty());
    }
}
