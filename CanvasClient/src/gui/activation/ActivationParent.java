package gui.activation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class ActivationParent {
    private static ActivationParent activationParent;

    public static ActivationParent getInstance() {
        if (activationParent == null) {
            activationParent = new ActivationParent();
            return activationParent;
        }
        return activationParent;
    }

    public Parent getParent() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Activation.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }
}
