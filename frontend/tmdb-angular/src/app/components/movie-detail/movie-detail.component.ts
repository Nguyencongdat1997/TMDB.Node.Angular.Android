import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Observable } from 'rxjs';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';

import { TmdbProxyServiceService } from '../../services/tmdb-proxy-service.service';
import {chunkArray} from '../../utils/chunkArray';
import {LocalStorageService} from '../../services/local-storage-service';
import { MovieItem } from 'src/app/models/movieItems';

let apiLoaded=false;

@Component({
    selector: 'app-movie-detail',
    templateUrl: './movie-detail.component.html',
    styleUrls: ['./movie-detail.component.css']
})
export class MovieDetailComponent implements OnInit {

    data: [];
    movieItem: MovieItem;
    _tmdbService: TmdbProxyServiceService;
    localStroageService: LocalStorageService;
    twitterShareText : string;
    casts: [];
    reviews: [];
    recommendations: Array<Array<any>>;
    similarItems: Array<Array<any>>;
    selectedCast= {
        'id': '',
        'name': '',
    };
    isItemAddedToWatchList: Boolean;

    constructor(
        private tmdbService: TmdbProxyServiceService,
        private route: ActivatedRoute,
        private modalService: NgbModal,        
    ) {
        this._tmdbService = tmdbService;
        this.data = null;
        this.localStroageService = new LocalStorageService();
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
                this.movieItem = MovieItem.fromJSON(data.item_detail);
                this.twitterShareText = "Wacth " + data.item_detail.title + "%0D%0A " 
                                        + data.youtube_video.url + "%0D%0A " 
                                        + "%23USC %23CSCI571 %23FightOn";
                this.casts = data.casts;
                this.reviews = data.reviews;
                this.recommendations = chunkArray(data.recommendations,6);
                this.similarItems = chunkArray( data.similars, 6);
                this.isItemAddedToWatchList = this.localStroageService.isItemInWatchList(this.movieItem);
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

    openModal(content, castId) {
        this.tmdbService.getCastDetail(castId).subscribe(
            data => {
                this.selectedCast = data;
            }
        );

        this.modalService
            .open(content, {ariaLabelledBy: 'modal-basic-title', size: 'xl'})
            .result
            .then((result) => {
                //   this.closeResult = `Closed with: ${result}`;
                }, (reason) => {
                //   this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
                }
            );
    }

    addToWatchList() {
        this.localStroageService.addWatchList(this.movieItem);
        this.isItemAddedToWatchList = this.localStroageService.isItemInWatchList(this.movieItem);
    }
    removeFromWatchList() {
        this.localStroageService.removeFromWatchList(this.movieItem);
        this.isItemAddedToWatchList = this.localStroageService.isItemInWatchList(this.movieItem);
    }
}
