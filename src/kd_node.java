public class kd_node {
    int[] point;
    String name;
    boolean is_main;
    kd_node right_child;
    kd_node left_child;

    public kd_node(int x, int y, String name, boolean is_main) {
        point = new int[2];
        point[0] = x;
        point[1] = y;
        this.name = name;
        this.is_main = is_main;
        right_child = null;
        left_child = null;
    }
    public boolean equals(kd_node other) {
        if(other.point[0]==this.point[0] && other.point[1] == this.point[1])
            return true;
        else return false;
    }

}
