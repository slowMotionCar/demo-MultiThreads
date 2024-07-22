package com.example.demomultithreads;

import com.example.demomultithreads.demos.web.AOPService;
import lombok.Getter;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: AOPTest
 * @Description: TODO
 * @Author: zzl
 * @Date: 2024/7/12 14:27
 * @Version: 1.0
 */

@SpringBootTest
public class AOPTest {

    @Resource
    AOPService aopService;


    @Test
    public void test() throws InterruptedException {
        String s = aopService.AOPHellotest();
        System.out.println(s);
    }


    @Test
    public void test2(){
        List<Student> basketballStudents = Lists.newArrayList(
                new Student("Bob", 18),
                new Student("Ted", 17),
                new Student("Zeka", 19),
                new Student("Tom", 19),
                new Student("方琪",19),
                new Student("吴羽霏", 19));

        //findFirst
        basketballStudents.stream()
                .filter(s -> s.getAge() == 19)
                .findFirst()
                .map(Student::getName)
                .ifPresent(name -> System.out.println("findFirst: " + name));


        //findAny
        for (int i = 0; i < 10; i++) {
            basketballStudents.parallelStream()
                    .filter(s -> s.getAge() == 19)
                    .findAny()
                    .map(Student::getName)
                    .ifPresent(name -> System.out.println("findAny: " + name));
        }

        //Collectors.maxBy()
        basketballStudents.stream()
//                .map(Student::getAge)
                .max(Comparator.comparingInt(a -> a.getAge()))
                .ifPresent(a -> System.out.println("The maximum age for a football team is " + a.getName()+a.getAge()));

    }


    @Test
    public void test3(){

            List<Student2> computerStudents = Arrays.asList(
                    new Student2("Alice", "IT"),
                    new Student2("Bob", "IT"),
                    new Student2("Carol", "Engineering"),
                    new Student2("David", "IT"),
                    new Student2("Eve", "Engineering")
            );

            // 创建一个空的 resultMap
            Map<String, List<Student2>> resultMap = new HashMap<>();

            // 遍历每个学生，按部门分组
            for (Student2 student : computerStudents) {
                // computeIfAbsent方法来处理分组逻辑
                List<Student2> groupList = resultMap.computeIfAbsent(student.getDepartment(), s -> new ArrayList<>());
                groupList.add(student);
            }

            // 打印结果
            for (Map.Entry<String, List<Student2>> entry : resultMap.entrySet()) {
                System.out.println("Department: " + entry.getKey());
                for (Student2 student : entry.getValue()) {
                    System.out.println("- " + student.getName());
                }
                System.out.println();
            }

    }

}

class Student{
    private String name;
    @Getter
    private Integer age;

    public Student(String name, Integer age){
        this.age = age;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Student2 {
    private String name;
    private String department;

    public Student2(String name, String department) {
        this.name = name;
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public String getName() {
        return name;
    }
}
