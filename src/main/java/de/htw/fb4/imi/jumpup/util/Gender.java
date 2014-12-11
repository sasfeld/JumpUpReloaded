package de.htw.fb4.imi.jumpup.util;

import java.util.HashMap;
import java.util.Map;

public enum Gender {

	MAN, WOMAN, LADYBOY;
	
	protected Map<String, Gender> storedMap;
	
	/**
     * Get all genders
     * 
     * Should be used by JSF selectitems primarly.
     * 
     * @return {@link Map} of label - gender elements.
     */
    public Map<String, Gender> getAllGenders()
    {
        if (null == this.storedMap) {
            this.fillGenders();
        }
        
        return storedMap;
    }
    
    private void fillGenders()
    {
        this.storedMap = new HashMap<String, Gender>();
        
        // return a map of english label as key and the enumeration value itself as value
        this.storedMap.put("Man", MAN);
        this.storedMap.put("Woman", WOMAN);        
        this.storedMap.put("Ladyboy", LADYBOY);        
    }
}
