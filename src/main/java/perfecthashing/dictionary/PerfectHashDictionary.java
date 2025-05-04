package perfecthashing.dictionary;

import java.util.*;
import java.io.*;

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

    public int[] batchInsert(final String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int newlyAdded = 0;
            int alreadyExisting = 0;

            while ((line = reader.readLine()) != null) {
                String key = line.trim();
                if (!key.isEmpty()) {
                    if (insert(key)) {
                        newlyAdded++;
                    } else {
                        alreadyExisting++;
                    }
                }

                if(newlyAdded % 1 == 0) {
                    System.out.println("Inserted " + newlyAdded + " keys so far...");
                }
            }

            return new int[] { newlyAdded, alreadyExisting };
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
            return null;
        }
    }

    public int[] batchDelete(final String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int deleted = 0;
            int nonExisting = 0;

            while ((line = reader.readLine()) != null) {
                String key = line.trim();
                if (!key.isEmpty()) {
                    if (delete(key)) {
                        deleted++;
                    } else {
                        nonExisting++;
                    }
                }
            }

            return new int[] { deleted, nonExisting };
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
            return null;
        }
    }
}
