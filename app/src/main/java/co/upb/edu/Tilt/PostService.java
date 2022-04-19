package co.upb.edu.Tilt;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PostService {
    String API_ROUTE_Juego ="api/games/{id}?key=4b8cec93234b437eb0f8acfbd08c3485";
    @GET(API_ROUTE_Juego)
    Call<Juego> getJuego(/*@Path("id") int id*/);

    String API_ROUTE_JUEGOS = "api/games?key=4b8cec93234b437eb0f8acfbd08c3485";
    @GET(API_ROUTE_JUEGOS)
    Call<Juegos> getJuegos();
}
