package br.brunocatao.s2ge.arkanoid;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import br.brunocatao.s2ge.SimpleWindowedGame;

public class Raquete extends Sprite {
	private int velocidade;
	
	public Raquete(BufferedImage imagem, int largura, int altura) {
		super(imagem, largura, altura);
		velocidade = 5;
	}
	
	public void centralizar(SimpleWindowedGame janela) {
		int xRaquete = (janela.getWidth() - getLargura()) / 2;
		setPosicao(xRaquete, janela.getHeight() - 50);
	}
	
	public void mover(SimpleWindowedGame janela) {
		// movendo a raquete
		if (janela.isKeyPressed(KeyEvent.VK_LEFT)) {
			setX(getX() - velocidade);
		} else if (janela.isKeyPressed(KeyEvent.VK_RIGHT)) {
			setX(getX() + velocidade);
		}
		
		// limitar o movimento
		if (getX() < 0) {
			setX(0);
		}
		if (getX() > janela.getWidth() - getLargura()) {
			setX(janela.getWidth() - getLargura());
		}
	}

	public int getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(int velocidade) {
		this.velocidade = velocidade;
	}

}
