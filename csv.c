#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX 128

void err(char *msg) {
	fprintf(stderr, "%s\n", msg);
	exit(EXIT_FAILURE);
}

int main () {
	
	FILE * f;
	int num_cols, i, bool;
	char buf[MAX], word[MAX], file[MAX];
	
	printf("Enter file name: \n");
	scanf("%s", file);
	
	strcat(file, ".csv");
	
	f = fopen(file, "w");
	if(f == NULL)
		err("error: could not open file");
	
	printf("Enter num cols\n");
	scanf("%d", &num_cols);
	
	printf("Enter headers: \n");
	
	for(i = 0; i < num_cols; i++) {
		
		scanf("%s", buf);
		fprintf(f, "%s", buf);
		if(i + 1 != num_cols)
			fprintf(f, ",");
		else
			fprintf(f, "\n");
	}
	bool = 1;
	printf("Make entries: \n");
	while(bool){ 
		
		for(i = 0; i  < num_cols; i++) {
			scanf("%s", buf);
			fprintf(f, "%s", buf);
			if(i + 1 != num_cols)
				fprintf(f, ",");
			else
				fprintf(f, "\n");
		}
		
		printf("Make another entry? (y, ye or yes) or anything else\n");
		scanf("%s", buf);
		if(strcmp(buf, "yes") && strcmp(buf, "y") && strcmp(buf, "ye"))
			bool = 0;			
		
	}
	
	fclose(f);
	
	return 0;
}