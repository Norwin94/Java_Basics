package lab4;

import java.awt.BasicStroke;

/*
 ---------------------------------------------------------------
 
 £ukasz Szumilas, 236068, W04 Inf           26.11.2018r 
 
 Grafy
 
*/

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;
import javax.swing.JPanel;


abstract class Figure{

	static Random random = new Random();
	public Vector<Line> lines;
	private boolean selected = false;
    
	public boolean isSelected() { return selected; }
	public void select() {	selected = true; }
	public void select(boolean z) { selected = z; }
	public void unselect() { selected = false; }

	protected void setColor(Graphics g) {
		if (selected) g.setColor(Color.RED);
		           else g.setColor(Color.BLACK);
	}

	public abstract boolean isInside(float px, float py);
	public boolean isInside(int px, int py) {
		return isInside((float) px, (float) py);
	}
	
	abstract float  getX();
	abstract float  getY();

  abstract float computeArea();
  abstract float computePerimeter();

  abstract void move(float dx, float dy);
  abstract void scale(float s);

  abstract void draw(Graphics g);
  


}

//////////////////////////////////////////////////////////////////////////////


class Point extends Figure{

	protected float x, y;

	Point()
	{ this.x=random.nextFloat()*400;
	  this.y=random.nextFloat()*400;
	}

	Point(float x, float y)
	{ this.x=x;
	  this.y=y;
	}

	@Override
	public boolean isInside(float px, float py) {
		return (Math.sqrt((x - px) * (x - px) + (y - py) * (y - py)) <= 6);
	}

	public boolean isInside1(int px, int py) {
		return false;
	}

	@Override
	float getX() {
		return x;
	}

	@Override
	float getY() {
		return y;
	}

	@Override
  float computeArea(){ return 0; }

	@Override
	float computePerimeter(){ return 0; }

	@Override
  void move(float dx, float dy){ x+=dx; y+=dy; }

	@Override
  void scale(float s){ }

	@Override
  void draw(Graphics g){
		setColor(g);
		g.fillOval((int)(x-3),(int)(y-3), 6,6);
	}
}

//////////////////////////////////////////////////////////////////////////////




class Circle extends Point{
  float r;
 Point un = new Point(x,y);
 Point du;
 //Vector<Line> lines;
 
  Circle(){
      super();
      r=50;
  }

  Circle(float px, float py){
	  
      super(px,py);
      
      r = 50;
      
     lines = new Vector<Line>();
  }
  
  float getR()
  {
	  return r;
  }
  
  Point getPoint()
  {
	  return un;
  }
  

  @Override
	public boolean isInside(float px, float py) {
		return (Math.sqrt((x - px) * (x - px) + (y - py) * (y - py)) <= r);
	}
  
  public boolean isInside1(int px, int py) {
		return false;
	}

  @Override
  float computeArea(){ return (float)Math.PI*r*r; }

  @Override
  float computePerimeter(){ return (float)Math.PI*r*2; }

  @Override
  void scale(float s){ r*=s; }

  @Override
  void draw(Graphics g){
  	setColor(g);
      g.drawOval((int)(x-r), (int)(y-r), (int)(2*r), (int)(2*r));
  }
}

//////////////////////////////////////////////////////////////////////////////

class Line extends Figure{

	
	Point point1, point2;
	
	float r = 50;
	
	Line()
	{
		this.point1 = new Point();
		this.point2 = new Point();
	}
	
	Line(Point un, Point du)
	{
		this.point1 = un;
		this.point2 = du;
	}
	
	@Override
	public boolean isInside(float px, float py) {
		
	    if (((px > point1.getX()) && (px > point2.getX())) || (
	      (px < point1.getX()) && (px < point2.getX()))) return false;
	    if (((py > point1.getY()) && (py > point2.getY())) || (
	      (py < point1.getY()) && (py < point2.getY()))) return false;
	    float A = point2.getY() - point1.getY();
	    float B = point1.getX() - point2.getX();
	    float C = point2.getX() * point1.getY() - point1.getX() * point2.getY();
	    double d = Math.abs(A * px + B * py + C) / Math.sqrt(A * A + B * B);
	    return d <= 2.0D;
	  
	}
	
	public boolean isInside1(int px, int py) {
		return (Math.sqrt((point1.x - px) * (point1.x - px) + (point1.y - py) * (point1.y - py)) <= r);
	}

	@Override
	float getX() {
		return (point1.x + point2.x)/2;
	}

	@Override
	float getY() {
		return (point1.y + point2.y)/2;
	}

	@Override
	float computeArea() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	float computePerimeter() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	void move(float dx, float dy) {
		//point1.move(dx,dy);
	      point2.move(dx,dy);
	}

