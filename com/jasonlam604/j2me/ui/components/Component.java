package com.jasonlam604.j2me.ui.components;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

abstract public class Component implements ComponentInterface {

	private static Font defaultFont = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN,Font.SIZE_SMALL);
	//private static Font defaultFontButton = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD,Font.SIZE_MEDIUM);
	private static Font defaultFontButton = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD,Font.SIZE_SMALL);
	
	private int x;
	private int y;
	private int width;
	private int height;
	
	// Refers to the assigned index when added to a Component Container
	private int index;	
	private boolean tabStop;
	private boolean dirty;
	private boolean modal;
	private boolean visible;
	private boolean focus;
	private boolean dynamic;


	private ComponentContainer componentContainer;

	public Component() {
		setTabStop(true);
		setDirty(true);
		setModal(false);
		setVisible(true);
		setFocus(false);
		setDynamic(false);
	}
	
	public void setContainer(ComponentContainer componetContainer) {
		this.componentContainer = componetContainer;
	}
	
	public ComponentContainer getContainer() {
		return this.componentContainer;
	}
	
	public static Font getDefaultFont() {
		return Component.defaultFont;
	}
	
	public static Font getDefaultFontButton() {
		return Component.defaultFontButton;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}



	public void setY(int y) {
		this.y = y;
	}



	public int getWidth() {
		return width;
	}



	public void setWidth(int width) {
		this.width = width;
	}



	public int getHeight() {
		return height;
	}



	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public boolean isTabStop() {
		return tabStop;
	}

	public void setTabStop(boolean tabStop) {
		this.tabStop = tabStop;
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}		

	public boolean isModal() {
		return modal;
	}

	public void setModal(boolean modal) {
		this.modal = modal;
	}
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void setFocus(boolean focus) {
		this.focus = focus;
	}

	public boolean isFocus() {
		return focus;
	}
	
	public boolean isDynamic() {
		return dynamic;
	}

	public void setDynamic(boolean dynamic) {
		this.dynamic = dynamic;
	}	

	public boolean isPointerPress(int x, int y) {
		if (x >= this.getX() && x <= (this.getX() + this.getWidth())
				&& y >= this.getY()
				&& y <= (this.getY() + this.getHeight())) {
			return true;
		} else {
			return false;
		}
	}
	
	public void paintFocus(Graphics g) {
		//g.setColor(0x00000000);
		g.setColor(0x00F69401);
		g.drawRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}	
	
	public void keyPressed(int keyCode, int gameKeyCode) {}
	public void keyReleased(int keyCode, int gameKeyCode) {}
	public void keyRepeated(int keyCode, int gameKeyCode) {}	
    public boolean pointerPressed(int x, int y) { return false;}   
    public boolean pointerDragged(int x, int y) { return false;}  
    public boolean pointerReleased(int x, int y) { return false;}	

}

