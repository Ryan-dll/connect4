package edu.msu.weath151.connect4.Cloud.Models;

import androidx.annotation.Nullable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "connect4")
public final class GamesCatalog {
    @Attribute
    private String status = null;

    @ElementList(name = "active_game", inline = true, required = false, type = ActiveGame.class)
    private List<ActiveGame> items;

    @Attribute(name = "msg", required = false)
    private String message = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ActiveGame> getItems() {
        return items;
    }

    public void setItems(List<ActiveGame> items) {
        this.items = items;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GamesCatalog(String status, ArrayList<ActiveGame> items, @Nullable String msg) {
        this.status = status;
        this.items = items;
        this.message = msg;
    }

    public GamesCatalog() {
    }
}
