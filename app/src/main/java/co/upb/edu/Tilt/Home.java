package co.upb.edu.Tilt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends AppCompatActivity {
    int id = 0;
    public static String newid = "0";
    Juegos juegos;
    LinearLayout layoutJuegos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent thisIntent = getIntent();
        String usuario = thisIntent.getStringExtra(MainActivity.usuarioActual);

        //Botones
        ImageButton btnPerfilHome = (ImageButton) findViewById(R.id.btnPerfilHome);
        inicializarBotones(btnPerfilHome, usuario);
        //ImagenButton

        //Layout
        layoutJuegos = (LinearLayout) findViewById(R.id.LinearLayout_home);
        getPosts();


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

    private void getPosts(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rawg.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostService postService = retrofit.create(PostService.class);
        Call<Juegos> call = postService.getJuegos();

        call.enqueue(new Callback <Juegos>() {
            @Override
            public void onResponse(Call <Juegos> call, Response<Juegos> response) {
                if(response.isSuccessful()){
                    juegos = response.body();
                    CrearImageButton();
                    ColocarImagenesDeJuegos();
                }else{
                    Toast.makeText(getBaseContext(),"Error en el formato de la respuesta", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<Juegos> call, Throwable t) {
                Toast.makeText(getBaseContext(),"Error de la respuesta", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void CrearImageButton(){

        String  aux, NombreAux;
        for(int i =0;i<juegos.getResults().size();i++){
            ImageButton imgBtn = new ImageButton(getApplicationContext());
            layoutJuegos.addView(imgBtn);
            imgBtn.setId(i);
            NombreAux = String.valueOf(i);
            ImageButton imgViews = (ImageButton) findViewById(i);
            imgViews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    id = juegos.getResults().get(view.getId()).getId();
                    newid = String.valueOf(id);

                    Intent intent = new Intent(Home.this, Game.class);
                    startActivity(intent);
                }
            });
        }
    }
    private void ColocarImagenesDeJuegos(){
        ImageButton imgBtn;
        for(int i=0; i<juegos.getResults().size();i++){
            imgBtn = (ImageButton) findViewById(i);
            Picasso.get()
                    .load(juegos.getResults().get(i).getBackground_image())
                    .resize(590,650)
                    .into(imgBtn);
        }
    }

}