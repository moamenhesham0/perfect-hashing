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
import perfecthashing.hashing.PerfectHashTableLinear;
import perfecthashing.hashing.PerfectHashTableQuadratic;

public class PerfectHashingUnitTest
{
    private static PerfectHashTableLinear perfectHashTableLinear;
    private static PerfectHashTableQuadratic perfectHashTableQuadratic;
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
        perfectHashTableLinear = null;
        perfectHashTableQuadratic = null;

        List<String> keys = Arrays.asList("apple","fruit", "banana", "cherry", "date", "fig", "grape");

        double linearExecutionTime = measureExecutionTime(() -> {
            perfectHashTableLinear = new PerfectHashTableLinear(keys);
        });

        double quadraticExecutionTime = measureExecutionTime(() -> {
            perfectHashTableQuadratic = new PerfectHashTableQuadratic(keys);
        });



        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println("Total Keys: " + keys.size() + "\n");


        System.out.println("Linear :\n");
        System.out.println("Execution Time: " + linearExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectHashTableLinear.getSize());
        System.out.println("Collisions: " + perfectHashTableLinear.getTotalCollisions());
        System.out.println("Total Rehashings: " + perfectHashTableLinear.getTotalRehashingTrials());
        System.out.println("Total Buckets: " + perfectHashTableLinear.getTotalCapacity());
        System.out.println("Total Inner Buckets: " + perfectHashTableLinear.getInnerBucketsTotalCapacity());
        System.out.println("Usage Ratio: " + perfectHashTableLinear.getUsageRatio()+" %");

        System.out.println("\nQuadratic :\n");
        System.out.println("Execution Time: " + quadraticExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectHashTableQuadratic.getSize());
        System.out.println("Collisions: " + perfectHashTableQuadratic.getCollisions());
        System.out.println("Total Rehashings: " + perfectHashTableQuadratic.getRehashingTrials());
        System.out.println("Total Buckets: " + perfectHashTableQuadratic.getCapacity());
        System.out.println("Usage Ratio: " + perfectHashTableQuadratic.getUsageRatio()+" %");
        System.out.println("\n");
        System.out.println("--------------------------------------------------------");



