package gui;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;


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
        loadView("/gui/DepartmentList.fxml" , (DepartmentListController controller) -> {
            controller.setDepartmentService(new DepartmentService());
            controller.updateTableView();
        });
    }


    @FXML
    public void onMenuItemAboutAction() {
        loadView("/gui/About.fxml", (x) -> {});
    }


    private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
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

            T controller = fxmlLoader.getController();
            initializingAction.accept(controller);

            /*<T> Vai ser substituido pelo atribuito quem vem na expressao Lambda,neste caso DeparmentListController
            * Antes da linha 72, controller da expressao é nulo, apos a linha 73 a expressao vai receber controller com os getController().*/

        } catch (IOException e) {
            Alerts.showAlert("Error", "Error About View", e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
