/**
 * Sample Skeleton for 'historyview.fxml' Controller Class
 */

package customerhistory;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import connectdb.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import customerhistory.customerBean;

public class historyviewController {
	Connection con=DBConnection.doConnect();
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    @FXML
    private ToggleGroup tog;
    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="dtpdos"
    private DatePicker dtpdos; // Value injected by FXMLLoader

    @FXML // fx:id="radcow"
    private RadioButton radcow; // Value injected by FXMLLoader

    @FXML // fx:id="radbuff"
    private RadioButton radbuff; // Value injected by FXMLLoader

    @FXML // fx:id="btnfind"
    private Button btnfind; // Value injected by FXMLLoader

    @FXML // fx:id="tbl"
    private TableView<customerBean> tbl; // Value injected by FXMLLoader

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
    void doexport(ActionEvent event) {
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

    @FXML
    void dofetch(ActionEvent event) {
    	playsound();
    	LocalDate local=dtpdos.getValue();
    	java.sql.Date dos=	java.sql.Date.valueOf(local);
        ObservableList<customerBean> lst=FXCollections.observableArrayList();
        try{
            PreparedStatement	pst=con.prepareStatement("select * from customer where dos<=?");
            pst.setDate(1,dos);
           	ResultSet table= pst.executeQuery();
           	boolean jasus=false;
           		while(table.next())
           		{
           			jasus=true;
           			String nam=table.getString("name");//col. name acc. to table
           			float cmv=table.getFloat("cmq");
           			float bmv=table.getFloat("bmq");
           			String date=table.getString("dos");
           			customerBean cb=new customerBean(nam,cmv,bmv,date);
           		    lst.add(cb);
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
        TableColumn<customerBean, String> nam=new TableColumn<customerBean, String>("Customer Name");//Dikhava Title
    	nam.setCellValueFactory(new PropertyValueFactory<>("name"));//bean field name, no link with table col name

    	TableColumn<customerBean, Float> cmv=new TableColumn<customerBean, Float>("customer milk Quantity");//Dikhava Title
    	cmv.setCellValueFactory(new PropertyValueFactory<>("cmq"));//bean field name, no link with table col name
    	
    	TableColumn<customerBean, Float> bmv=new TableColumn<customerBean, Float>("buffalo milk Quantity");//Dikhava Title
    	bmv.setCellValueFactory(new PropertyValueFactory<>("bmq"));//bean field name, no link with table col name
    	
    	TableColumn<customerBean, String> doss=new TableColumn<customerBean, String>("date of Starting");//Dikhava Title
    	doss.setCellValueFactory(new PropertyValueFactory<>("dos"));//bean field name, no link with table col name
    	
    	tbl.getColumns().clear();
     	tbl.getColumns().addAll(nam,cmv,bmv,doss);
    	tbl.setItems(lst);
        }
    

    @FXML
    void dofetchall(ActionEvent event) {
    	playsound();
        ObservableList<customerBean> lst=FXCollections.observableArrayList();
        try{
            PreparedStatement	pst=con.prepareStatement("select * from customer ");
           	ResultSet table= pst.executeQuery();
           	boolean jasus=false;
           		while(table.next())
           		{
           			jasus=true;
           			String nam=table.getString("name");//col. name acc. to table
           			float cmv=table.getFloat("cmq");
           			float bmv=table.getFloat("bmq");
           			String date=table.getString("dos");
           			customerBean cb=new customerBean(nam,cmv,bmv,date);
           		    lst.add(cb);
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
        TableColumn<customerBean, String> nam=new TableColumn<customerBean, String>("Customer Name");//Dikhava Title
    	nam.setCellValueFactory(new PropertyValueFactory<>("name"));//bean field name, no link with table col name

    	TableColumn<customerBean, Float> cmv=new TableColumn<customerBean, Float>("customer milk Quantity");//Dikhava Title
    	cmv.setCellValueFactory(new PropertyValueFactory<>("cmq"));//bean field name, no link with table col name
    	
    	TableColumn<customerBean, Float> bmv=new TableColumn<customerBean, Float>("buffalo milk Quantity");//Dikhava Title
    	bmv.setCellValueFactory(new PropertyValueFactory<>("bmq"));//bean field name, no link with table col name
    	
    	TableColumn<customerBean, String> doss=new TableColumn<customerBean, String>("date of Starting");//Dikhava Title
    	doss.setCellValueFactory(new PropertyValueFactory<>("dos"));//bean field name, no link with table col name
    	
    	tbl.getColumns().clear();
     	tbl.getColumns().addAll(nam,cmv,bmv,doss);
    	tbl.setItems(lst);
    }

    @FXML
    void dofetchone(ActionEvent event) {
    if(radcow.isSelected()==true)
    {
    	playsound();
        ObservableList<customerBean> lst=FXCollections.observableArrayList();
        try{
            PreparedStatement	pst=con.prepareStatement("select * from customer where bmq=0");
           	ResultSet table= pst.executeQuery();
           	boolean jasus=false;
           		while(table.next())
           		{
           			jasus=true;
           			String nam=table.getString("name");//col. name acc. to table
           			float cmv=table.getFloat("cmq");
           			float bmv=table.getFloat("bmq");
           			String date=table.getString("dos");
           			customerBean cb=new customerBean(nam,cmv,bmv,date);
           		    lst.add(cb);
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
        TableColumn<customerBean, String> nam=new TableColumn<customerBean, String>("Customer Name");//Dikhava Title
    	nam.setCellValueFactory(new PropertyValueFactory<>("name"));//bean field name, no link with table col name

    	TableColumn<customerBean, Float> cmv=new TableColumn<customerBean, Float>("customer milk Quantity");//Dikhava Title
    	cmv.setCellValueFactory(new PropertyValueFactory<>("cmq"));//bean field name, no link with table col name
    	
    	TableColumn<customerBean, Float> bmv=new TableColumn<customerBean, Float>("buffalo milk Quantity");//Dikhava Title
    	bmv.setCellValueFactory(new PropertyValueFactory<>("bmq"));//bean field name, no link with table col name
    	
    	TableColumn<customerBean, String> doss=new TableColumn<customerBean, String>("date of Starting");//Dikhava Title
    	doss.setCellValueFactory(new PropertyValueFactory<>("dos"));//bean field name, no link with table col name
    	
    	tbl.getColumns().clear();
     	tbl.getColumns().addAll(nam,cmv,bmv,doss);
    	tbl.setItems(lst);
    }
    if(radbuff.isSelected()==true)
    {
    	playsound();
        ObservableList<customerBean> lst=FXCollections.observableArrayList();
        try{
            PreparedStatement	pst=con.prepareStatement("select * from customer where cmq=0");
           	ResultSet table= pst.executeQuery();
           	boolean jasus=false;
           		while(table.next())
           		{
           			jasus=true;
           			String nam=table.getString("name");//col. name acc. to table
           			float cmv=table.getFloat("cmq");
           			float bmv=table.getFloat("bmq");
           			String date=table.getString("dos");
           			customerBean cb=new customerBean(nam,cmv,bmv,date);
           		    lst.add(cb);
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
        TableColumn<customerBean, String> nam=new TableColumn<customerBean, String>("Customer Name");//Dikhava Title
    	nam.setCellValueFactory(new PropertyValueFactory<>("name"));//bean field name, no link with table col name

    	TableColumn<customerBean, Float> cmv=new TableColumn<customerBean, Float>("customer milk Quantity");//Dikhava Title
    	cmv.setCellValueFactory(new PropertyValueFactory<>("cmq"));//bean field name, no link with table col name
    	
    	TableColumn<customerBean, Float> bmv=new TableColumn<customerBean, Float>("buffalo milk Quantity");//Dikhava Title
    	bmv.setCellValueFactory(new PropertyValueFactory<>("bmq"));//bean field name, no link with table col name
    	
    	TableColumn<customerBean, String> doss=new TableColumn<customerBean, String>("date of Starting");//Dikhava Title
    	doss.setCellValueFactory(new PropertyValueFactory<>("dos"));//bean field name, no link with table col name
    	
    	tbl.getColumns().clear();
     	tbl.getColumns().addAll(nam,cmv,bmv,doss);
    	tbl.setItems(lst);
    }
    }

    @FXML
    void doradcow(ActionEvent event) {
    	playsound();
    btnfind.setDisable(false);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        btnfind.setDisable(true);
    }
}
