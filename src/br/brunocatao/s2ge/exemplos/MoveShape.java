package br.brunocatao.s2ge.exemplos;

import java.awt.Color;
import java.awt.Graphics2D;

import br.brunocatao.s2ge.SimpleWindowedGame;

public class MoveShape extends SimpleWindowedGame {
	private int x = 0;
	private int y = 0;
	private int vx = 2;
	private int vy = 2;
	private int largura = 100;
	private int altura = 100;
	private Color cor = Color.RED	;
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(cor);
		g.fillRect(x, y, largura, altura);
		
		x += vx;
		y += vy;
		
		if (x >= (this.getWidth() - largura) || x <= 0) vx = -vx;
		if (y >= (this.getHeight() - altura) || y <= 0) vy = -vy;
	}

	public static void main(String[] args) {
		MoveShape ms = new MoveShape();
		ms.start();
	}
}
