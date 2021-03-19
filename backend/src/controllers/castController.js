import {getCastDetail as getTmdbCastDetail} from "../services/tmdbService.js"


async function getCastDetailFunc (req, res) {    
    var id = req.params.id;
    var data = await getTmdbCastDetail(id);
    res.status(200).send(JSON.stringify(data));
};


//Export
export const getCastDetail = getCastDetailFunc;