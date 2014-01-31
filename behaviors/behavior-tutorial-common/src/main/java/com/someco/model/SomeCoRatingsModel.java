package com.someco.model;

public interface SomeCoRatingsModel {

    // Namespaces
    public static final String NAMESPACE_SOMECO_RATINGS_CONTENT_MODEL  = "http://www.someco.com/model/ratings/1.0";

    // Types
    public static final String TYPE_SCR_RATING = "rating";
    
    // Aspects
    public static final String ASPECT_SCR_RATEABLE = "rateable";
    
    // Properties
    public static final String PROP_RATING = "rating";
    public static final String PROP_RATER = "rater";
    public static final String PROP_AVERAGE_RATING= "averageRating";
    public static final String PROP_TOTAL_RATING= "totalRating";
    public static final String PROP_RATING_COUNT= "ratingCount";
    
    // Associations
    public static final String ASSN_SCR_RATINGS = "ratings";
}
