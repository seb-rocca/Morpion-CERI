package ui.view;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import ui.MainApp;

public class WinController {

    @FXML
    private Text resultText;

    private MainApp mainApp;

    private boolean withAI;

    public void setMainApp(MainApp mainApp, boolean withAI)
    {

        this.mainApp = mainApp;
        this.withAI = withAI;

    }

    @FXML
    public void retourMenu()
    {
        mainApp.showMenuOverview();
    }

    @FXML
    public void jouerEncore()
    {
        mainApp.showPlayOverview(withAI);
    }

    public void sendResult(String s)
    {
        resultText.setText(s);
    }
}
