package com.example.basejavafxhibernate;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    VBox vBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        HibernateUtil.inicia();

        RepositorioEditoriales repositorioEditoriales=new RepositorioEditoriales();
        //repositorioEditoriales.insertar(new Editorial("Bruguera", "España"));
        ObservableList<Editorial> lista=repositorioEditoriales.listarTodas();

        TableView<Editorial> tblEditoriales=new TableView<>(lista);
        TableColumn<Editorial, Integer> colId=new TableColumn<>("Id");
        TableColumn<Editorial, String> colEditorial=new TableColumn<>("Editorial");
        TableColumn<Editorial, String> colPais=new TableColumn<>("País");

        colId.setCellValueFactory(new PropertyValueFactory<Editorial, Integer>("id"));
        colEditorial.setCellValueFactory(new PropertyValueFactory<Editorial, String>("editorial"));
        colPais.setCellValueFactory(new PropertyValueFactory<Editorial, String>("pais"));

        tblEditoriales.getColumns().addAll(colId, colEditorial, colPais);
        vBox.getChildren().add(tblEditoriales);

        Label lblId=new Label();
        TextField txtEditorial=new TextField();
        txtEditorial.setPromptText("Escribe el nombre de la editorial");
        TextField txtPais=new TextField();
        txtPais.setPromptText("Escribe el país");
        vBox.getChildren().addAll(lblId, txtEditorial, txtPais);

        tblEditoriales.setOnMouseClicked(e -> {
            Editorial editorial =tblEditoriales.getSelectionModel().getSelectedItem();
            lblId.setText(String.valueOf(editorial.getId()));
            txtEditorial.setText(editorial.getEditorial());
            txtPais.setText(editorial.getPais());
        });

        Button btnInsertar=new Button("Insertar");
        btnInsertar.setOnAction(e->{
            repositorioEditoriales.insertar(new Editorial(txtEditorial.getText(), txtPais.getText()));
        });
        vBox.getChildren().add(btnInsertar);

    }
}