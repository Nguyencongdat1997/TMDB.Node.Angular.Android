import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  public isMenuCollapsed = true;
  public selectedTab = 'home';

  public navClick(target: string){    
    this.isMenuCollapsed = true;
    this.selectedTab = target;
  }
}
