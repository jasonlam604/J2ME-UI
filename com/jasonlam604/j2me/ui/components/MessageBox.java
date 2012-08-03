package com.jasonlam604.j2me.ui.components;
 
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

import com.jasonlam604.j2me.util.Device;

public class MessageBox extends Component {

	public final static int TYPE_OK = 0;
	public final static int TYPE_YESNO = 1;
	private int type = TYPE_OK;	
	
	public final static int RESULT_OK = 0;	
	public final static int RESULT_YES = 1;
	public final static int RESULT_NO = 2;	
	
	private String[] message;
	private int messageOffsetY;
	
	private Button leftButton;
	private Button rightButton;	
	
	private MessageBoxListener messageBoxListener;
	
	private int invokerId;
	
	public MessageBox() {
		this.setModal(true);
		this.setVisible(false);
		
		message = new String[1];
		message[0] = "change me";
		
		
		if(message != null && message.length > 0)
			this.adjustComponents();
	}
	
	public void setMessageBoxListener(MessageBoxListener messageBoxListener) {
		this.messageBoxListener = messageBoxListener;
	}
	
	public void setMessage(String[] message, int invokerId, int type) {
		this.setDirty(true);
		this.message = message;
		this.invokerId = invokerId;
		this.type = type;
		adjustComponents();
	}
	
	private void adjustComponents() {
		this.setDirty(true);
		setWidth(Device.SCREEN_WIDTH - 20);
		
		setHeight( (message.length + 4) * Component.getDefaultFont().getHeight());

		setX( (Device.SCREEN_WIDTH/2) - (getWidth()/2) );
		setY( (Device.SCREEN_HEIGHT/2) - (getHeight()/2));

		messageOffsetY = getY() + Component.getDefaultFont().getHeight();
		
		/*
		if(this.type == TYPE_OK) {
			leftButton = null;
			rightButton = new Button("OK",getPosX() + getWidth() - 4 - Button.buttonWidth,getPosY() + getHeight() - 4 - Button.buttonHeight);
			rightButton.setFocus(true);
		}
		*/

		Button dummyButton = new Button("dummy");
		
		if(this.type == TYPE_OK) {
			leftButton = null;
			rightButton = new Button("OK");
			rightButton.setX(getX() + getWidth() - 4 - dummyButton.getWidth());
			rightButton.setY(getY() + getHeight() - 4 - dummyButton.getHeight());
			rightButton.setFocus(true);
		} else if(this.type == TYPE_YESNO) {
			leftButton = new Button("No");
			leftButton.setX(getX() + getWidth() - 8 - (dummyButton.getWidth()*2));
			leftButton.setY(getY() + getHeight() - 4 - dummyButton.getHeight());
			rightButton = new Button("Yes");
			rightButton.setX(getX() + getWidth() - 4 - dummyButton.getWidth());
			rightButton.setY(getY() + getHeight() - 4 - dummyButton.getHeight());
			leftButton.setFocus(false);
			rightButton.setFocus(true);
		}
		
	}
	
	public void paint(Graphics g) {
		
		if(isDirty() && isVisible()) {
			setDirty(false);
		
			g.setFont(Component.getDefaultFont());
			
			g.setColor(0x008DA3A9);
			g.fillRoundRect(getX(), getY(), getWidth(), getHeight(), 15,15);

			g.setColor(0x00000000);
			g.drawRoundRect(getX(), getY(), getWidth(), getHeight(), 15,15);


			if(message != null && message.length > 0) {
				g.setColor(0x00000000);
				for(int i=0; i<message.length; i++) {
					g.drawString(message[i], getX() + (getWidth()/2) - (g.getFont().stringWidth(message[i])/2), messageOffsetY + (i * (g.getFont().getHeight())), 20);
				}
			}

			if(leftButton != null) {
				leftButton.paint(g);
			}

			if(rightButton != null) {
				rightButton.paint(g);
			}
		}
	}
	
	public void keyPressed(int keyCode, int gameKeyCode) {
		
		
		
		if(this.type == TYPE_YESNO) {
			if (gameKeyCode == Canvas.LEFT && !leftButton.isFocus()) {
				leftButton.setFocus(true);
				leftButton.setDirty(true);
				rightButton.setFocus(false);
				rightButton.setDirty(true);
				this.setDirty(true);
				this.getContainer().refresh();
			} else if (gameKeyCode == Canvas.RIGHT && leftButton.isFocus()) {
				leftButton.setFocus(false);
				leftButton.setDirty(true);
				rightButton.setFocus(true);
				rightButton.setDirty(true);
				this.setDirty(true);
				this.getContainer().refresh();
			}
		}

		if (gameKeyCode == Canvas.FIRE) {

			if(this.type == TYPE_YESNO) {
				this.setDirty(false);
				this.setVisible(false);				
				if(leftButton.isFocus()) {
					this.messageBoxListener.messageBoxEvent(RESULT_NO,this.invokerId);
				} else if(rightButton.isFocus()) {
					this.messageBoxListener.messageBoxEvent(RESULT_YES,this.invokerId);
				}					
			} else {
				this.setDirty(false);
				this.setVisible(false);
				this.messageBoxListener.messageBoxEvent(RESULT_OK,this.invokerId);
			}
		}

	}		

}
