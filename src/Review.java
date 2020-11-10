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
    
    
    
    // returns a string containing the specified file's contents. Words are single-space-delimited format (separated by a single space)
     
    
    
}// class end
