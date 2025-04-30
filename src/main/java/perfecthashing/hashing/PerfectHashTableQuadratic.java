package perfecthashing.hashing;

import java.util.*;

public class PerfectHashTableQuadratic implements IPerfectHashTable {


    private static final double LOG_2 = Math.log(2);

    private String[] hashTable;
    private List<String> keys;
    private int size;
    private int capacity;
    private UniversalHashing hashFunction;

    /* Computes the number of bits needed in the hash function matrix row dimension */

    public static final int computeUBits(int capacity)
    {
        return (int) Math.floor(Math.log(capacity) / LOG_2);
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

        String[] newHashTable = new String[newCapacity];

        for(int i = 0 ; i<this.capacity ; ++i)
        {
            newHashTable[i] = this.hashTable[i];
        }

        this.capacity = newCapacity;
        this.hashTable = newHashTable;
    }



    public PerfectHashTableQuadratic(List<String> keys)
    {

        final int capacity = keys.size();

        this.capacity = capacity * capacity;
        this.size = 0;
        this.hashTable = new String[this.capacity];
        this.keys = new ArrayList<>();
        this.hashFunction = new UniversalHashing(Integer.SIZE, PerfectHashTableQuadratic.computeUBits(capacity));

        for(String key : keys)
        {
            this.insert(key);
        }

    }

    public PerfectHashTableQuadratic(int capacity)
    {
        this.capacity = capacity * capacity;
        this.size = 0;
        this.hashTable = new String[this.capacity];
        this.keys = new ArrayList<>();
        this.hashFunction = new UniversalHashing(Integer.SIZE, computeUBits(capacity));
    }

    /* Default Constructor */
    public PerfectHashTableQuadratic()
    {
        this(0);
    }





    public boolean insert(String key) {
        int index = this.hashFunction.hash(key);

        // Returns false on existing dictionary entry
        if (this.hashTable[index] != null && this.hashTable[index].equals(key))
        {
            return false;
        }


        keys.add(key);
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

    public boolean delete(String key) {
        int index = this.hashFunction.hash(key);

        if (this.hashTable[index] != null && this.hashTable[index].equals(key))
        {
            this.hashTable[index] = null;
            --this.size;
        }
        else
        {
            return false;
        }

        return true;
    }

    public boolean search(String key) {
        int index = this.hashFunction.hash(key);

        if (this.hashTable[index] != null && this.hashTable[index].equals(key))
        {
            return true;
        }

        return false;
    }
}