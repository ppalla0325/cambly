import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.io.FileWriter;

/**
 * Class Desc:
 * Takes in a text file and prints the occurrences of unique words in file.
 * prints to console and generates a file the occurrences of unique words with the Form:
 * "Word Count"
 */
public class UniqueWords {
    /**
     * wordMap
     * LinkedHashMap: "Key: Unique Word" "Value : Count of Occurrences"
     * Use 'Linked' so we don't waste no more space than the amt. of Unique words in the text.
     */
    private LinkedHashMap<String, Integer> wordMap;

    /**
     * wordList
     * ArrayList: Stores the unique words and their corresponding count.
     * Useful for sorting.
     */
    private ArrayList<WordCount> wordList;


    /**
     * Constructor to instantiate both our wordMap which holds the occurrences of words
     * and the wordList that holds the sorted word count.
     * @preconditions None
     * @postconditions wordMap and wordList member variables are instantiated.
     * @params none
     * @return none
     */
    public UniqueWords() {
        wordMap = new LinkedHashMap<>();
        wordList = new ArrayList<>();
    }


    /**
     * Prints to console the count of unique words in the text.
     * Creates a file named "uniqueWords.txt"
     * Form:
     * "word count" -- 'end line'
     * @preconditions none
     * @postconditons wordMap now stores mapping of unique words to count and
     * wordList now stores in sorted order the unique words. Console will then print
     * the unique words and their corresponding counts.
     * @param scan Scanner object that represents the text file of the given text
     * @return none
     */
    public void generateUniqueWords(Scanner scan) {

        //first send the file and count the words
        //this will make the given word mapping in the hashmap
        countUniqueWords(scan);

        //copy the key:value pair in the wordMap into wordList
        //in the order the key's were added into the hashmap
        populateWordList();

        //sort the wordList using quicksort in-place sorting algorithm
        quickSortWordList(0, wordList.size() - 1);

        //print the unique words in the Form:
        //"word count" -- 'end line'
        //Loops through the wordList array backwards
        printWordList();

        //generate a file with the unique words in Form:
        //"word count" -- 'end line'
        //Loops through the wordList array backwards
        writeToFile();
    }

    /**
     * Creates a file named "uniqueWords.txt"
     * Form:
     * "word count" -- 'end line'
     * @preconditions none
     * @postconditions file "uniqueWords.txt generated with unique words
     * @param
     * @return none
     */
    private void writeToFile()
    {
        try {

            FileWriter writeFile = new FileWriter("uniqueWords.txt");
            for (int i = wordList.size() - 1; i >= 0; i--) {
                writeFile.write(wordList.get(i).word + " " + wordList.get(i).count + "\r\n");
            }
            writeFile.close();

        }catch(Exception e)
        {
            System.out.print(e);
        }
    }

    /**
     * scans the .txt file and counts the unique words in the file
     *
     * IMPLEMENTATION:
     * 1. scan the word using a regex to replace punctuation when forming the words
     * 2. check to see if the "word" exists in the map
     * 3. if the "word" exists, increment the counter of the word by 1
     * 4. if the "word" does not exists, insert the word in the map and set the count to 1
     * 5. scan the entire text of the file and increment the words accordingly
     *
     * @precondtions none
     * @postconditions wordMap stores the count of unique words
     * @param scan Scanner object that represents the text file of the given text
     * @return none
     */
    private void countUniqueWords(Scanner scan) {

        //loop through the entire given text file
        while (scan.hasNext()) {
            //word scanned from file with punctuation removed
            String toAdd = parseWord(scan);
            //check to see if the word exists in the map
            if (wordMap.containsKey(toAdd)) {
                //increment count of word
                int count = wordMap.get(toAdd);
                wordMap.replace(toAdd, ++count);
            } else {
                //add the word to the map and set the count to 1
                wordMap.put(toAdd, 1);
            }
        }
    }

    /**
     * Prints to console the count of unique words in Descending order.
     * Form:
     * "word count" -- 'end line'
     * @preconditions sorted wordList
     * @postconditions console will print the count of unique words in the text file
     * @param
     * @return none
     */
    private void printWordList() {
        for (int i = wordList.size() - 1; i >= 0; i--) {
            System.out.println(wordList.get(i).word + " " + wordList.get(i).count);
        }
    }

    /**
     * copy the key:value pairs in the LinkedHashMap into the array
     * using WordCount objects that store both the "word and count"
     * @preconditions wordMap stores the final result from scanning the text file
     * @postconditions wordList will now store the unique values
     * @param
     * @return none
     */
    private void populateWordList() {
        for (String key : wordMap.keySet()) {
            wordList.add(new WordCount(key, wordMap.get(key)));
        }
    }

    /**
     * scans the word in the text file and replaces the punctuation with empty space
     * and sets the word to lowercase.
     * @preconditions none
     * @postconditions Scanned word removed of punctuation
     * @param scan
     * @return
     */
    private String parseWord(Scanner scan) {
        return (scan.next().replaceAll("[,.;]", "").toLowerCase());
    }

    /**
     * to find the middle index for the wordList of the given subarray
     * @param low the beginning index
     * @param high the ending index
     * @return int, the middle of the given array index
     */
    private int middle(int low, int high) {
        return low + (high - low) / 2;
    }

    /**
     * Sorts the wordList in Ascending order
     *
     * Recursive QuickSort Algorithm, chooses the pivot at the middle index of the given subarray and
     * places the pivot index value at the correct index by sorting the left and right subarrays.
     *
     * @preconditions populated wordList
     * @postConditions sorted wordList
     * @param low beginning index
     * @param high ending index
     */
    private void quickSortWordList(int low, int high) {
        int i = low;
        int j = high;

        //pivot will be the middle index of the given subarray
        int pivot = wordList.get(middle(low, high)).count;

        //divides the array into 2 subarrays
        //the dividing index will be the correct position of the value at the pivot index
        while (i <= j) {
            /**
             * Search for a number 'A' greater than the pivot on the left side
             * Search for a number 'B' less than the pivot on the right side
             * swap the number 'A' and 'B'
             */
            while (wordList.get(i).count < pivot) {
                i++;
            }
            while (wordList.get(j).count > pivot) {
                j--;
            }

            //if the greater than number behind the pivot is also behind the less than number in front of pivot
            //swap the 2 numbers
            if (i <= j) {
                Collections.swap(wordList, i, j);
                //increment and decrement counters
                i++;
                j--;
            }
        }

        //call QuickSort method on the left subarray of the pivot.
        if (low < j) {
            quickSortWordList(low, j);
        }
        //call QuickSort method on the right subarray of the pivot.
        if (i < high) {
            quickSortWordList(i, high);
        }
    }
}

