import {getHomeData as getTmdbHomeData} from "../services/tmdbService.js"

async function getHomeDataFunc (req, res) {
    var data = await getTmdbHomeData();
    res.status(200).send(JSON.stringify(data));
};


//Export
export const getHomeData = getHomeDataFunc;