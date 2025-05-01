package perfecthashing.hashing;

import perfecthashing.hashing.PerfectHashTableQuadratic;
import java.util.*;
public class PerfectHashTableLinear implements IPerfectHashTable {

    private static final int DEFAULT_CAPACITY = 50;
    private static final int INITIAL_SIZE = 0;

    private PerfectHashTableQuadratic[] hashTable;
    private int capacity;
    private int size;
    private UniversalHashing hashFunction;



    public PerfectHashTableLinear(int capacity)
    {
        this.capacity = capacity;
        this.size = INITIAL_SIZE;
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
            insert(key);
        }
    }


    /* Default Constructor */

    public PerfectHashTableLinear()
    {
        this(DEFAULT_CAPACITY);
    }



    private void resizeHashTable()
    {
        int newCapacity = this.size * 2;
        PerfectHashTableQuadratic[] newHashTable = Arrays.copyOf(this.hashTable, newCapacity);
        this.hashTable = newHashTable;
        this.capacity = newCapacity;
    }





    @Override
    public boolean insert(String key)
    {
        if (this.size >= this.capacity)
        {
            this.resizeHashTable();
        }

        int index = hashFunction.hash(key) % capacity;

        if(this.hashTable[index].insert(key))
        {
            ++this.size;
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(String key)
    {
        int index = hashFunction.hash(key) % capacity;

        if (this.hashTable[index].delete(key))
        {
            --this.size;
            return true;
        }

        return false;
    }

    @Override
    public boolean search(String key)
    {
        int index = hashFunction.hash(key) % capacity;
        return this.hashTable[index].search(key);
    }






}
