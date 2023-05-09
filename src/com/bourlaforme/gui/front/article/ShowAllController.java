package com.bourlaforme.gui.front.article;

import com.bourlaforme.entities.Article;
import com.bourlaforme.entities.PanierArticle;
import com.bourlaforme.gui.front.MainWindowController;
import com.bourlaforme.gui.front.panier.MonPanierController;
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
import javafx.scene.control.Pagination;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfAction;
import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.itextpdf.text.Font;
public class ShowAllController implements Initializable {

    public static Article currentArticle;

    @FXML
    public Text topText;
    @FXML
    public VBox mainVBox;
    @FXML
    public ComboBox<String> sortCB;
    @FXML
    public Button btnAjout;
    List<Article> listArticle;

    @Override
      public void initialize(URL url, ResourceBundle rb) {
           Pagination pagination=new Pagination() ;
        listArticle = ArticleService.getInstance().getAll();
        sortCB.getItems().addAll("Nom", "Description", "Prix", "Etat");
        int itemsPerPage = 3;
    int totalPages = (int) Math.ceil(listArticle.size() / (double) itemsPerPage);
    pagination = new Pagination(totalPages, 0);
    pagination.setPageFactory(this::createPage);
    pagination.setPageFactory(this::createPage);
    mainVBox.getChildren().add(pagination);
    }

    
    
    public VBox createPage(int pageIndex) {
    VBox pageBox = new VBox();
    int itemsPerPage = 3;
    int startIndex = pageIndex * itemsPerPage;
    int endIndex = Math.min(startIndex + itemsPerPage, listArticle.size());
    List<Article> itemsToShow = listArticle.subList(startIndex, endIndex);

    for (Article article: itemsToShow) {
         if (!article.getEtat().equals("supprimer")) {
        Parent ArticleModel = makeArticleModel(article);
        pageBox.getChildren().add(ArticleModel);
    }
    }
    return pageBox;
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

    public Parent makeArticleModel(Article article) {
    Parent parent = null;
    try {
        parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_ARTICLE)));

        HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
        ((Text) innerContainer.lookup("#nomText")).setText("Nom : " + article.getNom());
        ((Text) innerContainer.lookup("#descriptionText")).setText("Description : " + article.getDescription());

        ((Text) innerContainer.lookup("#prixText")).setText("Prix : " + article.getPrix());
        ((Text) innerContainer.lookup("#etatText")).setText("Etat : " + article.getEtat());

        String imageName = article.getImage();
        String imagePath = "C:\\Users\\aziz3\\OneDrive\\Bureau\\projet\\Projet_Anarchy\\public\\uploads\\articles\\" + imageName;
        Path selectedImagePath = FileSystems.getDefault().getPath(imagePath);

        if (selectedImagePath.toFile().exists()) {
            ((ImageView) innerContainer.lookup("#imageIV")).setImage(new Image(selectedImagePath.toUri().toString()));
        }

        ((Button) innerContainer.lookup("#showCommentsButton")).setOnAction((event) -> specialAction(article));
        ((Button) innerContainer.lookup("#showPdfButton")).setOnAction((event) -> genererPDF(article));
        ((Button) innerContainer.lookup("#showPanier")).setOnAction((event) -> AjouterPanier(article));

    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
    return parent;
}

    
    
     public Parent makeArticleModel2(
            Article article
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_ARTICLE2)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#nomText")).setText("Nom : " + article.getNom());


            ((Button) innerContainer.lookup("#showCommentsButton")).setOnAction((event) -> specialAction(article));
            ((Button) innerContainer.lookup("#showPdfButton")).setOnAction((event) -> genererPDF(article));
            ((Button) innerContainer.lookup("#showPanier")).setOnAction((event) -> AjouterPanier(article));


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
            if (!article.getEtat().equals("supprimer")) {
                mainVBox.getChildren().add(makeArticleModel2(article));
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
     try {
    com.itextpdf.text.Image logo = com.itextpdf.text.Image.getInstance("C:/Users/Administrateur/OneDrive/Bureau/bourlaforme/src/com/bourlaforme/images/uploads/logo.jpg");
    logo.setAlignment(Element.ALIGN_LEFT);
    logo.scaleToFit(100, 100);
    document.add(logo);
    } catch (IOException e) {
        AlertUtils.makeError("Logo not found, PDF will display without logo");
    }

        // Add title
        Paragraph title = new Paragraph("Fiche article : " + article.getNom(), new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 22, com.itextpdf.text.Font.BOLD));
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);


 
        // Add article image
        try {
            com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(article.getImage());
            image.setAlignment(Element.ALIGN_CENTER);
            image.scaleToFit(400, 400);
            document.add(image);
        } catch (FileNotFoundException e){
            AlertUtils.makeError("Image not found, PDF will display without image");
        }

        // Add article details
        Paragraph details = new Paragraph();
        details.add(new Chunk("Nom : ", new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD)));
        details.add(new Chunk(article.getNom() + "\n", new Font(Font.FontFamily.TIMES_ROMAN, 16)));
        details.add(new Chunk("Description : ", new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD)));
        details.add(new Chunk(article.getDescription() + "\n", new Font(Font.FontFamily.TIMES_ROMAN, 16)));
        details.add(new Chunk("Etat : ", new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD)));
        details.add(new Chunk(article.getEtat() + "\n", new Font(Font.FontFamily.TIMES_ROMAN, 16)));
        details.add(new Chunk("Prix : ", new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD)));
        details.add(new Chunk(article.getPrix() + "\n", new Font(Font.FontFamily.TIMES_ROMAN, 16)));
        document.add(details);

        // Add QR code
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String qrCodeData = article.getNom(); // Use article name as QR code data
       //String qrCodeData = "http://127.0.0.1:8000/client/article2/" + article.getId(); 
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, 300, 300);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", out);
        com.itextpdf.text.Image qrCodeImage = com.itextpdf.text.Image.getInstance(out.toByteArray());
        qrCodeImage.setAlignment(Element.ALIGN_CENTER);
        qrCodeImage.scaleToFit(200, 200);
        document.add(qrCodeImage);

        // Add link to article
        Chunk link = new Chunk("Cliquez ici pour voir l'article", new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.UNDERLINE));
        link.setAction(new PdfAction("http://127.0.0.1:8000/client/article2/" + article.getId()));
        Paragraph linkParagraph = new Paragraph(link);
        linkParagraph.setAlignment(Element.ALIGN_CENTER);
        document.add(linkParagraph);

        document.close();

        writer.close();

        Desktop.getDesktop().open(new File("article.pdf"));
    } catch (DocumentException | IOException | WriterException e) {
        e.printStackTrace();
    }
}

@FXML
private void AjouterPanier(Article article) {
    boolean articleExists = false;
    for (PanierArticle panierArticle : MonPanierController.monPanierArticleList) {
        if (panierArticle.getArticle().equals(article)) {
            panierArticle.setQuantity(panierArticle.getQuantity()+ 1);
            articleExists = true;
            break;
        }
    }
    if (!articleExists) {
        PanierArticle panierArticle = new PanierArticle(article, MonPanierController.panier, 0);
        MonPanierController.monPanierArticleList.add(panierArticle);
    }
    else 

    AlertUtils.makeSuccessNotification("Article ajouté avec succés");
    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_PANIER_ARTICLE);
}


    



    private void specialAction(Article article) {
        currentArticle = article;
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_COMMENTAIRE);
    }
}
