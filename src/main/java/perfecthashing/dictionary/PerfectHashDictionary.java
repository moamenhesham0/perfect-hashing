package perfecthashing.dictionary;

import java.util.*;

import perfecthashing.hashing.*;

public class PerfectHashDictionary {
    private IPerfectHashTable backend;
    private List<String> keys;
    private String type;

    public PerfectHashDictionary(String type, List<String> keys) {
        this.type = type;
        this.keys = new ArrayList<>(keys);
        build();
    }

    private void build() {
        if (type.equals("quadratic")) {
            backend = new PerfectHashTableQuadratic(keys);
        } else {
            backend = new PerfectHashTableLinear(keys);
        }
    }

    public boolean search(String key) {
        return backend.search(key);
    }

    public boolean delete(String key) {
        return backend.delete(key);
    }
}
