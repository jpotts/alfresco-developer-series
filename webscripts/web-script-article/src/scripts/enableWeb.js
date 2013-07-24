if (document.hasAspect("sc:webable")) {
  //doc already has aspect
} else {
  document.addAspect("sc:webable");
}

document.properties["sc:isActive"] = true;
document.properties["sc:published"] = new Date();

document.save();
