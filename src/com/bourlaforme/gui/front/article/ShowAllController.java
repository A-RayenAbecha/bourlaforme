package com.bourlaforme.gui.front.article;

import com.bourlaforme.entities.Article;
import com.bourlaforme.gui.front.MainWindowController;
import com.bourlaforme.services.ArticleService;
import com.bourlaforme.utils.AlertUtils;
import com.bourlaforme.utils.Constants;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;

public class ShowAllController implements Initializable {

    public static Article currentArticle;

    @FXML
    public Text topText;
    @FXML
    public VBox mainVBox;
    @FXML
    public ComboBox<String> sortCB;

    List<Article> listArticle;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listArticle = ArticleService.getInstance().getAll();
        sortCB.getItems().addAll("Nom", "Description", "Prix", "Etat");
        displayData();
    }

    void displayData() {
        mainVBox.getChildren().clear();

        Collections.reverse(listArticle);

        if (!listArticle.isEmpty()) {
            for (Article article : listArticle) {
                if (!article.getEtat().equals("supprimer")) {
                    mainVBox.getChildren().add(makeArticleModel(article));
                }
            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }

    public Parent makeArticleModel(
            Article article
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_ARTICLE)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#nomText")).setText("Nom : " + article.getNom());
            ((Text) innerContainer.lookup("#descriptionText")).setText("Description : " + article.getDescription());

            ((Text) innerContainer.lookup("#prixText")).setText("Prix : " + article.getPrix());
            ((Text) innerContainer.lookup("#etatText")).setText("Etat : " + article.getEtat());
            Path selectedImagePath = FileSystems.getDefault().getPath(article.getImage());
            if (selectedImagePath.toFile().exists()) {
                ((ImageView) innerContainer.lookup("#imageIV")).setImage(new Image(selectedImagePath.toUri().toString()));
            }

            ((Button) innerContainer.lookup("#showCommentsButton")).setOnAction((event) -> specialAction(article));
            ((Button) innerContainer.lookup("#showPdfButton")).setOnAction((event) -> genererPDF(article));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

   @FXML
void prix() {
        mainVBox.getChildren().clear();

        Collections.reverse(listArticle);

        if (!listArticle.isEmpty()) {
            for (Article article : listArticle) {
                if (!article.getEtat().equals("supprimer") && article.getPrix()<50) {
                    mainVBox.getChildren().add(makeArticleModel(article));
                }
            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }
   
  

    @FXML
    public void sort(ActionEvent actionEvent) {
        Constants.compareVar = sortCB.getValue();
        Collections.sort(listArticle);
        displayData();
    }



    private void genererPDF(Article article) {
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, Files.newOutputStream(Paths.get("article.pdf")));
            document.open();

            com.itextpdf.text.Font font = new com.itextpdf.text.Font();
            font.setSize(20);

            document.add(new Paragraph("- Article -"));

            try {
                com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(article.getImage());
                image.scaleAbsoluteWidth(300);
                image.scaleAbsoluteHeight(300);
                image.isScaleToFitHeight();
                document.add(image);
            } catch (FileNotFoundException e){
                AlertUtils.makeError("Image not found, PDF will display without image");
            }

            document.add(new Paragraph("Nom : " + article.getNom()));
            document.add(new Paragraph("Description : " + article.getDescription()));
            document.add(new Paragraph("Etat : " + article.getEtat()));
            document.add(new Paragraph("Prix : " + article.getPrix()));
            document.newPage();
            document.close();

            writer.close();

            Desktop.getDesktop().open(new File("article.pdf"));
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }

    }

    private void specialAction(Article article) {
        currentArticle = article;
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_COMMENTAIRE);
    }
}
