package com.hospital.management;

import com.model.Hospital;
import com.model.HospitalBedInfo;
import com.model.Patient;
import com.mysql.cj.protocol.Resultset;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.random.RandomGenerator;

public class HospitalSystem {

    public static void main(String[] args) throws IOException {
        String url =null;
        String userName =null;
        String password = null;
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        Scanner scanner = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties properties = new Properties();

            FileReader fileReader = new FileReader("C:\\Zorba_Assignment_submission\\JDBCAssignment\\src\\main\\resources\\application.properties");

            BufferedReader bufferReader = new BufferedReader(fileReader);

            properties.load(bufferReader);

            url = (String) properties.getProperty("url");
            userName = (String) properties.getProperty("userName");
            password = (String) properties.getProperty("password");


            connection = DriverManager.getConnection(url, userName, password);
            System.out.println("Connection is successfull");

            // drop tables
            preparedStatement = connection.prepareStatement("drop table if exists Hospital_Bed_Info");
            preparedStatement.executeUpdate();
            System.out.println("Dropped table Hospital_Bed_Info");

            statement = connection.createStatement();

            String hospitalTableCreated = "create table if not exists Hospital(hospital_id int primary key , hospital_name varchar(50), patient_id int)";
            statement.execute(hospitalTableCreated);
            System.out.println("hospital table created");

            String patientTableCreated = "create table if not exists Patient(patient_id int primary key , patient_name varchar(20), patient_type varchar(20),no_of_days int,total_bed_charges double(10,2))";
            statement.execute(patientTableCreated);
            System.out.println("patient table created");


            String alterHospitalTable = "alter table Hospital add foreign key (patient_id) references Patient(patient_id)";
            statement.execute(alterHospitalTable);
            System.out.println("hospital table altered");


            String hospitalInfoTableCreated = "create table if not exists Hospital_Bed_Info(bed_id varchar (20) primary key , bed_type varchar(20), bed_charges_rate double(10,2), hospital_id int)";
            System.out.println("Hospital_Bed_Info table created");
            statement.execute(hospitalInfoTableCreated);


            String alterHospital_bed_info = "alter table Hospital_Bed_Info add foreign key (hospital_id) references Hospital(hospital_id)";
            statement.execute(alterHospital_bed_info);
            System.out.println("hospital_bed_info table altered");

            scanner = new Scanner(System.in);

            // insert into patient without total charges
            preparedStatement = connection.prepareStatement("insert into Patient (patient_id, patient_name, patient_type,no_of_days) values(?, ?, ?, ?)");
            System.out.println("Enter patientId: ");
            int patientId = scanner.nextInt();
            System.out.println("Enter patientName: ");
            String patientName = scanner.next();
            System.out.println("Enter patientType: ");
            String patientType = scanner.next();
            System.out.println("Enter noOfDays: ");
            int noOfDays = scanner.nextInt();

            Patient newPatient = new Patient(patientId, patientName, patientType, noOfDays);
            preparedStatement.setInt(1, newPatient.getPatientId());
            preparedStatement.setString(2, newPatient.getPatientName());
            preparedStatement.setString(3, newPatient.getPatientType());
            preparedStatement.setInt(4, newPatient.getNoOfDays());
            preparedStatement.executeUpdate();

            System.out.println("Added patient data without total charges");

            preparedStatement = connection.prepareStatement("insert into  Hospital values(?,?,?)");
           // int randomHospitalId = RandomGenerator.getDefault().nextInt(1000); // to allow multiple patient entries

            System.out.println("Enter hospitalId: ");
            int hospitalId = scanner.nextInt();
            System.out.println("Enter hospitalName: ");
            String hospitalName = scanner.next();
            Hospital hospital = new Hospital(hospitalId, hospitalName);
            preparedStatement.setInt(1, hospital.getHospitalId());
            preparedStatement.setString(2, hospital.getHospitalName());
            preparedStatement.setInt(3, newPatient.getPatientId());
            preparedStatement.executeUpdate();

            System.out.println("hospital table inserted successfully ");



            preparedStatement = connection.prepareStatement("insert into  Hospital_Bed_Info values(?,?,?,?)");

            HospitalBedInfo hospitalBedInfo1 = new HospitalBedInfo("BE01", "Emergency", 50.00, hospital.getHospitalId());

            preparedStatement.setString(1, hospitalBedInfo1.getBedId());
            preparedStatement.setString(2, hospitalBedInfo1.getBedType());
            preparedStatement.setDouble(3, hospitalBedInfo1.getBedChargesRate());
            preparedStatement.setInt(4, hospitalBedInfo1.getHospitalId());

            preparedStatement.executeUpdate();

            System.out.println("Added Emergency bed type into  hospital bed info ");

            preparedStatement = connection.prepareStatement("insert into  Hospital_Bed_Info values(?,?,?,?)");
            HospitalBedInfo hospitalBedInfo2 = new HospitalBedInfo("BN01", "Regular", 30.00, hospital.getHospitalId());
            preparedStatement.setString(1, hospitalBedInfo2.getBedId());


            preparedStatement.setString(2, hospitalBedInfo2.getBedType());
            preparedStatement.setDouble(3, hospitalBedInfo2.getBedChargesRate());
            preparedStatement.setInt(4, hospitalBedInfo2.getHospitalId());

            preparedStatement.executeUpdate();

            System.out.println("Added Normal bed type into  bed info table");


            preparedStatement = connection.prepareStatement("select bed_charges_rate from Hospital_Bed_Info where bed_id=? and bed_type=?");

            if(newPatient.getPatientType().equals("Critical")){
                preparedStatement.setString(1, hospitalBedInfo1.getBedId());
                preparedStatement.setString(2, hospitalBedInfo1.getBedType());
            } else { // Default to Normal
                preparedStatement.setString(1, hospitalBedInfo2.getBedId());
                preparedStatement.setString(2, hospitalBedInfo2.getBedType());
            }
            ResultSet rs = preparedStatement.executeQuery();
            double bedChargeRate = 0.0;
            while (rs.next()){
                bedChargeRate = rs.getDouble("bed_charges_rate");
            }
            rs.close();
            // Update Patient with total charges now
            preparedStatement = connection.prepareStatement("update patient set total_bed_charges=? where patient_id=?");
            preparedStatement.setDouble(1, bedChargeRate* newPatient.getNoOfDays());
            preparedStatement.setInt(2, newPatient.getPatientId());
            preparedStatement.executeUpdate();
            System.out.println("Updated Patient table with total charges as " + bedChargeRate * newPatient.getNoOfDays());



            preparedStatement = connection.prepareStatement("select H.hospital_name as hospitalName,  P.patient_name as patientName, P.patient_type as patientType, B.bed_type as bedType,  P.no_of_days as noOfDays, P.total_bed_charges as totalBedCharges \n" +
                    " from patient as P \n" +
                    " inner join \n" +
                    " hospital H\n" +
                    " on \n" +
                    " P.patient_id=H.patient_id\n" +
                    " inner join \n" +
                    " hospital_bed_info B\n" +
                    " on H.hospital_id = B.hospital_id");



             rs =preparedStatement.executeQuery();
             hospitalInfoDetailsExcelFile(rs, "C:\\Zorba_Assignment_submission\\JDBCAssignment\\src\\main\\resources\\HospitalDetails.xlsx");

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

    }


    public static void hospitalInfoDetailsExcelFile(ResultSet rs, String fileName) throws SQLException, IOException {


        ResultSetMetaData rsmd = rs.getMetaData();


        List<String> columns = new ArrayList<>();

        for (int i = 1; i <= rsmd.getColumnCount(); i++) {


            columns.add(rsmd.getColumnLabel(i));

        }
        //create sheet from row columns

        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet();
            Row row = sheet.createRow(0);

            for (int i = 0; i < columns.size(); i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(columns.get(i));

                //create other rows below header, create cell and insert values
            }

            int rowIndex = 0;

            while (rs.next()) {

                Row rows = sheet.createRow(++rowIndex);

                for (int i = 0; i < columns.size(); i++) {

                    Cell cell = rows.createCell(i);

                    String values = Objects.toString(rs.getObject(columns.get(i)), "");

                    cell.setCellValue(values.toString());
                }


            }
            try (FileOutputStream fos = new FileOutputStream(fileName) ){
                workbook.write(fos);
                System.out.println("file inseretd: " + fileName);

            }
            catch(IOException e ){
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            rs.close();
        }


    }

}


