import java.io.*;
import java.util.*;
import java.lang.*;

public class Main {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final BufferedWriter WRITER = new BufferedWriter(new OutputStreamWriter(System.out));
    // private static final HashMap<String, Cat> catsByName = new HashMap<>(200002);
    // private static final TreeMap<Cat, Cat> cats = new TreeMap<>((a, b) -> { // The value is never used
    //     int infectionDiff = b.infection - a.infection;
    //     if (infectionDiff == 0) {
    //         return a.timeArrived - b.timeArrived;
    //     } else {
    //         return infectionDiff;
    //     }
    // });
    private static final TreeSet<Cat> cats = new TreeSet<>((a, b) -> {
        int infectionDiff = b.infection - a.infection;
        if (infectionDiff == 0) {
            return a.timeArrived - b.timeArrived;
        } else {
            return infectionDiff;
        }
    });
    private static int catsInClinic = 0;

    public static void main(String[] args) throws IOException {
        BalancedBST<Integer> test = new BalancedBST<>((a, b) -> a - b);
        test.push(3);
        test.push(1);
        test.push(5);
        test.push(0);
        test.push(2);
        test.push(4);
        test.push(8);
        test.push(6);
        test.push(9);
        test.push(7);
        System.out.println(test.pop());
        System.out.println(test.pop());
        System.out.println(test.pop());
        System.out.println(test.pop());
        System.out.println(test.pop());
        System.out.println(test.pop());
        System.out.println(test.pop());
        System.out.println(test.pop());
        System.out.println(test.pop());
        System.out.println(test.pop());

        return;

        /*int numCommands = Integer.parseInt(READER.readLine());
        for (; numCommands > 0; numCommands--) {
            String[] command = READER.readLine().split(" ");
            int type = Integer.parseInt(command[0]);
        
            if (type == 0) { // Arrive at clinic
                int infection = Integer.parseInt(command[2]);
                Cat newCat = new Cat(command[1], catsInClinic++, infection);
                cats.add(newCat); // Maybe a problem if cat arrives at clinic multiple times
                // cats.put(newCat, newCat);
                // catsByName.put(command[1], newCat);
            } else if (type == 1) { // Update infection level
                // Cat cat = cats.remove(catsByName.get(command[1]));
                // cat.infection += Integer.parseInt(command[2]);
                // cats.put(cat, cat);
            } else if (type == 2) { // Treat a cat
                //cats.remove(catsByName.get(command[1]));
                // Cat is never removed from the HashMap
            } else if (type == 3) { // Output cat with the highest infection level
                // if (cats.isEmpty()) {
                //     WRITER.write("The clinic is empty");
                // } else {
                //     WRITER.write(cats.firstKey().name);
                // }
                // WRITER.newLine();
            }
        }
        
        WRITER.close();*/
    }

    private static class Cat {
        public final String name;
        public final int timeArrived;
        public int infection;
        private final int hashCode;

        public Cat(String name, int timeArrived, int infection) {
            this.name = name;
            this.infection = infection;
            this.timeArrived = timeArrived;
            hashCode = name.hashCode();
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof Cat otherCat) {
                return name.equals(otherCat.name);
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return hashCode;
        }
    }

    private static class BalancedBST<T> {
        private final HashMap<T, Node<T>> nodesMap = new HashMap<>();
        private final Comparison<T> comparison;
        private Node<T> root;

        public BalancedBST(Comparison<T> comparison) {
            this.comparison = comparison;
        }

        public T peek() {
            Node<T> smallest = findSmallest();
            if (smallest == null) {
                return null;
            }

            return smallest.value;
        }

        public T pop() {
            Node<T> smallest = findSmallest();
            if (smallest == null) {
                return null;
            }
            remove(smallest.value);

            return smallest.value;
        }

        public void push(T obj) {
            if (root == null) {
                root = new Node<T>(obj, null, null, null);
                nodesMap.put(obj, root);
                return;
            }

            Node<T> currentNode = root;
            while (true) {
                if (comparison.compare(obj, currentNode.value) <= 0) {
                    if (currentNode.left == null) {
                        currentNode.left = new Node<T>(obj, currentNode, null, null);
                        nodesMap.put(obj, currentNode.left);

                        Node<T> unbalancedNode = findUnbalancedNode(currentNode.left);
                        if (unbalancedNode != null) {
                            balance(unbalancedNode);
                        }

                        return;
                    }

                    currentNode = currentNode.left;
                } else {
                    if (currentNode.right == null) {
                        currentNode.right = new Node<T>(obj, currentNode, null, null);
                        nodesMap.put(obj, currentNode.right);

                        Node<T> unbalancedNode = findUnbalancedNode(currentNode.right);
                        if (unbalancedNode != null) {
                            balance(unbalancedNode);
                        }

                        return;
                    }

                    currentNode = currentNode.right;
                }
            }
        }

