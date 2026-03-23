# Snakes & Ladders — LLD Implementation

## Overview

A fully object-oriented **Snakes & Ladders** game implemented in Java.  
Supports:
- Custom board size `n` (board is `n × n`, cells numbered `1` to `n²`)
- Configurable number of players
- Two difficulty levels — **EASY** and **HARD**
- `n` snakes and `n` ladders placed randomly (no cycle guarantee logic)

---

## Class Diagram

```
┌──────────────────────────────────────────────────────────────────┐
│                            Main                                   │
│  + main(args: String[]) : void                                    │
│  (reads n, x, difficulty, player names → creates and starts Game) │
└──────────────┬───────────────────────────────────────────────────┘
               │ creates
               ▼
┌──────────────────────────────────────────────────────────────────┐
│                            Game                                   │
│  - board    : Board                                               │
│  - dice     : Dice                                                │
│  - players  : List<Player>                                        │
│  - winners  : List<Player>                                        │
│  ─────────────────────────────────────────────────────────────── │
│  + Game(n: int, playerNames: List<String>, diff: DifficultyLevel) │
│  + start() : void                                                 │
│  - printBoardSummary() : void                                     │
└──────┬───────────────┬────────────────────────────────────────────┘
       │ has-a         │ has-a
       ▼               ▼
┌────────────┐   ┌────────────┐
│    Dice    │   │   Player   │
│ ────────── │   │ ────────── │
│ + roll():  │   │ - name     │
│   int      │   │ - position │
└────────────┘   │ + getName()│
                 │ + getPos() │
                 │ + setPos() │
                 └────────────┘

       │ has-a
       ▼
┌──────────────────────────────────────────────────────────────────┐
│                            Board                                  │
│  - n          : int                                               │
│  - totalCells : int  (= n²)                                       │
│  - snakeMap   : Map<Integer, Snake>   (keyed by snake head)       │
│  - ladderMap  : Map<Integer, Ladder>  (keyed by ladder start)     │
│  ─────────────────────────────────────────────────────────────── │
│  + Board(n: int, difficulty: DifficultyLevel)                     │
│  - placeSnakes(count, difficulty, occupied) : void                │
│  - placeLadders(count, difficulty, occupied) : void               │
│  + getSnakeAt(pos: int) : Snake                                   │
│  + getLadderAt(pos: int) : Ladder                                 │
│  + getTotalCells() : int                                          │
└──────┬───────────────────────────────────────────────────────────┘
       │ holds
       ├──────────────────────┐
       ▼                      ▼
┌──────────────┐       ┌──────────────────┐
│    Snake     │       │     Ladder       │
│ ──────────── │       │ ──────────────── │
│ - head: int  │       │ - start: int     │
│ - tail: int  │       │ - end:   int     │
│ + getHead()  │       │ + getStart()     │
│ + getTail()  │       │ + getEnd()       │
└──────────────┘       └──────────────────┘

┌──────────────────┐
│  DifficultyLevel │  (enum)
│  ──────────────  │
│  EASY            │
│  HARD            │
└──────────────────┘
```

---

## Design Decisions & How to Explain in a Viva

| Question | Answer |
|---|---|
| Why a `Dice` class? | SRP — only the Dice knows how to roll. Easy to mock in tests. |
| Why `HashMap` for snakes/ladders? | O(1) lookup per move. Lists would be O(n) per cell. |
| Why an `occupied` `HashSet`? | Prevents a cell from being both a snake head and a ladder start. Guarantees no cycles. |
| How is difficulty implemented? | DifficultyLevel enum → Board adjusts snake drop length and ladder boost height proportionally to `n`. |
| Why does `Game` have `winners` list? | To track finishing order (1st, 2nd, …) not just the final winner. |
| When does the game stop? | When only **1 active player** remains — the rest have all reached cell `n²`. |
| What happens on overshoot? | If `currentPos + roll > n²`, the player **stays** at their current position. |
| Can the same cell be a snake tail and ladder start? | No — the `occupied` set prevents it. |

---

## How to Run

```bash
# Compile from the snakes-and-ladders directory
javac -d out src/snl/*.java

# Run
java -cp out snl.Main
```

**Sample input:**
```
Enter board size n (board will be n x n): 10
Enter number of players: 3
Enter difficulty level (EASY / HARD): EASY
Enter name of player 1: Alice
Enter name of player 2: Bob
Enter name of player 3: Charlie
```

---

## File Structure

```
snakes-and-ladders/
└── src/
    └── snl/
        ├── Main.java            ← entry point (reads user input)
        ├── Game.java            ← orchestrates the game loop
        ├── Board.java           ← places snakes & ladders randomly
        ├── Player.java          ← tracks player name & position
        ├── Dice.java            ← simulates a 6-sided die
        ├── Snake.java           ← head → tail data object
        ├── Ladder.java          ← start → end data object
        └── DifficultyLevel.java ← EASY / HARD enum
```
