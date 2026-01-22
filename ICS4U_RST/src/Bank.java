/**
 * @author Jacob Lor
 * Date: 2025-09-24
 * ICS4U
 * Bank.java
 * This bank class is responsible for handling the graphical interface of the monopoly game and the associated rules and logic.
 */
// Imports
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Optional;
import simpleIO.Console;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This class manages all game logic, player interactions, property transactions,
 */
public class Bank extends Application {
    
    // Static game data storage
    private static ArrayList<Player> players = new ArrayList<Player>(); // Collection of all players in the game
    private static Object[] tiles = new Object[40]; // Array representing all 40 board positions
    private static Player banker = new Player("banker", 0); // Special banker player for property management
    public static int roll = 0; // Current dice roll value
    
    // Game board image resource
    Image imgBoard = new Image(getClass().getResource("/images/monopolyboard.png").toString());

    // Array storing the visual coordinates for each board position
    public static Point2D[] tilePositions = new Point2D[40];
    
    // Current player turn tracking
    private int currentPlayerIndex = 0;
    
    // UI component references for updates
    private Label lblTurnInfo;          // Displays whose turn it is
    private Label lblRollResult;        // Shows dice roll results
    private VBox vbxStatsBoxRef;        // Container for player statistics
    private VBox vbxPropertyInfoRef;    // Container for property information display
    private Button btnRoll;             // Button to roll dice
    private Button btnEndTurn;          // Button to end current turn
    private Button btnMyProperties;     // Button to open property management window

