import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
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
    
    @ViewChild('alertcontainer') alertContainer: ElementRef;

    data: [];
    movieItem: MovieItem;
    _tmdbService: TmdbProxyServiceService;
    localStorageService: LocalStorageService;
    twitterShareText : string;
    casts: [];
    reviews: [];
    recommendations: Array<Array<any>>;
    similarItems: Array<Array<any>>;
    defaultCast = {
        'id': '',
        'name': '',
    };
    selectedCast;
    isItemAddedToWatchList: boolean;
    isAlertShowed = false;
    timeOut: any;
    chunkSize = 6;
    mobile: boolean;

    constructor(
        private tmdbService: TmdbProxyServiceService,
        private route: ActivatedRoute,
        private modalService: NgbModal,        
    ) {
        if (window.screen.width <= 777){
            this.chunkSize = 1;
            this.mobile = true;
        }
        else{
            this.chunkSize = 6;
            this.mobile = false;
        }
        this._tmdbService = tmdbService;
        this.data = null;
        this.selectedCast = this.defaultCast;
        this.localStorageService = new LocalStorageService();
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
                this.recommendations = chunkArray(data.recommendations, this.chunkSize);
                this.similarItems = chunkArray( data.similars, this.chunkSize);

                this.isItemAddedToWatchList = this.localStorageService.isItemInWatchList(this.movieItem);
                this.localStorageService.addContinueWatching(this.movieItem);
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
        this.selectedCast = this.defaultCast;
        this.tmdbService.getCastDetail(castId).subscribe(
            data => {
                this.selectedCast = data;
            }
        );

        this.modalService
            .open(content, {ariaLabelledBy: 'modal-basic-title', size: 'lg', scrollable: true})
            .result
            .then((result) => {
                //   this.closeResult = `Closed with: ${result}`;
                }, (reason) => {
                //   this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
                }
            );
    }

    addToWatchListBtnClickedHandle(){
        if (!this.isItemAddedToWatchList){
            this.localStorageService.addWatchList(this.movieItem);
        }
        else{
            this.localStorageService.removeFromWatchList(this.movieItem);
        }
        this.isItemAddedToWatchList = this.localStorageService.isItemInWatchList(this.movieItem);
        this.showAlert();
    }    
    
    showAlert(){
        this.isAlertShowed = true;
        clearTimeout(this.timeOut);

        this.timeOut = setTimeout(() => {
            this.isAlertShowed = false;
        }, 5000);
    }
}
