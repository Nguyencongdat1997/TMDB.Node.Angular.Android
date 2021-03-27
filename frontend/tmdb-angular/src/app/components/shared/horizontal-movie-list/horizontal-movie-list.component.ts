import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'app-horizontal-movie-list',
    templateUrl: './horizontal-movie-list.component.html',
    styleUrls: ['./horizontal-movie-list.component.css']
})
export class HorizontalMovieListComponent implements OnInit {
    @Input()
    movieItemSets: [[]];

    constructor() { }

    ngOnInit(): void {
    }

}
