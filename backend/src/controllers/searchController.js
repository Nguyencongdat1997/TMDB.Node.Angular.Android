function searchItemsFunc(req, res) {
    res.status(200).send('Hello world from Search Results');
};

//Export
export const searchItems = searchItemsFunc;