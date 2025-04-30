public interface IPerfectHashTable <T>{

    /*Inserts an entry to the hash set*/
    boolean insert(String key);

    /* Deletes an entry from the hash set */
    boolean delete(String key);

    /* Returns true if the key exists in the hash set */
    boolean search(String key);

    /* Inserts entries from a file to the hash set */
    boolean batchInsert(final String filePath);

    /* Deletes entries from a file to the hash set */
    boolean batchDelete(final String filePath);
}
