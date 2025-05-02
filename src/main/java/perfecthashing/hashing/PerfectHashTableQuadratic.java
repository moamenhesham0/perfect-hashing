package perfecthashing.hashing;

import java.util.*;

public class PerfectHashTableQuadratic implements IPerfectHashTable {


    private static final int DEFAULT_CAPACITY = 100;
    private static final int INITIAL_SIZE = 0;

    private String[] hashTable;
    private List<String> keys;
    private int size;
    private int capacity;
    private UniversalHashing hashFunction;


    /* Constructors */

     public PerfectHashTableQuadratic(int capacity)
    {
        this.capacity = capacity * capacity;
        this.size = INITIAL_SIZE;
        this.hashTable = new String[this.capacity];
        this.keys = new ArrayList<>();
        this.hashFunction = new UniversalHashing(Integer.SIZE, computeUBits(capacity));
    }

    public PerfectHashTableQuadratic(List<String> keys)
    {
        this(keys.size());

        for(String key : keys)
        {
            insert(key);
        }
    }

    /* Default Constructor */

    public PerfectHashTableQuadratic()
    {
        this(DEFAULT_CAPACITY);
    }


    /* Computes the number of bits needed in the hash function matrix row dimension */

    public static final int computeUBits(int capacity)
    {
        return (int) Math.floor(Math.log(capacity) / Math.log(2));
    }


    /* Rehashes the hash table when a collision occurs.
       This is resets the hash set to a new hash set and reinserts the keys */

    private void rehash()
    {
        int newSize = 0;
        while (newSize < this.size)
        {
            newSize = 0;

            this.hashFunction = new UniversalHashing(Integer.SIZE, computeUBits(capacity));

            this.hashTable = new String[capacity];

            for (String key : keys)
            {
                int index = hashFunction.hash(key);

                if (this.hashTable[index] == null)
                {
                    this.hashTable[index] = key;
                    ++newSize;
                }
                else
                {
                    break;
                }

            }

        }
    }


    /* Resizes the capacity to keep the relation (capacity >= size^2) */

    private void resizeHashTable()
    {
        int newCapacity = this.size * this.size;

        String[] newHashTable = Arrays.copyOf(this.hashTable, newCapacity);

        this.capacity = newCapacity;
        this.hashTable = newHashTable;
    }



    @Override
    public boolean insert(String key)
    {
        int index = this.hashFunction.hash(key);

        // Returns false on existing dictionary entry
        if (this.hashTable[index] != null && this.hashTable[index].equals(key))
        {
            return false;
        }

        this.keys.add(key);
        ++this.size;

        // Resizes the hash table on capacity < size^2
        if(this.size * this.size > this.capacity)
        {
            this.resizeHashTable();
        }


        if (this.hashTable[index] == null)
        {
            this.hashTable[index] = key;
        }
        else
        {
            this.rehash();
        }

        return true;
    }

    @Override
    public boolean delete(String key)
    {
        int index = this.hashFunction.hash(key);

        if (this.hashTable[index] != null && this.hashTable[index].equals(key))
        {
            this.hashTable[index] = null;
            --this.size;
            this.keys.remove(key);
            return true;
        }

        return false;
    }

    @Override
    public boolean search(String key)
    {
        int index = this.hashFunction.hash(key);
        return (this.hashTable[index] != null && this.hashTable[index].equals(key));
    }
}