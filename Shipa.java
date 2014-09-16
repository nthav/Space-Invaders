import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
public class Shipa extends GameObj {

	public static final String burst_file = "burst.jpg";
	
	private static BufferedImage img;

	public Shipa(String img_name, int courtWidth, int courtHeight, int INIT_VEL_X, int INIT_VEL_Y, 
			 int INIT_X, int INIT_Y, int SIZE) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_X, INIT_Y, 
				SIZE, SIZE, courtWidth, courtHeight);
		try {
			if (img == null) {
				img = ImageIO.read(new File(img_name));
			}
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(img, pos_x, pos_y, width, height, null); 
	}
	
	public void burst(int flag) {
		if (flag == 1) {
			try {
				if (img != ImageIO.read(new File(burst_file))) {
					img = ImageIO.read(new File(burst_file));
				}
			} catch (IOException e) {
				System.out.println("Internal Error:" + e.getMessage());
			}
		}
	}
}