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
import lk.ijse.bo.custom.ItemBO;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.dto.ItemDTO;
import lk.ijse.dto.tm.CustomerTM;
import lk.ijse.dto.tm.ItemTM;

import java.io.IOException;
import java.util.Optional;

public class ItemView {
    @FXML
    private AnchorPane anchorpane;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colDescript;

    @FXML
    private TableColumn<?, ?> colQOH;

    @FXML
    private TableColumn<?, ?> colUP;

    @FXML
    private TableView<ItemTM> itemTable;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtDescipt;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtSearchItem;

    @FXML
    private TextField txtUnitprice;

    private int index;

    private final ObservableList<ItemTM> itemTMS = FXCollections.observableArrayList();

    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    public void initialize(){
        setCellValueFactory();
        loadAllItems();
    }

    private void setCellValueFactory(){
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescript.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUP.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQOH.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
    }

    private void loadAllItems() {
        itemTMS.clear();
        //get items
        for (ItemDTO itemDTO : itemBO.getAll()) {
            itemTMS.add(new ItemTM(
                    itemDTO.getCode(),
                    itemDTO.getDescription(),
                    itemDTO.getUnitPrice(),
                    itemDTO.getQtyOnHand())
            );
        }
        itemTable.setItems(itemTMS);
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
            String code = txtCode.getText();
            //delete item
            boolean delete = itemBO.delete(code);
            if (delete) {
                new Alert(Alert.AlertType.INFORMATION, "delete successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "something went wrong").show();
            }
        }
        loadAllItems();
        initUi();
    }

    private void initUi() {
        txtCode.clear();
        txtDescipt.clear();
        txtUnitprice.clear();
        txtQtyOnHand.clear();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        //save item
        boolean save = itemBO.save(new ItemDTO(Integer.parseInt(txtCode.getText()), txtDescipt.getText(), Double.parseDouble(txtUnitprice.getText()), Integer.parseInt(txtQtyOnHand.getText())));
        if (save){
            new Alert(Alert.AlertType.CONFIRMATION,"saved successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"something went wrong").show();
        }
        loadAllItems();
        initUi();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        //update item
        boolean update = itemBO.update(new ItemDTO(Integer.parseInt(txtCode.getText()), txtDescipt.getText(), Double.parseDouble(txtUnitprice.getText()), Integer.parseInt(txtQtyOnHand.getText())));
        if (update){
            new Alert(Alert.AlertType.INFORMATION,"update successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"something went wrong").show();
        }
        loadAllItems();
        initUi();
    }

    @FXML
    void tableOnAction(MouseEvent event) {
        index = itemTable.getSelectionModel().getSelectedIndex();

        if(index <= -1){
            return;
        }

        txtCode.setText(colCode.getCellData(index).toString());
        txtDescipt.setText(colDescript.getCellData(index).toString());
        txtUnitprice.setText(colUP.getCellData(index).toString());
        txtQtyOnHand.setText(colQOH.getCellData(index).toString());
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        FilteredList<ItemTM> filteredData = new FilteredList<>(itemTMS, b -> true);

        txtSearchItem.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(ItemTM -> {

                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();

                String code = String.valueOf(ItemTM.getCode());
                String uP = String.valueOf(ItemTM.getUnitPrice());
                String qOH = String.valueOf(ItemTM.getQtyOnHand());

                if(code.toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(ItemTM.getDescription().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(uP.toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(qOH.toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else
                    return false;
            });
        });

        SortedList<ItemTM> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(itemTable.comparatorProperty());

        itemTable.setItems(sortedData);
    }
}
