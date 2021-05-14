package ui.view;

import javafx.fxml.FXML;
import ui.MainApp;

public class SettingsController {

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
