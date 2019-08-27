# AmazingApp
A simple maze game app for android devices created for a class project by a group of 3 students.

User could go through the maze themselves with their finger or watch the program solve the maze itself. 
The user must go from upper left corner to the bottom right corner of the maze. After completing a maze they are brought back to the main menu to select another maze if they wish.
The user is denoted on the map with a red block and their path through the maze is the green path they leave.
The walls of the maze are denoted with a blue color.

Mazes can be added to the program with text files added into the app by moving the files into app/src/main/Assests/Mazes.
The text files required the height of the maze and width of the maze in characters in the first line of file.
The following lines should either be a space or an * to denote a wall. The top left and bottom right corners should be empty and a path should be available between the two for app to work.
