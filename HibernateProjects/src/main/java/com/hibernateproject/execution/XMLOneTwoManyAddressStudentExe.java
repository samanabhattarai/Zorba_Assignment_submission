package com.hibernateproject.execution;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import com.hibernateproject.entity.Address;
import com.hibernateproject.entity.Students;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class XMLOneTwoManyAddressStudentExe {

    public static void main(String[] args) {

        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");


        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();

        Transaction tx=null;
  /*      private String street, city, state;
private Long zipcode;*/



        Address address = new Address();

        Scanner sc = new Scanner(System.in);

        System.out.println("How many address you want to enter");
        int enterAddress = sc.nextInt();

        for (int i = 0; i < enterAddress; i++) {

            System.out.println("Enter Street name: ");
            String studStreet = sc.next();
            address.setStreet(studStreet);



            System.out.println("Enter city name: ");
            String studCity = sc.next();
            address.setStreet(studCity);



            System.out.println("Enter State name: ");
            String studState = sc.next();
            address.setStreet(studState);

            System.out.println("Enter zipcode: ");
            Long zipcode = sc.nextLong();
            address.setZipcode(zipcode);
        }

        System.out.println("How many students you want to enter?");
        int numberOfStudent = sc.nextInt();

        for (int i = 0; i < numberOfStudent; i++) {
            Students students1 = new Students();
            System.out.println("Enter Student name: ");
            String studName = sc.next();
            students1.setStudName(studName);
            System.out.println("Enter Student rollNUmber: ");
            int studRollNumber = sc.nextInt();
            students1.setStudRollNumber(studRollNumber);
            students1.setAddresses(address);

            Students students2 = new Students();
            System.out.println("Enter Student name: ");
            String studName1 = sc.next();
            students2.setStudName(studName1);
            System.out.println("Enter Student rollNUmber: ");
            int studRollNumber1 = sc.nextInt();
            students2.setStudRollNumber(studRollNumber1);
            students2.setAddresses(address);

            Students students3 = new Students();
            System.out.println("Enter Student name: ");
            String studName2 = sc.next();
            students3.setStudName(studName2);
            System.out.println("Enter Student rollNUmber: ");
            int studRollNumber2 = sc.nextInt();
            students3.setStudRollNumber(studRollNumber2);
            students3.setAddresses(address);

            Set<Students> studentsSet = new HashSet<>();
            studentsSet.add(students1);
            studentsSet.add(students2);
            studentsSet.add(students3);

            address.setStudents(studentsSet);
        }

        try{
            tx =session.beginTransaction();
            session.persist(address);
            tx.commit();
        }
        catch(Exception e){
            e.printStackTrace();
            tx.rollback();
        }
        finally{
            if(session != null){
                session.close();
            }
        }

        Session session2 = sessionFactory.openSession();
        Address addressRetrived = new Address();

        try{
            addressRetrived = session2.get(Address.class,1);
            System.out.println(addressRetrived);
            Set<Students> students = address.getStudents();
            System.out.println(students);

        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            if(session2 != null){
                session2.close();
            }
        }
        Session session3 = sessionFactory.openSession();
        Transaction tx1 = null;
        try {
            tx1 = session3.beginTransaction();
            session3.delete(addressRetrived);
            tx1.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            if (session3 != null) {
                session3.close();
            }
        }
    }
}