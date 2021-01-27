package passkey;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import connectdb.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class passviewController{

	   @FXML
	    private ProgressBar pd;
	   
	@FXML
    private Button btnlogin;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtpass"
    private PasswordField txtpass; // Value injected by FXMLLoader

    @FXML // fx:id="txtname"
    private TextField txtname; // Value injected by FXMLLoader
    Connection con=DBConnection.doConnect();
    URL url;
	Media media;
	MediaPlayer mediaplayer;
   class progress implements Runnable
{
	public void run()
	{
		for(int i=0;i<100;i++)
		{
			pd.setProgress(i/100.0);
			try{Thread.sleep(100);}
			catch(Exception e){e.printStackTrace();}
		}
	}
}
	void playSong() {

		url = getClass().getResource("Sound.mp3");
		media = new Media(url.toString());
		mediaplayer = new MediaPlayer(media);
		mediaplayer.play();

	}
    @FXML
    void dologin(ActionEvent event) {
    	playSong();
    	try{
        	PreparedStatement pst=con.prepareStatement("select * from user");
            	ResultSet table= pst.executeQuery();//one or zero possibility
            	boolean jasus=false;
            		while(table.next())
            		{
            			jasus=true;
            			String uname=table.getString("name");
            			String pass=table.getString("password");
            			if(txtname.getText().equals(uname) && txtpass.getText().equals(pass))
            			{
            				progress obj=new progress();
            				Thread th1=new Thread(obj);
            				th1.start();
            				try{
            					th1.join(1600);
            				}
            				catch(Exception e){e.printStackTrace();}
            				try {
            					Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("dashboard/dashView.fxml")); 
            					Scene scene = new Scene(root,610,700);
            					Stage primaryStage=new Stage();
            					primaryStage.setScene(scene);
            					primaryStage.show();
            					Scene s1=btnlogin.getScene();
            					 s1.getWindow().hide();
            			    } 
            			catch(Exception e)
            				{
            					e.printStackTrace();
            				}
            			}
            			else
            			{Alert confirm=new Alert(AlertType.ERROR);
            	    	confirm.setTitle("Selected customer");
            	    	confirm.setContentText("Invalid data");
            	    	confirm.showAndWait();}
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

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        
    }
}
