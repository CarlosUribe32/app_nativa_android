package co.upb.edu.Tilt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ListaSeguidores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_seguidores);

        ImageButton imgButton_ListaSeguidores_home = (ImageButton) findViewById(R.id.imgButton_ListaSeguidos_home);
        ImageButton imgButton_ListaSeguidores_perfil = (ImageButton) findViewById(R.id.imgButton_ListaSeguidos_perfil);

        inicializarBotones(imgButton_ListaSeguidores_home,imgButton_ListaSeguidores_perfil);
    }
    private void inicializarBotones(ImageButton home, ImageButton perfil){
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaSeguidores.this, Home.class);
                startActivity(intent);
            }
        });
        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaSeguidores.this, perfil.class);
                startActivity(intent);
            }
        });
    }
}