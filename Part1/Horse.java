/**
 * Write a description of class Horse here.
 * 
 * @author Mehraz Saif
 * @version 1
 */
public class Horse
{
    //Fields of class Horse
    private String name;
    private char symbol;
    private double confidence;
    private int distanceTravelled;
    private boolean fallen;
    
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
       this.symbol = horseSymbol;
       this.name = horseName;
       this.setConfidence(horseConfidence);
       this.distanceTravelled = 0;
       this.fallen = false;
    }
    
    //Other methods of class Horse
    public void fall()
    {
        this.fallen = true;
    }
    
    public double getConfidence()
    {
        return this.confidence;
    }
    
    public int getDistanceTravelled()
    {
        return this.distanceTravelled;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public char getSymbol()
    {
        return this.symbol;
    }
    
    public void goBackToStart()
    {
        this.distanceTravelled = 0;
        this.fallen = false;
    }
    
    public boolean hasFallen()
    {
        return this.fallen;
    }

    public void moveForward()
    {
        this.distanceTravelled += 1;
    }

    public void setConfidence(double newConfidence)
    {
        if(newConfidence <= 0){
            this.confidence = 0.1;
        }else if(newConfidence > 1){
            this.confidence = 1;
        }else{
            this.confidence = newConfidence;
        }
    }
    
    public void setSymbol(char newSymbol)
    {
        this.symbol = newSymbol;
    }
}
