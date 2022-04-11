package co.upb.edu.Tilt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ListaSeguidos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_seguidos);

        //Recibe usuario
        Intent thisIntent = getIntent();
        String usuario = thisIntent.getStringExtra(MainActivity.usuarioActual);

        //Botones
        ImageButton btnListaSeguidosHome = (ImageButton) findViewById(R.id.btn_listaSeguidos_home);
        ImageButton btnListaSeguidosPerfil = (ImageButton) findViewById(R.id.btn_listaSeguidos_perfil);
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
                if(!TextUtils.isEmpty(sUsuarioBuscado)){
                    Intent intent = new Intent(ListaSeguidos.this, perfilSecundario.class);
                    intent.putExtra(MainActivity.usuarioActual, usuario);
                    intent.putExtra(MainActivity.usuarioSeleccionado, sUsuarioBuscado);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(ListaSeguidos.this, "Complete el campo para buscar", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}