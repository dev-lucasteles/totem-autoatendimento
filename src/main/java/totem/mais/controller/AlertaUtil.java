package totem.mais.controller;

import javafx.scene.control.Alert;

public class AlertaUtil {
    public static void mostrar(String titulo, String mensagem) {
        Alert.AlertType tipo = mensagem.startsWith("ERRO") ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION;
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}