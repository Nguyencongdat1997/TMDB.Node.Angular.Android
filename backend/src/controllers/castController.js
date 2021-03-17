function getCastDetailFunc(req, res) {
    res.status(200).send('Hello world from Cast Detail');
};

//Export
export const getCastDetail = getCastDetailFunc;