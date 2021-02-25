package nobel.springsecurity.demo.controllers;


import nobel.springsecurity.demo.entities.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/students")
public class StudentController {

    Set<Student> set = new HashSet<>(
            Arrays.asList(new Student(1l, "Ahmed"),
                          new Student(2l, "Nabil"),
                          new Student(3l, "Lotfi"))
    );

    @GetMapping("/{id}")
    private Student findById(@PathVariable Long id) {
        return set.stream().filter(student -> student.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
