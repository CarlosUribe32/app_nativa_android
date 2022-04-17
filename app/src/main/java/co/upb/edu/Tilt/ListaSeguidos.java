package co.upb.edu.Tilt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListaSeguidos extends AppCompatActivity {

    private FirebaseDatabase usuariosBD;
    private DatabaseReference losUsuariosBD;

    private List<String> misSeguidos;
    private TableLayout miTabla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_seguidos);

        //Recibe usuario
        Intent thisIntent = getIntent();
        String usuario = thisIntent.getStringExtra(MainActivity.usuarioActual);

        //Firebase
        usuariosBD = FirebaseDatabase.getInstance();
        losUsuariosBD = usuariosBD.getReference("Usuarios");

        //Inicializacion de la lista en la vista
        inicializarLista(usuario);

        //Botones
        ImageButton btnListaSeguidosHome = (ImageButton) findViewById(R.id.imgButton_ListaSeguidos_home);
        ImageButton btnListaSeguidosPerfil = (ImageButton) findViewById(R.id.imgButton_ListaSeguidos_perfil);
        ImageButton btnBuscarPerfil = (ImageButton) findViewById(R.id.btnBuscarPerfil);
        inicializarBotones(btnListaSeguidosHome, btnListaSeguidosPerfil, btnBuscarPerfil, usuario);
    }

    private void inicializarLista(String usuario){
        losUsuariosBD.child(usuario).child("listaSeguidos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    misSeguidos = (List<String>) dataSnapshot.getValue();
                }
                else{
                    misSeguidos = new ArrayList<String>();
                }
                miTabla = (TableLayout) findViewById(R.id.table_listaSeguidos);
                miTabla.removeAllViews();

                for (int i = 0; i < misSeguidos.size(); i++){
                    View registro =  LayoutInflater.from(ListaSeguidos.this).inflate(R.layout.row_table_lista_seguidos_seguidores, null, false);
                    Button campoUsuario = (Button) registro.findViewById(R.id.btn_listaSeguidores_nombreUsuario);
                    campoUsuario.setText(misSeguidos.get(i));
                    campoUsuario.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(ListaSeguidos.this, perfilSecundario.class);
                            intent.putExtra(MainActivity.usuarioActual, usuario);
                            intent.putExtra(MainActivity.usuarioSeleccionado, campoUsuario.getText().toString());
                            startActivity(intent);
                            finish();
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

    private void inicializarBotones(ImageButton home, ImageButton perfil, ImageButton buscar, String usuario){
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaSeguidos.this, Home.class);
                intent.putExtra(MainActivity.usuarioActual, usuario);
                startActivity(intent);
            }
        });
        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaSeguidos.this, perfil.class);
                intent.putExtra(MainActivity.usuarioActual, usuario);
                startActivity(intent);
            }
        });
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText usuarioBuscado = (EditText) findViewById(R.id.txtBuscarUsuarioPerfil);
                String sUsuarioBuscado = usuarioBuscado.getText().toString();
                final boolean[] buscoUnaVez = {false};
                if(sUsuarioBuscado.equals(usuario))
                    Toast.makeText(ListaSeguidos.this, "Eres tu bro <3", Toast.LENGTH_LONG).show();
                else if(!TextUtils.isEmpty(sUsuarioBuscado)){
                    losUsuariosBD.child(sUsuarioBuscado).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                Intent intent = new Intent(ListaSeguidos.this, perfilSecundario.class);
                                intent.putExtra(MainActivity.usuarioActual, usuario);
                                intent.putExtra(MainActivity.usuarioSeleccionado, sUsuarioBuscado);
                                startActivity(intent);
                                buscoUnaVez[0] = true;
                                finish();
                            }
                            else if(!buscoUnaVez[0]){
                                Toast.makeText(ListaSeguidos.this, "Este usuario no existe", Toast.LENGTH_LONG).show();
                                buscoUnaVez[0] = true;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                else{
                    Toast.makeText(ListaSeguidos.this, "Complete el campo para buscar", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}