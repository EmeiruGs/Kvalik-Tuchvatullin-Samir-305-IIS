public class Product {
    private int inum;
    private double time;
    private double jnum;

    public Product(int inum, double time, double jnum) {
        this.inum = inum;
        this.time = time;
        this.jnum = jnum;
    }

    public int getInum() { return inum; }
    public void setInum(int inum) { this.inum = inum; }

    public double getTime() { return time; }
    public void setTime(double time) { this.time = time; }

    public double getJnum() { return jnum; }
    public void setJnum(double jnum) { this.jnum = jnum; }
}
