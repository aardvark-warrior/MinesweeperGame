/**
 * @(#)MineSweeper2.java
 *
 *
 * @author Arthur Wang
 * @version 2.00 2019/4/28
 */
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;


public class MineSweeper2 extends Applet implements MouseListener			//applet MineSweeper2 uses MouseListener to detect left and right clicks
{
   	Image offScreen;
   	Graphics offG;

   	Image mineL, mineW;
   	AudioClip win, lose;
   	Image grid;		//variable holds image of unopened grid
   	Image flag;		//variable holds image of flag
   	Image zero, one, two, three, four, five, six, seven, eight;		//variable holds images for different number grids; zero holds image of blank opened grid
	Image number;													//variable holds different image variables (row above)

	boolean click; //click = true if rightclick, click = flase if leftclick

	Image G0,G1,G2,G3,G4,G5,G6,G7,G8,G9,G10,G11,G12,G13,G14,G15,G16,G17,G18,G19,G20,G21,G22,G23,G24,G25;	//the image assigned to be drawn in each grid

	//height and width of screen
	final int dimensionX = 500;
	final int dimensionY = 500;
	//number of rows and columns in grid system
	final int	HEIGHT = 4;
	final int	 WIDTH = 4;

    int[][] grids = new int[HEIGHT+1][WIDTH+1];	//int array holds back-up copy of array below (to avoid recounting grids when determining numbers and bombs
    int[][] GRIDS = new int[HEIGHT+1][WIDTH+1];	//int array holds an integer for each grid
	Image[][] gridImage = new Image[HEIGHT+1][WIDTH+1];
	Image[] numbers = new Image[8];

    int count;					//count number of bombs surrounding number grids

	int over = 0;
	int remBombs = 0;


