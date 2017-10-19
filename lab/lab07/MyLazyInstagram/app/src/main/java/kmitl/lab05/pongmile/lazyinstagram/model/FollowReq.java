package kmitl.lab05.pongmile.lazyinstagram.model;

/**
 * Created by pongmile on 10/20/2017.
 */

public class FollowReq {

    private String user;
    private boolean isFollow;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }
}
