package kmitl.lab03.pongmile.simplemydot.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import kmitl.lab03.pongmile.simplemydot.R;
import kmitl.lab03.pongmile.simplemydot.model.Dot;

/**
 * Created by pongmile on 9/22/2017.
 */

public class Editdot extends Fragment implements View.OnClickListener{


    public interface EditListener {
        void editlistener();
    }

    private EditListener listener;
    private Dot dot;
    private View rootView;
    private SeekBar shadebar;
    private SeekBar colorred;
    private SeekBar colorgreen;
    private SeekBar colorblue;
    private EditText radius;
    private EditText centerX;
    private EditText centerY;
    private TextView preview;

    public Editdot() {
        // Required empty public constructor
    }

    public void setListener(EditListener listener) {
        this.listener = listener;
    }

    public static Editdot newInstance(Dot dot) {
        Bundle args = new Bundle();
        Editdot fragment = new Editdot();
        args.putParcelable("dot", dot);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_edit_dot, container, false);
        listener = (EditListener)getActivity();

        Button okay = rootView.findViewById(R.id.okay);
        okay.setOnClickListener(this);

        shadebar = rootView.findViewById(R.id.shadebar);
        colorred = rootView.findViewById(R.id.colorred);
        colorgreen = rootView.findViewById(R.id.colorgreen);
        colorblue = rootView.findViewById(R.id.colorblue);
        radius = rootView.findViewById(R.id.radius);
        centerX = rootView.findViewById(R.id.centerX);
        centerY = rootView.findViewById(R.id.centerY);
        preview = rootView.findViewById(R.id.preview);

        dot = getArguments().getParcelable("dot");
        shadebar.setProgress(Color.alpha(dot.getColor()));
        colorred.setProgress(Color.red(dot.getColor()));
        colorgreen.setProgress(Color.green(dot.getColor()));
        colorblue.setProgress(Color.blue(dot.getColor()));

        radius.setText(Integer.toString(dot.getRadius()));
        centerX.setText(Integer.toString(dot.getCenterX()));
        centerY.setText(Integer.toString(dot.getCenterY()));

        preview.setBackgroundColor(dot.getColor());

        seekbarListener(rootView);
        return rootView;
    }

    public void seekbarListener(View view){
        shadebar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                preview.setBackgroundColor(Color.argb(shadebar.getProgress(), colorred.getProgress(), colorgreen.getProgress(), colorblue.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        colorred.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                preview.setBackgroundColor(Color.argb(shadebar.getProgress(), colorred.getProgress(), colorgreen.getProgress(), colorblue.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        colorgreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                preview.setBackgroundColor(Color.argb(shadebar.getProgress(), colorred.getProgress(), colorgreen.getProgress(), colorblue.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        colorblue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                preview.setBackgroundColor(Color.argb(shadebar.getProgress(), colorred.getProgress(), colorgreen.getProgress(), colorblue.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    public void onOkay() {
        dot.setColor(Color.argb(shadebar.getProgress(), colorred.getProgress(), colorgreen.getProgress(), colorblue.getProgress()));
        dot.setRadius(Integer.parseInt(radius.getText().toString()));
        dot.setCenterX(Integer.parseInt(centerX.getText().toString()));
        dot.setCenterY(Integer.parseInt(centerY.getText().toString()));
        listener.editlistener();
    }

    @Override
    public void onClick(View view) {
        if (R.id.okay == view.getId()) {
            onOkay();
        }
    }

}