    public void init()
	{
        offScreen = createImage(dimensionX,dimensionY);
        offG = offScreen.getGraphics();

		addMouseListener(this);

		//form grid system; assigns every grid the value of zero
		for (int X=0; X<=WIDTH; X++)
		{
			for (int Y=0; Y<=HEIGHT; Y++){
				GRIDS[X][Y]=0;
				//System.out.println("GRIDS[" + X + "]" + "[" + Y + "]" + " = " + GRIDS[X][Y]);
			}
		}
		System.out.println("	");

		//randomly select bomb location FOUR times
		Random rand = new Random();
		int row = rand.nextInt(5);
		int col = rand.nextInt(5);

		for (int i=0; i<4; i++)
		{
			System.out.println("Bomb selection:");
			while(GRIDS[row][col]==-1){
				row = rand.nextInt(5);
				col = rand.nextInt(5);
			}
			GRIDS[row][col] = -1;
			System.out.println("grids[" + row + "]" + "[" + col + "]" + " = " + GRIDS[row][col]);
			remBombs++;
   		}
   		System.out.println("	");

		//copy grid-values to backup grid
   		//System.out.println("All grids");
   		for (int X=0; X<=WIDTH; X++){
			for (int Y=0; Y<=HEIGHT; Y++){
				grids[X][Y] = GRIDS[X][Y];
				//System.out.println("GRIDS[" + X + "]" + "[" + Y + "]" + " = " + GRIDS[X][Y]);
			}
		}
		System.out.println("	");

		//count numbers of bombs around number grids
		for (int XX=0; XX<=WIDTH; XX++)
		{
			for (int YY=0; YY<=HEIGHT; YY++){
				if (GRIDS[XX][YY]!=-1){
					if (XX==0 && YY==0){	//top left(g0)
						count = (GRIDS[XX+1][YY]+GRIDS[XX+1][YY+1]+GRIDS[XX][YY+1]);
						//System.out.println("g0 =" + count);
					}
					else if (XX==0 && YY==HEIGHT){		//bottom left
						count = (GRIDS[XX][YY-1]+GRIDS[XX+1][YY-1]+GRIDS[XX+1][YY]);
						//System.out.println("g6 =" + count);
					}
					else if (XX==WIDTH && YY==0){		//top right
						count = (GRIDS[XX-1][YY]+GRIDS[XX-1][YY+1]+GRIDS[XX][YY+1]);
						//System.out.println("g2 =" + count);
					}
					else if (XX==WIDTH && YY==HEIGHT){	//bottom right
						count = (GRIDS[XX][YY-1]+GRIDS[XX-1][YY-1]+GRIDS[XX-1][YY]);
						//System.out.println("g8 =" + count);
					}
					else if (XX==0 && YY>0 && YY<HEIGHT){					//left edge
						count = (GRIDS[XX][YY-1]+GRIDS[XX+1][YY-1]+GRIDS[XX+1][YY]+GRIDS[XX+1][YY+1]+GRIDS[XX][YY+1]);
						//System.out.println("g3 =" + count);
					}
					else if (YY==0 && XX>0 && XX<WIDTH){					//top edge
						count = (GRIDS[XX-1][YY]+GRIDS[XX-1][YY+1]+GRIDS[XX][YY+1]+GRIDS[XX+1][YY+1]+GRIDS[XX+1][YY]);
						//System.out.println("g1 =" + count);
					}
					else if (XX==WIDTH && YY>0 && YY<HEIGHT){				//right edge
						count = (GRIDS[XX][YY-1]+GRIDS[XX-1][YY-1]+GRIDS[XX-1][YY]+GRIDS[XX-1][YY+1]+GRIDS[XX][YY+1]);
						//System.out.println("g5 =" + count);
					}
					else if (YY==HEIGHT && XX>0 && XX<WIDTH){				//bottom edge
						count = (GRIDS[XX-1][YY]+GRIDS[XX-1][YY-1]+GRIDS[XX][YY-1]+GRIDS[XX+1][YY-1]+GRIDS[XX+1][YY]);
						//System.out.println("g7 =" + count);
					}
					else if (XX>0 && XX<WIDTH && YY>0 && YY<HEIGHT){								//middle grids
						count = (GRIDS[XX+1][YY]+GRIDS[XX+1][YY+1]+GRIDS[XX][YY+1]+GRIDS[XX-1][YY+1]+GRIDS[XX-1][YY]+GRIDS[XX-1][YY-1]+GRIDS[XX][YY-1]+GRIDS[XX+1][YY-1]);
						//System.out.println("g4 =" + count);
					}

					grids[XX][YY] = count*-1;
					//System.out.println("GRIDS[" + XX + "][" + YY + "] = " + GRIDS[XX][YY]);
					count = 0;
	        	}
			}
		}

		System.out.println("All Grids Final");
		for (int X=0; X<=WIDTH; X++){
			for (int Y=0; Y<=HEIGHT; Y++){
				System.out.println("grids[" + X + "]" + "[" + Y + "]" + " = " + grids[X][Y]);
			}
		}

		//track media
        win = getAudioClip(getCodeBase(), "disarmed.au");
		lose = getAudioClip(getCodeBase(), "explode.au");
        mineW = getImage(getCodeBase(), "mine.jpg");
        mineL = getImage(getCodeBase(), "mine1.jpg");
        grid = getImage(getCodeBase(), "grid.jpg");
        flag = getImage(getCodeBase(), "flag.jpg");
        zero = getImage(getCodeBase(), "zero.jpg");
        one = getImage(getCodeBase(), "one.jpg");
        two = getImage(getCodeBase(), "two.jpg");
        three = getImage(getCodeBase(), "three.jpg");
        four = getImage(getCodeBase(), "four.jpg");
        five = getImage(getCodeBase(), "five.jpg");
        six = getImage(getCodeBase(), "six.jpg");
        seven = getImage(getCodeBase(), "seven.jpg");
        eight = getImage(getCodeBase(), "eight.jpg");

        numbers[0] = one;
        numbers[1] = two;
        numbers[2] = three;
        numbers[3] = four;
        numbers[4] = five;
        numbers[5] = six;
        numbers[6] = seven;
        numbers[7] = eight;

        G0 = grid;	G1 = grid;	G2 = grid;	G3 = grid;	G4 = grid;
		G5 = grid;	G6 = grid;	G7 = grid;	G8 = grid;	G9 = grid;
		G10 = grid;	G11 = grid;	G12 = grid;	G13 = grid;	G14 = grid;
		G15 = grid;	G16 = grid;	G17 = grid;	G18 = grid;	G19 = grid;
		G20 = grid;	G21 = grid;	G22 = grid;	G23 = grid;	G24 = grid;

        MediaTracker tracker = new MediaTracker(this);  //track loading of pics

		tracker.addImage(mineW, 0);
		tracker.addImage(mineL,0);
		tracker.addImage(grid, 0);
		tracker.addImage(flag, 0);
		tracker.addImage(zero, 0);
		tracker.addImage(one, 0);
		tracker.addImage(two, 0);
		tracker.addImage(three, 0);
		tracker.addImage(four, 0);
		tracker.addImage(five, 0);
		tracker.addImage(six, 0);
		tracker.addImage(seven, 0);
		tracker.addImage(eight, 0);

		//Wait for pictures to complete loading
		while(tracker.checkAll(true) != true){ }
		//Check if trouble loading pics
		if (tracker.isErrorAny()){
			JOptionPane.showMessageDialog(null, "Trouble loading pictures.");
		}
		//MediaTracker end


		//initialize gridImage array components
		gridImage[0][0] = G0;
		gridImage[0][1] = G1;
		gridImage[0][2] = G2;
		gridImage[0][3] = G3;
		gridImage[0][4] = G4;

		gridImage[1][0] = G5;
		gridImage[1][1] = G6;
		gridImage[1][2] = G7;
		gridImage[1][3] = G8;
		gridImage[1][4] = G9;

		gridImage[2][0] = G10;
		gridImage[2][1] = G11;
		gridImage[2][2] = G12;
		gridImage[2][3] = G13;
		gridImage[2][4] = G14;

		gridImage[3][0] = G15;
		gridImage[3][1] = G16;
		gridImage[3][2] = G17;
		gridImage[3][3] = G18;
		gridImage[3][4] = G19;

		gridImage[4][0] = G20;
		gridImage[4][1] = G21;
		gridImage[4][2] = G22;
		gridImage[4][3] = G23;
		gridImage[4][4] = G24;

		for (int x=0; x<=WIDTH; x++)
		{
			for (int y=0; y<=HEIGHT; y++)
			{
				if (grids[x][y]==0){
					gridImage[x][y]=zero;
				}
			}
		}

		for (int x=0; x<=WIDTH; x++)
		{
			for (int y=0; y<=HEIGHT; y++)
			{
				offG.drawImage(gridImage[x][y],x*100,y*100,100,100,this);
			}
		}
    }	//initialize END

