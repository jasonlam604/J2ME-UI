package com.jasonlam604.j2me.ui.components;
 
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

public class ButtonMenu extends Component {

	private Button[] buttons; 
	private String[] buttonLabels;
	private int selectedIndex;
	

	private int pad = 6;
	
	private ButtonMenuListener buttonMenuListener;
	
	public ButtonMenu() {
		this.setModal(true);
	}
	
	public void setButtonMenuListener(ButtonMenuListener buttonMenuListener) {
		this.buttonMenuListener = buttonMenuListener;
	}
	
	public void setButtonLabels(String[] buttonLabels) {
		this.buttonLabels = buttonLabels;
		
		this.setWidth(100);
		this.setHeight(160);
		
		
		
		buttons = new Button[buttonLabels.length];
		for(int i=0; i<buttons.length; i++) {
			buttons[i] = new Button(buttonLabels[i]);
		}
		
		
		this.setX(this.getContainer().getWidth()/2 - buttons[0].getWidth()/2);
		int h = (Component.getDefaultFontButton().getHeight() * buttons.length) + (pad * buttons.length);
		this.setY(this.getContainer().getHeight()/2 - h/2);
		
		this.setDirty(true);
	}
	
	

	public void paint(Graphics g) {
		if(isDirty() && isVisible()) {
			this.setDirty(false);
			
			g.setFont(Component.getDefaultFontButton());			
			
			if(buttonLabels != null && buttonLabels.length > 0) {
				for(int i=0; i<buttonLabels.length; i++) {			
					
					if(selectedIndex == i)
						buttons[i].setFocus(true);
					else
						buttons[i].setFocus(false);
					
					buttons[i].setDirty(true);
					buttons[i].setX(getX());	
					buttons[i].setY(getY() + ( (buttons[i].getHeight()+pad)*i) );
					buttons[i].paint(g);
				}
			}
		}
	}
	
	public void keyPressed(int keyCode, int gameKeyCode) {
		if(gameKeyCode == Canvas.UP || gameKeyCode == Canvas.LEFT) {
			selectedIndex--;
			if(selectedIndex < 0)
				selectedIndex = buttonLabels.length - 1;
			this.setDirty(true);
			this.getContainer().refresh();
		} else if (gameKeyCode == Canvas.DOWN || gameKeyCode == Canvas.RIGHT) {
			selectedIndex++;
			if(selectedIndex >= buttonLabels.length)
				selectedIndex = 0;
			this.setDirty(true);
			this.getContainer().refresh();
		} else if (gameKeyCode == Canvas.FIRE) {			
			buttonMenuListener.buttonMenuPressed(buttonLabels[selectedIndex]);
		}
	}
	
	public boolean pointerPressed(int x, int y) {
		
		for(int i=0; i<buttonLabels.length; i++) {
			if(buttons[i].isPointerPress(x, y)) {
				buttonMenuListener.buttonMenuPressed(buttonLabels[selectedIndex]);	
				return true;
			}
		}		
		return false; 
	}
}
