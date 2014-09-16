/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Set;

/**
 * GameCourt
 * 
 * This class holds the primary game logic of how different objects 
 * interact with one another.  Take time to understand how the timer 
 * interacts with the different methods and how it repaints the GUI 
 * on every tick().
 *
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

	// the state of the game logic
	private Square square; // the player ship, keyboard control
	private UFO ufo;
	private ArrayList<Shipa> shipr0 = new ArrayList<Shipa>();
	private ArrayList<Ship> shipr1 = new ArrayList<Ship>();
	private ArrayList<Ship> shipr2 = new ArrayList<Ship>();
	private ArrayList<Shipb> shipr3 = new ArrayList<Shipb>();
	private ArrayList<Shipb> shipr4 = new ArrayList<Shipb>();
	private ArrayList<Bullet> bullet = new ArrayList<Bullet>();
	private ArrayList<UBullet> ubullet = new ArrayList<UBullet>();
	public static final String ship_2 = "ship.jpg";
	public static final String ship_1 = "ship1.jpg";
	public static final String ship_3 = "ship3.jpg";
	
	int bflag = 0;
	int gamelevel = 0;
	int uflag = 0;
	int bcounter = -1;
	int lives = 3;
	int dircounter = 0;
	int ycounter = 0;
	int ufocounter = 0;
	int speedcounter = 0;
	boolean changedir = true;
	boolean lflag = true;
	boolean clearflag = false;
	boolean gameover = false;
	int points = 0;
	int ufolife = 5;
	
	
	public boolean playing = false;  // whether the game is running
	private JLabel status;       // Current status text (i.e. Running...)
	private JLabel score;
	private JLabel level;

	// Game constants
	public static final int COURT_WIDTH = 500;
	public static final int COURT_HEIGHT = 500;
	public static final int SQUARE_VELOCITY = 6;
	public static final int BULLET_VELOCITY = 4;
	// Update interval for timer in milliseconds 
	public static final int INTERVAL = 35; 

	public GameCourt(JLabel status, JLabel score, JLabel level){
		
		
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        // The timer is an object which triggers an action periodically
        // with the given INTERVAL. One registers an ActionListener with
        // this timer, whose actionPerformed() method will be called 
        // each time the timer triggers. We define a helper method
        // called tick() that actually does everything that should
        // be done in a single timestep.
		Timer timer = new Timer(INTERVAL, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tick();
			}
		});
		 // MAKE SURE TO START THE TIMER!
		timer.start();

		// Enable keyboard focus on the court area
		// When this component has the keyboard focus, key
		// events will be handled by its key listener.
		setFocusable(true);

		// this key listener allows the square to move as long
		// as an arrow key is pressed, by changing the square's
		// velocity accordingly. (The tick method below actually 
		// moves the square.)
		
		addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if (e.getKeyCode() == KeyEvent.VK_LEFT)
					square.v_x = -SQUARE_VELOCITY;
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					square.v_x = SQUARE_VELOCITY;
				else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					bflag = 1;
					Bullet b = square.shoot(COURT_WIDTH, COURT_HEIGHT, 
							square.pos_x + 10, square.pos_y + 10);
					bullet.add(b);
					
					Bullet getb = bullet.get(bullet.size() - 1);
					getb.v_y = -BULLET_VELOCITY;
				}
			}
			public void keyReleased(KeyEvent e){
				square.v_x = 0;
				square.v_y = 0;
			}
		});
		
		this.status = status;
		this.score = score;
		this.level = level;
	}

	/** (Re-)set the state of the game to its initial state.
	 */
	public void reset() {
		clearCanvas();
		playing = true;
		dircounter = 0;
		ycounter = 0;
		ufocounter = 0;
		bflag = 0;
		uflag = 0;
		speedcounter = 0;
		changedir = true;
		clearflag = false;
		lflag = true;
		
		if (gameover == true) {
			lives = 3;
			points = 0;
			gameover = false;
		}
		
		
		square = new Square(COURT_WIDTH, COURT_HEIGHT);
		ufo = new UFO(COURT_WIDTH, COURT_HEIGHT, 3, 0, -40, 5, 40);
		

		int shipx = 2;
	for (int i = 0; i < 11; i++) {
			Shipa newship = new Shipa(ship_1, COURT_WIDTH, COURT_HEIGHT, 2 + gamelevel, 0,
					shipx, 5, 25);
			shipr0.add(newship);
			shipx += 35;
		}
		
		
		shipx = 0;
		for (int i = 0; i < 11; i++) {
			
			Ship newship = new Ship(ship_2, COURT_WIDTH, COURT_HEIGHT, 2 + gamelevel, 0,
					shipx, 35, 30);
			shipr1.add(newship);
			shipx += 35;
		}
		
		shipx = 0;
		for (int i = 0; i < 11; i++) {
			Ship newship = new Ship(ship_2, COURT_WIDTH, COURT_HEIGHT, 2 + gamelevel, 0,
					shipx, 65, 30);
			shipr2.add(newship);
			shipx += 35;
		}
		
		shipx = 2;
		for (int i = 0; i < 11; i++) {
			Shipb newship = new Shipb(ship_3, COURT_WIDTH, COURT_HEIGHT, 2 + gamelevel, 0,
					shipx, 95, 25);
			shipr3.add(newship);
			shipx += 35;
		}
		
		shipx = 2;
		for (int i = 0; i < 11; i++) {
			Shipb newship = new Shipb(ship_3, COURT_WIDTH, COURT_HEIGHT, 2 + gamelevel, 0,
					shipx, 125, 25);
			shipr4.add(newship);
			shipx += 35;
		}
		
		playing = true;
		status.setText("Score: " + points);
		score.setText("Lives: " + lives);
		level.setText("Level: " + gamelevel);

		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}
	
    /**
     * This method is called every time the timer defined
     * in the constructor triggers.
     */
	void tick(){
		if (playing) {
			dircounter++;
			ycounter++;
			
			square.move();
			
			for (int i = 0; i < shipr0.size(); i++) {
				shipr0.get(i).move();
			}
			
			for (int i = 0; i < shipr1.size(); i++) {
				shipr1.get(i).move();	
			}
			
			for (int i = 0; i < shipr2.size(); i++) {
				shipr2.get(i).move();	
			}
			
			for (int i = 0; i < shipr3.size(); i++) {
				shipr3.get(i).move();	
			}
			
			for (int i = 0; i < shipr4.size(); i++) {
				shipr4.get(i).move();	
			}
		
			if (bflag == 1) {
				for (int i = 0; i < bullet.size(); i++) {
					bullet.get(i).move();
				}
			}

			//make ships move from right wall to left wall
			if ((dircounter * (2 + gamelevel)) >= 120) {
				if (changedir) {	
				for (int i = 0; i < shipr0.size(); i++) {
					shipr0.get(i).bounce(Direction.RIGHT);
				}
				for (int i = 0; i < shipr1.size(); i++) {
					shipr1.get(i).bounce(Direction.RIGHT);
				}
				for (int i = 0; i < shipr2.size(); i++) {
					shipr2.get(i).bounce(Direction.RIGHT);
				}
				for (int i = 0; i < shipr3.size(); i++) {
					shipr3.get(i).bounce(Direction.RIGHT);
				}
				for (int i = 0; i < shipr4.size(); i++) {
					shipr4.get(i).bounce(Direction.RIGHT);
				}
				} else {
					for (int i = 0; i < shipr0.size(); i++) {
						shipr0.get(i).bounce(Direction.LEFT);
					}
					for (int i = 0; i < shipr1.size(); i++) {
						shipr1.get(i).bounce(Direction.LEFT);
					}
					for (int i = 0; i < shipr2.size(); i++) {
						shipr2.get(i).bounce(Direction.LEFT);
					}
					for (int i = 0; i < shipr3.size(); i++) {
						shipr3.get(i).bounce(Direction.LEFT);
					}
					for (int i = 0; i < shipr4.size(); i++) {
						shipr4.get(i).bounce(Direction.LEFT);
					}
				}
				
				changedir = !changedir;
				dircounter = 0;
			}
			
			if (ycounter >= (200 - (gamelevel * 20))) {
				for (int i = 0; i < shipr0.size(); i++) {
					shipr0.get(i).pos_y = shipr0.get(i).pos_y + 30;
				}
				for (int i = 0; i < shipr1.size(); i++) {
					shipr1.get(i).pos_y = shipr1.get(i).pos_y + 30;
				}
				for (int i = 0; i < shipr2.size(); i++) {
					shipr2.get(i).pos_y = shipr2.get(i).pos_y + 30;
				}
				for (int i = 0; i < shipr3.size(); i++) {
					shipr3.get(i).pos_y = shipr3.get(i).pos_y + 30;
				}
				for (int i = 0; i < shipr4.size(); i++) {
					shipr4.get(i).pos_y = shipr4.get(i).pos_y + 30;
				}
				ycounter = 0;
			}
			ufocounter++;
			if ((ufocounter > (500 - (gamelevel * 20))) && 
					(ufocounter <= (750 - (gamelevel * 20)))) {
				ufo.move();
				if (ufocounter == (750 - (gamelevel * 20))) {
					ufocounter = 400;
					ufo.pos_x = -40;
					uflag = 0;
					
				}
				if (ufo.pos_x >= ufo.max_x) {
					uflag = 1;
					ufo.pos_x = 600;
				} else if ((ufocounter % (15 - gamelevel)) == 0) {
					uflag = 1;
					UBullet ubull = ufo.shoot(COURT_WIDTH, COURT_HEIGHT, 
							ufo.pos_x + 20, ufo.pos_y + 20);
					ubull.v_y = 6 + gamelevel;
					ubullet.add(ubull);
				}
				if (uflag == 1) {
					for (int i = 0; i < ubullet.size(); i++) {
						ubullet.get(i).move();
					}
				}
				
			}
		
			// check for the game end conditions
			for (int j = 0; j < ubullet.size(); j++) {
				if (((ubullet.get(j).intersects(square)) &&
					(lflag == true))) {
					if (lives == 1) {
						lflag = false;
						playing = false;
						gameover = true;
						gamelevel = 0;
						score.setText("Lives: 0");
						status.setText("You lose!");
					} else {
						lflag = false;
						lives--;
						playing = false;
						reset();
					}  
				}
			}
			
			for (int j = 0; j < shipr0.size(); j++) {
				if (((shipr0.get(j).intersects(square)) &&
					(lflag == true)) || 
					(shipr0.get(j).pos_y >= shipr0.get(j).max_y)) {
					if (lives == 1) {
						lflag = false;
						playing = false;
						gameover = true;
						gamelevel = 0;
						score.setText("Lives: 0");
						status.setText("You lose!");
					} else {
						lflag = false;
						lives--;
						playing = false;
						reset();
					}  
				}
			}
				for (int j = 0; j < shipr1.size(); j++) {
					if (((shipr1.get(j).intersects(square)) &&
						(lflag == true)) || 
						(shipr1.get(j).pos_y >= shipr1.get(j).max_y)) {
						if (lives == 1) {
							lflag = false;
							playing = false;
							gameover = true;
							gamelevel = 0;
							score.setText("Lives: 0");
							status.setText("You lose!");
						} else {
							lflag = false;
							lives--;
							playing = false;
							reset();
					}  
					
					}
				}
				for (int j = 0; j < shipr2.size(); j++) {
						if (((shipr2.get(j).intersects(square)) &&
								(lflag == true)) || 
								(shipr2.get(j).pos_y >= shipr2.get(j).max_y)){
							if (lives == 1) {
								lflag = false;
								playing = false;
								gameover = true;
								gamelevel = 0;
								score.setText("Lives: 0");
								status.setText("You lose!");
							} else {
								lflag = false;
								lives--;
								playing = false;
								reset();
							}  
							
						}
				}
				for (int j = 0; j < shipr3.size(); j++) {
					if (((shipr3.get(j).intersects(square)) &&
						(lflag == true)) || 
						(shipr3.get(j).pos_y >= shipr3.get(j).max_y)) {
						if (lives == 1) {
							lflag = false;
							playing = false;
							gameover = true;
							gamelevel = 0;
							score.setText("Lives: 0");
							status.setText("You lose!");
						} else {
							lflag = false;
							lives--;
							playing = false;
							reset();
						} } }
				for (int j = 0; j < shipr4.size(); j++) {
					if (((shipr4.get(j).intersects(square)) &&
						(lflag == true)) || 
						(shipr4.get(j).pos_y >= shipr4.get(j).max_y)) {
						if (lives == 1) {
							lflag = false;
							playing = false;
							gameover = true;
							gamelevel = 0;
							score.setText("Lives: 0");
							status.setText("You lose!");
						} else {
							lflag = false;
							lives--;
							playing = false;
							reset();
						} } }

		boolean deadships = ((shipr0.size() == 0) && (((shipr1.size() == 0) && 
				(shipr2.size() == 0)) && ((shipr3.size() == 0) 
				&& (shipr4.size() == 0))));
		
		if (deadships && (clearflag == false)) {
			playing = false;
			if (gamelevel == 10) {
				status.setText("YOU WIN!!!");
				gamelevel = 0;
			} else {
				gamelevel++;
				reset();
			}
			

		}
		
		
		//check for destroying ships
		if (bflag == 1) {
			for (int i = 0; i < bullet.size(); i++) {
				int flag = 0;
				Bullet b = bullet.get(i);
				
				for (int j = 0; j < shipr4.size(); j++) {
					if (b.intersects(shipr4.get(j))) { 	
						shipr4.remove(j);
						bullet.remove(i);
						flag = 1;
						points += 10;
						status.setText("Score: " + points);
						break;
					} 
				}
				
				if (flag == 1) continue;
				flag = 0;
				for (int j = 0; j < shipr3.size(); j++) {
					if (b.intersects(shipr3.get(j))) { 	
						shipr3.remove(j);
						bullet.remove(i);
						flag = 1;
						points += 10;
						status.setText("Score: " + points);
						break;
					} 
				}
				
				if (flag == 1) continue;
				flag = 0;
				
				for (int j = 0; j < shipr2.size(); j++) {
						if (b.intersects(shipr2.get(j))) { 	
							shipr2.remove(j);
							bullet.remove(i); 
							flag = 1;
							points += 20; //remember to have different points for different things
							status.setText("Score: " + points);
							break;
						}
				}
				if (flag == 1) continue;
				flag = 0;
				for (int j = 0; j < shipr1.size(); j++) {
					if (b.intersects(shipr1.get(j))) { 	
						shipr1.remove(j);
						bullet.remove(i);
						flag = 1;
						points += 20;
						status.setText("Score: " + points);
						break;
					} 
				}
				
				if (flag == 1) continue;
				flag = 0;
				for (int j = 0; j < shipr0.size(); j++) {
					if (b.intersects(shipr0.get(j))) { 	
						shipr0.remove(j);
						bullet.remove(i);
						flag = 1;
						points += 40;
						status.setText("Score: " + points);
						break;
					} 
				}
				
				if (flag == 1) continue;
				flag = 0;
				
				if (b.intersects(ufo)) {
					bullet.remove(i);
					flag = 1;
					points += 100;
					status.setText("Score: " + points);
				}
				
			}
		}
		
		for (int i = 0; i < bullet.size(); i++) {
			if (bullet.get(i).pos_y <= 0) {
				bullet.remove(i);
			}
		}
		
		for (int i = 0; i < ubullet.size(); i ++) {
			if (ubullet.get(i).pos_y >= ubullet.get(i).max_y) {
				ubullet.remove(i);
			}
		}
			// update the display
			repaint();
			}
	}

	@Override 
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		setBackground(Color.black);
		
		if (bflag == 1) {
			for (int i = 0; i < bullet.size(); i++) {
				bullet.get(i).draw(g);
			}
		}
		square.draw(g);
		ufo.draw(g);
		
		for (int i = 0; i < shipr0.size(); i++) {
			shipr0.get(i).draw(g);
		}
		

		for (int i = 0; i < shipr1.size(); i++) {
			shipr1.get(i).draw(g);
		}
		
		for (int i = 0; i < shipr2.size(); i++) {
			shipr2.get(i).draw(g);
		} 
		
		for (int i = 0; i < shipr3.size(); i++) {
			shipr3.get(i).draw(g);
		}
		
		for (int i = 0; i < shipr4.size(); i++) {
			shipr4.get(i).draw(g);
		}
		
		if (uflag == 1) {
			for (int i = 0; i < ubullet.size(); i++) {
				ubullet.get(i).draw(g);
			}
		}
		
	}

	@Override
	public Dimension getPreferredSize(){
		return new Dimension(COURT_WIDTH,COURT_HEIGHT);
	}
	
	public void clearCanvas() {
		bullet.clear();
		ubullet.clear();
		shipr0.clear();
		shipr1.clear();
		shipr2.clear();
		shipr3.clear();
		shipr4.clear();
		clearflag = true;	
	}
}
