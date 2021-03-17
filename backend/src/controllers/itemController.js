function getItemDetailFunc (req, res) {
    res.status(200).send('Hello world from Item Detail');
};

//Export
export const getItemDetail = getItemDetailFunc;