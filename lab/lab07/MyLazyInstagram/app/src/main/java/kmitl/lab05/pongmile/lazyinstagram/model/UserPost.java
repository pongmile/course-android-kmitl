package kmitl.lab05.pongmile.lazyinstagram.model;

/**
 * Created by pongmile on 10/20/2017.
 */

public class UserPost {
    private String url;
    private int like;
    private int comment;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }
}
