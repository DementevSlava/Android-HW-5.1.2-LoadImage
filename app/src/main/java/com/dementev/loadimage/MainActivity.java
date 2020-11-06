package com.dementev.loadimage;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    Double operand = null;
    String lastOperation = "=";
    TextView resultField;
    EditText numberField;
    TextView operationField;
    EditText imageName;
    View layoutRelative;
    View layoutSettings;
    String fileName;

    public static final int REQUEST_CODE_PERMISSION_READ_STORAGE = 10;
    public static final int REQUEST_CODE_PERMISSION_WRITE_STORAGE = 11;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION_READ_STORAGE);
        }

        init();


    }

    private void init() {
        resultField = findViewById(R.id.resultField);
        numberField = findViewById(R.id.numberField);
        operationField = findViewById(R.id.operationField);
        imageName = findViewById(R.id.editFileName);
        layoutRelative = findViewById(R.id.layoutRelative);
        layoutSettings = findViewById(R.id.layoutSettings);

        Button settings = findViewById(R.id.setting);
        Button loadBtn = findViewById(R.id.loadButton);

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

        settings.setOnClickListener(v -> {
            layoutRelative.setVisibility(View.INVISIBLE);
            layoutSettings.setVisibility(View.VISIBLE);
        });

        loadBtn.setOnClickListener(v -> {
            fileName = imageName.getText().toString();
            layoutRelative.setVisibility(View.VISIBLE);
            layoutSettings.setVisibility(View.INVISIBLE);
            loadImage(fileName);
        });

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

    private void loadImage(String fileName){

        if (isExternalStorageWritable()){
            File pic = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
            Bitmap bitmap = BitmapFactory.decodeFile(pic.getAbsolutePath());
            ImageView im = findViewById(R.id.imageView);
            im.setImageBitmap(bitmap);
        }

    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
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