package com.fer.hr.payment.service;

import com.fer.hr.payment.model.CompletedOrder;
import com.fer.hr.payment.model.PaymentOrder;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@AllArgsConstructor
public class PaymentService {

    private final PayPalHttpClient payPalHttpClient;

    public PaymentOrder createPayment(BigDecimal fee) {
        //Create a new OrderRequest object to define the payment details
        OrderRequest orderRequest = new OrderRequest();

        //Set the payment intent to "CAPTURE" for immediate payment
        orderRequest.checkoutPaymentIntent("CAPTURE");

        //Set the amount details with currency code and the fee to pay
        AmountWithBreakdown amountWithBreakdown = new AmountWithBreakdown()
                .currencyCode("EUR").value(fee.toString());

        //Create a PurchaseUnitRequest object which contains the amount breakdown
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .amountWithBreakdown(amountWithBreakdown);

        //Add the purchase unit request to the order request
        orderRequest.purchaseUnits(List.of(purchaseUnitRequest));

        //Define the return and cancel URL for the user navigation after payment
        ApplicationContext applicationContext = new ApplicationContext()
                .returnUrl("http://localhost:4200/purchase/capture")
                .cancelUrl("http://localhost:4200/purchase/cancel");
        orderRequest.applicationContext(applicationContext);

        //Create the OrdersCreateRequest object to send to Paypal client
        OrdersCreateRequest ordersCreateRequest = new OrdersCreateRequest()
                .requestBody(orderRequest);

        try {
            //Try to execute the request to create order through Paypal HTTP client
            HttpResponse<Order> orderHttpResponse = payPalHttpClient.execute(ordersCreateRequest);
            Order order = orderHttpResponse.result();

            //Extracting the redirect URL from response to navigate to the Paypal view
            String redirectUrl = order.links().stream()
                    .filter(link -> "approve".equals(link.rel()))
                    .findFirst()
                    .orElseThrow(NoSuchElementException::new)
                    .href();
            return new PaymentOrder("success", order.id(), redirectUrl);

        } catch (IOException e) {
            log.error(e.getMessage());
            return new PaymentOrder("error");
        }
    }

    public CompletedOrder completePayment(String token) {
        //Create a new OrderCaptureRequest object to capture the authorized payment using the provided token
        OrdersCaptureRequest ordersCaptureRequest = new OrdersCaptureRequest(token);
        try {
            //Execute the capture request using the Paypal HTTP client
            HttpResponse<Order> httpResponse = payPalHttpClient.execute(ordersCaptureRequest);
            if (httpResponse.result().status() != null) {
                //Payment captured successful, return success message and token
                return new CompletedOrder("success", token);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        //Payment capture failed, return error message
        return new CompletedOrder("error");
    }

}
