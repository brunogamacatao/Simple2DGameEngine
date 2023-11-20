package br.brunocatao.s2ge.exemplos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import br.brunocatao.s2ge.SimpleWindowedGame;

public class Sokoban extends SimpleWindowedGame {
	public static final int TAMANHO_BLOCO = 64;
	
	private static final Tile LEVELS [][][] = {
			{
				{Tile.PAREDE, Tile.PAREDE, Tile.PAREDE, Tile.PAREDE, Tile.PAREDE, Tile.PAREDE, Tile.PAREDE},
				{Tile.PAREDE, Tile.ALVO,   Tile.CHAO,   Tile.CAIXA,  Tile.CHAO,   Tile.ALVO,   Tile.PAREDE},
				{Tile.PAREDE, Tile.CHAO,   Tile.CAIXA,  Tile.CHAO,   Tile.CAIXA,  Tile.CHAO,   Tile.PAREDE},
				{Tile.PAREDE, Tile.ALVO,   Tile.CHAO,   Tile.CAIXA,  Tile.CHAO,   Tile.ALVO,   Tile.PAREDE},
				{Tile.PAREDE, Tile.PAREDE, Tile.PAREDE, Tile.PAREDE, Tile.PAREDE, Tile.PAREDE, Tile.PAREDE},
			}
	};
	
	private Map<Tile, BufferedImage> imagemTile;
	private int level = 0;
	
	public Sokoban() {
		super("Sokoban", 7 * TAMANHO_BLOCO, 5 * TAMANHO_BLOCO, Color.WHITE);
		
		imagemTile = new HashMap<>();
		imagemTile.put(Tile.CHAO,   loadImage("images/ground_05.png"));
		imagemTile.put(Tile.PAREDE, loadImage("images/block_05.png"));
		imagemTile.put(Tile.CAIXA,  loadImage("images/crate_07.png"));
		imagemTile.put(Tile.ALVO,   loadImage("images/crate_27.png"));
	}

	@Override
	public void draw(Graphics2D g) {
		for (int y = 0; y < LEVELS[level].length; y++) {
			for (int x = 0; x < LEVELS[level][y].length; x++) {
				g.drawImage(imagemTile.get(LEVELS[level][y][x]), x * TAMANHO_BLOCO, y * TAMANHO_BLOCO, this);
			}
		}
	}

	public static void main(String[] args) {
		new Sokoban().start();
	}
}

enum Tile {
	CHAO,
	PAREDE,
	CAIXA,
	ALVO
}
