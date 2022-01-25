public class kd_tree {
    public kd_node root;

    public boolean insert(kd_node node) {
        if (root == null) {
            root = node;
            return true;
        }
        int currDim = 0;
        kd_node currRoot = root;
        while (true) {
            if (node.point[currDim] <= currRoot.point[currDim]) {
                if (currRoot.point[0] == node.point[0] & currRoot.point[1] == node.point[1]) {
                    return false;
                }
                if (currRoot.left_child == null) {
                    currRoot.left_child = node;
                    return true;
                } else
                    currRoot = currRoot.left_child;
            } else {
                if (currRoot.right_child == null) {
                    currRoot.right_child = node;
                    return true;
                } else
                    currRoot = currRoot.right_child;
            }
            currDim = (currDim + 1) % 2;
        }
    }

    public kd_node contains(kd_node node, int[] point, int axis) {
        if (node == null)
            return null;
        if (node.point[0] == point[0] && node.point[1] == point[1])
            return node;
        if (point[axis] > node.point[axis]) {
            return contains(node.right_child, point, (axis + 1) % 2);
        } else return contains(node.left_child, point, (axis + 1) % 2);
    }

    public kd_node contains(int[] point) {
        if (root == null)
            return null;
        else return contains(root, point, 0);
    }

    private kd_node find_nearest_neighbour(kd_node Root, int[] point, int axis) {
        if (Root == null)
            return null;

        kd_node nextBranch;
        kd_node otherBranch;

        if (point[axis] < Root.point[axis]) {
            nextBranch = Root.left_child;
            otherBranch = Root.right_child;
        } else {
            nextBranch = Root.right_child;
            otherBranch = Root.left_child;
        }

        kd_node tmp = find_nearest_neighbour(nextBranch, point, (axis + 1) % 2);
        kd_node near = closest(tmp, Root, point);

        int near_dist = (near.point[0] - point[0]) * (near.point[0] - point[0]) +
                (near.point[1] - point[1]) * (near.point[1] - point[1]);

        int dist = point[axis] - Root.point[axis];

        if (near_dist >= dist * dist) {
            tmp = find_nearest_neighbour(otherBranch, point, (axis + 1) % 2);
            near = closest(tmp, near, point);
        }

        return near;
    }

    public kd_node find_nearest_neighbour(int[] point) {
        if (root == null)
            return null;
        else return find_nearest_neighbour(root, point, 0);
    }

    public kd_node closest(kd_node temp, kd_node Root, int[] point) {
        if (temp == null)
            return Root;
        double dist_temp = (temp.point[0] - point[0]) * (temp.point[0] - point[0]) + (temp.point[1] - point[1])
                * (temp.point[1] - point[1]);
        double dist_Root = ((Root.point[0] - point[0]) * (Root.point[0] - point[0])) + ((Root.point[1] - point[1])
                * (Root.point[1] - point[1]));
        if (dist_temp < dist_Root)
            return temp;
        else return Root;
    }

    public kd_node findMinimum(kd_node Root, int dimension, int currentDimension) {
        if (Root == null)
            return null;
        if (dimension == currentDimension) {
            if (Root.left_child == null)
                return Root;
            return findMinimum(Root.left_child, dimension, (currentDimension + 1) % 2);
        }
        kd_node leftMin = findMinimum(Root.left_child, dimension, (currentDimension + 1) % 2);
        kd_node rightMin = findMinimum(Root.right_child, dimension, (currentDimension + 1) % 2);
        kd_node min = Root;
        if (rightMin != null && rightMin.point[dimension] < min.point[dimension])
            min = rightMin;
        if (leftMin != null && leftMin.point[dimension] < min.point[dimension])
            min = leftMin;
        return min;
    }

    public kd_node delete(int[] point) {
        if (root == null)
            return null;
        else return delete(root, point, 0);
    }

    public kd_node delete(kd_node currRoot, int[] point, int dimension) {
        if (currRoot == null) {
            return null;
        }
        if (point[0] == currRoot.point[0] & point[1] == currRoot.point[1]) {
            if (currRoot.is_main) {
                System.out.println("You can't delete a main bank!");
                return null;
            }
            if (currRoot.right_child != null) {
                kd_node rightMin = findMinimum(currRoot.right_child, dimension, (dimension + 1) % 2);
                currRoot.point = rightMin.point;
                currRoot.name = rightMin.name;
                currRoot.is_main = rightMin.is_main;
                int[] temp = new int[2];
                temp[0] = rightMin.point[0];
                temp[1] = rightMin.point[1];
                currRoot.right_child = delete(currRoot.right_child, temp, (dimension + 1) % 2);
            } else if (currRoot.left_child != null) {
                kd_node leftMin = findMinimum(currRoot.left_child, dimension, (dimension + 1) % 2);
                currRoot.point = leftMin.point;
                currRoot.name = leftMin.name;
                currRoot.is_main = leftMin.is_main;
                int[] temp = new int[2];
                temp[0] = leftMin.point[0];
                temp[1] = leftMin.point[1];
                currRoot.right_child = delete(currRoot.left_child, temp, (dimension + 1) % 2);
                currRoot.left_child = null;
            } else {
                return null;
            }
            if (currRoot.left_child != null && currRoot.left_child.point[dimension] >= currRoot.point[dimension] ||
                    currRoot.right_child != null && currRoot.right_child.point[dimension] < currRoot.point[dimension])
                System.out.println("wrong position");
            return currRoot;
        }
        if (point[dimension] < currRoot.point[dimension]) {
            currRoot.left_child = delete(currRoot.left_child, point, (dimension + 1) % 2);
        } else {
            currRoot.right_child = delete(currRoot.right_child, point, (dimension + 1) % 2);
        }
        return currRoot;
    }

    public void inOrder(kd_node node) {
        if (node == null)
            return;
        else {
            inOrder(node.left_child);
            System.out.println("name : " + node.name);
            System.out.println("x : " + node.point[0]);
            System.out.println("y : " + node.point[1]);
            inOrder(node.right_child);
        }
    }

    public void inOrder() {
        inOrder(this.root);
    }

    boolean incircle(kd_node node, double radius, kd_node center) {
        double d = ((node.point[0] - center.point[0]) * (node.point[0] - center.point[0]) +
                (node.point[1] - center.point[1]) * (node.point[1] - center.point[1]));
        return d <= radius * radius;
    }

    public void in_range(kd_node Root, kd_node node, double radius, int dim) {
        if (Root == null)
            return;

        kd_node current = new kd_node(Root.point[0], Root.point[1], Root.name, Root.is_main);

        if (incircle(node, radius, current)) {
            System.out.println("name : " + current.name);
            System.out.println("Address : ( " + current.point[0] + " , " + current.point[1] + " )");
        }
        if (current.point[dim] > (node.point[dim] - radius))
            in_range(Root.left_child, node, radius, (dim + 1) % 2);
        if (current.point[0] < (node.point[0] + radius))
            in_range(Root.right_child, node, radius, (dim + 1) % 2);
    }

    public void in_neighbor(kd_node root, neighborhood neighbor, int dimens) {

        if (root == null) {
            return;
        }
        kd_node node = new kd_node(root.point[0], root.point[1], root.name, root.is_main);
        if (neighbor.contains(node)) {
            System.out.println("name : " + node.name);
            System.out.println("Address : ( " + node.point[0] + " , " + node.point[1] + " )");
        }

        if (root.right_child != null | root.left_child != null) {
            if (dimens == 0) {
                if (neighbor.x_min <= root.point[0] && root.point[0] <= neighbor.x_max) {
                    if (root.right_child != null && root.left_child != null) { // has left & right child
                        in_neighbor(root.right_child, neighbor, (dimens + 1) % 2);
                        in_neighbor(root.left_child, neighbor, (dimens + 1) % 2);
                    } else if (root.right_child != null) {
                        in_neighbor(root.right_child, neighbor, (dimens + 1) % 2);
                    } else if (root.left_child != null) {
                        in_neighbor(root.left_child, neighbor, (dimens + 1) % 2);
                    }
                } else if (root.point[0] >= neighbor.x_max) {
                    if (root.left_child != null)
                        in_neighbor(root.left_child, neighbor, (dimens + 1) % 2);
                } else if (root.point[0] <= neighbor.x_min) {
                    if (root.right_child != null)
                        in_neighbor(root.right_child, neighbor, (dimens + 1) % 2);
                }
            } else {
                if (neighbor.y_min <= root.point[1] && root.point[1] <= neighbor.y_max) {
                    if (root.right_child != null && root.left_child != null) {
                        in_neighbor(root.right_child, neighbor, (dimens + 1) % 2);
                        in_neighbor(root.left_child, neighbor, (dimens + 1) % 2);
                    } else if (root.left_child != null) {
                        in_neighbor(root.left_child, neighbor, (dimens + 1) % 2);
                    } else if (root.right_child != null) {
                        in_neighbor(root.right_child, neighbor, (dimens + 1) % 2);
                    }
                } else if (root.point[1] >= neighbor.y_max) {
                    if (root.left_child != null)
                        in_neighbor(root.left_child, neighbor, (dimens + 1) % 2);
                } else if (root.point[1] <= neighbor.y_min) {
                    if (root.right_child != null)
                        in_neighbor(root.right_child, neighbor, (dimens + 1) % 2);
                }
            }
        }
    }

    public void in_neighbor(neighborhood neighbor) {
        in_neighbor(root,neighbor,0);
    }


    public static void main(String[] args) {
        kd_tree kdTree = new kd_tree();
        kdTree.insert(new kd_node(2, 4, "br1", false));
        kdTree.insert(new kd_node(3, 8, "br2", false));
        kdTree.insert(new kd_node(2, 6, "br3", false));
        kdTree.inOrder();
    }
}
