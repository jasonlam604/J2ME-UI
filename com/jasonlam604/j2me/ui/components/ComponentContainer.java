package com.jasonlam604.j2me.ui.components;

import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;


public class ComponentContainer extends Canvas {

	
	private Vector components;
	
	private boolean isInit = true;
	private int focusIndex = -1;
	private int focusIndexLast = -2;
	private boolean modalComponent = false;
	
	public ComponentContainer() {
		setFullScreenMode(true);
	}
	
	private void paintWidget(int index,boolean isFocus) {
		Component c = ((Component)components.elementAt(index));
		c.setFocus(isFocus);
		c.setDirty(true);
		refresh();
	}	
	
	protected void paint(Graphics g) {
		
		
		g.setFont(Component.getDefaultFont());
		
		if(isInit) {
			isInit = false;
					
			
			if(leftSoftKey != null) {
				addComponent(leftSoftKey);
			}
			if(rightSoftKey != null) {
				addComponent(rightSoftKey);
			}
			
			initFocus();
		}
		
		
		
		onPaintBefore(g);
		
		modalComponent = false;
		if(components != null && components.size() > 0) {
			Enumeration e = components.elements();
			int idx = 0;
			int modalIndex = -1;			
			while (e.hasMoreElements()) {
				Component c = (Component) e.nextElement();
				if(!c.isModal()) {
					c.paint(g);
				} else {
					if(c.isVisible())
						modalIndex = idx;
				}
				idx++;
			}
			
			if(modalIndex != -1) {
				//Paint modal last modal added, ideally you can only have 1 modal at a time
				//((Component)components.elementAt(modalIndex)).paint(g);
				((Component)components.elementAt(modalIndex)).paint(g);
				focusIndex = modalIndex;
				modalComponent = true;
			}
		}
		
		onPaintAfter(g);
	}
	
	public void onPaintBefore(Graphics g) {

		if(paintBg) {
			paintBg = false;
			onPaintBackground(g);
		}
	}
	
	public void onPaintAfter(Graphics g) {
		
	}
	
	private boolean paintBg = true;
	public void onPaintBackground(Graphics g) {
		
	}
	
	public void addComponent(Component component) {
		if(components == null || components.size() == 0) {
			components = new Vector();
		}
		component.setIndex(components.size() + 1);
		components.addElement(component);

	}
	
	public void dirtyAll() {
		if(components != null && components.size() > 0) {			
			Enumeration e = components.elements();
			paintBg = true;
			while (e.hasMoreElements()) {
				((Component) e.nextElement()).setDirty(true);				
			}			
		}	
	}
		
	public void refresh() {
		repaint();
	}
	
	protected void keyPressed(int keyCode) {
		//System.out.println("---" + keyCode);
		
		if(leftSoftKey != null && leftSoftKey.isVisible() && softKeyListener != null && keyCode == -6) {			
			this.softKeyListener.leftSoftKeyPressed();
		} else if(rightSoftKey != null && rightSoftKey.isVisible() && softKeyListener != null && keyCode == -7) {
			this.softKeyListener.rightSoftKeyPressed();
		} else { 
			
			if(modalComponent) {
				((Component)components.elementAt(focusIndex)).keyPressed(keyCode,getGameAction(keyCode));
			} else {
				
				if(getGameAction(keyCode) == Canvas.UP) {
					moveFocus(false);
				} else if(getGameAction(keyCode) == Canvas.DOWN) {
					moveFocus(true);			
				} else {		
					
					if(this.components != null && this.components.size() > 0) {
						//((Component)components.elementAt(focusIndex)).keyPressed(keyCode,getGameAction(keyCode));
						if(focusIndex != -1) {
						Component c = ((Component)components.elementAt(focusIndex));
						if(!c.isDynamic() && getGameAction(keyCode) == Canvas.RIGHT) {
							moveFocus(false);
						} else if(!c.isDynamic() && getGameAction(keyCode) == Canvas.LEFT) {
							moveFocus(true);
						} else {
							c.keyPressed(keyCode,getGameAction(keyCode));	
						}
						}
					}   	
				}
			}
		}
		
		refresh();
	}
	
	// Set Focus to the first tabStoppable component
	private void initFocus() {
		if(components != null && components.size() > 0) {
			boolean focusFound = false;
			Enumeration e = components.elements();
			int idx = 0;			
			while (e.hasMoreElements()) {
				Component c = (Component) e.nextElement();
				c.setFocus(false);
				if(c.isVisible() && c.isTabStop() && !focusFound) {
					c.setFocus(true);
					focusFound = true;
					focusIndex = idx;
				}
				idx++;
			}			
		}	
	}
	
	public void updateFocus() {
		// Need to do this better!!!!!
		// No component has focus when a Modal component closes 'visible = false'
		initFocus();
		
	}
	
	private void moveFocus(boolean isDown) {		
		if(this.components != null && this.components.size() > 0) {
			int maxLoop = 0;
			focusIndexLast = focusIndex;
			do {  
				if(isDown) {
					focusIndex++;
					if(focusIndex >= components.size())
						focusIndex = 0;
				} else {
					focusIndex--;
					if(focusIndex < 0)
						focusIndex = (components.size() - 1);				  
				}

				// Break Out after cycling through all widgets
				maxLoop++;
				if(maxLoop > components.size())
					break;
			} while (!((Component)components.elementAt(focusIndex)).isTabStop() || !((Component)components.elementAt(focusIndex)).isVisible());
			
			if(focusIndex != -1) {
			Component c = ((Component)components.elementAt(focusIndexLast));
			c.setFocus(false);
			c.setDirty(true);
			}
			
			if(focusIndex != -1) {
				Component c = ((Component)components.elementAt(focusIndex));
			c.setFocus(true);
			c.setDirty(true);			
			}
			
			return;
		}		
	}	
	
	private Button leftSoftKey = null;
	private Button rightSoftKey = null;
	private SoftKeyListener softKeyListener;
	public void setSoftKeyListener(SoftKeyListener softKeyListener) {
		this.softKeyListener = softKeyListener;
	}
	public void addSoftKeys(String leftLabel,String rightLabel) {
		
		if(leftLabel != null) {	
			leftSoftKey = new Button(leftLabel);
			leftSoftKey.setX(0);
			leftSoftKey.setY(getHeight() - leftSoftKey.getHeight());			
		}
		
		if(rightLabel != null) {
			rightSoftKey = new Button(rightLabel);
			rightSoftKey.setX(getWidth() - rightSoftKey.getWidth());
			rightSoftKey.setY(getHeight() - rightSoftKey.getHeight());
		}
	
	}

}
