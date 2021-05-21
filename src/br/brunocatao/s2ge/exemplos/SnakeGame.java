package br.brunocatao.s2ge.exemplos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import br.brunocatao.s2ge.SimpleWindowedGame;

public class SnakeGame extends SimpleWindowedGame {
	public static final int TILES_X = 40;
	public static final int TILES_Y = 40;
	
	private boolean gameOver = false;
	private Point maca; // maçã
	private List<Point> snake;
	private int velX = -1;
	private int velY = 0;
	private long gameSpeed = 100; // msecs entre os frames
	
	public SnakeGame() {
		super("Snake", 400, 400, Color.WHITE);
		inicializaJogo();
		
		this.setFrameDelay(gameSpeed);
	}

	private void inicializaJogo() {
		gameOver = false;
		snake = new ArrayList<>();
		
		// a cobra vai iniciar no meio da tela
		snake.add(new Point(TILES_X / 2, TILES_Y / 2));
		
		// coloca uma "maçã" no ponto 2, 2
		maca = new Point(2, 2);
	}
	
	@Override
	public void draw(Graphics2D g) {
		int larguraTile = this.getWidth() / TILES_X;
		int alturaTile = this.getHeight() / TILES_Y;
		
		desenhaMacas(g, larguraTile, alturaTile);
		desenhaCobra(g, larguraTile, alturaTile);

		if (gameOver) {
			desenhaGameOver(g);
		} else {
			if (comeuMaca()) {
				// adiciona um novo ponto ao final
				Point novoPonto = new Point(snake.get(snake.size() - 1));
				snake.add(novoPonto);
			} else if (teveColisao()) {
				gameOver = true;
			}
			
			moveCobra();
		}
		
		rolagemDeCenario();
		desenhaPlacar(g);
	}

	private void desenhaGameOver(Graphics2D g) {
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.setColor(Color.RED);
		g.drawString("Game Over", 125, 200);
	}

	private boolean teveColisao() {
		Point cabeca = snake.get(0);
		for (int i = 1; i < snake.size(); i++) {
			Point p = snake.get(i);
			if (cabeca.x == p.x && cabeca.y == p.y) {
				return true;
			}
		}
		
		return false;
	}

	private void desenhaPlacar(Graphics2D g) {
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.setColor(Color.BLUE);
		g.drawString("Pontos: " + snake.size(), 290, 20);
	}

	private boolean comeuMaca() {
		Point cabeca = snake.get(0);
		if (cabeca.x == maca.x && cabeca.y == maca.y) {
			// gera uma maçã aleatória
			int novoX = (int)(Math.random() * TILES_X);
			int novoY = (int)(Math.random() * TILES_Y);
			maca.setLocation(novoX, novoY);
			return true;
		}
		return false;
	}

	private void rolagemDeCenario() {
		for (Point p : snake) {
			if (p.x < 0) {
				p.setLocation(TILES_X - 1, p.y);
			} else if (p.x >= TILES_X) {
				p.setLocation(0, p.y);
			}
			if (p.y < 0) {
				p.setLocation(p.x, TILES_Y - 1);
			} else if (p.y >= TILES_Y) {
				p.setLocation(p.x, 0);
			}
		}
	}

	private void moveCobra() {
		// 1. cada ponto deverá ir para o lugar do anterior
		for (int i = snake.size() - 1; i > 0; i--) {
			Point atual = snake.get(i);
			Point anterior = snake.get(i - 1);
			atual.setLocation(anterior);
		}
		// 2. move a cabeça da cobra
		Point cabeca = snake.get(0);
		cabeca.setLocation(cabeca.x + velX, cabeca.y + velY);
	}

	private void desenhaCobra(Graphics2D g, int larguraTile, int alturaTile) {
		for (Point p : snake) {
			g.setColor(Color.BLACK);
			g.fillRect(p.x * larguraTile, p.y * alturaTile, larguraTile, alturaTile);
		}
	}

	private void desenhaMacas(Graphics2D g, int larguraTile, int alturaTile) {
		g.setColor(Color.RED);
		g.fillRect(maca.x * larguraTile, maca.y * alturaTile, larguraTile, alturaTile);
	}
	
	@Override
	public void keyPressed(KeyEvent evt) {
		if (gameOver) {
			inicializaJogo();
		}
		
		// atualiza o movimento de acordo com o teclado
		if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
			velX = 1;
			velY = 0;
		} else if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
			velX = -1;
			velY = 0;
		} else if (evt.getKeyCode() == KeyEvent.VK_UP) {
			velY = -1;
			velX = 0;
		} else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
			velY = 1;
			velX = 0;
		}
		
		if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
	}
	

	public static void main(String[] args) {
		SnakeGame snake = new SnakeGame();
		snake.start();
	}
}
