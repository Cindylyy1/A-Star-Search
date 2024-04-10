import java.util.*;

public class AStar {
    public static final int size = 15;
    public static final double block_percentage = 0.10; // 10% of spots will be blocked

    static class Node {
        int row, col;
        int heuristics = 0;
        int finalPrice = 0;
        Node parent;

        Node(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return "[" + this.row + ", " + this.col + "]";
        }
    }

    static Node[][] grids = new Node[size][size];
    static PriorityQueue<Node> op;
    static boolean close[][];
    static int startRow, startCol;
    static int endRow, endCol;

    public static void setBlocked(int row, int col) {
        grids[row][col] = null;
    }

    public static void setStartCell(int row, int col) {
        startRow = row;
        startCol = col;
    }

    public static void setEndCell(int row, int col) {
        endRow = row;
        endCol = col;
    }

    static void updates(Node curr, Node neighbor, int cost) {
        if (neighbor == null || close[neighbor.row][neighbor.col]) return;
        int t_final_cost = neighbor.heuristics + cost;

        boolean inOp = op.contains(neighbor);
        if (!inOp || t_final_cost < neighbor.finalPrice) {
            neighbor.finalPrice = t_final_cost;
            neighbor.parent = curr;
            if (!inOp) {
                op.add(neighbor);
                close[neighbor.row][neighbor.col] = true; // Mark as visited
            }
        }
    }

    public static void generateRandomGrid() {
        Random rand = new Random();

        // Populate the grid with random blocks
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (rand.nextDouble() < block_percentage) {
                    grids[i][j] = null; // Blocked
                } else {
                    grids[i][j] = new Node(i, j); // Clear path
                }
            }
        }
    }

    public static void displayGridWithPath(List<int[]> path) {
        char[][] grid = new char[size][size];

        // Initialize grid with empty spaces
        for (int i = 0; i < size; i++) {
            Arrays.fill(grid[i], ' ');
        }

        // Mark blocked cells
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grids[i][j] == null) {
                    grid[i][j] = '1'; // Blocked
                } else {
                    grid[i][j] = '0'; // Clear path
                }
            }
        }

        // Mark start and end points
        grid[startRow][startCol] = 'S';
        grid[endRow][endCol] = 'G';

        // Mark path
        for (int[] point : path) {
            int row = point[0];
            int col = point[1];
            if (grid[row][col] != 'S' && grid[row][col] != 'G') {
                grid[row][col] = 'P'; // Path
            }
        }

        // Display grid
        for (char[] row : grid) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Generate random grid
        generateRandomGrid();

        // Get start point from user
        System.out.println("Enter the row and column coordinates for the start point (0-14):");
        int startRow = scanner.nextInt();
        int startCol = scanner.nextInt();
        setStartCell(startRow, startCol);

        // Get end point from user
        System.out.println("Enter the row and column coordinates for the goal point (0-14):");
        int goalRow = scanner.nextInt();
        int goalCol = scanner.nextInt();
        setEndCell(goalRow, goalCol);

        // Run A* algorithm
        op = new PriorityQueue<>((a, b) -> Integer.compare(a.finalPrice, b.finalPrice));
        close = new boolean[size][size];
        op.add(grids[startRow][startCol]); // Add start node to priority queue
        close[startRow][startCol] = true; // Mark start node as visited

        while (!op.isEmpty()) {
            Node curr = op.poll();
            if (curr.row == endRow && curr.col == endCol) {
                break;
            }

            // Check neighbors
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) continue;
                    int newRow = curr.row + i;
                    int newCol = curr.col + j;
                    if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size && grids[newRow][newCol] != null) {
                        updates(curr, grids[newRow][newCol], 1);
                    }
                }
            }
        }

        // Retrieve the path
        List<int[]> path = new ArrayList<>();
        Node curr = grids[endRow][endCol];
        while (curr != null) {
            path.add(new int[]{curr.row, curr.col});
            curr = curr.parent;
        }
        Collections.reverse(path);

        // Display grid with path
        displayGridWithPath(path);

        // Print path coordinates
        System.out.println("Path coordinates:");
        for (int[] point : path) {
            System.out.println("(" + point[0] + ", " + point[1] + ")");
        }

        scanner.close();
    }
}

