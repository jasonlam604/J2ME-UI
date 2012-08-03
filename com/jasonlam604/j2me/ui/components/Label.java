package com.jasonlam604.j2me.ui.components;
 
import javax.microedition.lcdui.Graphics;

/**
 * Label Component, fairly straight-forward component. Do auto-refresh, any attribute updates
 * set the component dirty but requires a 'refresh' / 'repaint' call by the component container.
 * 
 * @author c_JLam
 *
 */
public class Label extends Component {
	
	private String text;
	
	private boolean transparent;	
	private int bgColor;
	//private int fgColor = 0x00FF0000;		
	private int fgColor = 0x00cccccc;

	public Label(String text, int x, int y) {
		this.text = text;
		if (this.text == null)
			this.text = "";
		this.setX(x);
		this.setY(y);
		this.setWidth(Component.getDefaultFont().stringWidth(this.text));
		this.setHeight(Component.getDefaultFont().getHeight());
		this.setTabStop(false);
		this.setDirty(true);
		this.transparent = true;		
	}

	public void paint(Graphics g) {		
		if(isDirty() && isVisible()) {			
			g.setFont(Component.getDefaultFont());			
			this.setDirty(false);
			
			System.out.println("Paint label: " + text);
			
			if(!transparent) {			
				g.setColor(bgColor);
				g.fillRect(getX(), getY(), getWidth(), getHeight());
			}
			g.setFont(Component.getDefaultFont());
			g.setColor(this.fgColor);
			g.drawString(this.text, getX(), getY(), 20);
			
			if(isFocus())
				this.paintFocus(g);
		}
	}	
	
	public void setText(String text) {
		if(text != null && !text.equals(this.text)) {
			this.setDirty(true);
			this.text = text;
			this.setWidth(Component.getDefaultFont().stringWidth(this.text));			
		}
	}	
	
	public void setTransparent(boolean transparent) {
		if(transparent != this.transparent) {
			this.setDirty(true);		
			this.transparent = transparent;			
		}
	}
	
	public void setColorBG(int bgColor) {
		if(bgColor != this.bgColor) {
			this.setDirty(true);
			this.bgColor = bgColor;			
		}
	}
	
	public void setColorFG(int fgColor) {
		if(fgColor != this.fgColor) {
			this.setDirty(true);
			this.fgColor = fgColor;			
		}
	}

}
