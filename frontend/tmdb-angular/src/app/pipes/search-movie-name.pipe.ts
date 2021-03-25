import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'searchMovieName'
})
export class SearchMovieNamePipe implements PipeTransform {

  transform(value: string, searchTerm: string) {
    var range = searchTerm.length;
    var startPoint = value.toLocaleLowerCase().indexOf(searchTerm.toLocaleLowerCase());
    if (startPoint<0){
        return value;
    }
    var leftSide = value.slice(0,startPoint);
    var termPart = value.slice(startPoint,startPoint+range);
    var rightSide = value.slice(startPoint+range, value.length);
    var highlighedName = `
     ${leftSide}<b>${termPart}</b>${rightSide}</span>
    `;
    return highlighedName;
  }

}
