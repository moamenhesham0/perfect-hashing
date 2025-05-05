package perfecthashing.hashing;

import java.util.List;

public class PerfectHashTableQuadratic implements IPerfectHashTable {


    private static final int DEFAULT_CAPACITY = 16;
    private static final int INITIAL_SIZE = 0;

    private String[] hashTable;
    private int size;
    private int capacity;
    private int collisions;
    private long rehashingTrials;
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
            this.insert(key);
        }
    }

    /* Default Constructor */

    public PerfectHashTableQuadratic()
    {
        this(DEFAULT_CAPACITY);
    }


    /* Getters */

    public String[] getHashTable()
    {
        return this.hashTable;
    }

    public int getSize()
    {
        return this.size;
    }

    public int getCapacity()
    {
        return this.capacity;
    }

    public int getCollisions()
    {
        return this.collisions;
    }

    public double getUsageRatio()
    {
        return ((double)this.size / this.capacity *1e2);
    }

    public long getRehashingTrials()
    {
        return this.rehashingTrials;
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
        do
        {
            ++this.rehashingTrials;
            success = true;
            this.hashFunction = new UniversalHashing(Integer.SIZE, this.capacity);

            newHashTable = new String[this.capacity];

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

            if(collisionKey != null)
            {
                success &= this.reinsert(collisionKey , newHashTable);
            }

        }
        while (!success);
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

        // Resizes the hash table on capacity < size^2
        if(this.size * this.size >= this.capacity)
        {
            this.resizeHashTable();
        }

        int index = this.hashFunction.hash(key);

        // Returns false on existing dictionary entry
        if (this.hashTable[index] != null && this.hashTable[index].equals(key))
        {
            return false;
        }


        if (this.hashTable[index] == null)
        {

            this.hashTable[index] = key;
        }
        else
        {
            ++this.collisions;
            this.rehash(key);
        }

        ++this.size;
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

    public int getIndex(String key)
    {
        return this.hashFunction.hash(key);
    }
}