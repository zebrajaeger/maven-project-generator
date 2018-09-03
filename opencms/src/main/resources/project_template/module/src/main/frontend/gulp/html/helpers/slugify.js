module.exports =  function(strValue, options) {
    var strReturn = strValue.toString().toLowerCase()
        .replace(/\s+/g, '-')           // Replace spaces with -
        .replace(/[^\w\-]+/g, '')       // Remove all non-word chars
        .replace(/\-\-+/g, '-')         // Replace multiple - with single -
        .replace(/^-+/, '')             // Trim - from start of text
        .replace(/-+$/, '');            // Trim - from end of text

    var arrSpecialChars = ['ä', 'ö', 'ü', 'ß'];
    var arrReplacedSpecialChars = ['ae', 'oe', 'ue', 'ss'];

    for (var i = 0, j = arrSpecialChars.length; i < j; i++ ) {
        strReturn = strReturn.replace(arrSpecialChars[i], arrReplacedSpecialChars[i]);
    }

    return strReturn;
};
