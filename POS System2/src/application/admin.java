package application;

import java.sql.*;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
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
import javafx.scene.text.TextAlignment;

public class admin extends Application {
	Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		launch(args);
	}
	
	private static void initializeDB() throws SQLException {
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/POS", "root", "VinPet1!");
			Statement stmt = conn.createStatement();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	//statement.executeUpdate(query) add delete update either passes success or failure
	//ResultSet resultset = statement.executeQuery(Select statement);
	public void addItem() throws SQLException {
		
		try {
			/*
			Label name = new Label();
			Label price = new Label();
			Label img = new Label();
			TextInputDialog td = new TextInputDialog();
			td.setHeaderText("Adding item to database");
			Button addItem = new Button("Save");
			addItem.setText("Add Item");
			td.showAndWait();
			addItem.setOnAction(new EventHandler() {
				@Override
				public void handle(Event event) {
					name.setText(td.getEditor().getText());
					price.setText(td.getEditor().getText());
					img.setText(td.getEditor().getText());
				}
			});
			
			String query = "INSERT INTO item (item_name, price, img) values ("+name+","+price+","+img+")";
		
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.execute();
			*/
		}
		catch (Exception ex) {
			System.err.println("Exception!");
			System.err.println(ex.getMessage());
		}
	}
	
	public void deleteItem() throws SQLException {
		
		try {
			
			String query = "DELETE FROM item WHERE item_name = ";
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		initializeDB();
		
		primaryStage.setTitle("ADMIN");
		
		
		BorderPane border = new BorderPane();
		
		
		HBox hbTop = new HBox();
		hbTop.setPadding(new Insets(15, 12, 15, 12));
		hbTop.setSpacing(10);
		hbTop.setStyle("-fx-background-color: #336699;");
		
		Text topText = new Text("Welcome Admin");
		topText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
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
		
		
		VBox vbLeft = new VBox();
		vbLeft.setPadding(new Insets(15, 12, 15, 12));
		vbLeft.setSpacing(10);
		//vbLeft.setStyle("-fx-background-color: #336699;");
		
		Text leftTitle = new Text("Navagation");
		leftTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
	    vbLeft.getChildren().add(leftTitle);
	    
	    Hyperlink optionsLeft[] = new Hyperlink[] {
	    	new Hyperlink("Kiosk"),
	    	new Hyperlink("Kitchen")};

	        for (int i = 0; i < optionsLeft.length; i++) {
	            VBox.setMargin(optionsLeft[i], new Insets(0, 0, 0, 8));
	            vbLeft.getChildren().add(optionsLeft[i]);
	        }
	    
	    border.setLeft(vbLeft);
	    
	    
	    VBox vbRight = new VBox();
		vbRight.setPadding(new Insets(15, 12, 15, 12));
		vbRight.setSpacing(10);
		//vbRight.setStyle("-fx-background-color: #336699;");
		
		Text rightTitle = new Text("View DataBase");
		rightTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
	    vbRight.getChildren().add(rightTitle);
	    
	    Hyperlink optionsRight[] = new Hyperlink[] {
	    	new Hyperlink("Employees"),
	    	new Hyperlink("Items")};

	        for (int i = 0; i < optionsRight.length; i++) {
	            VBox.setMargin(optionsRight[i], new Insets(0, 0, 0, 8));
	            vbRight.getChildren().add(optionsRight[i]);
	        }
	    
	    border.setRight(vbRight);
	    
	    VBox vbCenter = new VBox();
	    vbCenter.setPadding(new Insets(10));
	    vbCenter.setSpacing(8);
	    
	    Text userCenter = new Text("User");
		userCenter.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
	    
	    HBox hbCenter1 = new HBox();
	    hbCenter1.setPadding(new Insets(15, 12, 15, 12));
		hbCenter1.setSpacing(10);
		//hbCenter1.setStyle("-fx-background-color: #336699;");
		Button btnAddUser = new Button("Add User");
		Button btnDeleteUser = new Button("Delete User");
		hbCenter1.getChildren().addAll(btnAddUser, btnDeleteUser);
		
		Text itemCenter = new Text("Item");
		itemCenter.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
		
		HBox hbCenter2 = new HBox();
	    hbCenter2.setPadding(new Insets(15, 12, 15, 12));
		hbCenter2.setSpacing(10);
		//hbCenter2.setStyle("-fx-background-color: #336699;");
		Button btnAddItem = new Button("Add Item");
		Button btnDeleteItem = new Button("Delete Item");
		Button btnUpdateItem = new Button("Change Item");
		
		btnAddItem.setOnAction(new EventHandler() {
			@Override
			public void handle(Event event) {
				try {
					addItem();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		hbCenter2.getChildren().addAll(btnAddItem, btnDeleteItem, btnUpdateItem);
		
		vbCenter.getChildren().addAll(userCenter, hbCenter1, itemCenter, hbCenter2);
		
		border.setCenter(vbCenter);
		
		
		
		//Dimensions are 1540, 790
		Scene scene = new Scene(border,550,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	/*
	public TextInputDialog td() {
		TextInputDialog td = new TextInputDialog("Enter Text");
		
	}
	 */
}
