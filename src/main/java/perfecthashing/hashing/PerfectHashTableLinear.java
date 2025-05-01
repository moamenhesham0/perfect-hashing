package perfecthashing.hashing;

import perfecthashing.hashing.PerfectHashTableQuadratic;
import java.util.*;
public class PerfectHashTableLinear implements IPerfectHashTable {

    private static final int DEFAULT_CAPACITY = 50;

    private PerfectHashTableQuadratic[] hashTable;
    private int capacity;
    private UniversalHashing hashFunction;



    public PerfectHashTableLinear(int capacity)
    {

        this.capacity = capacity;
        this.hashTable = new PerfectHashTableQuadratic[this.capacity];
        this.hashFunction = new UniversalHashing(Integer.SIZE, PerfectHashTableQuadratic.computeUBits(this.capacity));

        for(PerfectHashTableQuadratic perfectHashTable : hashTable)
        {
            perfectHashTable = new PerfectHashTableQuadratic();
        }
    }

    public PerfectHashTableLinear(List<String> keys)
    {
        this(keys.size());

        for(String key : keys)
        {
            this.insert(key);
        }
    }


    /* Default Constructor */

    public PerfectHashTableLinear()
    {
        this(DEFAULT_CAPACITY);
    }





    @Override
    public boolean insert(String key)
    {
        int index = hashFunction.hash(key) % capacity;
        return this.hashTable[index].insert(key);
    }

    @Override
    public boolean delete(String key)
    {
        int index = hashFunction.hash(key) % capacity;
        return this.hashTable[index].delete(key);
    }

    @Override
    public boolean search(String key)
    {
        int index = hashFunction.hash(key) % capacity;
        return this.hashTable[index].search(key);
    }






}
