package perfecthashing.hashing;

import java.util.List;

public class PerfectQuadraticHashSet implements IPerfectHashSet {


    private static final int DEFAULT_CAPACITY = 16;
    private static final int INITIAL_SIZE = 0;

    private String[] hashSet;
    private int size;
    private int capacity;
    private int collisions;
    private long rehashingTrials;
    private UniversalHashing hashFunction;



    /* Constructors */

     public PerfectQuadraticHashSet(int capacity)
    {
        this.capacity = capacity * capacity;
        this.size = INITIAL_SIZE;
        this.hashSet = new String[this.capacity];
        this.hashFunction = new UniversalHashing(Integer.SIZE, this.capacity);
    }

    public PerfectQuadraticHashSet(List<String> keys)
    {
        this(keys.size());

        for(String key : keys)
        {
            this.insert(key);
        }
    }

    /* Default Constructor */

    public PerfectQuadraticHashSet()
    {
        this(DEFAULT_CAPACITY);
    }


    /* Getters */

    public String[] getHashSet()
    {
        return this.hashSet;
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

    private boolean reinsert(String key , String[] newHashSet)
    {
        final int index = this.hashFunction.hash(key);

        if (newHashSet[index] != null)
        {
            return false;
        }

        newHashSet[index] = key;
        return true;
    }


    /* Rehashes the hash table when a collision occurs.
       This is resets the hash set to a new hash set and reinserts the keys */

    private void rehash(String collisionKey)
    {
        boolean success = false;
        String[] newHashSet = new String[this.capacity];
        do
        {
            ++this.rehashingTrials;
            success = true;
            this.hashFunction = new UniversalHashing(Integer.SIZE, this.capacity);

            newHashSet = new String[this.capacity];

            for (String key : this.hashSet)
            {
                if(key == null)
                {
                    continue;
                }

                if(!this.reinsert(key , newHashSet))
                {
                    success = false;
                    break;
                }
            }

            if(collisionKey != null)
            {
                success &= this.reinsert(collisionKey , newHashSet);
            }

        }
        while (!success);

        this.hashSet = newHashSet;
    }




    /* Resizes the capacity to ((2*size)^2) in order to keep the relation (capacity >= size^2) */

    private void resizehashSet()
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
            this.resizehashSet();
        }

        int index = this.hashFunction.hash(key);

        // Returns false on existing dictionary entry
        if (this.hashSet[index] != null && this.hashSet[index].equals(key))
        {
            return false;
        }


        if (this.hashSet[index] == null)
        {

            this.hashSet[index] = key;
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

        if (this.hashSet[index] != null && this.hashSet[index].equals(key))
        {
            this.hashSet[index] = null;
            --this.size;
            return true;
        }

        return false;
    }

    @Override
    public boolean search(String key)
    {
        int index = this.hashFunction.hash(key);
        return (this.hashSet[index] != null && this.hashSet[index].equals(key));
    }

    public int getIndex(String key)
    {
        return this.hashFunction.hash(key);
    }
}