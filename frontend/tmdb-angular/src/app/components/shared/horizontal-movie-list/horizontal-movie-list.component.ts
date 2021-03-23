import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'app-horizontal-movie-list',
    templateUrl: './horizontal-movie-list.component.html',
    styleUrls: ['./horizontal-movie-list.component.css']
})
export class HorizontalMovieListComponent implements OnInit {
    @Input()
    movieItemSets: any;

    images = [944, 1011, 984].map((n) => `https://picsum.photos/id/${n}/900/500`);
    constructor() { }

    ngOnInit(): void {
    }

}
