import { Component, OnInit, Input} from '@angular/core';
import { Observable } from 'rxjs';

import { TmdbProxyServiceService } from '../../services/tmdb-proxy-service.service';
import { chunkArray } from '../../utils/chunkArray';
import { LocalStorageService} from '../../services/local-storage-service';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {    
    
    data = [];
    _defaultItem = {
        "id": 0,
        "title": "",
        "poster_path": "../../../../assets/img/movie-placeholder.jpg",
        "backdrop_path": "../../../../assets/img/movie-placeholder.jpg",
        "category": "movie"
    };
    carouselList = [this._defaultItem];
    continueWathcingList: Array<Array<any>>;
    popularMovies = [Array.from({length:6}, (_)=>(this._defaultItem))];
    topRatedMovies =  [Array.from({length:6}, (_)=>(this._defaultItem))];
    trendingMovies = [Array.from({length:6}, (_)=>(this._defaultItem))];
    popularTvs = [Array.from({length:6}, (_)=>(this._defaultItem))];
    topRatedTvs = [Array.from({length:6}, (_)=>(this._defaultItem))];
    trendingTvs = [Array.from({length:6}, (_)=>(this._defaultItem))];
    mobile = false;

    constructor(private tmdbService: TmdbProxyServiceService) {
        var chunkSize = 6;
        if (window.screen.width <= 777){
            chunkSize = 1;
            this.mobile = true;
        }
        if (window.screen.width > 777){
            chunkSize = 6;
            this.mobile = false;
        }
        tmdbService.getHomeData().subscribe(x => {
            this.data = x;
            this.carouselList = x.carousel_list.slice(0,5);                            
            this.popularMovies = chunkArray(x.popular_movies, chunkSize);
            this.topRatedMovies = chunkArray(x.topRated_movies, chunkSize);
            this.trendingMovies = chunkArray(x.trending_movies, chunkSize);
            this.popularTvs = chunkArray(x.popular_tvs, chunkSize);
            this.topRatedTvs = chunkArray(x.topRated_tvs, chunkSize);
            this.trendingTvs = chunkArray(x.trending_tvs, chunkSize);
        });
        
        var localStorageService = new LocalStorageService();
        this.continueWathcingList = chunkArray(localStorageService.getContinueWatching().slice(0,24), chunkSize);
    }

    ngOnInit(): void {
    }

}


