package kmitl.lab05.pongmile.lazyinstagram.api;

import kmitl.lab05.pongmile.lazyinstagram.UserProfile;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by pongmile on 10/6/17.
 */

public interface LazyIgapi {

    String BASE_URL = "https://us-central1-retrofit-course.cloudfunctions.net";

    @GET("/getProfile")

    Call<UserProfile> getProfile(@Query("user") String usrName);

}