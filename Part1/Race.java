import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.lang.Math;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author McRaceface
 * @author McRaceface
 * @author McRaceface
 * @version 1.0
 */
public class Race {
    private int raceLength;
    private Horse lane1Horse;
    private Horse lane2Horse;
    private Horse lane3Horse;
    private static final double fallProb = 0.01;
    public static Scanner scanner = new Scanner(System.in); // Single Scanner object for the entire class

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("Race length must be positive");
        }
        raceLength = distance;
        lane1Horse = null;
        lane2Horse = null;
        lane3Horse = null;
    }

    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse   the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber) {
        if (laneNumber == 1) {
            // Validates if another horse is already in the lane
            if (lane1Horse != null) {
                System.out.println("Lane 1 already has a horse in it!");
                System.out.println("Do you want to overwrite this horse? (y/n)");
                String ans = scanner.nextLine();
                if (ans.equalsIgnoreCase("y")) {
                    lane1Horse = theHorse;
                }
            } else {
                lane1Horse = theHorse;
            }
        } else if (laneNumber == 2) {
            if (lane2Horse != null) {
                System.out.println("Lane 2 already has a horse in it!");
                System.out.println("Do you want to overwrite this horse? (y/n)");
                String ans = scanner.nextLine();
                if (ans.equalsIgnoreCase("y")) {
                    lane2Horse = theHorse;
                }
            } else {
                lane2Horse = theHorse;
            }
        } else if (laneNumber == 3) {
            if (lane3Horse != null) {
                System.out.println("Lane 3 already has a horse in it!");
                System.out.println("Do you want to overwrite this horse? (y/n)");
                String ans = scanner.nextLine();
                if (ans.equalsIgnoreCase("y")) {
                    lane3Horse = theHorse;
                }
            } else {
                lane3Horse = theHorse;
            }
        } else {
            System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
        }
    }

    /**
     * Start the race
     * The horses are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    public void startRace() {
        boolean finished = false;

        // Resets all horses in their positions
        if (lane1Horse != null) lane1Horse.goBackToStart();
        if (lane2Horse != null) lane2Horse.goBackToStart();
        if (lane3Horse != null) lane3Horse.goBackToStart();

        // Moves the horses while the race is still ongoing
        while (!finished) {
            if (lane1Horse != null) moveHorse(lane1Horse);
            if (lane2Horse != null) moveHorse(lane2Horse);
            if (lane3Horse != null) moveHorse(lane3Horse);

            printRace();

            // Check if any horse has won
            if ((lane1Horse != null && raceWonBy(lane1Horse)) ||
                (lane2Horse != null && raceWonBy(lane2Horse)) ||
                (lane3Horse != null && raceWonBy(lane3Horse))) {
                finished = true;
            }

            // Check if all horses have fallen
            if ((lane1Horse != null && lane1Horse.hasFallen()) &&
                (lane2Horse != null && lane2Horse.hasFallen()) &&
                (lane3Horse != null && lane3Horse.hasFallen())) {
                System.out.println("All horses have fallen. No winner.");
                return;
            }

            // Pause briefly before the next iteration
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Race interrupted");
                return;
            }
        }

        // Determine the winner
        Horse winner = null;
        if (lane1Horse != null && raceWonBy(lane1Horse)) {
            winner = lane1Horse;
        } else if (lane2Horse != null && raceWonBy(lane2Horse)) {
            winner = lane2Horse;
        } else if (lane3Horse != null && raceWonBy(lane3Horse)) {
            winner = lane3Horse;
        }

        // Announce the winner
        if (winner != null) {
            System.out.println("Winner is " + winner.getName() + "!");
            winner.setConfidence(Math.min(1.0, winner.getConfidence() + 0.1)); // Winner gains confidence
        } 
    }

    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse) {
        if (!theHorse.hasFallen()) {
            // Move the horse forward based on its confidence
            if (Math.random() < theHorse.getConfidence()) {
                theHorse.moveForward();
            }

            // Determine if the horse falls based on its confidence and fall probability
            if (Math.random() < (fallProb * theHorse.getConfidence() * theHorse.getConfidence())) {
                theHorse.fall();            
                theHorse.setConfidence(Math.max(0.0, theHorse.getConfidence() - 0.05)); // Horse loses confidence when falling
            }
        }
    }

    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse) {
        return theHorse.getDistanceTravelled() >= raceLength;
    }

    /***
     * Print the race on the terminal
     */
    private void printRace() {
        System.out.print('\u000C'); // clear the terminal window

        multiplePrint('=', raceLength + 3); // top edge of track
        System.out.println();

        if (lane1Horse != null) printLane(lane1Horse);
        System.out.println();

        if (lane2Horse != null) printLane(lane2Horse);
        System.out.println();

        if (lane3Horse != null) printLane(lane3Horse);
        System.out.println();

        multiplePrint('=', raceLength + 3); // bottom edge of track
        System.out.println();
    }

    /**
     * Print a horse's lane during the race
     */
    private void printLane(Horse theHorse) {
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();

        System.out.print('|');
        multiplePrint(' ', spacesBefore);

        if (theHorse.hasFallen()) {
            System.out.print('X'); // Print 'X' emoji to signal a horse has fallen
        } else {
            System.out.print(theHorse.getSymbol());
        }

        multiplePrint(' ', spacesAfter);
        System.out.print('|');
    }

    /***
     * Print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times) {
        for (int i = 0; i < times; i++) {
            System.out.print(aChar);
        }
    }
}