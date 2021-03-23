import axios from "axios";

import {Item, ItemDetail, Cast, CastDetail, Review} from "../DTOs/models.js";


var tmdpUrl = 'https://api.themoviedb.org/3';
var tmdpKey = 'b83b77fbc8d6732bebccd197763554a4';


async function getCurrentlyPlayingMovieFunc(){
    var queryUrl =  tmdpUrl + '/movie/now_playing?api_key=' + tmdpKey + '&language=enUS&page=1';
    var rawData = "";    
    await axios.get(queryUrl)
    .then(response => {     
        rawData = response.data;    
        console.log("aaa"+reponse.data);
    })
    .catch(error => {
        // console.log(error);
    }); 
    return rawData;   
}

async function getPopuplarMovieFunc(){
    var queryUrl =  tmdpUrl + '/movie/popular?api_key=' + tmdpKey + '&language=enUS&page=1';
    var rawData = "";    
    await axios.get(queryUrl)
    .then(response => {     
        rawData = response.data;    
    })
    .catch(error => {
        console.log(error);
    }); 
    return rawData;   
}

async function getTrendingMovieFunc(){
    var queryUrl =  tmdpUrl + '/trending/movie/day?api_key=' + tmdpKey + '&language=enUS&page=1';
    var rawData = "";    
    await axios.get(queryUrl)
    .then(response => {     
        rawData = response.data;    
    })
    .catch(error => {
        console.log(error);
    }); 
    return rawData;   
}

async function getTopRatedMovieFunc(){
    var queryUrl =  tmdpUrl + '/movie/top_rated?api_key=' + tmdpKey + '&language=enUS&page=1';
    var rawData = "";    
    await axios.get(queryUrl)
    .then(response => {     
        rawData = response.data;    
    })
    .catch(error => {
        console.log(error);
    }); 
    return rawData;   
}

async function getPopuplarTvFunc(){
    var queryUrl =  tmdpUrl + '/tv/popular?api_key=' + tmdpKey + '&language=enUS&page=1';
    var rawData = "";    
    await axios.get(queryUrl)
    .then(response => {     
        rawData = response.data;    
    })
    .catch(error => {
        console.log(error);
    }); 
    return rawData;   
}

async function getTrendingTvFunc(){
    var queryUrl =  tmdpUrl + '/trending/tv/day?api_key=' + tmdpKey + '&language=enUS&page=1';
    var rawData = "";    
    await axios.get(queryUrl)
    .then(response => {     
        rawData = response.data;    
    })
    .catch(error => {
        console.log(error);
    }); 
    return rawData;   
}

async function getTopRatedTvFunc(){
    var queryUrl =  tmdpUrl + '/tv/top_rated?api_key=' + tmdpKey + '&language=enUS&page=1';
    var rawData = "";    
    await axios.get(queryUrl)
    .then(response => {     
        rawData = response.data;    
    })
    .catch(error => {
        console.log(error);
    }); 
    return rawData;   
}

async function getHomeDataFunc(){
    var carouselList = (await getCurrentlyPlayingMovieFunc()).results.map(rawItemData => {
        return Item.fromItem(rawItemData, 'movie');
    });

    var popularMovies = (await getPopuplarMovieFunc()).results.map(rawItemData => {
        return Item.fromItem(rawItemData, 'movie');
    });

    var topRatedMovies = (await getTopRatedMovieFunc()).results.map(rawItemData => {
        return Item.fromItem(rawItemData, 'movie');
    });

    var trendingMovies = (await getTrendingMovieFunc()).results.map(rawItemData => {
        return Item.fromItem(rawItemData, 'movie');
    });

    var popularTvs = (await getPopuplarTvFunc()).results.map(rawItemData => {
        return Item.fromItem(rawItemData, 'tv');
    });

    var topRatedTvs = (await getTopRatedTvFunc()).results.map(rawItemData => {
        return Item.fromItem(rawItemData, 'tv');
    });

    var trendingTvs = (await getTrendingTvFunc()).results.map(rawItemData => {
        return Item.fromItem(rawItemData, 'tv');
    });
    
    var result = {
        carousel_list: carouselList,
        popular_movies: popularMovies,
        topRated_movies: topRatedMovies,
        trending_movies: trendingMovies,
        popular_tvs: popularTvs,
        topRated_tvs: topRatedTvs,
        trending_tvs: trendingTvs,
    };

    return result;
}


async function getSearchResultsFunc(keyword){
    var queryUrl =  tmdpUrl + '/search/multi?api_key=' + tmdpKey + '&language=en-US&query=' + keyword;
    
    var rawData = {};    
    await axios.get(queryUrl)
    .then(response => {     
        rawData = response.data;    
    })
    .catch(error => {
        console.log(error);
    }); 
    
    var result= rawData.results.map(rawItemData => {
        return Item.fromSearchResultItem(rawItemData);
    });
    
    return result;
}


