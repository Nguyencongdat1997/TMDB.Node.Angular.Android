import { Component, OnInit, Input} from '@angular/core';
import { Observable } from 'rxjs';

import { TmdbProxyServiceService } from '../../services/tmdb-proxy-service.service';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {    
    data = [];
    carouselList = [];
    popularMovies = [];

    constructor(private tmdbService: TmdbProxyServiceService) {
        tmdbService.getHomeData().subscribe(x => {
            this.data = x;
            this.carouselList = x.carousel_list.slice(0,5);
            this.popularMovies = chunkArray(x.popular_movies, 6);
        });
    }

    ngOnInit(): void {
    }

}

function chunkArray(array, chunkSize){
    var index = 0;
    var arrayLength = array.length;
    var chunkedArray = [];
    
    for (index = 0; index < arrayLength; index += chunkSize) {
        var newChunk = array.slice(index, index+chunkSize);
        chunkedArray.push(newChunk);
    }
    return chunkedArray;
}
