import {getTmdbImageUrl} from '../utils/getTmdbImageUrl.js';
import {parseTmdbGenres} from '../utils/parseTmdbGenres.js';
import {parseTmdbLanguage} from '../utils/parseTmdbLanguage.js';


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


class ItemDetail{
    constructor(id, category, title, genres, spokenLanguage, date, runtime, overview, voteAverate, tagline){
        this.id = id;
        this.category = category;
        this.title = title;
        this.genres = genres
        this.spoken_language = spokenLanguage;
        this.date = date;
        this.runtime = runtime;
        this.overview = overview;
        this.vote_average = voteAverate;
        this.tagline = tagline;
        
    }

    static fromItem(id, category, rawItemData, genresDict){
        if (category != 'movie' && category != 'tv'){
            return null;
        }
        var title = null;
        var runtime = null;
        var date = null
        if (category == 'movie'){
            title = rawItemData['title'];
            runtime = rawItemData['runtime'];
            date = rawItemData['release_date'];
        }
        if (category == 'tv'){
            title = rawItemData['name'];  
            runtime = rawItemData['episode_run_time'];
            date = rawItemData['first_air_date'];
        }              
        var genres = parseTmdbGenres(rawItemData['genres'], genresDict);
        var spokenLanguage = parseTmdbLanguage(rawItemData['spoken_languages']);        
        var overview = rawItemData['overview'];
        var voteAverate = rawItemData['vote_average'];
        var tagline = rawItemData['tagline'];
        return new ItemDetail(id, category, title, genres, spokenLanguage, date, runtime, overview, voteAverate, tagline);
    }
}


class Cast{
    constructor(id, name, character, profilePath){
        this.id = id;
        this.name = name;
        this.character = character;
        this.profile_path = profilePath;
    }

    static fromRawCast(rawCast){
        var id = rawCast['id'];
        var name = rawCast['name'];
        var character = rawCast['character'];
        var profilePath = getTmdbImageUrl(rawCast['profile_path'], 'w500');
        return new Cast(id, name, character, profilePath);
    }
}


class CastExternalLinks{
    constructor(imdbID, instaID, fbID, twiterID){
        if (imdbID == null || imdbID==''){
            this.imdb_id = null;
        }
        else{
            this.imdb_id = imdbID;
        }
        if (instaID == null || instaID==''){
            this.insta_id = null;
        }
        else{
            this.insta_id = instaID;            
        }
        if (fbID == null || fbID==''){
            this.fb_id = null;
        }
        else{
            this.fb_id = fbID;
        }
        if (twiterID == null || twiterID==''){
            this.twiter_id = null;
        }
        else{
            this.twiter_id = twiterID;
        }
    }

    static fromRawExternalLinks(rawExternalLinks){
        return new CastExternalLinks(
            rawExternalLinks['imdb_id'], 
            rawExternalLinks['instagram_id'],
            rawExternalLinks['facebook_id'], 
            rawExternalLinks['twitter_id'],
        );
    }
}


class CastDetail{
    constructor(id, name, imageUrl, birthDate, birthPlace, gender, knownFor, otherNames, homepage, externalLinks, biography){
        this.id = id;
        this.name = name;
        this.image_url = imageUrl;
        this.birth_date = birthDate;
        this.birth_place = birthPlace;
        this.gender = gender;
        this.known_for = knownFor;
        this.other_names = otherNames;
        this.homepage = homepage;
        this.external_links = externalLinks;
        this.biography = biography;
    }

    static fromRawCastDetail(rawCastDetail, rawExternalLinks){
        var id = rawCastDetail['id'];
        var name = rawCastDetail['name'];
        var imageUrl = getTmdbImageUrl(rawCastDetail['profile_path'], 'w500');
        var birthDate = rawCastDetail['birthday'];
        var birthPlace = rawCastDetail['place_of_birth'];
        var gender = rawCastDetail['gender'];
        var knownFor = rawCastDetail['known_for_department'];
        var otherNames = rawCastDetail['also_known_as'].join(', ');
        var biography = rawCastDetail['biography'];
        var homepage = rawCastDetail['homepage'];
        
        var externalLinks = CastExternalLinks.fromRawExternalLinks(rawExternalLinks);

        return new CastDetail(id, name, imageUrl, birthDate, birthPlace, gender, knownFor, otherNames, homepage, externalLinks, biography);
    }
}


class Review{
    constructor(author, content, createAt, url, rating, avatarPath){
        this.author = author;
        this.content = content;
        this.create_at = createAt;
        this.url = url;
        this.rating = rating;
        this.avatar_path = avatarPath;
    }

    static fromRawReview(rawReview){
        var author = rawReview['author_details']['username'];
        var content = rawReview['content'];
        var createAt = rawReview['created_at'];
        var url = rawReview['url'];
        var rating = rawReview['author_details']['rating'];
        if (rating == null){
            rating = 0;
        }
        var avatarPath = rawReview['author_details']['avatar_path'];
        return new Review(author, content, createAt, url, rating, avatarPath);
    }
}


export {
    Item,
    ItemDetail,
    Cast,
    CastDetail,
    Review,
};