    /**
     * This method initializes the JavaFX application and sets up the initial game setup screen.
     * @param stage The primary stage for this application, onto which the application scene can be set
     * @return This method does not return anything
     */
    public void start(Stage stage) {
        // Initialize all 40 board tile positions
        
        //Bottom row positions 
        tilePositions[0] = new Point2D(585, 585); // GO corner position
        tilePositions[1] = new Point2D(525, 585); // Mediterranean Avenue
        tilePositions[2] = new Point2D(475, 585); // Community Chest
        tilePositions[3] = new Point2D(420, 585); // Baltic Avenue
        tilePositions[4] = new Point2D(365, 585); // Income Tax
        tilePositions[5] = new Point2D(310, 585); // Reading Railroad
        tilePositions[6] = new Point2D(255, 585); // Oriental Avenue
        tilePositions[7] = new Point2D(200, 585); // Chance
        tilePositions[8] = new Point2D(145, 585); // Vermont Avenue
        tilePositions[9] = new Point2D(90, 585);  // Connecticut Avenue
        // Jail corner position - tile 10
        tilePositions[10] = new Point2D(35, 585); 
        // Left side positions 
        tilePositions[11] = new Point2D(35, 525); // St. Charles Place
        tilePositions[12] = new Point2D(35, 475); // Electric Company
        tilePositions[13] = new Point2D(35, 420); // States Avenue
        tilePositions[14] = new Point2D(35, 365); // Virginia Avenue
        tilePositions[15] = new Point2D(35, 310); // Pennsylvania Railroad
        tilePositions[16] = new Point2D(35, 255); // St. James Place
        tilePositions[17] = new Point2D(35, 200); // Community Chest
        tilePositions[18] = new Point2D(35, 145); // Tennessee Avenue
        tilePositions[19] = new Point2D(35, 90);  // New York Avenue
        // Free Parking corner position - tile 20
        tilePositions[20] = new Point2D(35, 35); // Free Parking corner
        // Top row positions 
        tilePositions[21] = new Point2D(90, 35);  // Kentucky Avenue
        tilePositions[22] = new Point2D(145, 35); // Chance
        tilePositions[23] = new Point2D(200, 35); // Indiana Avenue
        tilePositions[24] = new Point2D(255, 35); // Illinois Avenue
        tilePositions[25] = new Point2D(310, 35); // B&O Railroad
        tilePositions[26] = new Point2D(365, 35); // Atlantic Avenue
        tilePositions[27] = new Point2D(420, 35); // Ventnor Avenue
        tilePositions[28] = new Point2D(475, 35); // Water Works
        tilePositions[29] = new Point2D(525, 35); // Marvin Gardens
        // Go to Jail 
        tilePositions[30] = new Point2D(585, 35);
        // Right side positions 
        tilePositions[31] = new Point2D(585, 90);  // Pacific Avenue
        tilePositions[32] = new Point2D(585, 145); // North Carolina Avenue
        tilePositions[33] = new Point2D(585, 200); // Community Chest
        tilePositions[34] = new Point2D(585, 255); // Pennsylvania Avenue
        tilePositions[35] = new Point2D(585, 310); // Short Line Railroad
        tilePositions[36] = new Point2D(585, 365); // Chance
        tilePositions[37] = new Point2D(585, 420); // Park Place
        tilePositions[38] = new Point2D(585, 475); // Luxury Tax
        tilePositions[39] = new Point2D(585, 525); // Boardwalk

        // Set up the initial game setup window
        stage.setTitle("Monopoly Setup");

        // Create player selection interface components
        Label lblPlayerSelector = new Label("Choose Number of Players");

        // Spinner for selecting number of players (2-4 players supported)
        Spinner<Integer> spnPlayer = new Spinner<>();
        spnPlayer.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 4, 2)
        );

        // Button to start the game with selected number of players
        Button btnStart = new Button("Start Game");

        // Layout container for setup screen
        VBox root = new VBox(15, lblPlayerSelector, spnPlayer, btnStart);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        
        // Event handler for start game button
        btnStart.setOnAction(e -> {
            int numPlayers = spnPlayer.getValue(); // Get selected number of players
            createPlayers(numPlayers); // Initialize player objects
            stage.close(); // Close setup window
            showGameBoard(); // Open main game board
        });

        // Display the setup scene
        stage.setScene(new Scene(root, 300, 200));
        stage.show();
    }

    /**
     * The main method serves as the entry point for the JavaFX application. It initializes the property data from file and launches the JavaFX application.
     * @param args Command line arguments passed to the application
     * @return This method does not return anything
     */
    public static void main(String[] args) {
        createProperties(); // Load property data from external file
        launch(args); // Launch the JavaFX application
    }

    /**
     * This method simulates rolling two six-sided dice and returns the sum.
     * @param prompt This method does not expect a prompt
     * @return The sum of two dice rolls (integer between 2 and 12)
     */
    public static int rollDie() {
        int a = randomNumber(1, 6); // First die roll
        int b = randomNumber(1, 6); // Second die roll
        roll = a + b; 
        return roll; // Return the sum of both dice
    }

    /**
     * This method generates a random number within a specified range (inclusive).
     * @param a First boundary of the range
     * @param b Second boundary of the range
     * @return A random integer within the specified range
     */
    public static int randomNumber(int a, int b) {
        int highNum = Math.max(a, b); // Determine the higher boundary
        int lowNum = Math.min(a, b);  // Determine the lower boundary
        int range = highNum - lowNum + 1; 
        return (int) (Math.random() * range) + lowNum; 
    }

    /**
     * This method creates the specified number of player objects with default starting values.
     * @param numPlayers The number of players to create (between 2 and 4)
     * @return This method does not return anything
     */
    public static void createPlayers(int numPlayers) {
        players.clear(); // Remove any existing players from previous games
        
        // Array of distinct colors for player tokens
        Color[] colors = {
                Color.RED,    // Player 1 color
                Color.BLUE,   // Player 2 color
                Color.GREEN,  // Player 3 color
                Color.ORANGE  // Player 4 color
        };

        // Create the specified number of players with starting cash and unique colors
        for (int i = 0; i < numPlayers; i++) {
            players.add(new Player("Player " + (i + 1), 1500, colors[i]));
        }
    }

    /**
     * This method creates and displays the main game board interface with all UI components.
     * @param prompt This method does not expect a prompt
     * @return This method does not return anything
     */
    public void showGameBoard() {
        // Create the main game window
        Stage gameStage = new Stage();
        gameStage.setTitle("Monopoly");

        // Set up the monopoly board image with proper scaling
        ImageView imgBoardView = new ImageView(imgBoard);
        imgBoardView.setFitWidth(650); // Set board width to 650 pixels
        imgBoardView.setPreserveRatio(true); // Maintain aspect ratio
        
        // Container for the board and player tokens
        StackPane stpBoard = new StackPane();
        stpBoard.getChildren().add(imgBoardView);
        
        // Offset positions for multiple player tokens on the same tile
        // This prevents tokens from overlapping when on the same space
        double[][] offsets = {
                { -10, -10 }, 
                {  10, -10 }, 
                { -10,  10 }, 
                {  10,  10 }  
        };
        
        // Place all player tokens at the starting position (GO)
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            Point2D pos = tilePositions[0]; // GO position coordinates

            // Calculate token position with offset to prevent overlap
            p.getToken().setTranslateX(pos.getX() - 325 + offsets[i][0]);
            p.getToken().setTranslateY(pos.getY() - 325 + offsets[i][1]);

            // Add token to the board display
            stpBoard.getChildren().add(p.getToken());
        }

        // Container for the board section
        VBox vbxBoard = new VBox(stpBoard);
        vbxBoard.setPadding(new Insets(10));

        // Right panel setup for game controls and information
        VBox vbxRightPanel = new VBox(15);
        vbxRightPanel.setPadding(new Insets(10));
        vbxRightPanel.setPrefWidth(350); // Fixed width for consistent layout

        // Dice and turn control section
        lblTurnInfo = new Label("Turn: " + players.get(0).getName()); // Display current player
        lblTurnInfo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        lblRollResult = new Label("Roll the dice!"); // Initial roll prompt
        
        // Roll dice button with full width
        btnRoll = new Button("Roll Dice");
        btnRoll.setMaxWidth(Double.MAX_VALUE);
        btnRoll.setOnAction(e -> handleRoll()); // Connect to roll handling method
        
        // End turn button (initially disabled until dice are rolled)
        btnEndTurn = new Button("End Turn");
        btnEndTurn.setMaxWidth(Double.MAX_VALUE);
        btnEndTurn.setDisable(true); // Disabled until player rolls dice
        btnEndTurn.setOnAction(e -> endTurn()); // Connect to turn ending method

        // Container for dice controls with styling
        VBox vbxDice = new VBox(10, lblTurnInfo, lblRollResult, btnRoll, btnEndTurn);
        vbxDice.setPadding(new Insets(10));
        vbxDice.setStyle("-fx-border-color: black; -fx-background-color: #f4f4f4;");

        // Player statistics display section
        vbxStatsBoxRef = new VBox(6);
        vbxStatsBoxRef.setPadding(new Insets(10));
        vbxStatsBoxRef.setStyle("-fx-border-color: black;");
        updatePlayerStats(); // Initialize with current player stats

        // Property management button
        btnMyProperties = new Button("My Properties");
        btnMyProperties.setMaxWidth(Double.MAX_VALUE);
        btnMyProperties.setStyle("-fx-font-size: 14; -fx-padding: 10;");
        btnMyProperties.setOnAction(e -> showPlayerProperties(players.get(currentPlayerIndex)));

        // Property information display panel
        vbxPropertyInfoRef = new VBox(10);
        vbxPropertyInfoRef.setPadding(new Insets(10));
        vbxPropertyInfoRef.setStyle("-fx-border-color: black;");
        Label lblPropertyHeader = new Label("Current Tile Info:");
        lblPropertyHeader.setStyle("-fx-font-weight: bold;");
        vbxPropertyInfoRef.getChildren().add(lblPropertyHeader);

        // Scrollable container for property information
        ScrollPane scrollPane = new ScrollPane(vbxPropertyInfoRef);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(250); // Fixed height with scrolling

        // Assemble the right panel components
        vbxRightPanel.getChildren().addAll(vbxDice, vbxStatsBoxRef, btnMyProperties, scrollPane);

        // Main layout using BorderPane
        BorderPane root = new BorderPane();
        root.setLeft(vbxBoard);   // Board on the left
        root.setRight(vbxRightPanel); // Controls on the right

        // Create and display the game scene
        Scene scene = new Scene(root, 1050, 750);
        gameStage.setScene(scene);
        gameStage.show();
    }

    /**
     * This method handles the dice rolling action and all subsequent game logic.
     * @param prompt This method does not expect a prompt
     * @return This method does not return anything
     */
    private void handleRoll() {
        Player currentPlayer = players.get(currentPlayerIndex); // Get the active player

        int rollValue = rollDie(); // Roll two dice and get the sum
        lblRollResult.setText(currentPlayer.getName() + " rolled: " + rollValue); // Display roll result

        movePlayer(currentPlayer, currentPlayerIndex, rollValue); // Move player on board
        
        // Handle any special actions based on the tile landed on
        handlePropertyLanding(currentPlayer);
        
        updatePlayerStats(); // Refresh player information display
        
        // Prevent multiple rolls per turn by disabling roll button
        btnRoll.setDisable(true);
        btnEndTurn.setDisable(false); // Enable end turn button
    }
    
    /**
     * This method handles the end of a player's turn and advances to the next player.
     * @param prompt This method does not expect a prompt
     * @return This method does not return anything
     */
    private void endTurn() {
    	printLeaderboard(); // Display current standings to console
        
        // Advance to the next player (wraps around to first player after last)
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        
        // Update UI elements for the new active player
        lblTurnInfo.setText("Turn: " + players.get(currentPlayerIndex).getName());
        lblRollResult.setText("Roll the dice!"); // Reset roll prompt
        
        // Clear property information panel for new turn
        vbxPropertyInfoRef.getChildren().clear();
        vbxPropertyInfoRef.getChildren().add(new Label("Current Tile Info: Wait for roll"));
        
        updatePlayerStats(); // Update displayed player statistics
        
        // Reset button states for new turn
        btnRoll.setDisable(false);  // Enable dice rolling
        btnEndTurn.setDisable(true); // Disable end turn until dice are rolled
    }

    /**
     * This method handles all interactions when a player lands on a tile.
     * @param player The player who landed on the tile
     * @return This method does not return anything
     */
    private void handlePropertyLanding(Player player) {
        int position = player.getPosition(); // Get player's current board position
        Object tile = tiles[position]; // Get the tile object at that position
        String tileName = getTileName(position); // Get the display name of the tile
        
        // Clear and update the property information display
        vbxPropertyInfoRef.getChildren().clear();
        Label lblPropertyHeader = new Label("Current Tile: " + tileName);
        lblPropertyHeader.setStyle("-fx-font-weight: bold;");
        vbxPropertyInfoRef.getChildren().add(lblPropertyHeader);

        // Check if the tile is a purchasable property
        if (tile instanceof Property) {
            Property property = (Property) tile;
            displayPropertyInfo(property, player); // Show property details and purchase options
        } else {
            // Handle special tiles (non-property spaces)
            if (tileName.equals("Chance") || tileName.equals("Community Chest")) {
                // Apply penalty for landing on Chance or Community Chest
                int penalty = 50;
                player.setCash(player.getCash() - penalty);
                vbxPropertyInfoRef.getChildren().add(new Label("Result: You lost $" + penalty));
                showAlert(Alert.AlertType.INFORMATION, "Bad Luck", 
                        "You landed on " + tileName + " and lost $" + penalty + "!");
                
            } else if (tileName.equals("Luxury Tax") || tileName.equals("Income Tax")) {
                // Apply tax penalty for landing on tax spaces
                int penalty = 100;
                player.setCash(player.getCash() - penalty);
                vbxPropertyInfoRef.getChildren().add(new Label("Result: You paid $" + penalty + " in tax."));
                showAlert(Alert.AlertType.INFORMATION, "Tax Collector", 
                        "You landed on " + tileName + " and paid $" + penalty + ".");
                
            } else if (tileName.equals("Go To Jail")) {
                // Send player directly to jail
                vbxPropertyInfoRef.getChildren().add(new Label("Result: Go Directly To Jail!"));
                sendToJail(player);
            } else {
                // Safe spaces like Free Parking, GO, or Just Visiting
                vbxPropertyInfoRef.getChildren().add(new Label("Action: Resting..."));
            }
        }
    }
    
    /**
     * This method sends a player directly to the Jail position on the board.
     * @param player The player being sent to jail
     * @return This method does not return anything
     */
    private void sendToJail(Player player) {
        // Display jail notification to player
        showAlert(Alert.AlertType.WARNING, "GO TO JAIL", "Do not pass Go. Do not collect $200.");
        
        // Set player position to Jail (tile index 10)
        player.setPosition(10);
        
        // Update the visual position of the player's token immediately
        Point2D pos = tilePositions[10]; // Get jail coordinates
        double[][] offsets = { { -10, -10 }, {  10, -10 }, { -10,  10 }, {  10,  10 } };
        
        // Use currentPlayerIndex to maintain consistent token positioning
        player.getToken().setTranslateX(pos.getX() - 325 + offsets[currentPlayerIndex][0]);
        player.getToken().setTranslateY(pos.getY() - 325 + offsets[currentPlayerIndex][1]);
    }

    /**
     * This method displays detailed information about a property and handles purchase interactions.
     * @param property The property object to display information for
     * @param player The player who landed on the property
     * @return This method does not return anything
     */
    private void displayPropertyInfo(Property property, Player player) {
        VBox infoBox = new VBox(5); // Container for property information
        
        // Display basic property information
        infoBox.getChildren().add(new Label("Property: " + property.getName()));
        infoBox.getChildren().add(new Label("Price: $" + property.getInherentValue()));
        
        // Display detailed rent schedule for building properties
        if (property instanceof Building) {
            Building b = (Building) property;
            Separator sep = new Separator(); // Visual separator
            Label lblRentHeader = new Label("--- Rent Schedule ---");
            lblRentHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 10;");
            
            // Container for rent information with styling
            VBox rents = new VBox(2);
            rents.setStyle("-fx-padding: 5; -fx-background-color: #eee;");
            
            // Display all rent levels from site rent to hotel
            rents.getChildren().add(new Label("Site Rent: $" + b.rents.get(0)));
            rents.getChildren().add(new Label("1 House: $" + b.rents.get(1)));
            rents.getChildren().add(new Label("2 Houses: $" + b.rents.get(2)));
            rents.getChildren().add(new Label("3 Houses: $" + b.rents.get(3)));
            rents.getChildren().add(new Label("4 Houses: $" + b.rents.get(4)));
            rents.getChildren().add(new Label("Hotel: $" + b.rents.get(5)));
            
            rents.getChildren().add(new Label("House Cost: $" + b.housePrice));
            
            // Add rent schedule to the information display
            infoBox.getChildren().addAll(sep, lblRentHeader, rents, new Separator());
        }
        
        // Property ownership status label
        Label lblStatus = new Label();
        lblStatus.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        
        // Check if property is already owned
        if (property.purchased) {
            lblStatus.setText("Status: OWNED by " + property.getOwner().getName());
            infoBox.getChildren().add(lblStatus);
            
            // Display mortgage status if applicable
            if (property.mortgaged) {
                Label lblMortgaged = new Label("*** MORTGAGED ***");
                lblMortgaged.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                infoBox.getChildren().add(lblMortgaged);
            }
            
            // Check if current player owns this property
            if (property.getOwner() == player) {
                infoBox.getChildren().add(new Label("(You own this)"));
            } else {
                // Another player owns it - calculate and charge rent
                int rentAmount = property.getFees();
                Label lblRent = new Label("Rent Due: $" + rentAmount);
                lblRent.setStyle("-fx-text-fill: red; -fx-font-size: 16; -fx-font-weight: bold;");
                infoBox.getChildren().add(lblRent);
                
                // Charge rent if property is not mortgaged and rent is due
                if (rentAmount > 0 && !property.mortgaged) {
                    chargeRent(player, property.getOwner(), rentAmount);
                }
            }
        } else {
            // Property is available for purchase
            lblStatus.setText("Status: AVAILABLE");
            lblStatus.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            infoBox.getChildren().add(lblStatus);
            
            // Create purchase button with property price
            Button btnBuy = new Button("Buy Property ($" + property.getInherentValue() + ")");
            btnBuy.setStyle("-fx-font-size: 14; -fx-padding: 10;");
            btnBuy.setOnAction(e -> buyProperty(property, player)); // Connect to purchase handler
            
            // Check if player has sufficient funds
            if (player.getCash() < property.getInherentValue()) {
                btnBuy.setDisable(true); // Disable button if insufficient funds
                Label lblCantBuy = new Label("Insufficient funds!");
                lblCantBuy.setStyle("-fx-text-fill: red;");
                infoBox.getChildren().add(lblCantBuy);
            }
            
            infoBox.getChildren().add(btnBuy);
        }
        
        // Add the complete information box to the property display panel
        vbxPropertyInfoRef.getChildren().add(infoBox);
    }

    /**
     * This method handles the purchase of a property by a player.
     * @param property The property being purchased
     * @param player The player attempting to purchase the property
     * @return This method does not return anything
     */
    private void buyProperty(Property property, Player player) {
        // Verify player has sufficient funds for purchase
        if (player.getCash() >= property.getInherentValue()) {
            // Deduct purchase price from player's cash
            player.setCash(player.getCash() - property.getInherentValue());
            
            // Transfer ownership to the player
            property.purchased = true;
            property.owner = player;
            player.addProperty(property); // Add to player's property collection
            
            // Check if this purchase creates a monopoly
            checkMonopoly(property, player);
            
            // Notify player of successful purchase
            showAlert(Alert.AlertType.INFORMATION, "Purchase Successful", 
                     player.getName() + " bought " + property.getName() + "!");
            
            // Update displays to reflect the purchase
            updatePlayerStats();
            handlePropertyLanding(player); // Refresh property display
        } else {
            // Insufficient funds - show error message
            showAlert(Alert.AlertType.ERROR, "Insufficient Funds", 
                     "You need $" + property.getInherentValue() + " but only have $" + player.getCash());
        }
    }

    /**
     * This method handles rent payment between players.
     * @param payer The player who must pay rent
     * @param owner The property owner receiving rent payment
     * @param amount The amount of rent to be paid
     * @return This method does not return anything
     */
    private void chargeRent(Player payer, Player owner, int amount) {
        // Check if payer has sufficient cash for rent
        if (payer.getCash() >= amount) {
            // Transfer rent money between players
            payer.setCash(payer.getCash() - amount);
            owner.setCash(owner.getCash() + amount);
            
            // Notify players of the rent transaction
            showAlert(Alert.AlertType.INFORMATION, "Rent Paid", 
                     payer.getName() + " paid $" + amount + " to " + owner.getName());
        } else {
            // Handle case where player cannot afford rent
            handleInsufficientFunds(payer, owner, amount);
        }
        updatePlayerStats(); // Refresh player cash displays
    }

    /**
     * This method handles situations where a player cannot pay required fees.
     * @param payer The player who cannot pay the required amount
     * @param creditor The player or entity owed money
     * @param amountOwed The total amount that must be paid
     * @return This method does not return anything
     */
    private void handleInsufficientFunds(Player payer, Player creditor, int amountOwed) {
        // Display insufficient funds warning with current financial status
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Insufficient Funds");
        alert.setHeaderText(payer.getName() + " cannot pay $" + amountOwed);
        alert.setContentText("Cash: $" + payer.getCash() + "\nYou must mortgage or sell properties using 'My Properties'.");
        alert.showAndWait();
        
        // Open property management window to allow fundraising
        showPlayerProperties(payer);
        
        // Check if player raised sufficient funds after property management
        if (payer.getCash() >= amountOwed) {
            // Complete the payment transaction
            payer.setCash(payer.getCash() - amountOwed);
            creditor.setCash(creditor.getCash() + amountOwed);
            showAlert(Alert.AlertType.INFORMATION, "Debt Settled", "Payment complete.");
        } else {
            // Player still cannot pay - offer bankruptcy option
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Bankruptcy");
            confirm.setContentText("Still can't pay. Declare bankruptcy?");
            Optional<ButtonType> res = confirm.showAndWait();
            if(res.isPresent() && res.get() == ButtonType.OK) {
                declareBankruptcy(payer, creditor); // Handle bankruptcy proceedings
            }
        }
    }

    /**
     * This method handles player bankruptcy by transferring all assets to the creditor.
     * @param bankrupt The player declaring bankruptcy
     * @param creditor The player receiving the bankrupt player's assets
     * @return This method does not return anything
     */
    private void declareBankruptcy(Player bankrupt, Player creditor) {
        bankrupt.setIsAlive(false); // Mark player as eliminated
        
        // Transfer all properties to the creditor
        for (Property p : new ArrayList<>(bankrupt.getProperties())) {
            bankrupt.removeProperty(p); // Remove from bankrupt player
            p.owner = creditor;         // Transfer ownership
            creditor.addProperty(p);    // Add to creditor's portfolio
        }
        
        // Transfer remaining cash to creditor
        creditor.setCash(creditor.getCash() + bankrupt.getCash());
        bankrupt.setCash(0); // Zero out bankrupt player's cash
        
        // Notify of bankruptcy
        showAlert(Alert.AlertType.WARNING, "Bankruptcy", bankrupt.getName() + " is bankrupt!");
        
        // Remove bankrupt player from active game
        players.remove(bankrupt);
        
        // Check if only one player remains (game over condition)
        if (players.size() == 1) {
            showAlert(Alert.AlertType.INFORMATION, "Game Over", players.get(0).getName() + " wins!");
        }
    }

    /**
     * This method checks if a property purchase creates a monopoly and updates property status accordingly.
     * @param property The property that was just purchased
     * @param player The player who purchased the property
     * @return This method does not return anything
     */
    private void checkMonopoly(Property property, Player player) {
        // Handle building properties (color groups)
        if (property instanceof Building) {
            Building building = (Building) property;
            String color = building.colour; // Get the color group of the building
            
            // Find all properties of the same color owned by this player
            ArrayList<Building> sameColor = new ArrayList<>();
            for (Property p : player.getProperties()) {
                if (p instanceof Building && ((Building)p).colour.equals(color)) {
                    sameColor.add((Building)p);
                }
            }
            
            // Check if player owns all properties in this color group
            int requiredCount = getRequiredCountForMonopoly(color);
            if (sameColor.size() == requiredCount) {
                // Player has monopoly - enable house building on all properties in group
                for (Building b : sameColor) {
                    b.setMonopolized(true);
                }
            }
        } 
        // Handle railroad properties
        else if (property instanceof Railroad) {
            int railroadCount = 0;
            // Count total railroads owned by player
            for (Property p : player.getProperties()) {
                if (p instanceof Railroad) {
                    railroadCount++;
                    // Update monopoly level based on number of railroads owned
                    ((Railroad)p).updateMonopolizedLevel(railroadCount - 1);
                }
            }
        } 
        // Handle utility properties
        else if (property instanceof Utility) {
            int utilityCount = 0;
            // Count utilities owned by player
            for (Property p : player.getProperties()) {
                if (p instanceof Utility) {
                    utilityCount++;
                }
            }
            // If player owns both utilities, set monopoly status
            if (utilityCount == 2) {
                for (Property p : player.getProperties()) {
                    if (p instanceof Utility) {
                        p.setMonopolized(true);
                    }
                }
            }
        }
    }

    /**
     * This method returns the number of properties required for a monopoly in a given color group.
     * @param color The color group to check
     * @return The number of properties required for monopoly in this color group
     */
    private int getRequiredCountForMonopoly(String color) {
        // Brown and Dark Blue color groups only have 2 properties each
        if (color.equalsIgnoreCase("brown") || color.equalsIgnoreCase("darkblue")) return 2;
        return 3; // All other color groups have 3 properties
    }

    /**
     * This method returns the display name for a tile at a given board position.
     * @param position The board position (0-39) to get the name for
     * @return The display name of the tile at the specified position
     */
    private String getTileName(int position) {
        // If the tile is a property, return its name
        if (tiles[position] instanceof Property) {
            return ((Property)tiles[position]).getName();
        }
        
        // Array of special tile names corresponding to board positions
        String[] specialTiles = {
            "GO", null, "Community Chest", null, "Income Tax", null, null, "Chance", null, null,
            "Jail/Just Visiting", null, null, null, null, null, null, "Community Chest", null, null,
            "Free Parking", null, "Chance", null, null, null, null, null, null, null,
            "Go To Jail", null, null, "Community Chest", null, null, "Chance", null, "Luxury Tax", null
        };
        
        // Return special tile name or generic tile name if not defined
        return specialTiles[position] != null ? specialTiles[position] : "Tile " + position;
    }

    /**
     * This method moves a player on the board and handles passing GO.
     * @param player The player to move
     * @param playerIndex The index of the player for visual positioning
     * @param steps the number of spaces to move forward
     * @return This method does not return anything
     */
    public void movePlayer(Player player, int playerIndex, int steps) {
        int oldPos = player.getPosition(); 
        int newPos = (oldPos + steps) % 40; // Calculate new position (wraps around board)
        
        // Check if player passed GO (new position is less than old position after wrapping)
        if (newPos < oldPos) {
            // Player passed GO - collect $200
            player.setCash(player.getCash() + 200);
            showAlert(Alert.AlertType.INFORMATION, "Passed GO!", "You passed GO and collected $200.");
        }
        
        // Update player's position data
        player.setPosition(newPos);
        Point2D pos = tilePositions[newPos]; 

        // Offset positions to prevent token overlap when multiple players on same tile
        double[][] offsets = { { -10, -10 }, {  10, -10 }, { -10,  10 }, {  10,  10 } };
        
        // Update visual position of player token
        player.getToken().setTranslateX(pos.getX() - 325 + offsets[playerIndex][0]);
        player.getToken().setTranslateY(pos.getY() - 325 + offsets[playerIndex][1]);
    }

    /**
     * This method updates the player statistics display panel with current player information.
     * @param prompt This method does not expect a prompt
     * @return This method does not return anything
     */
    private void updatePlayerStats() {
        vbxStatsBoxRef.getChildren().clear(); // Clear existing statistics display
        
        // Add header for the statistics section
        Label lblHeader = new Label("PLAYER STATUS");
        lblHeader.setStyle("-fx-font-weight: bold; -fx-underline: true;");
        vbxStatsBoxRef.getChildren().add(lblHeader);
        
        // Get the currently active player
        Player activeP = players.get(currentPlayerIndex);
        
        // Create labels for player information
        Label lblName = new Label("Name: " + activeP.getName());
        Label lblCash = new Label("Cash: $" + activeP.getCash());
        Label lblProps = new Label("Properties Owned: " + activeP.getProperties().size());
        
        // Apply styling to the labels
        lblName.setStyle("-fx-font-size: 14px;");
        lblCash.setStyle("-fx-font-size: 15px; -fx-text-fill: green; -fx-font-weight: bold;");
        
        // Add all player information to the display
        vbxStatsBoxRef.getChildren().addAll(lblName, lblCash, lblProps);
    }

    /**
     * This method displays alert dialogs to communicate game events to players.
     * @param type The type of alert 
     * @param title The title text for the alert dialog
     * @param content The main message content to display
     * @return This method does not return anything
     */
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type); 
        alert.setTitle(title);         
        alert.setHeaderText(null);     
        alert.setContentText(content); 
        alert.showAndWait();           
    }

    /**
     * This method loads property data from an external text file and populates the game board.
     * @param prompt This method does not expect a prompt
     * @return This method does not return anything
     */
    private static void createProperties() {
        // Initialize all tile positions to null
        for (int i = 0; i < 40; i++) tiles[i] = null;
        
        try (BufferedReader br = new BufferedReader(new FileReader("data/properties.txt"))) {
            String line;
            int tileIndex = 0; // Track current board position
            
            // Read file line by line until end of file or all 40 tiles processed
            while ((line = br.readLine()) != null && tileIndex < 40) {
                line = line.trim(); // Remove whitespace
                
                if (line.equals("blank")) {
                    // Skip blank tiles (special spaces like GO, Jail, etc.)
                    tileIndex++;
                } else if (line.equals("building")) {
                    // Read building property data
                    String name = br.readLine().trim().replace("__", ""); // Property name
                    String color = br.readLine().trim();                  // Color group
                    
                    // Read rent schedule (6 values: site, 1-4 houses, hotel)
                    ArrayList<Integer> rents = new ArrayList<>();
                    for (int i = 0; i < 6; i++) rents.add(Integer.parseInt(br.readLine().trim()));
                    
                    // Read building costs and values
                    int housePrice = Integer.parseInt(br.readLine().trim());     // Cost per house
                    int hotelPrice = Integer.parseInt(br.readLine().trim());     // Cost for hotel
                    int inherentValue = Integer.parseInt(br.readLine().trim());  // Purchase price
                    int mortgagedValue = Integer.parseInt(br.readLine().trim()); // Mortgage value
                    
                    // Create building object and place on board
                    tiles[tileIndex] = new Building(name, color, banker, rents, housePrice, hotelPrice, inherentValue, mortgagedValue);
                    tileIndex++;
                } else if (line.equals("railroad")) {
                    // Read railroad property data
                    String name = br.readLine().trim(); // Railroad name
                    tiles[tileIndex] = new Railroad(name); // Create railroad object
                    tileIndex++;
                } else if (line.equals("utility")) {
                    // Read utility property data
                    String name = br.readLine().trim(); // Utility name
                    tiles[tileIndex] = new Utility(name); // Create utility object
                    tileIndex++;
                }
            }
        } catch (Exception e) { 
            e.printStackTrace(); // Print any file reading errors
        }
    }

    /**
     * This method creates and displays a property management window for a specific player.
     * @param player The player whose properties are being managed
     * @return This method does not return anything
     */
    private void showPlayerProperties(Player player) {
        // Create new window for property management
        Stage propStage = new Stage();
        propStage.setTitle("Manage Properties - " + player.getName());

        // Main container for the property management interface
        VBox mainBox = new VBox(10);
        mainBox.setPadding(new Insets(15));
        
        // Header section showing player's current cash
        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);
        Label lblCash = new Label("Your Cash: $" + player.getCash());
        lblCash.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: green;");
        header.getChildren().add(lblCash);
        
        // Add header and separator to main container
        mainBox.getChildren().addAll(header, new Separator());

        // Container for scrollable property list
        VBox scrollContent = new VBox(10);

        // Check if player owns any properties
        if (player.getProperties().isEmpty()) {
            scrollContent.getChildren().add(new Label("You don't own any properties yet."));
        } else {
            // Create a card for each property owned by the player
            for (Property p : new ArrayList<>(player.getProperties())) {
                VBox card = new VBox(5); // Container for individual property card
                
                // Determine border color based on property type
                String borderColor = "#cccccc"; // Default gray border
                if (p instanceof Building) {
                    borderColor = getPropertyColorHex(((Building)p).getColour()); // Use property color
                } else if (p instanceof Railroad) {
                    borderColor = "#000000"; // Black for railroads
                } else if (p instanceof Utility) {
                    borderColor = "#708090"; // Slate gray for utilities
                }
                
                // Apply styling to property card
                card.setStyle("-fx-border-color: " + borderColor + "; -fx-border-width: 3; -fx-border-radius: 5; -fx-padding: 8; -fx-background-color: white;");
                
                // Property name and mortgage status header
                HBox cardHeader = new HBox(10);
                Label pName = new Label(p.getName());
                pName.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
                cardHeader.getChildren().add(pName);
                
                // Add mortgage indicator if property is mortgaged
                if (p.mortgaged) {
                    Label m = new Label("[MORTGAGED]");
                    m.setTextFill(Color.RED);
                    cardHeader.getChildren().add(m);
                }
                card.getChildren().add(cardHeader);

                // Action buttons container
                HBox actions = new HBox(10);
                actions.setAlignment(Pos.CENTER_LEFT);

                // Mortgage/Unmortgage button with dynamic text and cost
                Button btnMort = new Button(p.mortgaged ? "Unmortgage ($" + (int)(p.mortgagedValue * 1.1) + ")" : "Mortgage (+$" + p.mortgagedValue + ")");
                btnMort.setOnAction(e -> {
                    handleMortgageToggle(p, player, propStage); // Handle mortgage state change
                });
                
                // Sell property button
                Button btnSell = new Button("Sell (+$" + p.mortgagedValue + ")");
                btnSell.setStyle("-fx-text-fill: red;");
                btnSell.setOnAction(e -> {
                    handleSellProperty(p, player, propStage); // Handle property sale
                });
                
                // Add basic action buttons to the container
                actions.getChildren().addAll(btnMort, btnSell);

                // Special handling for building properties
                if (p instanceof Building) {
                    Building b = (Building) p;
                    Label info = new Label("Houses: " + b.getHouses()); // Show current house count
                    
                    // Display current rent and next level rent information
                    String rentInfo = "Current Rent: $" + b.getFees();
                    if (b.getHouses() < 5) {
                    // Show next rent level if not at maximum
                    if (b.getHouses() < 5) {
                        rentInfo += " | Next Level: $" + b.rents.get(Math.min(5, b.getHouses() + 1));
                    }
                    Label lblRentDetails = new Label(rentInfo);
                    lblRentDetails.setStyle("-fx-font-size: 10px; -fx-text-fill: grey;");

                    // Add building information to the property card
                    card.getChildren().addAll(info, lblRentDetails);

                    // Add house building option if player has monopoly and property isn't mortgaged
                    if (b.monopolized && !p.mortgaged) {
                        // Dynamic button text based on current development level
                        String buildBtnText = (b.getHouses() == 4) ? "Buy Hotel (-$" + b.housePrice + ")" : "Build House (-$" + b.housePrice + ")";
                        Button btnBuild = new Button(buildBtnText);
                        btnBuild.setStyle("-fx-background-color: #eeffee; -fx-text-fill: green; -fx-border-color: green;");
                        
                        // Disable building if not allowed or insufficient funds
                        if (!b.canBuildHouse() || player.getCash() < b.housePrice) {
                            btnBuild.setDisable(true);
                        }
                        
                        // Handle house/hotel construction
                        btnBuild.setOnAction(e -> {
                            player.setCash(player.getCash() - b.housePrice); // Deduct building cost
                            b.addHouse(); // Add house/hotel to property
                            propStage.close(); // Close current window
                            showPlayerProperties(player); // Reopen with updated information
                            updatePlayerStats(); // Update main game display
                        });
                        actions.getChildren().add(btnBuild);
                    } else if (!b.monopolized) {
                        // Show message if monopoly is required for building
                        Label lblMono = new Label("(Need Monopoly to build)");
                        lblMono.setStyle("-fx-font-size: 10; -fx-font-style: italic;");
                        actions.getChildren().add(lblMono);
                    }
                }

                // Add action buttons to property card and card to scroll content
                card.getChildren().add(actions);
                scrollContent.getChildren().add(card);
            }
        }

        // Create scrollable container for property list
        ScrollPane sp = new ScrollPane(scrollContent);
        sp.setFitToWidth(true);
        mainBox.getChildren().add(sp);

        // Display the property management window
        Scene scene = new Scene(mainBox, 500, 600);
        propStage.setScene(scene);
        propStage.show();
    }}
    
    /**
     * This method converts property color names to hexadecimal color codes for UI styling.
     * @param color The color name to convert
     * @return The hexadecimal color code as a string
     */
    private String getPropertyColorHex(String color) {
        // Normalize color name by removing spaces, underscores, and converting to lowercase
        String norm = color.toLowerCase().replace(" ", "").replace("_", "");
        
        // Return appropriate hex color code based on normalized color name
        switch(norm) {
            case "brown": return "#8B4513";        
            case "lightblue": 
            case "skyblue": return "#87CEEB";      
            case "pink": return "#FF69B4";         
            case "orange": return "#FFA500";       
            case "red": return "#FF0000";          
            case "yellow": return "#FFD700";       
            case "green": return "#008000";        
            case "darkblue": 
            case "blue": return "#00008B";         
            default: return "#CCCCCC";             
        }
    }
    
    /**
     * This method handles toggling the mortgage status of a property and manages the  transactions and validation for mortgaging/unmortgaging.
     * @param p The property to mortgage or unmortgage
     * @param player The player who owns the property
     * @param window The property management window to refresh
     * @return This method does not return anything
     */
    private void handleMortgageToggle(Property p, Player player, Stage window) {
        if (p.mortgaged) {
            // Unmortgaging: player pays 110% of mortgage value
            int cost = (int)(p.mortgagedValue * 1.1);
            if (player.getCash() >= cost) {
                player.setCash(player.getCash() - cost); // Deduct unmortgage cost
                p.setMortgaged(false); // Remove mortgage status
            } else {
                // Insufficient funds for unmortgaging
                showAlert(Alert.AlertType.ERROR, "Funds", "Not enough cash to unmortgage.");
                return;
            }
        } else {
            // check if houses need to be sold first
            if (p instanceof Building && ((Building)p).getHouses() > 0) {
                 showAlert(Alert.AlertType.ERROR, "Houses", "Must sell houses before mortgaging.");
                 return;
            }
            // Give player mortgage value in cash
            player.setCash(player.getCash() + p.mortgagedValue);
            p.setMortgaged(true); // Set mortgage status
        }
        
        // Update displays and reopen property window
        updatePlayerStats();
        window.close();
        showPlayerProperties(player);
    }
    
    /**
     * This method handles the sale of a property back to the bank. It confirms the sale, handles house removal, and transfers money to the player.
     * @param p The property to sell
     * @param player The player selling the property
     * @param window The property management window to refresh
     * @return This method does not return anything
     */
    private void handleSellProperty(Property p, Player player, Stage window) {
        // Confirm property sale with user
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setContentText("Sell " + p.getName() + " to Bank for $" + p.mortgagedValue + "?");
        Optional<ButtonType> res = confirm.showAndWait();
        
        if (res.isPresent() && res.get() == ButtonType.OK) {
            // Remove all houses from building properties before sale
            if (p instanceof Building) {
                Building b = (Building) p;
                while(b.getHouses() > 0) {
                    b.removeHouse(); // Sell houses back to bank
                }
            }
            
            // Transfer property sale proceeds to player
            player.setCash(player.getCash() + p.mortgagedValue);
            
            // Remove property from player's ownership
            player.removeProperty(p);
            
            // Reset property to unowned state
            p.purchased = false;
            p.owner = banker;
            p.setMortgaged(false);
            p.setMonopolized(false);
            
            // Update displays and reopen property window
            updatePlayerStats();
            window.close();
            showPlayerProperties(player);
        }
    }
 /**
  * This method uses a linear search to find the player with the highest total assets
  * @param prompt This method does not expect a prompt
  * @return This method does not return anything
  */
 public static void printLeaderboard() {

     Console.print("\n===== LEADERBOARD =====\n"); // Print leaderboard header

     Player leader = null;    // Track the current leader
     int highestAssets = -1;  // Track the highest asset value found

     // Linear search through all active players
     for (Player p : players) {

         int totalAssets = p.getCash(); // Start with player's cash amount

         // Add value of all owned properties to total assets
         for (Property prop : p.getProperties()) {
             totalAssets += prop.getInherentValue(); // Add property purchase price

             // Add value of houses and hotels if property is a Building
             if (prop instanceof Building) {
                 Building b = (Building) prop;
                 totalAssets += b.getHouses() * b.housePrice; // Add house/hotel investment
             }
         }

         // Display individual player information
         Console.print(p.getName() + " | Cash: $");
         Console.print(p.getCash());
         Console.print(" | Total Assets: $");
         Console.print(totalAssets);
         Console.print("\n");

         // Linear max-search to find the wealthiest player
         if (totalAssets > highestAssets) {
             highestAssets = totalAssets; // Update highest asset value
             leader = p;                  // Update current leader
         }
     }

     Console.print("-----------------------\n"); // Print separator line

     // Display the current leader information
     if (leader != null) {
         Console.print("CURRENT LEADER: " + leader.getName() + " with $");
         Console.print(highestAssets);
         Console.print(" in total assets\n");
     }

     Console.print("=======================\n\n"); // Print footer
 }

}

