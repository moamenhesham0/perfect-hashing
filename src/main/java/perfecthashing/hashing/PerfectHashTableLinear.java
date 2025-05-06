package perfecthashing.hashing;

import java.util.List;
public class PerfectHashTableLinear implements IPerfectHashTable {

    private static final int DEFAULT_CAPACITY = 5;
    private static final int INITIAL_SIZE = 0;

    private PerfectHashTableQuadratic[] hashTable;
    private int capacity;
    private int size;
    private UniversalHashing hashFunction;



    public PerfectHashTableLinear(final int capacity)
    {
        this.capacity = capacity;
        this.size = INITIAL_SIZE;
        this.hashTable = new PerfectHashTableQuadratic[this.capacity];
        this.hashFunction = new UniversalHashing(Integer.SIZE,this.capacity);

        for(int i = 0; i < this.capacity; i++)
        {
            this.hashTable[i] = new PerfectHashTableQuadratic();
        }
    }

    public PerfectHashTableLinear(final List<String> keys)
    {
        this(keys.size());

        for(final String key : keys)
        {
            this.insert(key);
        }
    }


    /* Default Constructor */

    public PerfectHashTableLinear()
    {
        this(DEFAULT_CAPACITY);
    }



    /* Getters */

    public int getSize()
    {
        return this.size;
    }

    public long getTotalCollisions()
    {
        long totalCollisions = 0;
        for (PerfectHashTableQuadratic bucket : this.hashTable)
        {
            totalCollisions += bucket.getCollisions();
        }
        return totalCollisions;
    }

    public int getTotalCapacity()
    {
        return this.capacity;
    }
    public long getInnerBucketsTotalCapacity()
    {
        long totalCapacity = 0;
        for (PerfectHashTableQuadratic bucket : this.hashTable)
        {
            totalCapacity += bucket.getCapacity();
        }
        return totalCapacity;
    }

    public double getUsageRatio()
    {
        return ((double) this.size / this.getInnerBucketsTotalCapacity()  * 1e2);
    }

    public long getTotalRehashingTrials()
    {
        long totalRehashingTrials = 0;
        for (PerfectHashTableQuadratic bucket : this.hashTable)
        {
            totalRehashingTrials += bucket.getRehashingTrials();
        }
        return totalRehashingTrials;
    }




    private void rehash()
    {
        this.hashFunction = new UniversalHashing(Integer.SIZE, this.capacity);

        PerfectHashTableQuadratic[] newHashTable = new PerfectHashTableQuadratic[this.capacity];



        for(int i = 0; i < this.capacity; i++)
        {
            newHashTable[i] = new PerfectHashTableQuadratic();
        }

        for(PerfectHashTableQuadratic bucket : this.hashTable)
        {
            if(bucket.getSize() == 0) continue;

            String[] table = bucket.getHashTable();

            for(String key : table)
            {
                if(key == null) continue;
                final int index = this.hashFunction.hash(key);
                newHashTable[index].insert(key);
            }
        }

        this.hashTable = newHashTable;
    }

    private void resizeHashTable()
    {
        this.capacity = this.size * 2;
        this.rehash();
    }


    @Override
    public boolean insert(final String key)
    {
        if (this.size >= this.capacity)
        {
            this.resizeHashTable();
        }

        final int index = this.hashFunction.hash(key);

        if(this.hashTable[index].insert(key))
        {
            ++this.size;
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(final String key)
    {
        final int index = this.hashFunction.hash(key);

        if (this.hashTable[index].delete(key))
        {
            --this.size;
            return true;
        }

        return false;
    }

    @Override
    public boolean search(final String key)
    {
        final int index = this.hashFunction.hash(key);
        return this.hashTable[index].search(key);
    }






}
