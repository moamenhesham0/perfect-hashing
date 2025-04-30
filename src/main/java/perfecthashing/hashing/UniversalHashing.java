package perfecthashing.hashing;

import java.util.*;

public class UniversalHashing {
    private final int[][] matrix;
    private final int b, u;

    public UniversalHashing(int b, int u) {
        this.b = b;
        this.u = u;
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
        // Creat the vector result that equall to matrix(b,u) * x(u, 1)
        int[] result = new int[b];

        for (int i = 0; i < b; i++) {
            int sum = 0;
            for (int j = 0; j < u; j++) {
                sum += matrix[i][j] * x[j];
            }
            result[i] = sum % 2;
        }

        // Convert the binary jey vector to int
        return toInt(result);
    }

    private int[] toBits(String key, int u) {
        int hash = key.hashCode();
        int[] bits = new int[u];
        for (int i = 0; i < u; i++) {
            bits[i] = (hash >> i) & 1;
        }
        return bits;
    }

    private int toInt(int[] bits) {
        int value = 0;
        for (int i = 0; i < bits.length; i++) {
            value |= (bits[i] << i);
        }
        return value;
    }
}
