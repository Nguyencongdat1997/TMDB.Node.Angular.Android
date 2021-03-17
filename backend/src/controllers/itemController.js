var getItemDetail = function (req, res) {
    res.status(200).send('Hello world from Item Detail');
};

//Export
module.exports = {
    getItemDetail: getItemDetail,
};