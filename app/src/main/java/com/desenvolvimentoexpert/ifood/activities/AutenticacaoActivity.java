package com.desenvolvimentoexpert.ifood.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.desenvolvimentoexpert.ifood.R;

public class AutenticacaoActivity extends AppCompatActivity {

    private EditText boxEmail, boxPassword;
    private Switch switchLogar;
    private TextView textCadastrar;
    private Button btnAcessar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticacao);
        getSupportActionBar().hide();

        startComponents();

        btnAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void startComponents() {
        boxEmail = findViewById(R.id.textAuthEmail);
        boxPassword = findViewById(R.id.textAuthPass);
        switchLogar = findViewById(R.id.switchAuthLogar);
        textCadastrar = findViewById(R.id.textAuthCadastrar);
        btnAcessar = findViewById(R.id.buttonAuthAcessar);
    }
}
