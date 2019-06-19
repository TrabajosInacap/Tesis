package com.example.estesi;

import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.example.estesi.Entidades.Persona;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText nombre,correo,clave;
    private Button btnRegis;
    private FirebaseAuth mAuth;// ...
    private FirebaseDatabase database;
    private DatabaseReference referenceUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nombre = (EditText) findViewById(R.id.txtNombre);
        correo = (EditText) findViewById(R.id.txtCorreo);
        clave = (EditText) findViewById(R.id.txtClave);
        btnRegis =(Button) findViewById(R.id.btnRegistrar);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        referenceUsuarios = database.getReference("Usuarios");


        //Evento de Registro
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String correo1 = correo.getText().toString();
                final String nombre1 = nombre.getText().toString();


                if(isValidEmail(correo1) && ValidaContraseña() && ValidarNombre(nombre1)){
                    String contraseña1 = clave.getText().toString();
                    // Initialize Firebase Auth

                    //creacion de usuarios
                    mAuth.createUserWithEmailAndPassword(correo1, contraseña1)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(MainActivity.this,"Se registro correactamente",Toast.LENGTH_SHORT).show();
                                        Persona persona = new Persona();
                                        persona.setCorreo(correo1);
                                        persona.setNombre(nombre1); // datos que iran a la BD
                                        referenceUsuarios.push().setValue(persona);
                                        finish();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(MainActivity.this,"Error al Registrar",Toast.LENGTH_SHORT).show();

                                    }

                                    // ...
                                }
                            });


                }else{
                    Toast.makeText(MainActivity.this,"Validaciones Funcionando",Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
    //validacion correo
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
    //Validacion de nombre de usuario
    public boolean ValidarNombre(String nombre1){
        return !nombre1.isEmpty();
    }
}
