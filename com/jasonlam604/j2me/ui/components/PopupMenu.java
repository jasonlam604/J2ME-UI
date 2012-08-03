package com.jasonlam604.j2me.ui.components;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

public class PopupMenu extends Component {
	private ImageHolder menuBg;	
	private String[] menuItems;
	private int menuIdx;	
	private int menuInset = 5;
	private int pad = 2;
	private PopupMenuListener popupMenuListener;
	
	public PopupMenu() {
		menuBg = new ImageHolder();
		menuBg.setImage("/popup.png");
		init();
	}
	
	public void setPopupMenuListener(PopupMenuListener popupMenuListener) {
		this.popupMenuListener = popupMenuListener;
	}
	
	private void init() {
		this.setHeight(menuBg.getHeight());
		this.setWidth(menuBg.getWidth());
		this.setDirty(true);
		this.setModal(true);
		this.setDynamic(true);
	}
	
	public void hideMenu() {
		this.setVisible(false);
		this.setDirty(true);
		this.getContainer().dirtyAll();
		this.getContainer().refresh();
	}
	
	public void setMenuItems(String[] menuItems) {
		
		this.menuItems = menuItems;
		
		if(menuItems != null && menuItems.length > 0) {
			int height = (menuInset*2) + menuItems.length * (pad + getDefaultFont().getHeight());
			int width = getDefaultFont().stringWidth("MenuMenu");
			for(int i=0; i<menuItems.length; i++) {
				int ww = getDefaultFont().stringWidth(menuItems[i]) + (menuInset*2);
				if(ww > width)
					width = ww;
			}
		
			menuBg.setImage(ImageHolder.scaleImage2(menuBg.getImage(), width,height));
		
			this.setHeight(menuBg.getHeight());
			this.setWidth(menuBg.getWidth());
			
			// position bottom right corner - maybe make this a settings
			this.setX(this.getContainer().getWidth() - this.getWidth());
			this.setY(this.getContainer().getHeight() - this.getHeight());
		}
	}
	
	public void paint(Graphics g) {
		if(isDirty() && isVisible() && menuItems != null && menuItems.length > 0) {
			setDirty(false);	
			//System.out.println("Paint PopupMenu");
			
			g.setFont(Component.getDefaultFont());
			
			menuBg.setX(getX());
			menuBg.setY(getY());
			menuBg.setDirty(true);
			menuBg.paint(g);
			
			for(int i=0; i<menuItems.length; i++) {
				
				if(menuIdx == i)
					g.setColor(0x00FF0000);
				else
					g.setColor(0x00000000);
				g.drawString(menuItems[i],getX() + menuInset,getY() + menuInset + ((g.getFont().getHeight()+pad)*i),20);	
			}
		
		}
	}	
	
	public void keyPressed(int keyCode, int gameKeyCode) {		
		if(menuItems != null && menuItems.length > 0) {		
			if(getContainer().getGameAction(keyCode) == Canvas.UP || getContainer().getGameAction(keyCode) == Canvas.LEFT) {
				menuIdx--;
				if(menuIdx < 0)
					menuIdx = menuItems.length - 1;
				this.setDirty(true);
				getContainer().refresh();
			} else if(getContainer().getGameAction(keyCode) == Canvas.DOWN || getContainer().getGameAction(keyCode) == Canvas.RIGHT) {
				menuIdx++;
				if(menuIdx >= menuItems.length)
					menuIdx = 0;
				this.setDirty(true);
				getContainer().refresh();			
			} else if(getContainer().getGameAction(keyCode) == Canvas.FIRE) {
				this.popupMenuListener.menuItemSelected(menuItems[menuIdx]);
			}
		}
	}
}
