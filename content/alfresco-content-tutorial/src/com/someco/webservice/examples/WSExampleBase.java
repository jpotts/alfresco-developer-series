package com.someco.webservice.examples;

import org.alfresco.webservice.repository.UpdateResult;
import org.alfresco.webservice.types.Reference;

import com.someco.examples.ExampleBase;

/**
 * This class holds common properties and methods for the example classes.
 * 
 * @author jpotts
 */
public class WSExampleBase extends ExampleBase {
    
	public void dumpUpdateResults(UpdateResult[] results) {
        for (UpdateResult updateResult : results) {
            String sourceId = "none";
            Reference source = updateResult.getSource();
            if (source != null) {
                sourceId = source.getUuid();
            }
            
            String destinationId = "none";
            Reference destination = updateResult.getDestination();
            
            if (destination != null) {
                destinationId = destination.getUuid();
            }
            
            System.out.println(
                    "Command = " + updateResult.getStatement() + 
                    "; Source = " + sourceId +
                    "; Destination = " + destinationId);
        }
    }

}
