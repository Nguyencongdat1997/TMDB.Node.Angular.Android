import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule } from '@angular/common/http';
import { YouTubePlayerModule} from '@angular/youtube-player';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { MovieDetailComponent } from './components/movie-detail/movie-detail.component';
import { MylistComponent } from './components/mylist/mylist.component';
import { CarouselComponent } from './components/shared/carousel/carousel.component';
import { HorizontalMovieListComponent } from './components/shared/horizontal-movie-list/horizontal-movie-list.component';
import { TimeHourMinutePipe } from './pipes/time-hour-minute.pipe';
import { GenderPipe } from './pipes/gender.pipe';
import { SearchComponent } from './components/shared/search/search.component';
import { SearchMovieNamePipe } from './pipes/search-movie-name.pipe';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    MovieDetailComponent,
    MylistComponent,
    CarouselComponent,
    HorizontalMovieListComponent,
    TimeHourMinutePipe,
    GenderPipe,
    SearchComponent,
    SearchMovieNamePipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule,
    // NoopAnimationsModule,
    YouTubePlayerModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
