
package ni.edu.uam.equiposlaboratorio.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ni.edu.uam.equiposlaboratorio.dao.EquipoDao;
import ni.edu.uam.equiposlaboratorio.dao.TipoEquipoDao;
import ni.edu.uam.equiposlaboratorio.model.Equipo;
import ni.edu.uam.equiposlaboratorio.model.TipoEquipo;

import java.util.List;
import java.util.Optional;

public class EquipoController {

    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtMarca;
    @FXML private TextField txtCantidad;
    @FXML private ComboBox<TipoEquipo> cmbTipoEquipo;
    @FXML private ComboBox<String> cmbEstado;
    @FXML private TextField txtBuscarNombre;
    @FXML private ComboBox<TipoEquipo> cmbFiltroTipo;
    @FXML private TableView<Equipo> tblEquipos;
    @FXML private TableColumn<Equipo, String> colCodigo;
    @FXML private TableColumn<Equipo, String> colNombre;
    @FXML private TableColumn<Equipo, String> colMarca;
    @FXML private TableColumn<Equipo, String> colEstado;
    @FXML private TableColumn<Equipo, Integer> colCantidad;
    @FXML private TableColumn<Equipo, String> colTipo;
    @FXML private Button btnGuardar;
    @FXML private Button btnCargar;
    @FXML private Button btnModificar;
    @FXML private Button btnLimpiar;
    @FXML private Button btnEliminar;
    @FXML private Button btnBuscar;
    @FXML private Button btnMostrarTodos;

    private final EquipoDao equipoDao = new EquipoDao();
    private final TipoEquipoDao tipoEquipoDao = new TipoEquipoDao();

    @FXML
    public void initialize() {

        configurarTableView();
        configurarSeleccionTableView();
        configurarComboBox();

        cargarTipos();
        cargarEstados();
        cargarEquipos();

        btnModificar.setDisable(true);
        btnEliminar.setDisable(true);
    }

