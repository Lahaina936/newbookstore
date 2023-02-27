/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxapplication6;

import classjava.book;
import classjava.connectSQL;
import classjava.getData;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author HDTC
 */
public class GuestController implements Initializable {

    @FXML
    private ImageView availableBooks_imageView;
    @FXML
    private TableView<book> availableBooks_tableView;
    @FXML
    private TableColumn<book,Integer> availableBooks_col_bookid;
    @FXML
    private TableColumn<book,String> availableBooks_col_bookname;
    @FXML
    private TableColumn<book,String> availableBooks_col_catalog;
    @FXML
    private TableColumn<book,String> availableBooks_col_author;
    @FXML
    private TableColumn<book,Double> availableBooks_col_price;
    @FXML
    private Button btn_close;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            availableBooksShowListData();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GuestController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
     public ObservableList<book> availableBooksListData() throws ClassNotFoundException, SQLException{
        ObservableList<book> listData = FXCollections.observableArrayList();
        String sql = "SELECT *FROM menu";
        
        Connection connect = connectSQL.getconnect();
        
        try{
           PreparedStatement prepare = connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();
            
            book bookD;
            
            while(result.next()){
                bookD = new book(result.getInt("product_id"), result.getString("bookname")
                       , result.getString("catalog")
                        ,result.getString("author")
                        , result.getDouble("price")
                        , result.getString("imagelink"));
                
                listData.add(bookD);
            }
        }catch(SQLException e){}
        return listData;
    }
    
    private ObservableList<book> availableBooksList;
    public void availableBooksShowListData() throws ClassNotFoundException, SQLException{
        availableBooksList = availableBooksListData();
        
        availableBooks_col_bookid.setCellValueFactory(new PropertyValueFactory<>("bookid"));
        availableBooks_col_bookname.setCellValueFactory(new PropertyValueFactory<>("bookname"));
        availableBooks_col_author.setCellValueFactory(new PropertyValueFactory<>("author"));
        availableBooks_col_catalog.setCellValueFactory(new PropertyValueFactory<>("catalog"));
        availableBooks_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
         availableBooks_tableView.setItems(availableBooksList);
    }
    @FXML
    public void availableBooksSelect(){
        book bookD = availableBooks_tableView.getSelectionModel().getSelectedItem();
        int num = availableBooks_tableView.getSelectionModel().getSelectedIndex();
        
        if((num - 1) < -1){ return; }
        
        
        
        
        String uri = "file:"+bookD.getImagelink();
        Image image = new Image(uri, 105, 89, false, true);
        
        availableBooks_imageView.setImage(image);
    }
    @FXML
    public void close(){
        try {
            btn_close.getScene().getWindow().hide();
            // LINK YOUR LOGIN FORM
            Parent root = FXMLLoader.load(getClass().getResource("/javafxapplication6/login.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.initStyle(StageStyle.TRANSPARENT);
            
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(GuestController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
