import { Component, OnInit } from '@angular/core';
import {Observable, of, OperatorFunction} from 'rxjs';
import { catchError, debounceTime, distinctUntilChanged, map, switchMap, tap } from 'rxjs/operators';

import { TmdbProxyServiceService } from '../../../services/tmdb-proxy-service.service';

@Component({
    selector: 'app-search',
    templateUrl: './search.component.html',
    styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
    
    model: any;
    searching = false;
    searchFailed = false;

    constructor(private _service: TmdbProxyServiceService) { }

    ngOnInit(): void {
    }

    search = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      switchMap(term => 
            term === '' ? of([]) : 
            this._service.search(term)
            )
    )

    // search: OperatorFunction<string, readonly string[]> = (text$: Observable<string>) =>
    //     text$.pipe(
    //         debounceTime(300),
    //         distinctUntilChanged(),
    //         tap(() => this.searching = true),
    //         switchMap(term =>{
    //              ;                   
    //             return "hello";
    //         }                
    //         ),
    //         tap(() => this.searching = false)
    //     )

    public openNewTab(url){
        window.open(url, '_self');
    }
}
