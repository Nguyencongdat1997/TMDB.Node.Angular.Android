import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'gender'
})
export class GenderPipe implements PipeTransform {

    transform(genderNum: number): string {
        if (genderNum == 1){
            return 'Female';
        }
        if (genderNum == 2){
            return 'Male';
        }
        return 'Other';
    }

}
