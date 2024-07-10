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
    jdbcTemplate.execute("CREATE OR REPLACE VIEW availableasset AS SELECT stockdetails.stockbranch AS stockbranch, stockdetails.sku AS sku, stockdetails.skucode AS skucode, SUM(stockdetails.skuqty) AS `sum(skuqty)`, stockdetails.orgid AS orgid, category FROM stockdetails WHERE stockdetails.status = 'S' GROUP BY stockdetails.stockbranch, stockdetails.sku, stockdetails.skucode, category, stockdetails.orgid HAVING SUM(stockdetails.skuqty) > 0");
    
    jdbcTemplate.execute("CREATE or replace VIEW avalablekit AS\r\n"
    + "    \r\n"
    + "    SELECT \r\n"
    + "    a.branchcode AS stockbranch,\r\n"
    + "    a.kitcode AS kitcode,\r\n"
    + "    COALESCE(FLOOR(MIN(a.avalqty)), 0) AS avalqty,\r\n"
    + "    MAX(a.stockdate) AS stockdate\r\n"
    + "FROM\r\n"
    + "    (SELECT \r\n"
    + "        sb.branchcode AS branchcode,\r\n"
    + "        a.kitno AS kitcode,\r\n"
    + "        b.asset AS asset,\r\n"
    + "        b.quantity AS quantity,\r\n"
    + "        MAX(c.stockdate) AS stockdate,\r\n"
    + "        COALESCE(SUM(c.skuqty), 0) AS SUM_skuqty,\r\n"
    + "        CASE\r\n"
    + "            WHEN b.quantity <> 0 THEN COALESCE(SUM(c.skuqty) / b.quantity, 0)\r\n"
    + "            ELSE 0\r\n"
    + "        END AS avalqty\r\n"
    + "    FROM\r\n"
    + "        kit a\r\n"
    + "    JOIN kit2 b ON a.kitid = b.kitid\r\n"
    + "    JOIN stockbranch sb ON 1 = 1\r\n"
    + "    LEFT JOIN stockdetails c ON b.asset = c.sku AND c.status = 'S' AND c.stockbranch = sb.branchcode\r\n"
    + "    GROUP BY sb.branchcode, a.kitno, b.asset, b.quantity) a\r\n"
    + "GROUP BY a.kitcode, a.branchcode");
    
    jdbcTemplate.execute("CREATE \r\n"
    + "    or replace \r\n"
    + "VIEW availablekitemitter AS\r\n"
    + "    SELECT \r\n"
    + "        a.emitterid AS emitterid,\r\n"
    + "        a.kitno AS kitno,\r\n"
    + "        (a.kitqty - COALESCE(b.kitqty, 0)) AS availkitqty,\r\n"
    + "        a.flowid AS flowid,\r\n"
    + "        a.orgid AS orgid\r\n"
    + "    FROM\r\n"
    + "        (SELECT \r\n"
    + "            b.emitterid AS emitterid,\r\n"
    + "            b.kitcode AS kitno,\r\n"
    + "            SUM(b.allotedqty) AS kitqty,\r\n"
    + "            (SELECT \r\n"
    + "                a.flowid\r\n"
    + "             FROM\r\n"
    + "                flow a\r\n"
    + "             WHERE\r\n"
    + "                a.flow = b.flow\r\n"
    + "                AND a.orgid = b.orgid) AS flowid,\r\n"
    + "            b.orgid AS orgid\r\n"
    + "        FROM\r\n"
    + "            bininward b\r\n"
    + "        GROUP BY \r\n"
    + "            b.emitterid, \r\n"
    + "            b.kitcode, \r\n"
    + "            flowid, \r\n"
    + "            b.orgid) a\r\n"
    + "    LEFT JOIN \r\n"
    + "        (SELECT \r\n"
    + "            binoutward.emitterid AS emitterid,\r\n"
    + "            binoutward.kitno AS kitno,\r\n"
    + "            SUM(binoutward.outwardkitqty) AS kitqty,\r\n"
    + "            binoutward.flow AS flowid,\r\n"
    + "            binoutward.orgid AS orgid\r\n"
    + "        FROM\r\n"
    + "            binoutward\r\n"
    + "        GROUP BY \r\n"
    + "            binoutward.emitterid, \r\n"
    + "            binoutward.kitno, \r\n"
    + "            binoutward.flow, \r\n"
    + "            binoutward.orgid) b \r\n"
    + "    ON \r\n"
    + "        a.emitterid = b.emitterid\r\n"
    + "        AND a.kitno = b.kitno\r\n"
    + "        AND a.flowid = b.flowid\r\n"
    + "        AND a.orgid = b.orgid");
    
    jdbcTemplate.execute("create or replace view kitstockdetails as\r\n"
    		+ "select a.orgid,a.flow,b.flowid,a.docdate stockdate,a.kitcode kitno,sum(a.allotedqty)sqty  from bininward a,flow b where a.flow=b.flow group by a.orgid,a.flow,b.flowid,a.docdate,a.kitcode\r\n"
    		+ "union\r\n"
    		+ "select orgid,flow,flowid,docdate stockdate,kitno, sum(outwardkitqty*-1)sqty  from binoutward   group by orgid,flow,flowid,docdate,kitno");
    
    jdbcTemplate.execute("CREATE or replace\r\n"
    		+ "VIEW availablekit1 AS\r\n"
    		+ "    SELECT \r\n"
    		+ "        a.whlocation AS whlocation,\r\n"
    		+ "        a.kitcode AS kitcode,\r\n"
    		+ "        COALESCE(FLOOR(MIN(a.avalqty)), 0) AS avalqty\r\n"
    		+ "    FROM\r\n"
    		+ "        (SELECT \r\n"
    		+ "            w.whlocation AS whlocation,\r\n"
    		+ "            a.kitno AS kitcode,\r\n"
    		+ "            b.asset AS asset,\r\n"
    		+ "            b.quantity AS quantity,\r\n"
    		+ "            COALESCE(SUM(c.skuqty), 0) AS SUM_skuqty,\r\n"
    		+ "            (CASE\r\n"
    		+ "                WHEN (b.quantity <> 0) THEN COALESCE((SUM(c.skuqty) / b.quantity), 0)\r\n"
    		+ "                ELSE 0\r\n"
    		+ "            END) AS avalqty\r\n"
    		+ "        FROM\r\n"
    		+ "            (kit a\r\n"
    		+ "        JOIN kit2 b ON (a.kitid = b.kitid))\r\n"
    		+ "        JOIN warehouse w ON (1 = 1)\r\n"
    		+ "        LEFT JOIN stockdetails c ON (b.asset = c.sku\r\n"
    		+ "            AND c.status = 'S'\r\n"
    		+ "            AND c.stockbranch = w.whlocation)\r\n"
    		+ "        GROUP BY w.whlocation, a.kitno, b.asset, b.quantity) a\r\n"
    		+ "    GROUP BY a.whlocation, a.kitcode\r\n");
    		
    }
}

