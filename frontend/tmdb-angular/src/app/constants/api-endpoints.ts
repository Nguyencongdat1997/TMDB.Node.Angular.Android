import { environment } from 'src/environments/environment';

export const ApiEndpoints: { [key: string]: string } = {
  HomeUrl: environment.serverUrl + '/api/v1/home',
  SearchUrl: environment.serverUrl + '/api/v1/search',
  ItemUrl: environment.serverUrl + '/api/v1/item',
  CastUrl: environment.serverUrl + '/api/v1/cast',
};