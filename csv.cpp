#include <iostream>
#include <fstream>
#include <string>
#include <cstdlib>

#define MAX 100

using namespace std;

void error(string msg) {
	cout << msg;
	exit(EXIT_FAILURE);
}

int main () {
	
	char filename[MAX];
	cout << "Enter filename: ";
	cin.getline(filename, MAX);
	
	strcat(filename, ".csv");
	// cout << filename;
	
	
	
	ofstream f(filename);
	int n;
	cout << "Enter num cols: ";
	cin >> n;
	// cout << "Enter headers";
	
	
	char ** headers = (char **)malloc(n * sizeof(char *));
	if(!headers)
		error("malloc1");
	for(int i = 0; i < n; i++){
		headers[i] = (char *)malloc(MAX * sizeof(char));
		if(!headers[i])
			error("malloc");
	}
	fflush(stdin);
	for(int i = 0; i < n ;i++) {
		cout << "Enter header " << i + 1 << ":";
		cin.getline(headers[i], MAX);
		if(f.is_open()) {
			if(strchr(headers[i], ' '))
				f << "\"" << headers[i] << "\"";
			else
				f << headers[i];
			if(i != n-1)
				f << ",";
		}
	}
	
	if(f.is_open())
		f << '\n';
	
	bool more = true;
	
	while(more){ 
		char temp[MAX];
		for(int i = 0; i < n; i++){
			
			cout << "Enter " << headers[i] << ": ";
			cin.getline(temp, MAX);
			if(f.is_open()){
				if(strchr(temp, ' '))
					f << "\"" << temp << "\"";
				else
					f << temp;
				
				if(i != n-1)
					f << ",";
			}
			
		}
		
		if(f.is_open())
			f << '\n';
		
		string prompt;
		cout << "Make another entry (yes/ye/y)? ";
		cin >> prompt;
		
		if(!(prompt == "yes" || prompt == "ye" || prompt == "y"))
			more = false;
		fflush(stdin);
	}
	f.close();
	return 0;
}