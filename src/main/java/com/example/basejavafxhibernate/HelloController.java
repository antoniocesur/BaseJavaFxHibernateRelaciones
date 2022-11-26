package com.example.basejavafxhibernate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    VBox vBox;
    TableView<Editorial> tblEditoriales;
    RepositorioEditoriales repositorioEditoriales;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        HibernateUtil.inicia();

        repositorioEditoriales=new RepositorioEditoriales();
        ObservableList<Editorial> lista=repositorioEditoriales.listarTodas();
        if(lista.size()<1) {
            repositorioEditoriales.insertar(new Editorial("Bruguera", "España"));
        }

        tblEditoriales=new TableView<Editorial>(lista);
        tblEditoriales.setPrefSize(300, 400);
        TableColumn<Editorial, Integer> colId=new TableColumn<>("Id");
        TableColumn<Editorial, String> colEditorial=new TableColumn<>("Editorial");
        TableColumn<Editorial, String> colPais=new TableColumn<>("País");

        colId.setCellValueFactory(new PropertyValueFactory<Editorial, Integer>("id"));
        colEditorial.setCellValueFactory(new PropertyValueFactory<Editorial, String>("editorial"));
        colPais.setCellValueFactory(new PropertyValueFactory<Editorial, String>("pais"));

        tblEditoriales.getColumns().addAll(colId, colEditorial, colPais);
        vBox.getChildren().add(tblEditoriales);

        HBox hBoxTextFields=new HBox(20);
        Label lblId=new Label("0");
        TextField txtEditorial=new TextField();
        txtEditorial.setPrefWidth(200);
        txtEditorial.setPromptText("Escribe el nombre de la editorial");

        String[] listaPaises={"España", "Alemania", "Italia", "Francia", "China"};
        ComboBox<String> comboPaises=new ComboBox<>(FXCollections.observableList(Arrays.asList(listaPaises)));
        hBoxTextFields.getChildren().addAll(lblId, txtEditorial, comboPaises);
        vBox.getChildren().add(hBoxTextFields);

        tblEditoriales.setOnMouseClicked(e -> {
            Editorial editorial =tblEditoriales.getSelectionModel().getSelectedItem();
            //lblId.setText(String.valueOf(editorial.getId()));
            txtEditorial.setText(editorial.getEditorial());
            comboPaises.getSelectionModel().select(editorial.getPais());
        });

        HBox botonera=new HBox(20);

        Button btnNuevo=new Button("Nuevo");
        btnNuevo.setOnAction(e->{
            System.out.println(((Control)e.getSource()).getId() );
            comboPaises.getSelectionModel().select(0);
            lblId.setText("0");
            txtEditorial.setText("");

        });
        botonera.getChildren().add(btnNuevo);

        Button btnInsertar=new Button("Insertar");
        btnInsertar.setOnAction(e->{
            repositorioEditoriales.insertar(new Editorial(txtEditorial.getText(), comboPaises.getSelectionModel().getSelectedItem()));
            actualizarTablaEditoriales();
        });
        botonera.getChildren().add(btnInsertar);

        Button btnModificar=new Button("Modificar");
        btnModificar.setOnAction(e->{
            repositorioEditoriales.modificar(new Editorial(Integer.parseInt(lblId.getText()), txtEditorial.getText(), comboPaises.getSelectionModel().getSelectedItem()));
            actualizarTablaEditoriales();
        });
        botonera.getChildren().add(btnModificar);

        Button btnBorrar=new Button("Borrar");
        btnBorrar.setOnAction(e->{
            //Para borrar solo necesito el id
            repositorioEditoriales.borrar(new Editorial(Integer.parseInt(lblId.getText()), "", ""));
            actualizarTablaEditoriales();
        });
        botonera.getChildren().add(btnBorrar);

        vBox.getChildren().add(botonera);
        vBox.setAlignment(Pos.CENTER_LEFT);

    }

    public void actualizarTablaEditoriales(){
        ObservableList<Editorial> lista=repositorioEditoriales.listarTodas();
        tblEditoriales.setItems(lista);
    }
}