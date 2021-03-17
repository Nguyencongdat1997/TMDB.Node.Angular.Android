var homeController = require("../controllers/homeController.js");
const searchController = require("../controllers/searchController.js");
const itemController = require("../controllers/itemController.js");
const castController = require("../controllers/castController.js");

var registerAPIs = (app, router) => {
	app.get('/', function(req, res) {
		res.status(200).send('Welcome to the service.');
	});

    router.get('/home', homeController.getHomeData);

    router.get('/search', searchController.searchItems);
    
    router.get('/item', itemController.getItemDetail);
    
    router.get('/cast', castController.getCastDetail);

}

module.exports = {
    registerAPIs: registerAPIs,
};

