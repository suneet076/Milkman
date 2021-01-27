/**
 * Sample Skeleton for 'customerview.fxml' Controller Class
 */

package customer;

import java.awt.Label;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import connectdb.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class customerviewController {
	Connection con=DBConnection.doConnect();
	String ip;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtname"
    private TextField txtname; // Value injected by FXMLLoader

    @FXML // fx:id="txtmob"
    private TextField txtmob; // Value injected by FXMLLoader

    @FXML // fx:id="txtadd"
    private TextField txtadd; // Value injected by FXMLLoader

    @FXML // fx:id="imgview"
    private ImageView imgview; // Value injected by FXMLLoader

    @FXML // fx:id="txtqty1"
    private TextField txtqty1; // Value injected by FXMLLoader

    @FXML // fx:id="txtqty2"
    private TextField txtqty2; // Value injected by FXMLLoader

    @FXML // fx:id="txtpri1"
    private TextField txtpri1; // Value injected by FXMLLoader

    @FXML // fx:id="txtpri2"
    private TextField txtpri2; // Value injected by FXMLLoader

    @FXML // fx:id="dtpdos"
    private DatePicker dtpdos; // Value injected by FXMLLoader
    

    @FXML
    private TextField txtpath;

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
    void dobrowser(ActionEvent event) {
    	playSong();
    	FileChooser ch=new FileChooser();
        ch.setTitle("search file");
        File file=ch.showOpenDialog(new Stage());
        if(file!=null)
        {
        	try{
        	ip=file.getPath();
        	txtpath.setText(ip);
        	FileInputStream is=new FileInputStream(ip);
        	javafx.scene.image.Image image=new javafx.scene.image.Image(is);
        	imgview.setImage(image);
        	}
        	catch(Exception e)
        	{e.printStackTrace();}
        }
        else
        {
        	Alert al=new Alert(AlertType.CONFIRMATION);
    		al.setTitle("Choose Image");
    		al.setContentText("Select File Image");
    		al.setHeaderText("Choose");
    		al.show();
        }
    }

    @FXML
    void dofetch(ActionEvent event) {
    	playSong();
    	String name=String.valueOf(txtname.getText());
    	try{
    	PreparedStatement pst=con.prepareStatement("select * from customer where name=?");
        	pst.setString(1,name);
        	ResultSet table= pst.executeQuery();//one or zero possibility
        	boolean jasus=false;
        		while(table.next())
        		{
        			jasus=true;
        		    name=table.getString("name");
        			String mob=table.getString("Mobile");
        			String add=table.getString("address");
        			float cmq=table.getFloat("cmq");
        			float cmp=table.getFloat("cmp");
        			float bmq=table.getFloat("bmq");
        			float bmp=table.getFloat("bmp");
        			String path=table.getString("path");
        			LocalDate date=LocalDate.parse(table.getString("dos"));
        			txtname.setText(name);
        			txtmob.setText(String.valueOf(mob));
        			txtadd.setText(add);
        			txtqty1.setText(String.valueOf(cmq));
        			txtpri1.setText(String.valueOf(cmp));
        			txtqty2.setText(String.valueOf(bmq));
        			txtpri2.setText(String.valueOf(bmp));
        			dtpdos.setValue(date);
        			txtpath.setText(path);
        			FileInputStream is=new FileInputStream(path);
        	    	javafx.scene.image.Image image=new javafx.scene.image.Image(is);
        	    	imgview.setImage(image);
        		}
        		if(jasus==false)
        			System.out.println("Invalid productId");
        		
        	}
        	catch(Exception ex)
        	{
        		ex.printStackTrace();
        	}
    }
    
    @FXML
    void doDelete(ActionEvent event) {
    	playSong();
    	Alert confirm=new Alert(AlertType.CONFIRMATION);
    	confirm.setTitle("delete data");
    	confirm.setContentText("R U sure you want to delete all the data of this customer?");
    	Optional<ButtonType> res= confirm.showAndWait();
    	if(res.get()==ButtonType.OK)
    			dodelete();
    }
    void dodelete()
    {
    	String name=String.valueOf(txtname.getText());
		try {
		PreparedStatement pst=con.prepareStatement("delete from customer where name=?");//in parameters
		pst.setString(1,name);
		pst.executeUpdate();//fire query in table
		System.out.println("delete");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
    }

    
    @FXML
    void dosave(ActionEvent event) {
    	playSong();
    	Alert confirm=new Alert(AlertType.CONFIRMATION);
    	confirm.setTitle("Save data");
    	confirm.setContentText("R U sure?");
    	Optional<ButtonType> res= confirm.showAndWait();
    	if(res.get()==ButtonType.OK)
                 doSave();
    }
    
    void doSave()
	{
    	String name=String.valueOf(txtname.getText());
    	String mob=String.valueOf(txtmob.getText());
    	String address=String.valueOf(txtadd.getText());
    	float cmq=Float.valueOf(txtqty1.getText());
    	float cmp=Float.valueOf(txtpri1.getText());
    	float bmq=Float.valueOf(txtqty2.getText());
    	float bmp=Float.valueOf(txtpri2.getText());
        String path=String.valueOf(String.valueOf(txtpath.getText()));
    	LocalDate local=dtpdos.getValue();
    	java.sql.Date dos=	java.sql.Date.valueOf(local);
		try {
		PreparedStatement pst=	con.prepareStatement("insert into customer values(?,?,?,?,?,?,?,?,?)");//in parameters
		pst.setString(1,name);
		pst.setString(2,mob);
		pst.setString(3,address);
		pst.setFloat(4,cmq);
		pst.setFloat(5,cmp);
		pst.setFloat(6,bmq);
		pst.setFloat(7,bmp);
		pst.setDate(8, dos);
		pst.setString(9,path);
		pst.executeUpdate();//fire query in table
		System.out.println("Saved");
				
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
    @FXML
    void doupdate(ActionEvent event) {
    	String name=String.valueOf(txtname.getText());
    	String mob=String.valueOf(txtmob.getText());
    	String address=String.valueOf(txtadd.getText());
    	float cmq=Float.valueOf(txtqty1.getText());
    	float cmp=Float.valueOf(txtpri1.getText());
    	float bmq=Float.valueOf(txtqty2.getText());
    	float bmp=Float.valueOf(txtpri2.getText());
    	LocalDate local=dtpdos.getValue();
    	java.sql.Date dos=	java.sql.Date.valueOf(local);
    	String path=String.valueOf(txtpath.getText());
    	try {
		PreparedStatement pst=con.prepareStatement("update customer set Mobile=?,address=?,cmq=?,cmp=?,bmq=?,bmp=?,dos=?,path=? where name=?");//in parameters
		pst.setString(1,mob);
		pst.setString(2,address);
		pst.setFloat(3,cmq);
		pst.setFloat(4,cmp);
		pst.setFloat(5,bmq);
		pst.setFloat(6,bmp);
		pst.setDate(7, dos);
		pst.setString(8,path);
		pst.setString(9,name);
		pst.executeUpdate();//fire query in table
		System.out.println("update");
				
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        
    }
}
