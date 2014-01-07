// add the aspect to this document if it needs it
if (document.hasAspect("scr:rateable")) {
  logger.log("Document already as aspect");
} else {
  logger.log("Adding rateable aspect");
  document.addAspect("scr:rateable");
}

// randomly pick a num b/w 1 and 5 inclusive
var ratingValue = Math.floor(Math.random()*5) + 1;

var props = new Array(2);
props["scr:rating"] = ratingValue;
props["scr:rater"] = person.properties.userName;

// create a new ratings node and set its properties
var ratingsNode = document.createNode("rating" + new Date().getTime(), "scr:rating", props, "scr:ratings");
ratingsNode.save();
logger.log("Ratings node saved.");