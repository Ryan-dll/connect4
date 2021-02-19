package edu.msu.weath151.connect4;

import android.graphics.Canvas;

import org.junit.Test;

import static org.junit.Assert.*;



/*
* Tests the BoardView class
*
*/
public class BoardViewUnitTest{
    @Test
    public void EmptyTest()
    {
        assertEquals(4, 2+2);
    }

    @Test
    public void ClassExistsTest()
    {
        BoardView boardView = new BoardView(null);
    }


}
