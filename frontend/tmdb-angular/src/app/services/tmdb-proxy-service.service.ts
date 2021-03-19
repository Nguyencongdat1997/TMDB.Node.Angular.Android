import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import {ApiEndpoints} from '../constants/api-endpoints'

@Injectable({
    providedIn: 'root'
})
export class TmdbProxyServiceService {
    public url = 'http://localhost:4000/api/v1/home';

    constructor(private http: HttpClient) { }

    getHomeData(): Observable<any>{
        var url = ApiEndpoints['HomeUrl'];
        return this.http.get(url);
    }
}
