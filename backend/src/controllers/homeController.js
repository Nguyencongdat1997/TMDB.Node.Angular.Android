import {getHomeData as tmdbGetHomeData} from "../services/tmdbService.js"

async function getHomeDataFunc (req, res) {
    var data = await tmdbGetHomeData();
    res.status(200).send(JSON.stringify(data));
};


//Export
export const getHomeData = getHomeDataFunc;