<import resource="classpath:alfresco/module/behavior-tutorial-platform/scripts/rating.js">

// Check behaviour is set and the name of the behaviour
if (!behaviour || (behaviour.name == null || behaviour.name != "onDeleteNode")) {
    logger.log("The behaviour behaviour object or name has not been set correctly.");
} else {
    logger.log("Behaviour name: " + behaviour.name);

    // Check the arguments
    if (behaviour.args == null) {
        logger.log("The args have not been set.");
    } else {
        if (behaviour.args.length == 2) {
            var childAssoc = behaviour.args[0];
            logger.log("Calling compute average");
            computeAverage(childAssoc);
        } else {
            logger.log("The number of arguments is incorrect.");
        }
    }
}
