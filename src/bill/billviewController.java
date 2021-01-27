/**
 * Sample Skeleton for 'billview.fxml' Controller Class
 */

package bill;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import connectdb.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class billviewController {
	Connection con = DBConnection.doConnect();
	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="lstname"
	private ListView<String> lstname; // Value injected by FXMLLoader

	@FXML // fx:id="txtdod"
	private TextField txtdod; // Value injected by FXMLLoader

	@FXML // fx:id="txtdos"
	private TextField txtdos; // Value injected by FXMLLoader

	@FXML // fx:id="txtdoe"
	private TextField txtdoe; // Value injected by FXMLLoader

	@FXML // fx:id="txtcmq"
	private TextField txtcmq; // Value injected by FXMLLoader

	@FXML // fx:id="txtbmq"
	private TextField txtbmq; // Value injected by FXMLLoader

	@FXML // fx:id="txtbmp"
	private TextField txtbmp; // Value injected by FXMLLoader

	@FXML // fx:id="txtcmp"
	private TextField txtcmp; // Value injected by FXMLLoader

	@FXML // fx:id="txtcmqr"
	private TextField txtcmqr; // Value injected by FXMLLoader

	@FXML // fx:id="txtbmqr"
	private TextField txtbmqr; // Value injected by FXMLLoader

	@FXML // fx:id="txtamt"
	private TextField txtamt; // Value injected by FXMLLoader

	URL url;
	Media media;
	MediaPlayer mediaplayer;

	void playSong() {

		url = getClass().getResource("Sound.mp3");
		media = new Media(url.toString());
		mediaplayer = new MediaPlayer(media);
		mediaplayer.play();

	}

	@FXML
	void dobill(ActionEvent event) {
		playSong();
		float cmr=0;
		float bmr=0;
		float cma = (((Float.valueOf(txtdod.getText())) * (Float.valueOf(txtcmp.getText()))));
		float bma = (((Float.valueOf(txtdod.getText())) * (Float.valueOf(txtbmp.getText()))));
		if(Float.valueOf(txtcmq.getText())==0)
		{
			cmr=0;
		}
		else if(Float.valueOf(txtcmq.getText())!=0)
		{
		 cmr = ((Float.valueOf(txtcmqr.getText())) * (Float.valueOf(txtcmp.getText())))/(Float.valueOf(txtcmq.getText()));
		}
		if(Float.valueOf(txtbmq.getText())==0)
		{bmr=0;}
		else if(Float.valueOf(txtbmq.getText())!=0)
		{
		 bmr = (Float.valueOf(txtbmqr.getText()) * (Float.valueOf(txtbmp.getText())))/(Float.valueOf(txtbmq.getText()));
		}
		float tot = cma + bma + cmr+bmr ;
		txtamt.setText(String.valueOf(tot));
	}

	@FXML
	void dodays(ActionEvent event) {
		playSong();
		LocalDate dos = LocalDate.parse(String.valueOf(txtdos.getText()));
		LocalDate doe = LocalDate.parse(String.valueOf(txtdoe.getText()));
		long dod = ChronoUnit.DAYS.between(dos, doe);
		txtdod.setText(String.valueOf(dod));
	}
    String mob;
	@FXML
	void domessage(ActionEvent event) {
		playSong();
		String resp=Sms.SST_SMS.bceSunSoftSend(mob,"Amount:"+txtamt.getText());
    	if(resp.contains("successfully"))
			System.out.println("Sent...");
	else
		if(resp.contains("Unknown"))
			System.out.println("Check Internet connection");
		else
			System.out.println("Invalid Mobile Number");
		
	}

	@FXML
	void dosave(ActionEvent event) {
		playSong();
		String name = String.valueOf(lstname.getSelectionModel().getSelectedItem());
		float tcmq = Float.valueOf(txtcmqr.getText())
				+ Float.valueOf(txtcmq.getText()) * Float.valueOf(txtdod.getText());
		float tbmq = Float.valueOf(txtbmqr.getText())
				+ Float.valueOf(txtbmq.getText()) * Float.valueOf(txtdod.getText());
		LocalDate local = LocalDate.parse(String.valueOf(txtdos.getText()));
		java.sql.Date dos = java.sql.Date.valueOf(local);
		LocalDate loc = LocalDate.parse(String.valueOf(txtdoe.getText()));
		java.sql.Date doe = java.sql.Date.valueOf(loc);
		float tot = Float.valueOf(txtamt.getText());
		try {
			PreparedStatement pst = con.prepareStatement("insert into bills values(?,?,?,?,?,?,0)");// in
																									// parameters
			pst.setString(1, name);
			pst.setDate(2, dos);
			pst.setDate(3, doe);
			pst.setFloat(4, tcmq);
			pst.setFloat(5, tbmq);
			pst.setFloat(6, tot);
			pst.executeUpdate();// fire query in table
			Alert confirm = new Alert(AlertType.CONFIRMATION);
			confirm.setTitle("Saved");
			confirm.setContentText("Record is saved");
			confirm.showAndWait();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void doselect(MouseEvent event) {
		playSong();
		if (event.getClickCount() == 1) {
			String name = String.valueOf(lstname.getSelectionModel().getSelectedItem());
			try {
				PreparedStatement pst = con.prepareStatement("select * from customer where name=?");
				pst.setString(1, name);
				ResultSet table = pst.executeQuery();// one or zero possibility
				boolean jasus = false;
				while (table.next()) {
					jasus = true;
					float cmq = table.getFloat("cmq");
					float bmq = table.getFloat("bmq");
					float cmp = table.getFloat("cmp");
					float bmp = table.getFloat("bmp");
					      mob=table.getString("Mobile");
					txtcmq.setText(String.valueOf(cmq));
					txtbmq.setText(String.valueOf(bmq));
					txtcmp.setText(String.valueOf(cmp));
					txtbmp.setText(String.valueOf(bmp));
				}
				if (jasus == false) 
				{
					Alert confirm = new Alert(AlertType.ERROR);
					confirm.setTitle("Selected customer");
					confirm.setContentText("Invalid data");
					confirm.showAndWait();
				}
			}
			catch (Exception ex) 
			{
				ex.printStackTrace();
			}
		}
	}

	@FXML
	void dovariation(ActionEvent event) {
		playSong();
		String name = String.valueOf(lstname.getSelectionModel().getSelectedItem());
		String dos = txtdos.getText();
		String doe = txtdoe.getText();
		try {
			PreparedStatement pst = con.prepareStatement("select sum(cmq) as 'cmq',sum(bmq) as 'bmq' from routine where name=? and dos>=? && dos<=?");
			pst.setString(1, name);
			pst.setString(2, dos);
			pst.setString(3, doe);
			ResultSet table = pst.executeQuery();
			boolean jasus = false;
			while (table.next()) {
				jasus = true;
				float cmq = table.getFloat("cmq");
				float bmq = table.getFloat("bmq");
				txtcmqr.setText(String.valueOf(cmq));
				txtbmqr.setText(String.valueOf(bmq));
			}
			if (jasus == false) {
				Alert confirm = new Alert(AlertType.ERROR);
				confirm.setTitle("Selected customer");
				confirm.setContentText("Invalid data");
				confirm.showAndWait();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void doload() {
		lstname.getItems().clear();
		try {
			PreparedStatement pst = con.prepareStatement("select name from customer");
			ResultSet table = pst.executeQuery();
			while (table.next()) {
				String name = table.getString("name");
				lstname.getItems().add(name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML // This method is called by the FXMLLoader when initialization is
			// complete
	void initialize() {
		doload();
	}
}