        for (String key : keys) {
            assertTrue(perfectHashTableLinear.search(key));
            assertTrue(perfectHashTableQuadratic.search(key));
        }
    }


    @Test
    public void testDynamicInsertion()
    {
        perfectHashTableLinear = new PerfectHashTableLinear();
        perfectHashTableQuadratic = new PerfectHashTableQuadratic();

        String [] keys = {"apple", "banana", "cherry", "date", "fig", "grape" , "kiwi", "lemon", "mango", "nectarine", "orange", "papaya", "quince", "raspberry", "strawberry", "tangerine", "abcfruit", "vanilla bean", "watermelon"};

        double linearExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                perfectHashTableLinear.insert(key);
            }
        });

        double quadraticExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                perfectHashTableQuadratic.insert(key);
            }
        });

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println("Total Keys: " + keys.length + "\n");

        System.out.println("Linear :\n");
        System.out.println("Execution Time: " + linearExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectHashTableLinear.getSize());
        System.out.println("Collisions: " + perfectHashTableLinear.getTotalCollisions());
        System.out.println("Total Rehashings: " + perfectHashTableLinear.getTotalRehashingTrials());
        System.out.println("Total Buckets: " + perfectHashTableLinear.getTotalCapacity());
        System.out.println("Total Inner Buckets: " + perfectHashTableLinear.getInnerBucketsTotalCapacity());
        System.out.println("Usage Ratio: " + perfectHashTableLinear.getUsageRatio()+" %");

        System.out.println("\nQuadratic :\n");
        System.out.println("Execution Time: " + quadraticExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectHashTableQuadratic.getSize());
        System.out.println("Collisions: " + perfectHashTableQuadratic.getCollisions());
        System.out.println("Total Rehashings: " + perfectHashTableQuadratic.getRehashingTrials());
        System.out.println("Total Buckets: " + perfectHashTableQuadratic.getCapacity());
        System.out.println("Usage Ratio: " + perfectHashTableQuadratic.getUsageRatio()+" %");
        System.out.println("\n");
        System.out.println("--------------------------------------------------------");


        for (String key : keys) {
            assertTrue(perfectHashTableLinear.search(key));
            assertTrue(perfectHashTableQuadratic.search(key));
        }
    }

    @Test
    public void testInsertionSearchAndDeletionWithDuplicates()
    {
        perfectHashTableLinear = new PerfectHashTableLinear();
        perfectHashTableQuadratic = new PerfectHashTableQuadratic();

        List<String> keys = Arrays.asList("apple", "banana", "cherry", "date", "fig", "grape" , "kiwi", "lemon", "mango", "nectarine", "orange", "papaya", "quince", "raspberry", "strawberry", "tangerine", "ugli fruit", "vanilla bean", "watermelon" , "mango", "nectarine", "orange", "papaya", "quince", "raspberry", "strawberry", "tangerine", "abc fruit", "vanilla bean", "watermelon" , "mango", "nectarine", "orange", "papaya", "quince", "raspberry", "strawberry", "tangerine", "abc fruit", "vanilla bean", "watermelon" , "mango", "nectarine", "orange");


        double linearExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                perfectHashTableLinear.insert(key);
            }
            for (String key : keys) {
                assertTrue(perfectHashTableLinear.search(key));
            }
            for (String key : keys) {
                perfectHashTableLinear.delete(key);
            }
        });

        double quadraticExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                perfectHashTableQuadratic.insert(key);
            }
            for (String key : keys) {
                assertTrue(perfectHashTableQuadratic.search(key));
            }
            for (String key : keys) {
                perfectHashTableQuadratic.delete(key);
            }
        });

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());

        System.out.println("Total Keys: " + keys.size() + "\n");

        System.out.println("Linear :\n");
        System.out.println("Execution Time: " + linearExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectHashTableLinear.getSize());
        System.out.println("Collisions: " + perfectHashTableLinear.getTotalCollisions());
        System.out.println("Total Rehashings: " + perfectHashTableLinear.getTotalRehashingTrials());
        System.out.println("Total Buckets: " + perfectHashTableLinear.getTotalCapacity());
        System.out.println("Total Inner Buckets: " + perfectHashTableLinear.getInnerBucketsTotalCapacity());

        System.out.println("\nQuadratic :\n");
        System.out.println("Execution Time: " + quadraticExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectHashTableQuadratic.getSize());
        System.out.println("Collisions: " + perfectHashTableQuadratic.getCollisions());
        System.out.println("Total Rehashings: " + perfectHashTableQuadratic.getRehashingTrials());
        System.out.println("Total Buckets: " + perfectHashTableQuadratic.getCapacity());
        System.out.println("\n");
        System.out.println("--------------------------------------------------------");

        String [] table = perfectHashTableQuadratic.getHashTable();
        for(String k : table)
        {
            if(k!=null)
            System.out.println(k);
        }
        assertTrue(perfectHashTableLinear.getSize() ==0);
        assertTrue(perfectHashTableQuadratic.getSize() ==0);
    }


    @Test
    public void testBigDynamicInsertion()
    {
        perfectHashTableLinear = new PerfectHashTableLinear();
        perfectHashTableQuadratic = new PerfectHashTableQuadratic();

        final int MAX_KEYS = 16000;
        final int MAX_KEY_LENGTH = 32;

        List<String> keys = new ArrayList<>();

        for (int i = 0; i <MAX_KEYS ; i++) {
            String key = UUID.randomUUID().toString().substring(0, MAX_KEY_LENGTH);
            keys.add(key);
        }

        double linearExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                perfectHashTableLinear.insert(key);
            }
        });

        double quadraticExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                perfectHashTableQuadratic.insert(key);
            }
        });

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println("Total Keys: " + keys.size() + "\n");

        System.out.println("Linear :\n");
        System.out.println("Execution Time: " + linearExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectHashTableLinear.getSize());
        System.out.println("Collisions: " + perfectHashTableLinear.getTotalCollisions());
        System.out.println("Total Rehashings: " + perfectHashTableLinear.getTotalRehashingTrials());
        System.out.println("Total Buckets: " + perfectHashTableLinear.getTotalCapacity());
        System.out.println("Total Inner Buckets: " + perfectHashTableLinear.getInnerBucketsTotalCapacity());
        System.out.println("Usage Ratio: " + perfectHashTableLinear.getUsageRatio()+" %");

        System.out.println("\nQuadratic :\n");
        System.out.println("Execution Time: " + quadraticExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectHashTableQuadratic.getSize());
        System.out.println("Collisions: " + perfectHashTableQuadratic.getCollisions());
        System.out.println("Total Rehashings: " + perfectHashTableQuadratic.getRehashingTrials());
        System.out.println("Total Buckets: " + perfectHashTableQuadratic.getCapacity());
        System.out.println("Usage Ratio: " + perfectHashTableQuadratic.getUsageRatio()+" %");
        System.out.println("\n");
        System.out.println("--------------------------------------------------------");

        for (String key : keys) {
            assertTrue(perfectHashTableLinear.search(key));
            assertTrue(perfectHashTableQuadratic.search(key));
        }
    }

    @Test
    public void testBigBatchDeletion()
    {
        perfectHashTableLinear = new PerfectHashTableLinear();
        perfectHashTableQuadratic = new PerfectHashTableQuadratic();

        final int MAX_KEYS = 16000;
        final int MAX_KEY_LENGTH = 32;

        List<String> keys = new ArrayList<>();

        for (int i = 0; i <MAX_KEYS ; i++) {
            String key = UUID.randomUUID().toString().substring(0, MAX_KEY_LENGTH);
            keys.add(key);
        }

        for (String key : keys) {
            perfectHashTableLinear.insert(key);
        }
        for (String key : keys) {
            perfectHashTableQuadratic.insert(key);
        }


        double linearExecutionTime = measureExecutionTime(() -> {

            for (String key : keys) {
                assertTrue(perfectHashTableLinear.delete(key));
            }
        });

        double quadraticExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                assertTrue(perfectHashTableQuadratic.delete(key));
            }
        });

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println("Total Keys: " + keys.size() + "\n");

        System.out.println("Linear :\n");
        System.out.println("Execution Time: " + linearExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectHashTableLinear.getSize());
        System.out.println("Collisions: " + perfectHashTableLinear.getTotalCollisions());
        System.out.println("Total Rehashings: " + perfectHashTableLinear.getTotalRehashingTrials());
        System.out.println("Total Buckets: " + perfectHashTableLinear.getTotalCapacity());
        System.out.println("Total Inner Buckets: " + perfectHashTableLinear.getInnerBucketsTotalCapacity());

        System.out.println("\nQuadratic :\n");
        System.out.println("Execution Time: " + quadraticExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectHashTableQuadratic.getSize());
        System.out.println("Collisions: " + perfectHashTableQuadratic.getCollisions());
        System.out.println("Total Rehashings: " + perfectHashTableQuadratic.getRehashingTrials());
        System.out.println("Total Buckets: " + perfectHashTableQuadratic.getCapacity());
        System.out.println("\n");
        System.out.println("--------------------------------------------------------");


        assertTrue(perfectHashTableLinear.getSize() ==0);
        assertTrue(perfectHashTableQuadratic.getSize() ==0);
    }

    @Test
    public void InsertDuplicatesOnly()
    {
        perfectHashTableLinear = new PerfectHashTableLinear();
        perfectHashTableQuadratic = new PerfectHashTableQuadratic();

        String key = "apple";


        double linearExecutionTime = measureExecutionTime(() -> {
            assertTrue(perfectHashTableLinear.insert(key));
            for (int i = 0; i < 1000; i++) {
                assertTrue(!perfectHashTableLinear.insert(key));
            }
        });

        double quadraticExecutionTime = measureExecutionTime(() -> {
            assertTrue(perfectHashTableQuadratic.insert(key));
            for (int i = 0; i < 1000; i++) {
                assertTrue(!perfectHashTableQuadratic.insert(key));
            }
        });

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println("Total Keys: " + 1000 + "\n");

        System.out.println("Linear :\n");
        System.out.println("Execution Time: " + linearExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectHashTableLinear.getSize());
        System.out.println("Collisions: " + perfectHashTableLinear.getTotalCollisions());
        System.out.println("Total Rehashings: " + perfectHashTableLinear.getTotalRehashingTrials());
        System.out.println("Total Buckets: " + perfectHashTableLinear.getTotalCapacity());
        System.out.println("Total Inner Buckets: " + perfectHashTableLinear.getInnerBucketsTotalCapacity());
        System.out.println("Usage Ratio: " + perfectHashTableLinear.getUsageRatio()+" %");

        System.out.println("\nQuadratic :\n");
        System.out.println("Execution Time: " + quadraticExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectHashTableQuadratic.getSize());
        System.out.println("Collisions: " + perfectHashTableQuadratic.getCollisions());
        System.out.println("Total Rehashings: " + perfectHashTableQuadratic.getRehashingTrials());
        System.out.println("Total Buckets: " + perfectHashTableQuadratic.getCapacity());
        System.out.println("Usage Ratio: " + perfectHashTableQuadratic.getUsageRatio()+" %");
        System.out.println("\n");
        System.out.println("--------------------------------------------------------");

        assertTrue((perfectHashTableLinear.search(key) && perfectHashTableQuadratic.getSize() == 1));
        assertTrue((perfectHashTableQuadratic.search(key) && perfectHashTableQuadratic.getSize() == 1));
    }

    @Test
    public void testStringsWithCriticallyCloseHashValues()
    {
        perfectHashTableLinear = new PerfectHashTableLinear();
        perfectHashTableQuadratic = new PerfectHashTableQuadratic();


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
                perfectHashTableLinear.insert(key);
            }
        });

        double quadraticExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                perfectHashTableQuadratic.insert(key);
            }
        });

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println("Total Keys: " + keys.size() + "\n");

        System.out.println("Linear :\n");
        System.out.println("Execution Time: " + linearExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectHashTableLinear.getSize());
        System.out.println("Collisions: " + perfectHashTableLinear.getTotalCollisions());
        System.out.println("Total Rehashings: " + perfectHashTableLinear.getTotalRehashingTrials());
        System.out.println("Total Buckets: " + perfectHashTableLinear.getTotalCapacity());
        System.out.println("Total Inner Buckets: " + perfectHashTableLinear.getInnerBucketsTotalCapacity());
        System.out.println("Usage Ratio: " + perfectHashTableLinear.getUsageRatio()+" %");

        System.out.println("\nQuadratic :\n");
        System.out.println("Execution Time: " + quadraticExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectHashTableQuadratic.getSize());
        System.out.println("Collisions: " + perfectHashTableQuadratic.getCollisions());
        System.out.println("Total Rehashings: " + perfectHashTableQuadratic.getRehashingTrials());
        System.out.println("Total Buckets: " + perfectHashTableQuadratic.getCapacity());
        System.out.println("Usage Ratio: " + perfectHashTableQuadratic.getUsageRatio()+" %");
        System.out.println("\n");
        System.out.println("--------------------------------------------------------");

        // Verify all keys were inserted correctly
        for (String key : keys) {
            assertTrue(perfectHashTableLinear.search(key));
            assertTrue(perfectHashTableQuadratic.search(key));
        }

        // Check total insertions (accounting for duplicates like "aaaab")
        Set<String> uniqueKeys = new HashSet<>(keys);
        assertTrue(uniqueKeys.size()== perfectHashTableLinear.getSize());
        assertTrue(uniqueKeys.size()== perfectHashTableQuadratic.getSize());
    }

    @Test
    public void testBatchDeleteOnEmptyTable()
    {
        perfectHashTableLinear = new PerfectHashTableLinear();
        perfectHashTableQuadratic = new PerfectHashTableQuadratic();

        List<String> keys = Arrays.asList("apple", "banana", "cherry", "date", "fig", "grape");

        double linearExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                assertTrue(!perfectHashTableLinear.delete(key));
            }
        });

        double quadraticExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                assertTrue(!perfectHashTableQuadratic.delete(key));
            }
        });

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println("Total Keys: " + keys.size() + "\n");

        System.out.println("Linear :\n");
        System.out.println("Execution Time: " + linearExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectHashTableLinear.getSize());
        System.out.println("Collisions: " + perfectHashTableLinear.getTotalCollisions());
        System.out.println("Total Rehashings: " + perfectHashTableLinear.getTotalRehashingTrials());
        System.out.println("Total Buckets: " + perfectHashTableLinear.getTotalCapacity());

        System.out.println("\nQuadratic :\n");
        System.out.println("Execution Time: " + quadraticExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectHashTableQuadratic.getSize());
        System.out.println("Collisions: " + perfectHashTableQuadratic.getCollisions());
        System.out.println("Total Rehashings: " + perfectHashTableQuadratic.getRehashingTrials());
        System.out.println("Total Buckets: " + perfectHashTableQuadratic.getCapacity());
        System.out.println("\n");
        System.out.println("--------------------------------------------------------");

    }

    // @Test
    // public void testInsertOneMillionEntry()
    // {
    //     perfectHashTableLinear = null;




    //     List<String> keys = new ArrayList<>();
    //     double linearExecutionTime = measureExecutionTime(() -> {
    //         perfectHashDictionary = new PerfectHashDictionary("linear" , keys);
    //         perfectHashDictionary.batchInsert("/perfect-hashing/one_million_words.txt");
    //     });

    //     PerfectHashTableLinear table =  perfectHashDictionary.getBackend();
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
        perfectHashTableLinear = new PerfectHashTableLinear();
        perfectHashTableQuadratic = new PerfectHashTableQuadratic();



        List<String> keys = Arrays.asList(
            "apple!", "banana@", "cherry#", "date$", "fig%",
            "^grape", "&kiwi", "*lemon", "(mango)", "nectarine)",
            "-orange-", "+papaya+", "=quince=", "{raspberry}", "[strawberry]",
            "]tangerine[", "|ugli fruit|", ":vanilla bean:", ";watermelon;",
            "<abc fruit>", ">vanilla bean<", "?watermelon?", "/mango/"
        );
        double linearExecutionTime = measureExecutionTime(() -> {

            for (String key : keys) {
                perfectHashTableLinear.insert(key);
            }
        });
        double quadraticExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                perfectHashTableQuadratic.insert(key);
            }
        });
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println("Total Keys: " + keys.size() + "\n");
        System.out.println("Linear :\n");
        System.out.println("Execution Time: " + linearExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectHashTableLinear.getSize());
        System.out.println("Collisions: " + perfectHashTableLinear.getTotalCollisions());
        System.out.println("Total Rehashings: " + perfectHashTableLinear.getTotalRehashingTrials());
        System.out.println("Total Buckets: " + perfectHashTableLinear.getTotalCapacity());
        System.out.println("Total Inner Buckets: " + perfectHashTableLinear.getInnerBucketsTotalCapacity());
        System.out.println("Usage Ratio: " + perfectHashTableLinear.getUsageRatio()+" %");
        System.out.println("\nQuadratic :\n");
        System.out.println("Execution Time: " + quadraticExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectHashTableQuadratic.getSize());
        System.out.println("Collisions: " + perfectHashTableQuadratic.getCollisions());
        System.out.println("Total Rehashings: " + perfectHashTableQuadratic.getRehashingTrials());
        System.out.println("Total Buckets: " + perfectHashTableQuadratic.getCapacity());
        System.out.println("Usage Ratio: " + perfectHashTableQuadratic.getUsageRatio()+" %");
        System.out.println("\n");
        System.out.println("--------------------------------------------------------");

        for(String key : keys)
        {
            assertTrue(perfectHashTableLinear.search(key));
            assertTrue(perfectHashTableQuadratic.search(key));
        }
    }


    @Test
    public void testLongStrings()
    {
        perfectHashTableLinear = new PerfectHashTableLinear();
        perfectHashTableQuadratic = new PerfectHashTableQuadratic();

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

        perfectHashTableLinear = new PerfectHashTableLinear();
        double linearExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                perfectHashTableLinear.insert(key);
            }
        });

        perfectHashTableQuadratic = new PerfectHashTableQuadratic();
        double quadraticExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                perfectHashTableQuadratic.insert(key);
            }
        });

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println("Total Keys: " + keys.size() + "\n");

        System.out.println("Linear :\n");
        System.out.println("Execution Time: " + linearExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectHashTableLinear.getSize());
        System.out.println("Collisions: " + perfectHashTableLinear.getTotalCollisions());
        System.out.println("Total Rehashings: " + perfectHashTableLinear.getTotalRehashingTrials());
        System.out.println("Total Buckets: " + perfectHashTableLinear.getTotalCapacity());
        System.out.println("Total Inner Buckets: " + perfectHashTableLinear.getInnerBucketsTotalCapacity());
        System.out.println("Usage Ratio: " + perfectHashTableLinear.getUsageRatio()+" %");

        System.out.println("\nQuadratic :\n");
        System.out.println("Execution Time: " + quadraticExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectHashTableQuadratic.getSize());
        System.out.println("Collisions: " + perfectHashTableQuadratic.getCollisions());
        System.out.println("Total Rehashings: " + perfectHashTableQuadratic.getRehashingTrials());
        System.out.println("Total Buckets: " + perfectHashTableQuadratic.getCapacity());
        System.out.println("Usage Ratio: " + perfectHashTableQuadratic.getUsageRatio()+" %");
        System.out.println("\n");
        System.out.println("--------------------------------------------------------");

        for(String key : keys)
        {
            assertTrue(perfectHashTableLinear.search(key));
            assertTrue(perfectHashTableQuadratic.search(key));
        }
    }


    @Test
    public void testSearchNonExistentKeys() {
        perfectHashTableLinear = new PerfectHashTableLinear();
        perfectHashTableQuadratic = new PerfectHashTableQuadratic();

        List<String> keysToInsert = Arrays.asList("apple", "banana", "cherry", "date", "fig");
        List<String> keysToSearch = Arrays.asList("grape", "kiwi", "lemon", "mango", "orange");

        for (String key : keysToInsert) {
            perfectHashTableLinear.insert(key);
            perfectHashTableQuadratic.insert(key);
        }

        double linearExecutionTime = measureExecutionTime(() -> {
            for (String key : keysToSearch) {
                assertTrue(!perfectHashTableLinear.search(key));
            }
        });

        double quadraticExecutionTime = measureExecutionTime(() -> {
            for (String key : keysToSearch) {
                assertTrue(!perfectHashTableQuadratic.search(key));
            }
        });

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println("Keys in table: " + keysToInsert.size());
        System.out.println("Keys searched: " + keysToSearch.size() + "\n");

        System.out.println("Linear :\n");
        System.out.println("Execution Time: " + linearExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectHashTableLinear.getSize());
        System.out.println("Collisions: " + perfectHashTableLinear.getTotalCollisions());
        System.out.println("Total Rehashings: " + perfectHashTableLinear.getTotalRehashingTrials());
        System.out.println("Total Buckets: " + perfectHashTableLinear.getTotalCapacity());
        System.out.println("Total Inner Buckets: " + perfectHashTableLinear.getInnerBucketsTotalCapacity());
        System.out.println("Usage Ratio: " + perfectHashTableLinear.getUsageRatio()+" %");

        System.out.println("\nQuadratic :\n");
        System.out.println("Execution Time: " + quadraticExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectHashTableQuadratic.getSize());
        System.out.println("Collisions: " + perfectHashTableQuadratic.getCollisions());
        System.out.println("Total Rehashings: " + perfectHashTableQuadratic.getRehashingTrials());
        System.out.println("Total Buckets: " + perfectHashTableQuadratic.getCapacity());
        System.out.println("Usage Ratio: " + perfectHashTableQuadratic.getUsageRatio()+" %");

        System.out.println("\n");
        System.out.println("--------------------------------------------------------");

        for (String key : keysToInsert) {
            assertTrue(perfectHashTableLinear.search(key));
            assertTrue(perfectHashTableQuadratic.search(key));
        }
    }

    @Test
    public void testEmptyStringAndNullHandling() {
        perfectHashTableLinear = new PerfectHashTableLinear();
        perfectHashTableQuadratic = new PerfectHashTableQuadratic();

        assertTrue(perfectHashTableLinear.insert(""));
        assertTrue(perfectHashTableQuadratic.insert(""));

        assertTrue(perfectHashTableLinear.search(""));
        assertTrue(perfectHashTableQuadratic.search(""));

        assertTrue(perfectHashTableLinear.delete(""));
        assertTrue(perfectHashTableQuadratic.delete(""));

        assertTrue(!perfectHashTableLinear.search(""));
        assertTrue(!perfectHashTableQuadratic.search(""));

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println("Linear size: " + perfectHashTableLinear.getSize());
        System.out.println("Quadratic size: " + perfectHashTableQuadratic.getSize());
        System.out.println("--------------------------------------------------------");

        assertTrue(perfectHashTableLinear.getSize() == 0);
        assertTrue(perfectHashTableQuadratic.getSize() == 0);
    }

    @Test
    public void testMixOfOperations() {
        perfectHashTableLinear = new PerfectHashTableLinear();
        perfectHashTableQuadratic = new PerfectHashTableQuadratic();

        List<String> initialKeys = Arrays.asList(
            "apple", "banana", "cherry", "date", "elderberry",
            "fig", "grape", "honeydew", "imbe", "jackfruit"
        );

        List<String> keysToDelete = Arrays.asList("banana", "elderberry", "grape", "jackfruit");
        List<String> keysToAdd = Arrays.asList("kiwi", "lemon", "mango", "nectarine");

        double linearExecutionTime = measureExecutionTime(() -> {
            for (String key : initialKeys) {
                perfectHashTableLinear.insert(key);
            }

            for (String key : keysToDelete) {
                assertTrue(perfectHashTableLinear.delete(key));
            }

            for (String key : keysToAdd) {
                assertTrue(perfectHashTableLinear.insert(key));
            }

            for (String key : initialKeys) {
                if (keysToDelete.contains(key)) {
                    assertTrue(!perfectHashTableLinear.search(key));
                } else {
                    assertTrue(perfectHashTableLinear.search(key));
                }
            }

            for (String key : keysToAdd) {
                assertTrue(perfectHashTableLinear.search(key));
            }
        });

        double quadraticExecutionTime = measureExecutionTime(() -> {
            for (String key : initialKeys) {
                perfectHashTableQuadratic.insert(key);
            }

            for (String key : keysToDelete) {
                assertTrue(perfectHashTableQuadratic.delete(key));
            }

            for (String key : keysToAdd) {
                assertTrue(perfectHashTableQuadratic.insert(key));
            }

            for (String key : initialKeys) {
                if (keysToDelete.contains(key)) {
                    assertTrue(!perfectHashTableQuadratic.search(key));
                } else {
                    assertTrue(perfectHashTableQuadratic.search(key));
                }
            }

            for (String key : keysToAdd) {
                assertTrue(perfectHashTableQuadratic.search(key));
            }
        });

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println("Initial keys: " + initialKeys.size());
        System.out.println("Deleted keys: " + keysToDelete.size());
        System.out.println("Added keys: " + keysToAdd.size() + "\n");

        System.out.println("Linear :\n");
        System.out.println("Execution Time: " + linearExecutionTime + " ms");
        System.out.println("Final table size: " + perfectHashTableLinear.getSize());
        System.out.println("Collisions: " + perfectHashTableLinear.getTotalCollisions());
        System.out.println("Total Rehashings: " + perfectHashTableLinear.getTotalRehashingTrials());
        System.out.println("Total Buckets: " + perfectHashTableLinear.getTotalCapacity());
        System.out.println("Total Inner Buckets: " + perfectHashTableLinear.getInnerBucketsTotalCapacity());
        System.out.println("Usage Ratio: " + perfectHashTableLinear.getUsageRatio() + " %");

        System.out.println("\nQuadratic :\n");
        System.out.println("Execution Time: " + quadraticExecutionTime + " ms");
        System.out.println("Final table size: " + perfectHashTableQuadratic.getSize());
        System.out.println("Collisions: " + perfectHashTableQuadratic.getCollisions());
        System.out.println("Total Rehashings: " + perfectHashTableQuadratic.getRehashingTrials());
        System.out.println("Total Buckets: " + perfectHashTableQuadratic.getCapacity());
        System.out.println("Usage Ratio: " + perfectHashTableQuadratic.getUsageRatio() + " %");
        System.out.println("\n");
        System.out.println("--------------------------------------------------------");

        int expectedSize = initialKeys.size() - keysToDelete.size() + keysToAdd.size();
        assertTrue(perfectHashTableLinear.getSize() == expectedSize);
        assertTrue(perfectHashTableQuadratic.getSize() == expectedSize);
    }

    @Test
    public void testPerformanceWithNumbersAsStrings() {
        perfectHashTableLinear = new PerfectHashTableLinear();
        perfectHashTableQuadratic = new PerfectHashTableQuadratic();

        List<String> keys = new ArrayList<>();
        int numKeys = 1000;

        for (int i = 0; i < numKeys; i++) {
            keys.add(String.valueOf(i));
        }

        double linearExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                perfectHashTableLinear.insert(key);
            }
        });

        double quadraticExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                perfectHashTableQuadratic.insert(key);
            }
        });

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println("Total Keys: " + numKeys + "\n");

        System.out.println("Linear :\n");
        System.out.println("Execution Time: " + linearExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectHashTableLinear.getSize());
        System.out.println("Collisions: " + perfectHashTableLinear.getTotalCollisions());
        System.out.println("Total Rehashings: " + perfectHashTableLinear.getTotalRehashingTrials());
        System.out.println("Total Buckets:"  + perfectHashTableLinear.getTotalCapacity());
        System.out.println("Total Inner Buckets: " + perfectHashTableLinear.getInnerBucketsTotalCapacity());
        System.out.println("Usage Ratio: " + perfectHashTableLinear.getUsageRatio() + " %");


        System.out.println("\nQuadratic :\n");
        System.out.println("Execution Time: " + quadraticExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectHashTableQuadratic.getSize());
        System.out.println("Collisions: " + perfectHashTableQuadratic.getCollisions());
        System.out.println("Total Rehashings: " + perfectHashTableQuadratic.getRehashingTrials());
        System.out.println("Total Buckets: " + perfectHashTableQuadratic.getCapacity());
        System.out.println("Usage Ratio: " + perfectHashTableQuadratic.getUsageRatio() + " %");
        System.out.println("\n");
        System.out.println("--------------------------------------------------------");

        for (String key : keys) {
            assertTrue(perfectHashTableLinear.search(key));
            assertTrue(perfectHashTableQuadratic.search(key));
        }
    }

    @Test
    public void testHighLoadFactor() {

        int initialCapacity = 16;
        perfectHashTableLinear = new PerfectHashTableLinear(initialCapacity);
        perfectHashTableQuadratic = new PerfectHashTableQuadratic(initialCapacity);

        List<String> keys = new ArrayList<>();
        int numKeys = initialCapacity * 4;


        for (int i = 0; i < numKeys; i++) {
            keys.add("key_" + UUID.randomUUID().toString().substring(0, 8));
        }

        double linearExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                perfectHashTableLinear.insert(key);
            }
        });

        double quadraticExecutionTime = measureExecutionTime(() -> {
            for (String key : keys) {
                perfectHashTableQuadratic.insert(key);
            }
        });

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
        System.out.println("Initial Capacity: " + initialCapacity);
        System.out.println("Total Keys: " + numKeys + "\n");

        System.out.println("Linear :\n");
        System.out.println("Execution Time: " + linearExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectHashTableLinear.getSize());
        System.out.println("Collisions: " + perfectHashTableLinear.getTotalCollisions());
        System.out.println("Total Rehashings: " + perfectHashTableLinear.getTotalRehashingTrials());
        System.out.println("Total Buckets: " + perfectHashTableLinear.getTotalCapacity());
        System.out.println("Total Inner Buckets: " + perfectHashTableLinear.getInnerBucketsTotalCapacity());
        System.out.println("Usage Ratio: " + perfectHashTableLinear.getUsageRatio() + " %");

        System.out.println("\nQuadratic :\n");
        System.out.println("Execution Time: " + quadraticExecutionTime + " ms");
        System.out.println("Total Keys Inserted: " + perfectHashTableQuadratic.getSize());
        System.out.println("Collisions: " + perfectHashTableQuadratic.getCollisions());
        System.out.println("Total Rehashings: " + perfectHashTableQuadratic.getRehashingTrials());
        System.out.println("Total Buckets: " + perfectHashTableQuadratic.getCapacity());
        System.out.println("Usage Ratio: " + perfectHashTableQuadratic.getUsageRatio() + " %");
        System.out.println("\n");
        System.out.println("--------------------------------------------------------");

        for (String key : keys) {
            assertTrue(perfectHashTableLinear.search(key));
            assertTrue(perfectHashTableQuadratic.search(key));
        }

        assertTrue(perfectHashTableLinear.getTotalCapacity() > initialCapacity);
        assertTrue(perfectHashTableQuadratic.getCapacity() > initialCapacity);
    }













}