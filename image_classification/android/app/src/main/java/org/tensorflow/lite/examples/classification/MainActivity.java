package org.tensorflow.lite.examples.classification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button botao1;
    private Button botao2;
    private Button botao3;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        botao1 = (Button)findViewById(R.id.button);
        botao1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openbotoes();
            }
        });

        botao2 = (Button) findViewById(R.id.button2);
        botao2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opentensor();
            }
        });

        botao3 = (Button) findViewById(R.id.button3);
        botao3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opensetup();
            }
        });


    }
    public void openbotoes() {
        Intent intent1 = new Intent(this,ControleBotoes.class);
        startActivity(intent1);
    }

    public void opentensor(){
        Intent intent2 = new Intent(this,ClassifierActivity.class);
        startActivity(intent2);

    }
    public void opensetup(){
        Intent intent3 = new Intent(this,Setup.class);
        startActivity(intent3);

    }
}
