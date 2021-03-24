import { Component, OnInit, Input} from '@angular/core';
import { Observable } from 'rxjs';

import { TmdbProxyServiceService } from '../../services/tmdb-proxy-service.service';
import {chunkArray} from '../../utils/chunkArray';

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
        "poster_path": "../../../../assets/img/person-placeholder.png",
        "backdrop_path": "../../../../assets/img/movie-placeholder.jpg",
        "category": "movie"
    };
    carouselList = [this._defaultItem];
    popularMovies = [Array.from({length:6}, (_)=>(this._defaultItem))];
    topRatedMovies =  [Array.from({length:6}, (_)=>(this._defaultItem))];
    trendingMovies = [Array.from({length:6}, (_)=>(this._defaultItem))];
    popularTvs = [Array.from({length:6}, (_)=>(this._defaultItem))];
    topRatedTvs = [Array.from({length:6}, (_)=>(this._defaultItem))];
    trendingTvs = [Array.from({length:6}, (_)=>(this._defaultItem))];

    constructor(private tmdbService: TmdbProxyServiceService) {
        tmdbService.getHomeData().subscribe(x => {
            this.data = x;
            this.carouselList = x.carousel_list.slice(0,5);
            this.popularMovies = chunkArray(x.popular_movies, 6);
            this.topRatedMovies = chunkArray(x.topRated_movies, 6);
            this.trendingMovies = chunkArray(x.trending_movies, 6);
            this.popularTvs = chunkArray(x.popular_tvs, 6);
            this.topRatedTvs = chunkArray(x.topRated_tvs, 6);
            this.trendingTvs = chunkArray(x.trending_tvs, 6);
        });
    }

    ngOnInit(): void {
    }

}


