package br.brunocatao.s2ge.exemplos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import br.brunocatao.s2ge.SimpleWindowedGame;

public class Sokoban extends SimpleWindowedGame {
	private static final long serialVersionUID = -1354406462747417235L;

	/*
	 * Nesse array são definidas as fases do jogo.
	 * 
	 * Os atributos da fase são:
	 *   1. A posição x e y inicial do jogador
	 *   2. O array do cenário (1 é parede, 0 é chão e 3 é o alvo)
	 *   3. Array com as caixas e suas respectivas posições
	 *   
	 * O jogo roda em ciclo, cada vez que você resolve uma fase, automaticamente
	 * ele vai para a fase seguinte. Quando chega no final ele volta para o começo.
	 */
	private static final Level[] LEVELS = {
			new Level(3, 2, new int[][] {
				{1, 1, 1, 1, 1, 1, 1},
				{1, 3, 0, 0, 0, 3, 1},
				{1, 0, 0, 0, 0, 0, 1},
				{1, 3, 0, 0, 0, 3, 1},
				{1, 1, 1, 1, 1, 1, 1},
			}, new Caixa[] {new Caixa(3, 1), new Caixa(2, 2), new Caixa(4, 2), new Caixa(3, 3)}),
			new Level(3, 2, new int[][] {
				{1, 1, 1, 1, 1, 1, 1, 1},
				{1, 0, 0, 3, 3, 0, 0, 1},
				{1, 0, 0, 0, 0, 0, 0, 1},
				{1, 0, 0, 3, 3, 0, 0, 1},
				{1, 1, 1, 1, 1, 1, 1, 1},
			}, new Caixa[] {new Caixa(2, 2), new Caixa(2, 3), new Caixa(5, 1), new Caixa(5, 2)}),
			new Level(1, 3, new int[][] {
				{0, 1, 1, 1, 1, 1, 1, 1},
				{0, 1, 0, 0, 0, 0, 0, 1},
				{1, 1, 0, 3, 0, 3, 0, 1},
				{1, 0, 0, 0, 0, 0, 0, 1},
				{1, 0, 0, 3, 0, 3, 0, 1},
				{1, 1, 0, 0, 0, 0, 0, 1},
				{0, 1, 1, 1, 1, 1, 1, 1},
			}, new Caixa[] {new Caixa(4, 2), new Caixa(3, 3), new Caixa(5, 3), new Caixa(4, 4)})
			
	};
	
	private Map<Tile, BufferedImage> imagemTile;
	private BufferedImage[] imagensPlayer;
	private int level;
	private Player player;
	
	public Sokoban() {
		super("Sokoban");
		
		this.setBackground(Color.WHITE);
		
		// carregando as imagens do jogo
		imagemTile = new HashMap<>();
		imagemTile.put(Tile.CHAO,   loadImage("images/ground_05.png"));
		imagemTile.put(Tile.PAREDE, loadImage("images/block_05.png"));
		imagemTile.put(Tile.CAIXA,  loadImage("images/crate_07.png"));
		imagemTile.put(Tile.ALVO,   loadImage("images/crate_27.png"));
		
		imagensPlayer = new BufferedImage[25];
		for (int i = 1; i <= 24; i++) {
			imagensPlayer[i - 1] = loadImage(String.format("images/player/player_%02d.png", i));
		}
		
		// definindo qual será o level jogado
		loadLevel(0);
	}
	
	private void loadLevel(int level) {
		this.level = level;
		LEVELS[level].reset();
		player = createPlayer();
		
		setWidth(LEVELS[level].getLargura() * Config.TAMANHO_BLOCO);
		setHeight(LEVELS[level].getAltura() * Config.TAMANHO_BLOCO);
		
		this.pack();
		this.setLocationRelativeTo(null);
	}
	
	private Player createPlayer() {
		return new Player(LEVELS[level].getPlayerInitialX(), LEVELS[level].getPlayerInitialY(), imagensPlayer);
	}

	@Override
	public void draw(Graphics2D g) {
		// desenha o level
		LEVELS[level].draw(g, imagemTile);
		// desenha o player
		player.draw(g);
	}
	
	@Override
	public void keyPressed(KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
			player.move(-1, 0, LEVELS[level]);
		} else if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.move(+1, 0, LEVELS[level]);
		} else if (evt.getKeyCode() == KeyEvent.VK_UP) {
			player.move(0, -1, LEVELS[level]);
		} else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
			player.move(0, +1, LEVELS[level]);
		} else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
			loadLevel(level);
		}
		
		if (LEVELS[level].ganhou()) {
			int novoLevel = (level + 1) % LEVELS.length;
			loadLevel(novoLevel);
		}
	}	

	public static void main(String[] args) {
		new Sokoban().start();
	}
}

