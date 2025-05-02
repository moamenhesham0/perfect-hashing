package perfecthashing.CLI;

import perfecthashing.dictionary.PerfectHashDictionary;

import java.util.*;

public class DictionaryCLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PerfectHashDictionary dictionary = null;

        System.out.println("=== Perfect Hash Dictionary ===");
        System.out.print("Choose backend type (quadratic / linear): ");
        String type = scanner.nextLine().trim().toLowerCase();

        System.out.println("Enter initial keys separated by space (or press Enter for empty): ");
        String line = scanner.nextLine();
        List<String> initialKeys = line.isEmpty() ? new ArrayList<>() : Arrays.asList(line.split("\\s+"));

        dictionary = new PerfectHashDictionary(type, initialKeys);
        System.out.println("Dictionary initialized with backend: " + type);

        while (true) {
            System.out.println("\nChoose operation:");
            System.out.println("1. insert <word>");
            System.out.println("2. delete <word>");
            System.out.println("3. search <word>");
            System.out.println("4. batch_insert <file_path>");
            System.out.println("5. batch_delete <file_path>");
            System.out.println("6. exit");

            System.out.print("> ");
            String[] input = scanner.nextLine().trim().split("\\s+", 2);

            if (input.length == 0 || input[0].isEmpty()) continue;

            String command = input[0].toLowerCase();
            String argument = input.length > 1 ? input[1] : "";

                switch (command) {
                    case "insert":
                        if (argument.isEmpty()) {
                            System.out.println("Please provide a word to insert.");
                            break;
                        }
                        boolean inserted = dictionary.insert(argument);
                        System.out.println(inserted ? "Inserted." : "Word already exists.");
                        break;

                    case "delete":
                        if (argument.isEmpty()) {
                            System.out.println("Please provide a word to delete.");
                            break;
                        }
                        boolean deleted = dictionary.delete(argument);
                        System.out.println(deleted ? "Deleted." : "Word not found.");
                        break;

                    case "search":
                        if (argument.isEmpty()) {
                            System.out.println("Please provide a word to search.");
                            break;
                        }
                        boolean found = dictionary.search(argument);
                        System.out.println(found ? "Word exists." : "Word not found.");
                        break;

                    case "batch_insert":
                        if (argument.isEmpty()) {
                            System.out.println("Please provide a file path.");
                            break;
                        }
                        dictionary.batchInsert(argument);
                        break;

                    case "batch_delete":
                        if (argument.isEmpty()) {
                            System.out.println("Please provide a file path.");
                            break;
                        }
                        dictionary.batchDelete(argument);
                        break;

                    case "exit":
                        System.out.println("Exiting.");
                        return;

                    default:
                        System.out.println("Unknown command.");
                }
        }
    }
}

