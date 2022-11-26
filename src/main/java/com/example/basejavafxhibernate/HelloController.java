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
import org.w3c.dom.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    VBox vBox, vBoxLibros;
    TableView<Editorial> tblEditoriales;
    TableView<Libro> tblLibros;
    RepositorioEditoriales repositorioEditoriales;
    RepositorioLibros repositorioLibros;

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
            lblId.setText(String.valueOf(editorial.getId()));
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
        actualizarTablaEditoriales();

        //Empiezo la creación de la tabla de libros
        repositorioLibros=new RepositorioLibros();
        tblLibros=new TableView<>(repositorioLibros.listarTodos());
        tblLibros.setPrefSize(300, 400);
        TableColumn<Libro, Integer> colIdLibro=new TableColumn<>("Id");
        TableColumn<Libro, Editorial> colEditorialLibro=new TableColumn<>("Editorial");
        TableColumn<Libro, String> colTitulo=new TableColumn<>("Titulo");
        TableColumn<Libro, String> colAutor=new TableColumn<>("Autor");

        colIdLibro.setCellValueFactory(new PropertyValueFactory<Libro, Integer>("id"));
        colEditorialLibro.setCellValueFactory(new PropertyValueFactory<Libro, Editorial>("editorial"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<Libro, String>("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory<Libro, String>("autor"));

        tblLibros.getColumns().addAll(colIdLibro, colEditorialLibro, colTitulo, colAutor);
        vBoxLibros.getChildren().add(tblLibros);

        HBox hBoxTextFieldsLibro=new HBox(20);
        Label lblIdLibro=new Label("");
        ComboBox<Editorial> comboEditorial=new ComboBox<>(repositorioEditoriales.listarTodas());
        TextField txtTitulo=new TextField();
        TextField txtAutor=new TextField();
        hBoxTextFieldsLibro.getChildren().addAll(lblIdLibro, comboEditorial, txtTitulo, txtAutor);
        vBoxLibros.getChildren().add(hBoxTextFieldsLibro);

        HBox botoneraLibro=new HBox(20);

        Button btnNuevoLibro=new Button("Nuevo");
        btnNuevoLibro.setOnAction(e->{
            comboEditorial.getSelectionModel().select(0);
            lblIdLibro  .setText("0");
            txtTitulo.setText("");
            txtAutor.setText("");
        });

        Button btnInsertarLibro=new Button("Insertar");
        btnInsertarLibro.setOnAction(e->{
            repositorioLibros.insertar(new Libro(comboEditorial.getSelectionModel().getSelectedItem(), txtTitulo.getText(), txtAutor.getText()));
            actualizarTablaLibros();
        });

        Button btnModificarLibro=new Button("Modificar");
        btnModificarLibro.setOnAction(e->{
            repositorioLibros.modificar(new Libro(Integer.parseInt(lblIdLibro.getText()), comboEditorial.getSelectionModel().getSelectedItem(), txtTitulo.getText(), txtAutor.getText()));
            actualizarTablaLibros();
        });

        Button btnBorrarLibro=new Button("Borrar");
        btnBorrarLibro.setOnAction(e->{
            //Para borrar solo necesito el id
            repositorioLibros.borrar(new Libro(Integer.parseInt(lblIdLibro.getText()), null, "", ""));
            actualizarTablaLibros();
        });
        botoneraLibro.getChildren().addAll(btnNuevoLibro, btnInsertarLibro, btnModificarLibro, btnBorrarLibro);
        vBoxLibros.getChildren().add(botoneraLibro);

        tblLibros.setOnMouseClicked(e -> {
            Libro libro =tblLibros.getSelectionModel().getSelectedItem();
            lblIdLibro.setText(String.valueOf(libro.getId()));
            comboEditorial.getSelectionModel().select(libro.getEditorial());
            txtTitulo.setText(libro.getTitulo());
            txtAutor.setText(libro.getAutor());
        });
    }
    public void actualizarTablaEditoriales(){
        ObservableList<Editorial> lista=repositorioEditoriales.listarTodas();
        tblEditoriales.setItems(lista);
    }

    public void actualizarTablaLibros(){
        ObservableList<Libro> lista=repositorioLibros.listarTodos();
        tblLibros.setItems(lista);
    }
}