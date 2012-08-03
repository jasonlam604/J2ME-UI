package com.jasonlam604.j2me.ui.components;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import com.jasonlam604.j2me.util.Device;
import com.jasonlam604.j2me.util.Sprite;

public class ComboBox extends Component {

	private int selectedIndex;
	private String items[];

	private int textX;
	private int textY;
	private String text;

	private int arrowX;
	private int arrowY;
	private int arrowOffsetL;
	private int arrowOffsetR;
	private int textColor = 0x00477AC3;

	private Sprite IMG_COMBOBOXARROWS = new Sprite(Sprite.createImage("/comboboxarrows.png"),4);

	public ComboBox(String[] items, int posX, int posY, int width) {
		this.items = items;
		this.setSelectedIndex(0);
		this.setX(posX);
		this.setY(posY);
		this.setWidth(width);
		this.setHeight(Component.getDefaultFont().getHeight());
		this.textX = this.getX() + 1;
		this.textY = this.getY() + (this.getHeight() / 2) - (Component.getDefaultFont().getHeight() / 2) + 1;
		this.arrowOffsetL = 0;
		this.arrowOffsetR = 0;
		this.setDynamic(true);
		this.setDirty(true);
	}

	public ComboBox(String[] items, int posX, int posY) {
		this(items,posX,posY,Device.SCREEN_WIDTH - (posX * 3));
	}

	public void paint(Graphics g) {
		if(isDirty() && isVisible()) {
			setDirty(false);
		g.setFont(Component.getDefaultFont());

		// Draw Border and Background Color
		//g.setColor(0x00cccccc);
		g.setColor(0x00bbbbbb);
		g.drawRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		g.setColor(0x00FFFFFF);	g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());

		// Draw Selected Text in the ViewPort
		if (items != null & items.length > 0)
			this.text = items[this.getSelectedIndex()];
		else
			this.text = "";
		g.setColor(textColor);
		g.drawString(text, textX, textY, 20);

		// Draw Arrows
		arrowX = this.getX() + this.getWidth()
				- (IMG_COMBOBOXARROWS.getWidth() * 2);
		arrowY = this.getY() + (this.getHeight() / 2)
				- (IMG_COMBOBOXARROWS.getHeight() / 2);
		if (arrowOffsetL > 0) {
			g.setColor(0x00000000);
			g.fillRect(arrowX, this.getY(), IMG_COMBOBOXARROWS
					.getWidth(), this.getHeight());
		}
		IMG_COMBOBOXARROWS.setX(arrowX);
		IMG_COMBOBOXARROWS.setY(arrowY);
		IMG_COMBOBOXARROWS.setFrame(0 + arrowOffsetL);
		IMG_COMBOBOXARROWS.paint(g);

		if (arrowOffsetR > 0) {
			g.setColor(0x00000000);
			g.fillRect(arrowX + IMG_COMBOBOXARROWS.getWidth(), this.getY(), IMG_COMBOBOXARROWS.getWidth(), this.getHeight());
		}
		IMG_COMBOBOXARROWS.setX(arrowX + IMG_COMBOBOXARROWS.getWidth());
		IMG_COMBOBOXARROWS.setFrame(1 + arrowOffsetR);
		IMG_COMBOBOXARROWS.paint(g);

		// Draw Focus Border
		if (this.isFocus()) {
			this.paintFocus(g);
		}

		}
	}

	public int getSelectedIndex() {
		return this.selectedIndex;
	}

	public String getSelectedText() {
		if (this.items != null && this.items.length > 0)
			return this.items[this.selectedIndex];
		else
			return null;
	}

	public void setSelectedIndex(int selectedIndex) {
		if (this.items != null && items.length > 0 && selectedIndex >= 0 && selectedIndex < items.length) {
			this.setDirty(true);
			this.selectedIndex = selectedIndex;
		}
	}

	public void setSelectedIndex(String value) {
		if (this.items != null && items.length > 0 && value != null) {
			for(int i=0; i<items.length; i++) {
				if(items[i].equals(value)) {
					setSelectedIndex(i);
					this.setDirty(true);
					return;
				}
			}
		}
	}

	public void moveSelectorUp() {
		this.setDirty(true);
		arrowOffsetL = 2;
		if (this.selectedIndex - 1 >= 0)
			this.selectedIndex -= 1;
		else
			this.selectedIndex = this.items.length - 1;
		this.getContainer().refresh();
	}

	public void moveSelectorDown() {
		this.setDirty(true);
		arrowOffsetR = 2;
		if (this.selectedIndex + 1 < this.items.length)
			this.selectedIndex += 1;
		else
			this.selectedIndex = 0;
		this.getContainer().refresh();
	}

	public void keyPressed(int keyCode, int gameKeyCode) {
		if (gameKeyCode == Canvas.LEFT) {
			moveSelectorUp();
		} else if (gameKeyCode == Canvas.RIGHT) {
			moveSelectorDown();
		}
	}

	public void keyRepeated(int keyCode, int gameKeyCode) {
	}

	public void keyReleased(int keyCode, int gameKeyCode) {
		arrowOffsetL = 0;
		arrowOffsetR = 0;
	}

	public boolean pointerPressed(int x, int y) {
		boolean isComboBoxPressed = this.isPointerPress(x, y);

		// Point Press for Left Arrow
		if (x >= arrowX
				&& x <= (arrowX + IMG_COMBOBOXARROWS.getWidth())
				&& y >= arrowY
				&& y <= (arrowY + IMG_COMBOBOXARROWS.getHeight())) {
			moveSelectorUp();
		}

		if (x >= (arrowX + IMG_COMBOBOXARROWS.getWidth())
				&& x <= (arrowX + IMG_COMBOBOXARROWS.getWidth() * 2)
				&& y >= arrowY
				&& y <= (arrowY + IMG_COMBOBOXARROWS.getHeight())) {
			moveSelectorDown();
		}

		return isComboBoxPressed;
	}

	public boolean pointerDragged(int x, int y) {
		return false;
	}

	public boolean pointerReleased(int x, int y) {
		arrowOffsetL = 0;
		arrowOffsetR = 0;
		return true;
	}

}
