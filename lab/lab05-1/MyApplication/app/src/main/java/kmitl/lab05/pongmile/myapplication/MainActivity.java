package kmitl.lab05.pongmile.myapplication;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import kmitl.lab05.pongmile.myapplication.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentManager fragmentManager =  getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragmantContainer, new MainFragment().newInstance("")).commit();

        Button access = (Button) findViewById(R.id.access);

        access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager =  getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.fragmantContainer, new MainFragment().newInstance("From Activity")).commit();
            }
        });
    }
}
