
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bourlaforme.services;
 

import java.util.HashMap;

import java.util.Map;

import com.stripe.Stripe;
 

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import java.util.HashMap;

import java.util.HashMap;

import java.util.Map;

public class PaymentService {

    private String apiKey;

    public PaymentService(String apiKey) {
        this.apiKey = apiKey;
    }

    public boolean payer(String email, String name, int amount, String cardNumber, String expMonth, String expYear, String cvc) throws StripeException {
        Stripe.apiKey = apiKey;

        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", amount);
        chargeParams.put("currency", "usd");
        chargeParams.put("source", createCardToken(cardNumber, expMonth, expYear, cvc).getId());
        chargeParams.put("description", "Payment for " + name + " (" + email + ")");

        Charge charge = Charge.create(chargeParams);

        return charge.getPaid();
    }

    private com.stripe.model.Token createCardToken(String cardNumber, String expMonth, String expYear, String cvc) throws StripeException {
        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("number", cardNumber);
        cardParams.put("exp_month", expMonth);
        cardParams.put("exp_year", expYear);
        cardParams.put("cvc", cvc);

        Map<String, Object> tokenParams = new HashMap<>();
        tokenParams.put("card", cardParams);

        return com.stripe.model.Token.create(tokenParams);
    } 
}