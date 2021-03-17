function getTmdbImageUrlFunc(rawImageUrl, type){
    var tmdpImage = 'https://image.tmdb.org/t/p/';
    return tmdpImage+type+rawImageUrl;
}


//Export
export const getTmdbImageUrl = getTmdbImageUrlFunc;