    public void paint(Graphics g)				//draw offScreen
    {
        g.drawImage(offScreen, 0, 0, this);
    }

    public void mouseClicked(MouseEvent me)
    {
		if (over==0)
		{
	    	int xpos = me.getX();
			int ypos = me.getY();
			int gX=0, gY=0;				//x and y values for 2-D arrays: e.g. GRIDS[gX][gY]

			gX = gridX(xpos);
			gY = gridY(ypos);

			xpos = drawX(xpos);
		    ypos = drawY(ypos);

	       	if(me.getButton() == MouseEvent.BUTTON1) {
				drawNum(xpos,ypos,gX,gY);
				click = false;
		    }

		    if(me.getButton() == MouseEvent.BUTTON3) {
		    	System.out.println("Right-click");
		    		click = true;
					if	(gridImage[gX][gY]==grid || gridImage[gX][gY]==flag)	//only operates if the grid is not already a number or bomb
					{
						drawFlag(xpos,ypos,gX,gY);
					}
					else if (gridImage[gX][gY]!=grid && grids[gX][gY]>=1 && grids[gX][gY]<=8)
					{
						int flagcount = countFlag(gX,gY);
						if (grids[gX][gY]==flagcount)
						{
							System.out.println("Open grids");
							System.out.println("	");
							for (int x=gX-1; x<gX+1; x++)
							{
								for (int y=gY-1; y<gY+1; y++)
								{
									drawNum(x*100, y*100, x, y);
								}
							}
						}
					}
			}
		}//if statement end
		repaint();
    }//mouseClicked() end

	public void drawFlag(int xpos, int ypos, int gX, int gY)
	{
		int bomb = -1;		//sentinel
		if (gridImage[gX][gY]==grid)
			{
				gridImage[gX][gY] = flag;
				if (GRIDS[gX][gY]==bomb)			//if the flagged grid is a bomb-grid...
				{
					remBombs--;
				}
				if (remBombs==0)
				{
					over = 2;
					endScreen();
				}
			}
			else if (gridImage[gX][gY]==flag)
			{
				if (grids[gX][gY]==bomb)			//if the unflagged grid is a bomb-grid...
				{
					remBombs++;
				}
				gridImage[gX][gY] = grid;
			}
	    offG.drawImage(gridImage[gX][gY],xpos,ypos,100,100,this);
	    repaint();
	}

