
/* Bailey Garrett 
 * module 4 assignment 2  
 * 2/9/24  
 * CountKeywords.java revised 
 */
import java.util.*;
import java.io.*;

public class CountKeywords {
    public static void main(String[] args) throws Exception {
        // retrieve the filename from the command-line arguments
        String filename = args[0];
        File file = new File(filename);

        // print the result of counting keywords
        System.out.println("The number of keywords in " + filename + " is " + countKeywords(file));
    }

    // method to count keywords
    public static int countKeywords(File file) throws Exception {
        // Array of all Java keywords + true, false and null
        String[] keywordString = { "abstract", "assert", "boolean",
                "break", "byte", "case", "catch", "char", "class", "const",
                "continue", "default", "do", "double", "else", "enum",
                "extends", "for", "final", "finally", "float", "goto",
                "if", "implements", "import", "instanceof", "int",
                "interface", "long", "native", "new", "package", "private",
                "protected", "public", "return", "short", "static",
                "strictfp", "super", "switch", "synchronized", "this",
                "throw", "throws", "transient", "try", "void", "volatile",
                "while", "true", "false", "null" };

        Set<String> keywordSet = new HashSet<>(Arrays.asList(keywordString));
        int count = 0; // Initialize the count of keywords to 0
        boolean isInComment = false; // Flag to track if inside a comment
        boolean isInString = false; // Flag to track if inside a string

        // create scanner
        Scanner input = new Scanner(file);

        // loop through each line of the file
        while (input.hasNextLine()) {
            String line = input.nextLine().trim(); // Read and trim whitespace from each line

            // Check if not inside a comment or string
            if (!isInComment && !isInString) {
                // loop through each character of the line
                for (int i = 0; i < line.length(); i++) {
                    char currentChar = line.charAt(i);

                    // check for start of line comment
                    if (currentChar == '/' && i < line.length() - 1 && line.charAt(i + 1) == '/') {
                        break; // Ignore the rest of the line
                    }
                    // check for start of block comment
                    else if (currentChar == '/' && i < line.length() - 1 && line.charAt(i + 1) == '*') {
                        isInComment = true; // set flag to true (inside a block comment)
                        i++; // skip '*' in the next iteration
                    }
                    // check for start or end of string
                    else if (currentChar == '"') {
                        isInString = !isInString; // toggle the flag (inside or outside a string)
                    }
                    // check for keywords
                    else if (Character.isLetterOrDigit(currentChar)) {
                        StringBuilder wordBuilder = new StringBuilder(); // StringBuilder to build the word
                        // Loop to extract the complete word
                        while (i < line.length() && Character.isLetterOrDigit(line.charAt(i))) {
                            wordBuilder.append(line.charAt(i)); // append characters to the word
                            i++; // itterate character
                        }
                        String word = wordBuilder.toString(); // sonvert StringBuilder to string
                        // Check if the word is a keyword
                        if (keywordSet.contains(word)) {
                            count++; // increment count if keyword
                        }
                    }
                }
            }
            // check if inside a block comment and if it ends in the current line
            else if (isInComment && line.contains("*/")) {
                isInComment = false; // Set flag to false (outside a block comment)
            }
        }

        input.close(); // close scanner

        return count; // return amount of keywords
    }
}
