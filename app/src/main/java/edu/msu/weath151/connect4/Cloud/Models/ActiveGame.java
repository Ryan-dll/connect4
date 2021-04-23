package edu.msu.weath151.connect4.Cloud.Models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "active_game")
public final class ActiveGame {

    @Attribute(name = "user")
    private String user;


    @Attribute(name = "id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ActiveGame(String user, String id)
    {
        this.user = user;
        this.id = id;
    }

    public ActiveGame() {}

}
