import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { MovieDetailComponent } from './components/movie-detail/movie-detail.component';
import { MylistComponent } from './components/mylist/mylist.component';
import { CarouselComponent } from './components/shared/carousel/carousel.component';
import { HorizontalMovieListComponent } from './components/shared/horizontal-movie-list/horizontal-movie-list.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    MovieDetailComponent,
    MylistComponent,
    CarouselComponent,
    HorizontalMovieListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
