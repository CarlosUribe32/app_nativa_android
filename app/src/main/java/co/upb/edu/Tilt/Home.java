package co.upb.edu.Tilt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent thisIntent = getIntent();
        String usuario = thisIntent.getStringExtra(MainActivity.usuarioActual);

        //Botones
        ImageButton btnPerfilHome = (ImageButton) findViewById(R.id.btnPerfilHome);
        inicializarBotones(btnPerfilHome, usuario);
    }

    private void inicializarBotones (ImageButton perfil, String usuario){
        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, perfil.class);
                intent.putExtra(MainActivity.usuarioActual, usuario);
                startActivity(intent);
            }
        });
    }
}