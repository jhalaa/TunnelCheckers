# Tunnel Checker Game
[![Coverage Status](https://coveralls.io/repos/github/jhalaa/TunnelCheckers/badge.svg?branch=master)](https://coveralls.io/github/jhalaa/TunnelCheckers?branch=master)
[![Build Status](https://travis-ci.org/jhalaa/TunnelCheckers.svg?branch=master)](https://travis-ci.org/jhalaa/TunnelCheckers)


## A checker game with the sides rolled on the side


## Problem
This repository contains software to play “tunnel checkers” against an opponent: 
American checkers is played on a flat 8x8 board of alternating colored squares.  The players are at opposite ends of the board and follow a set of game rules.
Tunnel checkers follows the same game rules with the two players at opposite ends.  However, the sides of the board are rolled up (or down) to create a tunnel.  E.g. a player’s piece could keep moving to forward right without bumping into a side as it moves around the tunnel board.

## Goal
Write a complete game-play software that plays tunnel checkers with an opponent.  The algorithms listed below are to be used with the minimum specifications.

## Algorithms:
### Minimax:
The basic AI model to be used recursively searches the decision tree of the current game to a certain depth (e.g. when depth=4, predict 4 moves ahead in total), then comparing different paths in these trees and pick one that has the best payoff.

For each board state when it is the AI's turn to play, recursively search all possible legal moves:
After each move, do the same prediction for the opponent,
Repeat step 1 and 2 until the predetermined searching depth is reached.
Pick the path which has the best outcome in terms of average pieces number advantage.

### α-β pruning (Alpha-beta pruning) :
Stop searching a tree branch once there is proof that the to-be-searched branch is absolutely worse than one of the previous branches.


