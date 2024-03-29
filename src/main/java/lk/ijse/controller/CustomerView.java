package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.config.SessionFactoryConfig;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.dto.tm.CustomerTM;
import lk.ijse.entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.Optional;

public class CustomerView {
    @FXML
    private AnchorPane anchorpane;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<CustomerTM> cusTable;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtCusID;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSearchCus;

    private int index;

    private final ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();

    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public void initialize(){
        setCellValueFactory();
        loadAllCustomers();
    }

    private void setCellValueFactory(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    private void loadAllCustomers() {
        customerTMS.clear();
        //get customers
        for (CustomerDTO customerDTO : customerBO.getAll()) {
            customerTMS.add(new CustomerTM(
                    customerDTO.getId(),
                    customerDTO.getName(),
                    customerDTO.getAddress(),
                    customerDTO.getContact())
            );
        }
        cusTable.setItems(customerTMS);
    }

    @FXML
    void btnBackOnAction(ActionEvent event) {
        Parent rootNode = null;
        try {
            rootNode = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(rootNode);
        Stage stage = (Stage) this.anchorpane.getScene().getWindow();
        stage.setTitle("WELCOME");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if(type.orElse(no) == yes) {
            String id = txtCusID.getText();
            //delete customer
            boolean delete = customerBO.delete(id);
            if (delete) {
                new Alert(Alert.AlertType.INFORMATION, "delete successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "something went wrong").show();
            }
        }
        loadAllCustomers();
        initUi();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        //save customer
        boolean save = customerBO.save(new CustomerDTO(Integer.parseInt(txtCusID.getText()), txtName.getText(), txtAddress.getText(), txtContact.getText()));
        if (save){
            new Alert(Alert.AlertType.CONFIRMATION,"saved successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"something went wrong").show();
        }
        loadAllCustomers();
        initUi();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        //update customer
        boolean update = customerBO.update(new CustomerDTO(Integer.parseInt(txtCusID.getText()), txtName.getText(), txtAddress.getText(), txtContact.getText()));
        if (update){
            new Alert(Alert.AlertType.INFORMATION,"update successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"something went wrong").show();
        }
        loadAllCustomers();
        initUi();
    }

    @FXML
    void tableOnAction(MouseEvent event) {
        index = cusTable.getSelectionModel().getSelectedIndex();

        if(index <= -1){
            return;
        }

        txtCusID.setText(colId.getCellData(index).toString());
        txtName.setText(colName.getCellData(index).toString());
        txtAddress.setText(colAddress.getCellData(index).toString());
        txtContact.setText(colContact.getCellData(index).toString());
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        FilteredList<CustomerTM> filteredData = new FilteredList<>(customerTMS, b -> true);

        txtSearchCus.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(CustomerTM -> {

                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                String id = String.valueOf(CustomerTM.getId());

                if(id.toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(CustomerTM.getName().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(CustomerTM.getAddress().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(CustomerTM.getContact().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else
                    return false;
            });
        });

        SortedList<CustomerTM> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(cusTable.comparatorProperty());

        cusTable.setItems(sortedData);
    }

    private void initUi() {
        txtCusID.clear();
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
    }
}
