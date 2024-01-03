import java.util.HashMap;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;


//
// Michael Jedziniak (mjedzi3)
// Project 2 - Three Card Poker
//
// Using the Card, Player, Dealer, Deck, and Logic Class, we will
// create a GUI application that uses them to make a functional
// game that anyone can easily understand and play.
// Moreover, the GUI will be customized visually to be appealing
// to all users of the Game.
//
// NOTE!!!!!
// There is a known bug in the game where when you see results,
// sometimes the Dealer Hand will not shown. I tried my best to work around this
// issue, however wasn't able to make it work 100% of the time.
// RESOLVED: WAS AN ISSUE WITH DEALER QUAlIFYING, SHOULD BE FIXED NOW
//




public class  ThreeCardPokerGame extends Application {
	
	// SCREEN/COMPONENT SIZE VARIABLES
	static final int width = 1080;
	static final int height = 620;
	static final int table_width = 275;
	static final int table_height = 130;
	static final int card_width = 85;
	static final int card_height = 120;
	
	PauseTransition pause = new PauseTransition(Duration.seconds(1));
	
	
	// Players & Dealer
	Player playerOne = new Player();
	Player playerTwo = new Player();;
	Dealer theDealer = new Dealer();;
	// END OF Players & Dealer
	
	HashMap<String, Scene> sceneMap; 
	
	
	// APPLICATION STATES
	boolean newLookEnabled = false;
	
	// SCENE 1 (START SCREEN) COMPONENTS
	private Button start;
	private Label title;
	private VBox v;
	
	
	// SCENE 2 (GAME SCREEN) COMPONENTS
	private MenuBar menu;
	private GridPane grid;
	private Button startGame;
	private Label cardInfo;
	private Label gameInfo;
	private Label p1Info, p2Info;	
	
		// P1 COMPONENTS
	private HBox p1table;
	private Label p1Card1, p1Card2, p1Card3;
	private Label p1Stats;
	
		// P2 COMPONENTS
	private Label p2Card1, p2Card2, p2Card3;
	private HBox p2table;
	private Label p2Stats;
	
		// DEALER COMPONENTS
	private HBox dealtable;
	private Label dealCard1, dealCard2, dealCard3;
	
		// BETTING COMPONENTS
	private ToggleButton  p1Fold, p2Fold, p1Play, p2Play;
	private ToggleGroup p1Bets, p2Bets;
	private TextField p1Ante, p2Ante, p1Pair, p2Pair;
		// BET VARIABLES
	private int p1ABet, p2ABet, p1PBet, p2PBet;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Three Card Poker");
		
		sceneMap = new HashMap<String,Scene>();
			
		// SCENE 1
		sceneMap.put("start", createScene1());
		// SCENE 2
		sceneMap.put("game", createScene2());
		
		// Change from Scene 1 to Scene 2
		start.setOnAction(e -> primaryStage.setScene(sceneMap.get("game")));
		
