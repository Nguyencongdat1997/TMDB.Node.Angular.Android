import {getTmdbImageUrl} from '../utils/getTmdbImageUrl.js'


class Item{
    constructor(id, title, posterPath, backdropPath, category){
        this.id = id;
        this.title = title;
        this.poster_path = posterPath;
        this.backdrop_path = backdropPath;
        this.category = category;
    }

    static fromItem(rawItemData, category){
        if (category != 'movie' && category != 'tv'){
            return null;
        }
        var id = rawItemData["id"]
        var posterPath = getTmdbImageUrl(rawItemData["poster_path"], 'w500');        
        var backdropPath = getTmdbImageUrl(rawItemData["backdrop_path"], 'w500');        
        var title = "";
        if (category == 'movie'){
            title = rawItemData["title"]    
        }
        if (category == 'tv'){
            title = rawItemData["name"]    
        }              
        return new Item(id, title, posterPath, backdropPath, category);
    }

    static fromSearchResultItem(rawItemData){
        var category = rawItemData["media_type"];
        if (category != 'movie' && category != 'tv'){
            return null;
        }
        var id = rawItemData["id"]
        var posterPath = getTmdbImageUrl(rawItemData["poster_path"], 'w500');        
        var backdropPath = getTmdbImageUrl(rawItemData["backdrop_path"], 'w500');        
        var title = "";
        if (category == 'movie'){
            title = rawItemData["title"]    
        }
        if (category == 'tv'){
            title = rawItemData["name"]    
        }              
        return new Item(id, title, posterPath, backdropPath, category);
    }
}


export {
    Item,
};