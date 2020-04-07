#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX 128

//TODO - multiple word entries DONE!
//TODO - write functions
//TODO - trim inputs, input[strlen(input) - 1] = '\0' is lazy and trims one side only
//TODO - when making entries prompt "enter %s", header[i]

void err(char *msg) {
	fprintf(stderr, "%s\n", msg);
	exit(EXIT_FAILURE);
}

int main () {
	
	FILE * f;
	int num_cols, i, bool;
	char buf[MAX], file[MAX];
	
	
	printf("Enter file name: \n");
	fgets(file, MAX, stdin);
	
	//hack out filename.csv where filename can contain more strings
	
	file[strlen(file) - 1] = '\0';
	strcat(file, ".csv");
	
	
	f = fopen(file, "w");
	if(f == NULL)
		err("error: could not open file");
	
	printf("Enter num cols: \n");
	scanf("%d", &num_cols);
	
	if(num_cols < 1)
		err("error: number of cols can't be smaller than 1!");
	
	//scanf triggers fgets in without flushing
	fflush(stdin);
	
	printf("Enter headers: \n");
	
	for(i = 0; i < num_cols; i++) {
		fgets(buf, MAX, stdin);
		
		
		if(i + 1 != num_cols) {
			//print buf and ,
			buf[strlen(buf) -1] = '\0';
			if(strchr(buf, ' '))
				fprintf(f, "\"%s\",", buf);
			else
				fprintf(f, "%s,", buf);
		}
			
		else 
			//print buf and \n
			if(strchr(buf, ' ')) {
				buf[strlen(buf) - 1] = '\0';
				fprintf(f, "\"%s\"", buf);
				fprintf(f, "\n");
			}
			else
				fprintf(f, "%s", buf);
	}

	bool = 1;
	printf("Make entries: \n");
	while(bool){ 
		
		for(i = 0; i  < num_cols; i++) {
			fgets(buf, MAX, stdin);
			if(i + 1 != num_cols) {
				//print buf and ,
				buf[strlen(buf) -1] = '\0';
				if(strchr(buf, ' '))
					fprintf(f, "\"%s\",", buf);
				else
					fprintf(f, "%s,", buf);
			}
			else 
				//print buf and \n
				if(strchr(buf, ' ')) {
					buf[strlen(buf) - 1] = '\0';
					fprintf(f, "\"%s\"", buf);
					fprintf(f, "\n");
				}
				else
					fprintf(f, "%s", buf);
		}
		
		printf("Make another entry? (y, ye or yes) or anything else\n");
		scanf("%s", buf);
		if(strcmp(buf, "yes") && strcmp(buf, "y") && strcmp(buf, "ye"))
			bool = 0;			
		fflush(stdin);
	}
	
	fclose(f);
	
	return 0;
}