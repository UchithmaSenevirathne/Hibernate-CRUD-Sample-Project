package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainForm {
    @FXML
    private AnchorPane anchorpane;

    @FXML
    void btnCustomerOnAction(ActionEvent event) {
        Parent rootNode = null;
        try {
            rootNode = FXMLLoader.load(getClass().getResource("/view/Customer.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.anchorpane.getScene().getWindow();
        stage.setTitle("CUSTOMER");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnItemsOnAction(ActionEvent event) {
        Parent rootNode = null;
        try {
            rootNode = FXMLLoader.load(getClass().getResource("/view/Item.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.anchorpane.getScene().getWindow();
        stage.setTitle("ITEMS");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnOrderDetailsOnAction(ActionEvent event) {

    }

    @FXML
    void btnOrderOnAction(ActionEvent event) {

    }
}
