package br.brunocatao.s2ge;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public abstract class SimpleWindowedGame extends JFrame implements KeyListener {
	private static final long serialVersionUID = 8563172519415369467L;

	public static final long DEFAULT_FRAME_DELAY = 15; // +- 60 FPS
	
	private int width;
	private int height;
	private Canvas canvas;
	private BufferStrategy buffer;
	private Graphics graphics;
	private Graphics2D g2d;
	private BufferedImage bi;
	private Color background;
	private boolean[] keys;
	private long frameDelay;
	
	public SimpleWindowedGame() {
		this("Simple Windowed Game");
	}
	
	public SimpleWindowedGame(String title) {
		this(title, 640, 480, Color.BLACK);
	}
	
	public SimpleWindowedGame(String title, int width, int height, Color background) {
		super(title);
		
		this.width = width;
		this.height = height;
		this.background = background;
		this.frameDelay = DEFAULT_FRAME_DELAY;
		
		this.setIgnoreRepaint(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		canvas = createCanvas();
		
		this.add(canvas);
		this.pack();
		
		setupGraphics();
		
		this.keys = new boolean[512];
		this.addKeyListener(this);
	}
	
	protected Canvas createCanvas() {
		Canvas canvas = new Canvas();
		canvas.setIgnoreRepaint(true);
		canvas.setSize(width, height);
		
		return canvas;
	}
	
	protected void setupGraphics() {
		canvas.createBufferStrategy(2);
		buffer = canvas.getBufferStrategy();

		// Get graphics configuration...
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();

		// Create off-screen drawing surface
		bi = gc.createCompatibleImage(width, height);
	}
	
	public abstract void draw(Graphics2D g);
	
	protected void gameLoop() {
		new Thread(() -> {
			while (true) {
				try {
					// clear back buffer...
					g2d = bi.createGraphics();
					
			        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

			        g2d.setRenderingHints(rh);		
					
					g2d.setColor(background);
					g2d.fillRect(0, 0, width, height);
					
					draw(g2d);
	
					// Blit image and flip...
					graphics = buffer.getDrawGraphics();
					graphics.drawImage(bi, 0, 0, null);
					if (!buffer.contentsLost()) {
						buffer.show();
					}
	
					// Let the OS have a little time...
					Thread.sleep(frameDelay);
				} catch (InterruptedException ex) {
					// does nothing
				} finally {
					// release resources
					if (graphics != null) {
						graphics.dispose();
					}
					
					if (g2d != null) {
						g2d.dispose();
					}
				}
			}
		}).start();
	}
	
	public BufferedImage loadImage(String path) {
		try {
		    return ImageIO.read(new File(path));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void start() {
		SwingUtilities.invokeLater(() -> {
			setLocationRelativeTo(null);
			setVisible(true);
			gameLoop();
		});
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public boolean isKeyPressed(int keyCode) {
		return keys[keyCode];
	}

	public long getFrameDelay() {
		return frameDelay;
	}

	public void setFrameDelay(long frameDelay) {
		this.frameDelay = frameDelay;
	}

	@Override
	public void keyPressed(KeyEvent evt) {
		keys[evt.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent evt) {
		keys[evt.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent evt) {
	}
}