			primaryStage.setScene(sceneMap.get("start"));
			primaryStage.show();
	}
	
	public Scene createScene1() {
			start = new Button("START GAME");
			title = new Label("WELCOME TO THREE CARD POKER");
			
			v = new VBox(title, start);
			v.setAlignment(Pos.TOP_CENTER);
			v.setSpacing(200);
				
			customizeScene1();
			return new Scene(v, width, height);
	}
	
	public void customizeScene1() {
		v.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%"
				+ ", #FB8FFF, #FF5C5F)");
		title.setFont(Font.font ("Impact", 75));
		start.setFont(Font.font ("Impact", 35));
		start.setPrefSize(table_width, (table_height) - 50);
	}
	
	
	public Scene createScene2() {
		// SCENE 2
		// Menu
			menu = new MenuBar();
			Menu opt = new Menu("OPTIONS");
			MenuItem exit = new MenuItem("EXIT");
			MenuItem fresh = new MenuItem("FRESH START");
			MenuItem newLook = new MenuItem("NEW LOOK");
			opt.getItems().addAll(exit, fresh, newLook);
			menu.getMenus().add(opt);
		
		// Player/Dealer Hands
			createP1Hand();
			createP2Hand();
			createDealerHand();
			
		// Player Stats
			p1Stats = new Label();
			p2Stats = new Label();
			p1Stats.setMaxWidth(200);
			p2Stats.setMaxWidth(200);
			
		// Bet Buttons
			p1Fold = new ToggleButton("FOLD");
			p1Play = new ToggleButton("PLAY WAGER");
			p2Fold = new ToggleButton("FOLD");
			p2Play = new ToggleButton("PLAY WAGER");
			
			p1Bets = new ToggleGroup();
			p1Bets.getToggles().addAll(p1Fold, p1Play);
			p2Bets = new ToggleGroup();
			p2Bets.getToggles().addAll(p2Fold, p2Play);
			
			p1Ante = new TextField();
			p1Ante.setPrefWidth(table_width/2);
			p1Pair = new TextField();
			p1Pair.setPrefWidth(table_width/2);
			p2Ante = new TextField();
			p2Ante.setPrefWidth(table_width/2);
			p2Pair = new TextField();
			p2Pair.setPrefWidth(table_width/2);
			
		// GAME
			startGame = new Button();
			cardInfo = new Label();
			cardInfo.setMaxWidth(200);
			cardInfo.setText("CARD INFO\nH = Hearts              D = Diamonds\n"
							+ "S = Spades             C = Clubs\n"
							+ "1 = Ace                    13 = King\n"
							+ "12 = Queen           11 = Jack\n");
			
			gameInfo = new Label();
			gameInfo.setPrefSize(250, 140);
			p1Info = new Label();
			p1Info.setPrefSize(250, 140);
			p2Info = new Label();
			p2Info.setPrefSize(250, 140);
			
		// Grid
			grid = new GridPane();
			grid.add(dealtable, 2, 0);
			grid.add(p1Stats, 0, 0);
			grid.add(p2Stats, 4, 0);
			grid.add(p1table, 0, 3);
			grid.add(p2table, 4, 3);
			grid.add(new VBox(p1Play,p1Fold), 1, 3);
			grid.add(new HBox(p1Ante,p1Pair), 0, 2);
			grid.add(new HBox(p2Ante,p2Pair), 4, 2);
			grid.add(startGame, 2, 2);
			grid.add(cardInfo, 2, 3);
			grid.add(gameInfo, 2, 1);
			grid.add(p1Info, 0, 1);
			grid.add(p2Info, 4, 1);
			grid.add(new VBox(p2Play,p2Fold), 3, 3);
			
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setAlignment(Pos.CENTER);
			grid.setPrefSize(width, height);
			
			
		// Menu Event NOTE: Cannot move due to being local
			exit.setOnAction(e -> Platform.exit());					// close app
			newLook.setOnAction(e -> { customizeNewLook(); });
			fresh.setOnAction(e -> {
				playerOne.totalWinnings = 0;
				playerTwo.totalWinnings = 0;
				playerStatsDisplay();
			});
			
			
			prePokerGame();
			customizeScene2();
			return new Scene(new VBox(menu, grid), width, height);
	}
	
	public void eventListenStartGame() {
		startGame.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent a) {
				try {
					p1ABet = Integer.parseInt(p1Ante.getText());
					p1PBet = Integer.parseInt(p1Pair.getText());
					p2ABet = Integer.parseInt(p2Ante.getText());
					p2PBet = Integer.parseInt(p2Pair.getText());
					
					// Evaluate the Bets to be Valid
					if( (p1ABet>4 && p1ABet<26) && (p2ABet>4 && p2ABet<26) &&
						( (p1PBet>4 && p1PBet<26) || (p1PBet == 0) ) &&
						( (p2PBet>4 && p2PBet<26) || (p2PBet == 0) ) ) {
						startPokerGame();
					} else {
						gameInfo.setText("Invalid Amount");
					}
					
				}
				catch (NumberFormatException e){
					//myLabel.setText("enter only numbers plz");
					gameInfo.setText("Invalid Input");
				}
			}
		});
	}
	
	public void prePokerGame() {
		p1Ante.setEditable(true);
		p1Pair.setEditable(true);
		p2Ante.setEditable(true);
		p2Pair.setEditable(true);
		
		p1Fold.setDisable(true);
		p1Play.setDisable(true);
		p2Fold.setDisable(true);
		p2Play.setDisable(true);
		
		p1Ante.setPromptText("Ante Bet");
		p1Pair.setPromptText("Pair Plus Bet");
		p2Ante.setPromptText("Ante Bet");
		p2Pair.setPromptText("Pair Plus Bet");
		
		startGame.setVisible(true);
		startGame.setText("DEAL");
		
		gameInfo.setText("WELCOME!\nSet Your Bets!!");
		p1Info.setText("PLAYER 1\nBet Amount: $5-$25\nSet Pair Plus to 0 if you don't like a challenge!!");
		p2Info.setText("PLAYER 2\nBet Amount: $5-$25\nSet Pair Plus to 0 if you don't like a challenge!!");
		p1Info.setWrapText(true);
		p2Info.setWrapText(true);
		
		dealCard1.setVisible(false);
		dealCard2.setVisible(false);
		dealCard3.setVisible(false);

		p2Card1.setVisible(false);
		p2Card2.setVisible(false);
		p2Card3.setVisible(false);

		p1Card1.setVisible(false);
		p1Card2.setVisible(false);
		p1Card3.setVisible(false);

		playerStatsDisplay();
		eventListenStartGame();			// Call Event Listen for when to Start Game
	}
	
	public void eventListenSeeResults() {
		startGame.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent a) {
				Toggle selectedPlayerOne = p1Bets.getSelectedToggle();
				Toggle selectedPlayerTwo = p2Bets.getSelectedToggle();
				
				if (selectedPlayerOne == p1Play) {
					playerOne.playBet = playerOne.anteBet;
				} else if (selectedPlayerOne == p1Fold) {
					playerOne.totalWinnings -= (playerOne.anteBet + playerOne.pairPlusBet);
					playerOne.anteBet = 0;			// This will be used to show that the player folded
					playerOne.pairPlusBet = 0;
				} else {
					p1Info.setText("Player One! Play or Fold?");
				}
				
				if (selectedPlayerTwo == p2Play) {
					playerTwo.playBet = playerTwo.anteBet;
				} else if (selectedPlayerTwo == p2Fold) {
					playerTwo.totalWinnings -= (playerTwo.anteBet + playerTwo.pairPlusBet);
					playerTwo.anteBet = 0;
					playerTwo.pairPlusBet = 0;
				} else {
					p2Info.setText("Player Two! Play or Fold?");
				}
				
				if ( (selectedPlayerOne != null) && (selectedPlayerTwo != null) ) {
					postPokerGame();
				}
			}
		});
	}
	public void startPokerGame() {
		// Turn off bets
		p1Ante.setEditable(false);
		p1Pair.setEditable(false);
		p2Ante.setEditable(false);
		p2Pair.setEditable(false);
		
		// Turn on Play/Fold
		p1Fold.setDisable(false);
		p1Play.setDisable(false);
		p2Fold.setDisable(false);
		p2Play.setDisable(false);
		
		startGame.setText("SEE RESULTS");
		
		p1Info.setText("Will you win?");
		p2Info.setText("Will you win?");
		gameInfo.setText("Will you win?");
		
		// update Players Stats
		playerOne.anteBet = p1ABet;
		playerOne.pairPlusBet = p1PBet;
		playerTwo.anteBet = p2ABet;
		playerTwo.pairPlusBet = p2PBet;
		playerStatsDisplay();
		
		// Distribute Cards
		theDealer.dealersHand = theDealer.dealHand();
		playerOne.hand = theDealer.dealHand();
		playerTwo.hand = theDealer.dealHand();
		
		//Display Cards
		displayCards();
		
		// Eval
		//startGame.setOnAction(e -> { prePokerGame(); });
		eventListenSeeResults();
	}
	
	public void postPokerGame() {
		// Show Dealers Cards
		dealCard1.setText(theDealer.dealersHand.get(0).suit + String.valueOf(theDealer.dealersHand.get(0).value));
		dealCard2.setText(theDealer.dealersHand.get(1).suit + String.valueOf(theDealer.dealersHand.get(1).value));
		dealCard3.setText(theDealer.dealersHand.get(2).suit + String.valueOf(theDealer.dealersHand.get(2).value));
		
		gameInfo.setText("GAME RESULTS!!!");
		
		p1Fold.setSelected(false);
		p1Play.setSelected(false);
		p2Fold.setSelected(false);
		p2Play.setSelected(false);
		
		p1Fold.setDisable(true);
		p1Play.setDisable(true);
		p2Fold.setDisable(true);
		p2Play.setDisable(true);
		
		// Checking Pair Plus
		String p1PPResult, p2PPResult;
		if (playerOne.pairPlusBet != 0) {
			int winningsPP = ThreeCardLogic.evalPPWinnings(playerOne.hand, playerOne.pairPlusBet);
			if (winningsPP > 0) {
				playerOne.totalWinnings += winningsPP;
				p1PPResult = "Player One won $" + winningsPP + " from Pair Plus";
			} else {
				playerOne.totalWinnings -= playerOne.pairPlusBet;
				p1PPResult = "Player One lose from Pair Plus";
			}
		} else {
			p1PPResult = "";
		}
		if (playerTwo.pairPlusBet != 0 ) {
			int winningsPP = ThreeCardLogic.evalPPWinnings(playerOne.hand, playerOne.pairPlusBet);
			if (winningsPP > 0) {
				playerTwo.totalWinnings += winningsPP;
				p2PPResult = "Player Two won $" + winningsPP + " from Pair Plus";
			} else {
				playerTwo.totalWinnings -= playerTwo.pairPlusBet;
				p2PPResult = "Player Two lose from Pair Plus";
			}
		} else {
			p2PPResult = "";
		}
		
		// Compare to see Results
		int playerOneResults = ThreeCardLogic.compareHands(theDealer.dealersHand, playerOne.hand);
		int playerTwoResults = ThreeCardLogic.compareHands(theDealer.dealersHand, playerTwo.hand);
		
		int dealerQualify = 1;
		// if 0, Dealer didn't Qualify
		if (playerOneResults == 0 && ThreeCardLogic.evalHigh(theDealer)<12) {
			gameInfo.setText("Dealer didn't Qualify\nPushing Ante Bet...\nClick PLAY AGAIN to continue\nYou will receive a new Hand!");
			dealerQualify = 0;
		} else {
		
			// if not zero, they are still playing and didn't fold
			if (playerOne.anteBet != 0) {
				if (playerOneResults == 2) {
					int p1Winnings = 2 * (playerOne.anteBet + playerOne.playBet);
					playerOne.totalWinnings += p1Winnings;
					p1Info.setText("Player One won $" + p1Winnings + "\n" + p1PPResult);
				} else {
					int p1Losings = (playerOne.anteBet + playerOne.playBet);
					playerOne.totalWinnings -= p1Losings;
					p1Info.setText("Player One lose $" + p1Losings + "\n" + p1PPResult);
				}
			} else {			// They folded
				p1Info.setText("Player One Folded");
			}
			
			if (playerTwo.anteBet != 0 ) {
				if (playerTwoResults == 2) {
					int p2Winnings = 2 * (playerTwo.anteBet + playerTwo.playBet);
					playerTwo.totalWinnings += p2Winnings;
					p2Info.setText("Player Two won $" + p2Winnings + "\n" + p2PPResult);
				} else {
					int p2Losings = (playerTwo.anteBet + playerTwo.playBet);
					playerTwo.totalWinnings -= p2Losings;
					p2Info.setText("Player Two lose $" + p2Losings + "\n" + p2PPResult);
				}
			} else {			// They folded
				p2Info.setText("Player Two Folded");
			}
			// Result Bets
			playerOne.anteBet = 0;
			playerTwo.anteBet = 0;
			dealerQualify = 1;
		}	
		
		// DO NOT RESET ANTE only when the else is executed
		playerOne.playBet = 0;
		playerOne.pairPlusBet = 0;
		playerTwo.playBet = 0;
		playerTwo.pairPlusBet = 0;
		
		// Display updated Stats
		playerStatsDisplay();
		
		// Change game Button
		startGame.setText("Play Again?");
		
		// Playing again depending on if the Dealer Qualified
		if (dealerQualify == 1) {
			startGame.setOnAction(e -> { prePokerGame(); });
		} else {
			startGame.setOnAction(e -> { startPokerGame(); });
		}
		
	}
	
	
	public void displayCards() {
		// Change text to card
		p1Card1.setText(playerOne.hand.get(0).suit + String.valueOf(playerOne.hand.get(0).value));
		p1Card2.setText(playerOne.hand.get(1).suit + String.valueOf(playerOne.hand.get(1).value));
		p1Card3.setText(playerOne.hand.get(2).suit + String.valueOf(playerOne.hand.get(2).value));
		p2Card1.setText(playerTwo.hand.get(0).suit + String.valueOf(playerTwo.hand.get(0).value));
		p2Card2.setText(playerTwo.hand.get(1).suit + String.valueOf(playerTwo.hand.get(1).value));
		p2Card3.setText(playerTwo.hand.get(2).suit + String.valueOf(playerTwo.hand.get(2).value));
		dealCard1.setText("HIDDEN");
		dealCard2.setText("HIDDEN");
		dealCard3.setText("HIDDEN");
		
		// Make Visible
		p1Card1.setVisible(true);
		p1Card2.setVisible(true);
		p1Card3.setVisible(true);
		p2Card1.setVisible(true);
		p2Card2.setVisible(true);
		p2Card3.setVisible(true);
		dealCard1.setVisible(true);
		dealCard2.setVisible(true);
		dealCard3.setVisible(true);
	}
	
	public void playerStatsDisplay() {
		String playerOneStats = "PLAYER ONE\nAnte Bet: " + playerOne.anteBet + "\nPair Plus Bet: "
				+ playerOne.pairPlusBet + "\nPlay Wager: "
				+ playerOne.playBet + "\nTotal Winnings: "
				+ playerOne.totalWinnings;
		String playerTwoStats = "PLAYER TWO\nAnte Bet: " + playerTwo.anteBet + "\nPair Plus Bet: "
			+ playerTwo.pairPlusBet + "\nPlay Wager: "
			+ playerTwo.playBet + "\nTotal Winnings: "
			+ playerTwo.totalWinnings;
		p1Stats.setText(playerOneStats);
		p2Stats.setText(playerTwoStats);
	}
	
	public void customizeScene2() {
		p1Card1.setStyle("-fx-border-color:white; -fx-background-color: violet;");
		p1Card2.setStyle("-fx-border-color:white; -fx-background-color: violet;");
		p1Card3.setStyle("-fx-border-color:white; -fx-background-color: violet;");
		p1table.setBackground(new Background(new BackgroundFill(Color.GREEN,null,null)));
		p1table.setSpacing(5);
		
		p2Card1.setStyle("-fx-border-color:white; -fx-background-color: violet;");
		p2Card2.setStyle("-fx-border-color:white; -fx-background-color: violet;");
		p2Card3.setStyle("-fx-border-color:white; -fx-background-color: violet;");
		p2table.setBackground(new Background(new BackgroundFill(Color.GREEN,null,null)));
		p2table.setSpacing(5);
		
		dealCard1.setStyle("-fx-border-color:white; -fx-background-color: violet;");
		dealCard2.setStyle("-fx-border-color:white; -fx-background-color: violet;");
		dealCard3.setStyle("-fx-border-color:white; -fx-background-color: violet;");
		dealtable.setBackground(new Background(new BackgroundFill(Color.GREEN,null,null)));
		dealtable.setSpacing(5);
		
		startGame.setFont(Font.font ("Impact", 35));
		startGame.setPrefSize(table_width, (table_height) - 50);
		
		p1Fold.setPrefSize(table_width/3, table_height/2);
		p2Fold.setPrefSize(table_width/3, table_height/2);
		p1Play.setPrefSize(table_width/3, table_height/2);
		p2Play.setPrefSize(table_width/3, table_height/2);
		
		grid.setStyle("-fx-background-color:lightgreen;");
		
		cardInfo.setFont(Font.font ("Sans", 12));
		cardInfo.setAlignment(Pos.CENTER);
		gameInfo.setFont(Font.font ("Sans", 15));
		gameInfo.setAlignment(Pos.CENTER);
		p1Info.setFont(Font.font ("Sans", 20));
		p1Info.setAlignment(Pos.CENTER);
		p2Info.setFont(Font.font ("Sans", 20));
		p2Info.setAlignment(Pos.CENTER);
		p1Card1.setFont(Font.font ("Sans", 20));
		p1Card1.setAlignment(Pos.CENTER);
		p1Card2.setFont(Font.font ("Sans", 20));
		p1Card2.setAlignment(Pos.CENTER);
		p1Card3.setFont(Font.font ("Sans", 20));
		p1Card3.setAlignment(Pos.CENTER);
		p1Stats.setFont(Font.font ("Sans", 20));
		p1Stats.setAlignment(Pos.CENTER);
		p2Card1.setFont(Font.font ("Sans", 20));
		p2Card1.setAlignment(Pos.CENTER);
		p2Card2.setFont(Font.font ("Sans", 20));
		p2Card2.setAlignment(Pos.CENTER);
		p2Card3.setFont(Font.font ("Sans", 20));
		p2Card3.setAlignment(Pos.CENTER);
		p2Stats.setFont(Font.font ("Sans", 20));
		p2Stats.setAlignment(Pos.CENTER);
		dealCard1.setFont(Font.font ("Sans", 20));
		dealCard1.setAlignment(Pos.CENTER);
		dealCard2.setFont(Font.font ("Sans", 20));
		dealCard2.setAlignment(Pos.CENTER);
		dealCard3.setFont(Font.font ("Sans", 20));
		dealCard3.setAlignment(Pos.CENTER);
		
	}
	
	public void customizeNewLook() {
		grid.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%"
				+ ", #FB8FFF, #FF5C5F)");
		p1table.setStyle("-fx-background-color:lightblue;");
		p2table.setStyle("-fx-background-color:lightblue;");
		dealtable.setStyle("-fx-background-color:lightblue;");
		
		cardInfo.setFont(Font.font ("Impact", 15));
		cardInfo.setAlignment(Pos.CENTER);
		gameInfo.setFont(Font.font ("Impact", 20));
		gameInfo.setAlignment(Pos.CENTER);
		p1Info.setFont(Font.font ("Impact", 20));
		p1Info.setAlignment(Pos.CENTER);
		p2Info.setFont(Font.font ("Impact", 20));
		p2Info.setAlignment(Pos.CENTER);
		p1Card1.setFont(Font.font ("Impact", 20));
		p1Card1.setAlignment(Pos.CENTER);
		p1Card2.setFont(Font.font ("Impact", 20));
		p1Card2.setAlignment(Pos.CENTER);
		p1Card3.setFont(Font.font ("Impact", 20));
		p1Card3.setAlignment(Pos.CENTER);
		p1Stats.setFont(Font.font ("Impact", 20));
		p1Stats.setAlignment(Pos.CENTER);
		p2Card1.setFont(Font.font ("Impact", 20));
		p2Card1.setAlignment(Pos.CENTER);
		p2Card2.setFont(Font.font ("Impact", 20));
		p2Card2.setAlignment(Pos.CENTER);
		p2Card3.setFont(Font.font ("Impact", 20));
		p2Card3.setAlignment(Pos.CENTER);
		p2Stats.setFont(Font.font ("Impact", 20));
		p2Stats.setAlignment(Pos.CENTER);
		dealCard1.setFont(Font.font ("Impact", 20));
		dealCard1.setAlignment(Pos.CENTER);
		dealCard2.setFont(Font.font ("Impact", 20));
		dealCard2.setAlignment(Pos.CENTER);
		dealCard3.setFont(Font.font ("Impact", 20));
		dealCard3.setAlignment(Pos.CENTER);
	}
	
	public void createP1Hand() {
		p1Card1 = new Label();
		p1Card2 = new Label();
		p1Card3 = new Label();
		p1table = new HBox(p1Card1, p1Card2, p1Card3);
		
		p1Card1.setPrefSize(card_width, card_height);
		p1Card2.setPrefSize(card_width, card_height);
		p1Card3.setPrefSize(card_width, card_height);
		p1Card1.setAlignment(Pos.CENTER);
		p1Card2.setAlignment(Pos.CENTER);
		p1Card3.setAlignment(Pos.CENTER);
		
		p1table.setPrefSize(table_width, table_height);
		p1table.setSpacing(5);
		p1table.setPadding(new Insets(5, 5, 5, 5));
	}
	
	public void createP2Hand() {
		p2Card1 = new Label();
		p2Card2 = new Label();
		p2Card3 = new Label();
		p2table = new HBox(p2Card1, p2Card2, p2Card3);
		
		p2Card1.setPrefSize(card_width, card_height);
		p2Card2.setPrefSize(card_width, card_height);
		p2Card3.setPrefSize(card_width, card_height);
		p2Card1.setAlignment(Pos.CENTER);
		p2Card2.setAlignment(Pos.CENTER);
		p2Card3.setAlignment(Pos.CENTER);
		
		p2table.setPrefSize(table_width, table_height);
		p2table.setSpacing(5);
		p2table.setPadding(new Insets(5, 5, 5, 5));
	}

	public void createDealerHand() {
		dealCard1 = new Label();
		dealCard2 = new Label();
		dealCard3 = new Label();
		dealtable = new HBox(dealCard1, dealCard2, dealCard3);
		
		dealCard1.setPrefSize(card_width, card_height);
		dealCard2.setPrefSize(card_width, card_height);
		dealCard3.setPrefSize(card_width, card_height);
		dealCard1.setAlignment(Pos.CENTER);
		dealCard2.setAlignment(Pos.CENTER);
		dealCard3.setAlignment(Pos.CENTER);
		
		dealtable.setPrefSize(table_width, table_height);
		dealtable.setSpacing(5);
		dealtable.setPadding(new Insets(5, 5, 5, 5));
	}
	
}
