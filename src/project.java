import java.util.Scanner;

public class project {
    public static kd_tree all = new kd_tree();
    public static bank_hashTable banks_map = new bank_hashTable();
    public static bank_hashTable branches_map = new bank_hashTable();
    public static Bank Must_branch = null;
    public static neighbor_hashTable neighborhoods = new neighbor_hashTable();

    public static void addB(int x, int y, String name) {
        kd_node NewBank = new kd_node(x, y, name, true);
        Bank bank = new Bank(x, y, name);
        if (all.insert(NewBank)) {
            banks_map.put(name, bank);
            System.out.println("Bank added successfully!");
        } else System.out.println("a bank already exist in this place!");

    }

    public static void addBr(int x, int y, String bank_name, String branch_name) {
        kd_node node = new kd_node(x, y, branch_name, false);
        if (all.insert(node)) {
            Bank bank = banks_map.get(bank_name);
            bank.branches.insert(node);
            branches_map.put(branch_name, bank);
            System.out.println("branch added successfully!");
            return;
        } else
            System.out.println("a bank already exist in this place!");
    }

    public static void delBr(int x, int y) {
        int[] point = new int[2];
        point[0] = x;
        point[1] = y;
        kd_node node = all.contains(point);
        if (node == null) {
            System.out.println("there is no bank in this place!");
        } else {
            if (all.delete(point) != null) {
                banks_map.get(branches_map.get(node.name).bank_name).branches.delete(point);
                branches_map.remove(node.name);
                System.out.println("branch deleted successfully!");
            }
        }
    }

    public static void nearB(int x, int y) {
        int[] point = {x, y};
        kd_node near = all.find_nearest_neighbour(point);
        System.out.println("name : " + near.name);
        System.out.println("Address : ( " + near.point[0] + " , " + near.point[1] + " )");
    }

    public static void nearBr(int x, int y, String name) {
        System.out.println("here");
        int[] point = {x, y};
        banks_map.get(name).branches.find_nearest_neighbour(point);
    }

    public static void listB(String name) {
        all.in_neighbor(neighborhoods.get(name));
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        String input = scn.nextLine();
        int x, y;
        String bank_name;
        String branch_name;
        while (!input.equals("exit")) {
            if (input.equals("addN")) {
                System.out.print("Enter Coordinate of the neighborhood:");
                int x_min = scn.nextInt();
                int x_max = scn.nextInt();
                int y_min = scn.nextInt();
                int y_max = scn.nextInt();
                scn.nextLine();
                System.out.println("Enter name of the neighborhood:");
                String name = scn.nextLine();
                neighborhoods.put(name, new neighborhood(x_min, x_max, y_min, y_max, name));
                System.out.println("neighborhood added successfully!");
            } else if (input.equals("addB")) {
                System.out.println("enter coordinate of the bank:");
                x = scn.nextInt();
                y = scn.nextInt();
                scn.nextLine();
                System.out.println("enter name of the bank:");
                bank_name = scn.nextLine();
                addB(x, y, bank_name);
            } else if (input.equals("addBr")) {
                System.out.println("enter coordinate of the branch:");
                x = scn.nextInt();
                y = scn.nextInt();
                scn.nextLine();
                System.out.println("enter name of the main bank:");
                bank_name = scn.nextLine();
                System.out.println("enter name of the branch:");
                branch_name = scn.nextLine();
                addBr(x, y, bank_name, branch_name);
            } else if (input.equals("delBr")) {
                System.out.println("enter coordinate of the branch:");
                x = scn.nextInt();
                y = scn.nextInt();
                delBr(x, y);
            } else if (input.equals("listB")) {
                System.out.println("enter name of the neighborhood:");
                String name = scn.nextLine();
                listB(name);
            } else if (input.equals("listBrs")) {
                System.out.println("enter name of the bank:");
                bank_name = scn.nextLine();
                banks_map.get(bank_name).branches.inOrder();
            } else if (input.equals("nearB")) {
                System.out.println("enter coordinate:");
                x = scn.nextInt();
                y = scn.nextInt();
                nearB(x, y);
            } else if (input.equals("nearBr")) {
                System.out.println("enter coordinate :");
                x = scn.nextInt();
                y = scn.nextInt();
                scn.nextLine();
                System.out.println("enter name of the bank:");
                String name = scn.nextLine();
                nearBr(x, y, name);
            } else if (input.equals("availB")) {
                System.out.println("enter radius:");
                int R = scn.nextInt();
                System.out.println("enter center :");
                x = scn.nextInt();
                y = scn.nextInt();
                kd_node node = new kd_node(x, y, "center", false);
                all.in_range(all.root, node, R, 0);
            } else if (input.equals("mostBrs")) {
                //ToDo
            }
            input = scn.nextLine();
        }
    }
}
