/**
 * Sample Skeleton for 'bptview.fxml' Controller Class
 */

package billpaidtable;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import connectdb.DBConnection;
import billpaidtable.billsBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

public class bptviewController {
	Connection con=DBConnection.doConnect();
	 @FXML
	    private ToggleGroup discount;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="radpd"
    private RadioButton radpd; // Value injected by FXMLLoader

    @FXML // fx:id="radunpd"
    private RadioButton radunpd; // Value injected by FXMLLoader

    @FXML // fx:id="comboname"
    private ComboBox<String> comboname; // Value injected by FXMLLoader

    @FXML // fx:id="btnbill"
    private Button btnbill; // Value injected by FXMLLoader

    @FXML // fx:id="tbl"
    private TableView<billsBean> tbl; // Value injected by FXMLLoader

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
    	 if(radpd.isSelected()==true)
    	    {
    	    	playsound();
    	        ObservableList<billsBean> lst=FXCollections.observableArrayList();
    	        try{
    	            PreparedStatement	pst=con.prepareStatement("select * from bills where status=1");
    	           	ResultSet table= pst.executeQuery();
    	           	boolean jasus=false;
    	           		while(table.next())
    	           		{
    	           			jasus=true;
        	       			String nam=table.getString("name");//col. name acc. to table
                			String dos=table.getString("dos");
                			String doe=table.getString("doe");
                			float cmv=table.getFloat("tcmq");
                			float bmv=table.getFloat("tbmq");
                			float amt=table.getFloat("amount");
                			billsBean bb=new billsBean(nam,dos,doe,cmv,bmv,amt);
                			lst.add(bb);
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
    	        TableColumn<billsBean, String> nam=new TableColumn<billsBean, String>("Customer Name");//Dikhava Title
            	nam.setCellValueFactory(new PropertyValueFactory<>("name"));//bean field name, no link with table col name

            	TableColumn<billsBean, String> dos=new TableColumn<billsBean, String>("Date of Starting");//Dikhava Title
            	dos.setCellValueFactory(new PropertyValueFactory<>("dos"));//bean field name, no link with table col name

            	TableColumn<billsBean, String> doe=new TableColumn<billsBean, String>("Date of End");//Dikhava Title
            	doe.setCellValueFactory(new PropertyValueFactory<>("doe"));//bean field name, no link with table col name

            	TableColumn<billsBean, Float> tcmv=new TableColumn<billsBean, Float>("customer cow milk quantity");//Dikhava Title
            	tcmv.setCellValueFactory(new PropertyValueFactory<>("tcmq"));//bean field name, no link with table col name
            	
            	TableColumn<billsBean, Float> tbmv=new TableColumn<billsBean, Float>("customer buffalo milk quantity");//Dikhava Title
            	tbmv.setCellValueFactory(new PropertyValueFactory<>("tbmq"));//bean field name, no link with table col name
            	
            	TableColumn<billsBean, Float> amt=new TableColumn<billsBean, Float>("Amount");//Dikhava Title
            	amt.setCellValueFactory(new PropertyValueFactory<>("amount"));//bean field name, no link with table col name
            	
            	tbl.getColumns().clear();
             	tbl.getColumns().addAll(nam,dos,doe,tcmv,tbmv,amt);
            	tbl.setItems(lst);
    	    }
    	  if(radunpd.isSelected()==true)
 	    {
 	    	playsound();
 	        ObservableList<billsBean> lst=FXCollections.observableArrayList();
 	        try{
 	            PreparedStatement	pst=con.prepareStatement("select * from bills where status=0");
 	           	ResultSet table= pst.executeQuery();
 	           	boolean jasus=false;
 	           		while(table.next())
 	           		{
 	           			jasus=true;
     	       			String nam=table.getString("name");//col. name acc. to table
             			String dos=table.getString("dos");
             			String doe=table.getString("doe");
             			float cmv=table.getFloat("tcmq");
             			float bmv=table.getFloat("tbmq");
             			float amt=table.getFloat("amount");
             			billsBean bb=new billsBean(nam,dos,doe,cmv,bmv,amt);
             			lst.add(bb);
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
 	        TableColumn<billsBean, String> nam=new TableColumn<billsBean, String>("Customer Name");//Dikhava Title
         	nam.setCellValueFactory(new PropertyValueFactory<>("name"));//bean field name, no link with table col name

         	TableColumn<billsBean, String> dos=new TableColumn<billsBean, String>("Date of Starting");//Dikhava Title
         	dos.setCellValueFactory(new PropertyValueFactory<>("dos"));//bean field name, no link with table col name

         	TableColumn<billsBean, String> doe=new TableColumn<billsBean, String>("Date of End");//Dikhava Title
         	doe.setCellValueFactory(new PropertyValueFactory<>("doe"));//bean field name, no link with table col name

         	TableColumn<billsBean, Float> tcmv=new TableColumn<billsBean, Float>("customer cow milk quantity");//Dikhava Title
         	tcmv.setCellValueFactory(new PropertyValueFactory<>("tcmq"));//bean field name, no link with table col name
         	
         	TableColumn<billsBean, Float> tbmv=new TableColumn<billsBean, Float>("customer buffalo milk quantity");//Dikhava Title
         	tbmv.setCellValueFactory(new PropertyValueFactory<>("tbmq"));//bean field name, no link with table col name
         	
         	TableColumn<billsBean, Float> amt=new TableColumn<billsBean, Float>("Amount");//Dikhava Title
         	amt.setCellValueFactory(new PropertyValueFactory<>("amount"));//bean field name, no link with table col name
         	
         	tbl.getColumns().clear();
          	tbl.getColumns().addAll(nam,dos,doe,tcmv,tbmv,amt);
         	tbl.setItems(lst);
 	    }
    }

    @FXML
    void dofetchone(ActionEvent event) {
    	playsound();
    if(radpd.isSelected()==true)
    {
    	 String name=comboname.getSelectionModel().getSelectedItem();
 	    ObservableList<billsBean> lst=FXCollections.observableArrayList();
 	    try{
 	        PreparedStatement	pst=con.prepareStatement("select * from bills where name=? && status=1");
 	        pst.setString(1,name);
 	       	ResultSet table= pst.executeQuery();
 	       	boolean jasus=false;
 	       		while(table.next())
 	       		{
 	       			jasus=true;
 	       			String nam=table.getString("name");//col. name acc. to table
         			String dos=table.getString("dos");
         			String doe=table.getString("doe");
         			float cmv=table.getFloat("tcmq");
         			float bmv=table.getFloat("tbmq");
         			float amt=table.getFloat("amount");
         			billsBean bb=new billsBean(nam,dos,doe,cmv,bmv,amt);
         			lst.add(bb);
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
 	    TableColumn<billsBean, String> nam=new TableColumn<billsBean, String>("Customer Name");//Dikhava Title
     	nam.setCellValueFactory(new PropertyValueFactory<>("name"));//bean field name, no link with table col name

     	TableColumn<billsBean, String> dos=new TableColumn<billsBean, String>("Date of Starting");//Dikhava Title
     	dos.setCellValueFactory(new PropertyValueFactory<>("dos"));//bean field name, no link with table col name

     	TableColumn<billsBean, String> doe=new TableColumn<billsBean, String>("Date of End");//Dikhava Title
     	doe.setCellValueFactory(new PropertyValueFactory<>("doe"));//bean field name, no link with table col name

     	TableColumn<billsBean, Float> tcmv=new TableColumn<billsBean, Float>("customer cow milk quantity");//Dikhava Title
     	tcmv.setCellValueFactory(new PropertyValueFactory<>("tcmq"));//bean field name, no link with table col name
     	
     	TableColumn<billsBean, Float> tbmv=new TableColumn<billsBean, Float>("customer buffalo milk quantity");//Dikhava Title
     	tbmv.setCellValueFactory(new PropertyValueFactory<>("tbmq"));//bean field name, no link with table col name
     	
     	TableColumn<billsBean, Float> amt=new TableColumn<billsBean, Float>("Amount");//Dikhava Title
     	amt.setCellValueFactory(new PropertyValueFactory<>("amount"));//bean field name, no link with table col name
     	
     	tbl.getColumns().clear();
      	tbl.getColumns().addAll(nam,dos,doe,tcmv,tbmv,amt);
     	tbl.setItems(lst);
	}
     if(radunpd.isSelected()==true)
    {
    	 String name=comboname.getSelectionModel().getSelectedItem();
 	    ObservableList<billsBean> lst=FXCollections.observableArrayList();
 	    try{
 	        PreparedStatement	pst=con.prepareStatement("select * from bills where name=? && status=0");
 	        pst.setString(1,name);
 	       	ResultSet table= pst.executeQuery();
 	       	boolean jasus=false;
 	       		while(table.next())
 	       		{
 	       			jasus=true;
 	       			String nam=table.getString("name");//col. name acc. to table
         			String dos=table.getString("dos");
         			String doe=table.getString("doe");
         			float cmv=table.getFloat("tcmq");
         			float bmv=table.getFloat("tbmq");
         			float amt=table.getFloat("amount");
         			billsBean bb=new billsBean(nam,dos,doe,cmv,bmv,amt);
         			lst.add(bb);
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
 	    TableColumn<billsBean, String> nam=new TableColumn<billsBean, String>("Customer Name");//Dikhava Title
     	nam.setCellValueFactory(new PropertyValueFactory<>("name"));//bean field name, no link with table col name

     	TableColumn<billsBean, String> dos=new TableColumn<billsBean, String>("Date of Starting");//Dikhava Title
     	dos.setCellValueFactory(new PropertyValueFactory<>("dos"));//bean field name, no link with table col name

     	TableColumn<billsBean, String> doe=new TableColumn<billsBean, String>("Date of End");//Dikhava Title
     	doe.setCellValueFactory(new PropertyValueFactory<>("doe"));//bean field name, no link with table col name

     	TableColumn<billsBean, Float> tcmv=new TableColumn<billsBean, Float>("customer cow milk quantity");//Dikhava Title
     	tcmv.setCellValueFactory(new PropertyValueFactory<>("tcmq"));//bean field name, no link with table col name
     	
     	TableColumn<billsBean, Float> tbmv=new TableColumn<billsBean, Float>("customer buffalo milk quantity");//Dikhava Title
     	tbmv.setCellValueFactory(new PropertyValueFactory<>("tbmq"));//bean field name, no link with table col name
     	
     	TableColumn<billsBean, Float> amt=new TableColumn<billsBean, Float>("Amount");//Dikhava Title
     	amt.setCellValueFactory(new PropertyValueFactory<>("amount"));//bean field name, no link with table col name
     	
     	tbl.getColumns().clear();
      	tbl.getColumns().addAll(nam,dos,doe,tcmv,tbmv,amt);
     	tbl.setItems(lst);
	}
    }

    @FXML
    void doradpd(ActionEvent event) {
    	btnbill.setDisable(false);
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
    	btnbill.setDisable(true);
    	doload();
    }
}
