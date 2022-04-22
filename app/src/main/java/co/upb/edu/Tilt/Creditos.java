package co.upb.edu.Tilt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Creditos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditos);

        Button btnCreditosVolver = (Button) findViewById(R.id.btnCreditosVolver);
        inicializarBotonesCreditos(btnCreditosVolver);
    }

    private void inicializarBotonesCreditos (Button volver){
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Creditos.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}