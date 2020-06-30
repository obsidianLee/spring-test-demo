package com.test.demo;

import com.test.component.Hotel;
import com.test.entity.ClassOne;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Lixueyuan
 * @date 2019/9/14 1:27
 */
@Controller
//@Repository
@Slf4j
public class Demo {
    @Autowired
    Hotel hotel;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    GridDoRepository gridRepository;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    CatchDemo catchDemo;
//    public Logger logger = new Slf4j();

    public void demo() {
        hotel.countting();
    }

    @Transactional
    public void conne() {
//        String s = jdbcTemplate.queryForObject("SELECT bank_name FROM t_ac_bank WHERE bank_code = '323301000019'", String.class);
//        System.out.println(s);
        jdbcTemplate.execute("DROP TABLE IF EXISTS CLASS_ONE");
        jdbcTemplate.execute("DROP TABLE IF EXISTS CLASS_TWO");
        jdbcTemplate.execute("CREATE TABLE CLASS_ONE(ID INT NOT NULL AUTO_INCREMENT,STUDENT_NO INT NULL,NAME VARCHAR(32) NULL,SEX INT NULL,PRIMARY KEY(ID)) ");
        jdbcTemplate.execute("CREATE TABLE CLASS_TWO(ID INT NOT NULL AUTO_INCREMENT,STUDENT_NO INT NULL,NAME VARCHAR(32) NULL,SEX INT NULL,PRIMARY KEY(ID)) ");
        String[] names = {"王强", "西韩", "南朝", "建国", "卜筮", "猎月", "幻影", "乌神", "", "都铎"};
        String[] names2 = {"王强", null, "南朝", "建党", "卜筮", "猎月", "", "乌神", "会期", "都铎"};
        for (int i = 0; i < 10; i++) {
            if (i == 4) {
                jdbcTemplate.update("INSERT INTO CLASS_ONE(NAME,SEX) VALUES(?,?)",  names[i], i % 2);
            }else{
                jdbcTemplate.update("INSERT INTO CLASS_ONE(STUDENT_NO,NAME,SEX) VALUES(?,?,?)", i + 1, names[i], i % 2);
            }

            if (i == 1) {
                jdbcTemplate.update("INSERT INTO CLASS_TWO(STUDENT_NO,NAME,SEX) VALUES(?,?,?)", 11, names2[i], i % 2);
            } else if (i == 2) {
                jdbcTemplate.update("INSERT INTO CLASS_TWO(NAME,SEX) VALUES(?,?)", names2[i], i % 2);
            } else {
                jdbcTemplate.update("INSERT INTO CLASS_TWO(STUDENT_NO,NAME,SEX) VALUES(?,?,?)", i + 1, names2[i], i % 2);
            }

        }
    }

//    @test
    public void placeHolder() {
        Map<String,List<Integer>> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(7);
        map.put("P",list);
        List<Integer> nos = namedParameterJdbcTemplate.queryForList("SELECT STUDENT_NO FROM CLASS_ONE WHERE ID IN (:P) ORDER BY FIELD(STUDENT_NO,:P)", map, Integer.class);
        System.out.println(nos);
    }



//    @Transactional(rollbackFor = Exception.class)
    public void testTrac() {
//        int id = 2;
//        log.info("findById:{}", id);
//        Optional<ClassOne> optionalClassOne = gridRepository.findById(id);
//        ClassOne classOne = optionalClassOne.get();
//        log.info("already get:{},{}", classOne.getStudentNo(), classOne.getName());
        long num = gridRepository.count();
        log.info("total:{}",num);
        ClassOne one = new ClassOne();
//        one.setId(12);
        one.setName("那年花开");
        one.setStudentNo(11);
        log.info("save student:id:{},student_no:{},name:{}", one.getId(), one.getStudentNo(), one.getName());
//        gridRepository.save(one);
        setStu();
//        catchDemo.setStu();
        long count = gridRepository.count();
        log.info("total:{}",count);
        setExcep(2);
        int otherId = 3;
//        Optional<ClassOne> byId = gridRepository.findById(otherId);
//        ClassOne classOne1 = byId.get();
//        log.info("another get:{},{}", classOne1.getStudentNo(), classOne1.getName());
    }

    public void setExcep(int i) {
        if (i == 2)
            throw new RuntimeException("check trac");

    }

    @Transactional(rollbackFor = Exception.class)
    private void setStu() {
        ClassOne o = new ClassOne();
        o.setStudentNo(13);
        o.setName("杨十三");
        o.setSex(1);
        log.info("save student:id:{},student_no:{},name:{}", o.getId(), o.getStudentNo(), o.getName());
//        gridRepository.save(o);
        setExcep(2);
    }

}
