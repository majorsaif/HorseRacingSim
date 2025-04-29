import java.util.Scanner;

public class startRace {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Race race = null;

        while(true){
            System.out.println("Welcome to the Horse Race!");
            System.out.println("1. Set race length");
            System.out.println("2. Add/Edit horses");
            System.out.println("3. Start the race");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if(choice == 1){
                System.out.print("Enter the race length: ");
                int raceLength = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                race = new Race(raceLength);
                System.out.println("Race length set to " + raceLength + ".\n");
            }else if(choice == 2){
                if(race == null){
                    System.out.println("Please set the race length first.\n");
                }else{
                    System.out.print("Enter lane number (1-3): ");
                    int lane = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter horse name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter horse symbol: ");
                    char symbol = scanner.nextLine().charAt(0);
                    System.out.print("Enter horse confidence (0.1 to 1.0): ");
                    double confidence = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    Horse horse = new Horse(symbol, name, confidence);
                    race.addHorse(horse, lane);
                    System.out.println("Horse added/edited successfully.\n");
                }
            }else if(choice == 3){
                if(race == null){
                    System.out.println("Please set the race length and add horses first.\n");
                }else{
                    race.startRace();
                    System.out.print("Do you want to rerun the race? (y/n): ");
                    String rerun = scanner.nextLine();
                    if(!rerun.equalsIgnoreCase("y")){
                        System.out.print("Do you want to set up a new race? (y/n): ");
                        String newRace = scanner.nextLine();
                        if(!newRace.equalsIgnoreCase("y")){
                            System.out.println("Exiting the program...");
                            scanner.close();
                            return;
                        }else{
                            race = null; // Reset the race for a new setup
                        }
                    }
                }
            }else if(choice == 4){
                System.out.println("Exiting the program...");
                scanner.close();
                return;
            }else{
                System.out.println("Invalid choice. Please try again.\n");
            }
        }
    }
}
