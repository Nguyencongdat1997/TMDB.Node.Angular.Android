import {getSearchResult} from "../services/tmdbService.js"


async function searchItemsFunc(req, res) {
    var searchKeyword = req.param('keyword');
    var data = await getSearchResult(searchKeyword);
    res.status(200).send(JSON.stringify(data));;
};


//Export
export const searchItems = searchItemsFunc;