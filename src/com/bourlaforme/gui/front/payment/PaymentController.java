package com.bourlaforme.gui.front.payment;

import com.bourlaforme.entities.Commande;
import com.bourlaforme.gui.front.MainWindowController;
import com.bourlaforme.services.CommandeService;
import com.bourlaforme.services.PaymentService;
import com.bourlaforme.utils.AlertUtils;
import com.bourlaforme.utils.Constants;
import com.stripe.exception.StripeException;
import static java.time.Clock.system;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class PaymentController {

    @FXML
    private TextField emailTF;
    @FXML
    private TextField NomTF;
    @FXML
    private TextField CardTF;
    @FXML
    private TextField MoisTF;
    @FXML
    private TextField anneeTF;
    @FXML
    private TextField cvvTF;

    private PaymentService paymentService;
      private int i=0  ; 
    List<Commande> listCommande = CommandeService.getInstance().getAll();
    Commande lastCommande = listCommande.get(listCommande.size() - 1);
    public void initialize() {

     }

    @FXML
    public void handlePayment() {
        String email = emailTF.getText();
        String name = NomTF.getText();
         String cardNumber = CardTF.getText();
        String expMonth = MoisTF.getText();
        String expYear = anneeTF.getText();
        String cvc = cvvTF.getText();

        PaymentService paymentService = new PaymentService("sk_test_51MdLLSIdxzsb4CtapSbocwfwsUnBI1w4LtjUDAtRvw1k7SK2y0hdpW5SjZurt6htOehVYAV0tILGgXAubCiYWFvJ00O2fSM0G6");
        try {
            boolean paymentSuccessful = paymentService.payer(email, name, lastCommande.getMontant(), cardNumber, expMonth, expYear, cvc);
            if (paymentSuccessful) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Payment Successful");
                alert.setHeaderText(null);
                alert.setContentText("Your payment was successful!");
                alert.showAndWait();
                lastCommande.setConfirmeAdmin(true);
                System.out.println(lastCommande.isConfirmeAdmin());
                CommandeService.getInstance().edit(lastCommande);
                MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_MY_PANIER);
                AlertUtils.makeSuccessNotification("Payment Successful");

            } else {
 
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Payment Failed");
                alert.setHeaderText(null);
                alert.setContentText("Your payment was Failed !");
                alert.showAndWait();
                 MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_MY_PANIER);

 
        }}
            catch (StripeException e) {
                
            if (i==3){
                        System.out.println("error"+i);
            Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Payment Failed");
                alert.setHeaderText(null);
                alert.setContentText("Your payment failed ");
                alert.showAndWait();
                MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_MY_PANIER);

            } else {
                                        i=i+1;
                  Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Payment failed");
                alert.setHeaderText(null);
                alert.setContentText("Your payment failed. Please try again later. (you have "+(4-i)+" more tries left");
                alert.showAndWait();
                 }
        }
    }
}
