import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'app-horizontal-movie-list',
    templateUrl: './horizontal-movie-list.component.html',
    styleUrls: ['./horizontal-movie-list.component.css']
})
export class HorizontalMovieListComponent implements OnInit {
    @Input()
    movieItemSets: [[]];
    mobile: boolean;

    constructor() {
        if (window.screen.width > 777){
            this.mobile = true;
        }
    }

    ngOnInit(): void {
    }

}