	public void drawNum(int xpos, int ypos, int gX, int gY)
	{
		int bomb = -1;//sentinel
		if (grids[gX][gY]!=bomb)
			{
				if(grids[gX][gY]==1){
					//number = one;
					gridImage[gX][gY] = numbers[0];
				}
				else if(grids[gX][gY]==2){
					//number = two;
					gridImage[gX][gY] = numbers[1];
				}
				else if(grids[gX][gY]==3){
					//number = three;
					gridImage[gX][gY] = numbers[2];
				}
				else if(grids[gX][gY]==4){
					//number = four;
					gridImage[gX][gY] = numbers[3];
				}
				else if(grids[gX][gY]==5){
					//number = five;
					gridImage[gX][gY] = numbers[4];
				}
				else if(grids[gX][gY]==6){
					//number = six;
					gridImage[gX][gY] = numbers[5];
				}
				else if(grids[gX][gY]==7){
					//number = seven;
					gridImage[gX][gY] = numbers[6];
				}
				else if(grids[gX][gY]==8){
					//number = eight;
					gridImage[gX][gY] = numbers[7];
				}
				else if(grids[gX][gY]==0){
					gridImage[gX][gY] = zero;
				}
			}
			else if (grids[gX][gY]==bomb && click==false || grids[gX][gY]==bomb && gridImage[gX][gY]!=flag && click==true)
			{
				over = 1;									//set gamemode to losing conditions
				endScreen();								//display end screen
			}
		offG.drawImage(gridImage[gX][gY],xpos,ypos,100,100,this);
	}

	public static int gridX(int xpos){
		int gX = 0;
		if (xpos>0 && xpos<100)
			{
				gX = 0;
			}
			else if (xpos>100 && xpos<200)
			{
				gX = 1;
			}
			else if (xpos>200 && xpos<300)
			{
				gX = 2;
			}
			else if (xpos>300 && xpos<400)
			{
				gX = 3;
			}
			else if (xpos>400 && xpos<500)
			{
				gX = 4;
			}
		return gX;
	}

	public static int gridY(int ypos){
		int gY = 0;

		if (ypos>0 && ypos<100)
			{
				gY = 0;
			}
			else if (ypos>100 && ypos<200)
			{
				gY = 1;
			}
			else if (ypos>200 && ypos<300)
			{
				gY = 2;
			}
			else if (ypos>300 && ypos<400)
			{
				gY = 3;
			}
			else if (ypos>400 && ypos<500)
			{
				gY = 4;
			}
		return gY;
	}

	public static int drawX(int xpos){
		if (xpos>0 && xpos<100)
			{
				xpos = 0;
			}
			else if (xpos>100 && xpos<200){
				xpos = 100;
			}
			else if (xpos>200 && xpos<300){
				xpos = 200;
			}
			else if (xpos>300 && xpos<400){
				xpos = 300;
			}
			else if (xpos>400 && xpos<500){
				xpos = 400;
			}
			return xpos;
	}

	public static int drawY(int ypos){
		if (ypos>0 && ypos<100)
			{
				ypos = 0;
			}
			else if (ypos>100 && ypos<200){
				ypos = 100;
			}
			else if (ypos>200 && ypos<300){
				ypos = 200;
			}
			else if (ypos>300 && ypos<400){
				ypos = 300;
			}
			else if (ypos>400 && ypos<500){
				ypos = 400;
			}
			return ypos;
	}

