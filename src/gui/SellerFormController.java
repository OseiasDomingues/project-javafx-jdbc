package gui;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import model.entities.Department;
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.DepartmentService;
import model.services.SellerService;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class SellerFormController implements Initializable {

    private Seller entity;

    private SellerService service;

    private DepartmentService departmentService;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField txtID;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmail;
    @FXML
    private DatePicker dpBirthDate;
    @FXML
    private TextField txtBaseSalary;
    @FXML
    private ComboBox<Department> comboBoxDepartment;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;
    @FXML
    private ObservableList<Department> departmentObservableList;
    @FXML
    private Label labelErrorName;
    @FXML
    private Label labelErrorEmail;
    @FXML
    private Label labelErrorBirthDate;
    @FXML
    private Label labelErrorBaseSalary;

    @FXML
    public void onBtnSaveAction(ActionEvent actionEvent) {
        if (entity == null) {
            throw new IllegalStateException("Entity was null!");
        }
        if (service == null) {
            throw new IllegalStateException("Service was null!");
        }
        try {
            entity = getFormData();
            service.saveOrUpdate(entity);
            notifyDataChangeListeners();
            Utils.currentStage(actionEvent).close();
        } catch (ValidationException e) {
            setMessageError(e.getErrors());
        } catch (DbException e) {
            Alerts.showAlert("Error saving object", null, e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    private void notifyDataChangeListeners() {
        for (DataChangeListener listener : dataChangeListeners) {
            listener.onDataChanged();
        }
    }

    private Seller getFormData() {
        Seller Seller = new Seller();

        ValidationException validationException = new ValidationException("Validation Error");

        Seller.setId(Utils.tryParseToInt(txtID.getText()));

        if (txtName.getText() == null || txtName.getText().trim().equals("")) {
            validationException.addError("name", "Field can't be empty");
        }
        Seller.setName(txtName.getText());

        if (validationException.getErrors().size() > 0) {
            throw validationException;
        }

        return Seller;
    }

    @FXML
    public void onBtnCancelAction(ActionEvent actionEvent) {
        Utils.currentStage(actionEvent).close();
    }

    public void setEntity(Seller entity) {
        this.entity = entity;
    }

    public void setServices(SellerService service, DepartmentService departmentService) {
        this.service = service;
        this.departmentService = departmentService;
    }

    public void subscribeDataChangeListener(DataChangeListener listener) {
        dataChangeListeners.add(listener);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtID);
        Constraints.setTextFieldMaxLength(txtName, 70);
        Constraints.setTextFieldMaxLength(txtEmail, 50);
        Constraints.setTextFieldDouble(txtBaseSalary);
        Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");

        initializeComboBoxDepartment();
    }

    public void updateFormData() {
        if (entity == null) {
            throw new IllegalStateException("Seller was null!");
        }
        txtID.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getName());
        txtEmail.setText(entity.getEmail());
        Locale.setDefault(Locale.US);
        txtBaseSalary.setText(String.format("%.2f", entity.getBaseSalary()));
        if (entity.getBirthDate() != null) {
            dpBirthDate.setValue(LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()));
        }
        if (entity.getDepartment() == null){
            comboBoxDepartment.getSelectionModel().selectFirst();
        }else {
            comboBoxDepartment.setValue(entity.getDepartment());
        }
    }

    public void loadAssociatedObjects(){
        if(departmentService == null){
            throw new IllegalStateException("DepartmentService was null!");
        }
        List<Department> list = departmentService.findAll();
        departmentObservableList = FXCollections.observableArrayList(list);
        comboBoxDepartment.setItems(departmentObservableList);
    }

    public void setMessageError(Map<String, String> errors) {
        Set<String> fields = errors.keySet();

        if (fields.contains("name")) {
            labelErrorName.setText(errors.get("name"));
        }
    }
    private void initializeComboBoxDepartment() {
        Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
            @Override
            protected void updateItem(Department item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        comboBoxDepartment.setCellFactory(factory);
        comboBoxDepartment.setButtonCell(factory.call(null));
    }

}
