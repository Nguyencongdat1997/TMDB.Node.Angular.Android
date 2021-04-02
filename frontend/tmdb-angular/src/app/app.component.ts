import { Component } from '@angular/core';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent {
    public isMenuCollapsed = true;
    public selectedTab = 'home';

    mobile:boolean;

    public navClick(target: string) {
        this.isMenuCollapsed = true;
        this.selectedTab = target;
    }

    constructor(){
        if (window.screen.width <= 500){
            this.mobile = true;
        }
        else{
            this.mobile = false;
        }        
    }
}
