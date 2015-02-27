# cs203-game1

Game Manual 

A field of play where the blocks move.
  A field implemented using functional GameWorld, 400 by 800 pixels. The spawn area is above a line at 200 pixels- a Tetrimino can spawn   anywhere within this rectangle, then falls into the remaining 400 by 600 field

A set of "live" blocks that are player controlled. 
  A single falling tetrimino (a class called "Tetrimino" that implements the block interface). These blocks are always square, and vary in color

A set of "dead" blocks that are no longer player controlled.
  The tetriminos that are resting on top of each other or on the ground (a class called "DeadTetrimino" that implements the block interface. A live tetrimino becomes a new dead tetrimno- these do not move)

A scoring system.
  Points for eliminating "squares" of blocks- more points for more blocks in the square (a 2x2 gives 4 points, a 3x3 gives nine points a 4x4 gives 16 points- points are doubled if the eliminated square (or block of blocks) is the same color

A win or fail state.
  Fail: when a Tetrimino stops above the "spawn line" at 200 pixels
  Win: infinite play- all you can do is lose :(

A control mechanism.
  Tetriminos can be shifted left, right and down
