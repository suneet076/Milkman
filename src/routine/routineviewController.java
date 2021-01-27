/**
 * Sample Skeleton for 'routineview.fxml' Controller Class
 */

package routine;

import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import connectdb.DBConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class routineviewController {
	Connection con=DBConnection.doConnect();
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="lstname"
    private ListView<String> lstname; // Value injected by FXMLLoader

    @FXML // fx:id="txtcmq"
    private TextField txtcmq; // Value injected by FXMLLoader

    @FXML // fx:id="txtbmq"
    private TextField txtbmq; // Value injected by FXMLLoader

    @FXML // fx:id="dtpdoc"
    private DatePicker dtpdoc; // Value injected by FXMLLoader

    URL url;
   	Media media;
   	MediaPlayer mediaplayer;
       void playSong()
       {
       	
       	url=getClass().getResource("Sound.mp3");
   		media=new Media(url.toString());
   		mediaplayer=new MediaPlayer(media);
   		mediaplayer.play();
       }
       
    @FXML
    void dodelete(ActionEvent event) {
    	playSong();
    ObservableList<String> si=lstname.getSelectionModel().getSelectedItems();	
    lstname.getItems().retainAll(si);
    }

    @FXML
    void dosave(ActionEvent event) {
    	playSong();
    	String name=String.valueOf(lstname.getSelectionModel().getSelectedItem());
    	float cmq=Float.valueOf(txtcmq.getText());
    	float bmq=Float.valueOf(txtbmq.getText());
    	LocalDate local=dtpdoc.getValue();
    	java.sql.Date dos=	java.sql.Date.valueOf(local);
		try {
		PreparedStatement pst=	con.prepareStatement("insert into routine values(?,?,?,?)");//in parameters
		pst.setString(1,name);
		pst.setFloat(2,cmq);
		pst.setFloat(3,bmq);
		pst.setDate(4, dos);
		pst.executeUpdate();//fire query in table
		Alert confirm=new Alert(AlertType.CONFIRMATION);
    	confirm.setTitle("Saved");
    	confirm.setContentText("Record is saved");
    	confirm.showAndWait();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
    }
    
    @FXML
    void doSelect(MouseEvent event) {
    	playSong();
    if(event.getClickCount()==1)
    {
    	lstname.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
    if(event.getClickCount()==2)
    {
    	String name=String.valueOf(lstname.getSelectionModel().getSelectedItem());
    	try{
    	PreparedStatement pst=con.prepareStatement("select * from customer where name=?");
        	pst.setString(1,name);
        	ResultSet table= pst.executeQuery();//one or zero possibility
        	boolean jasus=false;
        		while(table.next())
        		{
        			jasus=true;
        			float cmq=table.getFloat("cmq");
        			float bmq=table.getFloat("bmq");
        			LocalDate date=LocalDate.parse(table.getString("dos"));
        			txtcmq.setText(String.valueOf(cmq));
        			txtbmq.setText(String.valueOf(bmq));
        			dtpdoc.setValue(date);
        		}
        		if(jasus==false)
        		{
        			Alert confirm=new Alert(AlertType.ERROR);
        	    	confirm.setTitle("Selected customer");
        	    	confirm.setContentText("Invalid data");
        	    	confirm.showAndWait();
        		}
        		
        	}
        	catch(Exception ex)
        	{
        		ex.printStackTrace();
        	}
    }
    }
    
void doload()
{
lstname.getItems().clear();
try{
	PreparedStatement pst=con.prepareStatement("select name from customer");
	ResultSet table=pst.executeQuery();
	while(table.next())
	{
		String name=table.getString("name");
		lstname.getItems().add(name);
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
