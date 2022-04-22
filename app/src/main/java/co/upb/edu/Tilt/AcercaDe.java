package co.upb.edu.Tilt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AcercaDe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);

        //Botones
        Button btnVolver = (Button) findViewById(R.id.btnVolver);
        inicializarBotones(btnVolver);
    }

    private void inicializarBotones (Button volver){
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AcercaDe.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}