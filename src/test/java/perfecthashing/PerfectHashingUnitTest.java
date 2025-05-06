package perfecthashing;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import perfecthashing.dictionary.PerfectHashDictionary;
import perfecthashing.hashing.PerfectLinearHashSet;
import perfecthashing.hashing.PerfectQuadraticHashSet;

public class PerfectHashingUnitTest
{
    private static PerfectLinearHashSet perfectLinearHashSet;
    private static PerfectQuadraticHashSet perfectQuadraticHashSet;
    private static PerfectHashDictionary perfectHashDictionary;
    private static final double NANO_TO_MILLI = 1_000_000.0;


    public static Double measureExecutionTime(Runnable function) {
        long startTime = System.nanoTime();
        function.run();
        return (System.nanoTime() - startTime)/NANO_TO_MILLI;
    }


    @Test
    public void testStaticInsertion()
    {
        perfectLinearHashSet = null;
        perfectQuadraticHashSet = null;

        List<String> keys = Arrays.asList("apple","fruit", "banana", "cherry", "date", "fig", "grape");

        double linearExecutionTime = measureExecutionTime(() -> {
            perfectLinearHashSet = new PerfectLinearHashSet(keys);
        });

        double quadraticExecutionTime = measureExecutionTime(() -> {
            perfectQuadraticHashSet = new PerfectQuadraticHashSet(keys);
        });



        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println("Total Keys: " + keys.size() + "\n");


        System.out.println("Linear :\n");
        System.out.println("Execution Time: " + linearExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectLinearHashSet.getSize());
        System.out.println("Collisions: " + perfectLinearHashSet.getTotalCollisions());
        System.out.println("Total Rehashings: " + perfectLinearHashSet.getTotalRehashingTrials());
        System.out.println("Total Buckets: " + perfectLinearHashSet.getTotalCapacity());
        System.out.println("Total Inner Buckets: " + perfectLinearHashSet.getInnerBucketsTotalCapacity());
        System.out.println("Usage Ratio: " + perfectLinearHashSet.getUsageRatio()+" %");

        System.out.println("\nQuadratic :\n");
        System.out.println("Execution Time: " + quadraticExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectQuadraticHashSet.getSize());
        System.out.println("Collisions: " + perfectQuadraticHashSet.getCollisions());
        System.out.println("Total Rehashings: " + perfectQuadraticHashSet.getRehashingTrials());
        System.out.println("Total Buckets: " + perfectQuadraticHashSet.getCapacity());
        System.out.println("Usage Ratio: " + perfectQuadraticHashSet.getUsageRatio()+" %");
        System.out.println("\n");
        System.out.println("--------------------------------------------------------");



        for (String key : keys) {
            assertTrue(perfectLinearHashSet.search(key));
            assertTrue(perfectQuadraticHashSet.search(key));
        }
    }


