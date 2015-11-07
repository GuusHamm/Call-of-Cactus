package game.ai;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Teun
 */
public class PathNode {

    public static final float PATH_NODE_DISTANCE = 1f;

    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int DOWN = 3;
    public static final int UP = 4;

    private Vector2 location;
    private PathNode parentNode;

    public PathNode(Vector2 location) {
        this.location = location;
    }

    public PathNode(Vector2 location, PathNode parentNode) {
        this.location = location;
        this.parentNode = parentNode;
    }

    public Vector2 getLocationNextTo(int direction) {
        switch (direction) {
            case LEFT:
                return new Vector2(location.x - PATH_NODE_DISTANCE, location.y);
            case RIGHT:
                return new Vector2(location.x + PATH_NODE_DISTANCE, location.y);
            case DOWN:
                return new Vector2(location.x, location.y - PATH_NODE_DISTANCE);
            default:
                return new Vector2(location.x, location.y + PATH_NODE_DISTANCE);
        }
    }

    public Vector2 getLocation() {
        return location;
    }

    public PathNode getParentNode() {
        return parentNode;
    }

    /**
     * Gets a child node in specified direction
     * @param direction Direction to GO, use the static fields of this class
     * @return New PathNode in specified direction
     */
    public PathNode getChildNode(int direction) {
        return new PathNode(getLocationNextTo(direction), this);
    }

    /**
     * Gets the direction of the parent node for this node
     * @return -1 if no parent is available, int specified in direction in class
     */
    public int getDirectionOfParentNode() {
        for (int i = LEFT; i < UP; i++) {
            if (getLocationNextTo(i) == getParentNode().getLocation())
                return i;
        }
        return -1;
    }

    /**
     * Generates a list of child nodes, assign parent nodes and skips the parent node direction
     * @return List of nodes
     */
    public ArrayList<PathNode> getChildNodes() {
        ArrayList<PathNode> pathNodes = new ArrayList<>();
        for (int i = LEFT; i < UP; i++) {
            if (i == getDirectionOfParentNode())
                continue;

            pathNodes.add(getChildNode(i));
        }
        return pathNodes;
    }

    public List<Integer> getDirections() {
        List<Integer> i = new ArrayList<>();
        i.add(LEFT);
        i.add(RIGHT);
        i.add(UP);
        i.add(DOWN);
        return i;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PathNode pathNode = (PathNode) o;

        return !(location != null ? !location.equals(pathNode.location) : pathNode.location != null);

    }

    @Override
    public int hashCode() {
        return location != null ? location.hashCode() : 0;
    }
}
