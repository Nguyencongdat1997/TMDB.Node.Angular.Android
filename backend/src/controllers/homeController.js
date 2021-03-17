function getHomeDataFunc (req, res) {
    res.status(200).send('Hello world from home');
};



//Export
export const getHomeData = getHomeDataFunc;