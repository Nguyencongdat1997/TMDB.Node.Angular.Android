import { Component, OnInit } from '@angular/core';

import { LocalStorageService } from '../../services/local-storage-service';
import { MovieItem } from 'src/app/models/movieItems';
import { chunkArray } from '../../utils/chunkArray';

@Component({
    selector: 'app-mylist',
    templateUrl: './mylist.component.html',
    styleUrls: ['./mylist.component.css']
})
export class MylistComponent implements OnInit {

    localStorageService: LocalStorageService;
    movieItems: MovieItem[][];
    mobile: boolean;
    chunkSize = 6;

    constructor() { 
        if (window.screen.width <= 777){
            this.chunkSize = 1;
            this.mobile = true;
        }
        else{
            this.chunkSize = 6;
            this.mobile = false;
        }

        this.localStorageService = new LocalStorageService;
        this.movieItems = chunkArray(this.localStorageService.getWatchList(),this.chunkSize);

    }

    ngOnInit(): void {
    }

}
