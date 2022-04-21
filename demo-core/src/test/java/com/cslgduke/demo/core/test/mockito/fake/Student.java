package com.cslgduke.demo.core.test.mockito.fake;

/**
 * @author i565244
 */
public class Student {
    private PersonPrinter personPrinter;

    public void doAnswerHighSchoolStudent(){
        HighSchoolStudent highSchoolStudent = new HighSchoolStudent();
        highSchoolStudent.setName("highSchoolStudent");
        highSchoolStudent.setAge(10);
        highSchoolStudent.setId(123456789);
        //verify highSchoolStudent
        personPrinter.printPerson(highSchoolStudent);
        highSchoolStudent.setGrade(3);
    }

    public void setPersonPrinter(PersonPrinter personPrinter) {
        this.personPrinter = personPrinter;
    }
}
