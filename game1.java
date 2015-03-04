
// (Hopefully) a simple tetris game (potentially with variable block sizes, but not shapes, and without rotation)

package game1;

import tester.*;

import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldcanvas.*;
import javalib.worldimages.*;

// Tetrimino Class that represents the tetriminos
public class Tetrimino {
    
    Pos posn;
    int side;
    IColor col;
    boolean moving;
    
    
    Tetrimino(Pos p, int s, IColor c){
        this.moving = true;
        this.posn = p;
        this.side = s;
        this.col = c;
    }
    
    WorldImage tetriminoImage(){
        return new RectangleImage(this.posn, this.side, this.side, this.col);
    }
    
    public Tetrimino moveTetrimino(String key){
                
        if(!this.atEndHuh()){
                if(key.equals("left") && this.atRightEdgeHuh()){
                    return new Tetrimino(new Pos(this.posn.x - 5, this.posn.y),
							this.side, this.col);
                }
                else if(key.equals("right") && this.atLeftEdgeHuh()){
                    return new Tetrimino(new Pos(this.posn.x + 5, this.posn.y),
							this.side, this.col);
                }
                
                else if (key.equals("right") && !this.atRightEdgeHuh()){
			return new Tetrimino(new Pos(this.posn.x + 5, this.posn.y),
							this.side, this.col);
		}
		else if (key.equals("left") && !this.atLeftEdgeHuh()){
			return new Tetrimino(new Pos(this.posn.x - 5, this.posn.y),
							this.side, this.col);
		}
                else if (key.equals("down")){
                        return new Tetrimino(new Pos(this.posn.x, this.posn.y + 5),
                                                        this.side, this.col);
                }
                else
                    return this;}
        else
            return this;
    }
    
    public boolean atEndHuh(){
        
        if(this.posn.y + this.side/2 == 800){
            moving = false;

            return true;
        }        
            return false;
        
    }
    
    public boolean atRightEdgeHuh(){
        return this.posn.x + this.side  == 400; 
    }
    
    public boolean atLeftEdgeHuh(){
        return this.posn.x - this.side == 0;
    }
    
    public boolean atAnyEdgeHuh(){
        return this.atRightEdgeHuh() || this.atLeftEdgeHuh();
    }
    
    public boolean willIntersectTop(){
        return this.posn.collidable;
    }
    
    public boolean willIntersectRight(){
        
    }
    
    public boolean willIntersectLeft(){
        
    }
    
    
    public Tetrimino minoDownShift(){
        
        if (this.posn.positionAhead().collidable)
        {System.out.println("POSITION AHEAD IS COLLIDABLE!!!!!");
            return this;}
        else if(!this.atEndHuh()){
            System.out.println("Current: " + this.posn.y + " Soon: " + this.posn.posAhead() + "!");
        return new Tetrimino(new Pos(this.posn.x, this.posn.y + 5), 
                this.side, this.col);
        }
        else
            return this;
    }
    
   public static IColor randomColor(){
       int r = (int) (Math.random() * 100);
       
       if(r < 25)
           return new Red();
       else if (r < 50)
           return new Blue();
       else if (r < 75)
           return new Green();
       else 
           return new Yellow();
   }    
   

}
//Pos class that extends the Posn class  (to check for positions ahead)
public class Pos extends Posn{
    
    Pos(int x, int y){
        super(x, y);
     }
    
   public Pos yPositionAhead(){
        return new Pos(this.x, this.y + 20);
    }
    
     public boolean isEqual(Pos p){
        return(this.x == p.x && this.y == p.y);
    }
 }

//Field class that represents the game world (extends the World from GameWorlds)

public class Field extends World{
    
    Tetrimino mino;

    Tetrimino newmino;
    WorldImage h;
    public CollidableArray collidablePosns = new CollidableArray();
    

