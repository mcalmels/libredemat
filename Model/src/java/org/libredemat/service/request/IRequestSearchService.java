package org.libredemat.service.request;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.libredemat.business.request.Request;
import org.libredemat.business.request.RequestState;
import org.libredemat.exception.CvqException;
import org.libredemat.exception.CvqObjectNotFoundException;
import org.libredemat.security.annotation.IsUser;
import org.libredemat.service.request.annotation.IsRequest;
import org.libredemat.util.Critere;


public interface IRequestSearchService {

    /**
     * Get a constrained list of requests according to a set of criteria and requirements.
     *
     * @param criteriaSet a set of {@link Critere criteria} to be applied to the search
     * @param sort an ordering to apply to results. value is one of the SEARCH_* static
     *        string defined in this service (null to use default sort on requests ids)
     * @param dir the direction of the sort (asc or desc, asc by default)
     * @param recordsReturned the number of records to return (-1 to get all results)
     * @param startIndex the start index of the records to return
     * @param full get full request objects with their specific data
     */
    List<Request> get(Set<Critere> criteriaSet, final String sort, final String dir, 
        final int recordsReturned, final int startIndex, final boolean full)
        throws CvqException;

    /**
     * Get a count of requests matching the given criteria.
     */
    Long getCount(Set<Critere> criteriaSet)
        throws CvqException;
    
    /**
     * Get a request by id.
     */
    Request getById(@IsRequest final Long id, final boolean full);

    /**
     * Get all requests belonging to the given home folder.
     */
    List<Request> getByHomeFolderId(@IsUser final Long homeFolderId, final boolean full);

    /**
     * Get all requests of the given type belonging to the given home folder.
     */
    List<Request> getByHomeFolderIdAndRequestLabel(@IsUser final Long homeFolderId,
        final String requestLabel, final boolean full)
        throws CvqException, CvqObjectNotFoundException;

    /**
     * Get the generated certificate for the given request at the given step.
     */
    byte[] getCertificate(@IsRequest final Long requestId, final RequestState requestState)
        throws CvqException;

    /**
     * Get the most recent certificate for the given request.
     * 
     * it is :
     * <ul>
     *   <li>The one generated at validation time if it exists</li>
     *   <li>The one generated at creation time if it does not</li>
     * </ul>
     */
    byte[] getCertificate(@IsRequest final Long requestId)
        throws CvqException;

    File getArchives(List<String> names)
        throws IOException;

    /**
     * @return the names of the archives which could not be deleted
     */
    List<String> deleteArchives(List<String> names);

    /**
     * Return {@link Request requests} in state
     * {@link RequestState#PENDING Pending},
     * {@link RequestState#COMPLETE Complete},
     * {@link RequestState#UNCOMPLETE Uncomplete}
     * or {@link RequestState#RECTIFIED Rectified}.
     *
     * @param qoS Quality of service to filter by. Value is one of the {@link Request Request}.QUALITY_TYPE_*
     * @param sortBy one of the {@link Request Request}.SEARCH_BY_*
     *
     * @throws CvqException
     */
    List<Request> listTasks(String qoS, String sortBy, final String dir, final int recordsReturned, final int startIndex)
            throws CvqException;

    Long countTasks(String qoS) throws CvqException;

    List<Request> find(String query, Object... params);

    List<Request> find(final Boolean full, String query, Object... params);
}
