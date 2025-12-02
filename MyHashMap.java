import java.util.Objects;

public class MyHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 32;
    private final Node<K, V>[] buckets;

    @SuppressWarnings("unchecked")
    public MyHashMap() {
        buckets = (Node<K, V>[]) new Node[DEFAULT_CAPACITY];
    }

    private static final class Node<K, V> {
        final K key;
        V value;
        Node<K, V> next;
        Node(K k, V v, Node<K, V> n) { key = k; value = v; next = n; }
    }

    private int indexFor(Object key) {
        return (Objects.hashCode(key) & 0x7fffffff) % buckets.length;
    }

    public void put(K key, V value) {
        int idx = indexFor(key);
        Node<K, V> node = buckets[idx];
        for (; node != null; node = node.next) {
            if (Objects.equals(node.key, key)) {
                node.value = value;
                return;
            }
        }
        buckets[idx] = new Node<>(key, value, buckets[idx]);
    }

    public V get(K key) {
        int idx = indexFor(key);
        Node<K, V> node = buckets[idx];
        for (; node != null; node = node.next) {
            if (Objects.equals(node.key, key)) return node.value;
        }
        return null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public V remove(K key) {
        int idx = indexFor(key);
        Node<K, V> node = buckets[idx];
        Node<K, V> prev = null;
        while (node != null) {
            if (Objects.equals(node.key, key)) {
                if (prev == null) buckets[idx] = node.next;
                else prev.next = node.next;
                return node.value;
            }
            prev = node;
            node = node.next;
        }
        return null;
    }
}