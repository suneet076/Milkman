/**
 * Sample Skeleton for 'tableview.fxml' Controller Class
 */

package table;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import com.mysql.jdbc.Connection;
import table.routineBean;
import connectdb.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
public class tableviewController<ExcelWorksheet> {
	Connection con;
	 @FXML // fx:id="txtdos"
	    private TextField txtdos; // Value injected by FXMLLoader

	    @FXML // fx:id="txtdoe"
	    private TextField txtdoe; // Value injected by FXMLLoader

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="tbl"
    private TableView<routineBean> tbl; // Value injected by FXMLLoader

    @FXML
    private ComboBox<String> comboname;
    URL url;
   	Media media;
   	MediaPlayer mediaplayer;
    void playsound()
    {
    	
    	url=getClass().getResource("Sound.mp3");
		media=new Media(url.toString());
		mediaplayer=new MediaPlayer(media);
		mediaplayer.play();
		
    }
    @FXML
    void docomboname(ActionEvent event) {
    	playsound();
    }
    @FXML
    void dofetchone(ActionEvent event) {
    	playsound();
    String name=comboname.getSelectionModel().getSelectedItem();
    String dos=txtdos.getText();
    String doe=txtdoe.getText();
    ObservableList<routineBean> lst=FXCollections.observableArrayList();
    try{
        PreparedStatement	pst=con.prepareStatement("select * from routine where name=? and date>=? && date<=?");
        pst.setString(1,name);
        pst.setString(2,dos);
        pst.setString(3,doe);
       	ResultSet table= pst.executeQuery();
       	boolean jasus=false;
       		while(table.next())
       		{
       			jasus=true;
       			String nam=table.getString("name");//col. name acc. to table
       			float cmv=table.getFloat("cmq");
       			float bmv=table.getFloat("bmq");
       			String date=table.getString("dos");
       			routineBean rbo=new routineBean(nam,cmv,bmv,date);
       		    lst.add(rbo);
       		}
       		if(jasus==false)
       		{Alert al=new Alert(AlertType.ERROR);
    		al.setTitle("Error");
    		al.setContentText("Invalid name");
    		al.setHeaderText("Data Error");
    		al.show();}
       	}
       	catch(Exception ex)
       	{
       		ex.printStackTrace();
       	}
    TableColumn<routineBean, String> nam=new TableColumn<routineBean, String>("Customer Name");//Dikhava Title
	nam.setCellValueFactory(new PropertyValueFactory<>("name"));//bean field name, no link with table col name

	TableColumn<routineBean, Float> cmv=new TableColumn<routineBean, Float>("customer milk varation");//Dikhava Title
	cmv.setCellValueFactory(new PropertyValueFactory<>("cmv"));//bean field name, no link with table col name
	
	TableColumn<routineBean, Float> bmv=new TableColumn<routineBean, Float>("buffalo milk varation");//Dikhava Title
	bmv.setCellValueFactory(new PropertyValueFactory<>("bmv"));//bean field name, no link with table col name
	
	TableColumn<routineBean, String> doss=new TableColumn<routineBean, String>("date of varation");//Dikhava Title
	doss.setCellValueFactory(new PropertyValueFactory<>("dos"));//bean field name, no link with table col name
	
	tbl.getColumns().clear();
 	tbl.getColumns().addAll(nam,cmv,bmv,doss);
	tbl.setItems(lst);
    }
    @FXML
    void dofetchall(ActionEvent event) {
    	playsound();
      	TableColumn<routineBean, String> name=new TableColumn<routineBean, String>("Customer Name");//Dikhava Title
    	name.setCellValueFactory(new PropertyValueFactory<>("name"));//bean field name, no link with table col name

    	TableColumn<routineBean, Float> cmv=new TableColumn<routineBean, Float>("customer milk varation");//Dikhava Title
    	cmv.setCellValueFactory(new PropertyValueFactory<>("cmv"));//bean field name, no link with table col name
    	
    	TableColumn<routineBean, Float> bmv=new TableColumn<routineBean, Float>("buffalo milk varation");//Dikhava Title
    	bmv.setCellValueFactory(new PropertyValueFactory<>("bmv"));//bean field name, no link with table col name
    	
    	TableColumn<routineBean, String> dos=new TableColumn<routineBean, String>("date of varation");//Dikhava Title
    	dos.setCellValueFactory(new PropertyValueFactory<>("dos"));//bean field name, no link with table col name
    	
    	tbl.getColumns().clear();
     	tbl.getColumns().addAll(name,cmv,bmv,dos);
    	tbl.setItems(list);
    }
    ObservableList<routineBean> list;
    
    @FXML
    void dosave(ActionEvent event) {
    	playsound();
    	Workbook wb=new HSSFWorkbook();
    	Sheet ss=wb.createSheet("variation");
    	Row row=ss.createRow(0);
    	for(int j=0;j<tbl.getColumns().size();j++)
    	{
    		row.createCell(j).setCellValue(tbl.getColumns().get(j).getText());
    	}
    	for(int i=0;i<tbl.getItems().size();i++)
    	{
    		row=ss.createRow(i+1);
    		for(int j=0;j<tbl.getColumns().size();j++)
    		{
    			if(tbl.getColumns().get(j).getCellData(i)!=null)
    			{
      				row.createCell(j).setCellValue(tbl.getColumns().get(j).getCellData(i).toString());
    			}
    			else
    			{
    				row.createCell(j).setCellValue("");
    			}
    		}
    	}
    	FileOutputStream fo;
    	try{
    	FileChooser ch=new FileChooser();
    	File  sf=ch.showSaveDialog(null);
    	ch.getExtensionFilters().addAll(new ExtensionFilter("Excel","*.xls"));
    	if(sf!=null)
    	{
    		fo=new FileOutputStream(sf.getName());
    		wb.write(fo);
    		fo.close();
    		System.out.println("export");
    	}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    void fetchAllRecords()
    {
    	 list=FXCollections.observableArrayList();
    	try{
         PreparedStatement	pst=con.prepareStatement("select * from routine");
        	ResultSet table= pst.executeQuery();
        		
        		while(table.next())
        		{
        			String name=table.getString("name");//col. name acc. to table
        			float cmv=table.getFloat("cmq");
        			float bmv=table.getFloat("bmq");
        			String date=table.getString("dos");
        			routineBean rb=new routineBean(name,cmv,bmv,date);
        			list.add(rb);
        		}
        	}
        	catch(Exception ex)
        	{
        		ex.printStackTrace();
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
    	con=(Connection) DBConnection.doConnect();
    	fetchAllRecords();
    	doload();
    }
}
