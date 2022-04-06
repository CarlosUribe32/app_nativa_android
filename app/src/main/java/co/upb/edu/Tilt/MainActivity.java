package co.upb.edu.Tilt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Botones
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        Button btnAcerca = (Button) findViewById(R.id.btnAcerca);
        Button btnCreditos = (Button) findViewById(R.id.btnCreditos);

        inicializarBotones(btnLogin, btnAcerca, btnCreditos);
    }

    private void inicializarBotones (Button login, Button acerca, Button creditos){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });
        acerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AcercaDe.class);
                startActivity(intent);
            }
        });
        creditos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Creditos.class);
                startActivity(intent);
            }
        });


    }

}