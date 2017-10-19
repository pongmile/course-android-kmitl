package kmitl.lab05.pongmile.lazyinstagram;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import kmitl.lab05.pongmile.lazyinstagram.adapter.PostAdapter;
import kmitl.lab05.pongmile.lazyinstagram.api.LazyIgapi;
import kmitl.lab05.pongmile.lazyinstagram.model.FollowReq;
import kmitl.lab05.pongmile.lazyinstagram.model.FollowResp;
import kmitl.lab05.pongmile.lazyinstagram.model.UserProfile;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    /*Spinner userSelect;
    RecyclerView list;
    String userName;
    private UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getUserProfile("cartoon");

        PostAdapter postAdapter = new PostAdapter(this, userProfile.getPosts());

        RecyclerView recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(postAdapter);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = getIntent().getStringExtra("name");

        if (userName == null){
            userName = "cartoon";
        }

        getUserProfile(userName);

        userSelect = findViewById(R.id.userSelect);

        ImageButton imageSingle = findViewById(R.id.imageSingle);
        ImageButton imageMulti = findViewById(R.id.imageMulti);

        list = findViewById(R.id.list);
        Toast.makeText(this, userName, Toast.LENGTH_SHORT).show();
        userSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != getUserNameNumber(userName)){
                    String item = adapterView.getItemAtPosition(i).toString();
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    intent.putExtra("name", item);
                    startActivity(intent);
                    finish();
                    Toast.makeText(MainActivity.this, adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String[] names = {"android", "cartoon", "nature"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSelect.setAdapter(adapter);
        userSelect.setSelection(getUserNameNumber(userName));

        imageSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
            }
        });

        imageMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
            }
        });

        Button buttonFollow = findViewById(R.id.buttonFollow);
        buttonFollow.setOnClickListener(this);

    }
    private void onClickFollow(){

        FollowReq request = new FollowReq();
        request.setUser(userName);
        request.setFollow(!userProfile.isFollow());

        OkHttpClient client = new OkHttpClient.Builder().build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LazyIgapi.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LazyIgapi lazyInstragramApi = retrofit.create(LazyIgapi.class);
        Call<FollowResp> call = lazyInstragramApi.follow(request);
        call.enqueue(new Callback<FollowResp>() {
            @Override
            public void onResponse(Call<FollowResp> call, Response<FollowResp> response) {
                if (response.isSuccessful()) {
                    userProfile.setFollow(!userProfile.isFollow());
                    updateFollowButton(userProfile);
                }
            }

            @Override
            public void onFailure(Call<FollowResp> call, Throwable t) {

            }

        });

    }

    private void updateFollowButton(UserProfile userProfile) {
        Button buttonFollow = findViewById(R.id.buttonFollow);
        buttonFollow.setText(userProfile.isFollow() ? "Followed" : "Follow");
        buttonFollow.setBackgroundColor(userProfile.isFollow() ? Color.parseColor("#F2594B") : Color.parseColor("#375FBF"));
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
                    userProfile = response.body();

                    ImageView imageProfile = findViewById(R.id.imageProfile);
                    Glide.with(MainActivity.this).load(userProfile.getUrlProfile())
                            .into(imageProfile);

                    TextView textPost = findViewById(R.id.valuepost);
                    textPost.setText(String.valueOf(userProfile.getPost()));

                    TextView textFollowing = findViewById(R.id.valuepost);
                    textFollowing.setText(String.valueOf(userProfile.getFollowing()));

                    TextView textFollower = findViewById(R.id.valuefollower);
                    textFollower.setText(String.valueOf(userProfile.getFollower()));

                    TextView textBio = findViewById(R.id.textBio);
                    textBio.setText(String.valueOf(userProfile.getBio()));

                    PostAdapter postAdapter = new PostAdapter(MainActivity.this, userProfile.getPosts());
                    list.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
                    list.setAdapter(postAdapter);

                    updateFollowButton(userProfile);

                }

            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {

            }
        });
    }

    private int getUserNameNumber(String userName){
        switch(userName){
            case "android":
                return 0;
            case "cartoon":
                return 1;
            default:
                return 2;
        }

    }

    @Override
    public void onClick(View view) {
        onClickFollow();
    }
    */

    RecyclerView recyclerView;
    Spinner spinnerUsrName;
    String userName;
    private UserProfile userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = getIntent().getStringExtra("name");

        if (userName == null){
            userName = "cartoon";
        }

        getUserProfile(userName);

        spinnerUsrName = findViewById(R.id.userSelect);

        ImageButton imageButtonSingle = findViewById(R.id.imageSingle);
        ImageButton imageButtonTriple = findViewById(R.id.imageMulti);
        recyclerView = findViewById(R.id.list);
        Toast.makeText(this, userName, Toast.LENGTH_SHORT).show();
        spinnerUsrName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != getUserNameNumber(userName)){
                    String item = adapterView.getItemAtPosition(i).toString();
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    intent.putExtra("name", item);
                    startActivity(intent);
                    finish();
                    Toast.makeText(MainActivity.this, adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String[] names = {"android", "cartoon", "nature"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUsrName.setAdapter(adapter);
        spinnerUsrName.setSelection(getUserNameNumber(userName));

        imageButtonSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
            }
        });

        imageButtonTriple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
            }
        });

        Button buttonFollow = findViewById(R.id.buttonFollow);
        buttonFollow.setOnClickListener(this);

    }

    private void updateFollowButton(UserProfile userProfile) {
        Button buttonFollow = findViewById(R.id.buttonFollow);
        buttonFollow.setText(userProfile.isFollow() ? "Followed" : "Follow");
        buttonFollow.setBackgroundColor(userProfile.isFollow() ? Color.parseColor("#cecece") : Color.parseColor("#0059ff"));
    }

    private void displayError(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    private AlertDialog createLoadingDialog() {
        ProgressBar bar = new ProgressBar(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(bar)
                .create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        return dialog;
    }

    private void onClickFollow(){
        final AlertDialog loadingDialog = createLoadingDialog();
        loadingDialog.show();

        FollowReq request = new FollowReq();
        request.setUser(userName);
        request.setFollow(!userProfile.isFollow());

        OkHttpClient client = new OkHttpClient.Builder().build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LazyIgapi.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LazyIgapi lazyInstragramApi = retrofit.create(LazyIgapi.class);
        Call<FollowResp> call = lazyInstragramApi.follow(request);
        call.enqueue(new Callback<FollowResp>() {
            @Override
            public void onResponse(Call<FollowResp> call, Response<FollowResp> response) {
                loadingDialog.dismiss();
                if (response.isSuccessful()) {
                    userProfile.setFollow(!userProfile.isFollow());
                    updateFollowButton(userProfile);
                } else {
                    displayError("Error!", "There is some thing wrong!");
                }
            }

            @Override
            public void onFailure(Call<FollowResp> call, Throwable t) {
                loadingDialog.dismiss();
                displayError("Error!", "There is some thing wrong!");
            }
        });

    }

    private void getUserProfile(String usrName){
        OkHttpClient client = new OkHttpClient.Builder().build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LazyIgapi.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LazyIgapi lazyInstragramApi = retrofit.create(LazyIgapi.class);

        Call<UserProfile> call = lazyInstragramApi.getProfile(usrName);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {

                if (response.isSuccessful()) {
                    userProfile = response.body();

                    ImageView imageProfile = findViewById(R.id.imageProfile);
                    Glide.with(MainActivity.this).load(userProfile.getUrlProfile())
                            .into(imageProfile);

                    TextView textPost = findViewById(R.id.valuepost);
                    textPost.setText(String.valueOf(userProfile.getPost()));

                    TextView textFollowing = findViewById(R.id.valuefollowing);
                    textFollowing.setText(String.valueOf(userProfile.getFollowing()));

                    TextView textFollower = findViewById(R.id.valuefollower);
                    textFollower.setText(String.valueOf(userProfile.getFollower()));

                    TextView textBio = findViewById(R.id.textBio);
                    textBio.setText(String.valueOf(userProfile.getBio()));

                    PostAdapter postAdapter = new PostAdapter(MainActivity.this, userProfile.getPosts());
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
                    recyclerView.setAdapter(postAdapter);

                    updateFollowButton(userProfile);

                }
                else {
                    displayError("Error!", "There is some thing wrong!");
                }

            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {

            }
        });

    }

    private int getUserNameNumber(String userName){
        switch(userName){
            case "android":
                return 0;
            case "cartoon":
                return 1;
            default:
                return 2;
        }

    }

    @Override
    public void onClick(View view) {
        onClickFollow();
    }

}
