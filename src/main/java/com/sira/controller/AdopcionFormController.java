package com.sira.controller;

import com.sira.model.Animal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdopcionFormController implements Initializable {

    @FXML private Label lblInfoAnimal;
    @FXML private TextField txtAdoptante;
    @FXML private Button btnConfirmar;
    @FXML private Button btnCancelar;

    private Animal animal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (lblInfoAnimal != null) {
            lblInfoAnimal.setText("Animal ya cargado: Luna");
        }
        if (txtAdoptante != null) {
            txtAdoptante.setText("Ana Pérez");
        }
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
        if (lblInfoAnimal != null && animal != null) {
            lblInfoAnimal.setText("Animal ya cargado: " + animal.getNombre());
        }
    }

    @FXML
    public void handleConfirmar(ActionEvent event) {
        if (animal != null) {
            animal.setEstado("ADOPTADO");
        }
        Stage stage = (Stage) btnConfirmar.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleCancelar(ActionEvent event) {
        Stage stage;
        if (btnCancelar != null && btnCancelar.getScene() != null) {
            stage = (Stage) btnCancelar.getScene().getWindow();
        } else if (btnConfirmar != null && btnConfirmar.getScene() != null) {
            stage = (Stage) btnConfirmar.getScene().getWindow();
        } else {
            return;
        }
        stage.close();
    }
}
