package application;

import java.sql.*;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Kiosk extends Application {
	protected static TextArea receipt;
	protected static Statement stmt;
	protected static ArrayList<Button> buttonList = new ArrayList<Button>();
	protected static ArrayList<Integer> itemList = new ArrayList<Integer>();
	protected static ArrayList<Integer> itemCount = new ArrayList<Integer>();
	
	protected static ArrayList<String> itemName = new ArrayList<String>();
	protected static ArrayList<Double> itemPrice = new ArrayList<Double>();
	protected static ArrayList<Integer> itemId = new ArrayList<Integer>();
	protected static ArrayList<String> imageUrl = new ArrayList<String>();
	protected static ArrayList<BackgroundImage> backgroundImage = new ArrayList<BackgroundImage>();
	protected static ArrayList<Background> background = new ArrayList<Background>();
	
	protected static int nextOrderId;
	
	protected static Label submitLbl = new Label("Order stashed");
	protected static VBox currentOrderItem = new VBox();
	protected static VBox currentOrderPrice = new VBox();

	public static void main(String[] args) {
		launch(args);
	}
	
	public void initializeDB() {
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/POS", "root", "VinPet1!");
			stmt = connection.createStatement();
			System.out.println("Database connected");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	protected static Label getLabel(String labelText) {
		Label label = new Label(labelText);
		label.setFont(new Font("Ariel Black", 18));
		return label;
	}
	
	private static void submitOrder() {
		String query = "INSERT INTO pos.order (order_id) VALUES (" + nextOrderId + ")";
		try {
			stmt.executeUpdate(query);
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		for (int i = 0; i < itemList.size(); i++) {
			query = "INSERT INTO order_has_item VALUES (" + nextOrderId + ", " + itemList.get(i) + ", " + itemCount.get(i) + ")";
			try {
				stmt.executeUpdate(query);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		nextOrderId++;
		submitLbl.setVisible(true);
		submitLbl.setStyle("-fx-text-fill: WHITE;");
		submitLbl.setFont(Font.font("Segoe Script", 20));
		currentOrderItem.getChildren().clear();
		currentOrderPrice.getChildren().clear();
		itemList.clear();
		itemCount.clear();
	}
	
	private void populateButtonArea(GridPane center) {
		int maxCol = 8;
		int currentCol = 0;
		int currentRow = 0;
		for (int i = 0; i < itemName.size(); i++) {
			if (currentCol % maxCol == 0 && i != 0) {
				currentRow++;
				currentCol = 0;
			}
			buttonList.add(new Button(itemName.get(i)));
			buttonList.get(i).setPrefSize(130, 130);

			if (imageUrl.get(i) != "noimage") {
//				System.out.println("url: " + imageUrl.get(i));
				backgroundImage.add(new BackgroundImage(new Image(getClass().getResource("/images/"+ imageUrl.get(i)).toExternalForm(), 130, 130, false, true, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT));
				background.add(new Background(backgroundImage.get(i)));
				buttonList.get(i).setBackground(background.get(i));
			}
			
			buttonList.get(i).setWrapText(true);
			buttonList.get(i).setAlignment(Pos.TOP_CENTER);
			buttonList.get(i).setStyle("-fx-font-size: 15");
			center.add(buttonList.get(buttonList.size() - 1), currentCol, currentRow);
			final int buttonNum = i;
			buttonList.get(buttonList.size() - 1).setOnAction(e -> {
				currentOrderItem.getChildren().add(getLabel(itemName.get(buttonNum)));
				currentOrderPrice.getChildren().add(getLabel(Double.toString(itemPrice.get(buttonNum))));
				int itemNum = itemId.get(buttonNum);
				int index = itemList.indexOf(itemNum);
				if (index != -1) {
					itemCount.set(index, itemCount.get(index) + 1);
				} else {
					itemList.add(itemId.get(buttonNum));
					itemCount.add(1);
				}
				submitLbl.setVisible(false);
			});
			currentCol++;
		}
	}
	
	private static void getNextOrder() {
		String query = "SELECT MAX(order_id) FROM pos.order";
		ResultSet result;
		try {
			result = stmt.executeQuery(query);
			while (result.next()) {
				nextOrderId = result.getInt(1) + 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void getMenu() {
		String query = "SELECT * FROM item";
		try {
			ResultSet result = stmt.executeQuery(query);
			while(result.next()) {
				itemName.add(result.getString(2));
				itemPrice.add(result.getDouble(3));
				itemId.add(result.getInt(1));
				imageUrl.add(result.getString(4));
			}
		} catch (SQLException e) {
			System.out.println("Menu not accessed");
			e.printStackTrace();
		}
	}
	
	public static VBox createVB() {
		VBox vb = new VBox();
		vb.setPadding(new Insets(8, 8, 8, 8));
		vb.setSpacing(10);
		
		return vb;
	}
	
	public static HBox createHB() {
		HBox hb = new HBox();
		hb.setPadding(new Insets(12, 15, 12, 15));
		hb.setSpacing(10);
		
		return hb;
	}
	
	public static GridPane createGridPane() {
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(15, 12, 15, 12));
		gp.setHgap(12);
		gp.setVgap(15);
		
		return gp;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		try {
			initializeDB();
			
			getMenu();
			getNextOrder();
			
			submitLbl.setVisible(false);
			
			primaryStage.setTitle("Menu");
			
			
			BorderPane border = new BorderPane();
			
			
			HBox hbTop = createHB();
			hbTop.setStyle("-fx-background-color: #F40009;");
			Text topText = new Text("Menu");
			topText.setFont(Font.font("Segoe Script", FontWeight.BOLD, 28));
		    topText.setFill(Color.WHITE);
		    topText.setStroke(Color.web("7080A0"));
			hbTop.getChildren().add(topText);
			border.setTop(hbTop);
			
			StackPane hbTopStack = new StackPane();
			Rectangle helpIcon = new Rectangle(30.0, 25.0);
		    helpIcon.setFill(new LinearGradient(0,0,0,1, true, CycleMethod.NO_CYCLE, new Stop[] {
		        new Stop(0,Color.web("#4977A3")),
		        new Stop(0.5, Color.web("#B0C6DA")),
		        new Stop(1,Color.web("#9CB6CF")),}));
		    helpIcon.setStroke(Color.web("#D0E6FA"));
		    helpIcon.setArcHeight(3.5);
		    helpIcon.setArcWidth(3.5);
		    Text helpText = new Text("?");
		    helpText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
		    helpText.setFill(Color.WHITE);
		    helpText.setStroke(Color.web("7080A0"));
		    Button btnHelpIcon = new Button();
			btnHelpIcon.setPrefSize(30.0, 25.0);
		    hbTopStack.getChildren().addAll(btnHelpIcon, helpIcon, helpText);
		    hbTopStack.setAlignment(Pos.CENTER_RIGHT);
		    StackPane.setMargin(helpText, new Insets(0, 10, 0, 0));
		    hbTop.getChildren().add(hbTopStack);
		    HBox.setHgrow(hbTopStack, Priority.ALWAYS);
					
			VBox vbRight = createVB();
			vbRight.setStyle("-fx-background-color: #0047bb;");
			HBox currentOrder = createHB();
			currentOrder.setStyle("-fx-background-color: WHITE");
			currentOrder.setPrefSize(350, 475);
			currentOrderItem = createVB();
			currentOrderItem.setPrefSize(250, 450);
			currentOrderPrice = createVB();
			currentOrderPrice.setPrefSize(75, 450);
			currentOrder.getChildren().addAll(currentOrderItem, currentOrderPrice);
			ScrollPane currentOrderScroll = new ScrollPane(currentOrder);
			
			Button submit = new Button("Total");
			submit.setStyle("-fx-font-size: 15");
			submit.setPrefSize(100, 35);
			submit.setOnAction(e -> {
				submitOrder();
			});
			vbRight.getChildren().addAll(currentOrderScroll, submit, submitLbl);
			border.setRight(vbRight);
			
			GridPane center = createGridPane();
			populateButtonArea(center);
			
			border.setCenter(center);
			
			Scene scene = new Scene(border,700,500);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setFullScreen(true);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
