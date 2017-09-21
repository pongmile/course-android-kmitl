package kmitl.lab03.pongmile.simplemydot.model;

import android.os.Parcel;
import android.os.Parcelable;

import kmitl.lab03.pongmile.simplemydot.fragment.MainFragment;

public class Dot implements Parcelable {

    private int centerX;
    private int centerY;
    private int radius;
    private int color;

    public Dot(int centerX, int centerY, int radius, int color, MainFragment mainFragment) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.color = color;
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    protected Dot(Parcel in) {
        centerX = in.readInt();
        centerY = in.readInt();
        radius = in.readInt();
        color = in.readInt();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(centerX);
        parcel.writeInt(centerY);
        parcel.writeInt(radius);
        parcel.writeInt(color);
    }

    public static final Creator<Dot> CREATOR = new Creator<Dot>() {
        @Override
        public Dot createFromParcel(Parcel in) {
            return new Dot(in);
        }

        @Override
        public Dot[] newArray(int size) {
            return new Dot[size];
        }
    };


}
