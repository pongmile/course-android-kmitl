package kmitl.lab05.pongmile.lazyinstagram.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import kmitl.lab05.pongmile.lazyinstagram.R;
import kmitl.lab05.pongmile.lazyinstagram.model.UserPost;


class Holder extends RecyclerView.ViewHolder {

    public ImageView image;
    public TextView textLike;
    public TextView textComment;

    public Holder(View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.image);
        textLike = itemView.findViewById(R.id.textLike);
        textComment = itemView.findViewById(R.id.textComment);
    }
}

public class PostAdapter extends RecyclerView.Adapter<Holder> {

    public Context context;
    private List<UserPost> posts;

    public PostAdapter(Context context, List<UserPost> posts) {
        this.context = context;
        this.posts = posts;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View itemView = inflater.inflate(R.layout.post_item, null, false);
        Holder holder = new Holder(itemView);
        return holder;
    }


    @Override
    public void onBindViewHolder(Holder holder, int position) {
        ImageView image = holder.image;
        Glide.with(context).load(posts.get(position).getUrl()).into(image);

        TextView textViewLike = holder.textLike;
        textViewLike.setText(String.valueOf(posts.get(position).getLike()));

        TextView textViewComment = holder.textComment;
        textViewComment.setText(String.valueOf(posts.get(position).getComment()));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
