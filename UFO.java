import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/** A basic game object displayed as a yellow circle, starting in the 
 * upper left corner of the game court.
 *
 */
public class UFO extends GameObj {
	public static final String img_file = "UFO.jpg";
	public static final String burst_file = "burst.jpg";
	
	private static BufferedImage img;

	public UFO(int courtWidth, int courtHeight, int INIT_VEL_X, int INIT_VEL_Y, 
			 int INIT_X, int INIT_Y, int SIZE) {
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
	
    public UBullet shoot(int courtWidth, int courtHeight, int POS_X, int POS_Y) {
    	UBullet ub = new UBullet(courtWidth, courtHeight, POS_X, POS_Y);
    	return ub;
    }

	@Override
	public void draw(Graphics g) {
		g.drawImage(img, pos_x, pos_y, width, height, null); 
	}
	
	
	
}