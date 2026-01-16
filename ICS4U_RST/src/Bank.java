import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import simpleIO.Console;

public class Bank {
	
	private static ArrayList<Player> players = new ArrayList<Player>();
	private static ArrayList<Building> buildings = new ArrayList<Building>(); 
	
	public static void main(String[] args) {
		createProperties();
	}
	
	private void addPlayer() {
		
	}
	private static void createProperties() {
        // File handling objects
        FileReader propertyFile;
        BufferedReader propertyReader;
       
        try {

            // Create a FileReader object, which handles the low-level details of
            // reading from a file
            propertyFile = new FileReader("data/properties.txt");
            // Now create a BufferedReader object to wrap around numFile - this lets use
            // one line from the file at a time
            propertyReader = new BufferedReader(propertyFile);
            String name;
            String colour;
            while (true) {
            	name = propertyReader.readLine();
            	if (name == null) {
            		break;
            	}
                colour = propertyReader.readLine();
                Console.print(colour);
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
            }
            Console.print("done");
            // Close the file
            propertyFile.close();
            
        } catch (IOException e) {
            Console.print("Problem reading from file: " + e.getMessage());
        } 

        // Output array of buildings
        Console.print("The average of the random numbers is: ");
    }
}


