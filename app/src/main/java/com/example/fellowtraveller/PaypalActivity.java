package com.example.fellowtraveller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.fellowtraveller.Config.Config;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;

public class PaypalActivity extends AppCompatActivity {
    public static  final int PAYPAL_REQUES_CODE = 7171;


    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) // use SandBox because we on test
            .clientId(Config.PAYPAL_CLIENT_ID);

    Button btnPayNow;
    EditText edtAmount;

    String amount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal);

        btnPayNow =  findViewById(R.id.payButton);
        edtAmount = (EditText) findViewById(R.id.Amount);
    }
}
