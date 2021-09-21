package gui;

import db.DbException;
import gui.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import gui.util.Constraints;
import gui.util.Alerts;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {

    private Department entity;

    private DepartmentService service;

    @FXML
    private TextField txtID;
    @FXML
    private TextField txtName;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;
    @FXML
    private Label labelError;

    @FXML
    public void onBtnSaveAction(ActionEvent actionEvent){
        if(entity == null){
            throw new IllegalStateException("Entity was null!");
        }
        if(service == null){
            throw new IllegalStateException("Service was null!");
        }
        try {
            entity = getFormData();
            service.saveOrUpdate(entity);
            Utils.currentStage(actionEvent).close();
        }catch (DbException e){
            Alerts.showAlert("Error saving object", null, e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    private Department getFormData() {
        Department department = new Department();

        department.setId(Utils.tryParseToInt(txtID.getText()));
        department.setName(txtName.getText());

        return department;
    }

    @FXML
    public void onBtnCancelAction(ActionEvent actionEvent){
        Utils.currentStage(actionEvent).close();
    }

    public void setEntity(Department entity) {
        this.entity = entity;
    }

    public void setService(DepartmentService service) {
        this.service = service;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtID);
        Constraints.setTextFieldMaxLength(txtName, 30);

    }

    public void updateFormData(){
        if(entity == null){
            throw new IllegalStateException("Department was null!");
        }
        txtID.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getName());
    }
}
