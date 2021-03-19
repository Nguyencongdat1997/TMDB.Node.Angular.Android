function getTmdbImageUrlFunc(rawImageUrl, type){
    if (rawImageUrl == null || rawImageUrl == "" ){
        return null;
    }
    var tmdpImage = 'https://image.tmdb.org/t/p/'+type+rawImageUrl;
    return tmdpImage;
}


//Export
export const getTmdbImageUrl = getTmdbImageUrlFunc;