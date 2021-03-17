import {getItemDetail as getTmdbItemDetail} from "../services/tmdbService.js"


async function getItemDetailFunc (req, res) {
    var category = req.params.category;
    var id = req.params.id;
    var data = await getTmdbItemDetail(id, category);
    res.status(200).send(JSON.stringify(data));
};

//Export
export const getItemDetail = getItemDetailFunc;