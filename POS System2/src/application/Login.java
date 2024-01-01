package application;

import java.sql.*;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.css.*;

public class Login extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			initializeDB();
			
			primaryStage.setTitle("Login");
			
			GridPane grid = addGridPane();
			grid.setBackground(new Background(myBI()));
			
			
			
			Scene scene = new Scene(grid,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setFullScreen(true);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static GridPane addGridPane() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		HBox topHBox = new HBox();
		topHBox.setStyle("-fx-background-color: #FFFAFA;");
		Text sceneTitle = new Text("Welcome");
		topHBox.getChildren().add(sceneTitle);
		grid.add(topHBox, 0, 0);
		
		Label userName = new Label("User Name:");
		grid.add(userName, 0, 1);
		
		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);
		
		Label password = new Label("Password:");
		grid.add(password, 0, 2);
		
		PasswordField passwordField = new PasswordField();
		grid.add(passwordField, 1, 2);
		
		Button btn = new Button("Sign in");
		
		/*
		btn.setOnAction(new EventHandler() {
			@Override
			public void handle(Event event) {
				try {
					String query = "SELECT";
					PreparedStatement preparedStmt = conn.prepareStatement();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		*/
		
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 4);
		
		sceneTitle.setStyle("-fx-font: normal bold 20px 'serif' ");
		userName.setStyle("-fx-font: normal bold 14px 'serif' "); 
	    password.setStyle("-fx-font: normal bold 14px 'serif' ");
		
		return grid;
	}
	
	public BackgroundImage myBI() {
		BackgroundImage myBI= new BackgroundImage(new Image("http://i.imgur.com/cIGSehG.jpg",32,32,false,true),
		        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
		          BackgroundSize.DEFAULT);
		return myBI;
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
	
	public static void main(String[] args) {
		launch(args);
	}
}
