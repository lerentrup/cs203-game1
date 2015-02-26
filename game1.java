
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
   
   public static int randomSize(){
       int r = (int) (Math.random() * 100);
       
       if(r < 25)
           return 15;
       else if (r < 50)
           return 20;  
       else if (r < 75)
           return 25; 
       else 
           return 30;
   }
}
//Pos class that extends the Posn class (to add collidability)
public class Pos extends Posn{
    
    boolean collidable;
    
    Pos(int x, int y){
        super(x, y);
        collidable = false;
    }
    
    Pos(int x, int y, boolean c){
        super(x, y);
        collidable = c;
    }
    
    public Pos nowCollidable(){
        collidable = true;
        return new Pos(this.x, this.y);
    }
    
    public boolean isCollidable(){
        return collidable;
    }
    
    public int posAhead(){
        return this.y + 11;
    }
    
    public Pos positionAhead(){
        return new Pos(this.x, this.y + 11);
    }
    
}

//Field class that represents the game world (extends the World from GameWorlds)

public class Field extends World{
    
    Tetrimino mino;
    Tetrimino deadmino;
    Tetrimino newmino;
    WorldImage h;
    
    public Field(){
        super();
        this.mino = new Tetrimino(new Pos(200, 10), 20, new Red());
        
    }
    
    public Field addTetrimno(Tetrimino t){
        return new Field(t);
    }
    
    public Field(Tetrimino t){
        super();
        this.mino = t;
}
        
    WorldImage backGround=
        new OverlayImages(new RectangleImage(new Pos(200, 400), 400, 800, Color.LIGHT_GRAY),
		new OverlayImages(new LineImage(new Pos(100, 0), new Pos(100, 800), new Black()),
                        new OverlayImages(new LineImage(new Pos(200, 0), new Pos(200, 800), new Black()),
                             new OverlayImages (new LineImage(new Pos(300, 0), new Pos(300, 800), new Black()),
                                    new RectangleImage(new Pos(200, 401, true), 20, 20, Color.ORANGE)))));
    
   
    
    public World onKeyEvent(String key) {
        return new Field(this.mino.moveTetrimino(key));
	}
    
    public World onTick(){
        
        //System.out.println(this.mino.posn.y);
        
        if(!this.mino.moving /**&& this.mino.posn.y < 600 **/){
            this.mino.posn.collidable = true;
            
            deadmino = mino;
            System.out.println(deadmino.toString());
            this.makeImage();
            this.mino = new Tetrimino(new Pos(200, 10), game1.Tetrimino.randomSize(), game1.Tetrimino.randomColor());
            return new Field(this.mino.minoDownShift());
        }
        
        else if(!this.mino.moving){
            this.worldEnds();            
        }
      return new Field(this.mino.minoDownShift());
    }

   public WorldImage buryMino(){
       return new OverlayImages(this.backGround, (new RectangleImage (this.mino.posn, this.mino.side, this.mino.side, this.mino.col)));
   } 
   
    public WorldImage makeImage(){        
        if(!mino.moving && this.mino.posn.y > 600){
            System.out.println("AYYYYYYYYYYYYYYY LMAO");
                Pos temp_pos = this.mino.posn;
		return new OverlayImages(this.backGround, (new OverlayImages(new RectangleImage(temp_pos, this.mino.side, this.mino.side, this.mino.col), this.mino.tetriminoImage())));}
        return new OverlayImages(this.backGround, this.mino.tetriminoImage());
	}
    
    public WorldImage lastImage(String s){
        Pos temp_pos = this.mino.posn;
        return new OverlayImages(this.backGround, 
                (new OverlayImages
        (new RectangleImage (temp_pos, this.mino.side, this.mino.side, this.mino.col), this.mino.tetriminoImage())));
    }
    
    public WorldEnd worldEnds(){
         if(!this.mino.moving && this.mino.posn.y > 600)
             return new WorldEnd(false, this.buryMino());
         else
             return new WorldEnd(false, this.makeImage());
         
    }
}
