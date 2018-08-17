#include <iostream>
#include <cmath>

using namespace std;

int main() {
	double h;
	int p;
	cin >> h >> p;
	cout.precision(2);
	cout << fixed << h / p << endl;
	return 0;
}

