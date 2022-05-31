/**
 * Partition.java  06/04/2015


 *
 * @author - Jane Doe
 * @author - Period n
 * @author - Id nnnnnnn
 *
 * @author - I received help from ... why from 2015
 *
 */

//why 
import java.util.List;
import java.util.ArrayList;

public class Partition
{
	private String       wordPattern;
	private List<String> wordList;

	/**
	 * Construct a Partition object with no words.
	 * @param pattern the pattern for the partition.
	 */
	public Partition(String pattern)
	{
		 wordPattern = pattern;
	       
		 wordList = new ArrayList<>();
//easyyy
	}

	/**
	 * Construct a Partition object with one given word.
	 * @param pattern the pattern for the partition.
	 * @param word the initial word for the partition.
	 */
	public Partition(String pattern, String word)
	{
		  wordPattern = pattern;
	        
		  wordList = new ArrayList<>();
	       
		  wordList.add(word);

	}

	/**
	 * Adds word to the partition IFF the pattern matches the given one.
	 * @param pattern the pattern to check for a match.
	 * @param word the word to add if the patterns match.
	 * @returns true if word is added; false otherwise.
	 */
	public boolean addIfMatches(String pattern, String word)
	{
		if(pattern == this.wordPattern){
           
			wordList.add(word);
            return true;
        }
        return false;  

	}

	/**
	 * @return the pattern's words.
	 */
	public List<String> getWords()
	{
		 return wordList;   

	}

	/**
	 * @return the number of dashs in the partition's pattern.
	 */
	public int getPatternDashCount()
	///whyyyyyyyyy
	{
		 int dash = 0;
	        for(int i = 0; i < wordPattern.length(); i++){
	            
	        	if(wordPattern.charAt(i)=='-')
	                dash++;
	        }
	        return dash;
	    }

	}

