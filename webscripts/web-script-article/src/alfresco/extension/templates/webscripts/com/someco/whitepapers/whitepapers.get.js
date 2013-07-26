<import resource="classpath:alfresco/extension/scripts/rating.js">

/* this worked in 4.0.c. in 4.2.c you must specify the namespace abbreviation instead for PATH */
/*
var whitepapers = search.luceneSearch("PATH:\"/{http://www.alfresco.org/model/application/1.0}company_home/{http://www.alfresco.org/model/content/1.0}Someco\" +TYPE:\"{http://www.someco.com/model/content/1.0}whitepaper\"");
*/
var whitepapers = search.luceneSearch("PATH:\"/app:company_home/cm:Someco/*\" +TYPE:\"{http://www.someco.com/model/content/1.0}whitepaper\"");
logger.log("Got whitepapers");

if (whitepapers == null || whitepapers.length == 0) {
	logger.log("No whitepapers found");
	status.code = 404;
	status.message = "No whitepapers found";
	status.redirect = true;
} else {
	logger.log("User:" + args.user);
	var whitepaperInfo = new Array();
	for (i = 0; i < whitepapers.length; i++) {
		var whitepaper = new whitepaperEntry(whitepapers[i], getRating(whitepapers[i], args.user));
		whitepaperInfo[i] = whitepaper;
	}
	model.whitepapers = whitepaperInfo;
}

function whitepaperEntry(whitepaper, rating) {
	this.whitepaper = whitepaper;
	this.rating = rating;
}