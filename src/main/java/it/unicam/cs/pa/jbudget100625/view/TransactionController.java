/*
 *
 *  * Copyright (c) 2019. [Acme Corp]
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in
 *  * all copies or substantial portions of the Software.
 *
 */

package it.unicam.cs.pa.jbudget100625.view;

import it.unicam.cs.pa.jbudget100625.controller.Controller;
import it.unicam.cs.pa.jbudget100625.controller.ControllerImplementation;
import it.unicam.cs.pa.jbudget100625.model.account.Account;
import it.unicam.cs.pa.jbudget100625.model.movement.Movement;
import it.unicam.cs.pa.jbudget100625.model.movement.MovementImplementation;
import it.unicam.cs.pa.jbudget100625.model.movement.MovementType;
import it.unicam.cs.pa.jbudget100625.model.tag.Tag;
import it.unicam.cs.pa.jbudget100625.model.transaction.Transaction;
import it.unicam.cs.pa.jbudget100625.model.transaction.TransactionImplementation;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static it.unicam.cs.pa.jbudget100625.model.movement.MovementType.CREDITS;
import static it.unicam.cs.pa.jbudget100625.model.movement.MovementType.DEBITS;

/**
 * @author Alex Citeroni
 */
public class TransactionController implements Initializable {
    @FXML
    private Spinner<Double> amountValue;
    @FXML
    private ChoiceBox<MovementType> movementTypeChoiceBox;
    @FXML
    private ChoiceBox<String> accountChoiceBox = new ChoiceBox<>();
    @FXML
    private TableView<Tag> tagView = new TableView<>();
    @FXML
    private TableColumn<Tag, String> nameTag, descriptionTag;
    @FXML
    private TableView<Movement> movementView = new TableView<>();
    @FXML
    private TableColumn<Movement, MovementType> movementType;
    @FXML
    private TableColumn<Movement, Double> amount;
    @FXML
    private TableColumn<Movement, String> movementDescription, account;
    @FXML
    private DatePicker date;
    @FXML
    private Stage stage = new Stage();
    @FXML
    private Button deleteButton, removeMovement, addMovementButton;
    @FXML
    private TextField descriptionTextField;

    private Controller controller;

    private List<Tag> tags = new ArrayList<>();

    private List<Movement> movements = new ArrayList<>();

