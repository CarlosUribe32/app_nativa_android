package co.upb.edu.Tilt;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.widget.*;
import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.*;

public class Game extends AppCompatActivity {
    ImageView imagenJuego;
    TextView nameJuego;
    TextView descripcion;
    DisplayMetrics metrics = new DisplayMetrics();
    int width;
    int height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent thisIntent = getIntent();
        String id = Home.newid;


        imagenJuego = (ImageView) findViewById(R.id.img_game_imagenJuego);
        nameJuego = (TextView) findViewById(R.id.txt_game_nameJuego);

        descripcion = (TextView) findViewById(R.id.txt_game_descripcion);



        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels; // ancho absoluto en pixels
        height = metrics.heightPixels; // alto absoluto en pixels

        getPosts(id);
        descripcion.setMovementMethod(new ScrollingMovementMethod());
    }

    private void getPosts(String id) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rawg.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostService postService = retrofit.create(PostService.class);
        Call<Juego> call = postService.getJuego(id);

        call.enqueue(new Callback <Juego>() {
            @Override
            public void onResponse(Call <Juego> call, Response <Juego> response) {
                if(response.isSuccessful()){
                    Picasso.get()
                            .load(response.body().getBackground_image())
                            .resize((width-100),(height/3))
                            .into(imagenJuego);

                    nameJuego.setText((CharSequence) response.body().getName());
                    descripcion.setText((CharSequence) response.body().getDescription().replaceAll("\\<.*?>",""));

                }else{
                    Toast.makeText(getBaseContext(),"Error en el formato de la respuesta", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<Juego> call, Throwable t) {
                Toast.makeText(getBaseContext(),"Error de la respuesta", Toast.LENGTH_SHORT).show();
            }
        });
    }

}