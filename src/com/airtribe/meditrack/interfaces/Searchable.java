package com.airtribe.meditrack.interfaces;

import java.util.List;

/**
 * Interface for searchable entities in the system.
 * Demonstrates polymorphism - different classes implement search differently.
 */
public interface Searchable {
    
    boolean searchById(String id);
    
    boolean searchByName(String name);
    
    List<?> search(String criteria);
    
    String[] getSearchableFields();
}
