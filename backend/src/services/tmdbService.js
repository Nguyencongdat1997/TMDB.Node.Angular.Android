import axios from "axios";

import {Item} from "../DTOs/models.js";


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
        carouselList: carouselList,
        popularMovies: popularMovies,
        topRatedMovies: topRatedMovies,
        trendingMovies: trendingMovies,
        popularTvs: popularTvs,
        topRatedTvs: topRatedTvs,
        trendingTvs: trendingTvs,
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


//Export
export const getHomeData = getHomeDataFunc;
export const getSearchResult = getSearchResultsFunc;