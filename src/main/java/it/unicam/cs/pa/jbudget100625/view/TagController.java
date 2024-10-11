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
import it.unicam.cs.pa.jbudget100625.model.tag.Tag;
import it.unicam.cs.pa.jbudget100625.model.tag.TagImplementation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Alex Citeroni
 */
public class TagController {
    @FXML
    private TextField tagName, tagDescription;
    @FXML
    private Button addTagButton, transactionTagButton, removeTagButton;
    @FXML
    private TableView<Tag> tagView = new TableView<>();
    @FXML
    private TableColumn<Tag, String> nameTag, descriptionTag;

    private Controller controller;

    private List<Tag> tags = new ArrayList<>();

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Tag> getTags() {
        return this.tags;
    }

    public void setController(Controller controller) {
        this.controller = controller;
        createTagTable();
    }

    public void addTag() {
        controller.addLedgerTag(new TagImplementation(new Date().hashCode(), tagName.getText(), tagDescription.getText()));
        tagName.clear();
        tagDescription.clear();
        createTagTable();
    }

    public void enableButton() {
        addTagButton.setDisable(tagName.getText().trim().isEmpty() || tagDescription.getText().trim().isEmpty());
    }

    private ObservableList<Tag> getTag() {
        ObservableList<Tag> observableList = FXCollections.observableArrayList();
        observableList.addAll(controller.getLedgerTags());
        return observableList;
    }

    private void createTagTable() {
        nameTag.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionTag.setCellValueFactory(new PropertyValueFactory<>("description"));
        tagView.setItems(getTag());
    }

    public void addTransactionTag() {
        if (!tags.contains(tagView.getSelectionModel().getSelectedItem()))
            tags.add(tagView.getSelectionModel().getSelectedItem());
        Stage stage = (Stage) tagName.getScene().getWindow();
        stage.close();
    }

    public void click() {
        if (tagView.getSelectionModel().getSelectedItem() != null) {
            transactionTagButton.setDisable(false);
            removeTagButton.setDisable(false);
        }
    }

    public void removeTag() {
        controller.removeLedgerTag(tagView.getSelectionModel().getSelectedItem());
        createTagTable();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagController that = (TagController) o;
        return Objects.equals(tagName, that.tagName) &&
                Objects.equals(tagDescription, that.tagDescription) &&
                Objects.equals(addTagButton, that.addTagButton) &&
                Objects.equals(transactionTagButton, that.transactionTagButton) &&
                Objects.equals(removeTagButton, that.removeTagButton) &&
                Objects.equals(tagView, that.tagView) &&
                Objects.equals(nameTag, that.nameTag) &&
                Objects.equals(descriptionTag, that.descriptionTag) &&
                Objects.equals(controller, that.controller) &&
                Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagName, tagDescription, addTagButton, transactionTagButton, removeTagButton, tagView, nameTag, descriptionTag, controller, tags);
    }

    @Override
    public String toString() {
        return "TagController{" +
                "tagName=" + tagName +
                ", tagDescription=" + tagDescription +
                ", addTagButton=" + addTagButton +
                ", transactionTagButton=" + transactionTagButton +
                ", removeTagButton=" + removeTagButton +
                ", tagView=" + tagView +
                ", nameTag=" + nameTag +
                ", descriptionTag=" + descriptionTag +
                ", controller=" + controller +
                ", tags=" + tags +
                '}';
    }
}
