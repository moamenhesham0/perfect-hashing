package perfecthashing.hashing;

import java.util.List;
public class PerfectLinearHashSet implements IPerfectHashSet {

    private static final int DEFAULT_CAPACITY = 5;
    private static final int INITIAL_SIZE = 0;

    private PerfectQuadraticHashSet[] hashSet;
    private int capacity;
    private int size;
    private UniversalHashing hashFunction;



    public PerfectLinearHashSet(final int capacity)
    {
        this.capacity = capacity;
        this.size = INITIAL_SIZE;
        this.hashSet = new PerfectQuadraticHashSet[this.capacity];
        this.hashFunction = new UniversalHashing(Integer.SIZE,this.capacity);
    }

    public PerfectLinearHashSet(final List<String> keys)
    {
        this(keys.size());

        for(final String key : keys)
        {
            this.insert(key);
        }
    }


    /* Default Constructor */

    public PerfectLinearHashSet()
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
        for(int i = 0 ; i<capacity ; ++i)
        {
            if(this.hashSet[i] == null) continue;

            totalCollisions+= this.hashSet[i].getCollisions();
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
        for(int i = 0 ; i<capacity ; ++i)
        {
            if(this.hashSet[i] == null) continue;

            totalCapacity+= this.hashSet[i].getCapacity();
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
        for(int i = 0 ; i<capacity ; ++i)
        {
            if(this.hashSet[i] == null) continue;

            totalRehashingTrials+= this.hashSet[i].getRehashingTrials();
        }
        return totalRehashingTrials;
    }






    private void rehash()
    {
        this.hashFunction = new UniversalHashing(Integer.SIZE, this.capacity);

        PerfectQuadraticHashSet[] newHashSet = new PerfectQuadraticHashSet[this.capacity];


        for(PerfectQuadraticHashSet bucket : this.hashSet)
        {
            if(bucket == null) continue;

            String[] table = bucket.getHashSet();

            for(String key : table)
            {
                if(key == null) continue;
                final int index = this.hashFunction.hash(key);

                if(newHashSet[index] == null)
                {
                    newHashSet[index] = new PerfectQuadraticHashSet();
                }

                newHashSet[index].insert(key);
            }
        }

        this.hashSet = newHashSet;
    }

    private void resizehashSet()
    {
        this.capacity = this.size * 2;
        this.rehash();
    }


    @Override
    public boolean insert(final String key)
    {
        if (this.size >= this.capacity)
        {
            this.resizehashSet();
        }

        final int index = this.hashFunction.hash(key);

        if(this.hashSet[index] == null)
        {
            this.hashSet[index] = new PerfectQuadraticHashSet();
        }

        if(this.hashSet[index].insert(key))
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

        if(this.hashSet[index] == null)
        {
            return false;
        }

        if (this.hashSet[index].delete(key))
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

        if(this.hashSet[index] == null)
        {
            return false;
        }

        return this.hashSet[index].search(key);
    }






}
