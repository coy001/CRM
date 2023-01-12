package com.congyuan.crm.dao;

import com.congyuan.crm.entity.Employee;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

// Hibernate: ORM
// ORM: Object Relation Mapping -> Java language <-> SQL language

/*
    Http request
    -> RequestHandler (Controller function)
        * request validation
        * call different service gather / process data
    -> Service (microservice)
        * Business logic
        * call DAO or other microservices
    -> DAO (Data Accessor Lay / Object)
        * DB insert / delete / modify
*/
@Repository
public class EmployeeDAOHibernateImpl implements EmployeeDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Employee> findAll() {
        Session session = entityManager.unwrap(Session.class);

        Query<Employee> query = session.createQuery("from Employee", Employee.class);

        return query.getResultList();
    }

    @Override
    @Transactional
    public Employee findById(int id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Employee.class,id);
    }

    @Override
    @Transactional
    public void save(Employee employee) {
        Session session = entityManager.unwrap(Session.class);
        session.saveOrUpdate(employee);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        Session session = entityManager.unwrap(Session.class);
        // SQL injection
        // database  transaction
        Query query = session.createQuery("delete from Employee where id=:employeeId");
        query.setParameter("employeeId",id);

        query.executeUpdate();
    }
}
