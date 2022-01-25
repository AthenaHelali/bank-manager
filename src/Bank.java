public class Bank {
    String bank_name;
    String branch_name;
    public int x;
    public int y;
    public kd_tree branches;
    public int Branches_num;
    public boolean is_main;

    public Bank(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.bank_name = name;
        branches = new kd_tree();
        Branches_num = 0;
    }

    public Bank(int x, int y, String bank_name, String branch_name) {
        this.x = x;
        this.y = y;
        this.bank_name = bank_name;
        this.branch_name = branch_name;
        branches = null;
        Branches_num = 0;
    }

}
