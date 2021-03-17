import { getHomeData } from "../controllers/homeController.js";
import { searchItems } from "../controllers/searchController.js";
import { getItemDetail } from "../controllers/itemController.js";
import { getCastDetail } from "../controllers/castController.js";

var registerAPIFuncs = (app, router) => {
	app.get('/', function(req, res) {
		res.status(200).send('Welcome to the service.');
	});

    router.get('/home', getHomeData);

    router.get('/search', searchItems);
    
    router.get('/item', getItemDetail);
    
    router.get('/cast', getCastDetail);

}

//Export
export const registerAPIs = registerAPIFuncs;
