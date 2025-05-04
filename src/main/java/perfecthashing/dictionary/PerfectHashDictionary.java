package perfecthashing.dictionary;

import java.util.*;

import perfecthashing.hashing.*;


public class PerfectHashDictionary {
    private IPerfectHashTable backend;
    private String type;

    public PerfectHashDictionary(String type, List<String> keys) {
        this.type = type;
        build(keys);
    }

    private void build(List<String> keys) {

        if (type.equals("quadratic"))
        {
            backend = (!keys.isEmpty()) ? new PerfectHashTableQuadratic(keys) : new PerfectHashTableQuadratic();
        }
        else
        {
            backend = (!keys.isEmpty()) ? new PerfectHashTableLinear(keys) : new PerfectHashTableLinear();
        }

    }

    public boolean search(String key) {
        return backend.search(key);
    }

    public boolean delete(String key) {
        return backend.delete(key);
    }

    public boolean insert(String key) {
        return backend.insert(key);
    }

    public boolean batchInsert(final String filePath) {
        // TODO: Implement a function to read from a file and insert
        throw new UnsupportedOperationException("Batch insert from file is coming in a future release");
    }

    public boolean batchDelete(final String filePath) {
        // TODO: Implement a function to read from a file and delete
        throw new UnsupportedOperationException("Batch delete from file is coming in a future release");
    }
}
