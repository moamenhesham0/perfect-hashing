package perfecthashing.CLI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import perfecthashing.dictionary.PerfectHashDictionary;
import perfecthashing.utils.PerfectHashingStatistics;

public class DictionaryCLI {
    public void runCLI(){
        Scanner scanner = new Scanner(System.in);
        PerfectHashDictionary dictionary = null;
        String lastMessage = "";

        System.out.println("=== Perfect Hash Dictionary ===");
        System.out.print("Choose backend type (quadratic / linear): ");
        String type = scanner.nextLine().trim().toLowerCase();

        System.out.println("Enter initial keys separated by space (or press Enter for empty): ");
        String line = scanner.nextLine();
        List<String> initialKeys = line.isEmpty() ? new ArrayList<>() : Arrays.asList(line.split("\\s+"));

        dictionary = new PerfectHashDictionary(type, initialKeys);
        lastMessage = "Dictionary initialized with backend: " + type;

        while (true) {
            // Clear screen
            clearScreen();

            // Display last message at the top
            if (!lastMessage.isEmpty()) {
                System.out.println(lastMessage);
            }

            System.out.println("Choose Number from 1 to 6 for operation:");
            System.out.println("1. insert");
            System.out.println("2. delete");
            System.out.println("3. search");
            System.out.println("4. batch_insert");
            System.out.println("5. batch_delete");
            System.out.println("6. exit");
            System.out.println("7. Generate Statistics Files");

            System.out.print("> ");
            String input = scanner.nextLine().trim();

            // Check if input is empty
            if (input.isEmpty()) {
                lastMessage = "Please enter a command.";
                continue;
            }

            // Get the first character as command
            char firstChar = input.charAt(0);

            switch (firstChar) {
                case '1': // insert
                    System.out.print("Enter word to insert: ");
                    String wordToInsert = scanner.nextLine().trim();
                    if (wordToInsert.isEmpty()) {
                        lastMessage = "Please provide a word to insert.";
                        break;
                    }
                    boolean inserted = dictionary.insert(wordToInsert);
                    lastMessage = inserted ? "Word '" + wordToInsert + "' inserted." :
                                           "Word '" + wordToInsert + "' already exists.";
                    break;

                case '2': // delete
                    System.out.print("Enter word to delete: ");
                    String wordToDelete = scanner.nextLine().trim();
                    if (wordToDelete.isEmpty()) {
                        lastMessage = "Please provide a word to delete.";
                        break;
                    }
                    boolean deleted = dictionary.delete(wordToDelete);
                    lastMessage = deleted ? "Word '" + wordToDelete + "' deleted." :
                                          "Word '" + wordToDelete + "' not found.";
                    break;

                case '3': // search
                    System.out.print("Enter word to search: ");
                    String wordToSearch = scanner.nextLine().trim();
                    if (wordToSearch.isEmpty()) {
                        lastMessage = "Please provide a word to search.";
                        break;
                    }
                    boolean found = dictionary.search(wordToSearch);
                    lastMessage = found ? "Word '" + wordToSearch + "' exists." :
                                        "Word '" + wordToSearch + "' not found.";
                    break;

                case '4': // batch_insert
                    System.out.print("Enter file path for batch insert: ");
                    String insertFilePath = scanner.nextLine().trim();
                    if (insertFilePath.isEmpty()) {
                        lastMessage = "Please provide a file path.";
                        break;
                    }

                    int[] insertResults = dictionary.batchInsert(insertFilePath);
                    if (insertResults == null) {
                        lastMessage = "Failed to perform batch insert operation.";
                    } else {
                        lastMessage = String.format("Batch insert completed: %d newly added, %d already existing",
                                                  insertResults[0], insertResults[1]);
                    }
                    break;

                case '5': // batch_delete
                    System.out.print("Enter file path for batch delete: ");
                    String deleteFilePath = scanner.nextLine().trim();
                    if (deleteFilePath.isEmpty()) {
                        lastMessage = "Please provide a file path.";
                        break;
                    }

                    int[] deleteResults = dictionary.batchDelete(deleteFilePath);
                    if (deleteResults == null) {
                        lastMessage = "Failed to perform batch delete operation.";
                    } else {
                        lastMessage = String.format("Batch delete completed: %d deleted, %d non-existing",
                                                  deleteResults[0], deleteResults[1]);
                    }
                    break;

                case '6': // exit
                    System.out.println("Exiting.");
                    scanner.close();
                    return;

                case '7':
                    PerfectHashingStatistics.runStatisticsOnHashingOperations() ;
                    break;



                default:
                    lastMessage = "Unknown command. Please enter a number between 1 and 6.";
            }
        }
    }

    // Method to clear the console screen
    private static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Fallback if clearing fails
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
}

