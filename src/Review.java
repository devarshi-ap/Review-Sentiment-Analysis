/* 
 * @author dev 
 * 
 * @since 2020-10-01
 * 
 */
import java.io.*;
import java.io.File;
import java.util.Random;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;



public class Review{

    // Hashmap to hold words and their sentiment values from csv file
    // Arraylists to hold positive and negative adjectives
    private static HashMap<String, Double> sentiment = new HashMap<String, Double>();
    private static ArrayList<String> positiveAdj = new ArrayList<String>();
    private static ArrayList<String> negativeAdj = new ArrayList<String>();
    
    
    private static final String SPACE = " ";
    
    
    
    
    static
    {
        // Copy content of long csv file onto Hashmap 'sentiment'
        try {
            Scanner sc = new Scanner(new File("cleanSentiment.csv"));
            while (sc.hasNextLine()) {
                String[] hold = sc.nextLine().split(",");
                sentiment.put( hold[0], Double.parseDouble(hold[1]) );                              
            }
            sc.close();
            
        } catch (Exception e) {
            System.out.println("Error while transfering cleanSentiment.csv onto Hashmap");
        }
        
        
        
        // Copy content of positiveAdjectives.txt onto positveAdj Arraylist
        try {
            Scanner sc = new Scanner(new File("positiveAdjectives.txt"));
            
            while (sc.hasNextLine())
                positiveAdj.add( sc.nextLine().trim() );        
            sc.close();
            
        } catch (Exception e) {
            System.out.println("Error while transfering positiveAdjectives.txt onto Arraylist");
        }
        
        
        
        // Copy content of negativeAdjectives.txt onto negativeAdj Arraylist
        try {
            Scanner sc = new Scanner(new File("negativeAdjectives.txt"));
            
            while (sc.hasNextLine())
                negativeAdj.add( sc.nextLine().trim() );        
            sc.close();
            
        } catch (Exception e) {
            System.out.println("Error while transfering negativeAdjectives.txt onto Arraylist");
        }
        
    }// static end
    
    
    
    
    // returns a single-space-delimited (separated by a single space) string of the specified file's content
    public static String fileToString(String fileName) {
        String text = "";
        
        try {
            Scanner sc = new Scanner(new File(fileName));
            
            while (sc.hasNext()) {
                text += sc.next() + " ";
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Error while fileToString; most likely file not found");
        }
        
        //trim extra " " at the end
        return text.trim(); 
    }
    
    
    
    /* returns a word after deleting any punctuation attached to it from the original sentence like '?', '!', '.', etc...
     * this is to allow for seemless sentiment value retrieval when getSentiment is called and compares words.
    */
    pubic static String deletePunctuation(String word) {
        // Character.isAlpabetic checks to see if the char is a unicode alpabet (not punctuation)
        while (word.length() >= 1  &&  !Character.isAlphabetic(word.charAt(0))) {
            word = word.substring(1);
        }
        
        while (word.length >= 1  &&  !Character.isAlphabetic(word.charAt(word.length() - 1))) {
            word = word.substring(0, word.length() - 1);
        }
        
        return word
    }
    
    
    
    // The next 2 methods return a random adjective (positive/negative) from their respective arraylists
    public static String randomPositive() {
        int a = (int) ( Math.random() * positiveAdj.size() );
        return positiveAdj.get(a);
    }
    
    public static String randomNegative() {
        int a = (int) ( Math.random() * negativeAdj.size() );
        return negativeAdj.get(a);
    }    
    
    
    
    // returns a random adjective from either the Arraylist of positive adjectives or negative adjectives (chooses randomly)
    public static String randomAdj() {
        double rand = Math.random();
        if (rand > 0.5) {
            return randomPositive();
        } else {
            return randomNegative();
        }
    }
    
    
    
    // returns a given word's <double> sentiment value in hashmap if found; otherwise, return 0. (Handles for letter casing)
    public static double getSentiment(String word) {
        try                 {  return sentiment.get(word.toLowerCase());  }
        catch(Exception e)  {  return 0;  }
    }
    
    
    
    // returns the total sentiment value of all words in a given txt file
    public static double totalSentiment(String filename) {
        String fileContents = fileToString(filename);
        
        double sentimentTotal = 0;
        String[] words = fileContents.split(" ");   // words in file.txt string are placed in array broken down by " ".

        for (String w : words) {
          sentimentTotal += getSentiment( deletePunctuation(w) );
        }

        return sentimentTotal;
    }

    
    
    // modifies the original review txt file by replacing all adjectives marked by '*' with relatively more negative adjectives, thus shaping the
    // the original review into a negative review. Same process is done for shaping it into a positive review.
    public static String slantedNegative(String fileName) {
        String rev = textToString(fileName);
        
        String negRev = "";
        String randNegWord = "";
        boolean hasAstr = false;

        String[] revWords = rev.split(" ");

        
        for (String t : revWords) {

            if (t.startsWith("*"))
            {
                randNegWord = randomNegativeAdj();
                t = t.substring(1);

                // Checks if random negative word created is more negative than original word. Note: won't work if original word has minimum sentiment value possible ("ugly")
                while (sentimentVal(randNegWord) > sentimentVal(t))
          	        randNegWord = randomNegativeAdj();
        
	            negRev += randNegWord + " ";
                
            } else {
                negRev += t + " ";
            }
        }

        return negRev;
    }
    
    
    
    
    public static String slantedPositive(String fileName) {
        String rev = textToString(fileName);
        
        String negRev = "";
        String randNegWord = "";
        boolean hasAstr = false;

        String[] revWords = rev.split(" ");

        
        for (String t : revWords) {

            if (t.startsWith("*"))
            {
                randNegWord = randomNegativeAdj();
                t = t.substring(1);

                // Checks if random negative word created is more negative than original word. Note: won't work if original word has minimum sentiment value possible ("ugly")
                while (sentimentVal(randNegWord) > sentimentVal(t))
          	        randNegWord = randomNegativeAdj();
        
	            negRev += randNegWord + " ";
                
            } else {
                negRev += t + " ";
            }
        }

        return negRev;
    }

    
}// class end
