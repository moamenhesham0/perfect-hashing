package perfecthashing.hashing;

import java.util.*;

public class UniversalHashing {
    private final int[][] matrix;
    private final int b, u;
    private final long capacity;

    public UniversalHashing(int b, long capacity) {
        this.b = b;
        this.u = computeUBits(capacity);
        this.capacity = capacity;
        this.matrix = new int[b][u];
        GenerateRandomHash();
    }

    private void GenerateRandomHash() {
        Random rand = new Random();
        for (int i = 0; i < b; i++) {
            for (int j = 0; j < u; j++) {
                matrix[i][j] = rand.nextBoolean() ? 1 : 0;
            }
        }
    }

    public int hash(String key) {
        // Convert the key to a u-bit binary vector
        int[] x = toBits(key, u);
        // Create the vector result that equals to matrix(b,u) * x(u, 1)
        int[] result = new int[b];

        for (int i = 0; i < b; i++) {
            int sum = 0;
            for (int j = 0; j < u; j++) {
                sum += matrix[i][j] * x[j];
            }
            result[i] = sum % 2;
        }
        
        // Convert the binary key vector to long and ensure positive index
        long hashValue = toInt(result);
        int index = Math.abs((int)(hashValue % capacity));
        return index;
    }

    private int[] toBits(String key, int u) {
        // Improve the hashing to reduce collisions
        long hash = 0;
        
        // Use multiple mixing techniques for better distribution
        for (int i = 0; i < key.length(); i++) {
            hash = 31 * hash + key.charAt(i);
            // Add rotations for better bit distribution
            hash = (hash << 5) | (hash >>> 59); // 5-bit left rotation
        }
        
        // Add key length influence
        hash = hash ^ key.length();
        
        // Ensure we get positive values
        hash = Math.abs(hash);
        
        int[] bits = new int[u];
        for (int i = 0; i < u; i++) {
            bits[i] = (int)((hash >> i) & 1);
        }
        return bits;
    }

    private long toInt(int[] bits) {
        long value = 0;
        for (int i = 0; i < bits.length; i++) {
            if(bits[i] == 1)
                value |= (1L << i);
        }
        return value;
    }

    /* Computes the number of bits needed in the hash function matrix row dimension */
    private int computeUBits(long capacity)
    {
        // Increase the bit size to reduce collision probability
        return Math.max(32, (int) Math.ceil(Math.log(capacity) / Math.log(2)) * 2);
    }
}
