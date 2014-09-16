/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import javax.imageio.ImageIO;

/** A basic game object displayed as a green ship, starting in the 
 * bottom middle of the game court. Player ship.
 *
 */
public class Square extends GameObj {
	public static final int SIZE = 30;
	public static final int INIT_X = 235;
	public static final int INIT_Y = 470;
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;
	
public static final String img_file = "player_ship.jpg";
	
	private static BufferedImage img;
    /** 
     * Note that because we don't do anything special
     * when constructing a Square, we simply use the
     * superclass constructor called with the correct parameters 
     */
    public Square(int courtWidth, int courtHeight){
        super(INIT_VEL_X, INIT_VEL_Y, INIT_X, INIT_Y, 
        		SIZE, SIZE, courtWidth, courtHeight);
		try {
			if (img == null) {
				img = ImageIO.read(new File(img_file));
			}
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}
    }
    
    public Bullet shoot(int courtWidth, int courtHeight, int POS_X, int POS_Y) {
    	Bullet b = new Bullet(courtWidth, courtHeight, POS_X, POS_Y);
    	return b;
    }

    @Override
    public void draw(Graphics g) {
    	g.drawImage(img, pos_x, pos_y, width, height, null); 
    }

}
