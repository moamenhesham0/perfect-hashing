package perfecthashing.hashing;

import java.util.*;

public class PerfectHashTableQuadratic implements IPerfectHashTable {


    private static final int DEFAULT_CAPACITY = 16;
    private static final int INITIAL_SIZE = 0;

    private String[] hashTable;
    private int size;
    private int capacity;
    private UniversalHashing hashFunction;


    /* Constructors */

     public PerfectHashTableQuadratic(int capacity)
    {
        this.capacity = capacity * capacity;
        this.size = INITIAL_SIZE;
        this.hashTable = new String[this.capacity];
        this.hashFunction = new UniversalHashing(Integer.SIZE, this.capacity);
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

    public String[] getHashTable()
    {
        return this.hashTable;
    }



    /* Reinsertion sub-routine in rehashing */

    private boolean reinsert(String key , String[] newHashTable)
    {
        final int index = this.hashFunction.hash(key);

        if (newHashTable[index] != null)
        {
            return false;
        }

        newHashTable[index] = key;
        return true;
    }


    /* Rehashes the hash table when a collision occurs.
       This is resets the hash set to a new hash set and reinserts the keys */

    private void rehash(String collisionKey)
    {
        boolean success = false;
        String[] newHashTable = new String[this.capacity];
        while (!success)
        {
            success = true;
            this.hashFunction = new UniversalHashing(Integer.SIZE, this.capacity);

            newHashTable = new String[capacity];

            for (String key : this.hashTable)
            {
                if(key == null)
                {
                    continue;
                }

                if(!this.reinsert(key , newHashTable))
                {
                    success = false;
                    break;
                }


            }

            if(collisionKey != null && success)
            {
                success &= this.reinsert(collisionKey , newHashTable);
            }
            
        }

        this.hashTable = newHashTable;
    }




    /* Resizes the capacity to ((2*size)^2) in order to keep the relation (capacity >= size^2) */

    private void resizeHashTable()
    {
        this.capacity = this.size * this.size * 4;
        this.rehash(null);
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


        // Resizes the hash table on capacity < size^2
        if(this.size * this.size >= this.capacity)
        {
            this.resizeHashTable();
        }

        ++this.size;

        if (this.hashTable[index] == null)
        {
            this.hashTable[index] = key;
        }
        else
        {
            this.rehash(key);
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