package co.upb.edu.Tilt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class perfilSecundario extends AppCompatActivity {

    //Referencia a la BD
    private FirebaseDatabase usuariosBD;
    private DatabaseReference losUsuariosBD;

    private List<String> listaSeguidos;
    private List<String> listaSeguidores;
    private int seguidores;
    private TextView numeroSeguidores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_secundario);

        //Firebase
        usuariosBD = FirebaseDatabase.getInstance();
        losUsuariosBD = usuariosBD.getReference("Usuarios");

        //Recibe usuarios
        Intent thisIntent = getIntent();
        String usuario = thisIntent.getStringExtra(MainActivity.usuarioActual);
        String usuarioBuscado = thisIntent.getStringExtra(MainActivity.usuarioSeleccionado);

        //Botones
        ImageButton btnPerfilSecundarioHome = (ImageButton) findViewById(R.id.imgButton_perfilSecundario_home);
        ImageButton btnPerfilSecundarioPerfil = (ImageButton) findViewById(R.id.imgButton_perfilSecundario_perfil);
        Button btnSeguirNoseguir = (Button) findViewById(R.id.btn_perfilSecundario_seguir_dejar);


        numeroSeguidores = (TextView) findViewById(R.id.text_perfilSecundario_numeroSeguidores);
        //Carga del perfil
        cargandoPerfil(usuarioBuscado, usuario, btnSeguirNoseguir);

        inicializarBotones(btnPerfilSecundarioHome, btnPerfilSecundarioPerfil,btnSeguirNoseguir, usuario, usuarioBuscado);
    }

    private void cargandoPerfil(String usuarioSecundario, String usuario, Button seguirNoseguir){
        final boolean[] buscoUnaVez = {false};
        final boolean[] buscoUnaVez2 = {false};

        TextView nombrePerfil = (TextView) findViewById(R.id.text_perfilSecundario_NombreUsuario);
        TextView infoPerfil = (TextView) findViewById(R.id.text_perfilSecundario_infoPerfil);

        nombrePerfil.setText(usuarioSecundario);
        losUsuariosBD.child(usuarioSecundario).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!buscoUnaVez[0]){
                    infoPerfil.setText(dataSnapshot.child("correo").getValue().toString());
                    if(dataSnapshot.child("listaSeguidores").exists()){
                        listaSeguidores = (List<String>) dataSnapshot.child("listaSeguidores").getValue();
                        seguidores = listaSeguidores.size();

                    }
                    else{
                        listaSeguidores = new ArrayList<String>();
                        seguidores = 0;
                    }
                    numeroSeguidores.setText(String.valueOf(seguidores));
                }
                buscoUnaVez[0] = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        losUsuariosBD.child(usuario).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!buscoUnaVez2[0]){
                    if(!dataSnapshot.child("listaSeguidos").exists()){
                        listaSeguidos = new ArrayList<String>();
                        seguirNoseguir.setText("Seguir");
                    }
                    else{
                        listaSeguidos = (List<String>) dataSnapshot.child("listaSeguidos").getValue();
                        if(listaSeguidos.contains(usuarioSecundario)){
                            seguirNoseguir.setText("Dejar de seguir");
                        }
                        else{
                            seguirNoseguir.setText("Seguir");
                        }
                    }
                }
                buscoUnaVez2[0] = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inicializarBotones(ImageButton home, ImageButton perfil, Button seguirNoseguir, String usuario, String usuarioBuscado){
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(perfilSecundario.this, Home.class);
                intent.putExtra(MainActivity.usuarioActual, usuario);
                startActivity(intent);
            }
        });
        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(perfilSecundario.this, perfil.class);
                intent.putExtra(MainActivity.usuarioActual, usuario);
                startActivity(intent);
            }
        });

        seguirNoseguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(seguirNoseguir.getText().toString().equals("Seguir")){
                    listaSeguidores.add(usuario);
                    listaSeguidos.add(usuarioBuscado);
                    seguidores++;
                    seguirNoseguir.setText("Dejar de seguir");
                }
                else{
                    listaSeguidores.remove(usuario);
                    listaSeguidos.remove(usuarioBuscado);
                    seguidores--;
                    seguirNoseguir.setText("Seguir");
                }
                losUsuariosBD.child(usuarioBuscado).child("listaSeguidores").setValue(listaSeguidores);
                losUsuariosBD.child(usuario).child("listaSeguidos").setValue(listaSeguidos);
                numeroSeguidores.setText(String.valueOf(seguidores));
            }
        });
    }
}