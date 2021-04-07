export function chunkArray(array, chunkSize): any{
    var index = 0;
    var arrayLength = array.length;
    var chunkedArray = [];
    
    for (index = 0; index < arrayLength; index += chunkSize) {
        var newChunk = array.slice(index, index+chunkSize);
        chunkedArray.push(newChunk);
    }
    return chunkedArray;
}