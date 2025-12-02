
public class MyHashSet {
    private final MyHashMap<Integer, Boolean> map = new MyHashMap<>();

    public void add(int value) { map.put(value, Boolean.TRUE); }
    public boolean contains(int value) { return map.containsKey(value); }
    public void remove(int value) { map.remove(value); }
    public void clear() {
        for (int i = 1; i <= 9; i++) map.remove(i);
    }
}