import express from 'express';
import cors from 'cors';
import { registerAPIs } from "./routers/routerRegistor.js";


const startServer = async () => {  
	//Create app
	const app = express();
    app.use(cors());

	//Create router
	const router = express.Router();
	//Register apis
	registerAPIs(app, router);		

	app.use('/api/v1', router);

	//Expose app
	app.listen({ port: 4000 }, () =>
		console.log(`🚀 Server ready at http://localhost:4000`)
	)
};

startServer();