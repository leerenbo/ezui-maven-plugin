package com.datalook.ezui.generate.plugin.scan;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.logging.Log;

import com.datalook.ezui.generate.plugin.model.EzuiHolder;

public class Scanner {
	private URLClassLoader ucl;
	private List<String> classNames=new ArrayList<String>();
//	private Log log;

	public Scanner(Log log) {
//		this.log = log;
	}

	public Scanner() {
		super();
	}

	public List<EzuiHolder> scan(File classPathFile) {
//		log.info("----------开始扫描target/classes----------");
		List<EzuiHolder> re = new LinkedList<EzuiHolder>();
		try {
			ucl = URLClassLoader.newInstance(new URL[] { classPathFile.toURL() });
		} catch (MalformedURLException e1) {
//			log.error("target/classes文件夹作为classpath异常，install后重新调用插件");
		}

		doScan(classPathFile);
//		log.info("----------扫描target/classes结束----------");
//		log.info("----------数据初始化----------");
		for (String className : classNames) {
			EzuiHolder eh = new EzuiHolder();
			try {
				eh.beanDefine.clazz = ucl.loadClass(className);
				eh.init();
				re.add(eh);
			} catch (ClassNotFoundException e) {
//				log.error("target/classes文件夹下类加载异常");
			}
		}
//		log.info("----------数据初始化结束----------");
		return re;
	}

	private void doScan(File file) {
		File[] files = file.listFiles();
		for (File eachfile : files) {
			if (eachfile.isDirectory()) {
				doScan(eachfile);
			}
			if (eachfile.isFile() && eachfile.getName().endsWith(".class")) {
				String className = StringUtils.substringAfterLast(eachfile.getAbsolutePath(), "\\target\\classes\\").replace("\\", ".");
				className = className.substring(0, className.length() - 6);
				classNames.add(className);
			}
		}
	}
}
