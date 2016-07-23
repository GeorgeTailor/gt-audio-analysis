package com.gt.user;

import java.io.File;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class UserInterface extends Application {
	
	private static boolean screenAdjustment = false;
	private String filePath = null;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("GT audio analysis");
        
        primaryStage.show();
        if(!screenAdjustment){
        	Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth())*1.4); 
            primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()));
            screenAdjustment = true;
        }       
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 500, 350);
        //scene.getStylesheets().add(UserInterface.class.getResource("/target/classes/main.css").toExternalForm());
        scene.getStylesheets().add(UserInterface.class.getResource("/src/main/resources/main.css").toExternalForm());
        primaryStage.setScene(scene);
        
        Text scenetitle = new Text("GT audio analysis");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scenetitle.setTextAlignment(TextAlignment.CENTER);
        grid.add(scenetitle, 0, 0, 2, 1);
        scenetitle.setId("welcome-text");
        
        final FileChooser fileChooser = new FileChooser();      
        final Button openButton = new Button("Load an audio file...");
        openButton.setMinWidth(180);
        HBox hbBtnFile = new HBox(10);
        hbBtnFile.setAlignment(Pos.CENTER);
        hbBtnFile.getChildren().add(openButton);
        grid.add(hbBtnFile, 1, 1);
        
        Button btnQuit = new Button("Quit");
        btnQuit.setMinWidth(180);
        HBox hbBtnQuit = new HBox(10);
        hbBtnQuit.setAlignment(Pos.CENTER);
        hbBtnQuit.getChildren().add(btnQuit);
        grid.add(hbBtnQuit, 1, 2);
        
        final Text actiontarget = new Text();
        actiontarget.setTextAlignment(TextAlignment.RIGHT);
        grid.add(actiontarget, 1, 6);
        final Text actiontarget1 = new Text();
        grid.add(actiontarget1, 1, 7);
        
        btnQuit.setOnAction(new EventHandler<ActionEvent>() {      	 
            @Override
            public void handle(ActionEvent e)  {
            	try {
					stop();
					System.exit(0);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
            }
        });
        final Label label = new Label("Finding notes...");
        final ProgressBar progressBar = new ProgressBar(0);
        final HBox hb = new HBox();
        hb.setSpacing(5);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(label, progressBar);
        hb.setVisible(false);
        grid.add(hb, 1, 4);
        openButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        File file = fileChooser.showOpenDialog(primaryStage);
                        if (file != null) {
                        	hb.setVisible(true);
                        	progressBar.setProgress(0);
                        	actiontarget1.setText(null);
                        	filePath = file.getAbsolutePath();
                    		try {                               
                    			NoteFinderThread noteFinderThread = new NoteFinderThread(filePath);
                    			new Thread(noteFinderThread).start();
                    			String result = noteFinderThread.getValue();
                    			hb.setVisible(false);
                    			actiontarget1.setFill(Color.ANTIQUEWHITE);
                    			actiontarget1.setText(result);
                    		} catch (Exception e1) {
                    			actiontarget.setFill(Color.FIREBRICK);
                    			actiontarget.setText(e1.getMessage());
                    		}
                        }
                    }
                });
	}
	
	public static void main(String[] args) throws Exception {
		launch(args);
	}
}
