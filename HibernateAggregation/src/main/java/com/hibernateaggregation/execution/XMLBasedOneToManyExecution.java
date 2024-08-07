package com.hibernateaggregation.execution;


import com.hibernateaggregation.entity.Student;
import com.hibernateaggregation.entity.Subject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class XMLBasedOneToManyExecution {

    public static void main(String[] args) {

        Configuration configuration = new Configuration();

        configuration.configure("hibernate-mapping/hibernate.cfg.xml");

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        Session session =sessionFactory.openSession();

        Transaction tx =null;

        Student student = new Student();
        Scanner sc = new Scanner(System.in);

        System.out.println("How many student you want to enter: ");
        int noOfStudEnter = sc.nextInt();
        for(int i=0;i<noOfStudEnter;i++){

            System.out.println("Enter the student name:");
            String studName =sc.next();
            student.setStudName(studName);
            System.out.println("Enter student RollNumber:");
            int studRollNumber = sc.nextInt();
            student.setStudRollNumber(studRollNumber);
        }

        System.out.println("How many subject you want to enter: ");
        int noOfStudEnter1 = sc.nextInt();

        for(int i=0;i<noOfStudEnter1;i++) {
            System.out.println("Enter the subject: ");
            String sunName = sc.next();
            Subject subject1 = new Subject();
            subject1.setSubName(sunName);
            subject1.setStudent(student);

            System.out.println("Enter the next subject: ");
            String sunName1 = sc.next();
            Subject subject2 = new Subject();
            subject2.setSubName(sunName1);
            subject2.setStudent(student);

            Set<Subject> subjects = new HashSet<>();
            subjects.add(subject1);
            subjects.add(subject2);
            student.setSubject(subjects);
        }
        try{
            tx = session.beginTransaction();
            session.persist(student);
            tx.commit();
        }
        catch(Exception e){
            e.printStackTrace();;
            tx.rollback();
        }
        finally{
            if(session != null){
                session.close();
            }
        }

     Session session1 = sessionFactory.openSession();

        try {
            tx = session1.beginTransaction();
            Student studentRetrived = session1.get(Student.class, 1);

            System.out.println(studentRetrived);
            Set<Subject> subject = student.getSubject();
        }
          catch(Exception e){
            e.printStackTrace();;
            tx.rollback();
        }
        finally{
            if(session != null){
                session.close();
            }
        }



    }


}
