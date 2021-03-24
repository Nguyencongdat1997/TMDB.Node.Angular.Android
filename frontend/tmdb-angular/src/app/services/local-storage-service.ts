import {MovieItem} from '../models/movieItems'

export class LocalStorageService {

    localStorage: any;
    continueWatching: MovieItem[];
    watchList: MovieItem[];

    constructor() { 
        this.localStorage = window.localStorage;
        this.continueWatching = new Array<MovieItem>();
        this.watchList = new Array<MovieItem>();
        if (this.getContinueWatching() == null){
            this.saveContinueWatching();            
        }
        if (this.getWatchList() == null){
            this.saveWatchList();            
        }
        // this.saveContinueWatching();
        // this.saveWatchList();   
    }

    public saveContinueWatching(){
        var txtContinueWatching = JSON.stringify(this.continueWatching);
        this.localStorage.setItem('tmdb-continue', txtContinueWatching);
    }

    public getContinueWatching(): MovieItem[]{        
        var txtContinueWatching = this.localStorage.getItem('tmdb-continue');
        var continueWatching = JSON.parse(txtContinueWatching);
        return continueWatching;
    }

    public addContinueWatching(newElement:MovieItem){
        this.continueWatching = this.getContinueWatching();
        var currentIndex = this.continueWatching.findIndex((e) => {return e['id'] == newElement['id']})
        if (currentIndex>=0 && currentIndex < this.continueWatching.length){
            this.continueWatching.splice(currentIndex, 1);
        }
        this.continueWatching.unshift(newElement);
        this.saveContinueWatching();
    }

    public saveWatchList(){
        var txt = JSON.stringify(this.watchList);
        this.localStorage.setItem('tmdb-watch-list', txt);
    }

    public getWatchList(): MovieItem[]{        
        var txt = this.localStorage.getItem('tmdb-watch-list');
        var watchList = JSON.parse(txt);
        return watchList;
    }

    public addWatchList(newElement:MovieItem){
        this.watchList = this.getWatchList();
        var currentIndex = this.watchList.findIndex((e) => {return e['id'] == newElement['id']})
        if (currentIndex>=0 && currentIndex < this.watchList.length){
            this.watchList.splice(currentIndex, 1);
        }
        this.watchList.unshift(newElement);
        this.saveWatchList();
    }

    public removeFromWatchList(element: MovieItem): Boolean{
        this.watchList = this.getWatchList();
        var currentIndex = this.watchList.findIndex((e) => {return e['id'] == element['id']});
        if (currentIndex>=0 && currentIndex < this.watchList.length){
            this.watchList.splice(currentIndex, 1);
            this.saveWatchList();
            return true;
        }
        return false;
    }

    public isItemInWatchList(element: MovieItem): Boolean{
        this.watchList = this.getWatchList();
        var currentIndex = this.watchList.findIndex((e) => {return e['id'] == element['id']});
        if (currentIndex>=0 && currentIndex < this.watchList.length){
            return true;
        }
        return false;
    }
}