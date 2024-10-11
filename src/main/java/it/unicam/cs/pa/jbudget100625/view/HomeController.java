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
import it.unicam.cs.pa.jbudget100625.model.account.AccountType;
import it.unicam.cs.pa.jbudget100625.model.movement.Movement;
import it.unicam.cs.pa.jbudget100625.model.movement.MovementType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Alex Citeroni
 */
public class HomeController {
    @FXML
    private TableView<Account> accountInformationView = new TableView<>();
    @FXML
    private TableColumn<Account, AccountType> accountTypeColumn;
    @FXML
    private TableColumn<Account, String> accountNameColumn, accountDescriptionColumn;
    @FXML
    private TableColumn<Account, Double> accountBalanceColumn;
    @FXML
    private TableView<Movement> movementInformationView = new TableView<>();
    @FXML
    private TableColumn<Movement, MovementType> movementTypeColumn;
    @FXML
    private TableColumn<Movement, String> movementDescriptionColumn;
    @FXML
    private TableColumn<Movement, Double> movementAmountColumn;
    @FXML
    private Stage stage = new Stage();
    @FXML
    private Button deleteButton, openAccount;

    private Controller controller = new ControllerImplementation();

    private FileChooser fileChooser = new FileChooser();

    private void createTable() throws IOException {
        accountInformationView.getItems().clear();
        accountTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        accountNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        accountDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        accountBalanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        accountInformationView.setItems(getAccounts());
    }

    private ObservableList<Account> getAccounts() throws IOException {
        ObservableList<Account> observableList = FXCollections.observableArrayList();
        observableList.addAll(controller.getAccounts());
        return observableList;
    }

    public void newAccount() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unicam/cs/pa/jbudget100625/windows/account.fxml"));
        Parent root = loader.load();
        AccountController accountController = loader.getController();
        accountController.setController(controller);
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.setTitle("Account");
        stage.showAndWait();
        createTable();
    }

    public void budget() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unicam/cs/pa/jbudget100625/windows/budget.fxml"));
        Parent root = loader.load();
        BudgetController budgetControllerController = loader.getController();
        budgetControllerController.setController(controller);
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.setTitle("Budget");
        stage.showAndWait();
    }

    public void addTransaction() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unicam/cs/pa/jbudget100625/windows/transaction.fxml"));
        Parent root = loader.load();
        TransactionController transactionController = loader.getController();
        transactionController.setController(controller);
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.setTitle("Transazione");
        stage.showAndWait();
        createTable();
        getAccountMovements();
    }

    public void removeAccount() throws IOException {
        if (accountInformationView.getSelectionModel().getSelectedItem() != null)
            controller.removeAccount(accountInformationView.getSelectionModel().getSelectedItem());
        createTable();
        getAccountMovements();
    }

    public void getAccountMovements() {
        movementTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        movementDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        movementAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        movementInformationView.setItems(getMovements());
    }

    private ObservableList<Movement> getMovements() {
        ObservableList<Movement> observableList = FXCollections.observableArrayList();
        if (accountInformationView.getSelectionModel().getSelectedItem() != null)
            observableList.addAll(accountInformationView.getSelectionModel().getSelectedItem().getMovements());
        return observableList;
    }

    public void click() {
        if (accountInformationView.getSelectionModel().getSelectedItem() != null) {
            deleteButton.setDisable(false);
            openAccount.setDisable(false);
        }
    }

    public void newTag() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unicam/cs/pa/jbudget100625/windows/tag.fxml"));
        Parent root = loader.load();
        TagController tagController = loader.getController();
        tagController.setController(controller);
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.setTitle("Tag");
        stage.showAndWait();
    }

    public void load() throws IOException {
        Window window = deleteButton.getScene().getWindow();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("json file", "*.json"));
        fileChooser.setTitle("Carica il Ledger");
        File ledger = null, budget = null;
        try {
            ledger = fileChooser.showOpenDialog(window);
            fileChooser.setInitialDirectory(ledger.getParentFile());
            fileChooser.setTitle("Carica il Budget");
            budget = fileChooser.showOpenDialog(window);
            if (ledger != null && budget != null)
                controller.load(ledger, budget);
        } catch (Exception ignored) {
        }
        createTable();
    }

    public void save() throws IOException {
        Window window = deleteButton.getScene().getWindow();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("json file", "*.json"));
        fileChooser.setTitle("Salva il Ledger");
        fileChooser.setInitialFileName("ledger");
        File ledger = null, budget = null;
        try {
            ledger = fileChooser.showSaveDialog(window);
            fileChooser.setInitialDirectory(ledger.getParentFile());
            fileChooser.setTitle("Salva il Budget");
            fileChooser.setInitialFileName("budget");
            budget = fileChooser.showSaveDialog(window);
        } catch (Exception ignored) {
        }
        if (ledger != null && budget != null)
            controller.save(ledger, budget);
    }

    public void close() {
        Stage stage = (Stage) accountInformationView.getScene().getWindow();
        stage.close();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HomeController that = (HomeController) o;
        return Objects.equals(accountInformationView, that.accountInformationView) &&
                Objects.equals(accountTypeColumn, that.accountTypeColumn) &&
                Objects.equals(accountNameColumn, that.accountNameColumn) &&
                Objects.equals(accountDescriptionColumn, that.accountDescriptionColumn) &&
                Objects.equals(accountBalanceColumn, that.accountBalanceColumn) &&
                Objects.equals(movementInformationView, that.movementInformationView) &&
                Objects.equals(movementTypeColumn, that.movementTypeColumn) &&
                Objects.equals(movementDescriptionColumn, that.movementDescriptionColumn) &&
                Objects.equals(movementAmountColumn, that.movementAmountColumn) &&
                Objects.equals(stage, that.stage) &&
                Objects.equals(deleteButton, that.deleteButton) &&
                Objects.equals(openAccount, that.openAccount) &&
                Objects.equals(controller, that.controller) &&
                Objects.equals(fileChooser, that.fileChooser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountInformationView, accountTypeColumn, accountNameColumn, accountDescriptionColumn, accountBalanceColumn, movementInformationView, movementTypeColumn, movementDescriptionColumn, movementAmountColumn, stage, deleteButton, openAccount, controller, fileChooser);
    }

    @Override
    public String toString() {
        return "HomeController{" +
                "accountInformationView=" + accountInformationView +
                ", accountTypeColumn=" + accountTypeColumn +
                ", accountNameColumn=" + accountNameColumn +
                ", accountDescriptionColumn=" + accountDescriptionColumn +
                ", accountBalanceColumn=" + accountBalanceColumn +
                ", movementInformationView=" + movementInformationView +
                ", movementTypeColumn=" + movementTypeColumn +
                ", movementDescriptionColumn=" + movementDescriptionColumn +
                ", movementAmountColumn=" + movementAmountColumn +
                ", stage=" + stage +
                ", deleteButton=" + deleteButton +
                ", openAccount=" + openAccount +
                ", controller=" + controller +
                ", fileChooser=" + fileChooser +
                '}';
    }
}
