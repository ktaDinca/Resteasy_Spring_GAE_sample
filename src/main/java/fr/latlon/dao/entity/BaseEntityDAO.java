package fr.latlon.dao.entity;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

/**
 * @author Catalin Dinca (alexandru.dinca2110@gmail.com)
 * @since 17/Jan/2015
 */
public abstract class BaseEntityDAO {

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

}
