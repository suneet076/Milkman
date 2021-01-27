/**
 * Sample Skeleton for 'dashView.fxml' Controller Class
 */

package dashboard;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class dashViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    URL url;
   	Media media;
   	MediaPlayer mediaplayer;
   	AudioClip audio;
    void playSound(){
       	url=getClass().getResource("crash.wav");
   		audio=new AudioClip(url.toString());
   		audio.play();
       }
    @FXML
    void dobill(MouseEvent event) {
    	playSound();
    	try {
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("bill/billview.fxml")); 
			Scene scene = new Scene(root,600,600);
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.show();
			 //scene.getWindow().hide();
	    } 
	catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void dobilltable(MouseEvent event) {
    	playSound();
    	try {
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("tablebill/tableview.fxml")); 
			Scene scene = new Scene(root,500,525);
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.show();
			 //scene.getWindow().hide();
	    } 
	catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void docustomerhistory(MouseEvent event) {
    	playSound();
    	try {
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("customerhistory/historyview.fxml")); 
			Scene scene = new Scene(root,500,600);
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.show();
			 //scene.getWindow().hide();
	    } 
	catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void dodelete(MouseEvent event) {
    	playSound();
    	try {
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("deleterecord/deleteview.fxml")); 
			Scene scene = new Scene(root,500,500);
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.show();
			 //scene.getWindow().hide();
	    } 
	catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void dopaidtable(MouseEvent event) {
    	playSound();
    	try {
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("billpaidtable/bptview.fxml")); 
			Scene scene = new Scene(root,500,525);
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.show();
			 //scene.getWindow().hide();
	    } 
	catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void dopayment(MouseEvent event) {
    	playSound();
    	try {
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("billpaid/paidview.fxml")); 
			Scene scene = new Scene(root,500,525);
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.show();
			 //scene.getWindow().hide();
	    } 
	catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void doregister(MouseEvent event) {
    	playSound();
    	try {
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("customer/customerview.fxml")); 
			Scene scene = new Scene(root,500,600);
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.show();
			 //scene.getWindow().hide();
	    } 
	catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void doroutine(MouseEvent event) {
    	playSound();
    	try {
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("routine/routineview.fxml")); 
			Scene scene = new Scene(root,500,525);
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.show();
			 //scene.getWindow().hide();
	    } 
	catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML
    void dovartable(MouseEvent event) {
    	playSound();
    	try {
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("table/tableview.fxml")); 
			Scene scene = new Scene(root,500,525);
			Stage primaryStage=new Stage();
			primaryStage.setScene(scene);
			primaryStage.show();
			 //scene.getWindow().hide();
	    } 
	catch(Exception e)
		{
			e.printStackTrace();
		}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

    }
}
