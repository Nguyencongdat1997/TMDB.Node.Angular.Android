export class MovieItem {
    public id: string;
    public title: string;
    public poster_path: string;
    public backdrop_path: string;
    public category: string;

    constructor(id, title, posterPath, backdropPath, category) {
        this.id = id;
        this.title = title;
        this.poster_path = posterPath;
        this.backdrop_path = backdropPath;
        this.category = category;
    }

    static fromJSON(rawItemData) {
        return new MovieItem(rawItemData["id"],
            rawItemData["title"],
            rawItemData["poster_path"],
            rawItemData["backdrop_path"],
            rawItemData["category"]
        );
    }

}
