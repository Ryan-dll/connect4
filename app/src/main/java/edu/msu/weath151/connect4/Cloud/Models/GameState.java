package edu.msu.weath151.connect4.Cloud.Models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "connect4")
public class GameState extends Result{

    @Attribute(name = "game")
    private String game;

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public GameState(String message, String status, String game)
    {
        super(message, status);
        this.game = game;
    }

    GameState(){
    }

}
