package sg.edu.rp.c346.id20032316.l03billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    EditText etamount;
    EditText etPaxNum;
    ToggleButton SVS;
    ToggleButton GST;
    EditText etDis;
    RadioGroup rgPay;
    Button btnSplit;
    Button btnRestart;
    TextView tvBill;
    TextView tvEachPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etamount = findViewById(R.id.etAmount); // amount
        etPaxNum = findViewById(R.id.etPax); // number of people sharing
        SVS = findViewById(R.id.tbSVS); // whether have Service charge
        GST = findViewById(R.id.tbGST); // whether have GST
        etDis = findViewById(R.id.etDiscount); // amount of discount
        rgPay = findViewById(R.id.rgPayment); // way of payment
        btnSplit = findViewById(R.id.btnSplit); // generate result
        btnRestart = findViewById(R.id.btnRestart); // restart
        tvBill = findViewById(R.id.tvBill); // total bill
        tvEachPay = findViewById(R.id.tvEach);

        btnSplit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // bill with extra charges
                String sAmt = etamount.getText().toString();
                String numPeo = etPaxNum.getText().toString();
//                if (sAmt.isEmpty() == false && numPeo.isEmpty() == false) { // amount and pax size is not empty
                    double amount = Double.parseDouble(sAmt);
                    double tbill = 0.0;
                    if (SVS.isChecked() && !GST.isChecked()) {
                        tbill = amount * 1.1;
                    } else if (!SVS.isChecked() && GST.isChecked()) {
                        tbill = amount * 1.07;
                    } else if (SVS.isChecked()&& GST.isChecked()) {
                        tbill = amount * 1.17;
                    }

                    // bill after discount
                    String discount = etDis.getText().toString();
                    if (!discount.isEmpty()) {
                        tbill = tbill * (1- Double.parseDouble(discount));
                    }

                    // payment method
                    String payment = "";
                    if (rgPay.getCheckedRadioButtonId() == R.id.rbCash) {
                        payment = " in cash";
                    } else {
                        payment = " via PayNow to 912345678";
                    }

                    // print total bill
                    String bill = tvBill.getText().toString();
                    tvBill.setText(bill + String.format("%.2f", tbill));

                    // print each pay
                    String eachpay = tvEachPay.getText().toString();
                    int pax = Integer.parseInt(eachpay);
                    if (pax == 1) {
                        tvEachPay.setText(eachpay + tbill);
                    } else {
                        tvEachPay.setText(eachpay + String.format("%.2f, tbill / pax" + payment));
                    }
                }

//            }
        });


        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etamount.setText("");
                etPaxNum.setText("");
                SVS.setChecked(false);
                GST.setChecked(false);
                etDis.setText("");
            }
        });



    }
}