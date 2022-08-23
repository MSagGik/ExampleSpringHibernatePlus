package ru.msaggik.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.msaggik.hibernate.models.Person;

import java.util.List;

@Component
public class PeopleDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public PeopleDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // возврат всех пользователей из БД
    @Transactional(readOnly = true) // данная аннотация открывает транзакцию внутри помеченного метода,
    // (readOnly = true) - означает, что разрешенно только чтение данных
    public List<Person> index() {
        // открытие сессии
        Session session = sessionFactory.getCurrentSession();
        // получение из БД информации всех пользователей (вручную)
        return session.createQuery("SELECT p FROM Person p", Person.class)
                .getResultList();
    }
    // показ данных выбранного пользователя
    @Transactional(readOnly = true)
    public Person show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, id);
    }
    // сохраненипе данных пользователя
    @Transactional
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }
    // обновление данных пользователя
    @Transactional
    public void update(int id, Person updatedPerson) {
        Session session = sessionFactory.getCurrentSession();
        Person personToBeUpdated = session.get(Person.class, id);
        // назначение параметров
        personToBeUpdated.setName(updatedPerson.getName());
        personToBeUpdated.setAge(updatedPerson.getAge());
        personToBeUpdated.setEmail(updatedPerson.getEmail());
    }
    // удаление данных пользователя
    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Person.class, id));
    }
}
