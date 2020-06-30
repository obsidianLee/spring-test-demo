package com.test.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Lixueyuan
 * @date 2019/9/14 1:28
 */
@Service
public class Demo2 implements SpringDemo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Transactional
    public void insertData() {
        jdbcTemplate.update("update class_one set student_no = '12',name = 'ruby' where id = ?",20);
    }

    public static void main(String[] args) {
//        List<Map<String, Object>> masterData = new ArrayList<>();
//
//        for (int j = 0; j < 3; j++) {
//
//            Map<String, Object> assetTypeMap = new HashMap<>();
//            assetTypeMap.put("AccountNo", j+1);
//            assetTypeMap.put("AssetCode", "assetAcct.getAssetCode");
//            assetTypeMap.put("ModifyDate", new Date());
//            assetTypeMap.put("Status", "1");
//
//            masterData.add(assetTypeMap);
//        }
//        String masterJson = JSON.toJSONString(masterData);
//        System.out.println(masterJson);
//        List list = new ArrayList();
//        list.add(masterJson);
//        System.out.println(JSON.toJSONString(list));
        String s = "add";
        Thread t = new Thread(() -> {
            String a = "abbort";
            System.out.println(s);

        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TT tt = new TT();
        tt.start();
        try {
            tt.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(tt.a);
    }


}

class TT extends Thread {
    public String a;

    @Override
    public void run() {
        a = "other Thread";
        System.out.println(a);
    }
}