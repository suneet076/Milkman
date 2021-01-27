/**
 * Sample Skeleton for 'paidview.fxml' Controller Class
 */
package billpaid;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import connectdb.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class paidviewController {
	Connection con=DBConnection.doConnect();
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="comboname"
    private ComboBox<String> comboname; // Value injected by FXMLLoader

    @FXML // fx:id="txtbmq"
    private TextField txtbmq; // Value injected by FXMLLoader

    @FXML // fx:id="txtcmq"
    private TextField txtcmq; // Value injected by FXMLLoader

    @FXML // fx:id="txtbill"
    private TextField txtbill; // Value injected by FXMLLoader

    @FXML // fx:id="txtdoe"
    private TextField txtdoe; // Value injected by FXMLLoader

    @FXML // fx:id="txtdos"
    private TextField txtdos; // Value injected by FXMLLoader

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
    void doexcel(ActionEvent event) {
    	playSong();
    	
    }
    @FXML
    void dofetch(ActionEvent event) {
    	playSong();
    	 String name=comboname.getSelectionModel().getSelectedItem();
 	    try{
 	        PreparedStatement	pst=con.prepareStatement("select * from bills where name=? && status=0");
 	        pst.setString(1,name);
 	       	ResultSet table= pst.executeQuery();
 	       	boolean jasus=false;
 	       		while(table.next())
 	       		{
 	       			jasus=true;
         			String dos=table.getString("dos");
         			String doe=table.getString("doe");
         			float tcmq=table.getFloat("tcmq");
         			float tbmq=table.getFloat("tbmq");
         			float amt=table.getFloat("amount");
         			txtcmq.setText(String.valueOf(tcmq));
        			txtbmq.setText(String.valueOf(tbmq));
        			txtdos.setText(String.valueOf(dos));
        			txtdoe.setText(String.valueOf(doe));
        			txtbill.setText(String.valueOf(amt));
 	       		}
 	       		if(jasus==false)
 	       		{Alert al=new Alert(AlertType.ERROR);
 	    		al.setTitle("Error");
 	    		al.setContentText("Bill Paid");
 	    		al.setHeaderText("Data Error");
 	    		al.show();}
 	    }
     	catch(Exception ex)
     	{
     		ex.printStackTrace();
     	}
    }

    @FXML
    void doreceived(ActionEvent event) {
    	playSong();
    	String name=comboname.getSelectionModel().getSelectedItem();
    	try {
    		PreparedStatement pst=con.prepareStatement("update bills set status=? where name=?");//in parameters
    		pst.setFloat(1,1);
    		pst.setString(2,name);
    		pst.executeUpdate();//fire query in table
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