async function getRecommendedItems(category, itemId){
    var queryUrl =  tmdpUrl + '/' + category + '/' + itemId + '/recommendations?api_key=' + tmdpKey + '&language=en-US&&page=1';
    var rawData = "";    
    await axios.get(queryUrl)
    .then(response => {     
        rawData = response.data;    
    })
    .catch(error => {
        console.log(error);
    }); 
    return rawData;   
}

async function getSimilarItems(category, itemId){
    var queryUrl =  tmdpUrl + '/' + category + '/' + itemId + '/similar?api_key=' + tmdpKey + '&language=en-US&&page=1';
    var rawData = "";    
    await axios.get(queryUrl)
    .then(response => {     
        rawData = response.data;    
    })
    .catch(error => {
        console.log(error);
    }); 
    return rawData;   
}

async function getItemDetailFunc(id, category){
    if (category != 'tv' && category != 'movie'){
        return null;
    }
    
    // Get Genres
    var genresQueryUrl = tmdpUrl + '/genre/' + category + '/list?api_key=' + tmdpKey + '&language=en-US';
    var rawGenresData = {};    
    await axios.get(genresQueryUrl)
    .then(response => {     
        rawGenresData = response.data;    
    })
    .catch(error => {
        console.log(error);
        
    }); 
    var genresDict = rawGenresData.genres;

    // Get basic information
    var queryUrl =  tmdpUrl + '/' + category + '/' + id + '?api_key=' + tmdpKey + '&language=en-US&&page=1';
    var rawData = {};    
    await axios.get(queryUrl)
    .then(response => {     
        rawData = response.data;    
    })
    .catch(error => {
        console.log(error);
        rawData = null;
    }); 
    if (rawData == null){
        return "";
    }
    var itemDetail = ItemDetail.fromItem(id, category, rawData, genresDict);

    // Get casts
    var queryUrl =  tmdpUrl + '/' + category + '/' + id + '/credits?api_key=' + tmdpKey + '&language=en-US&&page=1';
    var rawCastsData = {};    
    await axios.get(queryUrl)
    .then(response => {     
        rawCastsData = response.data;    
    })
    .catch(error => {
        console.log(error);
    }); 
    var casts = rawCastsData.cast.map(x=>{
        return Cast.fromRawCast(x);
    });

    // Get reivews
    var queryUrl =  tmdpUrl + '/' + category + '/' + id + '/reviews?api_key=' + tmdpKey + '&language=en-US&&page=1';
    var rawReviewsData = {};    
    await axios.get(queryUrl)
    .then(response => {     
        rawReviewsData = response.data;    
    })
    .catch(error => {
        console.log(error);
    }); 
    var review = rawReviewsData.results.map(x=>{
        return Review.fromRawReview(x);
    });

    // Get recommended movies
    var rawRecommendations = await getRecommendedItems(category, id);
    var recommendations = rawRecommendations.results.map(x=>{
        return Item.fromItem(x, category);
    });

    // Get similar movies
    var rawSimilarItems = await getSimilarItems(category, id);
    var similarItems = rawSimilarItems.results.map(x=>{
        return Item.fromItem(x, category);
    });

    // Merge data
    var result= {
        item_detail: itemDetail,
        casts: casts,
        reviews: review,
        recommendations: recommendations,
        similars: similarItems,
    };

    return result;
}


async function getCastDetailFunc(id){
    // Get Detail
    var queryUrl = tmdpUrl + '/person/' + id + '?api_key=' + tmdpKey + '&language=en-US&page=1';
    var rawDetailData = {};    
    await axios.get(queryUrl)
    .then(response => {     
        rawDetailData = response.data;    
    })
    .catch(error => {
        console.log(error);
    });     

    // Get External Ids
    var queryExternalIdsUrl =  tmdpUrl + '/person/' + id + '/external_ids?api_key=' + tmdpKey + '&language=en-US&page=1';
    var rawExternalIds = {};    
    await axios.get(queryExternalIdsUrl)
    .then(response => {     
        rawExternalIds = response.data;    
    })
    .catch(error => {
        console.log(error);
    }); 

    // Merge data
    var result= CastDetail.fromRawCastDetail(rawDetailData, rawExternalIds);

    return result;
}


//Export
export const getHomeData = getHomeDataFunc;
export const getSearchResult = getSearchResultsFunc;
export const getItemDetail = getItemDetailFunc;
export const getCastDetail = getCastDetailFunc;