package com.sira.controller;

import com.sira.model.Animal;
import com.sira.model.Datos;
import javafx.application.Platform;
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
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML private TableView<Animal> tablaAnimales;
    @FXML private TableColumn<Animal, String> colNombre;
    @FXML private TableColumn<Animal, String> colEspecie;
    @FXML private TableColumn<Animal, String> colEstado;

    @FXML private TextField txtBuscar;
    @FXML private Button btnBuscar;
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
        // Configurar columnas de la tabla vinculadas a la Entidad Animal
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEspecie.setCellValueFactory(new PropertyValueFactory<>("especie"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Cargar datos en memoria desde Datos.java usando Datos.listar()
        listaAnimales = FXCollections.observableArrayList(Datos.listar());
        tablaAnimales.setItems(listaAnimales);

        // Seleccionar el primer registro por defecto si existe
        if (!listaAnimales.isEmpty()) {
            tablaAnimales.getSelectionModel().selectFirst();
            actualizarResumen(listaAnimales.get(0));
        }

        // Listener de cambio de selección en la tabla
        tablaAnimales.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                actualizarResumen(newVal);
            }
        });

        // Doble clic en una fila abre la ficha del animal
        tablaAnimales.setRowFactory(tv -> {
            TableRow<Animal> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    handleAbrirFicha(null);
                }
            });
            return row;
        });

        // Búsqueda en tiempo real que invoca el método Datos.buscar(...)
        if (txtBuscar != null) {
            txtBuscar.textProperty().addListener((obs, oldVal, newVal) -> {
                ejecutarBusqueda(newVal);
            });
        }
    }

    private void ejecutarBusqueda(String criterio) {
        List<Animal> encontrados = Datos.buscar(criterio);
        listaAnimales.setAll(encontrados);

        if (!listaAnimales.isEmpty()) {
            tablaAnimales.getSelectionModel().selectFirst();
            actualizarResumen(listaAnimales.get(0));
        } else {
            limpiarResumen();
        }
    }

    private void actualizarResumen(Animal animal) {
        if (animal == null) {
            limpiarResumen();
            return;
        }
        lblResumenTitulo.setText("Resumen del seleccionado");
        lblResumenSub.setText(animal.getNombre() + " · " + animal.getEspecie() + " · " + animal.getSexo());
        lblResumenFecha.setText("Ingreso " + animal.getFechaIngreso());
        lblResumenEstado.setText("Estado " + animal.getEstado());
    }

    private void limpiarResumen() {
        lblResumenTitulo.setText("Sin selección");
        lblResumenSub.setText("No se encontraron animales");
        lblResumenFecha.setText("");
        lblResumenEstado.setText("");
    }

    @FXML
    public void handleBuscar(ActionEvent event) {
        String query = txtBuscar != null ? txtBuscar.getText() : "";
        ejecutarBusqueda(query);
    }

    @FXML
    public void handleNuevo(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sira/view/AnimalFichaView.fxml"));
            Parent root = loader.load();

            AnimalFichaController controller = loader.getController();
            // Modo nuevo: campos limpios para ingresar datos
            controller.setModoNuevo(true);

            Stage stage = new Stage();
            stage.setTitle("Nuevo Animal (Formulario)");
            stage.initModality(Modality.WINDOW_MODAL);
            Stage principal = (Stage) btnNuevo.getScene().getWindow();
            stage.initOwner(principal);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();

            // Si se agregó un nuevo objeto a Datos.java, refrescar la tabla y seleccionarlo
            Animal nuevoGuardado = controller.getAnimalGuardado();
            if (nuevoGuardado != null) {
                if (txtBuscar != null) {
                    txtBuscar.clear();
                }
                listaAnimales.setAll(Datos.listar());
                tablaAnimales.refresh();
                tablaAnimales.getSelectionModel().select(nuevoGuardado);
                actualizarResumen(nuevoGuardado);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleEditar(ActionEvent event) {
        handleAbrirFicha(event);
    }

    @FXML
    public void handleAbrirFicha(ActionEvent event) {
        try {
            Animal seleccionado = tablaAnimales.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                if (!listaAnimales.isEmpty()) {
                    seleccionado = listaAnimales.get(0);
                    tablaAnimales.getSelectionModel().select(seleccionado);
                } else {
                    return;
                }
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/sira/view/AnimalFichaView.fxml"));
            Parent root = loader.load();

            AnimalFichaController controller = loader.getController();
            controller.setAnimal(seleccionado);

            Stage stage = new Stage();
            stage.setTitle("Ficha animal modal");
            stage.initModality(Modality.WINDOW_MODAL);
            Stage principal = (Stage) btnNuevo.getScene().getWindow();
            stage.initOwner(principal);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();

            // Refrescar tabla y resumen tras cerrar la ficha
            listaAnimales.setAll(Datos.listar());
            tablaAnimales.refresh();
            if (controller.getAnimalGuardado() != null) {
                tablaAnimales.getSelectionModel().select(controller.getAnimalGuardado());
                actualizarResumen(controller.getAnimalGuardado());
            } else if (seleccionado != null) {
                actualizarResumen(seleccionado);
            }
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

            // Refrescar tabla y resumen tras confirmar la adopción
            listaAnimales.setAll(Datos.listar());
            tablaAnimales.refresh();
            if (seleccionado != null) {
                actualizarResumen(seleccionado);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRefrescar(ActionEvent event) {
        if (txtBuscar != null) {
            txtBuscar.clear();
        }
        listaAnimales.setAll(Datos.listar());
        tablaAnimales.refresh();
        if (!listaAnimales.isEmpty()) {
            tablaAnimales.getSelectionModel().selectFirst();
            actualizarResumen(listaAnimales.get(0));
        } else {
            limpiarResumen();
        }
    }

    @FXML
    public void handleSalir() {
        Platform.exit();
    }
}
