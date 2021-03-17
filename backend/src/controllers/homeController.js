var getHomeData = function (req, res) {
    res.status(200).send('Hello world from home');
};

//Export
module.exports = {
    getHomeData: getHomeData,
};