package br.brunocatao.s2ge.exemplos;

import java.awt.Color;
import java.awt.Graphics2D;

import br.brunocatao.s2ge.SimpleWindowedGame;

public class HelloWorld extends SimpleWindowedGame {

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillRect(20, 20, 150, 150);
	}
	
	public static void main(String[] args) {
		HelloWorld hw = new HelloWorld();
		hw.start();
	}
}
