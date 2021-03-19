function parseTmdbGenresFunc(itemGenres, genresDict){
    var itemGenreNames = itemGenres.map(x => {
        for (var g of genresDict){
            if (g['id'] == x['id']){
                return g['name'];
            }
        }
        return '';
    });
    var genresTxt = itemGenreNames.join(', ');    
    return genresTxt;    
}


//Export
export const parseTmdbGenres = parseTmdbGenresFunc;