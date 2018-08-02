#include <iostream>
#include <cmath>

using namespace std;

const int T = 3;
const int MAX = T - 1;
int val[T];
	
bool naive() {
	if (val[0] + val[1] - val[2] == 0 or 
		val[0] - val[1] + val[2] == 0 or
		val[0] - val[1] - val[2] == 0 or
		-val[0] + val[1] + val[2] == 0 or 
		-val[0] + val[1] - val[2] == 0 or
		-val[0] - val[1] + val[2] == 0 or
		val[0] == val[1] or val[0] == val[2] or val[1] == val[2]) {
		return true;
	}
	return false;
}

int main() {
	for (int i = 0; i < T; i++) {
		cin >> val[i];
	}
	if (naive()) {
		cout << "S\n";
	} else {
		cout << "N\n";
	}
	return 0;
}

