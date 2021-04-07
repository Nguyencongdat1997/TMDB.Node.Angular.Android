import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'timeHourMinute'
})
export class TimeHourMinutePipe implements PipeTransform {

    transform(time: number): string {
        var hour = Math.floor(time/60); 
        var minute = time%60;
        var result = '';
        if (hour >0){
            result += hour+'hrs ';
        }
        result += minute+'mins';
        return result;
    }

}
