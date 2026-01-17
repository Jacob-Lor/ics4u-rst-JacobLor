import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import simpleIO.Console;

public class Bank {
	
	private static ArrayList<Player> players = new ArrayList<Player>();
	private static Object[] tiles = new Object[40];
	private static Player banker = new Player("banker", 0); //The only non player
	public static int roll = 0;
	
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
            String type;
            String name;
            String colour;
            for (int i = 0; i < 40; i++) {
            	type = propertyReader.readLine();
            	if ("building".equals(type)) {
            		name = propertyReader.readLine();
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
                    tiles[i] = (new Building(name, colour, banker, rents, houseCost, hotelCost, inherentValue, mortgageValue));
            	}
            	else if ("railroad".equals(type)) {
            		name = propertyReader.readLine();
            		tiles[i] = new Railroad(name);
            	}
            	else if ("utility".equals(type)) {
            		name = propertyReader.readLine();
            		tiles[i] = new Utility(name);
            	}
            }
            Console.print("done");
            // Close the file
            propertyFile.close();
            
        } catch (IOException e) {
            Console.print("Problem reading from file: " + e.getMessage());
        } 

        // Output array of buildings
    }
}


