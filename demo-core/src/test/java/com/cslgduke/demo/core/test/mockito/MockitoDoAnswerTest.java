package com.cslgduke.demo.core.test.mockito;

import com.cslgduke.demo.core.test.mockito.fake.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.spy;

/**
 * @author i565244
 */
@ExtendWith(MockitoExtension.class)
public class MockitoDoAnswerTest {

    @Test
    public void testFakeObject() {
        //我们要测试Student的doAnswerHighSchoolStudent方法，但是该方法调用了（依赖）PersonPrinter的printPerson方法
        // 所以我们对printPerson进行mock
        Student student = new Student();
        //mock 对象
        PersonPrinter personPrinter = new FakePersonPrinter();
        //注入fake object
        student.setPersonPrinter(personPrinter);
        //调用要测试的方法
        student.doAnswerHighSchoolStudent();
        //从fake对象里面取出中间变量
        HighSchoolStudent highSchoolStudent = ((FakePersonPrinter) personPrinter).getHighSchoolStudent();

        assertThat(highSchoolStudent.getId()).isEqualTo(123456789);
        assertThat(highSchoolStudent.getAge()).isEqualTo(10);
        assertThat(highSchoolStudent.getName()).isEqualTo("highSchoolStudent");
        assertThat(highSchoolStudent.getGrade()).isEqualTo(3);
    }

    @Test
    public void testInterfaceDoAnswer() {
        //mock一下PersonPrinter
        PersonPrinter personPrinter = spy(PersonPrinter.class);
        PersonPrinterAnswer personPrinterAnswer = new PersonPrinterAnswer();
        //使用我们定义的personPrinterAnswer
        doAnswer(personPrinterAnswer).when(personPrinter).printPerson(any());
        Student student = new Student();
        //把我们mock的personPrinter注入student
        student.setPersonPrinter(personPrinter);
        //要测试的方法
        student.doAnswerHighSchoolStudent();
        //取出我们存在answer中的中间变量
        HighSchoolStudent highSchoolStudent = (HighSchoolStudent) personPrinterAnswer.getPerson();
        assertThat(highSchoolStudent.getId()).isEqualTo(123456789);
        assertThat(highSchoolStudent.getAge()).isEqualTo(10);
        assertThat(highSchoolStudent.getName()).isEqualTo("highSchoolStudent");
        assertThat(highSchoolStudent.getGrade()).isEqualTo(3);

    }


    public class PersonPrinterAnswer implements Answer<Object> {

        private Person person;
        @Override
        public Person answer(InvocationOnMock invocationOnMock) {

            // invocationOnMock.
            System.out.println(invocationOnMock.getMethod().getName());
            //调用 printPerson时传入的参数
            Object[] args = invocationOnMock.getArguments();
            person = (Person) args[0];
            return person;
        }

        public Person getPerson() {
            return person;
        }
    }

}
