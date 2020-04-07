# -*- coding: utf-8 -*-
"""

script that generates .csv file

"""

#TODO multiple word entries DONE

name = input('enter file name: ')
num_cols = int(input('enter num cols: '))

f = open(name+'.csv', 'w')
headers = []

for i in range(num_cols):
    curr_header = input(f'enter value of header {i+1}: ')
    headers.append(curr_header)
    if curr_header.strip().find(' ') != -1:
        f.write('"' + curr_header + '"')
    else:
        f.write(curr_header)
    if i != num_cols - 1:
        f.write(',')
        
f.write('\n')

more = True

while more: 
    for i in range(num_cols):
        user_input = input(f'enter {headers[i]}: ');
        if user_input.strip().find(' ') != -1:
            f.write('"' + user_input + '"')
        else:
            f.write(user_input)
        if i != num_cols - 1:
            f.write(',')
    
    f.write('\n')
    text = 'Make another entry? (yes/ye/y for more, anything else to end) '
    another_entry = input(text)  
    
    if not (another_entry.lower() == 'yes' or 
            another_entry.lower() == 'ye' or 
            another_entry.lower() == 'y'):
        more = False
        
f.close()
