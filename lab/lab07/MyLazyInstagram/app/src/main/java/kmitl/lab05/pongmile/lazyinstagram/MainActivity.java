package kmitl.lab05.pongmile.lazyinstagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import kmitl.lab05.pongmile.lazyinstagram.adapter.PostAdapter;
import kmitl.lab05.pongmile.lazyinstagram.api.LazyIgapi;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getUserProfile("cartoon");

        PostAdapter postAdapter = new PostAdapter(this);

        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(postAdapter);
    }

    private void getUserProfile(String usrName) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        Retrofit retrofit = new Retrofit.Builder().client(client).baseUrl(LazyIgapi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        LazyIgapi lazyIgapi = retrofit.create(LazyIgapi.class);

        Call<UserProfile> call = lazyIgapi.getProfile(usrName);

        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if (response.isSuccessful()) {
                    UserProfile userProfile = response.body();
                    TextView textName = (TextView) findViewById(R.id.textUser);
                    textName.setText("@" + userProfile.getUser());

                    ImageView imageProfile = findViewById(R.id.imageProfile);
                    Glide.with(MainActivity.this).load(userProfile.getUrlProfile()).into(imageProfile);

                }

            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {

            }
        });
    }

}
