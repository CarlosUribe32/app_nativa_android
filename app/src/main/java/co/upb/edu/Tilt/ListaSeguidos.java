package co.upb.edu.Tilt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ListaSeguidos extends AppCompatActivity {
    private FirebaseDatabase usuariosBD;
    private DatabaseReference losUsuariosBD;

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

        //Botones
        ImageButton btnListaSeguidosHome = (ImageButton) findViewById(R.id.imgButton_ListaSeguidos_home);
        ImageButton btnListaSeguidosPerfil = (ImageButton) findViewById(R.id.imgButton_ListaSeguidos_perfil);
        ImageButton btnBuscarPerfil = (ImageButton) findViewById(R.id.btnBuscarPerfil);
        inicializarBotones(btnListaSeguidosHome, btnListaSeguidosPerfil, btnBuscarPerfil, usuario);
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
                if(!TextUtils.isEmpty(sUsuarioBuscado)){
                    losUsuariosBD.child(sUsuarioBuscado).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                Intent intent = new Intent(ListaSeguidos.this, perfilSecundario.class);
                                intent.putExtra(MainActivity.usuarioActual, usuario);
                                intent.putExtra(MainActivity.usuarioSeleccionado, sUsuarioBuscado);
                                startActivity(intent);
                                buscoUnaVez[0] = true;
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