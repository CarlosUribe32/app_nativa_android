package co.upb.edu.Tilt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Tiltpoints extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiltpoints);

        ImageButton imgButton_tiltpoints_home = (ImageButton) findViewById(R.id.imgButton_tiltpoints_home);
        ImageButton imgButton_tiltpoints_perfil = (ImageButton) findViewById(R.id.imgButton_tiltpoints_perfil);

        inicializarBotones(imgButton_tiltpoints_home,imgButton_tiltpoints_perfil);
    }

    private void inicializarBotones(ImageButton home, ImageButton perfil){
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Tiltpoints.this, Home.class);
                startActivity(intent);
            }
        });
        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Tiltpoints.this, perfil.class);
                startActivity(intent);
            }
        });
    }
}