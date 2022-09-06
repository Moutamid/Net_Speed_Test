package com.moutamid.speed_test;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.PurchaseInfo;

public class DonateActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler {
//    BillingClient billingClient;

    BillingProcessor bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        /*billingClient = BillingClient.newBuilder(this)
                .enablePendingPurchases()
                .setListener(new PurchasesUpdatedListener() {
                    @Override
                    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
                        if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {
                            for(Purchase purchase: list) {
                                if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED &&
                                        !purchase.isAcknowledged()) {
                                    verifyPurchase(purchase);
                                }
                            }
                        }
                    }
                }).build();
        connectToGooglePlayBilling();*/

        bp = BillingProcessor.newBillingProcessor(this, Constants.LICENSE_KEY, this);
        bp.initialize();

        /*findViewById(R.id.twohundred).setOnClickListener((View.OnClickListener) view -> {
            bp.purchase(DonateActivity.this, Constants.TWO_HUNDRED_DOLLAR_PRODUCT);
        });

        findViewById(R.id.twotwofive).setOnClickListener((View.OnClickListener) view -> {
            bp.purchase(DonateActivity.this, Constants.TWO_TWENTY_FIVE_DOLLAR_PRODUCT);
        });

        findViewById(R.id.twofoursix).setOnClickListener((View.OnClickListener) view ->
                bp.purchase(DonateActivity.this, Constants.TWO_FORTY_SIX_DOLLAR_PRODUCT)
        );
        findViewById(R.id.twosixfive).setOnClickListener((View.OnClickListener) view ->
                bp.purchase(DonateActivity.this, Constants.TWO_SIXTY_FIVE_DOLLAR_PRODUCT)
        );
        findViewById(R.id.threehundred).setOnClickListener((View.OnClickListener) view ->
                bp.purchase(DonateActivity.this, Constants.THREE_HUNDRED_DOLLAR_PRODUCT)
        );
*/
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable PurchaseInfo details) {
        Toast.makeText(DonateActivity.this, "Purchase successful", Toast.LENGTH_SHORT).show();
        TextView textView = findViewById(R.id.purchasedTextView);

        if (productId.equals( Constants.TWO_HUNDRED_DOLLAR_PRODUCT))
            textView.setText("Two hundred tokens purchased");
        if (productId.equals( Constants.TWO_TWENTY_FIVE_DOLLAR_PRODUCT))
            textView.setText("Two twenty five tokens purchased");
        if (productId.equals( Constants.TWO_FORTY_SIX_DOLLAR_PRODUCT))
            textView.setText("Two forty six tokens purchased");
        if (productId.equals( Constants.TWO_SIXTY_FIVE_DOLLAR_PRODUCT))
            textView.setText("Two sixty five tokens purchased");
        if (productId.equals( Constants.THREE_HUNDRED_DOLLAR_PRODUCT))
            textView.setText("Three hundred purchased");

        textView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {
        Toast.makeText(DonateActivity.this, "onBillingError: code: " + errorCode + " \n" + error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    protected void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        billingClient.queryPurchasesAsync(
                BillingClient.SkuType.INAPP,
                new PurchasesResponseListener() {
                    @Override
                    public void onQueryPurchasesResponse(@NonNull BillingResult billingResult, @NonNull List<Purchase> list) {
                        if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                            for(Purchase purchase: list) {
                                if(purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED &&
                                        !purchase.isAcknowledged()) {
                                    verifyPurchase(purchase);
                                }
                            }
                        }
                    }
                }
        );
    }
    private void connectToGooglePlayBilling(){
        billingClient.startConnection(
                new BillingClientStateListener() {
                    @Override
                    public void onBillingServiceDisconnected() {
                        connectToGooglePlayBilling();
                    }

                    @Override
                    public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                        if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                            getProductDetails();
                        }
                    }
                }
        );
    }
    private void verifyPurchase(Purchase purchase) {
        String requestUrl = "https://us-central1-playbillingtutorial.cloudfunctions.net/verifyPurchases?" +
                "purchaseToken=" + purchase.getPurchaseToken() + "&" +
                "purchaseTime=" + purchase.getPurchaseTime() + "&" +
                "orderId=" + purchase.getOrderId();
        Activity activity = this;
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                requestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject purchaseInfoFromServer = new JSONObject(response);
                            if(purchaseInfoFromServer.getBoolean("isValid")) {

                                *//*AcknowledgePurchaseParams acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder().setPurchaseToken(purchase.getPurchaseToken()).build();
                                billingClient.acknowledgePurchase(
                                        acknowledgePurchaseParams,
                                        new AcknowledgePurchaseResponseListener() {
                                            @Override
                                            public void onAcknowledgePurchaseResponse(@NonNull BillingResult billingResult) {
                                                if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                                                    Toast.makeText(activity, "Consumed!", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }
                                );*//*
                                ConsumeParams consumeParams = ConsumeParams.newBuilder().setPurchaseToken(purchase.getPurchaseToken()).build();
                                billingClient.consumeAsync(
                                        consumeParams,
                                        new ConsumeResponseListener() {
                                            @Override
                                            public void onConsumeResponse(@NonNull BillingResult billingResult, @NonNull String s) {
                                                if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                                                    Toast.makeText(activity, "Consumed!", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }
                                );
                            }
                        } catch (Exception err) {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        Volley.newRequestQueue(this).add(stringRequest);

    }
    private void getProductDetails(){
        List<String> productIds = new ArrayList<>();
        productIds.add("fifty.com.dani");
        SkuDetailsParams getProductDetailsQuery = SkuDetailsParams
                .newBuilder()
                .setSkusList(productIds)
                .setType(BillingClient.SkuType.INAPP)
                .build();
        Activity activity = this;
        billingClient.querySkuDetailsAsync(
                getProductDetailsQuery,
                new SkuDetailsResponseListener() {
                    @Override
                    public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> list) {
                        if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK &&
                                list != null) {
                            TextView itemNameTextView = findViewById(R.id.tvSilver);
                            Button itemPriceButton = findViewById(R.id.btnSilver);
                            SkuDetails itemInfo = list.get(0);
                            itemNameTextView.setText(itemInfo.getTitle());
                            itemPriceButton.setText(itemInfo.getPrice());
                            itemPriceButton.setOnClickListener(
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            billingClient.launchBillingFlow(
                                                    activity,
                                                    BillingFlowParams.newBuilder().setSkuDetails(itemInfo).build()
                                            );
                                        }
                                    }
                            );
                        }
                    }
                }
        );
    }*/
}