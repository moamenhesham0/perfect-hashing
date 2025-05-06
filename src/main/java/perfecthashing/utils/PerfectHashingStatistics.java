package perfecthashing.utils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.DoubleStream;

import perfecthashing.hashing.PerfectLinearHashSet;
import perfecthashing.hashing.PerfectQuadraticHashSet;


public class PerfectHashingStatistics {
    private static final int DATA_POINTS = 200;
    private static final int MIN_KEYS = 10;
    private static final int MAX_KEYS = 16000;
    private static final double NANO_TO_MILLI = 1e6;
    private static final int MAX_KEY_LENGTH = 32;
    private static final double AVG_RUNS = 1e2;

    private static PerfectLinearHashSet perfectLinearHashSet;
    private static PerfectQuadraticHashSet perfectQuadraticHashSet;


    public static Double measureExecutionTime(Runnable function) {
        long startTime = System.nanoTime();
        function.run();
        return (System.nanoTime() - startTime)/NANO_TO_MILLI;
    }


    private static List<String> generateRandomStrings(int count) {
        List<String> keys = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            StringBuilder keyBuilder = new StringBuilder();
            while (keyBuilder.length() < MAX_KEY_LENGTH) {
                keyBuilder.append(UUID.randomUUID().toString().replace("-", ""));
            }
            String key = keyBuilder.substring(0, MAX_KEY_LENGTH);
            keys.add(key);
        }
        return keys;
    }



    public static void runStatisticsOnHashingOperations(){
        Set<Integer> uniquePoints = new LinkedHashSet<>();
        DoubleStream.iterate(MIN_KEYS,
                n -> n * Math.pow((double) MAX_KEYS / MIN_KEYS, 1.0 / (DATA_POINTS - 1)))
            .limit(DATA_POINTS)
            .mapToInt(n -> (int) Math.round(n))
            .forEach(uniquePoints::add);

        int[] points = uniquePoints.stream().mapToInt(Integer::intValue).toArray();

        ExcelFileHandler insertionStatistics = new ExcelFileHandler("InsertionStatistics");
        insertionStatistics.setRow(new String[]{"Key Count", "Linear Time", "Quadratic Time"});

        ExcelFileHandler searchStatistics = new ExcelFileHandler("SearchStatistics");
        searchStatistics.setRow(new String[]{"Key Count", "Linear Time", "Quadratic Time"});

        ExcelFileHandler deletionStatistics = new ExcelFileHandler("DeletionStatistics");
        deletionStatistics.setRow(new String[]{"Key Count", "Linear Time", "Quadratic Time"});

        ExcelFileHandler collisionStatistics = new ExcelFileHandler("CollisionStatistics");
        collisionStatistics.setRow(new String[]{"Key Count", "Linear Collisions", "Quadratic Collisions"});

        ExcelFileHandler rehashingStatistics = new ExcelFileHandler("RehashingStatistics");
        rehashingStatistics.setRow(new String[]{"Key Count", "Linear Rehashing", "Quadratic Rehashing"});

        for(int i = 0; i < points.length; ++i) {
            List<String> keys = generateRandomStrings(points[i]);

            // Run operations multiple times and calculate averages
            double avgLinearInsertionTime = 0;
            double avgQuadraticInsertionTime = 0;
            double avgLinearSearchTime = 0;
            double avgQuadraticSearchTime = 0;
            double avgLinearDeleteTime = 0;
            double avgQuadraticDeleteTime = 0;
            int avgLinearCollisions = 0;
            int avgQuadraticCollisions = 0;
            int avgLinearRehashing = 0;
            int avgQuadraticRehashing = 0;

            for(int j = 0; j < AVG_RUNS; ++j) {
                // Linear hash table operations
                perfectLinearHashSet = new PerfectLinearHashSet();
                avgLinearInsertionTime += measureExecutionTime(() -> {
                    for (String key : keys) {
                        perfectLinearHashSet.insert(key);
                    }
                });

                avgLinearSearchTime += measureExecutionTime(() -> {
                    for (String key : keys) {
                        perfectLinearHashSet.search(key);
                    }
                });

                avgLinearDeleteTime += measureExecutionTime(() -> {
                    for (String key : keys) {
                        perfectLinearHashSet.delete(key);
                    }
                });

                avgLinearCollisions += perfectLinearHashSet.getTotalCollisions();
                avgLinearRehashing += perfectLinearHashSet.getTotalRehashingTrials();

                // Quadratic hash table operations
                perfectQuadraticHashSet = new PerfectQuadraticHashSet();
                avgQuadraticInsertionTime += measureExecutionTime(() -> {
                    for (String key : keys) {
                        perfectQuadraticHashSet.insert(key);
                    }
                });

                avgQuadraticSearchTime += measureExecutionTime(() -> {
                    for (String key : keys) {
                        perfectQuadraticHashSet.search(key);
                    }
                });

                avgQuadraticDeleteTime += measureExecutionTime(() -> {
                    for (String key : keys) {
                        perfectQuadraticHashSet.delete(key);
                    }
                });

                avgQuadraticCollisions += perfectQuadraticHashSet.getCollisions();
                avgQuadraticRehashing += perfectQuadraticHashSet.getRehashingTrials();
            }

            // Calculate averages
            avgLinearInsertionTime /= AVG_RUNS;
            avgQuadraticInsertionTime /= AVG_RUNS;
            avgLinearSearchTime /= AVG_RUNS;
            avgQuadraticSearchTime /= AVG_RUNS;
            avgLinearDeleteTime /= AVG_RUNS;
            avgQuadraticDeleteTime /= AVG_RUNS;
            avgLinearCollisions = (int)Math.ceil( avgLinearCollisions/AVG_RUNS);
            avgQuadraticCollisions = (int)Math.ceil( avgQuadraticCollisions/AVG_RUNS);
            avgLinearRehashing = (int)Math.ceil( avgLinearRehashing/AVG_RUNS);
            avgQuadraticRehashing = (int)Math.ceil( avgQuadraticRehashing/AVG_RUNS);

            // Insert data into sheets
            Object[] rowDataInsertion = {(double)points[i], avgLinearInsertionTime, avgQuadraticInsertionTime};
            Object[] rowDataSearch = {(double)points[i], avgLinearSearchTime, avgQuadraticSearchTime};
            Object[] rowDataDeletion = {(double)points[i], avgLinearDeleteTime, avgQuadraticDeleteTime};
            Object[] rowDataCollision = {(double)points[i], avgLinearCollisions, avgQuadraticCollisions};
            Object[] rowDataRehashing = {(double)points[i], avgLinearRehashing, avgQuadraticRehashing};

            insertionStatistics.setRow(rowDataInsertion);
            searchStatistics.setRow(rowDataSearch);
            deletionStatistics.setRow(rowDataDeletion);
            collisionStatistics.setRow(rowDataCollision);
            rehashingStatistics.setRow(rowDataRehashing);
        }

        insertionStatistics.saveToFile();
        searchStatistics.saveToFile();
        deletionStatistics.saveToFile();
        collisionStatistics.saveToFile();
        rehashingStatistics.saveToFile();

        System.out.println("Statistics generated and saved to Excel files.");
    }


}



