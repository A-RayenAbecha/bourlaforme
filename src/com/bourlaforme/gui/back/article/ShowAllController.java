package com.bourlaforme.gui.back.article;

import com.bourlaforme.entities.Article;
import com.bourlaforme.gui.back.MainWindowController;
import static com.bourlaforme.gui.front.article.ShowAllController.currentArticle;
import com.bourlaforme.services.ArticleService;
import com.bourlaforme.utils.AlertUtils;
import com.bourlaforme.utils.Constants;
import java.awt.Desktop;
import java.io.File;
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

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import javafx.stage.FileChooser;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

public class ShowAllController implements Initializable {

    public static Article currentArticle;

    @FXML
    public Text topText;
    @FXML
    public Button addButton;
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

                mainVBox.getChildren().add(makeArticleModel(article));

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
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_BACK_MODEL_ARTICLE)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#nomText")).setText("Nom : " + article.getNom());
            ((Text) innerContainer.lookup("#descriptionText")).setText("Description : " + article.getDescription());

            ((Text) innerContainer.lookup("#prixText")).setText("Prix : " + article.getPrix());
            ((Text) innerContainer.lookup("#etatText")).setText("Etat : " + article.getEtat());
            Path selectedImagePath = FileSystems.getDefault().getPath(article.getImage());
      if (selectedImagePath.toFile().exists()) {
                ((ImageView) innerContainer.lookup("#imageIV")).setImage(new Image(selectedImagePath.toUri().toString()));
            }

            ((Button) innerContainer.lookup("#editButton")).setOnAction((event) -> modifierArticle(article));
            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((event) -> supprimerArticle(article));
             ((Button) innerContainer.lookup("#showCommentsButton")).setOnAction((event) -> displayComments(article));
            Button archiveButton = ((Button) innerContainer.lookup("#archiverButton"));
            archiveButton.setOnAction((event) -> specialAction(article, archiveButton));

            if (article.getEtat().equals("supprimer")) {
                archiveButton.setText("Désarchiver");
            } else {
                archiveButton.setText("Archiver");
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    @FXML
    private void ajouterArticle(ActionEvent event) {
        currentArticle = null;
        MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_ARTICLE);
    }

    private void modifierArticle(Article article) {
        currentArticle = article;
        MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_ARTICLE);
    }

    private void supprimerArticle(Article article) {
        currentArticle = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer article ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            if (ArticleService.getInstance().delete(article.getId())) {
                MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_ARTICLE);
            } else {
                AlertUtils.makeError("Could not delete article");
            }
        }
    }

    @FXML
    public void sort(ActionEvent actionEvent) {
        Constants.compareVar = sortCB.getValue();
        Collections.sort(listArticle);
        displayData();
    }

    @FXML
    public void genererExcel(ActionEvent actionEvent) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        try {
            FileChooser chooser = new FileChooser();
            // Set extension filter
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Excel Files(.xls)", ".xls");
            chooser.getExtensionFilters().add(filter);

            HSSFSheet workSheet = workbook.createSheet("sheet 0");
            workSheet.setColumnWidth(1, 25);

            HSSFFont fontBold = workbook.createFont();
            fontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            HSSFCellStyle styleBold = workbook.createCellStyle();
            styleBold.setFont(fontBold);

            Row row1 = workSheet.createRow((short) 0);

            row1.createCell(0).setCellValue("Nom");
            row1.createCell(1).setCellValue("Description");
            row1.createCell(2).setCellValue("Etat");
            row1.createCell(3).setCellValue("Prix");

            int i = 0;
            for (Article article : listArticle) {
                i++;
                Row row2 = workSheet.createRow((short) i);
                row2.createCell(0).setCellValue(article.getNom());
                row2.createCell(1).setCellValue(article.getDescription());
                row2.createCell(2).setCellValue(article.getEtat());
                row2.createCell(3).setCellValue(article.getPrix());
            }

            workSheet.autoSizeColumn(0);
            workSheet.autoSizeColumn(1);
            workSheet.autoSizeColumn(2);
            workSheet.autoSizeColumn(3);

            workbook.write(Files.newOutputStream(Paths.get("article.xls")));
            Desktop.getDesktop().open(new File("article.xls"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    
    private void displayComments(Article article) {
        currentArticle = article;
        com.bourlaforme.gui.back.MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_COMMENTAIRE);
    }

    private void specialAction(Article article, Button button) {
        currentArticle = null;

        if (article.getEtat().equals("supprimer")) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmer le désarchivage");
            alert.setHeaderText(null);
            alert.setContentText("Etes vous sûr de vouloir désarchiver cet article ?");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK) {
                article.setEtat("desarchiver");
                button.setText("Archiver");

                if (ArticleService.getInstance().edit(article)) {
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_ARTICLE);
                } else {
                    AlertUtils.makeError("Error");
                }
            }
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmer l'archivage");
            alert.setHeaderText(null);
            alert.setContentText("Etes vous sûr de vouloir archiver cet article ?");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK) {
                article.setEtat("supprimer");
                button.setText("Désarchiver");

                if (ArticleService.getInstance().edit(article)) {
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_ARTICLE);
                } else {
                    AlertUtils.makeError("Error");
                }
            }
        }

    }
}
