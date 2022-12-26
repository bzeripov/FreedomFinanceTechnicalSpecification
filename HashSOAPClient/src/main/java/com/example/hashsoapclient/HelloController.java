package com.example.hashsoapclient;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class HelloController {
    @FXML
    private Text fileSetText;
    @FXML
    private TextArea hashCodeTextArea;
    @FXML
    private Text fileNameText;

    private static File selectedFile = null;
    private static final HashClient client = new HashClient();
    public void initialize(){
        fileSetText.setVisible(false);
        fileNameText.setVisible(false);
    }

    public void onBrowseButtonClicked(){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(fileSetText.getScene().getWindow());
        if(file != null) {
            selectedFile = file;
            fileNameText.setText(file.getName());
            fileNameText.setVisible(true);
        }
    }

    public void onGetHashButtonClicked() throws IOException {
        if(selectedFile == null){
            fileSetText.setVisible(true);
            return;
        }
        String hash = client.getHashCode(Files.readAllBytes(selectedFile.toPath()));
        hashCodeTextArea.clear();
        hashCodeTextArea.setText(hash);
    }
}