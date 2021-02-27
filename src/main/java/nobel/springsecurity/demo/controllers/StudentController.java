package nobel.springsecurity.demo.controllers;


import nobel.springsecurity.demo.entities.Student;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    Set<Student> set = new HashSet<>(
            Arrays.asList(new Student(1l, "Ahmed"),
                          new Student(2l, "Nabil"),
                          new Student(3l, "Lotfi"))
    );

    @GetMapping
    private Set<Student> getAll() {
        return set;
    }

    @GetMapping("/{id}")
    private Student findById(@PathVariable Long id) {
        return set.stream().filter(student -> student.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    private Student saveStudent(@RequestBody Student student) {
        long nextId = (set.stream().max(Comparator.comparing(s -> s.getId())).get().getId() + 1);
        student.setId(nextId);
        set.add(student);
        return student;
    }

    @PutMapping("/{id}")
    private Student updateStudent(@PathVariable Long id, @RequestBody Student student) {
        Student foundStudent = set.stream().filter(s -> s.getId() == id).findFirst().orElseThrow(NoSuchElementException::new);
        foundStudent.setUserName(student.getUserName());
        return foundStudent;
    }

    @DeleteMapping("/{id}")
    private void deleteStudent(@PathVariable Long id) {
        Student studentToDelete = set.stream().filter(s -> s.getId() == id).findFirst().get();
        if(studentToDelete != null)
            set.remove(studentToDelete);
    }
}
