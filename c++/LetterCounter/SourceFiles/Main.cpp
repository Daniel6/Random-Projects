/*
 * Main.cpp
 *
 *  Created on: Jul 19, 2013
 *      Author: daniel
 */

#include "../Headers/Main.h"
#include <iostream>
using namespace std;

void readSentence();
void queryRetry();

int main() {
	readSentence();
	return 0;
}

int findOccurencesOfLetter(string s, char c) {
	int count = 0;
	int i = 0;
	while(s.find(c, i)!=string::npos) {
		i = s.find(c, i) + 1;
		count++;
	}
	return count;
}

void readSentence() {
	string enteredWord;
	string sentence = "";
	bool acceptInput = true;
	cout << "Type in words" << endl << "When you are done, type in a 0" << endl << ": ";
	while(acceptInput) {
		cin >> enteredWord;
		if(enteredWord.compare("0") == 0) {
			acceptInput = false;
			break;
		}
		sentence = sentence + enteredWord + " ";
		cout << ": ";

	}
	for (char i = 'a'; i <= 'z'; i++) {
		int numOccurences = findOccurencesOfLetter(sentence, i);
		if(numOccurences != 0) {
			cout << "Occurences of " << i << ": " << numOccurences << endl;
		}
	}

	queryRetry();
}

void queryRetry() {
	string response = "";
	cout << "Would you like to start over? (Y/N) ";
	cin >> response;
	if(response.compare("y") == 0) {
		readSentence();
	} else if (response.compare("n") == 0) {
		cout << "Exiting";
		return;
	} else {
		cout << response << " is not a valid answer.";
		queryRetry();
	}
}
