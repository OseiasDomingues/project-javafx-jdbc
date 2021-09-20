package gui;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import gui.util.Alerts;
import model.services.DepartmentService;


public class MainViewController implements Initializable {

    @FXML
    private MenuItem menuItemSeller;
    @FXML
    private MenuItem menuItemDepartment;
    @FXML
    private MenuItem menuItemAbout;

    @FXML
    public void onMenuItemSellerAction() {
        System.out.println("onMenuItemSellerAction");
    }

    @FXML
    public void onMenuItemDepartmentAction() {
        loadViewOther("/gui/DepartmentList.fxml");
    }

    @FXML
    public void onMenuItemAboutAction() {
        loadView("/gui/About.fxml");
    }

    private synchronized void loadView(String absoluteName) {
        try {
            //Carregar o FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(absoluteName));
            //Passar para um tipo VBOX
            VBox newVBox = fxmlLoader.load();

            //Cena Principal
            Scene mainScene = Main.getMainScene();

            //Pegando o conteudo do ScrollPane, no caso o VBox
            VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

            //Passando o Menu para um variavel temporaria
            Node menuTemp = mainVBox.getChildren().get(0);
            //Limpado o VBox
            mainVBox.getChildren().clear();
            //Adicionado o Menu + a nova View
            mainVBox.getChildren().add(menuTemp);
            mainVBox.getChildren().addAll(newVBox.getChildren());

        } catch (IOException e) {
            Alerts.showAlert("Error", "Error About View", e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    private synchronized void loadViewOther(String absoluteName) {
        try {
            //Carregar o FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(absoluteName));
            //Passar para um tipo VBOX
            VBox newVBox = fxmlLoader.load();

            //Cena Principal
            Scene mainScene = Main.getMainScene();

            //Pegando o conteudo do ScrollPane, no caso o VBox
            VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

            //Passando o Menu para um variavel temporaria
            Node menuTemp = mainVBox.getChildren().get(0);
            //Limpado o VBox
            mainVBox.getChildren().clear();
            //Adicionado o Menu + a nova View
            mainVBox.getChildren().add(menuTemp);
            mainVBox.getChildren().addAll(newVBox.getChildren());

            DepartmentListController departmentListController = fxmlLoader.getController();
            departmentListController.setDepartmentService(new DepartmentService());
            departmentListController.updateTableView();

        } catch (IOException e) {
            Alerts.showAlert("Error", "Error About View", e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
