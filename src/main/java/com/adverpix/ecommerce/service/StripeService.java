package com.adverpix.ecommerce.service;



import com.adverpix.ecommerce.dto.ProductRequest;
import com.adverpix.ecommerce.dto.StripeResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${stripe.secretKey}")
    private String secretKey;

    //stripe -API
    //-> productName , amount , quantity , currency
    //-> return sessionId and url



    public StripeResponse checkoutProducts(ProductRequest productRequest) {
        Stripe.apiKey = secretKey;

        try {
            // Create product data
            SessionCreateParams.LineItem.PriceData.ProductData productData =
                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                            .setName(productRequest.getName())
                            .build();

            // Create price data
            SessionCreateParams.LineItem.PriceData priceData =
                    SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency(productRequest.getCurrency() != null ? productRequest.getCurrency() : "USD")
                            .setUnitAmount(productRequest.getAmount())
                            .setProductData(productData)
                            .build();

            // Create line item
            SessionCreateParams.LineItem lineItem =
                    SessionCreateParams
                            .LineItem.builder()
                            .setQuantity(productRequest.getQuantity())
                            .setPriceData(priceData)
                            .build();

            // Create session parameters
            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .setSuccessUrl("http://192.168.3.150:8080/payment-success")
                            .setCancelUrl("http://192.168.3.150:8080/payment-failed")
                            .addLineItem(lineItem)
                            .build();

            // Create Stripe session
            Session session = Session.create(params);

            // Ensure session is not null
            if (session == null) {
                throw new RuntimeException("Stripe session creation failed: received null session");
            }

            return StripeResponse
                    .builder()
                    .status("SUCCESS")
                    .message("Payment session created")
                    .sessionId(session.getId())
                    .sessionUrl(session.getUrl())
                    .build();

        } catch (StripeException e) {
            return StripeResponse
                    .builder()
                    .status("FAILED")
                    .message("Error creating Stripe session: " + e.getMessage())
                    .sessionId(null)
                    .sessionUrl(null)
                    .build();
        } catch (RuntimeException e) {
            return StripeResponse
                    .builder()
                    .status("FAILED")
                    .message(e.getMessage())
                    .sessionId(null)
                    .sessionUrl(null)
                    .build();
        }
    }


}
