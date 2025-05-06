package perfecthashing.hashing;

public interface IPerfectHashSet {

    /* Inserts an entry to the hash set */
    boolean insert(String key);

    /* Deletes an entry from the hash set */
    boolean delete(String key);

    /* Returns true if the key exists in the hash set */
    boolean search(String key);

}
