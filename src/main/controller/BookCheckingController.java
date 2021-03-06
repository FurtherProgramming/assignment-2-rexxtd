package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.model.BookCheckingModel;

import java.io.IOException;
import java.sql.SQLException;

public class BookCheckingController
{
    public static String bd_username, bd_date, bd_time;
    public LoginController loginController = new LoginController();
    public BookCheckingModel bookCheckingModel = new BookCheckingModel();
    @FXML
    private ChoiceBox<String> cbhour;
    @FXML
    private ChoiceBox<String> cbminute;
    @FXML
    private DatePicker dpDate;
    @FXML
    private Label failMessage;
    @FXML
    private Label failMessage2;
    @FXML
    private Hyperlink clickHere;

    public void initialize()
    {
        //add and display choice for user to pick a time for sitting
        cbhour.getItems().addAll("08", "09", "10", "11", "12", "13", "14", "15", "16", "17");
        cbminute.getItems().addAll("00", "15", "30", "45");
    }

    //get information for date and time
    public boolean getInput() throws SQLException
    {
        try
        {
            String username = loginController.username;
            String date = dpDate.getValue().toString();
            String time = cbhour.getValue().toString() + ":" + cbminute.getValue().toString();

            if (bookCheckingModel.bookingExist(username, date, time))
            {
                return true;
            }
            else
            {
                failMessage.setText("An error has occurred. ");
                clickHere.setText("Click here ");
                failMessage2.setText("to learn more");
                return false;
            }
        }
        catch (Exception e)
        {
            failMessage.setText("Please provide all information!");
            return false;
        }
    }

    public void storeData()
    {
        bd_username = loginController.username;
        bd_date = dpDate.getValue().toString();
        bd_time = cbhour.getValue().toString() + ":" + cbminute.getValue().toString();
    }

    //action event when user click continue button in booking tab
    @FXML
    public void confirmBooking(ActionEvent event) throws IOException, SQLException
    {
        if(getInput())
        {
            storeData();
            Parent root = FXMLLoader.load(getClass().getResource("../ui/seatbooking.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void clickHereHyperlink(ActionEvent event) throws IOException
    {
        Parent anotherRoot = FXMLLoader.load(getClass().getResource("../ui/errorbooking.fxml"));
        Stage anotherStage = new Stage();
        Scene anotherScene = new Scene(anotherRoot);
        anotherStage.setScene(anotherScene);
        anotherStage.show();
        anotherStage.setTitle("Learn more");
    }
}
