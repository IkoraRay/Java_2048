package org.tensorflow.lite.examples.classification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ControleBotoes extends AppCompatActivity {
        private Button cima;
        private Button baixo;
        private Button direita;
        private Button esquerda;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controle_botoes);
        cima =  (Button)findViewById(R.id.bcima);
        cima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        baixo = (Button)findViewById(R.id.bbaixo);
        baixo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        direita = (Button)findViewById(R.id.bdireita);
        direita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        esquerda = (Button)findViewById(R.id.besquerda);
        esquerda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
