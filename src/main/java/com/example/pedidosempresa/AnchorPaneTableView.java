package com.example.pedidosempresa;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnchorPaneTableView {
    private Paneformulario paneform;
    private  Tabla tabla;
    private final TableView<ObservableList> tableView = new TableView<ObservableList>();
    private final List<String> columnNames = new ArrayList<>();
    private AnchorPane anchorp;
    private String operacion;
    ObservableList<ObservableList> data;





    public AnchorPaneTableView(ResultSet rs) throws SQLException {
        super();

            if(rs.isBeforeFirst()){
                this.tabla = new Tabla(rs);
                paneform = new Paneformulario(this.tabla, 0);
                buildData();
            }else{
                this.tabla = new Tabla(rs);
                paneform = new Paneformulario(this.tabla, 0);
                ArrayList<Button> botn=paneform.getBotones();
                data = FXCollections.observableArrayList();

                for (int i = 0; i < tabla.getColumNames().size(); i++) {

                    final int j = i;
                    TableColumn col = new TableColumn(tabla.getColumNames().get(i));
                    col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> {
                        if (param.getValue().get(j) != null) {
                            return new SimpleStringProperty(param.getValue().get(j).toString());
                        } else {
                            return null;
                        }
                    });

                    tableView.getColumns().addAll(col);
                    this.columnNames.add(col.getText());
                }

                for (int j = 0; j < botn.size(); j++) {

                    int finalJ = j;
                    botn.get(j).setOnMouseClicked(mouseEvent -> {

                        switch (finalJ){
                            case 0:
                                cambiarBotones();
                                guardarOperacion(0);
                                break;
                            case 1:
                                cambiarBotones();
                                for (int i = 0; i < paneform.getTextos().size(); i++) {
                                    paneform.getTextos().get(i).setText("");
                                }
                                guardarOperacion(1);
                                break;
                            case 2:
                                cambiarBotones();
                                guardarOperacion(2);
                                break;
                            case 3:


                                break;
                            case 4:
                                cambiarBotones();
                                guardarOperacion(4);
                                break;
                        }

                    });
                }
            }


            }

    public void setTabla(Tabla tabla) {

        this.tabla = tabla;

    }

    public Paneformulario getPaneform() {
        return paneform;
    }
    public ArrayList<Button> getArrayBotones(){
        return paneform.getBotones();
    }
    public void cambiarBotones(){
        ArrayList<Button> botonera=paneform.getBotones();
        for (int i = 0; i < botonera.size(); i++) {
            if(botonera.get(i).isDisabled()==true){
                botonera.get(i).setDisable(false);
            }else {
                botonera.get(i).setDisable(true);
            }
        }
    }

    public TableView<ObservableList> getTableView() {
        return tableView;
    }
    public List<String> getColumnNames() {
        return columnNames;
    }

    public AnchorPane getAnchorpaneTableView() {
        return anchorp;
    }
    public ArrayList<TextField> getArrayTextFields(){
        return paneform.getTextos();
    }
    public ArrayList<CampoTabla> getRegistro(int row){
        return tabla.getRegistro(row);

    }

    private void buildData() throws SQLException {
        ArrayList<Button> botn=paneform.getBotones();
        if (tabla.getSize() > 0) {
             data = FXCollections.observableArrayList();

            for (int i = 0; i < tabla.getRegistro(0).size(); i++) {

                final int j = i;
                TableColumn col = new TableColumn(tabla.getRegistro(0).get(i).getColumName());
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> {
                    if (param.getValue().get(j) != null) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    } else {
                        return null;
                    }
                });

                tableView.getColumns().addAll(col);
                this.columnNames.add(col.getText());
            }

            for (int j = 0; j < tabla.getSize(); j++) {

                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 0; i < tabla.getRegistro(0).size(); i++) {

                    row.add(String.valueOf(tabla.getRegistro(j).get(i).getValor()));
                }
                data.add(row);
            }

            tableView.setItems(data);
            anchorp = new AnchorPane();
            anchorp.getChildren().add(tableView);
            tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                    //Check whether item is selected and set value of selected item to Label
                    if (tableView.getSelectionModel().getSelectedItem() != null) {
                        TableView.TableViewSelectionModel selectionModel = tableView.getSelectionModel();
                        ObservableList selectedCells = selectionModel.getSelectedCells();
                        TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                        //Object val = tablePosition.getTableColumn().getCellData(newValue);
                        refreshpaneform(tablePosition.getRow());
                    }
                }

                private void refreshpaneform(int row) {
                    for (int i = 0; i < paneform.getTextos().size(); i++) {
                        paneform.getTextos().get(i).setText(String.valueOf(tabla.getRegistro(row).get(i).getValor()));

                    }
                }
            });

            for (int j = 0; j < botn.size(); j++) {

                int finalJ = j;
                botn.get(j).setOnMouseClicked(mouseEvent -> {

                    switch (finalJ){
                        case 0:
                            cambiarBotones();
                            guardarOperacion(0);
                            break;
                       case 1:
                           cambiarBotones();
                           for (int i = 0; i < paneform.getTextos().size(); i++) {
                               paneform.getTextos().get(i).setText("");
                           }
                           guardarOperacion(1);
                            break;
                       case 2:
                           cambiarBotones();
                           guardarOperacion(2);
                            break;
                       case 3:


                           break;
                       case 4:
                           cambiarBotones();
                           guardarOperacion(4);
                           break;
                    }

                });
            }


        }
    }
    public int tableviewGetRow() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            TableView.TableViewSelectionModel selectionModel = tableView.getSelectionModel();
            ObservableList selectedCells = selectionModel.getSelectedCells();
            TablePosition tablePosition = (TablePosition) selectedCells.get(0);
            return tablePosition.getRow();

        }else{
            return -1;
        }

    }
        public void tablaRemoveSingleRow(){

                data.remove(tableviewGetRow());
                this.tableView.refresh();

        }
        public void Refresh(ResultSet rs){
            Tabla tablTemp=new Tabla(rs);
            this.tabla=tablTemp;
            data.remove(0, data.size());
            for (int j = 0; j < tabla.getSize(); j++) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 0; i < tabla.getRegistro(0).size(); i++) {
                    row.add(String.valueOf(tabla.getRegistro(j).get(i).getValor()));
                }
                data.add(row);
            }
            tableView.refresh();
            tableView.requestFocus();
            tableView.getSelectionModel().select(0);
            tableView.getFocusModel().focus(0);
        }
        private void guardarOperacion ( int op){
            switch (op) {
                case 0:
                    operacion = "editar";
                    break;
                case 1:
                    operacion = "añadir";
                    break;
                case 2:
                    operacion = "borrar";
                    break;
                case 4:
                    operacion = null;
            }

        }
        public Button getBotonEjecutar () {
            return paneform.getBotones().get(3);
        }
        public String getOperacion () {
            return operacion;
        }
        /*public void refreshpanef () {
            if (tableView.getSelectionModel().getSelectedItem() != null) {
                TableView.TableViewSelectionModel selectionModel = tableView.getSelectionModel();
                ObservableList selectedCells = selectionModel.getSelectedCells();
                TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                for (int i = 0; i < paneform.getTextos().size(); i++) {
                    paneform.getTextos().get(i).setText(String.valueOf(tabla.getRegistro(tablePosition.getRow()).get(i).getValor()));

                }
            }


        }*/


}
