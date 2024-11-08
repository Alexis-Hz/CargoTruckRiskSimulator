package model.traffic;
/**
 * The segment class represents a line between two coordinates, or a segment in a route defined by a set of coordinates. <p>
 * GeoSpatially speaking it would be an arc, but in this implementation it is a straight arc
 * Segments have a normal speed and a current speed, the current speed will be most times either the max speed or slightly slower if affected by events.<p>
 * It also has a Length and a name, beginning and end
 * @author Jorge Alexis Hernandez
 * @version April 2010
 */
public class Segment
{
    public int normalSpeed;//change into averageSpeed
    public int currentSpeed;//the segment is not moving
    public int length;
    Coord pointA;
    Coord pointB;
    String name;
    //speed is units per minute
    /**
     * Succint constructor, using default speed and length values:
     * <ul>
     * <li>normal and current speed = 1
     * <li>lenght = 1
     * </ul>
     * @param pointA beginning
     * @param pointB end
     */
    public Segment(Coord pointA, Coord pointB)
    {
        normalSpeed = currentSpeed = 1;
        this.pointA = pointA;
        this.pointB = pointB;
        length = 1;
    }
    /**
     * Full constructor, sets current speed to normal speed
     * @param normalSpeed normal speed for the segment, or max speed
     * @param length length of the segment in segment units of distance
     * @param pointA beginning of the segment
     * @param pointB end of the segment
     */
    public Segment(int normalSpeed, int length, Coord pointA, Coord pointB)
    {
        this.normalSpeed = currentSpeed = normalSpeed;
        this.length = length;
        this.pointA = pointA;
        this.pointB = pointB;
    }
    public void setCurrent(int currentSpeed)
    {
        this.currentSpeed = currentSpeed;
    }
    
    public int getCurrent()
    {
        return currentSpeed;
    }
    
    public void setNormal(int normalSpeed)
    {
        this.normalSpeed = normalSpeed;
    }
    
    public int getNormal()
    {
        return normalSpeed;
    }
    
    public void setPointA(Coord pointA)
    {
        this.pointA = pointA;
    }
    
    public Coord getPointA()
    {
        return pointA;
    }
    
    public void setPointB(Coord pointB)
    {
        this.pointB = pointB;
    }
    
    public Coord getPointB()
    {
        return pointB;
    }
    
    public void setLength(int length)
    {
        this.length = length;
    }
    
    public int getLength()
    {
        return length;
    }
    public void setName(String namex)
    {
    	name = namex;
    }
    public String getName()
    {
    	return name;
    }
    /**
     * @return to String of the begging of the segment
     */
    public String toString()
    {
        //return (name + pointA.toString() + "->" + pointB.toString());
    	return pointA.toString();
    }
    
    
}
