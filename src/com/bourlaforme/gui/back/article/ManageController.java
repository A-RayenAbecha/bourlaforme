package com.bourlaforme.gui.back.article;

import com.bourlaforme.MainApp;
import com.bourlaforme.entities.Article;
import com.bourlaforme.gui.back.MainWindowController;
import com.bourlaforme.services.ArticleService;
import com.bourlaforme.utils.AlertUtils;
import com.bourlaforme.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.util.ResourceBundle;

public class ManageController implements Initializable {

    @FXML
    public TextField nomTF;
    @FXML
    public TextField descriptionTF;
    @FXML
    public ImageView imageIV;
    @FXML
    public TextField prixTF;
  //  @FXML
  //  public TextField etatTF;
    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;

    Article currentArticle;
    Path selectedImagePath;
    boolean imageEdited;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        currentArticle = ShowAllController.currentArticle;

        if (currentArticle != null) {
            topText.setText("Modifier article");
            btnAjout.setText("Modifier");

            try {
                nomTF.setText(currentArticle.getNom());
                descriptionTF.setText(currentArticle.getDescription());
                selectedImagePath = FileSystems.getDefault().getPath(currentArticle.getImage());
                if (selectedImagePath.toFile().exists()) {
                    imageIV.setImage(new Image(selectedImagePath.toUri().toString()));
                }
                prixTF.setText(String.valueOf(currentArticle.getPrix()));
              //  etatTF.setText(currentArticle.getEtat());

            } catch (NullPointerException ignored) {
                System.out.println("NullPointerException");
            }
        } else {
            topText.setText("Ajouter article");
            btnAjout.setText("Ajouter");
        }
    }

    @FXML
    private void manage(ActionEvent event) {

        if (controleDeSaisie()) {
     

            String imagePath;
            if (imageEdited) {
                imagePath = currentArticle.getImage();
            } else {
                createImageFile();
                imagePath = selectedImagePath.toString();
            }

            Article article = new Article(
                    nomTF.getText(),
                    descriptionTF.getText(),
                    imagePath,
                    Integer.parseInt(prixTF.getText()),
                  //  etatTF.getText()
                    "desarchiver"
            );

            if (currentArticle == null) {
                   String nom = nomTF.getText();

        if (!ArticleService.getInstance().isNomUnique(nom)) {
            AlertUtils.makeError("Nom d'article déjà utilisé. Veuillez choisir un autre nom.");
            return;
        }
                if (ArticleService.getInstance().add(article)) {
                    AlertUtils.makeSuccessNotification("Article ajouté avec succés");
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_ARTICLE);
                } else {
                    AlertUtils.makeError("Erreur");
                }
            } else {
                  String nom = nomTF.getText();

        if (!ArticleService.getInstance().isNomUnique2(nom, currentArticle.getId())) {
            AlertUtils.makeError("Nom d'article déjà utilisé. Veuillez choisir un autre nom.");
            return;
        }
                article.setId(currentArticle.getId());
                if (ArticleService.getInstance().edit(article)) {
                    AlertUtils.makeSuccessNotification("Article modifié avec succés");
                    ShowAllController.currentArticle = null;
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_ARTICLE);
                } else {
                    AlertUtils.makeError("Erreur");
                }
            }

            if (selectedImagePath != null) {
                createImageFile();
            }
        }
    }


    @FXML
    public void chooseImage(ActionEvent actionEvent) {

        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(MainApp.mainStage);
        if (file != null) {
            selectedImagePath = Paths.get(file.getPath());
            imageIV.setImage(new Image(file.toURI().toString()));
        }
    }

    public void createImageFile() {
        try {
            Path newPath = FileSystems.getDefault().getPath("src/com/bourlaforme/images/uploads/" + selectedImagePath.getFileName());
            Files.copy(selectedImagePath, newPath, StandardCopyOption.REPLACE_EXISTING);
            selectedImagePath = newPath;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     private boolean controleDeSaisie() {


        if (nomTF.getText().isEmpty()) {
            AlertUtils.makeInformation("nom ne doit pas etre vide");
            return false;
        }
        String nom = nomTF.getText();
    if (!nom.matches("^[a-zA-Z_ ]+$")) {
        AlertUtils.makeInformation("Le nom ne doit contenir que des lettres, des espaces et des tirets bas");
        return false;
    }
 /*    try {
        Connection conn = DatabaseConnection.getInstance().getConnection();

        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM article WHERE nom = ?");
        stmt.setString(1, nomTF.getText());
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            AlertUtils.makeInformation("Le nom existe déjà");
            return false;
        }
    } catch (SQLException e) {
    //    AlertUtils.makeError("Erreur lors de la vérification du nom", e);
        return false;
    }

*/
        if (descriptionTF.getText().isEmpty()) {
            AlertUtils.makeInformation("description ne doit pas etre vide");
            return false;
        }


        if (selectedImagePath == null) {
            AlertUtils.makeInformation("Veuillez choisir une image");
            return false;
        }


        if (prixTF.getText().isEmpty()) {
            AlertUtils.makeInformation("prix ne doit pas etre vide");
            return false;
        }
        
      try {
        int prix = Integer.parseInt(prixTF.getText());
        if (prix <= 0) {
            AlertUtils.makeInformation("Le prix doit être positif");
            return false;
        }
    } catch (NumberFormatException ignored) {
        AlertUtils.makeInformation("Prix doit être un nombre");
        return false;
    }
      /*  if (etatTF.getText().isEmpty()) {
            AlertUtils.makeInformation("etat ne doit pas etre vide");
            return false;
        }
*/

        return true;
    }
}