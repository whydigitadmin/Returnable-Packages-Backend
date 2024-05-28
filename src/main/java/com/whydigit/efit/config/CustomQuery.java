package com.whydigit.efit.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class CustomQuery {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initialize() {
        try {
            executeQueries();
        } catch (Exception e) {
            throw new RuntimeException("Error initializing database", e);
        }
    }

    private void executeQueries() {
	
	
	jdbcTemplate.execute("CREATE or replace\r\n"
			+ "VIEW `avalkitqty` AS\r\n"
			+ "    SELECT \r\n"
			+ "        `a`.`stockbranch` AS `stockbranch`,\r\n"
			+ "        `a`.`kitcode` AS `kitcode`,\r\n"
			+ "        (CASE\r\n"
			+ "            WHEN (FLOOR(MIN(`a`.`avalqty`)) IS NULL) THEN 0\r\n"
			+ "            ELSE FLOOR(MIN(`a`.`avalqty`))\r\n"
			+ "        END) AS `avalqty`\r\n"
			+ "    FROM\r\n"
			+ "        (SELECT \r\n"
			+ "            `c`.`stockbranch` AS `stockbranch`,\r\n"
			+ "                `a`.`kitno` AS `kitcode`,\r\n"
			+ "                `b`.`asset` AS `asset`,\r\n"
			+ "                `b`.`quantity` AS `quantity`,\r\n"
			+ "                SUM(`c`.`skuqty`) AS `SUM(skuqty)`,\r\n"
			+ "                (SUM(`c`.`skuqty`) / `b`.`quantity`) AS `avalqty`\r\n"
			+ "        FROM\r\n"
			+ "            ((`kit` `a`\r\n"
			+ "        JOIN `kit2` `b`)\r\n"
			+ "        JOIN `stockdetails` `c` ON (((`a`.`kitid` = `b`.`kitid`)\r\n"
			+ "            AND (`b`.`asset` = `c`.`sku`)\r\n"
			+ "            AND (`c`.`status` = 'S'))))\r\n"
			+ "        GROUP BY `a`.`kitno` , `c`.`stockbranch` , `b`.`asset` , `b`.`quantity`) `a`\r\n"
			+ "    GROUP BY `a`.`kitcode` , `a`.`stockbranch`");

    // Add your new SQL query
    jdbcTemplate.execute("CREATE OR REPLACE VIEW rp_dev.availableasset AS SELECT stockdetails.stockbranch AS stockbranch, stockdetails.sku AS sku, stockdetails.skucode AS skucode, SUM(stockdetails.skuqty) AS `sum(skuqty)`, stockdetails.orgid AS orgid, category FROM stockdetails WHERE stockdetails.status = 'S' GROUP BY stockdetails.stockbranch, stockdetails.sku, stockdetails.skucode, category, stockdetails.orgid HAVING SUM(stockdetails.skuqty) > 0");
    }
}