    private void configurarTableView() {

        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        colTipo.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getTipoEquipo().getNombre()
                )
        );
    }

    private void configurarSeleccionTableView() {

        tblEquipos.getSelectionModel().selectedItemProperty()
                .addListener((observable, anterior, seleccionado) -> {

                    if (seleccionado != null) {

                        cargarEquipoEnFormulario(seleccionado);

                        btnModificar.setDisable(false);
                        btnEliminar.setDisable(false);

                    } else {

                        btnModificar.setDisable(true);
                        btnEliminar.setDisable(true);
                    }
                });
    }

    private void cargarEquipoEnFormulario(Equipo equipo) {

        txtCodigo.setText(equipo.getCodigo());
        txtNombre.setText(equipo.getNombre());
        txtMarca.setText(equipo.getMarca());
        txtCantidad.setText(String.valueOf(equipo.getCantidad()));

        cmbEstado.setValue(equipo.getEstado());

        for (TipoEquipo tipo : cmbTipoEquipo.getItems()) {
            if (tipo.getIdTipo().equals(
                    equipo.getTipoEquipo().getIdTipo())) {

                cmbTipoEquipo.setValue(tipo);
                break;
            }
        }
    }

    private void configurarComboBox() {

        cmbTipoEquipo.setCellFactory(lista -> new ListCell<>() {

            @Override
            protected void updateItem(TipoEquipo tipo, boolean empty) {
                super.updateItem(tipo, empty);

                if (empty || tipo == null) {
                    setText(null);
                } else {
                    setText(tipo.getNombre());
                }
            }
        });

        cmbTipoEquipo.setButtonCell(new ListCell<>() {

            @Override
            protected void updateItem(TipoEquipo tipo, boolean empty) {
                super.updateItem(tipo, empty);

                if (empty || tipo == null) {
                    setText(null);
                } else {
                    setText(tipo.getNombre());
                }
            }
        });

        cmbFiltroTipo.setCellFactory(lista -> new ListCell<>() {

            @Override
            protected void updateItem(TipoEquipo tipo, boolean empty) {
                super.updateItem(tipo, empty);

                if (empty || tipo == null) {
                    setText(null);
                } else {
                    setText(tipo.getNombre());
                }
            }
        });

        cmbFiltroTipo.setButtonCell(new ListCell<>() {

            @Override
            protected void updateItem(TipoEquipo tipo, boolean empty) {
                super.updateItem(tipo, empty);

                if (empty || tipo == null) {
                    setText(null);
                } else {
                    setText(tipo.getNombre());
                }
            }
        });
    }

    private void cargarTipos() {

        try {

            List<TipoEquipo> tipos = tipoEquipoDao.listar();

            cmbTipoEquipo.setItems(
                    FXCollections.observableArrayList(tipos));

            cmbFiltroTipo.setItems(
                    FXCollections.observableArrayList(tipos));

        } catch (Exception ex) {

            ex.printStackTrace();

            mostrarAlerta(Alert.AlertType.ERROR,
                    "Error al cargar los tipos de equipo: " + ex.getMessage());
        }
    }

    private void cargarEstados() {

        cmbEstado.setItems(FXCollections.observableArrayList(
                "Disponible",
                "En mantenimiento",
                "Fuera de servicio"
        ));
    }

    @FXML
    private void cargarEquipos() {

        try {

            List<Equipo> equipos = equipoDao.listar();

            tblEquipos.setItems(
                    FXCollections.observableArrayList(equipos));

        } catch (Exception ex) {

            ex.printStackTrace();

            mostrarAlerta(Alert.AlertType.ERROR,
                    "Error al cargar los equipos: " + ex.getMessage());
        }
    }

    @FXML
    private void guardarEquipo() {

        try {

            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            String marca = txtMarca.getText().trim();
            String estado = cmbEstado.getValue();
            TipoEquipo tipo = cmbTipoEquipo.getValue();

            if (codigo.isEmpty() || nombre.isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING,
                        "El código y el nombre son obligatorios.");
                return;
            }

            if (tipo == null) {
                mostrarAlerta(Alert.AlertType.WARNING,
                        "Debe seleccionar un tipo de equipo.");
                return;
            }

            if (estado == null) {
                mostrarAlerta(Alert.AlertType.WARNING,
                        "Debe seleccionar un estado.");
                return;
            }

            if (txtCantidad.getText().trim().isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING,
                        "Debe ingresar la cantidad.");
                return;
            }

            int cantidad = Integer.parseInt(
                    txtCantidad.getText().trim());

            if (cantidad < 0) {
                mostrarAlerta(Alert.AlertType.WARNING,
                        "La cantidad no puede ser negativa.");
                return;
            }

            Equipo equipo = new Equipo(
                    codigo, nombre, marca, estado, cantidad, tipo
            );

            equipoDao.guardar(equipo);

            mostrarAlerta(Alert.AlertType.INFORMATION,
                    "Equipo guardado correctamente.");

            cargarEquipos();
            limpiarFormulario();

        } catch (NumberFormatException ex) {

            mostrarAlerta(Alert.AlertType.WARNING,
                    "La cantidad debe ser un número entero válido.");

        } catch (Exception ex) {

            mostrarAlerta(Alert.AlertType.ERROR,
                    "No se pudo guardar el equipo. Verifique que el código no esté repetido.");
        }
    }

    @FXML
    private void modificarEquipo() {

        Equipo seleccionado = tblEquipos.getSelectionModel()
                .getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING,
                    "Seleccione un equipo para modificar.");
            return;
        }

        try {

            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            String marca = txtMarca.getText().trim();
            String estado = cmbEstado.getValue();
            TipoEquipo tipo = cmbTipoEquipo.getValue();

            if (codigo.isEmpty() || nombre.isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING,
                        "El código y el nombre son obligatorios.");
                return;
            }

            if (tipo == null || estado == null) {
                mostrarAlerta(Alert.AlertType.WARNING,
                        "Seleccione el tipo de equipo y el estado.");
                return;
            }

            int cantidad = Integer.parseInt(
                    txtCantidad.getText().trim());

            if (cantidad < 0) {
                mostrarAlerta(Alert.AlertType.WARNING,
                        "La cantidad no puede ser negativa.");
                return;
            }

            seleccionado.setCodigo(codigo);
            seleccionado.setNombre(nombre);
            seleccionado.setMarca(marca);
            seleccionado.setEstado(estado);
            seleccionado.setCantidad(cantidad);
            seleccionado.setTipoEquipo(tipo);

            equipoDao.modificar(seleccionado);

            mostrarAlerta(Alert.AlertType.INFORMATION,
                    "Equipo modificado correctamente.");

            cargarEquipos();
            limpiarFormulario();

        } catch (NumberFormatException ex) {

            mostrarAlerta(Alert.AlertType.WARNING,
                    "La cantidad debe ser un número entero válido.");

        } catch (Exception ex) {

            mostrarAlerta(Alert.AlertType.ERROR,
                    "No se pudo modificar el equipo. Verifique los datos y que el código no esté repetido.");
        }
    }

    @FXML
    private void eliminarEquipo() {

        Equipo seleccionado = tblEquipos.getSelectionModel()
                .getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING,
                    "Seleccione un equipo para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(
                Alert.AlertType.CONFIRMATION
        );

        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText(
                "¿Está seguro de eliminar el equipo seleccionado?"
        );

        Optional<ButtonType> respuesta =
                confirmacion.showAndWait();

        if (respuesta.isPresent()
                && respuesta.get() == ButtonType.OK) {

            try {

                equipoDao.eliminar(seleccionado.getIdEquipo());

                mostrarAlerta(Alert.AlertType.INFORMATION,
                        "Equipo eliminado correctamente.");

                cargarEquipos();
                limpiarFormulario();

            } catch (Exception ex) {

                mostrarAlerta(Alert.AlertType.ERROR,
                        "No se pudo eliminar el equipo.");
            }
        }
    }

    @FXML
    private void buscarEquipos() {

        try {

            String nombre = txtBuscarNombre.getText().trim();
            TipoEquipo tipo = cmbFiltroTipo.getValue();

            List<Equipo> equipos = equipoDao.buscar(nombre, tipo);

            tblEquipos.setItems(
                    FXCollections.observableArrayList(equipos));

        } catch (Exception ex) {

            mostrarAlerta(Alert.AlertType.ERROR,
                    "No se pudo realizar la búsqueda.");
        }
    }

    @FXML
    private void limpiarFormulario() {

        txtCodigo.clear();
        txtNombre.clear();
        txtMarca.clear();
        txtCantidad.clear();

        cmbTipoEquipo.getSelectionModel().clearSelection();
        cmbEstado.getSelectionModel().clearSelection();

        tblEquipos.getSelectionModel().clearSelection();

        btnModificar.setDisable(true);
        btnEliminar.setDisable(true);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String mensaje) {

        Alert alerta = new Alert(tipo);
        alerta.setTitle("Control de Equipos");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
