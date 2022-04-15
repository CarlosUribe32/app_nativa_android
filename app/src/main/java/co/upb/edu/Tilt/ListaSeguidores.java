package co.upb.edu.Tilt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListaSeguidores extends AppCompatActivity {

    private FirebaseDatabase usuariosBD;
    private DatabaseReference losUsuariosBD;

    private List<String> misSeguidores;
    private TableLayout miTabla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_seguidores);

        //Recibe usuario
        Intent thisIntent = getIntent();
        String usuario = thisIntent.getStringExtra(MainActivity.usuarioActual);

        //Firebase
        usuariosBD = FirebaseDatabase.getInstance();
        losUsuariosBD = usuariosBD.getReference("Usuarios");

        //Inicializacion de la lista en la vista
        inicializarLista(usuario);

        //Botones
        ImageButton imgButton_ListaSeguidores_home = (ImageButton) findViewById(R.id.imgButton_ListaSeguidos_home);
        ImageButton imgButton_ListaSeguidores_perfil = (ImageButton) findViewById(R.id.imgButton_ListaSeguidos_perfil);

        inicializarBotones(imgButton_ListaSeguidores_home,imgButton_ListaSeguidores_perfil, usuario);
    }

    private void inicializarLista(String usuario){
        losUsuariosBD.child(usuario).child("listaSeguidores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    misSeguidores = (List<String>) dataSnapshot.getValue();
                }
                else{
                    misSeguidores = new ArrayList<String>();
                }
                miTabla = (TableLayout) findViewById(R.id.table_listaSeguidores);
                miTabla.removeAllViews();

                for (int i = 0; i < misSeguidores.size(); i++){
                    View registro =  LayoutInflater.from(ListaSeguidores.this).inflate(R.layout.row_table_lista_seguidos_seguidores, null, false);
                    Button campoUsuario = (Button) registro.findViewById(R.id.btn_listaSeguidores_nombreUsuario);
                    campoUsuario.setText(misSeguidores.get(i));
                    campoUsuario.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(ListaSeguidores.this, perfilSecundario.class);
                            intent.putExtra(MainActivity.usuarioActual, usuario);
                            intent.putExtra(MainActivity.usuarioSeleccionado, campoUsuario.getText().toString());
                            startActivity(intent);
                        }
                    });
                    miTabla.addView(registro);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void inicializarBotones(ImageButton home, ImageButton perfil, String usuario){
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaSeguidores.this, Home.class);
                intent.putExtra(MainActivity.usuarioActual, usuario);
                startActivity(intent);
            }
        });
        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaSeguidores.this, perfil.class);
                intent.putExtra(MainActivity.usuarioActual, usuario);
                startActivity(intent);
            }
        });
    }
}