    public Field(){
        super();
        this.mino = new Tetrimino(new Pos(200, 10), 20, new Red());
        for(int y = 1; y < 801; y++){
            collidablePosns.add(new Pos(0, y));
            collidablePosns.add(new Pos(400, y));}
        for(int x = 1; x < 400; x++)
            collidablePosns.add(new Pos(x, 800));
        
        collidablePosns.add(new Pos(200,400));
        
    }
    
    public Field addTetrimno(Tetrimino t){
        return new Field(t);
    }
    
    OverlayImages backGround=
        new OverlayImages(new RectangleImage(new Pos(200, 400), 400, 800, Color.LIGHT_GRAY),
		new OverlayImages(new LineImage(new Pos(100, 0), new Pos(100, 800), new Black()),
                        new OverlayImages(new LineImage(new Pos(200, 0), new Pos(200, 800), new Black()),
                             new OverlayImages (new LineImage(new Pos(300, 0), new Pos(300, 800), new Black()),
                                    new RectangleImage(new Pos(200, 400), 20, 20, Color.ORANGE)))));;
    
    public Field(Tetrimino t){
        super();
        this.mino = t;

    }
     public Field(Tetrimino t, OverlayImages w){
        super();
        this.mino = t;
        backGround = w;    }
    
    public ArrayList getCollidablePosns(){
        return collidablePosns;
    }
    
   
     public World onKeyEvent(String key) {
        
        return new Field(this.mino.moveTetrimino(key), backGround);
	}
    
    public World onTick(){
        
        if(collidablePosns.contains(new Pos(200,400)))
            System.out.println("CONTAINS 400 200");
            
        //I don't know why this isn't working. yPositionAhead() should detect the test position ahead and do this case    
        // but it isn't!
        Pos p = mino.posn.yPositionAhead();
        System.out.println("Equals: " + p.isEqual(new Pos(200,400)));
        System.out.println("Contains: " + collidablePosns.contains(p));
        if(collidablePosns.contains(p)){
            System.out.println("AT SECOND CASE!");
            collidablePosns.add(mino.posn);
            backGround = new OverlayImages(backGround, mino.tetriminoImage());
            this.mino = new Tetrimino(new Pos(200, 10), 20, game1.Tetrimino.randomColor());
            return new Field(this.mino.minoDownShift());
        }
        
        else if(this.mino.atEndHuh() && this.mino.posn.y > 600){
            System.out.println("AT FIRST CASE!");
            collidablePosns.add(mino.posn);
    
            System.out.println(collidablePosns.toString());
            backGround = new OverlayImages(backGround, mino.tetriminoImage());
  
            this.mino = new Tetrimino(new Pos(200, 10), 20, game1.Tetrimino.randomColor());
            return new Field(this.mino.minoDownShift(), backGround);
        }
                
        else if(!this.mino.moving){
            this.worldEnds();            
        }
        return new Field(this.mino.minoDownShift(), backGround);
    }

   
    public WorldImage makeImage(){        
        if(!mino.moving && this.mino.posn.y > 600){
                Pos temp_pos = this.mino.posn;
		return new OverlayImages(this.backGround, (new OverlayImages(new RectangleImage(temp_pos, this.mino.side, this.mino.side, this.mino.col), this.mino.tetriminoImage())));}
        return new OverlayImages(this.backGround, this.mino.tetriminoImage());
	}
       
    public WorldEnd worldEnds(){
         if(!this.mino.moving && this.mino.posn.y > 600)
             return new WorldEnd(false, this.buryMino());
         else
             return new WorldEnd(false, this.makeImage());
        
    }
}

// The collidableArray class- this was so I could confirm the functionality of the collidable positions process

public class CollidableArray extends ArrayList{
    
    CollidableArray(){
        super();
    }
    
    public boolean contains(Pos p){
        int i = 0;
        while(i < this.size()){
        if(((Pos)(this.get(i))).isEqual(p))
            return true;
        else
            i = i + 1;}
        return false;
    }
    
}

public class FieldTests {
    
    public static void main(String[] argv){

	Field f = new Field();
    	f.bigBang(400, 800, 0.1);
    
} 
}
