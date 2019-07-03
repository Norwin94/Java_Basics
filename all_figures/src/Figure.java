/**
  Figury
 
   Autor: Pawel Rogalinski, ...
   Data: 1. 10, 2015 r.
   ---------------------------------------------------------------
   
   £ukasz Szumilas, 236068, W04 Inf           20.11.2017r 
   
   Program edytowany
   Dodanie 3 rodzajów figur, implementacja odpowiednich metod do poprawnego 
   tworzenia i edycji tych figur
   
*/

import java.awt.Color;
import java.awt.Graphics;
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

	protected String properties() {
		String s = String.format("  Pole: %.0f  Obwod: %.0f", computeArea(), computePerimeter());
		if (isSelected()) s = s + "   [SELECTED]";
		return s;
	}

	abstract String getName();
	abstract float  getX();
	abstract float  getY();

    abstract float computeArea();
    abstract float computePerimeter();

    abstract void move(float dx, float dy);
    abstract void scale(float s);

    abstract void draw(Graphics g);

    @Override
    public String toString(){
        return getName();
    }

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
		// by umo¿liwiæ zaznaczanie punktu myszk¹
		// miejsca odleg³e nie wiêcej ni¿ 6 le¿¹ wewn¹trz
		return (Math.sqrt((x - px) * (x - px) + (y - py) * (y - py)) <= 6);
	}


    @Override
	String getName() {
		return "Point(" + x + ", " + y + ")";
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

    String toStringXY(){ return "(" + x + " , " + y + ")"; }

}

//////////////////////////////////////////////////////////////////////////////

class Circle extends Point{
    float r;

    Circle(){
        super();
        r=random.nextFloat()*100;
    }

    Circle(float px, float py, float pr){
        super(px,py);
        r=pr;
    }

    @Override
	public boolean isInside(float px, float py) {
		return (Math.sqrt((x - px) * (x - px) + (y - py) * (y - py)) <= r);
	}

