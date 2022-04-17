package co.upb.edu.Tilt;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PostService {
    String API_ROUTE ="api/games/89?key=4b8cec93234b437eb0f8acfbd08c3485";
    @GET(API_ROUTE)
    Call<Juego> getJuego();

}
