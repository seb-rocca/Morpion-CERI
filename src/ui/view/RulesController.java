package ui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ui.MainApp;

public class RulesController {

    private MainApp mainApp;

    @FXML
    private Button retourMenu;


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
