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

    constructor(private tmdbService: TmdbProxyServiceService) {
        tmdbService.getHomeData().subscribe(x => {
            this.data = x;
            this.carouselList = x.carousel_list.slice(0,5);
        });
    }

    ngOnInit(): void {
    }

}
