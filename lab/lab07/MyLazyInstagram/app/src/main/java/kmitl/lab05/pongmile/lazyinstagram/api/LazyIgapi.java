package kmitl.lab05.pongmile.lazyinstagram.api;

import kmitl.lab05.pongmile.lazyinstagram.model.FollowReq;
import kmitl.lab05.pongmile.lazyinstagram.model.FollowResp;
import kmitl.lab05.pongmile.lazyinstagram.model.UserProfile;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by pongmile on 10/6/17.
 */

public interface LazyIgapi {

    String BASE_URL = "https://us-central1-retrofit-course.cloudfunctions.net";

    @GET("/getProfile")
    Call<UserProfile> getProfile(@Query("user") String usrName);

    @Headers({"Content-Type:application/json"})
    @POST("/follow")
    Call<FollowResp> follow(@Body FollowReq request);

}