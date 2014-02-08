<import resource="classpath:alfresco/module/behavior-tutorial-repo/scripts/rating.js">

function main() {
	logger.log("Inside whitepapers.get.js");
	var whitepapers = search.luceneSearch("PATH:\"/app:company_home/cm:Someco/*\" +TYPE:\"{http://www.someco.com/model/content/1.0}whitepaper\" +@sc\\:isActive:true");
	if (whitepapers == null || whitepapers.length == 0) {
	    logger.log("No whitepapers found");
	    status.code = 404;
	    status.message = "No whitepapers found";
	    status.redirect = true;
	} else {
	    var whitepaperInfo = new Array();
	    for (i = 0; i < whitepapers.length; i++) {
	        var whitepaper = new whitepaperEntry(whitepapers[i], getRating(whitepapers[i]));
	        whitepaperInfo[i] = whitepaper;
	    }
	    model.whitepapers = whitepaperInfo;
	    return model;
	}
}

function whitepaperEntry(whitepaper, rating) {
    this.whitepaper = whitepaper;
    this.rating = rating;
}

main();