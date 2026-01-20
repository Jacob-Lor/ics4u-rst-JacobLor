/**
 * @author Jacob Lor
 * Date: 2025-09-24
 * ICS4U
 * Bank.java
 * This bank class is responsible for handling the graphical interface of the monopoly game and the associated rules and logic.
 */


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javafx.application.Application;
import simpleIO.Console;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


public class Bank extends Application {
	
	private static ArrayList<Player> players = new ArrayList<Player>();
	private static Object[] tiles = new Object[40];
	private static Player banker = new Player("banker", 0); //The only non player
	public static int roll = 0;
    Image imgBoard = new Image(getClass().getResource("/images/monopolyboard.png").toString());

	
	public void start(Stage stage) {
        stage.setTitle("Monopoly Setup");

        Label lblPlayerSelector = new Label("Choose Number of Players");
        lblPlayerSelector.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Spinner: monopoly supports 2–8 players
        Spinner<Integer> spnPlayer = new Spinner<>();
        spnPlayer.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 8, 2)
        );

        Button btnStart = new Button("Start Game");
        btnStart.setOnAction(e -> {
            int numPlayers = spnPlayer.getValue();
            createPlayers(numPlayers);
            stage.close(); // Close setup screen
        });

        VBox root = new VBox(15, lblPlayerSelector, spnPlayer, btnStart);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        
        //event handlers:
        btnStart.setOnAction(e -> {
            int numPlayers = spnPlayer.getValue();
            createPlayers(numPlayers);
            stage.close();
            showGameBoard();
        });


        stage.setScene(new Scene(root, 300, 200));
        stage.show();
    }

    public static void main(String[] args) {
        createProperties();   // Load board first
        launch(args);         // Start JavaFX
    }
	
	private static void createProperties() {
        // File handling objects
        FileReader propertyFile;
        BufferedReader propertyReader;
       
        try {

            // Create a FileReader object, which handles the low-level details of
            // reading from a file
            propertyFile = new FileReader("data/properties.txt");
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
    }
	public static int rollDie() {
		int a = randomNumber(1, 6);
		int b = randomNumber(1, 6);
		return a + b;
	}
	public static int randomNumber(int a, int b) {
	    int highNum = Math.max(a, b);
	    int lowNum = Math.min(a, b);
	    int range = highNum - lowNum + 1;
	    return (int) (Math.random() * range) + lowNum;
	}
    public static void createPlayers(int numPlayers) {
        players.clear();

        for (int i = 1; i <= numPlayers; i++) {
            players.add(new Player("Player " + i, 1500));
        }

        System.out.println(numPlayers + " players created.");
    }

    public void showGameBoard() {
        Stage gameStage = new Stage();
        gameStage.setTitle("Monopoly");

        //left board
        ImageView imgboardView = new ImageView(imgBoard);
        imgboardView.setFitWidth(650);
        imgboardView.setPreserveRatio(true);

        VBox vbxBoard = new VBox(imgboardView);
        vbxBoard.setPadding(new Insets(10));

        // right side
        VBox vbxRightPanel = new VBox(20);
        vbxRightPanel.setPadding(new Insets(10));
        vbxRightPanel.setPrefWidth(320);

        //tile info
        Label lblCardTitle = new Label("Tile Info");
        lblCardTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label lblCardInfo = new Label("Land on a tile to see details here.");
        lblCardInfo.setWrapText(true);

        VBox vbxCard = new VBox(8, lblCardTitle, lblCardInfo);
        vbxCard.setPadding(new Insets(10));
        vbxCard.setStyle("-fx-border-color: black;");

        //player stats
        Label lblStatsTitle = new Label("Player Stats");
        lblStatsTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        VBox vbxStats = new VBox(6);
        vbxStats.setPadding(new Insets(10));
        vbxStats.setStyle("-fx-border-color: black;");

        updatePlayerStats(vbxStats);

        VBox vbxStatSection = new VBox(8, lblStatsTitle, vbxStats);

        vbxRightPanel.getChildren().addAll(vbxCard, vbxStatSection);

        BorderPane root = new BorderPane();
        root.setLeft(vbxBoard);
        root.setRight(vbxRightPanel);

        Scene scene = new Scene(root, 1000, 700);
        gameStage.setScene(scene);
        gameStage.show();
    }
    
    private void updatePlayerStats(VBox statsBox) {
    	//clears the stats box and iterates through all the players to update their information
    	statsBox.getChildren().clear();
        for (int playerIndex = 0; playerIndex < players.size(); playerIndex++) {
        	Player p = players.get(playerIndex);
        	Label lblPlayer = new Label(p.getName() + " | $" + Integer.toString(p.getCash()) + " | Pos:" + Integer.toString(p.getPosition())); //Places these new strings into the stats box
        	lblPlayer.setStyle("-fx-font-size: 13px;");
            statsBox.getChildren().add(lblPlayer);
        	
        }
        
    }



}


