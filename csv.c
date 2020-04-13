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

FILE * open_csv(char *filename) {
	
	FILE * res;
	
	filename[strlen(filename) - 1] = '\0';
	strcat(filename, ".csv");
	res = fopen(filename, "w");
	if(res == NULL)
		err("error: could not open file");
	return res;
	
}

void make_entry(FILE *f, int i, char *buf, int num_cols) {
	
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
			fprintf(f, "\"%s\"\n", buf);
		}
		else
			fprintf(f, "%s", buf);
	
}

void save_header(char **headers, int i, char *buf) {
	
	headers[i] = malloc(strlen(buf) + 1);
	if(headers[i] == NULL)
		err("headers[i] malloc: no mem");
	if(buf[strlen(buf)] != '\0')
		buf[strlen(buf)] = '\0';
	strcpy(headers[i], buf); 
	
}

int main () {
	
	FILE * f;
	int num_cols, i, bool;
	char buf[MAX], file[MAX], **headers;
	
	printf("Enter file name: \n");
	fgets(file, MAX, stdin);
	
	f = open_csv(file);
	
	printf("Enter num cols: \n");
	scanf("%d", &num_cols);
	
	if(num_cols < 1)
		err("error: number of cols can't be smaller than 1!");
	
	//scanf triggers fgets in without flushing
	fflush(stdin);
	
	headers = malloc(num_cols * sizeof(char *));
	if(headers == NULL)
		err("error: could not allocate space for headers");
	
	printf("Enter headers: \n");
	
	for(i = 0; i < num_cols; i++) {
		fgets(buf, MAX, stdin);
		make_entry(f, i, buf, num_cols);
		save_header(headers, i, buf);
	}

	bool = 1;
	printf("Make entries: \n");
	while(bool){ 
		
		for(i = 0; i  < num_cols; i++) {
			printf("enter %s: ", headers[i]);
			fflush(stdout);
			fgets(buf, MAX, stdin);
			make_entry(f, i, buf, num_cols);
		}
		
		printf("Make another entry? (y, ye or yes) or anything else\n");
		scanf("%s", buf);
		if(strcmp(buf, "yes") && strcmp(buf, "y") && strcmp(buf, "ye"))
			bool = 0;			
		fflush(stdin);
	}
	
	for(i = 0; i < num_cols; i++)
		free(headers[i]);
	free(headers);
	
	fclose(f);
	
	return 0;
}