import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Observable } from 'rxjs';

import { TmdbProxyServiceService } from '../../services/tmdb-proxy-service.service';

let apiLoaded=false;

@Component({
    selector: 'app-movie-detail',
    templateUrl: './movie-detail.component.html',
    styleUrls: ['./movie-detail.component.css']
})
export class MovieDetailComponent implements OnInit {

    data: [];
    _tmdbService: TmdbProxyServiceService;
    twitterShareText : string;
    casts: [];

    constructor(
        private tmdbService: TmdbProxyServiceService,
        private route: ActivatedRoute,
    ) {
        this._tmdbService = tmdbService;
        this.data = null;
    }

    ngOnInit(): void {
        var id = this.route.snapshot.paramMap.get('id');
        var category = this.route.snapshot.paramMap.get('category');
        if (category != 'movie' && category != 'tv'){
            // TODO: do sthm
        }
        this.tmdbService.getItemDetail(id, category).subscribe(
            data => {
                this.data = data;    
                this.twitterShareText = "Wacth " + data.item_detail.title + "%0D%0A " 
                                        + data.youtube_video.url + "%0D%0A " 
                                        + "%23USC %23CSCI571 %23FightOn";
                this.casts = data.casts;
            }
        );

        if (!apiLoaded) {
            // This code loads the IFrame Player API code asynchronously, according to the instructions at
            // https://developers.google.com/youtube/iframe_api_reference#Getting_Started
            const tag = document.createElement('script');
            tag.src = 'https://www.youtube.com/iframe_api';
            document.body.appendChild(tag);
            apiLoaded = true;
          }
    }

    ngOnDestroy() {
        //this.sub.unsubscribe();
    }

}
