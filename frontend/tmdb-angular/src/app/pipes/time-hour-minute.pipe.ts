import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'timeHourMinute'
})
export class TimeHourMinutePipe implements PipeTransform {

    transform(time: number): string {
        var hour = Math.round(time/60); 
        var minute = time%60;
        return hour+'hrs '+minute+'mins';
    }

}
