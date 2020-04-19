package DiGraph_A5;
import java.util.HashMap;

public class Node {

    public long idNumber;
    public long distance;
    public boolean seen;

    public String label;
    public HashMap<String, Edge> outEdges;
    public HashMap<String, Edge> inEdges;
    public Node path;

    public Node(long passedID, String passedLabel) {
        idNumber = passedID;
        label = passedLabel;
        distance = 0;
        seen = false;
        path = null;
        outEdges = new HashMap<String, Edge>();
        inEdges = new HashMap<String, Edge>();
    }

    public void addInsideEdge(String passedString, Edge passedEdge) {
        inEdges.put(passedString, passedEdge);
    }

    public void addOutsideEdge(String passedString, Edge passedEdge) {
        outEdges.put(passedString, passedEdge);
    }

    public void removeInsideEdge(String passedString) {
        inEdges.remove(passedString);
    }

    public void removeOutsideEdge(String passedString) {
        outEdges.remove(passedString);
    }

    public HashMap<String, Edge> getInsideEdge() {return inEdges;}
    public HashMap<String, Edge> getOutsideEdge() {return outEdges;}

    public String getLabel() {return label;}
    public void setDistance(long passedDistance){ distance=passedDistance; }
    public long getDistance() {return distance;}
}
