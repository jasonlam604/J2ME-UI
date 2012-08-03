package com.jasonlam604.j2me.ui.components;
 
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.TextField;

import com.jasonlam604.j2me.util.Device;

public class TextBox extends Component {

	private String text;
	private String label;
	private int pad = 1;
	
	private int textX;
	private int textY;	
	
	private Display display;
	
	private int maxStrlen;
	
	public TextBox(String label,String text,int x, int y, int width) {
		this.setTabStop(true);	
		this.setDirty(true);
		this.label = label;
		this.text = text;
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(Component.getDefaultFont().getHeight());
		this.maxStrlen = this.getWidth() - 9;
		this.textX = this.getX() + 2;
		this.textY = this.getY() + 2;
		
	}
	
	public void setDisplay(Display display) {
		this.display = display;
	}	
	
	public void paint(Graphics g) {
		if(isDirty() && isVisible()) {
			this.setDirty(false);
			
			g.setFont(Component.getDefaultFont());

			// Draw Border and Background Color
			g.setColor(0x00cccccc);
			g.drawRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
			g.setColor(0x00FFFFFF);
			g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
			
			// Draw Char(s)
			g.setColor(0x00000000);
			String viewableText = getViewableText();
			g.drawString(viewableText, textX, textY, 20);	

			if (this.isFocus()) {
				this.paintFocus(g);
			}
		}
	}
	
	private String getViewableText() {
		String newText = this.text;
		int strLen = Component.getDefaultFont().stringWidth(newText);
		if (strLen < maxStrlen) {
			return newText;
		} else {
			int idx = this.text.length() - 1;
			newText = "";
			while (Component.getDefaultFont().stringWidth(newText) < maxStrlen) {
				newText = this.text.charAt(idx) + newText;
				idx--;
			}
		}
		return newText;
	}

	public String getText() {
		return this.text;
	}
	
	public void setText(String text) {
	  this.text = text;
	}	

	public void keyPressed(int keyCode, int gameKeyCode) {
		if (gameKeyCode == Canvas.FIRE) {
			enterText();
		}		
	}
	
	public void keyRepeated(int keyCode, int gameKeyCode) {
	}
	
	public void keyReleased(int keyCode, int gameKeyCode) {		
	}
	
    public boolean pointerPressed(int x, int y) {
  	  boolean result = this.isPointerPress(x, y);
	  if(result) {
		enterText();
	  }
	  return result;     	
    }
    
    public boolean pointerDragged(int x, int y) {
    	return false;
    }    
    
    public boolean pointerReleased(int x, int y) {
    	return false;
    }
    
	private void enterText() {
		  
	      TextInput entry = new TextInput(this.getContainer(),this, this.label,this.text,Device.SCREEN_WIDTH,TextField.ANY,this.display);		  
	      this.display.setCurrent(entry);
	}
	
	private final class TextInput extends javax.microedition.lcdui.TextBox implements CommandListener {
		
		  private final Command cancel;
		  private final Command ok;			
		  private final TextBox component;
		  private ComponentContainer parent;
		  private Display display;

		  public TextInput (ComponentContainer parent,TextBox box, String label, String contents, int maxSize, int constraints, Display display)	{
			super( label, contents,  maxSize,  constraints );			
			this.parent = parent;
			this.component = box;
			this.display = display;
			
			String cancelText = "Cancel";
			String okText = "OK";
			
			cancel = new Command(cancelText, Command.CANCEL, 1 );
			ok = new Command(okText, Command.OK, 2 );
			addCommand(cancel);			
			addCommand(ok);			
			setCommandListener(this);
		 }

		 public void commandAction (Command c, Displayable d) {
		   if ( c == ok ) {
		 	 if(this.getString()==null) {
			   this.component.setText("");	
			 } else {
			   this.component.setText(this.getString());
			 }
		   }		   
		   parent.setFullScreenMode(true);		   
		   parent.dirtyAll();
		   parent.refresh();
		   this.display.setCurrent(this.parent);
		}
	  }	
}
