# Maze-Solver

## Maze file format

```
<Number of columns> <Number of rows>
<0-based column number of the start square> <0-based row number of the start square> 
<0-based column number of the finish square> <0-based row number of the finish square> 
<Row 0 description>
<Row 1 description>
...
```

* `7` means that the square has both a top wall and a right wall
* `|` (vertical bar or pipe) means that the square has a right wall, but no top wall
* `_` (underscore) means that the square has a top wall, but no right wall
* `*` (asterisk) means that the square has neither a top wall nor a right wall Putting this together in a small example, if the input file contains the following:

| File     | Interpretation                                                                             |
| -------- | ------------------------------------------------------------------------------------------ |
| `3 2`  | The maze has 3 columns and 2 rows                                                          |
| `1 1`  | The start square is in the bottom middle.                                                  |
| `2 0`  | The finish square is in the upper right.                                                   |
| ` __7` | (0,0) has a top wall; (0,1) has a top wall; (0,2) has a top and right wall                 |
| `*_7`  | (1,0) has neither top or right walls; (1,1) has a top wall; (1,2) has a top and right wall |


then the resulting maze would be printed as follows:

```
+-----+-----+-----+
|                 |
|              F  |
|                 |
+     +-----+-----+
|                 |
|        S        |
|                 |
+-----+-----+-----+
```

![](image/README/1632449473771.png)
