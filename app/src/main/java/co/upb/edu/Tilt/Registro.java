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

import java.util.ArrayList;
import java.util.List;

public class Registro extends AppCompatActivity {

    //Referencia a la BD
    private FirebaseDatabase usuariosBD;
    private DatabaseReference losUsuariosBD;
    private DatabaseReference losEmailsDB;
    private List<String> listaEmails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //Firebase
        usuariosBD = FirebaseDatabase.getInstance();
        losUsuariosBD = usuariosBD.getReference("Usuarios");
        losEmailsDB = usuariosBD.getReference("emails");

        //Botones
        Button btnRegistroVolver = (Button) findViewById(R.id.btnRegistroVolver);
        Button btnRegistrarRegistrar = (Button) findViewById(R.id.btnRegistrarRegistrar);
        inicializarBotones(btnRegistroVolver, btnRegistrarRegistrar);
    }
    private void inicializarBotones (Button volver, Button registrar){
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registro.this, Login.class);
                startActivity(intent);
                Registro.this.finish();
            }
        });
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final boolean[] existeEmail = {false};

                EditText correo = (EditText) findViewById(R.id.editTxtRegistrarEmail);
                EditText usuario = (EditText) findViewById(R.id.editTxtRegistroUsuario);
                EditText password = (EditText) findViewById(R.id.editTxtRegistroPassword);

                String sCorreo = correo.getText().toString();
                String sUsuario = usuario.getText().toString();
                String sPassword = password.getText().toString();

                if(!TextUtils.isEmpty(sCorreo) && !TextUtils.isEmpty(sUsuario) && !TextUtils.isEmpty(sPassword)){
                    Usuario user = new Usuario(sCorreo, sUsuario, sPassword);

                    losUsuariosBD.child(sUsuario).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                if(!existeEmail[0])
                                    Toast.makeText(Registro.this, "Este usuario ya esta registrado", Toast.LENGTH_LONG).show();
                            }
                            else{
                                losEmailsDB.child("emails").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(!dataSnapshot.exists()){
                                            listaEmails = new ArrayList<String>();
                                            listaEmails.add(sCorreo);
                                            losUsuariosBD.child(sUsuario).setValue(user);
                                            losEmailsDB.child("emails").setValue(listaEmails);
                                            Toast.makeText(Registro.this, "Usuario Registrado", Toast.LENGTH_LONG).show();
                                            existeEmail[0] = true;
                                            Intent intent = new Intent(Registro.this, Home.class);
                                            startActivity(intent);
                                            Registro.this.finish();
                                        }
                                        else{
                                            listaEmails = (List<String>) dataSnapshot.getValue();
                                            if(!existeEmail[0]){
                                                if(listaEmails.contains(sCorreo)){
                                                    Toast.makeText(Registro.this, "Este correo ya esta registrado", Toast.LENGTH_LONG).show();
                                                }
                                                else{
                                                    losUsuariosBD.child(sUsuario).setValue(user);
                                                    listaEmails.add(sCorreo);
                                                    losEmailsDB.child("emails").setValue(listaEmails);
                                                    Toast.makeText(Registro.this, "Usuario Registrado", Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(Registro.this, Home.class);
                                                    startActivity(intent);
                                                    Registro.this.finish();
                                                }
                                            }
                                            existeEmail[0] = true;
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    Toast.makeText(Registro.this, "Complete todos los campos", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}