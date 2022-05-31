import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author - Hiya
 * 
 *
 */
public class EvilHangman
{
	private boolean      debug;
	private Scanner      in;
	private List<String> wordList;
	private int          wordLength;
	private int          remainingGuesses;
	private String       solution;
	private String       guessedLetters;

    private Scanner      file;

	/**
	 * Construct an EvilHangman object.
	 * @param fileName the name of the file that contains the word list.
	 * @param debug indicates if the size of the remaining word list
	 *        should be included in the toString result.
	 */
   public EvilHangman(String fileName, boolean debug1)
   {
	   debug = debug1;
       in =  new Scanner(System.in);
       
     
       
       inputWords(fileName);
      
       System.out.print("Number of guesses? ");
      
       remainingGuesses = in.nextInt();
       solution = "";
      
       for(int i = 0; i < wordLength; i++){
           solution += "-";
       }
       guessedLetters = "" ;
   }

	/**
	 * Plays one the game.  The user guesses letters until either
	 * they guess the word, or they run out of guesses.
	 * Game status is printed each turn and winning / losing
	 * information is printed at the conclusion of the game.
	 */
   public void playGame()
   {

       boolean w = true;
       	for(int i = 0; i < solution.length(); i++){
       		if(solution.charAt(i)=='-') {
       			w = false;
       			}
       	}
       	
        while(remainingGuesses > 0  && w == false){

            System.out.println(this);
           
            String l = inputLetter();
            guessedLetters += l;
            
            
            List<Partition> p=getPartitionList(l);
            
            removeSmallerPartitions(p);
            
            wordList=getWordsFromOptimalPartition(p);
            
            
            String x = solution;
            
            substitute(wordList.get(0), l);
            
            w = true;
            for(int i  = 0; i < solution.length(); i++){
                if(solution.charAt(i)=='-') {
                    w = false;
                }
            }
            if(solution.equals(x)){
                remainingGuesses--;
            }
        }
        if(remainingGuesses > 0){
            System.out.println("You Win!");
        }
        else{
            System.out.println("You Lose :(");
        }
        System.out.print("The word was \"" + wordList.get(0) + "\"");
    }

       	
   

	/**
	 * Creates and returns a status string that indicates the
	 * state of the game.
	 * @return the game status string.
	 */
   public String toString()
   {
	   if(debug == true) {
          return "Remaining guesses: "+remainingGuesses+"\nGuessed letters: "+guessedLetters+"\nSolution: "+solution+"\nremaining words: "+wordList.size();
	   }
	   
          return "Remaining guesses: "+remainingGuesses+"\nGuessed letters: "+guessedLetters+"\nSolution: "+solution;    
   }



	////////// PRIVATE HELPER METHODS //////////

	/**
	 * Helper method for the constructor:
	 * Inputs the word length from the user, reads in the words from
	 * the fileName file, and initializes the wordList instance variable
	 * with the words of the correct length.  This method loops until
	 * a valid word length is entered.
	 * @param fileName the name of the file that contains the word list.
	 */
   private void inputWords(String fileName)
   {
	   wordList = new ArrayList<>();
       try{
           file = new Scanner(new File(fileName));
       }
       
       catch(FileNotFoundException e){
    	   
       }
       while(wordList.size()==0){
           System.out.print("Word Length: ");
           wordLength = in.nextInt();
           
           
           
           while(file.hasNext()){
               String str = file.next();
               
               if(str.length()==wordLength) {
                   wordList.add(str);
               }
           }
           if(wordList.size()==0){
               System.out.println("There are no words with " + wordLength + " letters.");
           }
       }
   }
   

	/**
	 * Helper method for playGame:
	 * Inputs a one-letter string from the user.
	 * Loops until the string is a one-character character in the range
	 * a-z or A-Z.
	 * @return the single-letter string converted to upper-case.
	 */
	private String inputLetter()
	{
		return null;	
	}

	/**
	 * Helper method for getPartitionList:
	 * Uses word and letter to create a pattern.  The pattern string
	 * has the same number of letter as word.  If a character in
	 * word is the same as letter, the pattern is letter; Otherwise
	 * it's "-".
	 * @param word the word used to create the pattern
	 * @param letter the letter used to create the pattern
	 * @return the pattern
	 */
	private String getPattern(String word, String letter)
	{

        String s = "";
      
        
        for(int i = 0; i < word.length(); i++){
          
        	if(word.charAt(i)!=letter.charAt(0)){
                s += "-";
            }
            else{
                s += letter;
            }
        }
        return s;   

	}

	/**
	 * Helper method for playGame:
	 * Partitions each string in wordList based on their patterns.
	 * @param letter the letter used to find the pattern for
	 *        each word in wordList.
	 * @return the list of partitions.
	 */
	private List<Partition> getPartitionList(String letter)
	{
		 List<Partition> partition = new ArrayList<>();
	       
		 
		 for(int i = 0; i < wordList.size(); i++){
	           
	        	String str=getPattern(wordList.get(i), letter);
	            
	        	if(partition.size() == 0){
	                partition.add(new Partition(str, wordList.get(i)));
	            }
	            
	        	for(int x = 0; x < partition.size(); x++){
	              
	        		if(!partition.get(x).addIfMatches(str, wordList.get(i))){
	                	partition.add(new Partition(str, wordList.get(i)));
	                }
	            }
	        }
	        return partition;
	}

	/**
	 * Helper method for playGame:
	 * Removes all but the largest (most words) partitions from partitions.
	 * @param partitions the list of partitions.
	 *        Precondition: partitions.size() > 0
	 * Postcondition: partitions
	 * 1) contains all of the partitions with the most words.
	 * 2) does not contain any of the partitions with fewer than the most words.
	 */
	private void removeSmallerPartitions(List<Partition> partitions)
	{
		
		int biggest = 0;
	      
		for(int i = 0; i < partitions.size(); i++){
	           
	        	if(partitions.get(i).getWords().size() > biggest)
	                biggest = partitions.get(i).getWords().size();
	        }
	        for(int i = 0; i < partitions.size(); i++){
	            
	        	if(partitions.get(i).getWords().size() < biggest){
	                partitions.remove(i);
	                i--;
	            }
	        }


	}

	/**
	 * Helper method for playGame:
	 * Returns the words from one of the optimal partitions,
	 *    that is a partition with the most dashes in its pattern.
	 * @param partitions the list of partitions.
	 * @return the optimal partition.
	 */
	private List<String> getWordsFromOptimalPartition(List<Partition> partitions)
	{
		int index = 0;
       
		int high = partitions.get(0).getPatternDashCount();
        
        for(int i = 0; i < partitions.size(); i++){
            
        	if(partitions.get(i).getPatternDashCount() > high){
              
            	index = i;
                
            	high = partitions.get(i).getPatternDashCount();
            }
        }
        return partitions.get(index).getWords();

	}

	/**
	 * Helper method for playGame:
	 * Creates a new string for solution.  If the ith letter of
	 * found equals letter, then the ith letter of solution is
	 * changed to letter; Otherwise it is unchanged.
	 * @param found the string to check for occurances of letter.
	 * @param letter the letter to check for in found.
	 */
	private void substitute(String found, String letter)
	{
		String newSolution = "" ;
        for(int i = 0; i < found.length(); i++){
            if(found.charAt(i)==letter.charAt(0)){
            	
            
                newSolution+=letter;
            }
            else{
            	
            
                newSolution+=solution.substring(i, i+1);
            }
        }
        solution = newSolution;
    }

	
}
