package com.dementev.loadimage;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Double operand = null;
    String lastOperation = "=";
    TextView resultField;
    EditText numberField;
    TextView operationField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();
    }

    private void init() {
        resultField = findViewById(R.id.resultField);
        numberField = findViewById(R.id.numberField);
        operationField = findViewById(R.id.operationField);


        final Button oneBtn = findViewById(R.id.oneBtn);
        final Button twoBtn = findViewById(R.id.twoBtn);
        final Button threeBtn = findViewById(R.id.threeBtn);
        final Button fourBtn = findViewById(R.id.fourBtn);
        final Button fiveBtn = findViewById(R.id.fiveBtn);
        final Button sixBtn = findViewById(R.id.sixBtn);
        final Button sevenBtn = findViewById(R.id.sevenBtn);
        final Button eightBtn = findViewById(R.id.eightBtn);
        final Button nineBtn = findViewById(R.id.nineBtn);
        final Button zeroBtn = findViewById(R.id.zeroBtn);
        final Button pointBtn = findViewById(R.id.pointBtn);

        final Button divBtn = findViewById(R.id.divisionBtn);
        Button mulBtn = findViewById(R.id.multiplyBtn);
        Button minusBtn = findViewById(R.id.minusBtn);
        Button plusBtn = findViewById(R.id.plusBtn);
        Button equalBtn = findViewById(R.id.equalBtn);
        Button clearBtn = findViewById(R.id.clearBtn);
        Button signBtn = findViewById(R.id.signBtn);
        Button percentBtn = findViewById(R.id.percentBtn);

        oneBtn.setOnClickListener(v -> clickNumber(v));

        twoBtn.setOnClickListener(v -> clickNumber(v));

        threeBtn.setOnClickListener(v -> clickNumber(v));

        fourBtn.setOnClickListener(v -> clickNumber(v));

        fiveBtn.setOnClickListener(v -> clickNumber(v));

        sixBtn.setOnClickListener(v -> clickNumber(v));

        sevenBtn.setOnClickListener(v -> clickNumber(v));

        eightBtn.setOnClickListener(v -> clickNumber(v));

        nineBtn.setOnClickListener(v -> clickNumber(v));

        zeroBtn.setOnClickListener(v -> clickNumber(v));

        pointBtn.setOnClickListener(v -> clickNumber(v));

        clearBtn.setOnClickListener(v -> {
            resultField.setText("");
            numberField.setText("");
            operationField.setText("");
            operand = null;
        });

        signBtn.setOnClickListener(v -> {
            String tmp = numberField.getText().toString();
            if (tmp.contains("-")){
                tmp = tmp.substring(1);
                numberField.setText(tmp);
            } else{
                StringBuilder sb = new StringBuilder(tmp);
                sb.insert(0, "-");
                numberField.setText(sb.toString().replace('.', ','));

            }
        });

        percentBtn.setOnClickListener(v -> {
            try {
                Double number = Double.valueOf(numberField.getText().toString());
                Double tpm = operand * number / 100;
                numberField.setText(tpm.toString());
            } catch (Exception e){
                numberField.setText("0");
            }

        });

        divBtn.setOnClickListener(v -> operationClick(v));

        mulBtn.setOnClickListener(v -> operationClick(v));

        minusBtn.setOnClickListener(v -> operationClick(v));

        plusBtn.setOnClickListener(v -> operationClick(v));

        equalBtn.setOnClickListener(v -> operationClick(v));

    }

    public void operationClick(View view) {
        Button button = (Button) view;
        String op = button.getText().toString();
        String number = numberField.getText().toString();
        if (number.length() > 0) {
            number = number.replace(',', '.');
            try {
                performOperation(Double.valueOf(number), op);
            } catch (NumberFormatException e) {
                numberField.setText("");
            }
        }
        lastOperation = op;
        operationField.setText(lastOperation);
    }

    private void performOperation(Double number, String operation) {

        if (operand == null) {
            operand = number;
        } else {
            if (lastOperation.equals("=")) {
                lastOperation = operation;
            }
            switch (lastOperation) {
                case "=": {
                    operand = number;

                    break;
                }
                case "/": {
                    if (number == 0) {
                        operand = 0.0;
                    } else {
                        operand /= number;
                    }
                    break;
                }
                case "*": {
                    operand *= number;
                    break;
                }
                case "+": {
                    operand += number;
                    break;
                }
                case "-": {
                    operand -= number;
                    break;
                }
            }
        }
        resultField.setText(operand.toString().replace('.', ','));
        numberField.setText("");
    }

    public void clickNumber(View view) {

        Button button = (Button) view;
        numberField.append(button.getText());

        if (lastOperation.equals("=") && operand != null) {
            operand = null;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("OPERATION", lastOperation);
        if (operand != null)
            outState.putDouble("OPERAND", operand);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastOperation = savedInstanceState.getString("OPERATION");
        operand = savedInstanceState.getDouble("OPERAND");
        resultField.setText(operand.toString());
        operationField.setText(lastOperation);
    }


}