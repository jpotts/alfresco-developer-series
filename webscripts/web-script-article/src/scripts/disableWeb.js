if (document.hasAspect("sc:webable")) {
	if (document.properties["sc:isActive"] == true) {
		document.properties["sc:isActive"] = false;
		document.properties["sc:published"] = new Date();
	
		document.save();
	}
} 