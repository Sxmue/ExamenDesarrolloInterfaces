package com.example.exameninterfacesbueno;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class ExamenView
{
    @javafx.fxml.FXML
    private TextField txtMatricula;
    @javafx.fxml.FXML
    private ComboBox<String> comboModelo;
    @javafx.fxml.FXML
    private ComboBox<Cliente> comboCliente;
    @javafx.fxml.FXML
    private RadioButton radioStandard;
    @javafx.fxml.FXML
    private RadioButton radioOferta;
    @javafx.fxml.FXML
    private RadioButton radioLargaDuracion;
    @javafx.fxml.FXML
    private DatePicker datePickerEntrada;
    @javafx.fxml.FXML
    private DatePicker datePickerSalida;
    @javafx.fxml.FXML
    private Label lblCoste;
    @javafx.fxml.FXML
    private Button btnAñadirATabla;
    @javafx.fxml.FXML
    private Button btnSalir;
    @javafx.fxml.FXML
    private TableColumn<Entrada,String> cMatrícula;
    @javafx.fxml.FXML
    private TableColumn<Entrada,String> cModelo;
    @javafx.fxml.FXML
    private TableColumn<Entrada,String> cFechaEntrada;
    @javafx.fxml.FXML
    private TableColumn<Entrada,String> cFechaSalida;
    @javafx.fxml.FXML
    private TableColumn<Entrada,String> cCliente;
    @javafx.fxml.FXML
    private TableColumn<Entrada,String> cTarifa;
    @javafx.fxml.FXML
    private TableColumn<Entrada,Integer> cCoste;
    @javafx.fxml.FXML
    private TableView<Entrada> tablaEntradas;

    ObservableList<Entrada> observableEntradas;

    Cliente cliente1;
    Cliente cliente2;
    Cliente cliente3;
    @javafx.fxml.FXML
    private ToggleGroup toggleTarifa;


    @javafx.fxml.FXML
    public void initialize() {

        observableEntradas= FXCollections.observableArrayList();

        mapearTable();
        rellenarObservableTabla();

        tablaEntradas.getItems().addAll(observableEntradas);
        
        

        comboCliente.setConverter(new StringConverter<Cliente>() {

                                      @Override
                                      public String toString(Cliente cliente) {
                                          if (cliente != null) {

                                              return cliente.getNombre();
                                          } else {

                                              return null;
                                          }
                                      }

                                      @Override
                                      public Cliente fromString(String s) {
                                          return null;
                                      }
                                  }
        );


        comboCliente.getItems().addAll(cliente1,cliente2,cliente3);



        comboModelo.getItems().addAll("RENAULT","HONDA","FORD","SEAT");




    }

    private void mapearTable() {
        cCoste.setCellValueFactory(fila -> new SimpleObjectProperty<>(fila.getValue().getCosteTotal()));
        cCliente.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue().getCliente().getNombre()));
        cMatrícula.setCellValueFactory(fila -> new SimpleStringProperty((fila.getValue().getMatricula())));
        cModelo.setCellValueFactory(fila -> new SimpleStringProperty((fila.getValue().getModelo())));
        cTarifa.setCellValueFactory(fila -> new SimpleStringProperty((fila.getValue().getTipoTarifa())));
        cFechaEntrada.setCellValueFactory(fila -> new SimpleStringProperty((fila.getValue().getFechaEntrada().toString())));
        cFechaSalida.setCellValueFactory(fila -> new SimpleStringProperty((fila.getValue().getFechaSalida().toString())));
    }

    private void rellenarObservableTabla() {
        cliente1 = new Cliente(1L, "Paco Gonzalez", "cliente1@example.com");
        cliente2 = new Cliente(2L, "Flotentino Perez", "cliente2@example.com");
        cliente3 = new Cliente(3L, "Juan Carlos Rivero", "cliente3@example.com");

        LocalDate d1 = LocalDate.now();
        LocalDate d2 = LocalDate.now();
        LocalDate d3 = LocalDate.now();
        LocalDate d4 = LocalDate.now();

        Entrada e1 = new Entrada("JKHD3","HONDA",cliente1,"Standard",d1,d2,30);
        Entrada e2 = new Entrada("HJFAFSH432","RENAULT",cliente2,"Oferta",d3,d4,50);
        Entrada e3 = new Entrada("POSNFAS432","FORD",cliente3,"Larga Duración",d2,d3,150);

        observableEntradas.add(e1);
        observableEntradas.add(e2);
        observableEntradas.add(e3);
    }

    @javafx.fxml.FXML
    public void añadir(ActionEvent actionEvent) {
        if(!txtMatricula.getText().isBlank() &&
                comboModelo.getSelectionModel().getSelectedItem()!=null &&
                comboCliente.getSelectionModel().getSelectedItem() !=null &&
                toggleTarifa.getSelectedToggle()!=null &&
                datePickerEntrada.getValue()!=null &&
                datePickerSalida.getValue()!=null ){

            Period periodo = Period.between(datePickerEntrada.getValue(),datePickerSalida.getValue());

            int dias = periodo.getDays();

            int coste = 0;

            if(toggleTarifa.getSelectedToggle().toString().equalsIgnoreCase("standard")){
                coste=dias*8;
            }else if(toggleTarifa.getSelectedToggle().toString().equalsIgnoreCase("Oferta")){
                coste=dias*6;
            }else if(toggleTarifa.getSelectedToggle().toString().equalsIgnoreCase("larga duración")){
                coste=dias*2;
            }
         

            lblCoste.setText(coste+" €");


            Entrada eNueva = new Entrada(txtMatricula.getText(),
                    comboModelo.getSelectionModel().getSelectedItem(),
                    comboCliente.getSelectionModel().getSelectedItem(),
                    toggleTarifa.getSelectedToggle().toString(),
                    datePickerEntrada.getValue(),
                    datePickerSalida.getValue(),coste);


            observableEntradas.add(eNueva);
            tablaEntradas.getItems().clear();
            tablaEntradas.refresh();
            tablaEntradas.getItems().addAll(observableEntradas);


            comboModelo.setValue(null);
            comboCliente.setValue(null);
            toggleTarifa.selectToggle(null);
            datePickerEntrada.setValue(null);
            datePickerSalida.setValue(null);
            txtMatricula.setText("");
            lblCoste.setText("XXX €");




        }else{

            var alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("CUIDADO");
            alert.setContentText("TODOS LOS CAMPOS NO ESTAN RELLENOS");
            alert.showAndWait();


        }



    }

    @javafx.fxml.FXML
    public void salir(ActionEvent actionEvent) {

        System.exit(0);
    }
}