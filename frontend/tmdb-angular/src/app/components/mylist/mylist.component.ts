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

    constructor() { 
        this.localStorageService = new LocalStorageService;
        this.movieItems = chunkArray(this.localStorageService.getWatchList(),6);
    }

    ngOnInit(): void {
    }

}
