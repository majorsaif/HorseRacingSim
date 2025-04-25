import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.lang.Math;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
* @author McRaceface
 * @version 1.0
 */
public class Race
{
    private int raceLength;
    private Horse lane1Horse;
    private Horse lane2Horse;
    private Horse lane3Horse;
    private static final double fallProb = 0.01;
    private static Scanner scanner = new Scanner(System.in); // Single Scanner object for the entire class

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance)
    {
        if(distance <= 0){
            throw new IllegalArgumentException("Race length must be positive");
        }
        // initialise instance variables
        raceLength = distance;
        lane1Horse = null;
        lane2Horse = null;
        lane3Horse = null;
    }
    
    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber)
    {
        if (laneNumber == 1)
        {
            if(lane1Horse != null){
                System.out.println("Lane 1 already has a horse in it!");
                System.out.println("Do you want to overwrite this horse? (y/n)");
                String ans = scanner.nextLine();
                if(ans.equalsIgnoreCase("y")){
                    lane1Horse = theHorse;
                }
            }else{
                lane1Horse = theHorse;
            }
        }
        else if (laneNumber == 2)
        {
            if(lane2Horse != null){
                System.out.println("Lane 2 already has a horse in it!");
                System.out.println("Do you want to overwrite this horse? (y/n)");
                String ans = scanner.nextLine();
                if(ans.equalsIgnoreCase("y")){
                    lane2Horse = theHorse;
                }
            }else{
                lane2Horse = theHorse;
            }
        }
        else if (laneNumber == 3)
        {
            if(lane3Horse != null){
                System.out.println("Lane 3 already has a horse in it!");
                System.out.println("Do you want to overwrite this horse? (y/n)");
                String ans = scanner.nextLine();
                if(ans.equalsIgnoreCase("y")){
                    lane3Horse = theHorse;
                }
            }else{
                lane3Horse = theHorse;
            }
        }
        else
        {
            System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
        }
    }
    
    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    public void startRace()
    {
        //declare a local variable to tell us when the race is finished
        boolean finished = false;
        
        //reset all the lanes (all horses not fallen and back to 0). 
        if(lane1Horse != null)lane1Horse.goBackToStart();
        if(lane2Horse != null)lane2Horse.goBackToStart();
        if(lane3Horse != null)lane3Horse.goBackToStart();
                      
        while (!finished)
        {
            //move each horse
            if(lane1Horse != null) moveHorse(lane1Horse);
            if(lane2Horse != null) moveHorse(lane2Horse);
            if(lane3Horse != null) moveHorse(lane3Horse);
                        
            //print the race positions
            printRace();
            
            //if any of the three horses has won the race is finished
            if((lane1Horse != null && raceWonBy(lane1Horse)) || 
                (lane2Horse != null && raceWonBy(lane2Horse)) || 
                (lane3Horse != null && raceWonBy(lane3Horse)) ){
                finished = true;
            }

            //if all three horses have fallen, the race is finished with no winner
            if((lane1Horse != null && lane1Horse.hasFallen()) &&
               (lane2Horse != null && lane2Horse.hasFallen()) &&
               (lane3Horse != null && lane3Horse.hasFallen())){
                System.out.println("All horses have fallen. No winner.");
                return;
            }
           
            //wait for 100 milliseconds
            try{ 
                TimeUnit.MILLISECONDS.sleep(100);
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
                System.out.println("Race interrupted");
                return;
            }
        }

        // Finds the winner and then prints it out
        Horse winner = null;
        if(lane1Horse != null && raceWonBy(lane1Horse)){
            winner = lane1Horse;
        }else if(lane2Horse != null && raceWonBy(lane2Horse)){
            winner = lane2Horse;
        }else if(lane3Horse != null && raceWonBy(lane3Horse)){
            winner = lane3Horse;
        }

        if(winner != null){
            System.out.println("Winner is " + winner.getName() + "!");
        }
    }
    
    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse)
    {
        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen
        if  (!theHorse.hasFallen())
        {
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence())
            {
               theHorse.moveForward();
            }
            
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (fallProb * theHorse.getConfidence() * theHorse.getConfidence()))
            {
                theHorse.fall();
            }
        }
    }
        
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse)
    {
        if (theHorse.getDistanceTravelled() >= raceLength)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /***
     * Print the race on the terminal
     */
    private void printRace()
    {
        System.out.print('\u000C');  //clear the terminal window
        
        multiplePrint('=',raceLength+3); //top edge of track
        System.out.println();
        
        if (lane1Horse != null) printLane(lane1Horse);
        System.out.println();
        
        if (lane2Horse != null) printLane(lane2Horse);
        System.out.println();
        
        if (lane3Horse != null) printLane(lane3Horse);
        System.out.println();
        
        multiplePrint('=',raceLength+3); //bottom edge of track
        System.out.println();    
    }
    
    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(Horse theHorse)
    {
        //calculate how many spaces are needed before
        //and after the horse
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();
        
        //print a | for the beginning of the lane
        System.out.print('|');
        
        //print the spaces before the horse
        multiplePrint(' ',spacesBefore);
        
        //if the horse has fallen then print dead
        //else print the horse's symbol
        if(theHorse.hasFallen())
        {
            System.out.print('X'); // Print 'X' emoji to signal a horse has fallen
        }
        else
        {
            System.out.print(theHorse.getSymbol());
        }
        
        //print the spaces after the horse
        multiplePrint(' ',spacesAfter);
        
        //print the | for the end of the track
        System.out.print('|');
    }
        
    
    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times)
    {
        int i = 0;
        while (i < times)
        {
            System.out.print(aChar);
            i = i + 1;
        }
    }

    public static void main(String[] args) {
        Race race = new Race(100); // Example race length
        Horse horse1 = new Horse('S', "Sora", 0.7);
        Horse horse2 = new Horse('M', "Midnight", 0.5);
        Horse horse3 = new Horse('G', "Gallop", 0.2);

        race.addHorse(horse1, 1);
        race.addHorse(horse2, 2);
        race.addHorse(horse3, 3);

        race.startRace();
        scanner.close(); // Close the scanner at the end of the program
    }
}
