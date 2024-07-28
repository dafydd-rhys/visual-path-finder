# visual-path-finder
If you find any issues please post them in the issues section, also star this repository if you like the application - download [here](https://github.com/dafydd-rhys/visual-path-finder/releases).

## Overview
This application visually demonstrates the most efficient path from a starting node to a finishing node, navigating around any obstacles and passing through any intermediary nodes created by the user.

## How does it work?
Using the A* pathfinding algorithm, the process begins by initializing the starting and goal nodes and preparing the board by resetting tile statuses and calculating costs (G-cost, H-cost, F-cost) for each tile. The main loop selects the tile with the lowest F-cost from the open list for evaluation, marking it as checked and exploring its neighbors. For each traversable neighbor, it updates costs and adds it to the open list if not already present. If a shorter path to a neighbor is found, the algorithm updates its costs and parent. Upon reaching the goal node, the algorithm constructs the path by backtracking from the goal to the start node, marking the path tiles accordingly. The process continues until the goal is reached or the open list is empty.

## Features

The features of this program:

* Pathfinding Algorithm
  * Implements the A* algorithm for efficient pathfinding.
  * Calculates and visualizes the most efficient path from start to goal.
  * Handles obstacles and required nodes effectively.
 
* Obstacles and Must-Visit nodes.
  * Can implement nodes that can't be travelled through
  * Can implment nodes that must be reached before the goal node.

* GUI
  * Simple and nice-looking.
  * Easy to use.

## Watch a video
You can view a video of this application [here](https://www.youtube.com/watch?v=dQw4w9WgXcQ)

## Author(s)
* [Dafydd Maund](https://github.com/Stryzhh)
