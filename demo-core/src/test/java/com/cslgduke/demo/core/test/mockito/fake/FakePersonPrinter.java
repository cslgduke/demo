package com.cslgduke.demo.core.test.mockito.fake;

/**
 * @author i565244
 */
public class FakePersonPrinter implements PersonPrinter{
    private HighSchoolStudent highSchoolStudent;

    @Override
    public void printPerson(HighSchoolStudent highSchoolStudent) {
        this.highSchoolStudent = highSchoolStudent;
    }


    public HighSchoolStudent getHighSchoolStudent() {
        return highSchoolStudent;
    }

}
