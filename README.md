# Sliding puzzle solver

The Sliding puzzle game consists of a frame of numbered square tiles in random order with one tile missing. The object of the puzzle is to place the tiles in order by making sliding moves that use the empty space.

# Some numbers

8-puzzle ( 9 tiles, 9! = 362,880 permutations )  
15-puzzle ( 16 tiles, 16! = 20,922,789,888,000‬ permutations )  
24-puzzle ( 25 tiles , 25! = 15,511,210,043,330,985,984,000,000‬ permutations )  
35-puzzle ( 36 tiles , 36! = 371,993,326,789,901,217,467,999,448,150,835,200,000,000 permutations ) 

# Algorithms
 
**Breadth-first search** is an uninformed algorithm for traversing or searching tree or graph data structures. It starts at the tree root and explores all of the neighbor nodes at the present depth prior to moving on to the nodes at the next depth level.  
*Pros:* finds shortest path  
*Cons:* does not know where goal is

**A*** is an informed search algorithm, or a best-first search, meaning that it is formulated in terms of weighted graphs: starting from a specific starting node of a graph, it aims to find a path to the given goal node having the smallest cost (least distance travelled, shortest time, etc.). It does this by maintaining a tree of paths originating at the start node and extending those paths one edge at a time until its termination criterion is satisfied.  
*Pros:* knows where the goal is  
*Cons:* stores too much states

**Iterative Deepening A*** works like A* with one modification: at each iteration, perform a depth-first search, cutting off a branch when its total cost  f(n)=g(n)+h(n) exceeds a given threshold.  
*Pros:* does not store too many states  
*Cons:* visits same states

**Tile by tile** is a algorithm where the puzzle is divided into two parts: upper and lower half. The upper half works by solving two by two tiles and when a row is completed, cut it and don't search it anymore. The lower half works like the previous algorithms by solving the puzzle all at once. It is implemented using A*.  
*Pros:* much more efficient than previous algorithms despite puzzle size  
*Cons:* uses too many unnecessary moves

# Heuristic

**The Hamming distance** is the total number of misplaced tiles.

**The Manhattan distance** is the sum of the absolute differences between the two points.

**Linear conflict**: Tiles t1 and t2 are in a linear conflict if t1 and t2 are the same line, the goal positions of t1 and t2 are both in that line, t1 is to the right of t2 , and goal position of t1 is to the left of the goal position of t2. It is calculated as manhattanDistance + 2*linearConflict.

# How to use  
User can set a custom board by typing in the grid (not all custom puzzles are solvable) or by pressing the random button (all generated puzzles are solvable).  
User can select a custom image or use the default one below.

8-puzzle: can be solved using any algorithm and heuristic  
15-puzzle: can be solved using IDA* or tile by tile paired with linear conflict  
24-puzzle: can be solved using tile by tile paired with linear conflict  
35-puzzle: same as 24-puzzle but it may take a few minutes to solve


<p align="center">
  <img src="https://github.com/gojkovicmatija99/Sliding-puzzle-solver/blob/master/SlidingPuzzleSolver/src/view/images/puzzle.gif">
</p>