    @Test
    public void testDynamicInsertion()
    {
        perfectLinearHashSet = new PerfectLinearHashSet();
        perfectQuadraticHashSet = new PerfectQuadraticHashSet();

        String [] keys = {"apple", "banana", "cherry", "date", "fig", "grape" , "kiwi", "lemon", "mango", "nectarine", "orange", "papaya", "quince", "raspberry", "strawberry", "tangerine", "abcfruit", "vanilla bean", "watermelon"};

        double linearExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                perfectLinearHashSet.insert(key);
            }
        });

        double quadraticExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                perfectQuadraticHashSet.insert(key);
            }
        });

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println("Total Keys: " + keys.length + "\n");

        System.out.println("Linear :\n");
        System.out.println("Execution Time: " + linearExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectLinearHashSet.getSize());
        System.out.println("Collisions: " + perfectLinearHashSet.getTotalCollisions());
        System.out.println("Total Rehashings: " + perfectLinearHashSet.getTotalRehashingTrials());
        System.out.println("Total Buckets: " + perfectLinearHashSet.getTotalCapacity());
        System.out.println("Total Inner Buckets: " + perfectLinearHashSet.getInnerBucketsTotalCapacity());
        System.out.println("Usage Ratio: " + perfectLinearHashSet.getUsageRatio()+" %");

        System.out.println("\nQuadratic :\n");
        System.out.println("Execution Time: " + quadraticExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectQuadraticHashSet.getSize());
        System.out.println("Collisions: " + perfectQuadraticHashSet.getCollisions());
        System.out.println("Total Rehashings: " + perfectQuadraticHashSet.getRehashingTrials());
        System.out.println("Total Buckets: " + perfectQuadraticHashSet.getCapacity());
        System.out.println("Usage Ratio: " + perfectQuadraticHashSet.getUsageRatio()+" %");
        System.out.println("\n");
        System.out.println("--------------------------------------------------------");


        for (String key : keys) {
            assertTrue(perfectLinearHashSet.search(key));
            assertTrue(perfectQuadraticHashSet.search(key));
        }
    }

    @Test
    public void testInsertionSearchAndDeletionWithDuplicates()
    {
        perfectLinearHashSet = new PerfectLinearHashSet();
        perfectQuadraticHashSet = new PerfectQuadraticHashSet();

        List<String> keys = Arrays.asList("apple", "banana", "cherry", "date", "fig", "grape" , "kiwi", "lemon", "mango", "nectarine", "orange", "papaya", "quince", "raspberry", "strawberry", "tangerine", "ugli fruit", "vanilla bean", "watermelon" , "mango", "nectarine", "orange", "papaya", "quince", "raspberry", "strawberry", "tangerine", "abc fruit", "vanilla bean", "watermelon" , "mango", "nectarine", "orange", "papaya", "quince", "raspberry", "strawberry", "tangerine", "abc fruit", "vanilla bean", "watermelon" , "mango", "nectarine", "orange");


        double linearExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                perfectLinearHashSet.insert(key);
            }
            for (String key : keys) {
                assertTrue(perfectLinearHashSet.search(key));
            }
            for (String key : keys) {
                perfectLinearHashSet.delete(key);
            }
        });

        double quadraticExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                perfectQuadraticHashSet.insert(key);
            }
            for (String key : keys) {
                assertTrue(perfectQuadraticHashSet.search(key));
            }
            for (String key : keys) {
                perfectQuadraticHashSet.delete(key);
            }
        });

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());

        System.out.println("Total Keys: " + keys.size() + "\n");

        System.out.println("Linear :\n");
        System.out.println("Execution Time: " + linearExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectLinearHashSet.getSize());
        System.out.println("Collisions: " + perfectLinearHashSet.getTotalCollisions());
        System.out.println("Total Rehashings: " + perfectLinearHashSet.getTotalRehashingTrials());
        System.out.println("Total Buckets: " + perfectLinearHashSet.getTotalCapacity());
        System.out.println("Total Inner Buckets: " + perfectLinearHashSet.getInnerBucketsTotalCapacity());

        System.out.println("\nQuadratic :\n");
        System.out.println("Execution Time: " + quadraticExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectQuadraticHashSet.getSize());
        System.out.println("Collisions: " + perfectQuadraticHashSet.getCollisions());
        System.out.println("Total Rehashings: " + perfectQuadraticHashSet.getRehashingTrials());
        System.out.println("Total Buckets: " + perfectQuadraticHashSet.getCapacity());
        System.out.println("\n");
        System.out.println("--------------------------------------------------------");


        assertTrue(perfectLinearHashSet.getSize() ==0);
        assertTrue(perfectQuadraticHashSet.getSize() ==0);
    }


    @Test
    public void testBigDynamicInsertion()
    {
        perfectLinearHashSet = new PerfectLinearHashSet();
        perfectQuadraticHashSet = new PerfectQuadraticHashSet();

        final int MAX_KEYS = 16000;
        final int MAX_KEY_LENGTH = 32;

        List<String> keys = new ArrayList<>();

        for (int i = 0; i <MAX_KEYS ; i++) {
            String key = UUID.randomUUID().toString().substring(0, MAX_KEY_LENGTH);
            keys.add(key);
        }

        double linearExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                perfectLinearHashSet.insert(key);
            }
        });

        double quadraticExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                perfectQuadraticHashSet.insert(key);
            }
        });

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println("Total Keys: " + keys.size() + "\n");

        System.out.println("Linear :\n");
        System.out.println("Execution Time: " + linearExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectLinearHashSet.getSize());
        System.out.println("Collisions: " + perfectLinearHashSet.getTotalCollisions());
        System.out.println("Total Rehashings: " + perfectLinearHashSet.getTotalRehashingTrials());
        System.out.println("Total Buckets: " + perfectLinearHashSet.getTotalCapacity());
        System.out.println("Total Inner Buckets: " + perfectLinearHashSet.getInnerBucketsTotalCapacity());
        System.out.println("Usage Ratio: " + perfectLinearHashSet.getUsageRatio()+" %");

        System.out.println("\nQuadratic :\n");
        System.out.println("Execution Time: " + quadraticExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectQuadraticHashSet.getSize());
        System.out.println("Collisions: " + perfectQuadraticHashSet.getCollisions());
        System.out.println("Total Rehashings: " + perfectQuadraticHashSet.getRehashingTrials());
        System.out.println("Total Buckets: " + perfectQuadraticHashSet.getCapacity());
        System.out.println("Usage Ratio: " + perfectQuadraticHashSet.getUsageRatio()+" %");
        System.out.println("\n");
        System.out.println("--------------------------------------------------------");

        for (String key : keys) {
            assertTrue(perfectLinearHashSet.search(key));
            assertTrue(perfectQuadraticHashSet.search(key));
        }
    }

    @Test
    public void testBigBatchDeletion()
    {
        perfectLinearHashSet = new PerfectLinearHashSet();
        perfectQuadraticHashSet = new PerfectQuadraticHashSet();

        final int MAX_KEYS = 16000;
        final int MAX_KEY_LENGTH = 32;

        List<String> keys = new ArrayList<>();

        for (int i = 0; i <MAX_KEYS ; i++) {
            String key = UUID.randomUUID().toString().substring(0, MAX_KEY_LENGTH);
            keys.add(key);
        }

        for (String key : keys) {
            perfectLinearHashSet.insert(key);
        }
        for (String key : keys) {
            perfectQuadraticHashSet.insert(key);
        }


        double linearExecutionTime = measureExecutionTime(() -> {

            for (String key : keys) {
                assertTrue(perfectLinearHashSet.delete(key));
            }
        });

        double quadraticExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                assertTrue(perfectQuadraticHashSet.delete(key));
            }
        });

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println("Total Keys: " + keys.size() + "\n");

        System.out.println("Linear :\n");
        System.out.println("Execution Time: " + linearExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectLinearHashSet.getSize());
        System.out.println("Collisions: " + perfectLinearHashSet.getTotalCollisions());
        System.out.println("Total Rehashings: " + perfectLinearHashSet.getTotalRehashingTrials());
        System.out.println("Total Buckets: " + perfectLinearHashSet.getTotalCapacity());
        System.out.println("Total Inner Buckets: " + perfectLinearHashSet.getInnerBucketsTotalCapacity());

        System.out.println("\nQuadratic :\n");
        System.out.println("Execution Time: " + quadraticExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectQuadraticHashSet.getSize());
        System.out.println("Collisions: " + perfectQuadraticHashSet.getCollisions());
        System.out.println("Total Rehashings: " + perfectQuadraticHashSet.getRehashingTrials());
        System.out.println("Total Buckets: " + perfectQuadraticHashSet.getCapacity());
        System.out.println("\n");
        System.out.println("--------------------------------------------------------");


        assertTrue(perfectLinearHashSet.getSize() ==0);
        assertTrue(perfectQuadraticHashSet.getSize() ==0);
    }

    @Test
    public void InsertDuplicatesOnly()
    {
        perfectLinearHashSet = new PerfectLinearHashSet();
        perfectQuadraticHashSet = new PerfectQuadraticHashSet();

        String key = "apple";


        double linearExecutionTime = measureExecutionTime(() -> {
            assertTrue(perfectLinearHashSet.insert(key));
            for (int i = 0; i < 1000; i++) {
                assertTrue(!perfectLinearHashSet.insert(key));
            }
        });

        double quadraticExecutionTime = measureExecutionTime(() -> {
            assertTrue(perfectQuadraticHashSet.insert(key));
            for (int i = 0; i < 1000; i++) {
                assertTrue(!perfectQuadraticHashSet.insert(key));
            }
        });

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println("Total Keys: " + 1000 + "\n");

        System.out.println("Linear :\n");
        System.out.println("Execution Time: " + linearExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectLinearHashSet.getSize());
        System.out.println("Collisions: " + perfectLinearHashSet.getTotalCollisions());
        System.out.println("Total Rehashings: " + perfectLinearHashSet.getTotalRehashingTrials());
        System.out.println("Total Buckets: " + perfectLinearHashSet.getTotalCapacity());
        System.out.println("Total Inner Buckets: " + perfectLinearHashSet.getInnerBucketsTotalCapacity());
        System.out.println("Usage Ratio: " + perfectLinearHashSet.getUsageRatio()+" %");

        System.out.println("\nQuadratic :\n");
        System.out.println("Execution Time: " + quadraticExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectQuadraticHashSet.getSize());
        System.out.println("Collisions: " + perfectQuadraticHashSet.getCollisions());
        System.out.println("Total Rehashings: " + perfectQuadraticHashSet.getRehashingTrials());
        System.out.println("Total Buckets: " + perfectQuadraticHashSet.getCapacity());
        System.out.println("Usage Ratio: " + perfectQuadraticHashSet.getUsageRatio()+" %");
        System.out.println("\n");
        System.out.println("--------------------------------------------------------");

        assertTrue((perfectLinearHashSet.search(key) && perfectQuadraticHashSet.getSize() == 1));
        assertTrue((perfectQuadraticHashSet.search(key) && perfectQuadraticHashSet.getSize() == 1));
    }

    @Test
    public void testStringsWithCriticallyCloseHashValues()
    {
        perfectLinearHashSet = new PerfectLinearHashSet();
        perfectQuadraticHashSet = new PerfectQuadraticHashSet();


        List<String> keys = Arrays.asList(
            "aaaaa", "aaaab", "aaaac", "aaaad", "aaaae",
            "abaaa", "aabaa", "aaaba",
            "0a0a0", "0a0a1", "0a0b0", "0a1a0", "1a0a0",
            "abcde", "abcdf", "abcdg", "abcdh",
            "Aa", "BB",
            "AaAa", "BBBB",
            "temp01", "temp02", "temp03", "temp04", "temp05",
            "abc123", "abc124", "abc125", "abc126",
            "prefix_000", "prefix_001", "prefix_002", "prefix_003",
            "0123456789", "0123456798", "0123457689",
            "anagram", "anagrma", "aganram", "naagram"
        );

        double linearExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                perfectLinearHashSet.insert(key);
            }
        });

        double quadraticExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                perfectQuadraticHashSet.insert(key);
            }
        });

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println("Total Keys: " + keys.size() + "\n");

        System.out.println("Linear :\n");
        System.out.println("Execution Time: " + linearExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectLinearHashSet.getSize());
        System.out.println("Collisions: " + perfectLinearHashSet.getTotalCollisions());
        System.out.println("Total Rehashings: " + perfectLinearHashSet.getTotalRehashingTrials());
        System.out.println("Total Buckets: " + perfectLinearHashSet.getTotalCapacity());
        System.out.println("Total Inner Buckets: " + perfectLinearHashSet.getInnerBucketsTotalCapacity());
        System.out.println("Usage Ratio: " + perfectLinearHashSet.getUsageRatio()+" %");

        System.out.println("\nQuadratic :\n");
        System.out.println("Execution Time: " + quadraticExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectQuadraticHashSet.getSize());
        System.out.println("Collisions: " + perfectQuadraticHashSet.getCollisions());
        System.out.println("Total Rehashings: " + perfectQuadraticHashSet.getRehashingTrials());
        System.out.println("Total Buckets: " + perfectQuadraticHashSet.getCapacity());
        System.out.println("Usage Ratio: " + perfectQuadraticHashSet.getUsageRatio()+" %");
        System.out.println("\n");
        System.out.println("--------------------------------------------------------");

        // Verify all keys were inserted correctly
        for (String key : keys) {
            assertTrue(perfectLinearHashSet.search(key));
            assertTrue(perfectQuadraticHashSet.search(key));
        }

        // Check total insertions (accounting for duplicates like "aaaab")
        Set<String> uniqueKeys = new HashSet<>(keys);
        assertTrue(uniqueKeys.size()== perfectLinearHashSet.getSize());
        assertTrue(uniqueKeys.size()== perfectQuadraticHashSet.getSize());
    }

    @Test
    public void testBatchDeleteOnEmptyTable()
    {
        perfectLinearHashSet = new PerfectLinearHashSet();
        perfectQuadraticHashSet = new PerfectQuadraticHashSet();

        List<String> keys = Arrays.asList("apple", "banana", "cherry", "date", "fig", "grape");

        double linearExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                assertTrue(!perfectLinearHashSet.delete(key));
            }
        });

        double quadraticExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                assertTrue(!perfectQuadraticHashSet.delete(key));
            }
        });

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println("Total Keys: " + keys.size() + "\n");

        System.out.println("Linear :\n");
        System.out.println("Execution Time: " + linearExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectLinearHashSet.getSize());
        System.out.println("Collisions: " + perfectLinearHashSet.getTotalCollisions());
        System.out.println("Total Rehashings: " + perfectLinearHashSet.getTotalRehashingTrials());
        System.out.println("Total Buckets: " + perfectLinearHashSet.getTotalCapacity());

        System.out.println("\nQuadratic :\n");
        System.out.println("Execution Time: " + quadraticExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectQuadraticHashSet.getSize());
        System.out.println("Collisions: " + perfectQuadraticHashSet.getCollisions());
        System.out.println("Total Rehashings: " + perfectQuadraticHashSet.getRehashingTrials());
        System.out.println("Total Buckets: " + perfectQuadraticHashSet.getCapacity());
        System.out.println("\n");
        System.out.println("--------------------------------------------------------");

    }

    // @Test
    // public void testInsertOneMillionEntry()
    // {
    //     perfectLinearHashSet = null;




    //     List<String> keys = new ArrayList<>();
    //     double linearExecutionTime = measureExecutionTime(() -> {
    //         perfectHashDictionary = new PerfectHashDictionary("linear" , keys);
    //         perfectHashDictionary.batchInsert("/perfect-hashing/one_million_words.txt");
    //     });

    //     perfectLinearHashSet table =  perfectHashDictionary.getBackend();
    //     System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
    //     System.out.println("Total Keys: 1000000" + "\n");

    //     System.out.println("Linear :\n");
    //     System.out.println("Execution Time: " + linearExecutionTime + " ms");
    //     System.out.println("Total Keys Inserted: " + table.getSize());
    //     System.out.println("Collisions: " + table.getTotalCollisions());
    //     System.out.println("Total Rehashings: " + table.getTotalRehashingTrials());
    //     System.out.println("Total Buckets: " + table.getTotalCapacity());
    //     System.out.println("Usage Ratio: " + table.getUsageRatio()+" %");
    //     System.out.println("\n");
    //     System.out.println("--------------------------------------------------------");


    // }


    @Test
    public void testInsertSpecialCharactersStrings()
    {
        perfectLinearHashSet = new PerfectLinearHashSet();
        perfectQuadraticHashSet = new PerfectQuadraticHashSet();



        List<String> keys = Arrays.asList(
            "apple!", "banana@", "cherry#", "date$", "fig%",
            "^grape", "&kiwi", "*lemon", "(mango)", "nectarine)",
            "-orange-", "+papaya+", "=quince=", "{raspberry}", "[strawberry]",
            "]tangerine[", "|ugli fruit|", ":vanilla bean:", ";watermelon;",
            "<abc fruit>", ">vanilla bean<", "?watermelon?", "/mango/"
        );
        double linearExecutionTime = measureExecutionTime(() -> {

            for (String key : keys) {
                perfectLinearHashSet.insert(key);
            }
        });
        double quadraticExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                perfectQuadraticHashSet.insert(key);
            }
        });
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println("Total Keys: " + keys.size() + "\n");
        System.out.println("Linear :\n");
        System.out.println("Execution Time: " + linearExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectLinearHashSet.getSize());
        System.out.println("Collisions: " + perfectLinearHashSet.getTotalCollisions());
        System.out.println("Total Rehashings: " + perfectLinearHashSet.getTotalRehashingTrials());
        System.out.println("Total Buckets: " + perfectLinearHashSet.getTotalCapacity());
        System.out.println("Total Inner Buckets: " + perfectLinearHashSet.getInnerBucketsTotalCapacity());
        System.out.println("Usage Ratio: " + perfectLinearHashSet.getUsageRatio()+" %");
        System.out.println("\nQuadratic :\n");
        System.out.println("Execution Time: " + quadraticExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectQuadraticHashSet.getSize());
        System.out.println("Collisions: " + perfectQuadraticHashSet.getCollisions());
        System.out.println("Total Rehashings: " + perfectQuadraticHashSet.getRehashingTrials());
        System.out.println("Total Buckets: " + perfectQuadraticHashSet.getCapacity());
        System.out.println("Usage Ratio: " + perfectQuadraticHashSet.getUsageRatio()+" %");
        System.out.println("\n");
        System.out.println("--------------------------------------------------------");

        for(String key : keys)
        {
            assertTrue(perfectLinearHashSet.search(key));
            assertTrue(perfectQuadraticHashSet.search(key));
        }
    }


    @Test
    public void testLongStrings()
    {
        perfectLinearHashSet = new PerfectLinearHashSet();
        perfectQuadraticHashSet = new PerfectQuadraticHashSet();

        List<String> keys = Arrays.asList(
            "appleappleappleappleappleappleappleapple",
            "bananabananabananabananabananabanana",
            "cherrycherrycherrycherrycherrycherry",
            "date!date!date!date!date!date!",
            "fig%fig%fig%fig%fig%fig%",
            "^grape^grape^grape^grape^grape^grape",
            "&kiwi&kiwi&kiwi&kiwi&kiwi&kiwi",
            "*lemon*lemon*lemon*lemon*lemon*lemon",
            "(mango)(mango)(mango)(mango)(mango)(mango)",
            "-orange--orange--orange--orange--orange--orange-",
            "+papaya++papaya++papaya++papaya++papaya++papaya+",
            "=quince==quince==quince==quince==quince==quince=",
            "{raspberry}{raspberry}{raspberry}{raspberry}{raspberry}{raspberry}",
            "[strawberry][strawberry][strawberry][strawberry][strawberry][strawberry]",
            "]tangerine][tangerine][tangerine][tangerine][tangerine][tangerine]",
            "|ugli fruit||ugli fruit||ugli fruit||ugli fruit||ugli fruit||ugli fruit|",
            ":vanilla bean::vanilla bean::vanilla bean::vanilla bean::vanilla bean::vanilla bean:",
            ";watermelon;;watermelon;;watermelon;;watermelon;;watermelon;;watermelon;",
            "<abc fruit><abc fruit><abc fruit><abc fruit><abc fruit><abc fruit>",
            ">vanilla bean<vanilla bean<vanilla bean<vanilla bean<vanilla bean<vanilla bean>",
            "?watermelon?watermelon?watermelon?watermelon?watermelon?watermelon?",
            "/mango/mango/mango/mango/mango/mango/",
            "longstringlongstringlongstringlongstringlongstringlongstring",
            "cyanidecyanidecyanidecyanidecyanidecyanide",
            "potassiumpotassiumpotassiumpotassiumpotassiumpotassium",
            "hydroxydeoxycorticosteronehydroxydeoxycorticosterone",
            "ahmedahmedahmedahmedahmedahmed",
            "moamenmoamenmoamenmoamenmoamenmoamen",
            "muhanndismuhanndismuhanndismuhanndismuhanndis",
            "khamisakhamisakhamisakhamisakhamisakhamis",
            "hemahemhemahemhemahemhemahem"
        );

        double linearExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                perfectLinearHashSet.insert(key);
            }
        });

        double quadraticExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                perfectQuadraticHashSet.insert(key);
            }
        });

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println("Total Keys: " + keys.size() + "\n");

        System.out.println("Linear :\n");
        System.out.println("Execution Time: " + linearExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectLinearHashSet.getSize());
        System.out.println("Collisions: " + perfectLinearHashSet.getTotalCollisions());
        System.out.println("Total Rehashings: " + perfectLinearHashSet.getTotalRehashingTrials());
        System.out.println("Total Buckets: " + perfectLinearHashSet.getTotalCapacity());
        System.out.println("Total Inner Buckets: " + perfectLinearHashSet.getInnerBucketsTotalCapacity());
        System.out.println("Usage Ratio: " + perfectLinearHashSet.getUsageRatio()+" %");

        System.out.println("\nQuadratic :\n");
        System.out.println("Execution Time: " + quadraticExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectQuadraticHashSet.getSize());
        System.out.println("Collisions: " + perfectQuadraticHashSet.getCollisions());
        System.out.println("Total Rehashings: " + perfectQuadraticHashSet.getRehashingTrials());
        System.out.println("Total Buckets: " + perfectQuadraticHashSet.getCapacity());
        System.out.println("Usage Ratio: " + perfectQuadraticHashSet.getUsageRatio()+" %");
        System.out.println("\n");
        System.out.println("--------------------------------------------------------");

        for(String key : keys)
        {
            assertTrue(perfectLinearHashSet.search(key));
            assertTrue(perfectQuadraticHashSet.search(key));
        }
    }


    @Test
    public void testSearchNonExistentKeys() {
        perfectLinearHashSet = new PerfectLinearHashSet();
        perfectQuadraticHashSet = new PerfectQuadraticHashSet();

        List<String> keysToInsert = Arrays.asList("apple", "banana", "cherry", "date", "fig");
        List<String> keysToSearch = Arrays.asList("grape", "kiwi", "lemon", "mango", "orange");

        for (String key : keysToInsert) {
            perfectLinearHashSet.insert(key);
            perfectQuadraticHashSet.insert(key);
        }

        double linearExecutionTime = measureExecutionTime(() -> {
            for (String key : keysToSearch) {
                assertTrue(!perfectLinearHashSet.search(key));
            }
        });

        double quadraticExecutionTime = measureExecutionTime(() -> {
            for (String key : keysToSearch) {
                assertTrue(!perfectQuadraticHashSet.search(key));
            }
        });

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println("Keys in table: " + keysToInsert.size());
        System.out.println("Keys searched: " + keysToSearch.size() + "\n");

        System.out.println("Linear :\n");
        System.out.println("Execution Time: " + linearExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectLinearHashSet.getSize());
        System.out.println("Collisions: " + perfectLinearHashSet.getTotalCollisions());
        System.out.println("Total Rehashings: " + perfectLinearHashSet.getTotalRehashingTrials());
        System.out.println("Total Buckets: " + perfectLinearHashSet.getTotalCapacity());
        System.out.println("Total Inner Buckets: " + perfectLinearHashSet.getInnerBucketsTotalCapacity());
        System.out.println("Usage Ratio: " + perfectLinearHashSet.getUsageRatio()+" %");

        System.out.println("\nQuadratic :\n");
        System.out.println("Execution Time: " + quadraticExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectQuadraticHashSet.getSize());
        System.out.println("Collisions: " + perfectQuadraticHashSet.getCollisions());
        System.out.println("Total Rehashings: " + perfectQuadraticHashSet.getRehashingTrials());
        System.out.println("Total Buckets: " + perfectQuadraticHashSet.getCapacity());
        System.out.println("Usage Ratio: " + perfectQuadraticHashSet.getUsageRatio()+" %");

        System.out.println("\n");
        System.out.println("--------------------------------------------------------");

        for (String key : keysToInsert) {
            assertTrue(perfectLinearHashSet.search(key));
            assertTrue(perfectQuadraticHashSet.search(key));
        }
    }

    @Test
    public void testEmptyStringAndNullHandling() {
        perfectLinearHashSet = new PerfectLinearHashSet();
        perfectQuadraticHashSet = new PerfectQuadraticHashSet();

        assertTrue(perfectLinearHashSet.insert(""));
        assertTrue(perfectQuadraticHashSet.insert(""));

        assertTrue(perfectLinearHashSet.search(""));
        assertTrue(perfectQuadraticHashSet.search(""));

        assertTrue(perfectLinearHashSet.delete(""));
        assertTrue(perfectQuadraticHashSet.delete(""));

        assertTrue(!perfectLinearHashSet.search(""));
        assertTrue(!perfectQuadraticHashSet.search(""));

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println("Linear size: " + perfectLinearHashSet.getSize());
        System.out.println("Quadratic size: " + perfectQuadraticHashSet.getSize());
        System.out.println("--------------------------------------------------------");

        assertTrue(perfectLinearHashSet.getSize() == 0);
        assertTrue(perfectQuadraticHashSet.getSize() == 0);
    }

    @Test
    public void testMixOfOperations() {
        perfectLinearHashSet = new PerfectLinearHashSet();
        perfectQuadraticHashSet = new PerfectQuadraticHashSet();

        List<String> initialKeys = Arrays.asList(
            "apple", "banana", "cherry", "date", "elderberry",
            "fig", "grape", "honeydew", "imbe", "jackfruit"
        );

        List<String> keysToDelete = Arrays.asList("banana", "elderberry", "grape", "jackfruit");
        List<String> keysToAdd = Arrays.asList("kiwi", "lemon", "mango", "nectarine");

        double linearExecutionTime = measureExecutionTime(() -> {
            for (String key : initialKeys) {
                perfectLinearHashSet.insert(key);
            }

            for (String key : keysToDelete) {
                assertTrue(perfectLinearHashSet.delete(key));
            }

            for (String key : keysToAdd) {
                assertTrue(perfectLinearHashSet.insert(key));
            }

            for (String key : initialKeys) {
                if (keysToDelete.contains(key)) {
                    assertTrue(!perfectLinearHashSet.search(key));
                } else {
                    assertTrue(perfectLinearHashSet.search(key));
                }
            }

            for (String key : keysToAdd) {
                assertTrue(perfectLinearHashSet.search(key));
            }
        });

        double quadraticExecutionTime = measureExecutionTime(() -> {
            for (String key : initialKeys) {
                perfectQuadraticHashSet.insert(key);
            }

            for (String key : keysToDelete) {
                assertTrue(perfectQuadraticHashSet.delete(key));
            }

            for (String key : keysToAdd) {
                assertTrue(perfectQuadraticHashSet.insert(key));
            }

            for (String key : initialKeys) {
                if (keysToDelete.contains(key)) {
                    assertTrue(!perfectQuadraticHashSet.search(key));
                } else {
                    assertTrue(perfectQuadraticHashSet.search(key));
                }
            }

            for (String key : keysToAdd) {
                assertTrue(perfectQuadraticHashSet.search(key));
            }
        });

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println("Initial keys: " + initialKeys.size());
        System.out.println("Deleted keys: " + keysToDelete.size());
        System.out.println("Added keys: " + keysToAdd.size() + "\n");

        System.out.println("Linear :\n");
        System.out.println("Execution Time: " + linearExecutionTime + " ms");
        System.out.println("Final table size: " + perfectLinearHashSet.getSize());
        System.out.println("Collisions: " + perfectLinearHashSet.getTotalCollisions());
        System.out.println("Total Rehashings: " + perfectLinearHashSet.getTotalRehashingTrials());
        System.out.println("Total Buckets: " + perfectLinearHashSet.getTotalCapacity());
        System.out.println("Total Inner Buckets: " + perfectLinearHashSet.getInnerBucketsTotalCapacity());
        System.out.println("Usage Ratio: " + perfectLinearHashSet.getUsageRatio() + " %");

        System.out.println("\nQuadratic :\n");
        System.out.println("Execution Time: " + quadraticExecutionTime + " ms");
        System.out.println("Final table size: " + perfectQuadraticHashSet.getSize());
        System.out.println("Collisions: " + perfectQuadraticHashSet.getCollisions());
        System.out.println("Total Rehashings: " + perfectQuadraticHashSet.getRehashingTrials());
        System.out.println("Total Buckets: " + perfectQuadraticHashSet.getCapacity());
        System.out.println("Usage Ratio: " + perfectQuadraticHashSet.getUsageRatio() + " %");
        System.out.println("\n");
        System.out.println("--------------------------------------------------------");

        int expectedSize = initialKeys.size() - keysToDelete.size() + keysToAdd.size();
        assertTrue(perfectLinearHashSet.getSize() == expectedSize);
        assertTrue(perfectQuadraticHashSet.getSize() == expectedSize);
    }

    @Test
    public void testPerformanceWithNumbersAsStrings() {
        perfectLinearHashSet = new PerfectLinearHashSet();
        perfectQuadraticHashSet = new PerfectQuadraticHashSet();

        List<String> keys = new ArrayList<>();
        int numKeys = 1000;

        for (int i = 0; i < numKeys; i++) {
            keys.add(String.valueOf(i));
        }

        double linearExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                perfectLinearHashSet.insert(key);
            }
        });

        double quadraticExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                perfectQuadraticHashSet.insert(key);
            }
        });

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println("Total Keys: " + numKeys + "\n");

        System.out.println("Linear :\n");
        System.out.println("Execution Time: " + linearExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectLinearHashSet.getSize());
        System.out.println("Collisions: " + perfectLinearHashSet.getTotalCollisions());
        System.out.println("Total Rehashings: " + perfectLinearHashSet.getTotalRehashingTrials());
        System.out.println("Total Buckets:"  + perfectLinearHashSet.getTotalCapacity());
        System.out.println("Total Inner Buckets: " + perfectLinearHashSet.getInnerBucketsTotalCapacity());
        System.out.println("Usage Ratio: " + perfectLinearHashSet.getUsageRatio() + " %");


        System.out.println("\nQuadratic :\n");
        System.out.println("Execution Time: " + quadraticExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectQuadraticHashSet.getSize());
        System.out.println("Collisions: " + perfectQuadraticHashSet.getCollisions());
        System.out.println("Total Rehashings: " + perfectQuadraticHashSet.getRehashingTrials());
        System.out.println("Total Buckets: " + perfectQuadraticHashSet.getCapacity());
        System.out.println("Usage Ratio: " + perfectQuadraticHashSet.getUsageRatio() + " %");
        System.out.println("\n");
        System.out.println("--------------------------------------------------------");

        for (String key : keys) {
            assertTrue(perfectLinearHashSet.search(key));
            assertTrue(perfectQuadraticHashSet.search(key));
        }
    }

    @Test
    public void testHighLoadFactor() {

        int initialCapacity = 16;
        perfectLinearHashSet = new PerfectLinearHashSet(initialCapacity);
        perfectQuadraticHashSet = new PerfectQuadraticHashSet(initialCapacity);

        List<String> keys = new ArrayList<>();
        int numKeys = initialCapacity * 4;


        for (int i = 0; i < numKeys; i++) {
            keys.add("key_" + UUID.randomUUID().toString().substring(0, 8));
        }

        double linearExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                perfectLinearHashSet.insert(key);
            }
        });

        double quadraticExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                perfectQuadraticHashSet.insert(key);
            }
        });

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println("Initial Capacity: " + initialCapacity);
        System.out.println("Total Keys: " + numKeys + "\n");

        System.out.println("Linear :\n");
        System.out.println("Execution Time: " + linearExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectLinearHashSet.getSize());
        System.out.println("Collisions: " + perfectLinearHashSet.getTotalCollisions());
        System.out.println("Total Rehashings: " + perfectLinearHashSet.getTotalRehashingTrials());
        System.out.println("Total Buckets: " + perfectLinearHashSet.getTotalCapacity());
        System.out.println("Total Inner Buckets: " + perfectLinearHashSet.getInnerBucketsTotalCapacity());
        System.out.println("Usage Ratio: " + perfectLinearHashSet.getUsageRatio() + " %");

        System.out.println("\nQuadratic :\n");
        System.out.println("Execution Time: " + quadraticExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectQuadraticHashSet.getSize());
        System.out.println("Collisions: " + perfectQuadraticHashSet.getCollisions());
        System.out.println("Total Rehashings: " + perfectQuadraticHashSet.getRehashingTrials());
        System.out.println("Total Buckets: " + perfectQuadraticHashSet.getCapacity());
        System.out.println("Usage Ratio: " + perfectQuadraticHashSet.getUsageRatio() + " %");
        System.out.println("\n");
        System.out.println("--------------------------------------------------------");

        for (String key : keys) {
            assertTrue(perfectLinearHashSet.search(key));
            assertTrue(perfectQuadraticHashSet.search(key));
        }

        assertTrue(perfectLinearHashSet.getTotalCapacity() > initialCapacity);
        assertTrue(perfectQuadraticHashSet.getCapacity() > initialCapacity);
    }













}