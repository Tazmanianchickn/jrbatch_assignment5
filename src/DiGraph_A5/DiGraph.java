package DiGraph_A5;

import java.util.Collection;
import java.util.HashMap;
import java.util.PriorityQueue;

public class DiGraph implements DiGraphInterface {
    private long nodeCount;
    private long edgeCount;
    private HashMap<Long, Node> nodeMap;
    private HashMap<Long, Edge> edgeMapId;
    private HashMap<String, Node> nodeLocationMap;
    private HashMap<String, Edge> edgeLocationMap;
    final static int INFINITY = Integer.MAX_VALUE;


    public DiGraph ( ) {
        nodeCount = 0;
        edgeCount = 0;
        nodeMap = new HashMap<>();
        edgeMapId = new HashMap<>();
        nodeLocationMap = new HashMap<>();
        edgeLocationMap = new HashMap<>();
    }

    public boolean addNode(long idNum, String label) {
        Node currentNode = new Node(idNum, label);
        if (addNodeErrorCheck(idNum, label)) {
            nodeMap.put(idNum, currentNode);
            nodeLocationMap.put(label, currentNode);
            nodeCount++;
            return true;
        } else {return false;}
    }

    public boolean addEdge(long idNum, String sLabel, String dLabel, long weight, String eLabel) {
        Edge edge = new Edge(idNum, weight, sLabel, dLabel, eLabel);
        if (addEdgeErrorCheck(idNum, sLabel, dLabel)) {
            String combinedLabel0 = sLabel+dLabel;
            String combinedLabel1 = dLabel+sLabel;
            if (edgeLocationMap.containsKey(combinedLabel0)) {
                return false;
            } else {
                Node temp0 = nodeLocationMap.get(dLabel);
                Node temp1 = nodeLocationMap.get(sLabel);
                edgeLocationMap.put(combinedLabel0, edge);
                temp0.addInsideEdge(combinedLabel0, edge);
                temp1.addOutsideEdge(combinedLabel1, edge);
                edgeMapId.put(idNum, edge);
                edgeCount++;
                return true;
            }
        } else {return false; }
    }

    public boolean delNode(String label) {
        if (delNodeErrorCheck(label)) {
            nodeMap.remove(label);
            nodeLocationMap.remove(label);
            nodeCount--;
            return true;
        } else {return false;}
    }

    public boolean delEdge(String sLabel, String dLabel) {
        String temp0 = sLabel+dLabel;
        String temp1 = dLabel+sLabel;
        if (delEdgeErrorCheck(temp0)) {
            long tempId = edgeLocationMap.get(temp0).getID();
            Node tempNode0 = nodeLocationMap.get(dLabel);
            tempNode0.removeInsideEdge(temp0);
            Node tempNode1 = nodeLocationMap.get(sLabel);
            tempNode1.removeOutsideEdge(temp1);
            edgeLocationMap.remove(temp0);
            edgeMapId.remove(tempId);
            edgeCount--;
            return true;
        } else {return false;}
    }

    public long numNodes() {
        return nodeCount;
    }

    public long numEdges() {
        return edgeCount;
    }

    public ShortestPathInfo[] shortestPath(String label) {
        ShortestPathInfo[] shortestPath = new ShortestPathInfo[nodeLocationMap.size()];
        PriorityQueue<Entry> minHeap = new PriorityQueue<Entry>();
        Entry first = new Entry(label, 0);
        minHeap.add(first);
        initNodeValues();
        findShortestPath(minHeap);
        return updatePathArray(shortestPath);
    }



    // ************ Helper Functions **************
    private boolean addNodeErrorCheck(long passedLong, String passedLocation) {
        if (nodeMap.containsKey(passedLong) || passedLong<0) {
            return false;
        }
        return !nodeLocationMap.containsKey(passedLocation) && passedLocation != null;
    }

    private boolean addEdgeErrorCheck(long passedLong, String passedSLocation, String passedDLocation) {
        if (edgeMapId.containsKey(passedLong) || passedLong < 0) {
            return false;
        }
        return nodeLocationMap.containsKey(passedSLocation) && nodeLocationMap.containsKey(passedDLocation);
    }

    private boolean delNodeErrorCheck(String passedLabel) {
        return nodeMap.containsKey(passedLabel) || nodeLocationMap.containsKey(passedLabel);
    }

    private boolean delEdgeErrorCheck(String passedLabel) {
        return edgeLocationMap.containsKey(passedLabel);
    }

    private void initNodeValues() {
        for (Node node: nodeLocationMap.values()) {
            node.setDistance(INFINITY);
        }
    }

    private void findShortestPath(PriorityQueue<Entry> passedHeap) {
        int x = 0;
        while (passedHeap.size() != 0) {
            Entry entry = passedHeap.peek();
            Node tempNode = nodeLocationMap.get(entry.getLocation());
            Long tempLong = entry.getPriority();
            if (x==0) {
                tempNode.setDistance(0);
            }
            passedHeap.poll();
            nodeHasBeenSeen(passedHeap, tempNode, tempLong);
            x++;
        }
    }

    private void nodeHasBeenSeen(PriorityQueue<Entry> passedHeap, Node passedNode, Long passedLong) {
        Collection<Edge> edges = passedNode.getOutsideEdge().values();
        if (!passedNode.seen) {
            passedNode.seen = true;
            for (Edge e : edges) {
                Node node = nodeLocationMap.get(e.getDestinationLocation());
                if (!node.seen) {
                    if (passedLong+e.getWeight()<node.getDistance()) {
                        node.setDistance(passedLong+e.getWeight());
                        passedHeap.add(new Entry(node.getLabel(), node.getDistance()));
                    }
                }
            }
        }
    }

    private ShortestPathInfo[] updatePathArray(ShortestPathInfo[] passedPath) {
        int x = 0;
        for (Node node:nodeLocationMap.values()) {
            if (x == nodeLocationMap.size()) {
                break;
            }
            if (!node.seen) {
                passedPath[x] = new ShortestPathInfo(node.getLabel(), -1);
            } else {
                passedPath[x] = new ShortestPathInfo(node.getLabel(), node.getDistance());
            }
            x++;
        }
        return passedPath;
    }
}