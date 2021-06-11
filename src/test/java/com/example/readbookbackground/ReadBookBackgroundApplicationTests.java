package com.example.readbookbackground;

import com.example.readbookbackground.util.crontab.SyncBookData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReadBookBackgroundApplicationTests {

    @Test
    void contextLoads() {
        SyncBookData syncBookData=new SyncBookData();
        syncBookData.syncData();
    }

}
