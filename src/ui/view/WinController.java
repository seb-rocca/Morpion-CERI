package ui.view;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import ui.MainApp;

public class WinController {

    @FXML
    private Text resultText;

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

    @FXML
    public void jouerEncore()
    {
        mainApp.showPlayOverview(false);
    }

    public void sendResult(String s)
    {
        resultText.setText(s);
    }
}
