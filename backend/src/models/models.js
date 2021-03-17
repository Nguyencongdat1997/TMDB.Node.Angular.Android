import {getTmdbImageUrl} from '../utils/getTmdbImageUrl.js'


class Item{
    constructor(id, title, posterPath, category){
        this.id = id;
        this.title = title;
        this.poster_path = posterPath;
        this.category = category
    }

    static toItem(rawItemData, category){
        var posterPath = getTmdbImageUrl(rawItemData["poster_path"], 'w500');
        var title = rawItemData["title"]
        var id = rawItemData["id"]
        return new Item(id, title, posterPath, category);
    }
}


export {
    Item
};