import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import simpleIO.Console;

public class Bank {
	
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Building> buildings = new ArrayList<Building>(); 
	
	public static void main(String[] args) {
		
	}
	
	private void addPlayer() {
		
	}
	private void createProperties() {
        // File handling objects
        FileReader propertyFile;
        BufferedReader propertyReader;
       
        try {

            // Create a FileReader object, which handles the low-level details of
            // reading from a file
            propertyFile = new FileReader("data/random numbers.txt");
            // Now create a BufferedReader object to wrap around numFile - this lets use
            // one line from the file at a time
            propertyReader = new BufferedReader(propertyFile);
            String name = propertyReader.readLine();
            String colour = propertyReader.readLine();
            ArrayList<Integer> rents = new ArrayList<Integer>();
            rents.add(Integer.parseInt(propertyReader.readLine())); //Base Rent
            rents.add(Integer.parseInt(propertyReader.readLine())); //Monopolized Rent
            rents.add(Integer.parseInt(propertyReader.readLine())); //One House Rent
            rents.add(Integer.parseInt(propertyReader.readLine())); //Two House Rent
            rents.add(Integer.parseInt(propertyReader.readLine())); //Three House Rent
            rents.add(Integer.parseInt(propertyReader.readLine())); //Four House Rent
            rents.add(Integer.parseInt(propertyReader.readLine())); //Hotel Rent
            int houseCost = Integer.parseInt(propertyReader.readLine());
            int hotelCost = Integer.parseInt(propertyReader.readLine());
            int inherentValue = Integer.parseInt(propertyReader.readLine());
            int mortgageValue = Integer.parseInt(propertyReader.readLine());
            buildings.add(new Building(name, colour, rents, houseCost, hotelCost, inherentValue, mortgageValue));
            // Close the file
            propertyFile.close();
            
        } catch (IOException e) {
            Console.print("Problem reading from file: " + e.getMessage());
        } 

        // Call findAverage method; passes values from inMark array
        Console.print("The average of the random numbers is: " + findAverage(inMarks));
    }

	}

}
