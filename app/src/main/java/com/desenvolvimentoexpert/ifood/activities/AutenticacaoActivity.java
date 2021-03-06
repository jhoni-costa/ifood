package com.desenvolvimentoexpert.ifood.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.desenvolvimentoexpert.ifood.R;
import com.desenvolvimentoexpert.ifood.helper.FirebaseConfig;
import com.desenvolvimentoexpert.ifood.helper.UsuarioFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class AutenticacaoActivity extends AppCompatActivity {

    private EditText boxEmail, boxPassword;
    private Switch switchLogar, switchTipoUsuario;
    private TextView textCadastrar;
    private Button btnAcessar;
    private LinearLayout layoutTipo;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autenticacao);
        getSupportActionBar().hide();

        startComponents();

        auth = FirebaseConfig.getFirebaseAuth();
        auth.signOut();
        checkUserAuth();

        switchLogar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    layoutTipo.setVisibility(View.VISIBLE);
                } else {
                    layoutTipo.setVisibility(View.GONE);
                }
            }
        });

        btnAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = boxEmail.getText().toString();
                String password = boxPassword.getText().toString();
                if (!email.isEmpty()) {
                    if (!password.isEmpty()) {
                        if (switchLogar.isChecked()) {
                            auth.createUserWithEmailAndPassword(
                                    email, password
                            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();

                                        String tipo = getTipoUsuario();
                                        UsuarioFirebase.atualizarTipoUsuario(tipo);
                                        goHomeActivity(tipo);
                                    } else {
                                        String exceptionError = "";
                                        try {
                                            throw task.getException();
                                        } catch (FirebaseAuthWeakPasswordException e) {
                                            exceptionError = "Digite uma senha mais forte!";
                                        } catch (FirebaseAuthInvalidCredentialsException e) {
                                            exceptionError = "Digite um e-mial válido!";
                                        } catch (FirebaseAuthUserCollisionException e) {
                                            exceptionError = "Este email já está cadastrado";
                                        } catch (Exception e) {
                                            exceptionError = "Erro bizarro ao cadastrar usuário: " + e.getMessage();
                                        }
                                        Toast.makeText(getApplicationContext(), "Erro: " + exceptionError, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            auth.signInWithEmailAndPassword(
                                    email, password
                            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Logado com sucesso!", Toast.LENGTH_SHORT).show();
                                        String tipo = task.getResult().getUser().getDisplayName();
                                        goHomeActivity(tipo);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Erro ao fazer login, tente novamente mais tarde!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Preencha a senha corretamente!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Preencha o e-mail corretamente!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getTipoUsuario() {
        return switchTipoUsuario.isChecked() ? "E" : "U";
    }

    private void checkUserAuth() {
        FirebaseUser usuario = auth.getCurrentUser();
        if (usuario != null) {
            String tipo = usuario.getDisplayName();
            goHomeActivity(tipo);
        }
    }

    private void goHomeActivity(String tipo) {
        if (tipo.equals("E")) {
            startActivity(new Intent(getApplicationContext(), EmpresaActivity.class));
        } else {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }
    }

    private void startComponents() {
        boxEmail = findViewById(R.id.textAuthEmail);
        boxPassword = findViewById(R.id.textAuthPass);
        switchLogar = findViewById(R.id.switchAuthLogar);
        textCadastrar = findViewById(R.id.textAuthCadastrar);
        btnAcessar = findViewById(R.id.buttonAuthAcessar);
        switchTipoUsuario = findViewById(R.id.switchAuthEmpresa);
        layoutTipo = findViewById(R.id.layoutTipoUsuario);
    }
}
