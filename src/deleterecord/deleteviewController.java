/**
 * Sample Skeleton for 'deleteview.fxml' Controller Class
 */

package deleterecord;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import connectdb.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class deleteviewController {
	Connection con=DBConnection.doConnect();
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="comboname"
    private ComboBox<String> comboname; // Value injected by FXMLLoader

    @FXML // fx:id="txtdos"
    private TextField txtdos; // Value injected by FXMLLoader

    @FXML // fx:id="txtmob"
    private TextField txtmob; // Value injected by FXMLLoader

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
    void dodelete(ActionEvent event) {
    	playSong();
    	String name=comboname.getSelectionModel().getSelectedItem();
    	String mob=txtmob.getText();
    	String dos=txtdos.getText();
		try {
		PreparedStatement pst=con.prepareStatement("delete from customer where name=? && Mobile=? && dos=?");//in parameters
		pst.setString(1,name);
		pst.setString(2,mob);
		pst.setString(3,dos);
		pst.executeUpdate();//fire query in table
		System.out.println("delete");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		try {
			PreparedStatement pst=con.prepareStatement("delete from bills where name=?");//in parameters
			pst.setString(1,name);
			pst.executeUpdate();//fire query in table
			System.out.println("delete");
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		try {
			PreparedStatement pst=con.prepareStatement("delete from routine where name=?");//in parameters
			pst.setString(1,name);
			pst.executeUpdate();//fire query in table
			System.out.println("delete");
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
    }

    void doload()
    {
    comboname.getItems().clear();
    try{
    	PreparedStatement pst=con.prepareStatement("select name from customer");
    	ResultSet table=pst.executeQuery();
    	while(table.next())
    	{
    		String name=table.getString("name");
    		comboname.getItems().add(name);
    	}
    }
    catch(SQLException e)
    {
    e.printStackTrace();	
    }
    }
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	doload();
    }
}
