import { Component, OnInit, Input} from '@angular/core';
import { Observable } from 'rxjs';

import { TmdbProxyServiceService } from '../../services/tmdb-proxy-service.service';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
    
    @Input()
    result$: Observable<any>;

    constructor(private tmdbService: TmdbProxyServiceService) {
        this.result$ = tmdbService.getHomeData();
    }

    ngOnInit(): void {
    }

}
