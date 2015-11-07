package game.ai;

import com.badlogic.gdx.math.Vector2;
import game.Entity;
import game.Game;
import game.SpawnAlgorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Teun
 */
public class PathfindingAlgorithm {

    public static final float ALGORITHM_MIN_DISTANCE = PathNode.PATH_NODE_DISTANCE + 1f;

    private ArrayList<PathNode> nodes;
    private ArrayList<PathNode> pathToTarget;
    private SpawnAlgorithm spawnAlgorithm;
    private int currentNode;

    private Game game;
    private AICharacter ai;
    private Entity target;

    private Vector2 startLocation;
    private Vector2 endLocation;

    public PathfindingAlgorithm(Game game, AICharacter ai, Entity target) {
        this.game = game;
        this.ai = ai;
        this.target = target;
        this.currentNode = 1;
        this.nodes = new ArrayList<>();
        this.spawnAlgorithm = new SpawnAlgorithm(game);
        this.startLocation = ai.getLocation();
        this.endLocation = target.getLocation();
        createNodes();
    }

    public void createNodes() {
        PathNode startNode = new PathNode(startLocation);
        nodes.add(startNode);
        PathNode currentNode = startNode;
        while (true) {
            List<PathNode> childNodes = currentNode.getChildNodes();
            removeDuplicateNodes(childNodes);
            removeInvalidSpawnedNodes(childNodes);
            currentNode = findClosestPathNodeToTarget(childNodes);
            if (currentNode.getLocation().dst(target.getLocation()) < ALGORITHM_MIN_DISTANCE) {
                ai.move(target.getLocation());
                break;
            }
        }
    }

    public List<PathNode> removeDuplicateNodes(List<PathNode> pathNodeList) {
        for (PathNode pathNode : pathNodeList) {
            if (nodes.contains(pathNode)) {
                pathNodeList.remove(pathNode);
            }
        }
        return pathNodeList;
    }

    public List<PathNode> removeInvalidSpawnedNodes(List<PathNode> pathNodeList) {
        for (PathNode pathNode : pathNodeList) {
            if (spawnAlgorithm.isInInvalidLocation(pathNode.getLocation())) {
                pathNodeList.remove(pathNode);
            }
        }
        return pathNodeList;
    }

    public PathNode findClosestPathNodeToTarget(List<PathNode> pathNodeList) {
        PathNode closestNode = null;
        float smallestDistance = Float.MAX_VALUE;
        for (PathNode pathNode : pathNodeList) {
            float distance = pathNode.getLocation().dst(target.getLocation());
            if (distance < smallestDistance) {
                closestNode = pathNode;
                smallestDistance = distance;
            }
        }
        return closestNode;
    }

    public float getDistanceToTarget(PathNode pathNode) {
        return pathNode.getLocation().dst(target.getLocation());
    }

    public PathNode getNextNode() {
        return pathToTarget.get(currentNode++);
    }

    public Vector2 getEndLocation() {
        return endLocation;
    }

    public Vector2 getStartLocation() {
        return startLocation;
    }

    public Entity getTarget() {
        return target;
    }

    public AICharacter getAi() {
        return ai;
    }

    public Game getGame() {
        return game;
    }

    public int getCurrentNode() {
        return currentNode;
    }

    public SpawnAlgorithm getSpawnAlgorithm() {
        return spawnAlgorithm;
    }

    public ArrayList<PathNode> getPathToTarget() {
        return pathToTarget;
    }

    public ArrayList<PathNode> getNodes() {
        return nodes;
    }
}
