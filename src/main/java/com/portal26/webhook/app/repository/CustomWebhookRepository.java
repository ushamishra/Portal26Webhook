package com.portal26.webhook.app.repository;

import com.portal26.webhook.app.entity.WebhookDetails;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomWebhookRepository {

    @PersistenceContext
    private EntityManager em;

    public List<WebhookDetails> findByFilterSearchCriteria(String tenant_name,
                                                           String user_id,
                                                           String domain,
                                                           Long frmDate,Long toDate) {
        // implementation below

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<WebhookDetails> cq = cb.createQuery(WebhookDetails.class);
        List<Predicate> listOfPredicate = new ArrayList<>();


        Root<WebhookDetails> webhookDetailsRoot = cq.from(WebhookDetails.class);
        if(tenant_name!= null && !tenant_name.isEmpty()) {
            Predicate tenantNamePredicate = cb.equal(webhookDetailsRoot.get("tenant_name"), tenant_name);
            listOfPredicate.add(tenantNamePredicate);
        }
        if(user_id!= null && !user_id.isEmpty()){
            Predicate userIdPredicate = cb.equal(webhookDetailsRoot.get("user_id"), user_id);
            listOfPredicate.add(userIdPredicate);
        }
        if(domain!= null && !domain.isEmpty()){
            Predicate domainPredicate = cb.like(webhookDetailsRoot.get("url"), "%"+domain+"%");
            listOfPredicate.add(domainPredicate);
        }

        if(domain!= null && !domain.isEmpty()){
            Predicate domainPredicate = cb.like(webhookDetailsRoot.get("url"), "%"+domain+"%");
            listOfPredicate.add(domainPredicate);
        }

        if(frmDate != null && toDate!= null){
            Predicate fromDatePredicate = cb.greaterThanOrEqualTo(webhookDetailsRoot.get("creationDate"),frmDate);
            Predicate toDatePredicate = cb.lessThanOrEqualTo(webhookDetailsRoot.get("creationDate"),toDate);
            Predicate dateRangePredicate = cb.and(fromDatePredicate,toDatePredicate);
            listOfPredicate.add(dateRangePredicate);
        }


        Predicate[] predicateList = new Predicate[listOfPredicate.size()];
        cq.where(listOfPredicate.toArray(predicateList));


        TypedQuery<WebhookDetails> query = em.createQuery(cq);
        return query.getResultList();
    }
}
