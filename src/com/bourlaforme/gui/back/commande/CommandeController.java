package com.bourlaforme.gui.back.commande;

import com.bourlaforme.entities.Article;
import com.bourlaforme.entities.Commande;
import com.bourlaforme.entities.PanierArticle;
import com.bourlaforme.gui.back.MainWindowController;
import com.bourlaforme.services.CommandeService;
import com.bourlaforme.services.PanierArticleService;
import com.bourlaforme.utils.AlertUtils;
import com.bourlaforme.utils.Constants;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.lang.String;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CommandeController implements Initializable {

    @FXML
    public Text topText;
    @FXML
    public VBox mainVBox;
    @FXML
    public ComboBox<String> sortCB;
    @FXML
    public TextField searchTF;

    List<Commande> listCommande;

    @Override
public void initialize(URL url, ResourceBundle rb) {
    Pagination pagination=new Pagination() ;
    listCommande = CommandeService.getInstance().getAll();
    sortCB.getItems().addAll("Montant", "Date", "ConfirmeAdmin");
    int itemsPerPage = 3;
    int totalPages = (int) Math.ceil(listCommande.size() / (double) itemsPerPage);
    pagination = new Pagination(totalPages, 0);
    pagination.setPageFactory(this::createPage);
    pagination.setPageFactory(this::createPage);
    mainVBox.getChildren().add(pagination);
}

public VBox createPage(int pageIndex) {
    VBox pageBox = new VBox();
    int itemsPerPage = 3;
    int startIndex = pageIndex * itemsPerPage;
    int endIndex = Math.min(startIndex + itemsPerPage, listCommande.size());
    List<Commande> itemsToShow = listCommande.subList(startIndex, endIndex);

    for (Commande commande : itemsToShow) {
        Parent commandeModel = makeCommandeModel(commande);
        pageBox.getChildren().add(commandeModel);
    }

    return pageBox;
}

    void displayData(String searchText) {
        mainVBox.getChildren().clear();

        Collections.reverse(listCommande);

        if (!listCommande.isEmpty()) {
            for (Commande commande : listCommande) {
                if (String.valueOf(commande.getMontant()).startsWith(searchText.toLowerCase())) {
                    mainVBox.getChildren().add(makeCommandeModel(commande));
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

    public Parent makeCommandeModel(
            Commande commande
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_BACK_MODEL_COMMANDE)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#montantText")).setText("Montant : " + commande.getMontant());
            ((Text) innerContainer.lookup("#dateText")).setText("Date : " + commande.getDate());
            ((Text) innerContainer.lookup("#idPanierText")).setText("Panier : " + commande.getPanier().getId());
            ((Text) innerContainer.lookup("#confirmeAdminText")).setText("ConfirmeAdmin : " + commande.isConfirmeAdmin());
            ((Text) innerContainer.lookup("#idAddressText")).setText("Description : " + replaceBadWords(commande.getBillingAddress().getDescription(),"C:/Users/rayen/Desktop/BadWords.txt")+"\n"+ "Nom et Prenom : " + commande.getBillingAddress().getNom());


            ((Button) innerContainer.lookup("#pdfButton")).setOnAction((event) -> genererPDF(commande));
            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((event) -> delete(commande));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }
        public Parent makeCommandeModel2(
            Commande commande
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Model2.fxml")));
            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#idAddressText")).setText("Nom et Prenom : " + commande.getBillingAddress().getNom());


            ((Button) innerContainer.lookup("#pdfButton")).setOnAction((event) -> genererPDF(commande));
            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((event) -> delete(commande));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

 
private void genererPDF(Commande commande) {
    Document document = new Document();
    PanierArticleService panierArticleService = PanierArticleService.getInstance();
    try {
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Facture.pdf"));
        document.open();
         
        com.itextpdf.text.Image companyLogo = com.itextpdf.text.Image.getInstance("C:/Users/rayen/Documents/NetBeansProjects/bourlaforme/src/com/bourlaforme/images/uploads/BourLaForme.jpg"); // Update with the path to your company logo image
                        companyLogo.scaleAbsoluteWidth(100);
                        companyLogo.scaleAbsoluteHeight(100);
                        document.add(companyLogo);
                        com.itextpdf.text.Font font = new com.itextpdf.text.Font();
                        font.setSize(20);
        
        document.add(new Paragraph(" Facture "));
        Paragraph companyInfo = new Paragraph();
        companyInfo.add(new Chunk("\n\n"));
        companyInfo.add(new Phrase("BourLaforme\n", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD)));
        companyInfo.add(new Phrase("1234 Street Address\n", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
        companyInfo.add(new Phrase("Tunis, 2050\n", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
        companyInfo.add(new Phrase("Phone: (216) 23 4444 194 \n", FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)));
        document.add(companyInfo);

        // Add user name as a tab
        document.add(Chunk.TABBING);
        document.add(new Paragraph("Nom du client : " + commande.getBillingAddress().getNom()));

        PdfPTable table = new PdfPTable(4); // 4 columns for product details
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        PdfPCell cell;

        cell = new PdfPCell(new Phrase("Nom"));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Description"));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Prix"));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Quantité"));
        table.addCell(cell);

        List<PanierArticle> articles = panierArticleService.searchByPanier(commande.getPanier().getId());
        for (PanierArticle article : articles) {
            cell = new PdfPCell(new Phrase(article.getArticle().getNom()));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(article.getArticle().getDescription()));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(article.getArticle().getPrix())));
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(article.getQuantity())));
            table.addCell(cell);
        }

        document.add(table);

        // Add signature place
        Paragraph signature = new Paragraph();
        signature.add(new Chunk("\n\n\n"));
        signature.add(new Phrase("Montant : "+commande.getMontant(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL)));
        signature.setAlignment(Element.ALIGN_RIGHT);
        document.add(signature);

        document.close();

        writer.close();

 


                        Desktop.getDesktop().open(new File("Facture.pdf"));
                    } catch (DocumentException | IOException e) {
                        e.printStackTrace();
                    }}

    private void delete(Commande commande) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer cette commande ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.isPresent()) {
            if (action.get() == ButtonType.OK) {
                commande.setConfirmeAdmin(true);
                if (CommandeService.getInstance().delete(commande.getId())) {
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_COMMANDE);
                } else {
                    AlertUtils.makeError("Could not delete commande");
                }
            }
        }
    }

    @FXML
    public void sort(ActionEvent actionEvent) {
        Constants.compareVar = sortCB.getValue();
        Collections.sort(listCommande);
        displayData("");
    }

    @FXML
    private void search(KeyEvent event) {
        displayData(searchTF.getText());
    }
        public void Logout (ActionEvent e) throws IOException {
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root=FXMLLoader.load(getClass().getResource("/com/bourlaforme/gui/Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
       @FXML
   void nom() {
        mainVBox.getChildren().clear();

        Collections.reverse(listCommande);

        if (!listCommande.isEmpty()) {
            for (Commande commande : listCommande) {
                
                    mainVBox.getChildren().add(makeCommandeModel2(commande));
                
            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    } 
        
        public void Stat (ActionEvent e) throws IOException {
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root=FXMLLoader.load(getClass().getResource("Stat.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

   public static String replaceBadWords(String originalString, String badWordsFilePath) {
        String cleanedString = originalString;
        try {
            // Read bad words from file
            List<String> badWords = Files.readAllLines(Paths.get(badWordsFilePath));

            // Replace bad words with asterisks
            for (String badWord : badWords) {
                if (badWord != null && !badWord.isEmpty()) {
                   StringBuilder asterisksBuilder = new StringBuilder();
          for (int i = 0; i < badWord.length(); i++) {
    asterisksBuilder.append("*");
}
String asterisks = asterisksBuilder.toString();
                    cleanedString = cleanedString.replaceAll("(?i)\\b" + badWord + "\\b", asterisks);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading bad words file: " + e.getMessage());
        }
        return cleanedString;
    }
}
