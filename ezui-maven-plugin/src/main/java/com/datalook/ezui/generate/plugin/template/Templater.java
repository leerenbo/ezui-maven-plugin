package com.datalook.ezui.generate.plugin.template;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.maven.plugin.logging.Log;

import com.datalook.ezui.generate.plugin.model.EzuiHolder;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Templater {
	Configuration cfg;
	private Log log;

	public Templater(Log log) {
		log.info("----------模板初始化----------");
		this.log = log;
		cfg = new Configuration();
		cfg.setClassForTemplateLoading(this.getClass(), "/template");
		// cfg.setObjectWrapper(ObjectWrapper.DEFAULT_WRAPPER);
		log.info("----------模板初始化结束----------");
	}

	private void generateFTL(EzuiHolder ezuiHolder, String ftl, File file) {
		log.info("----------转换" + ezuiHolder.clazz.toString() + " " + ftl + " 开始----------");
		Template temp = null;
		try {
			temp = cfg.getTemplate(ftl);
		} catch (IOException e) {
			log.error("模板加载错误");
			e.printStackTrace();
		}

		Writer out = null;
		try {
			file.getParentFile().mkdirs();
			file.createNewFile();
			out = new OutputStreamWriter(new FileOutputStream(file));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			temp.process(ezuiHolder, out);
			out.flush();
		} catch (TemplateException e) {
			log.error("模板转换错误");
		} catch (IOException e) {
			log.error("模板转换后，输出错误");
		}
		log.info("----------转换" + ezuiHolder.clazz.toString() + " " + ftl + " 结束----------");
	}

	public void generate(EzuiHolder ezuiHolder) {
		log.info("----------转换" + ezuiHolder.clazz.toString() + "开始----------");
		if (ezuiHolder != null) {
			if (ezuiHolder.pages.dataGrid != null) {
				generateFTL(ezuiHolder,"datagrid.ftl",ezuiHolder.pages.dataGrid.file);
			}
			if (ezuiHolder.pages.form != null) {
				generateFTL(ezuiHolder,"form.ftl",ezuiHolder.pages.form.file);
			}
			if (ezuiHolder.javas.service != null) {
				generateFTL(ezuiHolder,"service.ftl",ezuiHolder.javas.service.file);
				generateFTL(ezuiHolder,"serviceImpl.ftl",ezuiHolder.javas.service.fileImpl);
			}
			if(ezuiHolder.javas.action!=null){
				generateFTL(ezuiHolder, "action.ftl", ezuiHolder.javas.action.file);
			}
		}
		log.info("----------转换" + ezuiHolder.clazz.toString() + "结束----------");
	}

}
