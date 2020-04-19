package DiGraph_A5;

public class Edge {
    private long id;
    private long weight;
    private String startLocation;
    private String destinationLocation;
    private String edgeLocation;

    public Edge(long passedId, long passedWeight, String passedSLocation,
                String passedDLocation, String passedELocation) {
        id = passedId;
        startLocation = passedSLocation;
        destinationLocation = passedDLocation;
        edgeLocation = passedELocation;
        weight = passedWeight;
    }

    public String getDestinationLocation() { return destinationLocation; }
    public long getWeight() {return weight; }
    public long getID() {return id; }


}
