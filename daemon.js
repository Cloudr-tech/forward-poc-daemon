var express = require('express');
var app = express();
var cors = require('cors');
var port = process.env.PORT || 4242;
var axios = require('axios');
var bodyParser = require('body-parser');
var ip = require('ip');
var fs = require('fs');
var os = require('os');
var api = process.env.API || "http://api.cloudr.tech";


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
		else
			res.json({status: true, message:"OK", data: str2ab(data)});

	});
});

app.use(function (req, res) {
	res.status(404).json({
		status: false,
		message: "Bad Request: " + req.originalUrl + " not found"
	});
});

app.listen(port);

axios.put(api + '/daemons', {
	hostname : os.hostname(),
	ip : ip.address(),
	storageLeft : 20000000000
}).then(function (res) {
	if (res.data.status == false) {
		console.log("Daemon already registered");
	} else {
		console.log("Registered successfully as " + os.hostname() + " with ip " + ip.address());
	}
}).catch(function (err) {
	console.log("Error during communication with api");
	console.log(err);
	process.exit(1);
})


setInterval(function () {
	axios.patch(api + '/daemons', {
		hostname : os.hostname(),
		ip : ip.address(),
		storageLeft : 20000000000
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
}, 5000)

console.log('daemon started on 4242');
