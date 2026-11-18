package com.sira.controller;

import com.sira.model.Animal;
import com.sira.model.Datos;
import com.sira.model.HistorialMedico;
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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AnimalFichaController implements Initializable {

    @FXML private Label lblTituloVentana;
    @FXML private Label lblHeaderTag;
    @FXML private Label lblHeaderDesc;
    @FXML private Label lblMensajeError;

    @FXML private TextField txtNombre;
    @FXML private Label lblErrorNombre;

    @FXML private TextField txtEspecie;
    @FXML private Label lblErrorEspecie;

    @FXML private TextField txtEstado;
    @FXML private TextField txtSexo;

    @FXML private VBox seccionHistorial;
    @FXML private TableView<HistorialMedico> tablaHistorial;
    @FXML private TableColumn<HistorialMedico, String> colFecha;
    @FXML private TableColumn<HistorialMedico, String> colTipo;

    @FXML private Button btnGuardar;
    @FXML private Button btnDeshacer;
    @FXML private Button btnCerrar;
    @FXML private Button btnNuevaAdopcion;

    private Animal animalActual;
    private Animal animalGuardado;
    private boolean modoNuevo = false;
    private ObservableList<HistorialMedico> historialList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        historialList = FXCollections.observableArrayList(
                new HistorialMedico("02/09", "Vacuna"),
                new HistorialMedico("18/09", "Control")
        );
        tablaHistorial.setItems(historialList);

        // Listeners para quitar el estado de error (color rojo) cuando el usuario escribe
        txtNombre.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.trim().isEmpty()) {
                limpiarErrorCampo(txtNombre, lblErrorNombre);
            }
        });

        txtEspecie.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.trim().isEmpty()) {
                limpiarErrorCampo(txtEspecie, lblErrorEspecie);
            }
        });
    }

    /**
     * Configura el formulario en modo de alta (Nuevo Animal).
     */
    public void setModoNuevo(boolean modoNuevo) {
        this.modoNuevo = modoNuevo;
        this.animalActual = null;
        this.animalGuardado = null;

        if (lblTituloVentana != null) lblTituloVentana.setText("Nuevo Animal   modal");
        if (lblHeaderTag != null) lblHeaderTag.setText("AnimalFichaView.fxml (Alta)");
        if (lblHeaderDesc != null) lblHeaderDesc.setText("Formulario de registro — Datos críticos: Nombre y Especie");

        txtNombre.clear();
        txtEspecie.clear();
        txtEstado.setText("EN_REFUGIO");
        txtSexo.setText("hembra");

        limpiarTodosLosErrores();

        // En alta no se muestran adopción ni historial previo
        if (btnNuevaAdopcion != null) {
            btnNuevaAdopcion.setVisible(false);
            btnNuevaAdopcion.setManaged(false);
        }
        if (seccionHistorial != null) {
            tablaHistorial.setItems(FXCollections.observableArrayList());
        }
    }

    /**
     * Carga un animal existente para su edición o visualización de ficha.
     */
    public void setAnimal(Animal animal) {
        this.modoNuevo = false;
        this.animalActual = animal;
        this.animalGuardado = null;

        if (lblTituloVentana != null) lblTituloVentana.setText("Ficha animal   modal");
        if (lblHeaderTag != null) lblHeaderTag.setText("AnimalFichaView.fxml");
        if (lblHeaderDesc != null) lblHeaderDesc.setText("Recibe: Animal " + (animal != null ? animal.getNombre() : "") + " + callback");

        if (animal != null) {
            txtNombre.setText(animal.getNombre());
            txtEspecie.setText(animal.getEspecie());
            txtEstado.setText(animal.getEstado());
            txtSexo.setText(animal.getSexo() != null ? animal.getSexo() : "hembra");
        }

        limpiarTodosLosErrores();

        if (btnNuevaAdopcion != null) {
            btnNuevaAdopcion.setVisible(true);
            btnNuevaAdopcion.setManaged(true);
        }
    }

    public Animal getAnimalGuardado() {
        return animalGuardado;
    }

    @FXML
    public void handleGuardar(ActionEvent event) {
        limpiarTodosLosErrores();

        String nombre = txtNombre.getText() != null ? txtNombre.getText().trim() : "";
        String especie = txtEspecie.getText() != null ? txtEspecie.getText().trim() : "";
        String estado = txtEstado.getText() != null ? txtEstado.getText().trim() : "EN_REFUGIO";
        String sexo = txtSexo.getText() != null ? txtSexo.getText().trim() : "hembra";

        boolean hayError = false;

        // Validación de datos críticos requerida: si falta algún dato crítico,
        // no cierra el formulario y avisa con color rojo qué dato falta ingresar.
        if (nombre.isEmpty()) {
            marcarErrorCampo(txtNombre, lblErrorNombre);
            hayError = true;
        }

        if (especie.isEmpty()) {
            marcarErrorCampo(txtEspecie, lblErrorEspecie);
            hayError = true;
        }

        if (hayError) {
            if (lblMensajeError != null) {
                lblMensajeError.setText("¡Atención! Complete los datos críticos obligatorios marcados en rojo.");
                lblMensajeError.setVisible(true);
                lblMensajeError.setManaged(true);
            }
            // NO CIERRA EL FORMULARIO
            return;
        }

        // Si los datos son válidos:
        if (modoNuevo) {
            String codigo = "A-" + String.format("%03d", Datos.listar().size() + 1);
            String fechaHoy = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            Animal nuevo = new Animal(codigo, nombre, especie, estado, sexo, fechaHoy);

            // Se agrega a la lista de datos en memoria mediante Datos.agregar
            Datos.agregar(nuevo);
            this.animalGuardado = nuevo;
            this.animalActual = nuevo;
        } else {
            if (animalActual != null) {
                animalActual.setNombre(nombre);
                animalActual.setEspecie(especie);
                animalActual.setEstado(estado);
                animalActual.setSexo(sexo);
                this.animalGuardado = animalActual;
            }
        }

        // Cierra el formulario únicamente tras la validación exitosa
        Stage stage = (Stage) btnGuardar.getScene().getWindow();
        stage.close();
    }

    private void marcarErrorCampo(TextField campo, Label labelError) {
        if (!campo.getStyleClass().contains("text-field-error")) {
            campo.getStyleClass().add("text-field-error");
        }
        if (labelError != null) {
            labelError.setVisible(true);
            labelError.setManaged(true);
        }
    }

    private void limpiarErrorCampo(TextField campo, Label labelError) {
        campo.getStyleClass().remove("text-field-error");
        if (labelError != null) {
            labelError.setVisible(false);
            labelError.setManaged(false);
        }
        // Si no hay más errores en campos, ocultar banner general
        if (!txtNombre.getStyleClass().contains("text-field-error") &&
            !txtEspecie.getStyleClass().contains("text-field-error")) {
            if (lblMensajeError != null) {
                lblMensajeError.setVisible(false);
                lblMensajeError.setManaged(false);
            }
        }
    }

    private void limpiarTodosLosErrores() {
        txtNombre.getStyleClass().remove("text-field-error");
        txtEspecie.getStyleClass().remove("text-field-error");
        if (lblErrorNombre != null) {
            lblErrorNombre.setVisible(false);
            lblErrorNombre.setManaged(false);
        }
        if (lblErrorEspecie != null) {
            lblErrorEspecie.setVisible(false);
            lblErrorEspecie.setManaged(false);
        }
        if (lblMensajeError != null) {
            lblMensajeError.setVisible(false);
            lblMensajeError.setManaged(false);
        }
    }

    @FXML
    public void handleDeshacer(ActionEvent event) {
        limpiarTodosLosErrores();
        if (modoNuevo) {
            txtNombre.clear();
            txtEspecie.clear();
            txtEstado.setText("EN_REFUGIO");
            txtSexo.setText("hembra");
        } else if (animalActual != null) {
            setAnimal(animalActual);
        }
    }

    @FXML
    public void handleCerrar(ActionEvent event) {
        animalGuardado = null;
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
