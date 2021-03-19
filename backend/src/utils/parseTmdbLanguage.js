function parseTmdbLanguageFunc(itemLanguages){
    var languagueTxt = itemLanguages.map(x => {        
        return x['english_name'];
    }).join(', ');   
    return languagueTxt;    
}


//Export
export const parseTmdbLanguage = parseTmdbLanguageFunc;