	@Override
	void scale(float s) {
		// TODO Auto-generated method stub
		
	}

	
	void draw(Graphics g) {
		
		setColor(g);
		g.drawLine((int)(point1.getX()), (int)point1.getY(), (int)point2.getX(), (int)point2.getY());
		
	}
	
}

////////////////////////////////////////////////////////////////////////////

class Picture extends JPanel implements KeyListener, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	
	Line l;

	Vector<Figure> figures = new Vector<Figure>();

private float mouseX;
private float mouseY;

private Point jeden = new Point(0,0);
private Point dwa = new Point(0,0);
	
	@Override
	public void paintComponent(Graphics g) {
		((Graphics2D)g).setStroke(new BasicStroke(3));
		super.paintComponent(g);
		for (Figure f : figures)
			f.draw(g);
	}


  void addFigure(Figure fig)
  { for (Figure f : figures){ f.unselect(); }
    fig.select();
    figures.add(fig);
    repaint();
  }


  void moveAllFigures(float dx, float dy){
  	for (Figure f : figures){
  		if (f.isSelected()) f.move(dx,dy);
  	}
      repaint();
  }


  public void keyPressed (KeyEvent evt)
  {  int dist;
     if (evt.isShiftDown()) dist = 10;
                       else dist = 1;
		switch (evt.getKeyCode()) {
		case KeyEvent.VK_UP:
			moveAllFigures(0, -dist);
			break;
		case KeyEvent.VK_DOWN:
			moveAllFigures(0, dist);
			break;
		case KeyEvent.VK_LEFT:
			moveAllFigures(-dist, 0);
			break;
		case KeyEvent.VK_RIGHT:
			moveAllFigures(dist, 0);
			break;
		case KeyEvent.VK_DELETE:
			Iterator<Figure> i = figures.iterator();
			while (i.hasNext()) {
				Figure f = i.next();
				if (f.isSelected()) {
					i.remove();
				}
			}
			repaint();
			break;
		}
  }

 public void keyReleased (KeyEvent evt)
 {  }

 public void keyTyped (KeyEvent evt)
 {
   char znak=evt.getKeyChar(); 
		switch (znak) {
		case 'p':
			addFigure(new Point());
			break;
		case 'c':
			addFigure(new Circle());
			break;
		case 'l':
			addFigure(new Line());
			break;
		case '+':
		}
 }


  
 public void mouseClicked(MouseEvent e)
 { int px = e.getX();
   int py = e.getY();

   
   
for (Figure f : figures)
     { if (e.isAltDown()==false) f.unselect();
       if (f.isInside(px,py))
       {
    	   f.select( !f.isSelected()); 
    	   for (Line g : f.lines)
    	   {
    		   g.select();
    		   
    	   }
       }
     }
   repaint();
 }

 public void mouseEntered(MouseEvent e)
 {
	}

 public void mouseExited(MouseEvent e)
 { }


	public void mousePressed(MouseEvent e)
	 {
		int px = e.getX();
		   int py = e.getY();
		   this.mouseX = e.getX();
			 this.mouseY = e.getY();
		for (Figure f : figures)
	     { if (e.isAltDown()==false)
	       if (f.isInside(px,py))
	       {
	    	   if(f.computeArea() > 0)
	    	   { 
	    		   jeden.x = f.getX();
	    		   jeden.y = f.getY();
	    	   }
	    	   
	    	   
	       }
	     }
	   repaint();
	 }
	

 public void mouseReleased(MouseEvent e)
 {
	int px = e.getX();
		   int py = e.getY();
		   this.mouseX = e.getX();
			 this.mouseY = e.getY();
		for (Figure f : figures)
	     { if (e.isAltDown()==false) f.unselect();
	       if (f.isInside(px,py))
	       {
	    	   if(f.computeArea() > 0)
	    	   { 
	    		   f.select( !f.isSelected()); 
	    		   dwa.x = f.getX();
	    		   dwa.y = f.getY();
	    		  Point un = new Point(jeden.x, jeden.y);
		    	  Point du = new Point(dwa.x, dwa.y);
		    	  l = new Line(un,du);
	    		 f.lines.add(l);
		    	addFigure(l);
	    	   }
	       }
	     }
	   repaint();
	 }
 public void mouseDragged(MouseEvent e)
 {	   
	 for (Figure f : figures)
     {if(f.isSelected())
     {
       {
	moveAllFigures(e.getX()-this.mouseX,e.getY()-this.mouseY);
	   
		this.mouseX = f.getX();
		this.mouseY = f.getY();
       }
     }
     }
     
	  
 }
 public void mouseMoved(MouseEvent e) 
 {
 	
 }

}



