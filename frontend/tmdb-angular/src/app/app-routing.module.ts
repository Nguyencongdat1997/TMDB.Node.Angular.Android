import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './components/home/home.component';
import { MovieDetailComponent } from './components/movie-detail/movie-detail.component';
import { MylistComponent } from './components/mylist/mylist.component';

const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'watch/tv/:id', component: MovieDetailComponent },
    { path: 'watch/movie/:id', component: MovieDetailComponent },
    { path: 'mylist', component: MylistComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
