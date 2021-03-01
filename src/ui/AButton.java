package ui;

import java.awt.Graphics2D;

public class AButton extends AComponent {
	protected OnClick onClick;
	
	public AButton(int x, int y, int width, int height, String text) {
		super(text);
		
		super.x = x;
		super.y = y;
		
		super.width = width;
		super.height = height;
	}
	
	public void onClick(OnClick onClick) {
		this.onClick = onClick;
	}
	
	@Override
	public void render(Graphics2D graphics) {
		super.render(graphics);
		
		graphics.drawRect(x, y, width, height);
	}
	
	@FunctionalInterface
	public interface OnClick {
		public void doSomething();
	}
}
