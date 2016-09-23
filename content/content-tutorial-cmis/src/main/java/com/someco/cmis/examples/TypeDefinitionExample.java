package com.someco.cmis.examples;

import java.util.Map;

import org.apache.chemistry.opencmis.client.api.ObjectType;
import org.apache.chemistry.opencmis.commons.definitions.PropertyDefinition;

public class TypeDefinitionExample extends CMISExampleBase {
	private static final String USAGE = "java TypeDefinitionExample <typeId> <propertyId>";
	
    public static void main(String[] args) {
    	if (args.length != 4)  doUsage(TypeDefinitionExample.USAGE);
    	TypeDefinitionExample tde = new TypeDefinitionExample();
    	tde.setUser(args[0]);
    	tde.setPassword(args[1]);
    	try {
    		tde.doExample(args[2], args[3]);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void doExample(String typeId, String propId) throws Exception {
    	ObjectType typeDef = getSession().getTypeDefinition(typeId);
    	Map<String, PropertyDefinition<?>> propDefs = typeDef.getPropertyDefinitions();
    	PropertyDefinition<?> propDef = propDefs.get(propId);
    	if (propDef == null) {
    		throw new Exception("Property not found: " + propId);
    	}
    	System.out.println("Type: " + typeId);
    	System.out.println(String.format("Is %s queryable? %s", propDef.getId(), propDef.isQueryable()));
    	System.out.println(String.format("Is %s orderable? %s", propDef.getId(), propDef.isOrderable()));
    	System.out.println(String.format("Is %s required? %s", propDef.getId(), propDef.isRequired()));    	
    }
    
}
