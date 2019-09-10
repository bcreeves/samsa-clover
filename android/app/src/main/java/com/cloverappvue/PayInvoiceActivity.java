package com.cloverappvue;

import android.accounts.Account;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.Context;
import android.util.Log;

import com.clover.sdk.util.CloverAccount;
import com.clover.sdk.v1.BindingException;
import com.clover.sdk.v1.ClientException;
import com.clover.sdk.v1.Intents;
import com.clover.sdk.v1.ServiceException;
import com.clover.sdk.v3.inventory.PriceType;
import com.clover.sdk.v3.order.Order;
import com.clover.sdk.v3.order.OrderConnector;
import com.clover.sdk.v3.inventory.Item;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PayInvoiceActivity extends Activity {

    private Account account;
    private OrderConnector orderConnector;
    private Order order;

    static final int REGISTER_ACTIVITY_REQUEST = 5694;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve the Clover account
        if (account == null) {
            account = CloverAccount.getAccount(PayInvoiceActivity.this);
            // If an account can't be acquired, exit the app
            if (account == null) {
                Toast.makeText(this, getString(R.string.no_account), Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        }

        // Create and Connect
        connect();

        // create order
        new OrderAsyncTask().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Get response from Register Activity and send to React Module
        if (requestCode == REGISTER_ACTIVITY_REQUEST) {
            // Log.d("PayInvoiceActivity.java", data.toString());

            Intent output = new Intent();
            if (resultCode == Activity.RESULT_OK) {
                setResult(Activity.RESULT_OK, output);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                setResult(Activity.RESULT_CANCELED, output);
            }
        }
        // End activity
        finish();
    }

    // Start intent to launch Clover's register pay activity.
    private void startRegisterIntent() {
        Intent intent = new Intent(Intents.ACTION_CLOVER_PAY);
        intent.putExtra(Intents.EXTRA_CLOVER_ORDER_ID, order.getId());
        startActivityForResult(intent, REGISTER_ACTIVITY_REQUEST);
    }

    @Override
    protected void onPause() {
        disconnect();
        super.onPause();
    }

    // Establishes a connection with the connectors
    private void connect() {
        disconnect();
        if (account != null) {
            orderConnector = new OrderConnector(this, account, null);
            orderConnector.connect();
        }
    }

    // Disconnects from the connectors
    private void disconnect() {
        if (orderConnector != null) {
            orderConnector.disconnect();
            orderConnector = null;
        }
    }

    // Creates a new order w/ hardcoded inventory item
    private class OrderAsyncTask extends AsyncTask<Void, Void, Order> {

        @Override
        protected final Order doInBackground(Void... params) {
            Order mOrder;
            List<Item> merchantItems;
            Item mItem;
            Intent mIntent = getIntent();
            Integer balanceRemaining = mIntent.getIntExtra("balanceRemaining", 0);
            try {
                // Create a new order
                mOrder = orderConnector.createOrder(new Order());
                orderConnector.addVariablePriceLineItem(mOrder.getId(), "P5J8R5VCB98ZC", balanceRemaining, "Custom Name", null);
                return mOrder;
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (ClientException e) {
                e.printStackTrace();
            } catch (ServiceException e) {
                e.printStackTrace();
            } catch (BindingException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected final void onPostExecute(Order order) {
            // Starts register intent if order is valid
            if (!isFinishing()) {
                PayInvoiceActivity.this.order = order;
                if (order != null) {
                    startRegisterIntent();
                }
            }
        }
    }
}