//calculate rating
function computeAverage(childAssocRef) {
		var parentRef = childAssocRef.parent;
		
		// check the parent to make sure it has the right aspect
		if (parentRef.hasAspect("{http://www.someco.com/model/ratings/1.0}rateable")) {
			// continue, this is what we want
		} else {
			logger.log("Rating's parent ref did not have rateable aspect.");
			return;
		}
		
		// get the parent node's children
		var children = parentRef.children;

		// iterate through the children to compute the total
		var average = 0.0;
		var total = 0;

		if (children != null && children.length > 0) {
			for (i in children) {
				var child = children[i];
				var rating = child.properties["{http://www.someco.com/model/ratings/1.0}rating"];
				total += rating;
			}
		
			// compute the average
			average = total / children.length;
		}			
	
		logger.log("Computed average:" + average);
		
		// store the average on the parent node
		parentRef.properties["{http://www.someco.com/model/ratings/1.0}averageRating"] = average;
		parentRef.save();
		
		logger.log("Property set");
		
		return;

}