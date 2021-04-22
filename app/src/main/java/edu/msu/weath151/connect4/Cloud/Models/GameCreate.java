package edu.msu.weath151.connect4.Cloud.Models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "connect4")
public class GameCreate extends Result{
    @Attribute(name = "game", required = false)
    private int game;

    public int getGame() {
        return game;
    }

    public void setGame(int game) {
        this.game = game;
    }

    @Attribute(name = "user", required = false)
    private int user;

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public GameCreate(String message, String status, int game, int user)
    {
        super(message, status);
        this.game = game;
        this.user = user;
    }

    public GameCreate(){}
}
