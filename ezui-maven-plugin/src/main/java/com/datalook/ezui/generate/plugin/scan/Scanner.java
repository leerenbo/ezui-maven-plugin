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
import org.apache.maven.project.MavenProject;

import com.datalook.ezui.annotation.page.EzuiDataGrid;
//import com.datalook.ezui.annotation.page.EzuiPropertyGrid;
import com.datalook.ezui.annotation.page.EzuiTreeGrid;
import com.datalook.ezui.generate.plugin.model.EzuiHolder;

public class Scanner {
	private static URLClassLoader urlClassLoader;
	private List<String> classNames = new ArrayList<String>();
	private Log log;
	public static List<EzuiHolder> ezuiHolderlist;
	private static Scanner scanner;

	public Scanner(Log log) {
		this.log = log;
		scanner = this;
	}

	public static Scanner getInstance() {
		return scanner;
	}

	public List<EzuiHolder> scan(MavenProject project) throws Exception {
		log.info("----------开始扫描target/classes----------");
		ezuiHolderlist = new LinkedList<EzuiHolder>();

		try {
			urlClassLoader = URLClassLoader.newInstance(new URL[] { project.getArtifact().getFile().toURL() }, this.getClass().getClassLoader());
		} catch (MalformedURLException e1) {
			log.error("target/classes文件夹作为classpath异常，install后重新调用插件");
		}

		doScan(project.getArtifact().getFile());
		log.info("----------扫描target/classes结束----------" + classNames.toString());
		log.info("----------数据初始化----------");
		for (String className : classNames) {
			try {
				Class clazz = urlClassLoader.loadClass(className);
				if (clazz.getAnnotation(EzuiDataGrid.class) != null|| clazz.getAnnotation(EzuiTreeGrid.class) != null) {// || clazz.getAnnotation(EzuiPropertyGrid.class) != null 
					EzuiHolder eh = new EzuiHolder(clazz, project);
					ezuiHolderlist.add(eh);
				}
			} catch (ClassNotFoundException e) {
				log.error("target/classes文件夹下类加载异常");
			}
		}
		for (EzuiHolder eh : ezuiHolderlist) {
			eh.init();
		}
		log.info("----------数据初始化结束----------");
		for (EzuiHolder ezuiHolder : ezuiHolderlist) {
			ezuiHolder.deepScan();
		}
		return ezuiHolderlist;
	}

	private void doScan(File file) {
		File[] files = file.listFiles();
		for (File eachfile : files) {
			if (eachfile.isDirectory()) {
				doScan(eachfile);
			}
			if (eachfile.isFile() && eachfile.getName().endsWith(".class")) {
				String className = StringUtils.substringBetween(eachfile.getAbsolutePath(), "\\target\\classes\\", ".class").replace("\\", ".");
				if (className.contains(".model."))
					classNames.add(className);
			}
		}
	}

	public static EzuiHolder getEzuiHolderByClass(Class clazz) {
		for (EzuiHolder eachEzuiHolder : ezuiHolderlist) {
			if (eachEzuiHolder.clazz.equals(clazz))
				return eachEzuiHolder;
		}
		return null;
	}

	public static URLClassLoader getUrlClassLoader() {
		return urlClassLoader;
	}
	
}
