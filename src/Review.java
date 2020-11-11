/* 
 * @author dev 
 * 
 * @since 2020-10-01
 * 
 */
import java.io.*;
import java.io.File;
import java.lang.Math;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;



public class Review{

	// Hashmap to hold words and their sentiment values from csv file
    // Arraylists to hold positive and negative adjectives
    private static HashMap<String, Double> sentiment = new HashMap<String, Double>();
    private static ArrayList<String> positiveAdj = new ArrayList<String>();
    private static ArrayList<String> negativeAdj = new ArrayList<String>();
    
    
    
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
    public static String deletePunctuation( String word ) {
    	while(word.length() > 0 && !Character.isAlphabetic(word.charAt(0))) {
			word = word.substring(1);
		}
		while(word.length() > 0 && !Character.isAlphabetic(word.charAt(word.length()-1))) {
			word = word.substring(0, word.length()-1);
		}
    
    	return word;
  	}



	// returns the ending punctuation if there is any at all; otherwise retuns empty string
	public static String retrievePunctuation( String word ) { 
    	String p = "";

		for(int i = word.length() - 1; i >= 0; i--) {

			if( !Character.isLetterOrDigit(word.charAt(i)) ){
				p = p + word.charAt(i);
			} else {
				return p;
			}
		}
		return p;
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



	// returns a more negative adjective than the one specified.
	public static String moreNegative(String word){
		String randNegWord = randomNegative();

        while (getSentiment(randNegWord) > getSentiment(word)) {
          	randNegWord = randomNegative();
        }

		return randNegWord;
	} 

	// returns a more positive adjective than the one specified.
	public static String morePositive(String word){
		String randPosWord = randomPositive();

        while (getSentiment(randPosWord) < getSentiment(word)) {
          	randPosWord = randomPositive();
        }

		return randPosWord;
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
		String[] words = fileContents.split(" ");
		double sentimentTotal = 0;


		for (String w : words) {
			sentimentTotal += getSentiment(deletePunctuation(w));
		}

    	return sentimentTotal;
  	}
	
	
	
    
    // modifies the original review txt file by replacing all adjectives marked by '*' with relatively more negative adjectives, thus shaping the
    // the original review into a negative review. Same process is done for shaping it into a positive review.
    public static String slantMoreNegative(String fileName) {

       	String rev = fileToString(fileName);
	   	String word = "";
		String negRev = "";
		
		
		for (int i = 0; i < rev.length(); i++)
		{
			if ( ((rev.substring(i, i+1).equals(" ")) || (i == rev.length() - 1)) == false) {
				word += rev.substring(i, i+1);

			} else if (i == rev.length() - 1) {
				word += rev.substring(rev.length() - 1, rev.length());

				if (word.substring(0, 1).equals("*")) {
					negRev += moreNegative(deletePunctuation(word)) + retrievePunctuation(word);
				} else {
					negRev += word;
				}

			} else if (word.substring(0, 1).equals("*")) {
				negRev += moreNegative(deletePunctuation(word)) + retrievePunctuation(word) + " ";
				word = "";

			} else {
				negRev += word + " ";
				word = "";
			}
		}
		return negRev;
    }
    
    
    
    
    public static String slantMorePositive(String fileName) {
		
       	String rev = fileToString(fileName);
	   	String word = "";
		String posRev = "";
		
		
		for (int i = 0; i < rev.length(); i++)
		{
			if ( ((rev.substring(i, i+1).equals(" ")) || (i == rev.length() - 1)) == false) {
				word += rev.substring(i, i+1);

			} else if (i == rev.length() - 1) {
				word += rev.substring(rev.length() - 1, rev.length());

				if (word.substring(0, 1).equals("*")) {
					posRev += morePositive(deletePunctuation(word)) + retrievePunctuation(word);
				} else {
					posRev += word;
				}

			} else if (word.substring(0, 1).equals("*")) {
				posRev += morePositive(deletePunctuation(word)) + retrievePunctuation(word) + " ";
				word = "";

			} else {
				posRev += word + " ";
				word = "";
			}
		}
		return posRev;
    }

    
}// class end
