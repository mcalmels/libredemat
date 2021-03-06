package org.libredemat.dao.request.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.type.Type;
import org.libredemat.business.request.RequestNote;
import org.libredemat.business.request.RequestNoteType;
import org.libredemat.dao.hibernate.HibernateUtil;
import org.libredemat.dao.jpa.JpaTemplate;
import org.libredemat.dao.request.IRequestNoteDAO;
import org.libredemat.business.request.RequestAction;
import org.libredemat.dao.jpa.JpaUtil;


/**
 * Implementation of the {@link IRequestNoteDAO} interface.
 * 
 * @author bor@zenexity.fr
 */
public class RequestNoteDAO extends JpaTemplate<RequestNote, Long> implements IRequestNoteDAO {

    public List<RequestNote> listByRequestAndType(final Long requestId, RequestNoteType type) {

        StringBuffer sb = new StringBuffer();
        sb.append("from RequestNote as requestNote");

        List<Type> typeList = new ArrayList<Type>();
        List<Object> objectList = new ArrayList<Object>();

        sb.append(" where request_id = ?");
        objectList.add(requestId);
        typeList.add(Hibernate.LONG);

        sb.append(" and parent_id is null");

        if (type != null) {
            sb.append(" and type = ?");
            objectList.add(type.toString());
            typeList.add(Hibernate.STRING);
        }

        sb.append(" order by date asc");

        Type[] typeTab = typeList.toArray(new Type[0]);
        Object[] objectTab = objectList.toArray(new Object[0]);

        return HibernateUtil.getSession()
            .createQuery(sb.toString())
            .setParameters(objectTab, typeTab).list();
    }

    public RequestNote getNote(final Long requestId, Long requestNoteId) {
        return (RequestNote) JpaUtil.getEntityManager()
               .createQuery("select requestNote from RequestNote as requestNote where " + "request_id = :requestId and id = :id")
               .setParameter("requestId", requestId)
               .setParameter("id", requestNoteId)
               .getSingleResult();
        }
}
