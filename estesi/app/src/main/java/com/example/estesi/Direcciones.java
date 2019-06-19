package com.example.estesi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Direcciones extends AppCompatActivity {
    private Button btnCerr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.direcciones);
        btnCerr=(Button)findViewById(R.id.btnCerrar);

        btnCerr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Direcciones.this,LoginActivity.class));
            }
        });
    }
}
