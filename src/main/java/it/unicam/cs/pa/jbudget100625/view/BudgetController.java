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
import it.unicam.cs.pa.jbudget100625.model.tag.Tag;
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
import java.util.stream.Collectors;

/**
 * @author Alex Citeroni
 */
public class BudgetController implements Initializable {
    @FXML
    private TableView<Tag> budgetView = new TableView<>();
    @FXML
    private TableColumn<Tag, Tag> tag;
    @FXML
    private TableColumn<Tag, Double> expected;
    @FXML
    private Spinner<Double> amount;
    @FXML
    private ChoiceBox<String> tagBox = new ChoiceBox<>();
    @FXML
    private Button removeBudgetButton;
    @FXML
    private ListView<Double> listView = new ListView<>();

    private Controller controller;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        amount.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 100000000.0));
        amount.getEditor().setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), 0.0, ControllerImplementation::apply));
    }

    public void setController(Controller controller) {
        this.controller = controller;
        tagBox();
        createTable();
        listReportMethod();
    }

    private void tagBox() {
        if (!getTags().isEmpty()) {
            tagBox.setItems(getTags());
            if (getTags().get(0) != null) tagBox.setValue(getTags().get(0));
        }
    }

    private ObservableList<String> getTags() {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        controller.getLedgerTags().stream().map(Tag::getName).forEachOrdered(observableList::add);
        return observableList;
    }

    private void createTable() {
        tag.setCellValueFactory(new PropertyValueFactory<>("name"));
        expected.setCellValueFactory(value -> new ReadOnlyObjectWrapper<>(controller.getBudget(value.getValue())));
        budgetView.setItems(getBudget());
    }

    public ObservableList<Tag> getBudget() {
        ObservableList<Tag> observableList = FXCollections.observableArrayList();
        observableList.addAll(controller.budgetTags());
        return observableList;
    }

    public void addBudget() {
        if (tagBox.getValue() != null)
            controller.setBudget(controller.getLedgerTags().get(tagBox.getSelectionModel().getSelectedIndex()), amount.getValue());
        listReportMethod();
        createTable();
    }

    public void close() {
        Stage stage = (Stage) tagBox.getScene().getWindow();
        stage.close();
    }

    public void click() {
        if (budgetView.getSelectionModel().getSelectedItem() != null) removeBudgetButton.setDisable(false);
    }

    public void addTag() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unicam/cs/pa/jbudget100625/windows/tag.fxml"));
        Parent root = loader.load();
        TagController tagController = loader.getController();
        tagController.setController(controller);
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.setTitle("Tag");
        stage.showAndWait();
        tagBox();
    }

    public void removeBudget() {
        controller.removeBudget(budgetView.getSelectionModel().getSelectedItem());
        createTable();
    }

    private void listReportMethod() {
        listView.getItems().clear();
        listView.getItems().addAll(controller.budgetTags().stream().map(tag -> controller.generateReport().report().get(tag)).collect(Collectors.toList()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BudgetController that = (BudgetController) o;
        return Objects.equals(budgetView, that.budgetView) &&
                Objects.equals(tag, that.tag) &&
                Objects.equals(expected, that.expected) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(tagBox, that.tagBox) &&
                Objects.equals(removeBudgetButton, that.removeBudgetButton) &&
                Objects.equals(controller, that.controller);
    }

    @Override
    public int hashCode() {
        return Objects.hash(budgetView, tag, expected, amount, tagBox, removeBudgetButton, controller);
    }

    @Override
    public String toString() {
        return "BudgetController{" +
                "budgetView=" + budgetView +
                ", tag=" + tag +
                ", expected=" + expected +
                ", amount=" + amount +
                ", tagBox=" + tagBox +
                ", removeBudgetButton=" + removeBudgetButton +
                ", controller=" + controller +
                '}';
    }
}
