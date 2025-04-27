public class RaceTest {
    public static void main(String[] args) {
        // Test 1: Create a race with a valid length
        System.out.println("Test 1: Creating a race with a valid length...");
        Race race = new Race(50);
        System.out.println("Race created successfully with length 50.\n");

        // Test 2: Add horses to the race
        System.out.println("Test 2: Adding horses to the race...");
        Horse horse1 = new Horse('S', "Sora", 0.7);
        Horse horse2 = new Horse('M', "Midnight", 0.5);
        Horse horse3 = new Horse('G', "Gallop", 0.2);

        race.addHorse(horse1, 1);
        race.addHorse(horse2, 2);
        race.addHorse(horse3, 3);
        System.out.println("Horses added successfully to lanes 1, 2, and 3.\n");

        // Test 3: Attempt to add a horse to an invalid lane
        System.out.println("Test 3: Attempting to add a horse to an invalid lane...");
        Horse horse4 = new Horse('K', "Kenji", 0.7);
        race.addHorse(horse4, -1);
        race.addHorse(horse4, 4);
        System.out.println("Invalid lane test completed.\n");

        // Test 4: Overwrite an existing horse in a lane
        System.out.println("Test 4: Overwriting an existing horse in a lane...");
        Horse horse5 = new Horse('C', "Chauds", 0.9);
        // Simulate "y" input
        Race.scanner = new java.util.Scanner("y\n");
        race.addHorse(horse5, 1);
        System.out.println("Horse in lane 1 overwritten successfully.\n");

        // Test 5: Start the race
        System.out.println("Test 5: Starting the race...");
        race.startRace();
        System.out.println("Race completed.\n");

        // Test 6: Create a race with an invalid length
        System.out.println("Test 6: Attempting to create a race with an invalid length...");
        try {
            Race invalidRace = new Race(-10);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
        System.out.println("Invalid race length test completed.\n");

        // Test 7: Resetting horses and starting a new race
        System.out.println("Test 7: Resetting horses and starting a new race...");     
        // Simulate "y" input for all 3 lanes
        Race.scanner = new java.util.Scanner("y\ny\ny\n");
        race.addHorse(horse1, 1);
        race.addHorse(horse2, 2);
        race.addHorse(horse3, 3);
        race.startRace();
        System.out.println("New race completed.\n");

        // Print the confidence levels of the horses after the race
        System.out.println("Confidence levels of horses after the race:");
        System.out.println(horse1.getName() + " confidence: " + horse1.getConfidence());
        System.out.println(horse2.getName() + " confidence: " + horse2.getConfidence());
        System.out.println(horse3.getName() + " confidence: " + horse3.getConfidence());
        System.out.println("All tests completed.");

        // Test 8: Edge case for race length
        System.out.println("Test 8: Creating a race with minimum valid length...");
        try {
            Race shortRace = new Race(1); // Minimum valid length
            System.out.println("Race with length 1 created successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Unexpected exception: " + e.getMessage());
        }
        System.out.println("Edge case for race length test completed.\n");

        // Test 9: Verifying confidence updates
        System.out.println("Test 10: Verifying confidence updates...");
        Horse horse8 = new Horse('C', "Charlie", 0.8);
        Race confidenceRace = new Race(20);
        confidenceRace.addHorse(horse8, 1);
        confidenceRace.startRace();
        System.out.println(horse8.getName() + " confidence after race: " + horse8.getConfidence());
        System.out.println("Confidence update test completed.\n");
    }
}