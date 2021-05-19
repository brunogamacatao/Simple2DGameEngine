package br.brunocatao.s2ge.exemplos;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import br.brunocatao.s2ge.SimpleWindowedGame;

public class MovePicture extends SimpleWindowedGame {
	private BufferedImage paisagem;
	private BufferedImage personagem;
	private int personagemX = 0;
	private int personagemY = 100;
	private int velocidade = 5;
	private int personagemLargura = 235;
	
	public MovePicture() {
		super("Quase um jogo");
		
		paisagem = loadImage("images/landscape.png");
		personagem = loadImage("images/girl.png");
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(paisagem, 0, 0, this);
		g.drawImage(personagem, personagemX, personagemY, this);
		
		// Movendo o personagem com o teclado
		if (isKeyPressed(KeyEvent.VK_LEFT)) { // se apertar seta pra esquerda
			personagemX -= velocidade; // move o personagem para esquerda
		}
		
		if (isKeyPressed(KeyEvent.VK_RIGHT)) { // se apertar seta para direita
			personagemX += velocidade; // move o personagem para direita
		}
		
		if (isKeyPressed(KeyEvent.VK_ESCAPE)) { // se apertar ESC
			System.exit(0); // sai do jogo
		}
		
		// Quando o personagem sair de um lado ele vai voltar pelo outro
		if (personagemX > this.getWidth()) {
			personagemX = -personagemLargura;
		}
		
		if (personagemX < -personagemLargura) {
			personagemX = this.getWidth();
		}
	}
	
	public static void main(String[] args) {
		MovePicture mp = new MovePicture();
		mp.start();
	}
}
