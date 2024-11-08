package model.adt;

import model.exception.DictionaryException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<K, V> implements IMyDictionary<K, V>{
    private Map<K, V> map;

    public MyDictionary() { this.map = new HashMap<K, V>(); }

    @Override
    public void insert(K k, V v) {
        this.map.put(k, v);
    }

    @Override
    public void remove(K k) throws DictionaryException {
        if (!this.map.containsKey(k)) throw new DictionaryException("Key doesn't exist!\n");
        this.map.remove(k);
    }

    @Override
    public boolean contains(K k) {
        return this.map.containsKey(k);
    }

    @Override
    public V lookup(K k) throws DictionaryException {
        if (!this.map.containsKey(k)) throw new DictionaryException("Key doesn't exist!\n");
        return this.map.get(k);
    }

    @Override
    public Set<K> keys() {
        return this.map.keySet();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        this.map.forEach((k, v) -> {
            str.append(k).append("->").append(v).append("\n");
        });
        return "SymTable:\n" + str;
    }
}
