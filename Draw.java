import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.*;

public class Draw extends JComponent{

	private BufferedImage image;
	private BufferedImage backgroundImage;
	public URL resource = getClass().getResource("run0.png");

	public Random randomizer = new Random();
	
	public boolean runback = false;
	public boolean right = true;
    public boolean notMoving = true;
	// circle's position
	public int x = 30;
	public int y = 30;

	// animation states
	public int state = 0;

	LinkedList<Monster> monsterList = new LinkedList<Monster>();

	Monster monster1;
	Monster monster2;
	Monster monster3;

	Dragon dragon1;
	Dragon dragon2;
	Dragon dragon3;


	public Draw(){
		monster1 = new Monster(200, 200);
		monster2 = new Monster(300, 200);
		monster3 = new Monster(400, 200);

		dragon1 = new Dragon(300, 200);
		dragon2 = new Dragon(200, 300);
		dragon3 = new Dragon(200, 400);

		monsterList.add(monster1);

		try{
			image = ImageIO.read(resource);
			backgroundImage = ImageIO.read(getClass().getResource("background.jpg"));
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	public void addMonster(){
		Monster monsterCreated = new Monster(randomizer.nextInt(400), randomizer.nextInt(400));
		monsterList.add(monsterCreated);
		this.repaint();
	}

	public void reloadImage(){
		state++;

		if(state == 0){
			resource = getClass().getResource("run0.png");
		}
		else if(state == 1){
			resource = getClass().getResource("run1.png");
		}
		else if(state == 2){
			resource = getClass().getResource("run2.png");
		}
		else if(state == 3){
			resource = getClass().getResource("run3.png");
		}
		else if(state == 4){
			resource = getClass().getResource("run4.png");
		}
		else if(state == 5){
			resource = getClass().getResource("run5.png");
			state = 0;
		}

		try{
			image = ImageIO.read(resource);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	public void reloadImage1(){
        state++;
        runback = true;
        if(state == 0){
            resource = getClass().getResource("runback0.png");
        }
        else if(state == 1){
            resource = getClass().getResource("runback1.png");
        }
        else if(state == 2){
            resource = getClass().getResource("runback2.png");
        }
        else if(state == 3){
            resource = getClass().getResource("runback3.png");
        }
        else if(state == 4){
            resource = getClass().getResource("runback4.png");
        }
        else if(state == 5){
            resource = getClass().getResource("runback5.png");
            state = 0;
        }

        try{ 
            image = ImageIO.read(resource);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

	public void attackAnimation(){
		Thread thread1 = new Thread(new Runnable(){
			public void run(){
				for(int ctr = 0; ctr < 5; ctr++){
					try {
						if(ctr==4){
							resource = getClass().getResource("run0.png");
						}
						else{
							resource = getClass().getResource("attack"+ctr+".png");
						}
						
						try{
							image = ImageIO.read(resource);
						}
						catch(IOException e){
							e.printStackTrace();
						}

						checkCollision();
						for(Monster monster : monsterList){
							if(monster.collided){
								monster.health = monster.health - 10;
							}
						}

				        repaint();
				        Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		thread1.start();
	}

	public void attack(){
		attackAnimation();
	}

	public void moveUp(){
		y = y - 5;
		reloadImage();
		repaint();
	}

	public void moveDown(){
		y = y + 5;
		reloadImage();
		repaint();
	}


	public void moveLeft(){
        notMoving = false;
        right = false;
        x = x - 5;
        reloadImage1();
        repaint();
    }

    public void checkCollision(){
    	Rectangle playerBounds = new Rectangle(x, y, image.getWidth(), image.getHeight());
    	for(Monster monsters: monsterList){
    		if(playerBounds.intersects(monsters.getBounds())){
    			monsters.collided = true;
    		}
    	}
    }

    public void moveRight(){
        notMoving = false;
        right = true;
        x = x + 5;
        reloadImage();
        repaint();
       
    }
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.YELLOW);
		g.drawImage(backgroundImage, 0, 0, this);
		g.drawImage(image, x, y, this);

		g.drawImage(monster1.image, monster1.xPos, monster1.yPos, this);
		g.drawImage(monster2.image, monster2.xPos, monster2.yPos, this);
		g.drawImage(monster3.image, monster3.xPos, monster3.yPos, this);

		for(Monster monster:monsterList){
			g.drawImage(monster.image, monster.xPos, monster.yPos, this);
			g.setColor(Color.GREEN);
			g.fillRect(monster.xPos, monster.yPos-5, monster.health, 5);
		}

		g.drawImage(dragon1.image, dragon1.xPos, dragon1.yPos, this);
		g.drawImage(dragon2.image, dragon2.xPos, dragon2.yPos, this);
		g.drawImage(dragon3.image, dragon3.xPos, dragon3.yPos, this);
	}
}