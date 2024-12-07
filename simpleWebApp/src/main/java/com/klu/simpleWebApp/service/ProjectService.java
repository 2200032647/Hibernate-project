package com.klu.simpleWebApp.service;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;

import java.util.List;

public class ProjectService {
    private SessionFactory factory;

    public ProjectService() {
        factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Project.class).buildSessionFactory();
    }

    public void insertProject(Project project) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(project);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void performAggregateFunctions() {
        Session session = factory.openSession();
        try {
            // Count
            Criteria criteria = session.createCriteria(Project.class);
            criteria.setProjection(Projections.rowCount());
            System.out.println("Total Projects: " + criteria.uniqueResult());

            // Max
            criteria.setProjection(Projections.max("budget"));
            System.out.println("Maximum Budget: " + criteria.uniqueResult());

            // Min
            criteria.setProjection(Projections.min("budget"));
            System.out.println("Minimum Budget: " + criteria.uniqueResult());

            // Sum
            criteria.setProjection(Projections.sum("budget"));
            System.out.println("Total Budget: " + criteria.uniqueResult());

            // Average
            criteria.setProjection(Projections.avg("budget"));
            System.out.println("Average Budget: " + criteria.uniqueResult());
        } finally {
            session.close();
        }
    }

    public void close() {
        factory.close();
    }
}