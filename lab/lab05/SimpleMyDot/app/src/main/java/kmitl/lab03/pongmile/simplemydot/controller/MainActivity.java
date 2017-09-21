package kmitl.lab03.pongmile.simplemydot.controller;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import kmitl.lab03.pongmile.simplemydot.R;
import kmitl.lab03.pongmile.simplemydot.fragment.Editdot;
import kmitl.lab03.pongmile.simplemydot.fragment.MainFragment;
import kmitl.lab03.pongmile.simplemydot.model.Dot;

public class MainActivity extends AppCompatActivity implements MainFragment.OnDotSelectListener, Editdot.EditListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            initialFragment();
        }
    }

    private void initialFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragmantContainer, new MainFragment().newInstance(), "DotViewFragmentTag").commit();
    }

    private void viewFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmantContainer, fragment).addToBackStack(null).commit();
    }

    @Override
    public void onDotSelect(Dot dot) {
        viewFragment(Editdot.newInstance(dot));
    }

    @Override
    public void editlistener() {
        getSupportFragmentManager().popBackStack();
    }


}