package perfecthashing.hashing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class PerfectHashTableLinear implements IPerfectHashTable {

    private static final int DEFAULT_CAPACITY = 50;
    private static final int INITIAL_SIZE = 0;

    private PerfectHashTableQuadratic[] hashTable;
    private int capacity;
    private int size;
    private List<String> keys;
    private UniversalHashing hashFunction;



    public PerfectHashTableLinear(final int capacity)
    {
        this.capacity = capacity;
        this.size = INITIAL_SIZE;
        this.keys = new ArrayList();
        this.hashTable = new PerfectHashTableQuadratic[this.capacity];
        this.hashFunction = new UniversalHashing(Integer.SIZE, PerfectHashTableQuadratic.computeUBits(this.capacity));

        for(PerfectHashTableQuadratic perfectHashTable : hashTable)
        {
            perfectHashTable = new PerfectHashTableQuadratic();
        }
    }

    public PerfectHashTableLinear(final List<String> keys)
    {
        this(keys.size());

        for(final String key : keys)
        {
            insert(key);
        }
    }


    /* Default Constructor */

    public PerfectHashTableLinear()
    {
        this(DEFAULT_CAPACITY);
    }


    private void rehash()
    {
        this.hashFunction = new UniversalHashing(Integer.SIZE, PerfectHashTableQuadratic.computeUBits(this.capacity));

        for(String key : this.keys)
        {
            this.insert(key);
        }
    }

    private void resizeHashTable()
    {
        final int newCapacity = this.size * 2;
        PerfectHashTableQuadratic[] newHashTable = new PerfectHashTableQuadratic[newCapacity];

        for(int i = 0; i < newCapacity; i++)
        {
            newHashTable[i] = new PerfectHashTableQuadratic();
        }

        this.hashTable = newHashTable;
        this.capacity = newCapacity;
        this.size = INITIAL_SIZE;

        this.rehash();
    }


    @Override
    public boolean insert(final String key)
    {
        if (this.size >= this.capacity)
        {
            this.resizeHashTable();
        }

        final int index = hashFunction.hash(key) % capacity;

        if(this.hashTable[index].insert(key))
        {
            ++this.size;
            this.keys.add(key);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(final String key)
    {
        final int index = hashFunction.hash(key) % capacity;

        if (this.hashTable[index].delete(key))
        {
            --this.size;
            this.keys.remove(key);
            return true;
        }

        return false;
    }

    @Override
    public boolean search(final String key)
    {
        final int index = hashFunction.hash(key) % capacity;
        return this.hashTable[index].search(key);
    }






}