        public T remove(T obj) {
            Node<T> currentNode = nodesMap.get(obj);
            if (currentNode == null) {
                return null;
            }

            Node<T> parent = currentNode.parent;
            unlinkFromParent(currentNode);

            if (currentNode.left == null && currentNode.right == null) {
                return currentNode.value;
            }

            if (currentNode.left == null) {
                currentNode.right.parent = parent;

                if (parent != null) {
                    if (comparison.compare(currentNode.right.value, parent.value) < 0) {
                        parent.left = currentNode.right;
                    } else {
                        parent.right = currentNode.right;
                    }
                }

                if (currentNode == root) {
                    root = currentNode.right;
                }

                return currentNode.value;
            }

            if (currentNode.right == null) {
                currentNode.left.parent = parent;

                if (parent != null) {
                    if (comparison.compare(currentNode.left.value, parent.value) < 0) {
                        parent.left = currentNode.left;
                    } else {
                        parent.right = currentNode.left;
                    }
                }

                if (currentNode == root) {
                    root = currentNode.left;
                }

                return currentNode.value;
            }

            Node<T> rightSmallest = currentNode.right;
            while (rightSmallest.left != null) {
                rightSmallest = rightSmallest.left;
            }

            remove(rightSmallest.value);
            if (currentNode.left != null) {
                currentNode.left.parent = rightSmallest;
            }
            if (currentNode.right != null) {
                currentNode.right.parent = rightSmallest;
            }
            rightSmallest.left = currentNode.left;
            rightSmallest.right = currentNode.right;
            rightSmallest.parent = parent;

            if (parent != null) {
                if (comparison.compare(rightSmallest.value, parent.value) < 0) {
                    parent.left = rightSmallest;
                } else {
                    parent.right = rightSmallest;
                }
            }

            if (currentNode == root) {
                root = rightSmallest;
            }

            return currentNode.value;
        }

        private void unlinkFromParent(Node<T> node) {
            if (node.parent == null) {
                return;
            }

            if (node.parent.left == node) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }

            node.parent = null;
        }

        private Node<T> findSmallest() {
            if (root == null) {
                return null;
            }

            Node<T> smallest = root;
            while (smallest.left != null) {
                smallest = smallest.left;
            }

            return smallest;
        }

        private Node<T> findUnbalancedNode(Node<T> node) {
            Node<T> parent = node.parent;
            while (parent != null) {
                if (parent.left != null && parent.left == node) {
                    parent.balance--;
                } else {
                    parent.balance++;
                }

                if (Math.abs(parent.balance) > 1) {
                    return parent;
                }

                node = parent;
                parent = node.parent;
            }

            return null;
        }

        private void balance(Node<T> node) { // Currently balancing only when adding and not removing elements
            if (node.left != null && node.left.left != null) {
                rotateRight(node);
                return;
            }

            if (node.left != null) {
                rotateLeft(node.left);
                rotateRight(node);
                return;
            }

            if (node.right != null && node.right.right != null) {
                rotateLeft(node);
                return;
            }

            if (node.right != null) {
                rotateRight(node.right);
                rotateLeft(node);
            }
        }

        private void rotateLeft(Node<T> node) {
            Node<T> a = node;
            Node<T> b = node.right;

            if (a.parent.left != null && a.parent.left == a) {
                a.parent.left = b;
            } else {
                a.parent.right = b;
            }

            b.parent = a.parent;
            b.left = a;
            a.parent = b;
            a.right = null;

            return;
        }

        private void rotateRight(Node<T> node) {
            Node<T> a = node;
            Node<T> b = node.left;

            if (a.parent.left != null && a.parent.left == a) {
                a.parent.left = b;
            } else {
                a.parent.right = b;
            }

            b.parent = a.parent;
            b.right = a;
            a.parent = b;
            a.left = null;

            return;
        }

        @FunctionalInterface
        public interface Comparison<T> {
            int compare(T a, T b);
        }

        private static class Node<T> {
            public T value;
            public Node<T> parent;
            public Node<T> left;
            public Node<T> right;
            public int balance = 0;

            public Node(T value, Node<T> parent, Node<T> left, Node<T> right) {
                this.value = value;
                this.parent = parent;
                this.left = left;
                this.right = right;
            }

            @Override
            public int hashCode() {
                return value.hashCode();
            }
        }
    }
}
