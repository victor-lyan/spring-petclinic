package org.springframework.samples.petclinic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExampleTest {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void testSomething() {
        LocalDate birthDate = LocalDate.parse("2018-01-01");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        System.out.println(birthDate.format(formatter));
        assertTrue(true);
    }
}
