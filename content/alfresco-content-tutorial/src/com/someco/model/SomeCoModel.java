package com.someco.model;

public interface SomeCoModel {
    
	// Types
	public static final String NAMESPACE_SOMECO_CONTENT_MODEL  = "http://www.someco.com/model/content/1.0";
    public static final String TYPE_SC_DOC = "doc";
    public static final String TYPE_SC_WHITEPAPER = "whitepaper";
    
    // Aspects
    public static final String ASPECT_SC_WEBABLE = "webable";
    public static final String ASPECT_SC_PRODUCT_RELATED = "productRelated";
    
    // Properties
    public static final String PROP_PRODUCT = "product";
    public static final String PROP_VERSION = "version";
    public static final String PROP_PUBLISHED = "published";
    public static final String PROP_IS_ACTIVE = "isActive";
    
    // Associations
    public static final String ASSN_RELATED_DOCUMENTS = "relatedDocuments";
}
