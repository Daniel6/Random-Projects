/*
 * Main.cpp
 *
 *  Created on: Jul 22, 2013
 *      Author: daniel
 */

#include "Main.h"
#include <vector>
#include <iostream>

int height = 20;
int width = 20;
int numAlive;
void updateGrid();
void printGrid();
int getAdjacent(int y, int x);
int checkGrid(int y, int x);
template<typename T>
struct Matrix
{
	int rows, cols;
	std::vector<T> data;

	Matrix(int rows, int cols)
	: rows(rows), cols(cols), data(rows*cols)
	{ }

	T& operator()(int row, int col)
	{
		return data[row*cols + col];
	}

	T operator()(int row, int col) const
	{
		return data[row*cols + col];
	}
};

Matrix<int> world(height, width);

int main() {

	for(int j = 0; j < height; j++) {
		for(int i = 0; i < width; i++) {
			world(j, i) = 0;
		}
	}
	world(1,2) = 1;
	world(2,2) = 1;
	world(3,2) = 1;
	world(3,1) = 1;
	world(2,0) = 1;
	numAlive = 5;
	int iterations;
	for(iterations = 0; iterations < 10; iterations++) {
		std::cout << "Turn: " << iterations << "-----------------------------------------------------------------------------------------" << std::endl;
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				if(getAdjacent(j, i) < 2 && world(j, i) == 1) {
					world(j,i) = -1;
				} else if(getAdjacent(j,i) == 3 && world(j,i) == 0) {
					world(j,i) = 2;
				} else if(getAdjacent(j,i) > 3 && world(j, i) == 1) {
					world(j,i) = -1;
				}
			}
		}
		updateGrid();
		printGrid();
		if(numAlive <= 0) {
			return 0;
		}
	}
	return 0;
}

void printGrid() {
	for(int j = 0; j < height; j++) {
		for(int i = 0; i < width; i++) {
			std::cout << " " << world(j, i) << " ";
		} std::cout << "|" << std::endl;
	} std::cout << "Survivors: " << numAlive << std::endl;
}

void updateGrid() {
	for(int j = 0; j < height; j++) {
		for(int i = 0; i < width; i++) {
			if(world(j, i) == 2) {
				world(j,i) = 1;
				numAlive += 1;
			} else if(world(j,i) == -1) {
				world(j,i) = 0;
				numAlive -= 1;
			}
		}
	}
}

int checkGrid(int y, int x) {
	if(world(y, x) == 1 || world(y, x) == 0) {
		return world(y, x);
	} else if(world(y, x) == 2) {
		return 0;
	} else if(world(y, x) == -1) {
		return 1;
	}
	return 0;
}

//Counter intuitively, x is height and y is width
int getAdjacent(int x, int y) {
	int numAdjacent = 0;
	if(x >= 1 && y >= 1 && x <= width - 2 && y <= height - 2) {
		numAdjacent = checkGrid(x - 1, y) + checkGrid(x + 1, y) + checkGrid(x, y + 1) + checkGrid(x, y - 1) + checkGrid(x - 1, y - 1) + checkGrid(x - 1, y + 1) + checkGrid(x + 1, y - 1) + checkGrid(x + 1, y + 1);
	} else if(x < 1) {
		if (y == 0) {
			numAdjacent = checkGrid(x + 1, y) + checkGrid(x + 1, y + 1) + checkGrid(x, y + 1);
		} else if(y == height - 1) {
			numAdjacent = checkGrid(x + 1, y) + checkGrid(x + 1, y - 1) + checkGrid(x, y - 1);
		} else {
			numAdjacent = checkGrid(x + 1, y) + checkGrid(x + 1, y + 1) + checkGrid(x + 1, y - 1) + checkGrid(x, y + 1) + checkGrid(x, y - 1);
		}
	} else if(x > height - 2) {
		if (y == 0) {
			numAdjacent = checkGrid(x - 1, y) + checkGrid(x - 1, y + 1) + checkGrid(x, y + 1);
		} else if(y == height - 1) {
			numAdjacent = checkGrid(x - 1, y) + checkGrid(x - 1, y - 1) + checkGrid(x, y - 1);
		} else {
			numAdjacent = checkGrid(x - 1,y) + checkGrid(x - 1,y + 1) + checkGrid(x - 1,y - 1) + checkGrid(x,y - 1) + checkGrid(x,y + 1);
		}
	} else if(y < 1) {
		numAdjacent = checkGrid(x - 1,y) + checkGrid(x + 1,y) + checkGrid(x - 1,y + 1) + checkGrid(x,y + 1) + checkGrid(x + 1,y + 1);
	} else if(y > height - 2) {
		numAdjacent = checkGrid(x - 1,y) + checkGrid(x + 1,y) + checkGrid(x - 1,y - 1) + checkGrid(x,y - 1) + checkGrid(x + 1,y - 1);
	}

	return numAdjacent;
}

Main::Main() {
	// TODO Auto-generated constructor stub

}

Main::~Main() {
	// TODO Auto-generated destructor stub
}
