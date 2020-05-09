import java.util.*;
import java.io.*;

// Class for the record (buildingNum, executed_time, total_time)
class Record {

    int buildingNum;
    int executed_time;
    int total_time;

    // Constructor for the class Record
    public Record(int buildingNum, int executed_time, int total_time) {
        this.buildingNum = buildingNum;
        this.executed_time = executed_time;
        this.total_time = total_time;
    }

}

// Class for Min-Heap
class MinHeap {

    // The main array that stores the entire Heap
    public Record[] Heap;
    // The current size of the Heap
    public int size;
    // The maximum possible size of the Heap
    private int max_poss_size;
    // Heap starts from index 1 of array
    public static final int TOP = 1;

    // Constructor for the class MinHeap
    public MinHeap(int max_poss_size) {
        this.max_poss_size = max_poss_size;
        this.size = 0;
        Heap = new Record[this.max_poss_size + 1];
        Heap[0] = new Record(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    // Function to return position of the left child for the node currently at current
    private int l_child(int current) {
        return current * 2;
    }

    // Function to return position of the right child for the node currently at current
    private int r_child(int current) {
        return current * 2 + 1;
    }

    // Function that returns true if the passed node is a leaf node
    private boolean check_leaf(int current) {
        if(current > size / 2 && current  <= size) {
            return true;
        }
        return false;
    }

    // Function to exchange two nodes of the Heap
    private void exchange(int pos1, int pos2) {
        Record temporary;
        temporary = Heap[pos1];
        Heap[pos1] = Heap[pos2];
        Heap[pos2] = temporary;
    }

    // Function to heapify the Heap
    private void minHeapify(int current) {
        // Condition to check if node at current is not a leaf
        if(!check_leaf(current)) {
            // Condition to check if node at current has a left child and a right child
            if(Heap[l_child(current)] != null && Heap[r_child(current)] != null) {
                // Condition to check if the executed_time of node at current is greater than both of its children
                if(Heap[current].executed_time > Heap[l_child(current)].executed_time || Heap[current].executed_time > Heap[r_child(current)].executed_time) {
                    // Condition to check if the executed_time of left child of node at current is less than the executed_time of right child of node at current
                    if(Heap[l_child(current)].executed_time < Heap[r_child(current)].executed_time) {
                        // Exhchange node at current with its left child and call minHeapify
                        exchange(current, l_child(current));
                        minHeapify(l_child(current));
                    }
                    // Condition to check if executed_time of left child of node at current is greater than the executed_time of right child of node at current
                    else if(Heap[l_child(current)].executed_time > Heap[r_child(current)].executed_time) {
                        // Exhange node at current with its right child and call minHeapify
                        exchange(current, r_child(current));
                        minHeapify(r_child(current));
                    }
                    // Conditin to check if executed_time of left child of node at current is equal to the executed_time of right child of node at current
                    else {
                        // Condition to check if buildingNum of left child of node at current is less than buildingNum of right child of node at current
                        if(Heap[l_child(current)].buildingNum < Heap[r_child(current)].buildingNum) {
                            // Exchange node at current with its left child and minHeapify
                            exchange(current, l_child(current));
                            minHeapify(l_child(current));
                        }
                        // Condition to check if buildingNum of left child of node at current is greater than buildingNum of right child of node at current
                        else {
                            // Exchange node at current with its right child and minHeapify
                            exchange(current,r_child(current));
                            minHeapify(r_child(current));
                        }
                    }
                }
                // Condition to check if executed_time if node at current is equal to executed_time of its left child and its right child
                else if(Heap[current].executed_time == Heap[l_child(current)].executed_time && Heap[current].executed_time == Heap[r_child(current)].executed_time) {
                    // Condition to check if buildingNum of node at current is greater than buildingNum of its left child and if buildingNum of left child is less than buildingNum of right child
                    if(Heap[l_child(current)].buildingNum < Heap[current].buildingNum && Heap[r_child(current)].buildingNum > Heap[l_child(current)].buildingNum) {
                        // Exchange node at current with its left child and minHeapify
                        exchange(current, l_child(current));
                        minHeapify(l_child(current));
                    }
                    // Condition to check if buildingNum of node at current is greater than buildingNum of its right child and if buildingNum of right child is less than buildingNum of its left child
                    else if(Heap[r_child(current)].buildingNum < Heap[current].buildingNum && Heap[r_child(current)].buildingNum < Heap[l_child(current)].buildingNum) {
                        // Exchange node at current with its right child and minHeapify
                        exchange(current, r_child(current));
                        minHeapify(r_child(current));
                    }
                }
                // Condition to check if executed_time of node at current is equal to executed_time at its left child
                else if(Heap[current].executed_time == Heap[l_child(current)].executed_time) {
                    // Condition to check if buildingNum of node at current is greater than buildingNum of its left child
                    if(Heap[current].buildingNum > Heap[l_child(current)].buildingNum) {
                        // Exchange node at current with its left child and minHeapify
                        exchange(current, l_child(current));
                        minHeapify(l_child(current));
                    }
                }
                // Condition to check if executed_time of node at current is equals to executed_time of its right child
                else if(Heap[current].executed_time == Heap[r_child(current)].executed_time) {
                    // Condition to check if buildingNum of node at current is greater than buildingNum of its right child
                    if(Heap[current].buildingNum > Heap[r_child(current)].buildingNum) {
                        // Exchange node at current with its right child and minHeapify
                        exchange(current, r_child(current));
                        minHeapify(r_child(current));
                    }
                }
            }
            // Condition to check if node at current has only a left child and not a right child
            else if(Heap[l_child(current)] != null && Heap[r_child(current)] == null) {
                // Condition to check if executed_time of node at current is greater than executed_time of its left child
                if(Heap[current].executed_time > Heap[l_child(current)].executed_time) {
                    // Exchange node at current with its left child and minHeapify
                    exchange(current, l_child(current));
                    minHeapify(l_child(current));
                }
                // Condition to check if executed_time of node at current is equal to executed_time of its left child
                else if(Heap[current].executed_time == Heap[l_child(current)].executed_time) {
                    // Condition to check if buildingNum of node at current is greater than buildingNum of its left child
                    if(Heap[current].buildingNum > Heap[l_child(current)].buildingNum) {
                        // Exchange node at current with its left child and minHeapify
                        exchange(current, l_child(current));
                        minHeapify(l_child(current));
                    }
                }
            }
            // Condition to check if node at current has only a right child and not a left child
            else if(Heap[l_child(current)] == null && Heap[r_child(current)] != null) {
                // Condition to check if executed_time of node at current is greater than executed_time of its right child
                if(Heap[current].executed_time > Heap[r_child(current)].executed_time) {
                    // Exchange node at current with its right child and minHeapify
                    exchange(current, r_child(current));
                    minHeapify(r_child(current));
                }
                // Condition to check if executed_time of node at current is equal to executed_time of its right child
                else if(Heap[current].executed_time == Heap[r_child(current)].executed_time) {
                    // Condition to check if buildingNum of node at current is greater than buildingNum of its right child
                    if(Heap[current].buildingNum > Heap[r_child(current)].buildingNum) {
                        // Exchange node at current with its right child and minHeapify
                        exchange(current, r_child(current));
                        minHeapify(r_child(current));
                    }
                }
            }
        }
    }

    // Function to insert a new node into the heap
    public void insert(Record element) {
        // Condition to check if size of Heap is greater than or equal to maximum size of Heap
        if(size >= max_poss_size) {
            // Return from the function
            return;
        }
        // Increase size of the Heap if and insert if performed
        Heap[++size] = element;
    }

    // Function to build the min-heap using minHeapify
    public void minHeapFunc() {
        // Loop on all non leaf nodes, i.e. from size of heap divided by 2 to 1
        for(int current = size / 2; current >= 1; current --) {
            // Call minHeapify on all non leaf nodes
            minHeapify(current);
        }
    }

    // Function to remove and return the minimum element from the heap
    public Record remove() {
        // Retrieve the element to be removed from the Heap
        Record peekedRecord = Heap[TOP];
        // Condition to check if size of the Heap is greater than 0
        if(size > 0) {
            // Make the top of the heap null and call minHeapify
            Heap[TOP] = Heap[size--];
            Heap[size + 1] = null;
            minHeapify(TOP);
        }
        // Return the Record removed at the top of the Heap
        return peekedRecord;
    }
}

// Class for COLOR_RED COLOR_BLACK Tree
class RedBlackTree {

    // Color of the node is indicated by this variable
    private final int COLOR_RED = 0;
    private final int COLOR_BLACK = 1;

    // Class for node structure of the COLOR_RED COLOR_BLACK Tree
    private class rbt_element {
        Record record_element;
        int color = COLOR_BLACK;
        rbt_element l_child = external;
        rbt_element r_child = external;
        rbt_element parent_node = external;
        rbt_element(Record record_element) {
            this.record_element = record_element;
        }
    }

    // Declare node external
    private final rbt_element external = new rbt_element(new Record(-1, -1, -1));
    private rbt_element root = external;

    // Function to check if given node is in between the given range
    boolean inRange(int bNo, int lower, int higher) {
        // Condition to check if bNo is between lower and higher
        if(bNo >= lower && bNo <= higher) {
            // Return true
            return true;
        }
        // Return false
        return false;
    }

    // Function to recursively print all nodes in the given range
    String printRecursively(rbt_element current, int lower, int higher) {
        // Condition to check of current is equal to null
        if(current == external) {
            // Return empty string
            return "";
        }
        // Condition to check if buildingNum of current is equal to lower
        if(current.record_element.buildingNum == lower) {
            // Recurse printRecursively function on r_child child of current
            return(printRecursively(current.r_child, lower, higher) + "(" + current.record_element.buildingNum + "," + current.record_element.executed_time + "," + current.record_element.total_time + "),");
        }
        // Condition to check if buildingNum of current is equal to higher
        else if(current.record_element.buildingNum == higher) {
            // Recurse printRecursively function on l_child child of current
            return(printRecursively(current.l_child, lower, higher) + "(" + current.record_element.buildingNum + "," + current.record_element.executed_time + "," + current.record_element.total_time + "),");
        }
        else {
            // Intitialize result string as empty
            String result = "";
            // Condition to check if l_child child of current is not null and buildingNum of l_child child of current is in between lower and higher
            if(current.l_child != external && inRange(current.l_child.record_element.buildingNum, lower, higher)) {
                // Append result of printRecursively Function to result
                result += printRecursively(current.l_child, lower, higher);
            }
            // Condition to check if buildingNum of current is in between lower and higher
            if(inRange(current.record_element.buildingNum, lower, higher)) {
                // Append current node buildingNum, executed_time and total_time ro result
                result += "(" + current.record_element.buildingNum + "," + current.record_element.executed_time + "," + current.record_element.total_time + "),";
            }
            // Condition to check if r_child child of current is not null and buildingNum of rigth child of current is between lower and higher
            if(current.r_child != external && inRange(current.r_child.record_element.buildingNum, lower, higher)) {
                // Append the result of printRecursively function to result
                result += printRecursively(current.r_child, lower, higher);
            }
            // Return the result
            return result;
        }
    }

    // Function to print all nodes in the given range
    String printInRange(int lower, int higher) {
        // Assign current as root
        rbt_element current = root;
        // Initialize result as empty string
        String result = "";
        // Loop while current is not null
        while(current != external) {
            // Condition to check if buildingNum of current is greater than or equal to lower and less than or equal to higher
            if(current.record_element.buildingNum >= lower && current.record_element.buildingNum <= higher) {
                // Assign result as result of printRecursively function
                result = printRecursively(current, lower, higher);
                // Return the result
                return result;
            }
            // Condition to check if buildingNum of current is less than lower
            else if(current.record_element.buildingNum < lower) {
                // Assign current as r_child child or current
                current = current.r_child;
            }
            else {
                // Assign current as l_child child of current
                current = current.l_child;
            }
        }
        // Return the result
        return "(0,0,0),";
    }

    // Function to print a specific node
    String printNode(int bNo) {
        // Intitialize current as root
        rbt_element current = root;
        // Intitialize result as empty string
        String result = "";
        // Loop while current is not null
        while(current != external) {
            // Condition to check if buildingNum is equal to bNo
            if(current.record_element.buildingNum == bNo) {
                // Assign result as current node buildingNum, executed_time and total_time
                return("(" + current.record_element.buildingNum + "," + current.record_element.executed_time + "," + current.record_element.total_time + ")");
            }
            // Condition to check if bNo is greater than buildingNum of current
            else if(bNo > current.record_element.buildingNum) {
                // Assign current as r_child child of current
                current = current.r_child;
            }
            else {
                // Assign current as l_child child of current
                current = current.l_child;
            }
        }
        return "(0,0,0)";
    }

    // Function to find a particular node in the COLOR_RED COLOR_BLACK Tree
    private rbt_element nodeSearch(rbt_element currNode, rbt_element node) {
        // Condition to check if root is external
        if (root == external) {
            // Return null
            return null;
        }
        // Condition to check if buildingNum of currNode is less than buildingNum of node
        if (currNode.record_element.buildingNum < node.record_element.buildingNum) {
            // Condition to check if l_child child of node is not external
            if (node.l_child != external) {
                // Recurse nodeSearch on l_child child of node
                return nodeSearch(currNode, node.l_child);
            }
        }
        // Condition to check if buildingNum of currNode is greater than buildingNum of node
        else if (currNode.record_element.buildingNum > node.record_element.buildingNum) {
            // Condition to check if rigth child of node is not external
            if (node.r_child != external) {
                // Rercurse nodeSearch on r_child child of node
                return nodeSearch(currNode, node.r_child);
            }
        }
        // Condition to check if buildingNum of currNode is equal to buildingNum of node
        else if (currNode.record_element.buildingNum == node.record_element.buildingNum) {
            // Return node
            return node;
        }
        // Return null if none of the above conditiond were satisfied
        return null;
    }

    // Function to insert a node into the COLOR_RED COLOR_BLACK Tree
    private void insert(rbt_element node) {
        // Assign temp as root of the tree
        rbt_element temp = root;
        // Condition to check if root is external
        if (root == external) {
            // Make node the root of the COLOR_RED COLOR_BLACK Tree with a external parent_node and assign it the color COLOR_BLACK
            root = node;
            node.color = COLOR_BLACK;
            node.parent_node = external;
        }
        // Condition to check if root is not external
        else {
            // Assign node the color COLOR_RED since all inserted nodes start with the color COLOR_RED
            node.color = COLOR_RED;
            // while(true) loop
            while (true) {
                // Condition to check if buildingNum of node is less than buildingNum of temp
                if (node.record_element.buildingNum < temp.record_element.buildingNum) {
                    // Condition to check if l_child child of temp is external
                    if (temp.l_child == external) {
                        // Make node the l_child child of the root and assign its parent_node as temp and break the loop
                        temp.l_child = node;
                        node.parent_node = temp;
                        break;
                    }
                    // Condition to check if l_child child of temp is not external
                    else {
                        // Recurse down the l_child subtree of temp
                        temp = temp.l_child;
                    }
                }
                // Condition to check if buildingNum of ndoe is greater than or equal to buildingNum of temp
                else if (node.record_element.buildingNum >= temp.record_element.buildingNum) {
                    // Condition to check if r_child child of temp is external
                    if (temp.r_child == external) {
                        // Make node the r_child child of temp and assign its parent_node as temp and break the loop
                        temp.r_child = node;
                        node.parent_node = temp;
                        break;
                    }
                    // Condition to check if r_child child of temp is not external
                    else {
                        // Recurse down the r_child subtree of temp
                        temp = temp.r_child;
                    }
                }
            }
            // Call insertFix function
            insertFix(node);
        }
    }

    // Function to fix the COLOR_RED COLOR_BLACK tree
    private void insertFix(rbt_element node) {
        // Loop while parent_node of node is color COLOR_RED
        while (node.parent_node.color == COLOR_RED) {
            // Assign external to uncle of node, i.e. sibling of parent_node of parent_node of node
            rbt_element uncle = external;
            // Condition to check if parent_node of node is equal to uncle of node
            if (node.parent_node == node.parent_node.parent_node.l_child) {
                // Assign uncle to the sibling of parent_node of parent_node of node
                uncle = node.parent_node.parent_node.r_child;
                // Condition to check if uncle is not external and uncle is of color COLOR_RED
                if (uncle != external && uncle.color == COLOR_RED) {
                    // Make the color of the parent_node of node COLOR_BLACK
                    node.parent_node.color = COLOR_BLACK;
                    // Make the color of the uncle of node COLOR_BLACK
                    uncle.color = COLOR_BLACK;
                    // Make the color of the grand parent_node of node COLOR_RED
                    node.parent_node.parent_node.color = COLOR_RED;
                    // Assign node as its grand parent_node
                    node = node.parent_node.parent_node;
                    // Continue the loop
                    continue;
                }
                // Condition to check if node is equal to its sibling
                if (node == node.parent_node.r_child) {
                    // Assign node as its parent_node
                    node = node.parent_node;
                    // Call leftRotation function to perfrom rotation on the tree
                    leftRotation(node);
                }
                // Make the color of the parent_node of node COLOR_BLACK
                node.parent_node.color = COLOR_BLACK;
                // Make the color of the grand parent_node of nofr COLOR_RED
                node.parent_node.parent_node.color = COLOR_RED;
                // Call rightRotation function to perform rotation on the tree
                rightRotation(node.parent_node.parent_node);
            }
            // Condition to check if parent_node of node is not equal to uncle of node
            else {
                // Assign uncle to the sibling of parent_node of parent_node of node
                uncle = node.parent_node.parent_node.l_child;
                // Condition to check if uncle is not external and color of uncle is COLOR_RED
                if (uncle != external && uncle.color == COLOR_RED) {
                    // Make the color of parent_node of node COLOR_BLACK
                    node.parent_node.color = COLOR_BLACK;
                    // Make the color of uncle COLOR_BLACK
                    uncle.color = COLOR_BLACK;
                    // Make the color of grand parent_node of node COLOR_RED
                    node.parent_node.parent_node.color = COLOR_RED;
                    // Assign node as its grand parent_node
                    node = node.parent_node.parent_node;
                    // Continue the loop
                    continue;
                }
                // Condition to check if node is equal to its sibling
                if (node == node.parent_node.l_child) {
                    // Assign node as its parent_node
                    node = node.parent_node;
                    // Call rightRotation function to perform rotation on the tree
                    rightRotation(node);
                }
                // Make the color of the parent_node of node COLOR_BLACK
                node.parent_node.color = COLOR_BLACK;
                // Make the color of the grand parent_node of node COLOR_RED
                node.parent_node.parent_node.color = COLOR_RED;
                // Call leftRotation function to perform rotation on the tree
                leftRotation(node.parent_node.parent_node);
            }
        }
        // Make the color of the root COLOR_BLACK
        root.color = COLOR_BLACK;
    }

    // Function to rotate l_child
    void leftRotation(rbt_element node) {
        // Condition to check if parent_node of node is not external
        if (node.parent_node != external) {
            // Condition to check if node is equal to sibling of node
            if (node == node.parent_node.l_child) {
                // Assign sibling of node as r_child child of node
                node.parent_node.l_child = node.r_child;
            }
            // Condition to check if node is not equal to sibling of node
            else {
                // Assign sibling of node as r_child child of node
                node.parent_node.r_child = node.r_child;
            }
            // Assign sibling of node as parent_node of node
            node.r_child.parent_node = node.parent_node;
            // Assign parent_node of node as r_child child of node
            node.parent_node = node.r_child;
            // Condition to check if l_child child of r_child child of node is not external
            if (node.r_child.l_child != external) {
                // Assign parent_node of l_child child of r_child child of node as node
                node.r_child.l_child.parent_node = node;
            }
            // Assign r_child child of node as l_child child of r_child child of node
            node.r_child = node.r_child.l_child;
            // Assign sibling of node as node
            node.parent_node.l_child = node;
        }
        // Condition to check if parent_node of node is external
        else {
            // Assign r_child as riught child of root
            rbt_element r_child = root.r_child;
            // Assign r_child child of root as l_child child of r_child
            root.r_child = r_child.l_child;
            // Assign parent_node of l_child child of r_child as root
            r_child.l_child.parent_node = root;
            // Assign parent_node of root as r_child
            root.parent_node = r_child;
            // Assign l_child child of r_child as root
            r_child.l_child = root;
            // Assign parent_node of r_child as external
            r_child.parent_node = external;
            // Assign root as r_child
            root = r_child;
        }
    }

    // Function to rotate r_child
    void rightRotation(rbt_element node) {
        // Condition to check if parent_node of node is not external
        if (node.parent_node != external) {
            // Condition to check if node is equal to sibling of node
            if (node == node.parent_node.l_child) {
                // Assign sibling of node as l_child child of node
                node.parent_node.l_child = node.l_child;
            }
            // Condition to check if node is not equal to sibling of node
            else {
                // Assign sibling of node as l_child child of node
                node.parent_node.r_child = node.l_child;
            }
            // Assign parent_node of l_child child of node as parent_node of node
            node.l_child.parent_node = node.parent_node;
            // Assign parent_node of node as l_child child of node
            node.parent_node = node.l_child;
            // Condition to check if r_child child of l_child child of node is not external
            if (node.l_child.r_child != external) {
                // Assign parent_node of r_child child of l_child child of node as node
                node.l_child.r_child.parent_node = node;
            }
            // Assign l_child child of node as r_child child of l_child child ot node
            node.l_child = node.l_child.r_child;
            // Assign sibling of node as node
            node.parent_node.r_child = node;
        }
        // Condition to check if parent_node of node is external
        else {
            // Assign l_child as l_child child of root
            rbt_element l_child = root.l_child;
            // Assign l_child child of root as r_child child of l_child child of root
            root.l_child = root.l_child.r_child;
            // Assign parent_node of r_child child of l_child as root
            l_child.r_child.parent_node = root;
            // Assign parent_node of root as l_child
            root.parent_node = l_child;
            // Assign as r_child child of l_child as root
            l_child.r_child = root;
            // Assign parent_node of l_child as external
            l_child.parent_node = external;
            // Assign root as l_child
            root = l_child;
        }
    }

    // Function to transplant the Red Black Tree
    void transplant(rbt_element target, rbt_element with){
        // Condition to check if parent_node of target is equal to external
        if(target.parent_node == external){
            // Assign root as with
            root = with;
        }
        // Condition to check if target is equal to l_child child of parent_node of target
        else if(target == target.parent_node.l_child){
            // Assign l_child child of parent_node of target as with
            target.parent_node.l_child = with;
        }
        // Condition to check if target is not equal to l_child child of parent_node of target
        else {
            // Assign r_child child of parent_node of target as with
            target.parent_node.r_child = with;
        }
        // Assign parent_node of with as parent_node of target
        with.parent_node = target.parent_node;
    }

    // Function to delte a node fromt the COLOR_RED COLOR_BLACK Tree
    boolean delete(rbt_element c){
        // Condition to check c is not there in the tree
        if((c = nodeSearch(c, root)) == null) {
            // Return false
            return false;
        }
        rbt_element a;
        rbt_element b = c;
        int b_original_color = b.color;

        // Condition to check if l_child child of c is external
        if(c.l_child == external){
            // Assign a as the rigth child of c
            a = c.r_child;
            // Call transplant function on the r_child child of c
            transplant(c, c.r_child);
        }
        // Condition to check if r_child child of c is external
        else if(c.r_child == external){
            // Assign a as l_child child of c
            a = c.l_child;
            // Call transplant function on the l_child child of c
            transplant(c, c.l_child);
        }
        else{
            // Assign b as minimum node in the rigth subtree of r_child child of c
            b = findMinimumNode(c.r_child);
            // Assign y_original_color as color of b
            b_original_color = b.color;
            // Assign a as the rigth child of b
            a = b.r_child;
            // Condition to check if parent_node of b is equal to c
            if(b.parent_node == c) {
                // Assign parent_node of a as b
                a.parent_node = b;
            }
            // Condition to check if parent_node of b is not qual to c
            else{
                // Call transplant function on r_child child of b
                transplant(b, b.r_child);
                // Assign rigth child of b as rigth child of c
                b.r_child = c.r_child;
                // Assign parent_node of r_child child of b as b
                b.r_child.parent_node = b;
            }
            // Call transplant function on b
            transplant(c, b);
            // Assign l_child child of b as l_child child of c
            b.l_child = c.l_child;
            // Assign parent_node of l_child child of b as b
            b.l_child.parent_node = b;
            // Assign color of b as color of c
            b.color = c.color;
        }
        // Condition to check if y_original_color is equal to COLOR_BLACK
        if(b_original_color==COLOR_BLACK) {
            // Call deleteFix function on a
            deleteFix(a);
        }
        // return true on successfull delet
        return true;
    }

    // Function to fix the tree after a delete
    void deleteFix(rbt_element a){
        // Loop while a is not equal to the root and color of a is equal to COLOR_BLACK
        while(a!=root && a.color == COLOR_BLACK) {
            // Condition to check if a is equal to l_child child of parent_node of a
            if(a == a.parent_node.l_child) {
                // Assign w as r_child child of parent_node of a
                rbt_element w = a.parent_node.r_child;
                // Condition to check if color of w is COLOR_RED
                if(w.color == COLOR_RED){
                    // Assign color of w as COLOR_BLACK
                    w.color = COLOR_BLACK;
                    // Assign color of parent_node of a as COLOR_RED
                    a.parent_node.color = COLOR_RED;
                    // Call leftRotation function to rotate the tree
                    leftRotation(a.parent_node);
                    // Assign w as r_child child of parent_node of a
                    w = a.parent_node.r_child;
                }
                // Condition to check of color of l_child child of w is equal to COLOR_BLACK and color of r_child child of w is equal to COLOR_BLACK
                if(w.l_child.color == COLOR_BLACK && w.r_child.color == COLOR_BLACK){
                    // Assign color of w as COLOR_RED
                    w.color = COLOR_RED;
                    // Assign a as parent_node of a
                    a = a.parent_node;
                    // Continue the loop
                    continue;
                }
                // Condition to check if color of r_child child of w is equal to COLOR_BLACK
                else if(w.r_child.color == COLOR_BLACK){
                    // Assign color of l_child child of w as COLOR_BLACK
                    w.l_child.color = COLOR_BLACK;
                    // Asssign color of w as COLOR_RED
                    w.color = COLOR_RED;
                    // Call rightRotation function to rotate the tree
                    rightRotation(w);
                    // Assign w as r_child child of parent_node of a
                    w = a.parent_node.r_child;
                }
                // Condition to check if color of r_child child of w is equal to COLOR_RED
                if(w.r_child.color == COLOR_RED){
                    // Assign color of q as color of parent_node of a
                    w.color = a.parent_node.color;
                    // Assign color of parent_node of a as COLOR_BLACK
                    a.parent_node.color = COLOR_BLACK;
                    // Assign color of rigth os a as COLOR_BLACK
                    w.r_child.color = COLOR_BLACK;
                    // Call leftRotation function on parent_node of a
                    leftRotation(a.parent_node);
                    // Assign a as the root
                    a = root;
                }
            }
            // Condition to check if a is not equal to l_child child of parent_node of a
            else{
                //Assign w as l_child child of parent_node of a
                rbt_element w = a.parent_node.l_child;
                // Condition to check of color of w is COLOR_RED
                if(w.color == COLOR_RED){
                    // Assign color of w as COLOR_BLACK
                    w.color = COLOR_BLACK;
                    // Assign color of parent_node of a as COLOR_RED
                    a.parent_node.color = COLOR_RED;
                    // Call rightRotation function to rotate the tree
                    rightRotation(a.parent_node);
                    // Assign w as l_child child of parent_node of a
                    w = a.parent_node.l_child;
                }
                // Condition to check if color of r_child child of w is equal to COLOR_BLACK and color of l_child child of w is equal to COLOR_BLACK
                if(w.r_child.color == COLOR_BLACK && w.l_child.color == COLOR_BLACK){
                    // Assign color of w as COLOR_RED
                    w.color = COLOR_RED;
                    // Assign a as parent_node of a
                    a = a.parent_node;
                    // Continue the loop
                    continue;
                }
                // Condition to check if color of l_child child of w is equal to COLOR_BLACK
                else if(w.l_child.color == COLOR_BLACK){
                    // Assign color of r_child child of w as COLOR_BLACK
                    w.r_child.color = COLOR_BLACK;
                    // Assign color of w as COLOR_RED
                    w.color = COLOR_RED;
                    // Call leftRotation function to rotate the tree
                    leftRotation(w);
                    // Assign w as l_child child of parent_node of a
                    w = a.parent_node.l_child;
                }
                // Condition to check if colot of l_child child od w is equal to COLOR_RED
                if(w.l_child.color == COLOR_RED){
                    // Assign color of w as color of parent_node of a
                    w.color = a.parent_node.color;
                    // Assign color of parent_node of a as COLOR_BLACK
                    a.parent_node.color = COLOR_BLACK;
                    // Assign color of l_child chilf of w as COLOR_BLACK
                    w.l_child.color = COLOR_BLACK;
                    // Call rightRotation function to rotate the tree
                    rightRotation(a.parent_node);
                    // Assign a as the root
                    a = root;
                }
            }
        }
        // Assign color og a as COLOR_BLACK
        a.color = COLOR_BLACK;
    }

    // Function to find the minimum node in the tree rooted at subTreeRoot
    rbt_element findMinimumNode(rbt_element subTreeRoot){
        // Loop while l_child child od subTreeRoot is not external
        while(subTreeRoot.l_child != external){
            // Change subTreeRoot to l_child child of subTreeRoot to recurse down the l_child subtree
            subTreeRoot = subTreeRoot.l_child;
        }
        // return the subTreeRoot, i.e. the smallest value in the tree rooted at subTreeRoot
        return subTreeRoot;
    }

    // Function acting as the driver for COLOR_RED COLOR_BLACK Tree class
    public void redBlackTreeHelper(int choice, Record item) {
        rbt_element node;
        // Switch case on choice
        switch (choice) {
            // Condition to check if choice is 2
            case 1:
                // Assign node as a new node made by item
                node = new rbt_element(item);
                // Insert node into the Heap
                insert(node);
                // Break the case
                break;
            // Condition to check if choice 2
            case 2:
                // Assign node as a new node made by item
                node = new rbt_element(item);
                // Condition to check if node was not deleted
                if (!delete(node)) {
                    // Print an error
                    System.out.print("ERROR: does not exist!");
                }
                // Break the case
                break;
            //case 4:
            //    break;
        }
    }

}

// Class used as Driver Code
class risingCity {

    public static void main(String[] args) {
        // Initialize minHeap
        MinHeap minHeap = new MinHeap(2001);
        // Intialize COLOR_RED COLOR_BLACK Tree
        RedBlackTree redBlackTree = new RedBlackTree();
        // Try block
        try {
            // Intitalize inputFile to null
            File inputFile = null;
            // Condition to check if args is empty
            if(args.length == 0) {
                // Print ERROR that no filename was entered
                System.out.println("ERROR: No filename entered.");
                // Exit the program
                System.exit(0);
            }
            // Condition to check if args has one element
            else if(args.length == 1) {
                // Try block
                try {
                    // Assign input file as a new file with name passed in args
                    inputFile = new File(args[0]);
                    // Condition to check if inputFile exists or not
                    if(!inputFile.exists()) {
                        // Throw FileNotFoundException exception
                        throw new FileNotFoundException();
                    }
                }
                // Catch block
                catch(FileNotFoundException fnfe) {
                    // Print ERROR that filename entered was wrong
                    System.out.println("ERROR: Enter correct filename.");
                    // Exit the program
                    System.exit(0);
                }
            }
            // Condition to check if args has more than one element
            else {
                // Print ERROR that there are too may parameters
                System.out.println("ERROR: Too many parameters.");
                // Exit the program
                System.exit(0);
            }
            // Assign scanner on inputFile
            Scanner sc = new Scanner(inputFile);
            // Assign line as first line in the inputFile
            String line = sc.nextLine();
            // Split line on '\\W'
            String contents[] = line.split("\\W");
            // Assign r1 as new record with contents of first line
            Record r1 = new Record(Integer.parseInt(contents[3]), 0, Integer.parseInt(contents[4]));
            // Insert r1 into the Heap
            minHeap.insert(r1);
            // Insert r1 into the COLOR_RED COLOR_BLACK Tree
            redBlackTree.redBlackTreeHelper(1, r1);
            // Get the next line of inputFile
            line = sc.nextLine();
            // Intitialize global_counter as 1
            int global_counter = 1;

            // Intitalize waitList as an empty ArrayList
            // This is used to store nodes that are not yet to be inserted
            List<Record> waitList = new ArrayList<>();
            // Intialize bufferedWriter to new BufferedWriter on new FileWriter on output file
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output_file.txt"));
            // Loop while file end is not reached
            while(line != null) {
                // Condition to check if size of Heap is 0
                if(minHeap.size == 0) {
                    // Condition to check if size of waitList is greater than 0
                    if(waitList.size() > 0) {
                        // Loop while size of waitList becomes 0
                        while(waitList.size() > 0) {
                            // Assign temp as first element of waitList
                            Record temp = waitList.remove(0);
                            // Insert temp into the Heap
                            minHeap.insert(temp);
                        }
                    }
                    // Call minHeapFunc to minHeapify the Heap
                    minHeap.minHeapFunc();
                }
                // Condition to check if size of minHeap is greater than 0 and executed_time and total_time of root element of minHeap is equal
                if(minHeap.size > 0 && minHeap.Heap[minHeap.TOP].executed_time == minHeap.Heap[minHeap.TOP].total_time) {
                    //Assign removedRecord as element that is removed from the Heap
                    Record removedRecord = minHeap.remove();
                    // Write to the file the completed building triplet
                    bufferedWriter.write("(" + removedRecord.buildingNum + "," + (global_counter - 1) + ")\n");
                    // Remove the node from the COLOR_RED COLOR_BLACK Tree as well
                    redBlackTree.redBlackTreeHelper(2, removedRecord);
                }
                // Condition to check if size of minHeap is greater than 0, executed_time of root element of Heap is a multiple of 5 and that it is not 0
                if(minHeap.size > 0 && minHeap.Heap[minHeap.TOP].executed_time % 5 == 0 && minHeap.Heap[minHeap.TOP].executed_time != 0) {
                    // Condition to check if size of waitList is greater than 0
                    if(waitList.size() > 0) {
                        // Loop while size of waitList is greater than 0
                        while(waitList.size() > 0) {
                            // Assign temp as first element of waitList
                            Record temp = waitList.remove(0);
                            // Insert temp into the waitList
                            minHeap.insert(temp);
                        }
                    }
                    // Call minHeapFunc to minHeapify the Heap
                    minHeap.minHeapFunc();
                }
                // Condition to check if size of Heap is greater than 0
                if(minHeap.size > 0) {
                    // Icrement executed_time of root element of Heap by 1
                    minHeap.Heap[minHeap.TOP].executed_time++;
                }
                // Split line on '\\W'
                contents = line.split("\\W");
                // Condition to check if time in inputFile is equal to global counter
                if(Integer.parseInt(contents[0]) == global_counter) {
                    // Condition to check if file has a next line
                    if(sc.hasNextLine()) {
                        // Assign line as nex line of the inputFile
                        line = sc.nextLine();
                    }
                    // Condition to check if file does not have a next line
                    else {
                        // Assign line as null
                        line = null;
                    }
                    // Condition to check if command is PrintBuilding
                    if(contents[2].equals("PrintBuilding") || contents[2].equals("Print")) {
                        // Condition to check if length of contents is 4
                        if(contents.length == 4) {
                            // Assign result as return value of printNode function on buildingNum
                            String result = redBlackTree.printNode(Integer.parseInt(contents[3])) + "\n";
                            // Write result to output file
                            bufferedWriter.write(result);
                        }
                        // Condition to check if length of contents is not 4
                        else {
                            // Assign result as return value of printInRange function on both buildingNums
                            String result = redBlackTree.printInRange(Integer.parseInt(contents[3]), Integer.parseInt(contents[4]));
                            // Assign result as substring of result without last character
                            result = result.substring(0, result.length() - 1);
                            // Append new line to result
                            result += "\n";
                            // Write result to output file
                            bufferedWriter.write(result);
                        }
                    }
                    // Condition to check if command is Insert
                    else {
                        // Assign r2 as new record with contents of current line
                        Record r2 = new Record(Integer.parseInt(contents[3]), 0, Integer.parseInt(contents[4]));
                        // Add r2 to waitList
                        waitList.add(r2);
                        // Inert r2 into the COLOR_RED COLOR_BLACK tree
                        redBlackTree.redBlackTreeHelper(1, r2);
                    }
                }
                // Increment global_counter by 1
                global_counter++;
            }
            // Decrement global_counter by 1
            global_counter--;
            // Loop while size of size of the Heap is greater than 0
            while(minHeap.size > 0) {
                // Condition to check if executed_time and total_time of root element of Heap are equal
                if(minHeap.Heap[minHeap.TOP].executed_time == minHeap.Heap[minHeap.TOP].total_time) {
                    // Assign removedRecord as root element of Heap and remove it
                    Record removedRecord = minHeap.remove();
                    // Write removedRecord triplet to the output file
                    bufferedWriter.write("(" + removedRecord.buildingNum + "," + global_counter + ")\n");
                    // Condition to check if size of Heap is 0
                    if(minHeap.size == 0) {
                        // Break the while loop
                        break;
                    }
                }
                // Condition to check if executed_time of root element of Heap is a multiple of 5
                else if(minHeap.Heap[minHeap.TOP].executed_time % 5 == 0) {
                    // Call minHeapFunc function to minHeapify the Heap
                    minHeap.minHeapFunc();
                }
                // Increment global_counter By 1
                global_counter++;
                // Increment executed_time of root element of Heap by 1
                minHeap.Heap[minHeap.TOP].executed_time++;
            }
            // Close the bufferedWriter
            bufferedWriter.close();
        }
        // Catch block to catch any exception
        catch(Exception e) {
            // Print stack trace
            e.printStackTrace();
        }
    }

}
