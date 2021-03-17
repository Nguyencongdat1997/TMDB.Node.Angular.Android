var getCastDetail = function (req, res) {
    res.status(200).send('Hello world from Cast Detail');
};

//Export
module.exports = {
    getCastDetail: getCastDetail,
};