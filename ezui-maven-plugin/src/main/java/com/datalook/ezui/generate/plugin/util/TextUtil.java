package com.datalook.ezui.generate.plugin.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TextUtil {
	public static String resourceDir;
	public static Integer id = 1001;
	public static BufferedReader br;
	public static BufferedWriter bw;
	
	public synchronized static void addSql(String sql) {
		try {
			FileReader fr=new FileReader(new File(resourceDir + "\\init.sql"));
			br = new BufferedReader(fr);
			String lineTxt="";
			while ((lineTxt = br.readLine()) != null) {
				if(lineTxt.contains(sql)){
					return;
				}
			}
			br.close();
			bw = new BufferedWriter(new FileWriter(new File(resourceDir + "/init.sql"),true)); 
			bw.append(sql+"\n");
			bw.flush();
			bw.close();
		} catch (IOException e) {
			try {
				br.close();
				bw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		System.out.println(sql);
	}

	public static Integer randomId() {
		return id++;
	}

	public static String formateString(String str, String... params) {
		for (int i = 0; i < params.length; i++) {
			str = str.replace("{" + i + "}", params[i] == null ? "" : params[i]);
		}
		return str;
	}

	public static void addFunction(String id, String functionname, String functiontype, String pid, String url) {
		TextUtil.addSql(TextUtil.formateString("insert into SYS_FUNCTION(id,functionname,functiontype,pid,seq,url) values({0},'{1}','{2}',{3},{4},'{5}');", new String[] { id, functionname, functiontype, pid, id, url }));
		TextUtil.addSql("insert into SYS_ROLE_FUNCTION_RELATION(roleid,sysfunctionid) values(1," + id + ");");
	}

	public static void addSysDict(String location, String value, String text) {
		TextUtil.addSql("insert into SYS_DICT(location,value,text) values('" + location + "','" + value + "','" + text + "');");
	}
}
