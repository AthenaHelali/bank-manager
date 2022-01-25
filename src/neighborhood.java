public class neighborhood {
    int x_min;
    int x_max;
    int y_min;
    int y_max;
    String name;

    public neighborhood(int x_min, int x_max, int y_min, int y_max, String name) {
        this.x_min = x_min;
        this.x_max = x_max;
        this.y_min = y_min;
        this.y_max = y_max;
        this.name = name;
    }

    public boolean contains(kd_node node) {
        if (node.point[0] >= x_min & node.point[0] <= x_max & node.point[1] >= y_min & node.point[1] <= y_max)
            return true;
        else return false;
    }
}
