package co.upb.edu.Tilt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class perfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //Recibe usuario
        Intent thisIntent = getIntent();
        String usuario = thisIntent.getStringExtra(MainActivity.usuarioActual);

        //Botones
        ImageButton btn_perfil_home = (ImageButton) findViewById(R.id.btn_perfil_home);
        Button btn_perfil_seguidores = (Button) findViewById(R.id.btn_perfil_seguidores);
        Button btn_perfil_tiltpoints = (Button) findViewById(R.id.btn_perfil_Tiltpoints);
        Button btn_perfil_seguidos = (Button) findViewById(R.id.btn_perfil_seguidos);

        inicializarBotones(btn_perfil_home, btn_perfil_seguidores, btn_perfil_seguidos, btn_perfil_tiltpoints, usuario);

        //Vista - User
        actualizarUsuario(usuario);


    }
    private  void inicializarBotones (ImageButton home, Button seguidores, Button seguidos, Button tiltpoints, String usuario){
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(perfil.this, Home.class);
                startActivity(intent);
            }
        });

        seguidores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(perfil.this, ListaSeguidores.class);
                intent.putExtra(MainActivity.usuarioActual, usuario);
                startActivity(intent);
            }
        });

        seguidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(perfil.this, ListaSeguidos.class);
                intent.putExtra(MainActivity.usuarioActual, usuario);
                startActivity(intent);
            }
        });
        tiltpoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(perfil.this, Tiltpoints.class);
                startActivity(intent);
            }
        });
    }
    private void actualizarUsuario(String usuario){
        TextView txtUser = (TextView) findViewById(R.id.text_perfil_NombreUsuario);
        txtUser.setText((CharSequence) usuario);
    }
}