    @Override
   	String getName() {
   		return "Circle(" + x + ", " + y + ")";
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


class Triangle extends Figure{
    Point point1, point2, point3;

    Triangle(){
    	point1 = new Point();
    	point2 = new Point();
    	point3 = new Point();
    }

    Triangle(Point p1, Point p2, Point p3){
        point1=p1; point2=p2; point3=p3;
    }

    @Override
    public boolean isInside(float px, float py)
    { float d1, d2, d3;
      d1 = px*(point1.y-point2.y) + py*(point2.x-point1.x) +
           (point1.x*point2.y-point1.y*point2.x);
      d2 = px*(point2.y-point3.y) + py*(point3.x-point2.x) +
           (point2.x*point3.y-point2.y*point3.x);
      d3 = px*(point3.y-point1.y) + py*(point1.x-point3.x) +
           (point3.x*point1.y-point3.y*point1.x);
      return ((d1<=0)&&(d2<=0)&&(d3<=0)) || ((d1>=0)&&(d2>=0)&&(d3>=0));
    }

    @Override
	String getName() {
    	return "Triangle{"+point1.toStringXY()+
                point2.toStringXY()+
                point3.toStringXY()+"}";
	}

	@Override
	float getX() {
		return (point1.x+point2.x+point3.x)/3;
	}

	@Override
	float getY() {
		return (point1.y+point2.y+point3.y)/3;
	}

	@Override
	float computeArea(){
        float a = (float)Math.sqrt( (point1.x-point2.x)*(point1.x-point2.x)+
                                    (point1.y-point2.y)*(point1.y-point2.y));
        float b = (float)Math.sqrt( (point2.x-point3.x)*(point2.x-point3.x)+
                                    (point2.y-point3.y)*(point2.y-point3.y));
        float c = (float)Math.sqrt( (point1.x-point3.x)*(point1.x-point3.x)+
                                    (point1.y-point3.y)*(point1.y-point3.y));
        float p=(a+b+c)/2;
        return (float)Math.sqrt(p*(p-a)*(p-b)*(p-c));
    }

	@Override
    float computePerimeter(){
        float a = (float)Math.sqrt( (point1.x-point2.x)*(point1.x-point2.x)+
                                    (point1.y-point2.y)*(point1.y-point2.y));
        float b = (float)Math.sqrt( (point2.x-point3.x)*(point2.x-point3.x)+
                                    (point2.y-point3.y)*(point2.y-point3.y));
        float c = (float)Math.sqrt( (point1.x-point3.x)*(point1.x-point3.x)+
                                    (point1.y-point3.y)*(point1.y-point3.y));
        return a+b+c;
    }

	@Override
    void move(float dx, float dy){
        point1.move(dx,dy);
        point2.move(dx,dy);
        point3.move(dx,dy);
    }

	@Override
    void scale(float s){
        Point sr1 = new Point((point1.x+point2.x+point3.x)/3,
                              (point1.y+point2.y+point3.y)/3);
        point1.x*=s; point1.y*=s;
        point2.x*=s; point2.y*=s;
        point3.x*=s; point3.y*=s;
        Point sr2 = new Point((point1.x+point2.x+point3.x)/3,
                              (point1.y+point2.y+point3.y)/3);
        float dx=sr1.x-sr2.x;
        float dy=sr1.y-sr2.y;
        point1.move(dx,dy);
        point2.move(dx,dy);
        point3.move(dx,dy);
    }

	@Override
    void draw(Graphics g){
		setColor(g);
        g.drawLine((int)point1.x, (int)point1.y,
                   (int)point2.x, (int)point2.y);
        g.drawLine((int)point2.x, (int)point2.y,
                   (int)point3.x, (int)point3.y);
        g.drawLine((int)point3.x, (int)point3.y,
                   (int)point1.x, (int)point1.y);
    }

}

//////////////////////////////////////////////////////////////////////////

class Rectangle extends Figure
{
    Point point1, point2, point3, point4;
    
    Rectangle(){
    	point1 = new Point();
    	point2 = new Point(point1.x+random.nextFloat()*50,point1.y);
    	point3 = new Point(point1.x,point1.y+random.nextFloat()*50);
    	point4 = new Point(point2.x,point3.y);
    }

    Rectangle(Point p1, Point p2, Point p3,  Point p4){
        point1=p1; point2=p2; point3=p3;
    }

	@Override
	public boolean isInside(float px, float py) {

		return ((px>=point1.x) && (px<=point2.x)&&(py<=point4.y) && (py>=point2.y));
	}

	@Override
	String getName() {
		return "Rectangle{"+point1.toStringXY()+
                point2.toStringXY()+
                point3.toStringXY()+point4.toStringXY()+"}";
	}

	@Override
	float getX() {
		return (point1.x+point2.x+point3.x+point4.x)/4;
	}

	@Override
	float getY() {
		return (point1.y+point2.y+point3.y+point4.y)/4;
	}

	@Override
	float computeArea() {
		return (point2.x-point1.x)*(point3.y-point2.y);
	}

	@Override
	float computePerimeter() {
		return (2*(point2.x-point1.x))+(2*(point3.y-point2.y));
	}

	@Override
	void move(float dx, float dy) {
		point1.move(dx,dy);
        point2.move(dx,dy);
        point3.move(dx,dy);
        point4.move(dx,dy);
		
	}

	@Override
	void scale(float s) 
	{
		point1.x*=s; point1.y*=s;
        point2.x*=s; point2.y*=s;
        point3.x*=s; point3.y*=s;
        point4.x*=s; point4.y*=s;
        
	}

	@Override
	void draw(Graphics g) {
		setColor(g);
        g.drawLine((int)point1.x, (int)point1.y,
                   (int)point2.x, (int)point2.y);
        g.drawLine((int)point2.x, (int)point2.y,
                   (int)point4.x, (int)point4.y);
        g.drawLine((int)point4.x, (int)point4.y,
                   (int)point3.x, (int)point3.y);
        g.drawLine((int)point3.x, (int)point3.y,
                (int)point1.x, (int)point1.y);
	}
    
}

/////////////////////////////////////////////////////////////////////////
class Trapeze extends Figure
{
    Point point1, point2, point3, point4;
    
    Trapeze(){
    	point1 = new Point();
    	point2 = new Point(point1.x+random.nextFloat()*50,point1.y);
    	point3 = new Point(point1.x-random.nextFloat()*50,point1.y+random.nextFloat()*50);
    	point4 = new Point(point2.x+(point1.x-point3.x),point3.y);
    }

    Trapeze(Point p1, Point p2, Point p3,  Point p4){
        point1=p1; point2=p2; point3=p3;
    }

	@Override
	public boolean isInside(float px, float py) {

		return ((px>=point1.x) && (px<=point2.x)&&(py<=point4.y) && (py>=point2.y));
	}

	@Override
	String getName() {
		return "Trapeze{"+point1.toStringXY()+
                point2.toStringXY()+
                point3.toStringXY()+point4.toStringXY()+"}";
	}

	@Override
	float getX() {
		return (point1.x+point2.x+point3.x+point4.x)/4;
	}

	@Override
	float getY() {
		return (point1.y+point2.y+point3.y+point4.y)/4;
	}

	@Override
	float computeArea() {
		return (((point2.x-point1.x)+(point4.x-point3.x)*(point4.y-point2.y))/2);
	}

	@Override
	float computePerimeter() {
		float a = (float) Math.sqrt((point1.x-point3.x)*(point1.x-point3.x)+(point3.y-point1.y)*(point3.y-point1.y));
		return ((point2.x-point1.x))+(point4.x-point3.x)+(2*a);
	}

	@Override
	void move(float dx, float dy) {
		point1.move(dx,dy);
        point2.move(dx,dy);
        point3.move(dx,dy);
        point4.move(dx,dy);
		
	}

	@Override
	void scale(float s) 
	{
		point1.x*=s; point1.y*=s;
        point2.x*=s; point2.y*=s;
        point3.x*=s; point3.y*=s;
        point4.x*=s; point4.y*=s;
        
	}

	@Override
	void draw(Graphics g) {
		setColor(g);
        g.drawLine((int)point1.x, (int)point1.y,
                   (int)point2.x, (int)point2.y);
        g.drawLine((int)point2.x, (int)point2.y,
                   (int)point4.x, (int)point4.y);
        g.drawLine((int)point4.x, (int)point4.y,
                   (int)point3.x, (int)point3.y);
        g.drawLine((int)point3.x, (int)point3.y,
                (int)point1.x, (int)point1.y);
	}
    
}

///////////////////////////////////////////////////////////////////////

class Diamond extends Figure
{
    Point point1, point2, point3, point4;
    
    
    Diamond(){
    	point1 = new Point();
    	point2 = new Point(point1.x,point1.y+random.nextFloat()*100);
    	point3 = new Point((point1.x+random.nextFloat()*50),(point1.y+((point2.y-point1.y)/2)));
    	point4 = new Point(point1.x-(point3.x-point1.x),point3.y);
    	
    }

    Diamond(Point p1, Point p2, Point p3,  Point p4){
        point1=p1; point2=p2; point3=p3;
    }

	@Override
	public boolean isInside(float px, float py) {
		
		return ((py>=point1.y) && (py<=point2.y)&&(px>=point4.x) && (px<=point3.x));
	}

	@Override
	String getName() {
		return "Diamond{"+point1.toStringXY()+
                point2.toStringXY()+
                point3.toStringXY()+point4.toStringXY()+"}";
	}

	@Override
	float getX() {
		return (point1.x+point2.x+point3.x+point4.x)/4;
	}

	@Override
	float getY() {
		return (point1.y+point2.y+point3.y+point4.y)/4;
	}

	@Override
	float computeArea() {
		return (((point2.y-point1.y)*(point3.x-point4.x))/2);
	}

	@Override
	float computePerimeter() {
		float a = (float) Math.sqrt((point3.y-point1.y)*(point3.y-point1.y)+(point3.x-point1.x)*(point3.x-point1.x));
		return (4*a);
	}

	@Override
	void move(float dx, float dy) {
		point1.move(dx,dy);
        point2.move(dx,dy);
        point3.move(dx,dy);
        point4.move(dx,dy);
		
	}

	@Override
	void scale(float s) 
	{
		point1.x*=s; point1.y*=s;
        point2.x*=s; point2.y*=s;
        point3.x*=s; point3.y*=s;
        point4.x*=s; point4.y*=s;
        
	}

	@Override
	void draw(Graphics g) {
		setColor(g);
        g.drawLine((int)point1.x, (int)point1.y,
                   (int)point3.x, (int)point3.y);
        g.drawLine((int)point3.x, (int)point3.y,
                   (int)point2.x, (int)point2.y);
        g.drawLine((int)point2.x, (int)point2.y,
                   (int)point4.x, (int)point4.y);
        g.drawLine((int)point4.x, (int)point4.y,
                (int)point1.x, (int)point1.y);
	}
    
}

class Picture extends JPanel implements KeyListener, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;


	Vector<Figure> figures = new Vector<Figure>();

 private float mouseX;
 private float mouseY;
	/*
	 * UWAGA: ta metoda bêdzie wywo³ywana automatycznie przy ka¿dej potrzebie
	 * odrysowania na ekranie zawartoœci panelu
	 *
	 * W tej metodzie NIE WOLNO !!! wywo³ywaæ metody repaint()
	 */
	@Override
	public void paintComponent(Graphics g) {
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

    void scaleAllFigures(float s){
    	for (Figure f : figures)
        	{ if (f.isSelected()) f.scale(s);
        	}
          repaint();
    }

    public String toString(){
        String str = "Rysunek{ ";
        for(Figure f : figures)
            str+=f.toString() +"\n         ";
        str+="}";
        return str;
    }

  
    /*
     *  Impelentacja interfejsu KeyListener - obs³uga zdarzeñ generowanych
     *  przez klawiaturê gdy focus jest ustawiony na ten obiekt.
     */
    public void keyPressed (KeyEvent evt)
    //Virtual keys (arrow keys, function keys, etc) - handled with keyPressed() listener.
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
   //Characters (a, A, #, ...) - handled in the keyTyped() listener.
   {
     char znak=evt.getKeyChar(); //reakcja na przycisku na naciœniêcie klawisza
		switch (znak) {
		case 'p':
			addFigure(new Point());
			break;
		case 'c':
			addFigure(new Circle());
			break;
		case 't':
			addFigure(new Triangle());
			break;
		case 'r':
			addFigure(new Rectangle());
			break;
		case 'a':
			addFigure(new Trapeze());
			break;
		case 'd':
			addFigure(new Diamond());
			break;

		case '+':
			scaleAllFigures(1.1f);
			break;
		case '-':
			scaleAllFigures(0.9f);
			break;
		}
   }


   /*
    * Implementacja interfejsu MouseListener - obs³uga zdarzeñ generowanych przez myszkê
    * gdy kursor myszki jest na tym panelu
    */
   public void mouseClicked(MouseEvent e)
   // Invoked when the mouse button has been clicked (pressed and released) on a component.
   { int px = e.getX();
     int py = e.getY();
     for (Figure f : figures)
       { if (e.isAltDown()==false) f.unselect();
         if (f.isInside(px,py)) f.select( !f.isSelected() );
       }
     repaint();
   }

   public void mouseEntered(MouseEvent e)
   //Invoked when the mouse enters a component.
   { }

   public void mouseExited(MouseEvent e)
   //Invoked when the mouse exits a component.
   { }


	public void mousePressed(MouseEvent e)
	 {
		this.mouseX = e.getX();
		this.mouseY = e.getY();
	 }
	

   public void mouseReleased(MouseEvent e)
   //Invoked when a mouse button has been released on a component.
   { }
   
   public void mouseDragged(MouseEvent e)
   {
	   moveAllFigures(e.getX()-this.mouseX,e.getY()-this.mouseY);
	   this.mouseX = e.getX();
	   this.mouseY = e.getY();
   }
   public void mouseMoved(MouseEvent e) 
   {
   	
   }

}



