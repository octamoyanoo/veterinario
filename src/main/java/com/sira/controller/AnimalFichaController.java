package com.sira.controller;

import com.sira.model.Animal;
import com.sira.model.HistorialMedico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AnimalFichaController implements Initializable {

    @FXML private TextField txtNombre;
    @FXML private TextField txtEspecie;
    @FXML private TextField txtEstado;

    @FXML private TableView<HistorialMedico> tablaHistorial;
    @FXML private TableColumn<HistorialMedico, String> colFecha;
    @FXML private TableColumn<HistorialMedico, String> colTipo;

    @FXML private Button btnGuardar;
    @FXML private Button btnDeshacer;
    @FXML private Button btnCerrar;

    private Animal animalActual;
    private ObservableList<HistorialMedico> historialList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        // Datos del historial clínico como en la imagen
        historialList = FXCollections.observableArrayList(
                new HistorialMedico("02/09", "Vacuna"),
                new HistorialMedico("18/09", "Control")
        );
        tablaHistorial.setItems(historialList);

        // Valores por defecto
        txtNombre.setText("Luna");
        txtEspecie.setText("Perro");
        txtEstado.setText("EN_REFUGIO");
    }

    public void setAnimal(Animal animal) {
        this.animalActual = animal;
        if (animal != null) {
            txtNombre.setText(animal.getNombre());
            txtEspecie.setText(animal.getEspecie());
            txtEstado.setText(animal.getEstado());
        }
    }

    @FXML
    public void handleGuardar(ActionEvent event) {
        // Sin lógica de persistencia según especificación
    }

    @FXML
    public void handleDeshacer(ActionEvent event) {
        if (animalActual != null) {
            setAnimal(animalActual);
        } else {
            txtNombre.setText("Luna");
            txtEspecie.setText("Perro");
            txtEstado.setText("EN_REFUGIO");
        }
    }

    @FXML
    public void handleCerrar(ActionEvent event) {
        Stage stage = (Stage) btnCerrar.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleAbrirAdopcion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sira/view/AdopcionFormView.fxml"));
            Parent root = loader.load();

            AdopcionFormController controller = loader.getController();
            if (animalActual != null) {
                controller.setAnimal(animalActual);
            }

            Stage stage = new Stage();
            stage.setTitle("Nueva adopción modal");
            stage.initModality(Modality.WINDOW_MODAL);
            Stage owner = (Stage) btnGuardar.getScene().getWindow();
            stage.initOwner(owner);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
