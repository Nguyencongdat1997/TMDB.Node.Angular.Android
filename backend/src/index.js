var express = require('express');
var routerRegistor = require("./routers/routerRegistor.js");

const startServer = async () => {  
	//Create app
	const app = express();

	//Create router
	const router = require('express').Router();
	//Register apis
	routerRegistor.registerAPIs(app, router);		

	app.use('/api/v1', router);

	//Expose app
	app.listen({ port: 4000 }, () =>
		console.log(`🚀 Server ready at http://localhost:4000`)
	)
};

startServer();