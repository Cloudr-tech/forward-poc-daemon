var express = require('express');
var app = express();
var cors = require('cors');
var port = process.env.PORT || 4242;
var axios = require('axios');
var bodyParser = require('body-parser');
var ip = require('ip');
var fs = require('fs');
var os = require('os');
var api = process.env.API || "https://cloudr-api.marmus.me";
var config = {
	storageLeft: 10000000000
};

try {
	config = require("./config.json");
}
catch (err) {
	console.log("Unable to load configuration\nStarting with default settings");
}

app.use(cors());
app.use(bodyParser.urlencoded({extended:true}));
app.use(bodyParser.json({
	limit: "500mb"
}));

app.route('/upload').post(function (req, res) {
	if (req.body.name && req.body.dataBuffer) {
		console.log("POST /upload");
		fs.writeFile("storage/" + req.body.name, new Buffer (req.body.dataBuffer), function (err) {
		if (err)
			res.json({
				status: false,
				message: "Error saving file \"" + req.body.name + "\""
			});
		else
			res.json({
				status: true,
				message: "OK"
			});
		});
	}
	else
		res.json({
			status: false,
			message: "Missing fields"
		});
});

function str2ab(str) {
  var buf = new ArrayBuffer(str.length*2); // 2 bytes for each char
  var bufView = new Uint16Array(buf);
  for (var i=0, strLen=str.length; i<strLen; i++) {
    bufView[i] = str.charCodeAt(i);
  }
  return bufView;
}

app.route('/download/:id').get(function (req, res) {
	console.log("GET /download");
	fs.readFile("storage/" + req.params.id, 'binary', function (err, data) {
		if (err)
			res.json({
				status: false,
				message: "Error saving file \"" + req.body.name + "\""
			});
		else {
			const dataToSend = [];
			var intArray = new Int8Array(str2ab(data));
			for (let item in intArray) {
				dataToSend.push(intArray[item]);
			}
			res.json({status: true, message:"OK", data: dataToSend});
		}

	});
});

app.use(function (req, res) {
	res.status(404).json({
		status: false,
		message: "Bad Request: " + req.originalUrl + " not found"
	});
});

app.listen(port);

function keepAlive() {
	axios.patch(api + '/daemons', {
		hostname : os.hostname(),
		ip : ip.address(),
		storageLeft : config.storageLeft
	}).then(function (res) {
		if (res.data.status == false) {
			console.log("Error: " + res.data.message);
		} else {
			console.log("Patch daemon send successfully");
		}
	}).catch(function (err) {
		console.log("Error during communication with api");
		console.log(err);
		process.exit(1);
	});
}

axios.put(api + '/daemons', {
	hostname : os.hostname(),
	ip : ip.address(),
	storageLeft : config.storageLeft
}).then(function (res) {
	if (res.data.status == false) {
		console.log("Daemon already registered");
	} else {
		console.log("Registered successfully as " + os.hostname() + " with ip " + ip.address());
	}
	keepAlive();
	setInterval(keepAlive, 5000);
}).catch(function (err) {
	console.log("Error during communication with api");
	console.log(err);
	process.exit(1);
});


console.log('daemon started on 4242');
