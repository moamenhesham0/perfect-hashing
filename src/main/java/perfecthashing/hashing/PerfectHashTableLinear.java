package perfecthashing.hashing;

import perfecthashing.hashing.PerfectHashTableQuadratic;
import java.util.*;
public class PerfectHashTableLinear implements IPerfectHashTable {


    private PerfectHashTableQuadratic[] hashTable;
    private int capacity;
    private UniversalHashing hashFunction;



    public PerfectHashTableLinear(List<String> keys)
    {

        final int capacity = keys.size();

        this.capacity = capacity * capacity;
        this.hashTable = new PerfectHashTableQuadratic[this.capacity];
        this.hashFunction = new UniversalHashing(Integer.SIZE, PerfectHashTableQuadratic.computeUBits(capacity));

        for(PerfectHashTableQuadratic perfectHashTable : hashTable)
        {
            perfectHashTable = new PerfectHashTableQuadratic();
        }

        for(String key : keys)
        {
            this.insert(key);
        }

    }




    public boolean insert(String key)
    {
        int index = hashFunction.hash(key) % capacity;
        return this.hashTable[index].insert(key);
    }


    public boolean delete(String key)
    {
        int index = hashFunction.hash(key) % capacity;
        return this.hashTable[index].delete(key);
    }

    public boolean search(String key)
    {
        int index = hashFunction.hash(key) % capacity;
        return this.hashTable[index].search(key);
    }






}
