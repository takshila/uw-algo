Out-degree one graph

Consider a directed graph on n vertices, where each vertex has exactly one outgoing edge.
This graph consists of a collection of cycles as well as additional vertices that have paths to
the cycles, which we will call the branches. We dene the weight of the cycle to be the total
number of vertices that are either on the cycle or on branches that are connected to the cycle.
Give pseudocode for a linear time algorithm that identies all of the cycles and computes the
length and weight of each cycle.

Implement your algorithm from the previous problem for nding the cycles in an out-degree
one graph. Your algorithm should be designed to work on very large graphs, e.g., with
n = 100,000,000.
Write an input generator which creates completely random out-degree one graphs where each
vertex points to another vertex chosen uniformly at random and run your program on inputs
from your generator of various sizes.