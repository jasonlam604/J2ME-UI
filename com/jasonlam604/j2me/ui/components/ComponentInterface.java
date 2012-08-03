package com.jasonlam604.j2me.ui.components;
 
import javax.microedition.lcdui.Graphics;

public interface ComponentInterface {
	
	public void paint(Graphics g);
	
	public void keyPressed(int keyCode, int gameKeyCode);
	public void keyRepeated(int keyCode, int gameKeyCode);
	public void keyReleased(int keyCode, int gameKeyCode);
	
    public boolean pointerPressed(int x, int y);    
    public boolean pointerDragged(int x, int y);    
    public boolean pointerReleased(int x, int y);
	
}
