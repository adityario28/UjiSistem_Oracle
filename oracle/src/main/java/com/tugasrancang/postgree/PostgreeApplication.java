package com.tugasrancang.postgree;

import static com.tugasrancang.postgree.CreateTable.CreateAllTable;
import static com.tugasrancang.postgree.InsertTable.InsertAllTable;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PostgreeApplication implements CommandLineRunner{
//    //Postgre
//        static final String DB_URL = "jdbc:postgresql://localhost:5432/public";
//        static final String USER = "postgres";
//        static final String PASS = "agung2002";
    //Oracle
//        static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
//        static final String USER = "system";
//        static final String PASS = "Namara2012.";

//        static final String DB_URL = "jdbc:oracle:thin:@localhost:1523/orcl";
//        static final String USER = "system";
//        static final String PASS = "agung2002";
//Ryo
//        static final String DB_URL = "jdbc:oracle:thin:@localhost:1522:orcl11";
//        static final String USER = "adityario28";
//        static final String PASS = "mahesario28";
//        
	public static void main(String[] args) {
		SpringApplication.run(PostgreeApplication.class, args);
	}

        @Override
        public void run(String... args) throws Exception {
          System.out.println(CreateAllTable());
//           System.out.println(CreateAllTable());
//           System.out.println(InsertAllTable(DB_URL,USER,PASS));
            System.out.println(InsertAllTable());

        }
}
