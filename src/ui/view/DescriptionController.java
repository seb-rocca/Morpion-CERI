package ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ui.MainApp;

public class DescriptionController {

    @FXML
    private Button retourMenu;

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;
    }

    @FXML
    public void retourMenu()
    {
        mainApp.showMenuOverview();
    }
}
