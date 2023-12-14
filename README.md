[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/1EiKHzOV)
# Picasso

An application that allows the user to create expressions that
evaluate to colors and then eventually to images.

The given code base is a good start, but it is sparsely documented
(document each method/part after you understand what it's doing) and,
as your application grows, you may need to refactor.

See the specification for Picasso on the course web site.

## Team GameChangers
Aiden, Chaz, Hân, Jenna, Lydia

## Running Picasso

To run Picasso, run `picasso.Main`

## Project Organization

`src` - the source code for the project

`conf` - the configuration files for the project

The `images` directory contains some sample images generated from Picasso.  Some of the expressions for these images can be found in the `expressions` directory.

## Code Base History

This code base originated as a project in a course at Duke University.  The professors realized that the code could be designed better and refactored.  This code base has some code leftover from the original version.

## Extensions

Generating Random Expressions

- To generate a random expression utilizing a combination of the functions and operators that have been implemented, simply click the random button in the Graphical User Interface in the top button panel. Continue generating new and fun images by clicking as many times as you want (Some look more fun than others).

Generating Expressions from a String

- To generate an expression from a String, input any string input you would like in the texfield. Then, click the button labeled "Generate From String." The program will accept the input as a seed to generate an expression that will be evaulated into an image. The same input will always yield the same image. (Be creative with your string expressions by using varied cases or different symbols to have more fun!)

Expression History

- History keeps track of previously evaluated expressions in a dropdown from the "History" tab, and allows the user to select an expression from this dropdown to evaluate again. It does so by utilizing the history view command and pulling from the expression list that is saved as the evaluator runs