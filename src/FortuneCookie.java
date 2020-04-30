/*
 * @author Joel Delgado
 * Fortune Cookie App 2020-05-30
 * @version 1.0.0
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Fortune Cookie App.
 * Class creates the GUI and contains the list of fortunes.
 */
public class FortuneCookie extends Application {
	private List<String> fortunes = new ArrayList<>();
	private FlowPane top;
	private FlowPane bottom;
	private VBox vbox;
	private Button btCookie;
	private Button btCrumb;
	private Button btNewCookie;
	private Button btOpenCookie;
	private Label lbFortune;

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		Image image = new Image("images/bakground_img.jpg");
		root.getChildren().add(new ImageView(image));

		populateFortuneList();

		top = new FlowPane();
		btCookie = new Button("", new ImageView("images/fortune_cookie_new.jpg"));
		btCookie.setStyle("-fx-background-color: rgb(253,158,0)");
		top.getChildren().add(btCookie);
		top.setPadding(new Insets(40, 10, 10, 10));
		top.setAlignment(Pos.CENTER);

		vbox = new VBox(createCenterPane());
		vbox.setAlignment(Pos.CENTER);

		bottom = new FlowPane(createBottomPane());
		bottom.setAlignment(Pos.CENTER);

		btCrumb = new Button("", new ImageView("images/crumbs.png"));
		btOpenCookie = new Button("", new ImageView("images/fortune_cookie_open.jpg"));
		btOpenCookie.setStyle("-fx-background-color: transparent");

		btCookie.setOnAction(new openCookie());
		btNewCookie.setOnAction(new NewCookie());

		root.setTop(top);
		root.setCenter(vbox);
		root.setBottom(bottom);

		Scene scen = new Scene(root, 325, 500);
		primaryStage.setScene(scen);
		primaryStage.setTitle("Fortune Cookie");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	

	/**
	 * Creates the complete center Pane which is a VBox with a label.
	 * @return complete vBox
	 */
	private VBox createCenterPane() {
		vbox = new VBox();
		lbFortune = new Label("");
		lbFortune.setAlignment(Pos.CENTER);
		lbFortune.setFont(Font.font("", FontWeight.BOLD, 16));
		lbFortune.setStyle("-fx-text-inner-color: gold");
		vbox.getChildren().add(lbFortune);
		vbox.setPadding(new Insets(10));
		vbox.setAlignment(Pos.CENTER);
		vbox.setPrefWidth(400);
		return vbox;
	}
	

	/**
	 * Creates the bottom pane which is a pane containing a button.
	 * @return complete Pane
	 */
	private Pane createBottomPane() {
		Pane bottomPane = new Pane();
		btNewCookie = new Button("New Cookie");
		btNewCookie.setFont(Font.font("", FontWeight.EXTRA_BOLD, 15));
		bottomPane.getChildren().add(btNewCookie);
		bottomPane.setPadding(new Insets(10, 10, 40, 10));
		btNewCookie.setDisable(true);
		return bottomPane;
	}

	
	/**
	 * Reads the fortunes from a text file and transfers them to a list.
	 * Changes all # to <code>/n</code>-character for optimal display on the pane.
	 * And finally shuffles the list.
	 * @throws FileNotFoundException
	 */
	private void populateFortuneList() throws FileNotFoundException {
		Scanner in = new Scanner(new File("fortunes.txt"));
		while (in.hasNext()) {
			String textLine = in.nextLine();
			textLine = textLine.replace("#", "\n");
			fortunes.add(textLine);
		}
		shuffleFortuneList();
	}

	
	/**
	 * Shuffles the list with fortunes.
	 */
	private void shuffleFortuneList() {
		Collections.shuffle(fortunes);
	}
	

	/**
	 * Class handles what happens when a cookie is opened.
	 */
	private class openCookie implements EventHandler<ActionEvent> {

		/**
		 * Handles what happens when a user clicks on cookie button
		 * Checks to see if there are any cookies left:
		 * if <code>false</code> buttons are disabled and Cookie-button is changed to Crumble-button
		 * if <code>true</code>  Cookie-button is changed to OpenCookie-button, a fortune is displayed
		 * where the label is situated, and the NewCookie-button is enabled.
		 */
		@Override
		public void handle(ActionEvent event) {
			if (fortunes.isEmpty()) {
				top.getChildren().remove(btCookie);
				top.getChildren().add(btCrumb);
				btCrumb.setDisable(true);
				lbFortune.setText("OMG!!!\nYou ate all the cookies!\nNo more cookies for you!\nGo home!");
			} else {
				top.getChildren().remove(btCookie);
				top.getChildren().add(btOpenCookie);
				int lastIndex = fortunes.size() - 1;
				lbFortune.setText(fortunes.get(lastIndex));
				lbFortune.setTextFill(Color.GOLD);
				fortunes.remove(lastIndex);
				btNewCookie.setDisable(false);
				btNewCookie.setDefaultButton(true);
			}
		}
	}
	

	/**
	 * Class handles what happens when new-cookie-button is pressed
	 */
	private class NewCookie implements EventHandler<ActionEvent> {
		/**
		* When NewCookie-button is pressed it gets dissabled, the OpenCookie-button
		* is removed from the pane and the Cookie-button is readded. All text deleted
		* from label.
		*/
		public void handle(ActionEvent event) {
			btNewCookie.setDisable(true);
			top.getChildren().remove(btOpenCookie);
			top.getChildren().add(btCookie);
			lbFortune.setText("");
		}
	}

	
	public static void main(String[] args) {
		launch(args);
	}
}
