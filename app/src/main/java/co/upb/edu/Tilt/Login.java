package co.upb.edu.Tilt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    //Referencia a la BD
    private FirebaseDatabase usuariosBD;
    private DatabaseReference losUsuariosBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Firebase
        usuariosBD = FirebaseDatabase.getInstance();
        losUsuariosBD = usuariosBD.getReference("Usuarios");

        //Botones
        Button btnLoginVolver = (Button) findViewById(R.id.btnLoginVolver);
        Button btnLoginRegistrar = (Button) findViewById(R.id.btnLoginRegistrar);
        Button btnIngresar = (Button) findViewById(R.id.btnIngresar);
        inicializarBotones(btnLoginVolver, btnLoginRegistrar, btnIngresar);
    }
    private void inicializarBotones (Button volver, Button registrar, Button ingresar){
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Registro.class);
                startActivity(intent);
            }
        });

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final boolean[] yaPregunto = {false};

                EditText editTxtUsuario = (EditText) findViewById(R.id.editTxtUsuario);
                EditText editTxtPassword = (EditText) findViewById(R.id.editTxtPassword);

                String sUsuario = editTxtUsuario.getText().toString();
                String sPassword = editTxtPassword.getText().toString();

                if(!TextUtils.isEmpty(sUsuario) && !TextUtils.isEmpty(sPassword)){
                    losUsuariosBD.child(sUsuario).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(!yaPregunto[0]){
                                if(dataSnapshot.exists()){
                                    if(dataSnapshot.child("pass").getValue().toString().equals(sPassword)){
                                        Toast.makeText(Login.this, "Bienvenido "+sUsuario, Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(Login.this, Home.class);
                                        intent.putExtra(MainActivity.usuarioActual, sUsuario);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(Login.this, "Contraseña incorrecta", Toast.LENGTH_LONG).show();
                                    }
                                }
                                else{
                                    Toast.makeText(Login.this, "Este usuario no esta registrado", Toast.LENGTH_LONG).show();
                                }
                            }
                            yaPregunto[0] = true;
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    Toast.makeText(Login.this, "Complete todos los campos", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}