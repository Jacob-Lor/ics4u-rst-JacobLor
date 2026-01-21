/**
 * @author Jacob Lor
 * Date: 2025-09-24
 * ICS4U
 * Bank.java
 * This bank class is responsible for handling the graphical interface of the monopoly game and the associated rules and logic.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.geometry.Point2D;

public class Bank extends Application {
	
	private static ArrayList<Player> players = new ArrayList<Player>();
	private static Object[] tiles = new Object[40];
	private static Player banker = new Player("banker", 0); //The only non player
	public static int roll = 0;
    Image imgBoard = new Image(getClass().getResource("/images/monopolyboard.png").toString());

    public static Point2D[] tilePositions = new Point2D[40]; //40 tiles on a monopoly board.
    private int currentPlayerIndex = 0;
    private Label lblTurnInfo;          
    private Label lblRollResult;        
    private VBox vbxStatsBoxRef;           

	public void start(Stage stage) {
		//Populate positions to set up positions before starting game.
		//bottom
		tilePositions[0] = new Point2D(585, 585); // go start
	    tilePositions[1] = new Point2D(525, 585);
	    tilePositions[2] = new Point2D(465, 585);
	    tilePositions[3] = new Point2D(405, 585);
	    tilePositions[4] = new Point2D(345, 585);
	    tilePositions[5] = new Point2D(285, 585);
	    tilePositions[6] = new Point2D(225, 585);
	    tilePositions[7] = new Point2D(165, 585);
	    tilePositions[8] = new Point2D(105, 585);
	    tilePositions[9] = new Point2D(45, 585);

	    //jail
	    tilePositions[10] = new Point2D(45, 585);
	    
	    //left
	    tilePositions[11] = new Point2D(45, 525);
	    tilePositions[12] = new Point2D(45, 465);
	    tilePositions[13] = new Point2D(45, 405);
	    tilePositions[14] = new Point2D(45, 345);
	    tilePositions[15] = new Point2D(45, 285);
	    tilePositions[16] = new Point2D(45, 225);
	    tilePositions[17] = new Point2D(45, 165);
	    tilePositions[18] = new Point2D(45, 105);
	    tilePositions[19] = new Point2D(45, 45);
	    
	    //free parking
	    tilePositions[20] = new Point2D(45, 45);
	    
	    tilePositions[21] = new Point2D(105, 45);
	    tilePositions[22] = new Point2D(165, 45);
	    tilePositions[23] = new Point2D(225, 45);
	    tilePositions[24] = new Point2D(285, 45);
	    tilePositions[25] = new Point2D(345, 45);
	    tilePositions[26] = new Point2D(405, 45);
	    tilePositions[27] = new Point2D(465, 45);
	    tilePositions[28] = new Point2D(525, 45);
	    tilePositions[29] = new Point2D(585, 45);
	    
	    tilePositions[30] = new Point2D(585, 45); // go to jail
	    
	    tilePositions[31] = new Point2D(585, 105);
	    tilePositions[32] = new Point2D(585, 165);
	    tilePositions[33] = new Point2D(585, 225);
	    tilePositions[34] = new Point2D(585, 285);
	    tilePositions[35] = new Point2D(585, 345);
	    tilePositions[36] = new Point2D(585, 405);
	    tilePositions[37] = new Point2D(585, 465);
	    tilePositions[38] = new Point2D(585, 525);
	    tilePositions[39] = new Point2D(585, 585);

        stage.setTitle("Monopoly Setup");

        Label lblPlayerSelector = new Label("Choose Number of Players");

        // Spinner: monopoly supports 2–4 players
        Spinner<Integer> spnPlayer = new Spinner<>();
        spnPlayer.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 4, 2)
        );

        Button btnStart = new Button("Start Game");

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
        
        Color[] colors = {
                Color.RED,
                Color.BLUE,
                Color.GREEN,
                Color.ORANGE
        };

        for (int i = 0; i < numPlayers; i++) {
        	players.add(new Player("Player " + (i + 1), 1500, colors[i]));
        }
    }

    public void showGameBoard() {
        Stage gameStage = new Stage();
        gameStage.setTitle("Monopoly");

        //left board
        ImageView imgBoardView = new ImageView(imgBoard);
        imgBoardView.setFitWidth(650);
        imgBoardView.setPreserveRatio(true);
        
        //stack pane: will allow tokens/characters to be placed on top of the board.
        StackPane stpBoard = new StackPane();
        stpBoard.getChildren().add(imgBoardView);
        
        // space so players do not overlap
        double[][] offsets = {
        	    { -10, -10 },
        	    {  10, -10 },
        	    { -10,  10 },
        	    {  10,  10 }
        };
        
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            Point2D pos = tilePositions[0]; // start at GO

            p.getToken().setTranslateX(pos.getX() - 325 + offsets[i][0]);
            p.getToken().setTranslateY(pos.getY() - 325 + offsets[i][1]);

            stpBoard.getChildren().add(p.getToken());
        }

        VBox vbxBoard = new VBox(stpBoard);
        vbxBoard.setPadding(new Insets(10));

        // right side
        VBox vbxRightPanel = new VBox(20);
        vbxRightPanel.setPadding(new Insets(10));
        vbxRightPanel.setPrefWidth(320);

        //dice and  turn controls
        lblTurnInfo = new Label("Turn: " + players.get(0).getName());
        lblRollResult = new Label("Roll the dice!");
        Button btnRoll = new Button("Roll Dice");

        btnRoll.setOnAction(e -> handleRoll()); // NEW

        VBox vbxDice = new VBox(10, lblTurnInfo, lblRollResult, btnRoll);
        vbxDice.setPadding(new Insets(10));
        vbxDice.setStyle("-fx-border-color: black;");

        //player stats
        vbxStatsBoxRef = new VBox(6);
        vbxStatsBoxRef.setPadding(new Insets(10));
        vbxStatsBoxRef.setStyle("-fx-border-color: black;");
        updatePlayerStats(vbxStatsBoxRef);

        vbxRightPanel.getChildren().addAll(vbxDice, vbxStatsBoxRef);

        BorderPane root = new BorderPane();
        root.setLeft(vbxBoard);
        root.setRight(vbxRightPanel);

        Scene scene = new Scene(root, 1000, 700);
        gameStage.setScene(scene);
        gameStage.show();
    }

    //handles dice roll and player movement
    private void handleRoll() {
        Player currentPlayer = players.get(currentPlayerIndex);

        int rollValue = rollDie();
        lblRollResult.setText(currentPlayer.getName() + " rolled: " + rollValue);

        movePlayer(currentPlayer, currentPlayerIndex, rollValue);
        updatePlayerStats(vbxStatsBoxRef);

        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        lblTurnInfo.setText("Turn: " + players.get(currentPlayerIndex).getName());
    }

    public void movePlayer(Player player, int playerIndex, int steps) {
        player.setPosition((player.getPosition() + steps) % 40); // wrap board
        Point2D pos = tilePositions[player.getPosition()];

        double[][] offsets = {
            { -10, -10 },
            {  10, -10 },
            { -10,  10 },
            {  10,  10 }
        };

        player.getToken().setTranslateX(pos.getX() - 325 + offsets[playerIndex][0]);
        player.getToken().setTranslateY(pos.getY() - 325 + offsets[playerIndex][1]);
    }

    private void updatePlayerStats(VBox statsBox) {
    	//clears the stats box and iterates through all the players to update their information
    	statsBox.getChildren().clear();
        for (Player p : players) {
        	Label lblPlayer = new Label(p.getName() + " | $" + p.getCash() + " | Pos:" + p.getPosition());
            statsBox.getChildren().add(lblPlayer);
        }
    }

    private static void createProperties() {
        try (BufferedReader br = new BufferedReader(new FileReader("data/properties.txt"))) {
            Console.print("done");
        } catch (IOException e) {
            Console.print("Problem reading from file");
        }
    }
}


