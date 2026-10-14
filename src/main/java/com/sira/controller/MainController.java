package com.sira.controller;

import com.sira.model.Animal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private TableView<Animal> tablaAnimales;
    @FXML private TableColumn<Animal, String> colNombre;
    @FXML private TableColumn<Animal, String> colEspecie;
    @FXML private TableColumn<Animal, String> colEstado;

    @FXML private TextField txtBuscar;
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnRefrescar;

    @FXML private Label lblResumenTitulo;
    @FXML private Label lblResumenSub;
    @FXML private Label lblResumenFecha;
    @FXML private Label lblResumenEstado;

    @FXML private Button btnAbrirFicha;
    @FXML private Button btnNuevaAdopcion;

    private ObservableList<Animal> listaAnimales;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configurar columnas de la tabla
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEspecie.setCellValueFactory(new PropertyValueFactory<>("especie"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Datos iniciales exactamente como en la imagen
        listaAnimales = FXCollections.observableArrayList(
                new Animal("Luna", "Perro", "EN_REFUGIO", "hembra", "12/08/2026"),
                new Animal("Mora", "Gato", "EN_TRATAMIENTO", "hembra", "01/09/2026"),
                new Animal("Otto", "Perro", "RESERVADO", "macho", "15/07/2026"),
                new Animal("Pipa", "Gato", "ADOPTADO", "hembra", "10/06/2026"),
                new Animal("Roco", "Perro", "EN_REFUGIO", "macho", "20/08/2026")
        );

        tablaAnimales.setItems(listaAnimales);

        // Seleccionar Luna por defecto
        tablaAnimales.getSelectionModel().selectFirst();
        actualizarResumen(listaAnimales.get(0));

        // Listener de selección
        tablaAnimales.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                actualizarResumen(newVal);
            }
        });
    }

    private void actualizarResumen(Animal animal) {
        lblResumenTitulo.setText("Resumen del seleccionado");
        lblResumenSub.setText(animal.getNombre() + " · " + animal.getEspecie() + " · " + animal.getSexo());
        lblResumenFecha.setText("Ingreso " + animal.getFechaIngreso());
        lblResumenEstado.setText("Estado " + animal.getEstado());
    }

    @FXML
    public void handleAbrirFicha(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sira/view/AnimalFichaView.fxml"));
            Parent root = loader.load();

            AnimalFichaController controller = loader.getController();
            Animal seleccionado = tablaAnimales.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                controller.setAnimal(seleccionado);
            }

            Stage stage = new Stage();
            stage.setTitle("Ficha animal modal");
            stage.initModality(Modality.WINDOW_MODAL);
            Stage principal = (Stage) btnAbrirFicha.getScene().getWindow();
            stage.initOwner(principal);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleNuevaAdopcion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sira/view/AdopcionFormView.fxml"));
            Parent root = loader.load();

            AdopcionFormController controller = loader.getController();
            Animal seleccionado = tablaAnimales.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                controller.setAnimal(seleccionado);
            }

            Stage stage = new Stage();
            stage.setTitle("Nueva adopción modal");
            stage.initModality(Modality.WINDOW_MODAL);
            Stage principal = (Stage) btnNuevaAdopcion.getScene().getWindow();
            stage.initOwner(principal);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleNuevo(ActionEvent event) {
        // Sin funcionalidad de persistencia según especificación
    }

    @FXML
    public void handleEditar(ActionEvent event) {
        handleAbrirFicha(event);
    }

    @FXML
    public void handleRefrescar(ActionEvent event) {
        tablaAnimales.refresh();
    }
}
