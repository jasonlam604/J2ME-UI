package com.jasonlam604.j2me.ui.components;
 
import javax.microedition.lcdui.Graphics;

public class Button extends Component {	
	
	private ImageHolder buttonImage;
	private ImageHolder buttonImageFocus;
	
	private String label;
	
	private int pad = 2;
	private int buttonWidth;
	
	public Button(String label) {
		super();
		this.buttonWidth = Component.getDefaultFontButton().stringWidth("MenuMenuMe");
		
		this.buttonImage = new ImageHolder();
		this.buttonImage.setImage("/button.png");		
		this.buttonImage.setImage(ImageHolder.scaleImage2(this.buttonImage.getImage(), buttonWidth + (pad*2),Component.getDefaultFontButton().getHeight() + (pad*2)));
		
		this.buttonImageFocus = new ImageHolder();
		this.buttonImageFocus.setImage("/buttonon.png");
		this.buttonImageFocus.setImage(ImageHolder.scaleImage2(this.buttonImageFocus.getImage(), buttonWidth + (pad*2),Component.getDefaultFontButton().getHeight() + (pad*2)));
		
		this.setHeight(this.buttonImage.getHeight());
		this.setWidth(this.buttonImage.getWidth());
		this.label = label;
		this.setTabStop(true);
	}

	public void paint(Graphics g) {
		if(isDirty() && isVisible()) {			
			this.setDirty(false);
			//System.out.println("Paint Button " + this.label);			
			if(isFocus()) {
				buttonImageFocus.setDirty(true);
				buttonImageFocus.setX(getX());
				buttonImageFocus.setY(getY());
				buttonImageFocus.paint(g);				
			} else {
				buttonImage.setDirty(true);
				buttonImage.setX(getX());
				buttonImage.setY(getY());
				buttonImage.paint(g);
			}			
			g.setFont(Component.getDefaultFontButton());
			g.setColor(0x00FFFFFF);
			g.drawString(label,getX() + pad + buttonImage.getWidth()/2 - g.getFont().stringWidth(label)/2,getY() + buttonImage.getHeight()/2 - g.getFont().getHeight()/2,20);
		}		
	}


	
}
