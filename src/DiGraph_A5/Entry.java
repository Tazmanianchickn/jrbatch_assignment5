package DiGraph_A5;

public class Entry implements Comparable<Entry>{

    private long priority;
    private String location;

    public Entry(String passedLocation, long passedPriority) {
        location = passedLocation;
        priority = passedPriority;
    }

    public long getPriority() {return priority; }
    public String getLocation() {return location; }


    public int compareTo(Entry passedEntry) {
        return (int) (priority - passedEntry.getPriority());
    }
}
