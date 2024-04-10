# A-Star-Search

This project implements a 15x15 tile-based world that dynamically generates environments with randomly placed unpathable nodes (blocks) in approximately 10% of the nodes. Each compilation guarantees a unique environment makeup, ensuring a fresh challenge every run.

Upon execution, the program displays the generated environment, allowing users to interactively select a starting node and a goal node. This selection process uses a scanner to allow user text-input for the 'start' coordinate and a 'goal' coordinate for the program to operate. 

Once the start and goal nodes are defined, the program employs the A* algorithm to find an optimal path. If a valid path exists, it is presented to the user, either as a series of [x,y] nodes, by highlighting the nodes, or by visually moving an agent along the path. If no path is found, a message indicating the inability to find a path is displayed.
