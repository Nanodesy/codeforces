import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OmkarAndMedians {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();

        while (T-- > 0) {
            int n = scanner.nextInt();
            int[] array = new int[n];

            for (int i = 0; i < n; i++) {
                array[i] = scanner.nextInt();
            }

            System.out.println(solveTask(array));
        }
    }

    public static String solveTask(int[] array) {
        Node node = new Node(array[0]);
        int value;
        Node temp;

        for (int i = 1; i < array.length; i++) {
            value = array[i];
            if (value < node.value) {
                if (node.prev != null && value < node.prev.value) {
                    // Previous node value higher
                    return "NO";
                }

                if (node.prev == null || node.prev.value < value) {
                    // Previous node value less
                    temp = new Node(value);
                    temp.prev = node.prev;
                    temp.next = node;
                    if (node.prev != null) {
                        node.prev.next = temp;
                    }
                    node.prev = temp;
                    node = temp;
                } else {
                    // Previous node value equal
                    node = node.prev;
                }

            } else if (value > node.value) {
                if (node.next != null && value > node.next.value) {
                    // Next node value less
                    return "NO";
                }

                if (node.next == null || node.next.value > value) {
                    // Next node value higher
                    temp = new Node(value);
                    temp.next = node.next;
                    temp.prev = node;
                    if (node.next != null) {
                        node.next.prev = temp;
                    }
                    node.next = temp;
                    node = temp;
                } else {
                    // Next node value equal
                    node = node.next;
                }
            }
        }

        return "YES";
    }

    private static class Node {
        private int value;
        private Node next;
        private Node prev;

        public Node(int value) {
            this.value = value;
        }
    }
}