	public int countFlag(int gX, int gY)
   	{
   		int count = 0;
				if (gX==0 && gY==0){	//top left(g0)
					System.out.println("Top left");
					if (gridImage[gX+1][gY]==flag)
						count++;
					if (gridImage[gX+1][gY+1]==flag)
						count++;
					if (gridImage[gX][gY+1]==flag)
						count++;
				}
				else if (gX==0 && gY==HEIGHT){		//bottom left
					System.out.println("Bottom left");
					if (gridImage[gX][gY-1]==flag)
						count++;
					if (gridImage[gX+1][gY-1]==flag)
						count++;
					if (gridImage[gX+1][gY]==flag)
						count++;
				}
				else if (gX==WIDTH && gY==0){		//top right
					System.out.println("Top right");
					if (gridImage[gX-1][gY]==flag)
						count++;
					if (gridImage[gX-1][gY+1]==flag)
						count++;
					if (gridImage[gX][gY+1]==flag)
						count++;
				}
				else if (gX==WIDTH && gY==HEIGHT){	//bottom right
					System.out.println("Bottom right");
					if (gridImage[gX][gY-1]==flag)
						count++;
					if (gridImage[gX-1][gY-1]==flag)
						count++;
					if (gridImage[gX-1][gY]==flag)
						count++;
				}
				else if (gX==0 && gY>0 && gY<HEIGHT){					//left edge
					System.out.println("Left edge");
					if (gridImage[gX][gY-1]==flag)
						count++;
					if (gridImage[gX+1][gY-1]==flag)
						count++;
					if (gridImage[gX+1][gY]==flag)
						count++;
					if (gridImage[gX+1][gY+1]==flag)
						count++;
					if (gridImage[gX][gY+1]==flag)
						count++;
				}
				else if (gY==0 && gX>0 && gX<WIDTH){					//top edge
					System.out.println("Top edge");
					if (gridImage[gX-1][gY]==flag)
						count++;
					if (gridImage[gX-1][gY+1]==flag)
						count++;
					if (gridImage[gX][gY+1]==flag)
						count++;
					if (gridImage[gX+1][gY+1]==flag)
						count++;
					if (gridImage[gX+1][gY]==flag)
						count++;
				}
				else if (gX==WIDTH && gY>0 && gY<HEIGHT){				//right edge
					System.out.println("Right edge");
					if (gridImage[gX][gY-1]==flag)
						count++;
					if (gridImage[gX-1][gY-1]==flag)
						count++;
					if (gridImage[gX-1][gY]==flag)
						count++;
					if (gridImage[gX-1][gY+1]==flag)
						count++;
					if (gridImage[gX][gY+1]==flag)
						count++;
				}
				else if (gY==HEIGHT && gX>0 && gX<WIDTH){				//bottom edge
					System.out.println("Bottom edge");
					if (gridImage[gX-1][gY]==flag)
						count++;
					if (gridImage[gX-1][gY-1]==flag)
						count++;
					if (gridImage[gX][gY-1]==flag)
						count++;
					if (gridImage[gX+1][gY-1]==flag)
						count++;
					if (gridImage[gX+1][gY]==flag)
						count++;
				}
				else if (gX>0 && gX<WIDTH && gY>0 && gY<HEIGHT){								//middle gridImage
					System.out.println("Middle");
					if (gridImage[gX+1][gY]==flag)
						count++;
					if (gridImage[gX+1][gY+1]==flag)
						count++;
					if (gridImage[gX][gY+1]==flag)
						count++;
					if (gridImage[gX-1][gY+1]==flag)
						count++;
					if (gridImage[gX-1][gY]==flag)
						count++;
					if (gridImage[gX-1][gY-1]==flag)
						count++;
					if (gridImage[gX][gY-1]==flag)
						count++;
					if (gridImage[gX+1][gY-1]==flag)
						count++;
				}
				System.out.println("Flags in vicinity: " + count);
				System.out.println("Grid number: " + grids[gX][gY]);
				return count;
   	}


	public void endScreen()				//opens all grids and disables mouse clicks one the game is over
	{
		System.out.println("gameover");
		int bomb = -1; 					//sentinel
		if (over==1||over==2)
		{
			for (int x=0; x<=WIDTH; x++)
			{
				for (int y=0; y<=HEIGHT; y++)
				{
					if (grids[x][y]!=bomb)
					{
						if(grids[x][y]==1){
							number = one;
						}
						else if(grids[x][y]==2){
							number = two;
						}
						else if(grids[x][y]==3){
							number = three;
						}
						else if(grids[x][y]==4){
							number = four;
						}
						else if(grids[x][y]==5){
							number = five;
						}
						else if(grids[x][y]==6){
							number = six;
						}
						else if(grids[x][y]==7){
							number = seven;
						}
						else if(grids[x][y]==8){
							number = eight;
						}
						else if(grids[x][y]==0){
							number = zero;
						}
						gridImage[x][y] = number;
					}
					else if (grids[x][y]==bomb)
					{
						if (over==1)
						{
							gridImage[x][y] = mineL;
							lose.play();								//play explosion sound; gameover
						}
						else if (over==2)
						{
							gridImage[x][y] = mineW;
							win.play();
						}


					}
					offG.drawImage(gridImage[x][y],x*100,y*100,100,100,this);
				}
			}
		}
	}	//endScreen() END



	public void mousePressed(MouseEvent me) { }
	public void mouseReleased(MouseEvent me) { }
    public void mouseEntered(MouseEvent me) { }
	public void mouseExited(MouseEvent me) { }

}
