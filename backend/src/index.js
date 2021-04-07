import express from 'express';
import cors from 'cors';
import path from 'path';

import { registerAPIs } from "./routers/routerRegistor.js";


const startServer = async () => {  
	// Create app
	const app = express();
    app.use(cors());

	// Create router
	const router = express.Router();
	// Register apis
	registerAPIs(app, router);		

    // App uses:
    const __dirname = path.resolve(path.dirname('./'));
    app.use(express.static(path.join(__dirname, 'dist/tmdb-angular')));
	app.use('/api/v1', router);
    app.use('/*', function(req, res){
        res.sendFile(path.join(__dirname + '/dist/tmdb-angular/index.html'));
    })

	// Expose app
	app.listen({ port: 8080 }, () =>
		console.log(`🚀 Server ready at http://localhost:8080`)
	)
};

startServer();