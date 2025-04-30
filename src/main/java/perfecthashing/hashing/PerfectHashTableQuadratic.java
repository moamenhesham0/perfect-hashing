package perfecthashing.hashing;

public class PerfectHashTableQuadratic
{


    private String[] hashTable;
    private List <String> keys;
    private int size;
    private int capacity;
    private UniversalHashFunction hashFunction;


    /* Computes the number of bits needed in the hash function matrix row dimension */

    private static final int computeUBits(int capacity)
    {
        return (int) Math.floor(Math.log(capacity) / Math.log(2));
    }



    /* Rehashes the hash table when a collision occurs.
      This is resets the hash set to a new hash set and reinserts the keys*/

    private void rehash()
    {
        int newSize = 0;
        while (newSize < this.size)
        {
            newSize = 0;

            this.hashFunction = new UniversalHashFunction(Integer.SIZE, computeUBits(capacity));

            this.hashTable = new String[capacity];


            for(String key : keys)
            {
                int index = hashFunction.hash(key) % capacity;

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



    public PerfectHashTableLinear(int capacity)
    {
        this.capacity = capacity;
        this.size = 0;
        this.hashTable = new String[capacity];
        this.keys = new ArrayList<>();
        this.hashFunction = new UniversalHashFunction(Integer.SIZE, computeUBits(capacity));
    }




    public void insert(String key)
    {
        int index = hashFunction.hash(key) % capacity;

        keys.add(key);
        ++size;


        if (hashTable[index] == null)
        {
            hashTable[index] = key;
        }

        else if (hashTable[index].equals(key))
        {
            return false;
        }

        else
        {
            rehash();
        }

        return true;
    }

    public boolean delete(String key)
    {
        int index = hashFunction.hash(key) % capacity;

        if (hashTable[index] != null && hashTable[index].equals(key))
        {
            hashTable[index] = null;
            --size;
        }

        else
        {
            return false;
        }

        return true;
    }

    public boolean search(String key)
    {
        int index = hashFunction.hash(key) % capacity;

        if (hashTable[index] != null && hashTable[index].equals(key))
        {
            return true;
        }

        return false;
    }