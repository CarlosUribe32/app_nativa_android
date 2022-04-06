package co.upb.edu.Tilt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent thisIntent = getIntent();
        String usuario = thisIntent.getStringExtra(MainActivity.usuarioActual);
    }
}