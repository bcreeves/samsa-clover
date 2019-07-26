package com.cloverappvue;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.view.View;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.ReactApplicationContext;

import com.clover.connector.sdk.v3.DisplayConnector;
import com.clover.connector.sdk.v3.PaymentConnector;
import com.clover.sdk.v3.base.CardData;
import com.clover.sdk.v3.base.Challenge;
import com.clover.sdk.v3.connector.IDisplayConnector;
import com.clover.sdk.v3.connector.IDisplayConnectorListener;
import com.clover.sdk.v3.payments.CardTransaction;
import com.clover.sdk.v3.payments.CardType;
import com.clover.sdk.v3.payments.Result;
import com.clover.sdk.v3.remotepay.AuthResponse;
import com.clover.sdk.v3.remotepay.CapturePreAuthResponse;
import com.clover.sdk.v3.remotepay.CloseoutRequest;
import com.clover.sdk.v3.remotepay.CloseoutResponse;
import com.clover.sdk.v3.remotepay.ConfirmPaymentRequest;
import com.clover.sdk.v3.remotepay.ManualRefundRequest;
import com.clover.sdk.v3.remotepay.ManualRefundResponse;
import com.clover.sdk.v3.remotepay.PaymentResponse;
import com.clover.sdk.v3.remotepay.PreAuthRequest;
import com.clover.sdk.v3.remotepay.PreAuthResponse;
import com.clover.sdk.v3.remotepay.ReadCardDataRequest;
import com.clover.sdk.v3.remotepay.ReadCardDataResponse;
import com.clover.sdk.v3.remotepay.RefundPaymentResponse;
import com.clover.sdk.v3.remotepay.ResponseCode;
import com.clover.sdk.v3.remotepay.RetrievePaymentRequest;
import com.clover.sdk.v3.remotepay.RetrievePaymentResponse;
import com.clover.sdk.v3.remotepay.RetrievePendingPaymentsResponse;
import com.clover.sdk.v3.remotepay.SaleRequest;
import com.clover.sdk.v3.remotepay.SaleResponse;
import com.clover.sdk.v3.remotepay.TipAdjustAuthResponse;
import com.clover.sdk.v3.remotepay.VaultCardResponse;
import com.clover.sdk.v3.remotepay.VerifySignatureRequest;
import com.clover.sdk.v3.remotepay.VoidPaymentResponse;
import com.clover.sdk.v3.remotepay.TipAdded;
import com.clover.sdk.v3.remotepay.VoidPaymentResponse;
import com.clover.sdk.v3.remotepay.VoidPaymentRefundResponse;
import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v3.connector.IPaymentConnector;
import com.clover.sdk.v3.connector.IPaymentConnectorListener;
import com.clover.sdk.v3.connector.ExternalIdUtils;
import com.clover.sdk.v3.payments.Credit;
import com.clover.sdk.v3.payments.Payment;
import android.accounts.Account;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PayInvoiceActivity extends ReactActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_example_pos);

        // Initialize the PaymentConnector with a listener
        final PaymentConnector paymentConnector = initializePaymentConnector();

        SaleRequest saleRequest = setupSaleRequest();
        paymentConnector.sale(saleRequest);
    }

    private PaymentConnector initializePaymentConnector() {
        // Get the Clover account that will be used with the service; uses the
        // GET_ACCOUNTS permission
        Account cloverAccount = CloverAccount.getAccount(PayInvoiceActivity.this);

        // Set your RAID as the remoteApplicationId
        // Set this in .env
        String remoteApplicationId = BuildConfig.RAID;

        // Implement the interface
        IPaymentConnectorListener paymentConnectorListener = new IPaymentConnectorListener() {
            @Override
            public void onSaleResponse(SaleResponse response) {
                String result;
                if (response.getSuccess()) {
                    result = "Sale was successful";
                } else {
                    result = "Sale was unsuccessful" + response.getReason() + ":" + response.getMessage();
                }
                Toast.makeText(PayInvoiceActivity.this, result, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPreAuthResponse(PreAuthResponse response) {
                // return;
            };

            @Override
            public void onAuthResponse(AuthResponse response) {
                // return;
            };

            @Override
            public void onTipAdjustAuthResponse(TipAdjustAuthResponse response) {
                // return;
            };

            @Override
            public void onCapturePreAuthResponse(CapturePreAuthResponse response) {
                // return;
            };

            @Override
            public void onVerifySignatureRequest(VerifySignatureRequest request) {
                // return;
            };

            @Override
            public void onConfirmPaymentRequest(ConfirmPaymentRequest request) {
                // return;
            };

            @Override
            public void onManualRefundResponse(ManualRefundResponse response) {
                // return;
            };

            @Override
            public void onRefundPaymentResponse(RefundPaymentResponse response) {
                // return;
            };

            @Override
            public void onTipAdded(TipAdded tipAdded) {
                // return;
            };

            @Override
            public void onVoidPaymentResponse(VoidPaymentResponse response) {
                // return;
            };

            @Override
            public void onVaultCardResponse(VaultCardResponse response) {
                // return;
            };

            @Override
            public void onRetrievePendingPaymentsResponse(
                    RetrievePendingPaymentsResponse retrievePendingPaymentResponse) {
                // return;
            };

            @Override
            public void onReadCardDataResponse(ReadCardDataResponse response) {
                // return;
            };

            @Override
            public void onCloseoutResponse(CloseoutResponse response) {
                // return;
            };

            @Override
            public void onRetrievePaymentResponse(RetrievePaymentResponse response) {
                // return;
            };

            @Override
            public void onVoidPaymentRefundResponse(VoidPaymentRefundResponse response) {
                // return;
            };

            @Override
            public void onDeviceConnected() {
                // return;
            }

            @Override
            public void onDeviceDisconnected() {
                // return;
            }
        };

        MainApplication application = (MainApplication) PayInvoiceActivity.this.getApplication();
        ReactNativeHost reactNativeHost = application.getReactNativeHost();
        ReactInstanceManager reactInstanceManager = reactNativeHost.getReactInstanceManager();
        ReactContext reactContext = reactInstanceManager.getCurrentReactContext();
        // Create the PaymentConnector with the context, account, listener, and RAID
        return new PaymentConnector(reactContext, cloverAccount, paymentConnectorListener,
                remoteApplicationId);
    }

    private SaleRequest setupSaleRequest() {
        // Create a new SaleRequest and populate the required request fields
        SaleRequest saleRequest = new SaleRequest();
        saleRequest.setExternalId(ExternalIdUtils.generateNewID()); // required, but can be any string
        saleRequest.setAmount(1000L);

        return saleRequest;
    }
}
