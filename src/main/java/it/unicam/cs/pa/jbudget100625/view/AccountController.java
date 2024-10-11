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
import it.unicam.cs.pa.jbudget100625.model.account.AccountImplementation;
import it.unicam.cs.pa.jbudget100625.model.account.AccountType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

import static it.unicam.cs.pa.jbudget100625.model.account.AccountType.ASSETS;
import static it.unicam.cs.pa.jbudget100625.model.account.AccountType.LIABILITIES;

/**
 * @author Alex Citeroni
 */
public class AccountController implements Initializable {
    @FXML
    private TextField accountNameTextField, accountDescriptionTextField;
    @FXML
    private Spinner<Double> openingBalanceSpinner;
    @FXML
    private ChoiceBox<AccountType> accountTypeChoiceBox;
    @FXML
    private Button addAccountButton;

    private Controller controller;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accountTypeChoiceBox.setValue(ASSETS);
        accountTypeChoiceBox.setItems(FXCollections.observableArrayList(ASSETS, LIABILITIES));
        openingBalanceSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 100000000.0));
        openingBalanceSpinner.getEditor().setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), 0.0, ControllerImplementation::apply));
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void addAccount() {
        controller.addAccount(new AccountImplementation(new Date().hashCode(), accountTypeChoiceBox.getValue(), accountNameTextField.getText(), accountDescriptionTextField.getText(), openingBalanceSpinner.getValue()));
        Stage stage = (Stage) accountNameTextField.getScene().getWindow();
        stage.close();
    }

    public void enableButton() {
        addAccountButton.setDisable(accountNameTextField.getText().trim().isEmpty() || accountDescriptionTextField.getText().trim().isEmpty());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountController that = (AccountController) o;
        return Objects.equals(accountNameTextField, that.accountNameTextField) &&
                Objects.equals(accountDescriptionTextField, that.accountDescriptionTextField) &&
                Objects.equals(openingBalanceSpinner, that.openingBalanceSpinner) &&
                Objects.equals(accountTypeChoiceBox, that.accountTypeChoiceBox) &&
                Objects.equals(addAccountButton, that.addAccountButton) &&
                Objects.equals(controller, that.controller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNameTextField, accountDescriptionTextField, openingBalanceSpinner, accountTypeChoiceBox, addAccountButton, controller);
    }

    @Override
    public String toString() {
        return "AccountController{" +
                "accountNameTextField=" + accountNameTextField +
                ", accountDescriptionTextField=" + accountDescriptionTextField +
                ", openingBalanceSpinner=" + openingBalanceSpinner +
                ", accountTypeChoiceBox=" + accountTypeChoiceBox +
                ", addAccountButton=" + addAccountButton +
                ", controller=" + controller +
                '}';
    }
}
