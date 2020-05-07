package corejava.bounceThread;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

class Ball{
    private static final int XSIZE = 15;
    private static final int YSIZE = 15;
    private double x = 0;//以(x,y)为左下角的点画一个15*15的小球
    private double y = 0;
    private double dx = 1;//每次移动的距离
    private double dy = 1;

    /*
    * Moves the ball to the next position ,reversing direction if it hits one of the edges.
    * */
    public void move(Rectangle2D bounds){
        x += dx;
        y += dy;

        //撞到左边界
        if(x < bounds.getMinX()){
            x = bounds.getMinX();
            //改变方向
            dx = -dx;
        }
        //撞到右边界
        if(x + XSIZE >= bounds.getMaxX()){
            x = bounds.getMaxX() - XSIZE;
            dx = -dx;
        }
        //撞到下边界
        if(y < bounds.getMinY()){
            y = bounds.getMinY();
            dy = -dy;
        }
        //撞到上边界
        if(y + YSIZE >= bounds.getMaxY()){
            y = bounds.getMaxY() - YSIZE;
            dy = -dy;
        }

    }

    /*
    * Gets the shape of the ball as its current position.
    * */
    public Ellipse2D getShape(){
        return new Ellipse2D.Double(x,y,XSIZE,YSIZE);
    }
}