package perfecthashing.hashing;

import java.util.*;

public class UniversalHashing {
    private final int[][] matrix;
    private final int b, u;
    private final int capacity;

    public UniversalHashing(int b, int capacity) {
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
        // Convert the binary key vector to long
        return (int)(toInt(result) % capacity);
    }

    private int[] toBits(String key, int u) {
        int hash = key.hashCode();
        int[] bits = new int[u];
        for (int i = 0; i < u; i++) {
            bits[i] = (hash >> i) & 1;
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
    private int computeUBits(int capacity)
    {
        return (int) Math.floor(Math.log(capacity) / Math.log(2));
    }
}
