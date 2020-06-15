const fs = require('fs');
const readline = require('readline');

const rl = readline.createInterface({
	input: process.stdin,
	output: process.stdout
});

function readInput(rl, q) {
	return new Promise((res, rej) => {
		rl.question(q, ans => res(ans))
	});
}

async function get(q) {
	return (await readInput(rl, q)).trim();
}

(async function main () {
	const filename = await get("enter filename: ") + '.csv';
	const n = parseInt(await get("enter num: "));
	
	let headers = [];
	let full_entry = '';
	
	for(let i = 0; i < n; i++) {
		headers.push(await get(`enter header ${i+1}: `));
		full_entry += headers[i].indexOf(' ') != -1 ? `"${headers[i]}"` : headers[i];
			if(i != n-1)
				full_entry += ',';
	}
	
	full_entry += '\n';
	
	let more = true;
	
	while(more) {
		
		for(let i = 0; i < n; i++) {
			let curr = await get('enter ' + headers[i] + ' :');
			full_entry += curr.indexOf(' ') != -1 ? `"${curr}"` : curr;
			if(i != n-1)
				full_entry += ',';	
		}
		
		full_entry += '\n';
		
		let prompt = await get('make another entry (yes/ye/y)? ');
		if(!(prompt === 'yes' || prompt == 'ye' || prompt == 'y'))
			more = false;		
	}
	
	//console.log(full_entry);
	rl.close();

	fs.writeFile(filename, full_entry, (err) => { if (err) throw err; else console.log('success!') } );
})();
