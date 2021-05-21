package br.brunocatao.s2ge.arkanoid;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import br.brunocatao.s2ge.SimpleWindowedGame;

public class Sprite {
	private BufferedImage imagem;
	private int largura;
	private int altura;
	private int x;
	private int y;
	
	public Sprite(BufferedImage imagem, int largura, int altura) {
		this.imagem  = imagem;
		this.largura = largura;
		this.altura  = altura;
		this.x = 0;
		this.y = 0;
	}
	
	public void desenha(Graphics2D g, SimpleWindowedGame janela) {
		g.drawImage(imagem, x, y, janela);
	}
	
	public void setPosicao(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public BufferedImage getImagem() {
		return imagem;
	}

	public void setImagem(BufferedImage imagem) {
		this.imagem = imagem;
	}

	public int getLargura() {
		return largura;
	}

	public void setLargura(int largura) {
		this.largura = largura;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
