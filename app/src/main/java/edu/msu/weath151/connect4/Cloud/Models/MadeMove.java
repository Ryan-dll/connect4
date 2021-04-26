package edu.msu.weath151.connect4.Cloud.Models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "connect4")
public class MadeMove extends Result{
    @Attribute(required = false)
    private String game;

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    MadeMove(){}

    MadeMove(String status, String message, String game)
    {
        super(status, message);
        this.game = game;
    }
}
