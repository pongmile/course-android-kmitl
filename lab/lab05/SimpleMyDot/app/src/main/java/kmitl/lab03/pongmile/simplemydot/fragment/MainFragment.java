package kmitl.lab03.pongmile.simplemydot.fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.ByteArrayOutputStream;
import java.util.Random;

import kmitl.lab03.pongmile.simplemydot.R;
import kmitl.lab03.pongmile.simplemydot.model.Dot;
import kmitl.lab03.pongmile.simplemydot.model.Dots;
import kmitl.lab03.pongmile.simplemydot.view.Dotview;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements Dots.OnDotsChangedListener, Dotview.OnDotViewPressListener, View.OnClickListener {


    public interface OnDotSelectListener {
        void onDotSelect(Dot dot);
    }

    private Dotview dotView;
    private Dots dotlist;
    private Dot dot;
    private OnDotSelectListener selectListener;


    public void setSelectListener(OnDotSelectListener selectListener) {
        this.selectListener = selectListener;
    }

    public MainFragment() {

    }

    public static MainFragment newInstance() {

        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dotlist = new Dots();
        dotlist.setListener(this);
        selectListener = (OnDotSelectListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initSetup(rootView);
        dotView.setDots(dotlist);
        dotView.invalidate();
        return rootView;

    }

    public void createDot(int centerX, int centerY) {
        dot = new Dot(centerX, centerY, 60, randomColor(), this);
        dotlist.addDot(dot);
    }

    private int randomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));
    }


    public void onDotViewPressed(int x, int y) {
        int result = dotlist.checkdot(x, y);
        if (result == -1) {
            createDot(x, y);
        } else {
            dotlist.removeDot(result);
        }
    }

    @Override
    public void onDotViewLongPressed(int x, int y) {
        int result = dotlist.checkdot(x, y);
        if (result == -1) {
            createDot(x, y);
        } else {
            selectListener.onDotSelect(dotlist.getDots().get(result));
        }
    }

    private boolean requestExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    100);
            return false;
        } else {
            return true;
        }
    }

    private Bitmap captureScreen() {
        View root = this.dotView.getRootView();
        Bitmap screenShot = Bitmap.createBitmap(root.getWidth(), root.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(screenShot);
        root.draw(canvas);
        return screenShot;
    }


    public Uri imageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void shareScreen(Uri uri) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/*");
        startActivity(Intent.createChooser(intent, "Share screenshot"));
    }

    private void initSetup(View rootView) {
        dotView = rootView.findViewById(R.id.dotView);
        Button ButtonRandom = rootView.findViewById(R.id.randomDot);
        Button ButtonClear = rootView.findViewById(R.id.clearDot);
        Button ButtonUndo = rootView.findViewById(R.id.Undo);
        Button ButtonShare = rootView.findViewById(R.id.share);

        dotView.setOnDotViewPressListener(this);
        ButtonRandom.setOnClickListener(this);
        ButtonClear.setOnClickListener(this);
        ButtonUndo.setOnClickListener(this);
        ButtonShare.setOnClickListener(this);
    }

    @Override
    public void onDotsChanged(Dots dots) {
        dotView.setDots(dots);
        dotView.invalidate();
    }

    public void onRandomDot() {
        Random random = new Random();
        int centerX = random.nextInt(dotView.getWidth());
        int centerY = random.nextInt(dotView.getHeight());
        dot = new Dot(centerX, centerY, 30, randomColor(), this);
        dotlist.addDot(dot);
    }

    public void onClearDot() {
        dotlist.clearDot();
        dotView.invalidate();
    }

    public void onUndo() {
        dotlist.undo();
        dotView.invalidate();
    }

    public void onShare() {
        if (requestExternalStoragePermission()) {
            Bitmap screenshot = captureScreen();
            Uri uri = imageUri(getContext(), screenshot);
            shareScreen(uri);
        }
    }

    @Override
    public void onClick(View view) {
        if (R.id.randomDot == view.getId()) {
            onRandomDot();
        }
        if (R.id.clearDot == view.getId()) {
            onClearDot();
        }
        if (R.id.Undo == view.getId()) {
            onUndo();
        }
        if (R.id.share == view.getId()) {
            onShare();
        }
    }


}
