package br.brunocatao.s2ge.arkanoid;

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
			System.exit(0);
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
		if (bolaColideEmCima(s) || bolaColideEmBaixo(s)) {
			velocidadeY = -velocidadeY;
			return true;
		}
		
		if (bolaColideNaEsquerda(s) || bolaColideNaDireita(s)) {
			velocidadeX = -velocidadeX;
			return true;
		}
		
		return false;
	}
	
	private boolean bolaColideEmCima(Sprite s) {
		if ((getX() + getLargura()) > s.getX()) {
			if (getX() < s.getX() + s.getLargura()) {
				if ((getY() + getAltura()) >= s.getY()) {
					if ((getY() + getAltura()) < (s.getY() + s.getAltura() / 2)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}

	private boolean bolaColideEmBaixo(Sprite s) {
		if ((getX() + getLargura()) > s.getX()) {
			if (getX() < s.getX() + s.getLargura()) {
				if (getY() <= (s.getY() + s.getAltura())) {
					if (getY() > (s.getY() + s.getAltura() / 2)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}

	private boolean bolaColideNaEsquerda(Sprite s) {
		if ((getY() - getAltura()) > s.getY()) {
			if (getY() < (s.getY() - s.getAltura())) {
				if ((getX() + getLargura()) >= s.getX()) {
					if ((getX() + getLargura()) <= (s.getX() + velocidadeX)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}

	private boolean bolaColideNaDireita(Sprite s) {
		if ((getY() - getAltura()) > s.getY()) {
			if (getY() < (s.getY() - s.getAltura())) {
				if (getX() <= (s.getX() + s.getLargura())) {
					if ((getX() - velocidadeX) >= (s.getX() + s.getLargura())) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
}
