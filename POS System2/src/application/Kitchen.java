package application;

import java.sql.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
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

public class Kitchen extends Application {
	protected static Statement stmt;
	
	protected static GridPane grid;
	protected static GridPane labelGrid;
	protected static ScrollPane scroll;
	protected static VBox vBox;
	protected static HBox hBox;
	
	protected static ArrayList<Button> buttonList = new ArrayList<Button>();
	
	protected static ArrayList<String> orderDate = new ArrayList<String>();
	protected static ArrayList<Boolean> orderComplete = new ArrayList<Boolean>();
	
	protected static ArrayList<Integer> orderId = new ArrayList<Integer>();
	protected static ArrayList<Integer> count = new ArrayList<Integer>();
	protected static ArrayList<String> itemName = new ArrayList<String>();
	
	protected static int nextOrderId;
	
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
	
	private static void getData() {
		String query = "SELECT order_id, HI.count, item_name "
				+ "FROM pos.order O "
				+ "	JOIN order_has_item HI "
				+ "		ON O.order_id = HI.order_order_id "
				+ "	JOIN item I "
				+ "		ON HI.item_item_id = I.item_id"
				+ "		WHERE O.complete = false";
		try {
			ResultSet result = stmt.executeQuery(query);
			while(result.next()) {
				orderId.add(result.getInt(1));
				count.add(result.getInt(2));
				itemName.add(result.getString(3));
			}
		} catch (SQLException e) {
			System.out.println("Menu not accessed");
			e.printStackTrace();
		}
	}
	
	protected static Label getLabel(String labelText) {
		Label label = new Label(labelText);
		label.setAlignment(Pos.CENTER);
		label.setFont(new Font("Ariel Black", 28));
		label.setPadding(new Insets(5));
		return label;
	}
	
	protected static void populateGridPane() {
		int prevId = -1;
		for (int i = 0; i < orderId.size(); i++) {
			if (orderId.get(i) != prevId) {
				
				grid.add(getLabel(Integer.toString(orderId.get(i))), 0, i);
				final int orderNum = orderId.get(i);
				grid.add(getCompleteButton(orderNum), 3, i);
				prevId = orderId.get(i);
			}
			
			grid.add(getLabel(Integer.toString(count.get(i))), 1, i);
			grid.add(getLabel(itemName.get(i)), 2, i);
		}
	}
	
	protected static Button getCompleteButton(int orderNum) {
		Button complete = new Button("Complete");
		complete.setStyle("-fx-font-size:25");
		String query = "UPDATE pos.order "
					 + "SET complete = 1 "
					 + "WHERE order_id = " + orderNum + ";";
		complete.setOnAction(e -> {
			try {
				stmt.executeUpdate(query);
				refreshList();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		return complete;
	}
	
	public static VBox getVBox() {
		VBox vb = new VBox();
		vb.setPadding(new Insets(15, 12, 15, 12));
		vb.setSpacing(10);
		
		return vb;
	}
	
	public static HBox getHBox() {
		HBox hb = new HBox();
		hb.setPadding(new Insets(15, 12, 15, 12));
		hb.setSpacing(10);
		
		return hb;
	}
	
	public static GridPane getGridPane() {
		GridPane gp = new GridPane();
        ColumnConstraints column1 = new ColumnConstraints(100);
        ColumnConstraints column2 = new ColumnConstraints(100);
        ColumnConstraints column3 = new ColumnConstraints(500);
        gp.getColumnConstraints().add(column1);
        gp.getColumnConstraints().add(column2);
        gp.getColumnConstraints().add(column3);


		gp.setPadding(new Insets(15, 12, 15, 12));
		gp.setHgap(12);
		gp.setVgap(15);
		
		return gp;
	}
	
	protected static void refreshList() {
		grid.getChildren().clear();
		orderId.clear();
		count.clear();
		itemName.clear();
		getData();
		populateGridPane();
	}
	
	protected static void setRefreshTimer() {
		
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					refreshList();
				});
			}
		};
		timer.scheduleAtFixedRate(task, 15000, 15000);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		initializeDB();
		
		getData();
		
		BorderPane border = new BorderPane();
		
		HBox topBanner = getHBox();
		topBanner.setStyle("-fx-background-color: #2E3E64;"
						 + "-fx-alignment: CENTER;");
		Label title = getLabel("Orders Waiting");
		title.setFont(Font.font("Segoe Script", FontWeight.BOLD, 40));
		title.setStyle("-fx-text-fill: WHITE;");
	    topBanner.getChildren().add(title);
		
		border.setTop(topBanner);
		
		labelGrid = getGridPane();
		labelGrid.setAlignment(Pos.CENTER);
		labelGrid.add(getLabel("Id"), 0, 0);
		labelGrid.add(getLabel("#"), 1, 0);
		labelGrid.add(getLabel("Items"), 2, 0);
		labelGrid.setPadding(new Insets(0, 165, 0, 0));
//		labelGrid.setStyle("-fx-background-color: #F40009;");
		
		grid = getGridPane();

		ScrollPane scroll = new ScrollPane(grid);
//		scroll.setPadding(new Insets(0, 0, 0, 300));
		scroll.setPrefWidth(1000);
		scroll.setMaxWidth(ScrollPane.USE_PREF_SIZE);
		scroll.setMinWidth(ScrollPane.USE_PREF_SIZE);
		vBox = getVBox();
		vBox.getChildren().addAll(labelGrid, scroll);
		VBox.setMargin(scroll, new Insets(0, 0, 0, 300));
		
		border.setCenter(vBox);
		populateGridPane();
		
		Scene scene = new Scene(border,700,500);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setFullScreen(true);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		setRefreshTimer();
	}

}