/*
 * Abaixo são definidas as classes auxiliares para o funcionamento do jogo
 */
class Config {
	public static final int TAMANHO_BLOCO = 64;
}

enum Tile {
	CHAO,   // 0
	PAREDE, // 1
	CAIXA,  // 2
	ALVO    // 3
}

class Level {
	private int playerInitialX, playerInitialY;
	private int[][] level;
	private Caixa[] caixas;
	
	public Level(int playerInitialX, int playerInitialY, int [][] level, Caixa[] caixas) {
		this.playerInitialX = playerInitialX;
		this.playerInitialY = playerInitialY;
		this.level  = level;
		this.caixas = caixas;
	}
	
	public void reset() {
		for (Caixa c : caixas) {
			c.reset();
		}
	}

	public void draw(Graphics2D g, Map<Tile, BufferedImage> imagemTile) {
		// desenha os blocos do cenário
		for (int y = 0; y < this.getAltura(); y++) {
			for (int x = 0; x < this.getLargura(); x++) {
				g.drawImage(imagemTile.get(this.getTile(x, y)), x * Config.TAMANHO_BLOCO, y * Config.TAMANHO_BLOCO, null);
			}
		}
		// desenha as caixas
		for (Caixa c : caixas) {
			g.drawImage(imagemTile.get(Tile.CAIXA), c.x * Config.TAMANHO_BLOCO, c.y * Config.TAMANHO_BLOCO, null);
		}
	}
	
	public int getPlayerInitialX() {
		return playerInitialX;
	}

	public int getPlayerInitialY() {
		return playerInitialY;
	}

	public int getLargura() {
		return level[0].length;
	}
	
	public int getAltura() {
		return level.length;
	}
	
	public Tile getTile(int x, int y) {
		switch (level[y][x]) {
		case 0:
			return Tile.CHAO;
		case 1:
			return Tile.PAREDE;
		case 2:
			return Tile.CAIXA;
		case 3:
			return Tile.ALVO;
		}
		return Tile.CHAO;
	}
	
	private Caixa getCaixa(int x, int y) {
		for (Caixa c : caixas) {
			if (c.x == x && c.y == y) {
				return c;
			}
		}
		return null;
	}
	
	public boolean temCaixa(int x, int y) {
		return getCaixa(x, y) != null;
	}
	
	public boolean podeMoverCaixaPara(int x, int y) {
		return !temCaixa(x, y) && (level[y][x] == Tile.CHAO.ordinal() || level[y][x] == Tile.ALVO.ordinal());
	}
	
	public void moveCaixa(int x0, int y0, int x1, int y1) {
		Caixa c = getCaixa(x0, y0);
		c.x = x1;
		c.y = y1;
	}
	
	public boolean ganhou() {
		// conta quantas caixas estão em cima de alvos
		int caixasNosAlvos = 0;
		for (Caixa c : caixas) {
			if (level[c.y][c.x] == Tile.ALVO.ordinal()) {
				caixasNosAlvos++;
			}
		}
		// ganhou se todas as caixas estão nos alvos
		return caixasNosAlvos == caixas.length;
	}
}

class Player {
	private int x, y;
	private BufferedImage[] imagens;
	
	
	public Player(int x, int y, BufferedImage[] imagens) {
		this.x = x; 
		this.y = y;
		this.imagens = imagens;
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(imagens[0], x * Config.TAMANHO_BLOCO, y * Config.TAMANHO_BLOCO, null);
	}
	
	public void move(int dx, int dy, Level level) {
		int nx = x + dx;
		int ny = y + dy;
		
		switch (level.getTile(nx, ny)) {
		case PAREDE:
			return;
		default:
			if (level.temCaixa(nx, ny)) {
				int caixaVaiParaX = nx + dx;
				int caixaVaiParaY = ny + dy;
				
				if (level.podeMoverCaixaPara(caixaVaiParaX, caixaVaiParaY)) {
					// move a caixa
					level.moveCaixa(nx, ny, caixaVaiParaX, caixaVaiParaY);
					// move o player
					x = nx;
					y = ny;
				}
			} else {
				// move o player
				x = nx;
				y = ny;
			}
		}
	}
}

class Caixa {
	public int x, y;
	private int x0, y0;
	
	public Caixa(int x, int y) {
		this.x = x;
		this.y = y;
		this.x0 = x;
		this.y0 = y;
	}
	
	public void reset() {
		this.x = x0;
		this.y = y0;
	}
}
