package com.cloverappvue;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.CatalystInstance;

import android.content.Intent;

import java.util.Map;
import java.util.HashMap;

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
import android.content.Context;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import java.util.List;

public class PayInvoiceModule extends ReactContextBaseJavaModule {

    private static final int PAYMENT_ACTIVITY_REQUEST = 568;
    private static final String E_ACTIVITY_DOES_NOT_EXIST = "E_ACTIVITY_DOES_NOT_EXIST";

    private Promise mPromise;

    private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {            
            if (requestCode == PAYMENT_ACTIVITY_REQUEST) {
                if (resultCode == Activity.RESULT_OK) {
                    // Log.d("PayInvoiceModule.java", "RESULT_OK");
                    mPromise.resolve("RESULT_OK");
                } else {
                    mPromise.resolve("RESULT_CANCELED");
                }
            }
        }
    };

    public PayInvoiceModule(ReactApplicationContext reactContext) {
        super(reactContext);
        reactContext.addActivityEventListener(mActivityEventListener);
    }

    @Override
    public String getName() {
        return "PayInvoice";
    }

    @ReactMethod
    public void makePayment(Integer balanceRemaining, final Promise promise) {
        Activity activity = getCurrentActivity();

        if (activity == null) {
            promise.reject(E_ACTIVITY_DOES_NOT_EXIST, "Activity doesn't exist");
            return;
        }

        // Store the promise to resolve/reject when activity returns data
        mPromise = promise;

        if (activity != null) {
            try {
                Intent intent = new Intent(activity, PayInvoiceActivity.class);
                intent.putExtra("balanceRemaining", balanceRemaining);
                activity.startActivityForResult(intent, PAYMENT_ACTIVITY_REQUEST);
            } catch (Exception e) {
                mPromise.reject("ACTIVITY_FAILED", e);
                mPromise = null;
            }
        }
    }
}