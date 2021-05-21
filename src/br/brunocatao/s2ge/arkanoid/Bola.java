package br.brunocatao.s2ge.arkanoid;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import br.brunocatao.s2ge.SimpleWindowedGame;

public class Bola extends Sprite {
	private int velocidadeX;
	private int velocidadeY;

	public Bola(BufferedImage imagem, int largura, int altura) {
		super(imagem, largura, altura);
		
		velocidadeX = 3;
		velocidadeY = 3;
		
		setPosicao(150, 250);
	}

	public void mover(SimpleWindowedGame janela) {
		// movendo a bola
		setX(getX() + velocidadeX);
		setY(getY() + velocidadeY);
		
		// checar as colisões da bola com o cenário
		if (getY() >= janela.getHeight() - getAltura()) {
			velocidadeY = -velocidadeY;
		}
		if (getY() <= 0) {
			velocidadeY = -velocidadeY;
		}
		if (getX() >= janela.getWidth() - getLargura()) {
			velocidadeX = -velocidadeX;
		}
		if (getX() <= 0) {
			velocidadeX = -velocidadeX;
		}
	}
	
	public boolean checaColisao(Sprite s) {
		return checaColisao(s, false);
	}
	
	public boolean checaColisao(Sprite s, boolean variaAngulo) {
		boolean colidiu = false;
		
		if (bolaColideEmCima(s) || bolaColideEmBaixo(s)) {
			velocidadeY = -velocidadeY;
			
			if (variaAngulo) {
				double meioBola = this.getX() + this.getLargura() / 2.0;
				double meioS = s.getX() + s.getLargura() / 2.0;
				velocidadeX = (int)((meioBola - meioS) * 0.15);
			}
			
			colidiu = true;
		}
		
		if (bolaColideNaEsquerda(s) || bolaColideNaDireita(s)) {
			velocidadeX = -velocidadeX;
			colidiu = true;
		}
		
		return colidiu;
	}
	
	private boolean bolaColideEmCima(Sprite s) {
		Rectangle rBola = new Rectangle(getX(), getY(), getLargura(), getAltura());
		Rectangle rCima = new Rectangle(s.getX(), s.getY() - Math.abs(velocidadeY), s.getLargura(), Math.abs(velocidadeY));
		return rBola.intersects(rCima);
	}

	private boolean bolaColideEmBaixo(Sprite s) {
		Rectangle rBola = new Rectangle(getX(), getY(), getLargura(), getAltura());
		Rectangle rBaixo = new Rectangle(s.getX(), s.getY() + s.getAltura(), s.getLargura(), Math.abs(velocidadeY));
		return rBola.intersects(rBaixo);
	}

	private boolean bolaColideNaEsquerda(Sprite s) {
		Rectangle rBola = new Rectangle(getX(), getY(), getLargura(), getAltura());
		Rectangle rEsquerda = new Rectangle(s.getX() - Math.abs(velocidadeX), s.getY(), Math.abs(velocidadeX), s.getAltura());
		return rBola.intersects(rEsquerda);
	}

	private boolean bolaColideNaDireita(Sprite s) {
		Rectangle rBola = new Rectangle(getX(), getY(), getLargura(), getAltura());
		Rectangle rDireita = new Rectangle(s.getX() + s.getLargura(), s.getY(), Math.abs(velocidadeX), s.getAltura());
		return rBola.intersects(rDireita);
	}
}
