package com.example.estesi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private EditText correo,clave;
    private Button btnLog,btnRegis;
    private FirebaseAuth mAuth;// ...
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        correo = (EditText) findViewById(R.id.txtCorreo);
        clave = (EditText) findViewById(R.id.txtClave);
        btnRegis =(Button) findViewById(R.id.btnRegistrar);
        btnLog =(Button) findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo1 = correo.getText().toString();
                String contraseña1 = clave.getText().toString();
                if(isValidEmail(correo1) && ValidaContraseña()){
                    mAuth.signInWithEmailAndPassword(correo1, contraseña1)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(LoginActivity.this,"Ha ingresado correctamente",Toast.LENGTH_SHORT).show();

                                        nextActivity();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(LoginActivity.this,"Correo o contraseña incorrecta",Toast.LENGTH_SHORT).show();

                                    }

                                    // ...
                                }
                            });



                }else{
                    Toast.makeText(LoginActivity.this,"Validaciones Funcionando",Toast.LENGTH_SHORT).show();
                }
            }
        });
btnRegis.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
    }
});

    }
    private  static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    public boolean ValidaContraseña(){
        String contraseña1 = clave.getText().toString();
        //contraseña debe tener entre 6 a 16 caracteres
        if(contraseña1.length()>=6 && contraseña1.length()<=16){
            return true;

        }else return false;
    }
    @Override

    protected void onResume(){
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();//Verifica si el usuario se ha logueado correctamente
        if(currentUser!=null){
            Toast.makeText(LoginActivity.this,"Sesión activa",Toast.LENGTH_SHORT).show();
            nextActivity();
            finish();

        }


    }

    private void nextActivity(){
        startActivity(new Intent(LoginActivity.this,Direcciones.class));

    }

}
