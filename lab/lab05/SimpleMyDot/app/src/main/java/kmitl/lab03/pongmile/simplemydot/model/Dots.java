package kmitl.lab03.pongmile.simplemydot.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by pongmile on 9/22/2017.
 */

public class Dots {

    private List<Dot> dots = new ArrayList<>();
    private OnDotsChangedListener listener;

    public interface OnDotsChangedListener {
        void onDotsChanged(Dots dots);
    }

    public void setListener(OnDotsChangedListener listener) {
        this.listener = listener;
    }


    public List<Dot> getDots() {
        return dots;

    }

    public void addDot(Dot dot) {
        this.dots.add(dot);
        listener.onDotsChanged(this);

    }

    public void clearDot() {
        dots.clear();
        listener.onDotsChanged(this);
    }

    public int checkdot(int centerX, int centerY) {
        double distance;
        for (int i = dots.size() - 1; i >= 0; i--) {
            distance = Math.sqrt(Math.pow(dots.get(i).getCenterX() - centerX, 2) + Math.pow(dots.get(i).getCenterY() - centerY, 2));
            if (dots.get(i).getRadius() >= distance) {

                return i;

            }
        }
        return -1;
    }

    public void removeDot(int i) {
        dots.remove(i);
        listener.onDotsChanged(this);
    }

    public void undo() {
        if (!dots.isEmpty()) {
            dots.remove(dots.remove(dots.size() - 1));
        }
        listener.onDotsChanged(this);
    }


}
