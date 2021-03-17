var searchItems = function (req, res) {
    res.status(200).send('Hello world from Search Results');
};

//Export
module.exports = {
    searchItems: searchItems,
};