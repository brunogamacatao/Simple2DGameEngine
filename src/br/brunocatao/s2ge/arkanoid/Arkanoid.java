package br.brunocatao.s2ge.arkanoid;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import br.brunocatao.s2ge.SimpleWindowedGame;

public class Arkanoid extends SimpleWindowedGame {
	private Bola bola;
	private Raquete raquete;
	private ArrayList<Sprite> tijolos;
	private int pontos;
	
	public Arkanoid() {
		super("Arkanoid", 640, 640, Color.BLACK);

		// criando a bola
		bola = new Bola(loadImage("images/bola.png"), 24, 24);
		
		// criando a raquete
		raquete = new Raquete(loadImage("images/raquete.png"), 96, 24);
		raquete.centralizar(this);
		
		// criando o cenário
		tijolos = new ArrayList<>();
		inicializaCenario();
		
		// inicializo o placar
		pontos = 0;
	}

	@Override
	public void draw(Graphics2D g) {
		// desenhar os tijolos
		for (Sprite t : tijolos) {
			t.desenha(g, this);
		}
		
		// desenhar a bola
		bola.desenha(g, this);
		
		// desenha raquete
		raquete.desenha(g, this);
		
		// movendo a bola
		bola.mover(this);
		
		// checar as colisões da bola com a raquete
		bola.checaColisao(raquete);
		
		// checar as colisões da bola com os tijolos
		ArrayList<Sprite> tijolosParaRemover = new ArrayList<>();
		
		for (Sprite t : tijolos) {
			if (bola.checaColisao(t)) {
				tijolosParaRemover.add(t); // marco esse tijolo para remover
			}
		}
		
		// removo todos os tijolos marcados
		pontos += tijolosParaRemover.size();
		tijolos.removeAll(tijolosParaRemover);
		
		// movendo a raquete
		raquete.mover(this);
		
		// desenho o placar
		g.setFont(new Font("Arial", Font.BOLD, 36));
		g.setColor(Color.WHITE);
		g.drawString("" + pontos, 8, 40);
		
		// se pressionar esc, sai do jogo
		if (isKeyPressed(KeyEvent.VK_ESCAPE)) {
			System.exit(0);
		}
	}
	
	private void inicializaCenario() {
		int espaco = 64;
		for (int coluna = 0; coluna < 10; coluna++) {
			for (int linha = 0; linha < 4; linha++) {
				Sprite tijolo = new Sprite(loadImage("images/tijolo.png"), 64, 32);
				tijolo.setPosicao(coluna * tijolo.getLargura(), espaco + linha * tijolo.getAltura());
				tijolos.add(tijolo);
			}
		}
	}
	
	public static void main(String[] args) {
		Arkanoid a = new Arkanoid();
		a.start();
	}
}
