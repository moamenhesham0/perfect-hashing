# Perfect Hashing Implementation

## Overview

This project implements perfect hashing techniques for efficient data retrieval. Perfect hashing guarantees O(1) lookup time in the worst case by ensuring no collisions occur in the hash table.

## Features

- **Two-Level Perfect Hashing**:
  - Level 1: Linear hashing with buckets
  - Level 2: Quadratic perfect hashing within each bucket
- **Universal Hash Family**: Randomized hash functions that minimize collision probability
- **O(1) Operations**: Constant time lookup, insertion, and deletion operations
- **Memory Efficient**: Space usage proportional to the number of keys
- **Dynamic Resizing**: Automatically expands to maintain performance guarantees

## Implementation Details

### Hash Set Types

1. **Linear Perfect Hash Set**
   - Uses an array of quadratic perfect hash sets as buckets
   - Maps keys to buckets with level-1 hash function
   - Handles collisions by placing keys in the same bucket
   - O(1) worst-case lookup time

2. **Quadratic Perfect Hash Set**
   - Used within buckets of the linear set
   - Resolves collisions through rehashing
   - Maintains size-squared space to guarantee perfect hashing
   - Collision-free after rehashing

### Key Operations

- **Insert**: Adds a key to the hash set, automatically handling any collisions
- **Search**: Looks up a key with O(1) worst-case time complexity
- **Delete**: Removes a key while maintaining the perfect hashing property
- **BatchInsert**: Adds a batch of words from a text file
- **BatchDelete**: Deletes a batch of words provided by the text file

## Usage

```java
// Create a perfect hash set (linear or quadratic)
IPerfectHashSet hashSet = new PerfectLinearHashSet();

// Insert keys
hashSet.insert("apple");
hashSet.insert("banana");

// Search for keys
boolean found = hashSet.search("apple");  // true
boolean notFound = hashSet.search("grape");  // false

// Delete keys
hashSet.delete("banana");

// Load a batch of keys
List<String> keys = Arrays.asList("one", "two", "three");
PerfectLinearHashSet set = new PerfectLinearHashSet(keys);

// Create a dictionary for higher-level operations
PerfectHashDictionary dictionary = new PerfectHashDictionary("linear", keys);
dictionary.batchInsert("path/to/textfile.txt");