    private Transaction transaction;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        movementTypeChoiceBox.setValue(CREDITS);
        movementTypeChoiceBox.setItems(FXCollections.observableArrayList(CREDITS, DEBITS));
        amountValue.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 100000000.0));
        amountValue.getEditor().setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), 0.0, ControllerImplementation::apply));
    }

    private void createMovementTable() {
        account.setCellValueFactory(element -> new ReadOnlyObjectWrapper<>(element.getValue().getAccount().getName()));
        movementDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        movementType.setCellValueFactory(new PropertyValueFactory<>("type"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        movementView.setItems(getMovements());
    }

    private ObservableList<Movement> getMovements() {
        ObservableList<Movement> observableList = FXCollections.observableArrayList();
        observableList.addAll(movements);
        return observableList;
    }

    private ObservableList<Tag> getTag() {
        ObservableList<Tag> observableList = FXCollections.observableArrayList();
        observableList.addAll(tags);
        return observableList;
    }

    public void createTagTable() {
        nameTag.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionTag.setCellValueFactory(new PropertyValueFactory<>("description"));
        tagView.setItems(getTag());
    }

    private ObservableList<String> getAccounts() throws IOException {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        controller.getAccounts().stream().map(Account::getName).forEach(observableList::add);
        return observableList;
    }

    public void setController(Controller controller) throws IOException {
        this.controller = controller;
        if (!getAccounts().isEmpty()) {
            accountChoiceBox.setItems(getAccounts());
            if (getAccounts().get(0) != null) accountChoiceBox.setValue(getAccounts().get(0));
        }
    }

    public void addTransaction() {
        if (!tags.isEmpty() && !movements.isEmpty() && !date.getValue().equals(null)) {
            transaction = new TransactionImplementation(new Date().hashCode(), date.getValue(), tags, movements);
            controller.addTransaction(transaction);
            movements.forEach(movement -> movement.setTransaction(transaction));
            Stage stage = (Stage) tagView.getScene().getWindow();
            stage.close();
        }
    }

    public void addMovement() throws IOException {
        if (accountChoiceBox.getValue() != null)
            movements.add(new MovementImplementation(new Date().hashCode(), controller.getAccounts().get(accountChoiceBox.getSelectionModel().getSelectedIndex()), descriptionTextField.getText(), movementTypeChoiceBox.getValue(), tags, amountValue.getValue()));
        descriptionTextField.clear();
        createMovementTable();
    }

    public void addTag() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unicam/cs/pa/jbudget100625/windows/tag.fxml"));
        Parent root = loader.load();
        TagController tagController = loader.getController();
        tagController.setController(controller);
        tagController.setTags(tags);
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.setTitle("Tag");
        stage.showAndWait();
        createTagTable();
    }

    public void movementClick() {
        if (movementView.getSelectionModel().getSelectedItem() != null) removeMovement.setDisable(false);
    }

    public void click() {
        if (tagView.getSelectionModel().getSelectedItem() != null) deleteButton.setDisable(false);
    }

    public void removeTag() {
        tags.remove(tagView.getSelectionModel().getSelectedItem());
        createTagTable();
    }

    public void removeMovement() {
        movements.remove(movementView.getSelectionModel().getSelectedItem());
        createMovementTable();
    }

    public void enabledButton() {
        addMovementButton.setDisable(descriptionTextField.getText().trim().isEmpty());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionController that = (TransactionController) o;
        return Objects.equals(amountValue, that.amountValue) &&
                Objects.equals(movementTypeChoiceBox, that.movementTypeChoiceBox) &&
                Objects.equals(accountChoiceBox, that.accountChoiceBox) &&
                Objects.equals(tagView, that.tagView) &&
                Objects.equals(nameTag, that.nameTag) &&
                Objects.equals(descriptionTag, that.descriptionTag) &&
                Objects.equals(movementView, that.movementView) &&
                Objects.equals(movementType, that.movementType) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(movementDescription, that.movementDescription) &&
                Objects.equals(account, that.account) &&
                Objects.equals(date, that.date) &&
                Objects.equals(stage, that.stage) &&
                Objects.equals(deleteButton, that.deleteButton) &&
                Objects.equals(removeMovement, that.removeMovement) &&
                Objects.equals(addMovementButton, that.addMovementButton) &&
                Objects.equals(descriptionTextField, that.descriptionTextField) &&
                Objects.equals(controller, that.controller) &&
                Objects.equals(tags, that.tags) &&
                Objects.equals(movements, that.movements) &&
                Objects.equals(transaction, that.transaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amountValue, movementTypeChoiceBox, accountChoiceBox, tagView, nameTag, descriptionTag, movementView, movementType, amount, movementDescription, account, date, stage, deleteButton, removeMovement, addMovementButton, descriptionTextField, controller, tags, movements, transaction);
    }

    @Override
    public String toString() {
        return "TransactionController{" +
                "amountValue=" + amountValue +
                ", movementTypeChoiceBox=" + movementTypeChoiceBox +
                ", accountChoiceBox=" + accountChoiceBox +
                ", tagView=" + tagView +
                ", nameTag=" + nameTag +
                ", descriptionTag=" + descriptionTag +
                ", movementView=" + movementView +
                ", movementType=" + movementType +
                ", amount=" + amount +
                ", movementDescription=" + movementDescription +
                ", account=" + account +
                ", date=" + date +
                ", stage=" + stage +
                ", deleteButton=" + deleteButton +
                ", removeMovement=" + removeMovement +
                ", addMovementButton=" + addMovementButton +
                ", descriptionTextField=" + descriptionTextField +
                ", controller=" + controller +
                ", tags=" + tags +
                ", movements=" + movements +
                ", transaction=" + transaction +
                '}';
    }
}
