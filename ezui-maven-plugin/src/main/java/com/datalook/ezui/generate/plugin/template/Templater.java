package com.datalook.ezui.generate.plugin.template;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.maven.plugin.logging.Log;

import com.datalook.ezui.generate.plugin.model.EzuiHolder;
import com.datalook.ezui.generate.plugin.model.bean.page.Tree;
import com.datalook.ezui.generate.plugin.model.bean.page.TreeGrid;
import com.datalook.ezui.generate.plugin.util.TextUtil;

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

	private void generateFTL(Object o, String ftl, File file) {
		log.info("----------转换" + file.toString() + " 开始----------");
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
			temp.process(o, out);
			out.flush();
		} catch (TemplateException e) {
			log.error("模板转换错误");
		} catch (IOException e) {
			log.error("模板转换后，输出错误");
		}
		log.info("----------转换" + file.toString() + " " + ftl + " 结束----------");
	}

	public void generate(EzuiHolder ezuiHolder) {
		log.info("----------转换" + ezuiHolder.clazz.toString() + "开始----------");
		TextUtil.addFunction("1000", "页面自动生成", "2", "0", "");
		if (ezuiHolder != null) {
			if (ezuiHolder.pages.dataGrid != null) {
				generateFTL(ezuiHolder, "datagrid.ftl", ezuiHolder.pages.dataGrid.file);
				ezuiHolder.pages.dataGrid.sqlId = TextUtil.randomId().toString();
				TextUtil.addFunction(ezuiHolder.pages.dataGrid.sqlId, ezuiHolder.pages.dataGrid.moduleName, "3", "1000", "/" + ezuiHolder.pages.dataGrid.webappURL);
				if (ezuiHolder.pages.dataGrid.saveabel) {
					TextUtil.addFunction(TextUtil.randomId().toString(), ezuiHolder.pages.dataGrid.moduleName + "添加", "0", ezuiHolder.pages.dataGrid.sqlId, "/" + ezuiHolder.javas.action.beanName + "!save");
				}
				if (ezuiHolder.pages.dataGrid.updateable) {
					TextUtil.addFunction(TextUtil.randomId().toString(), ezuiHolder.pages.dataGrid.moduleName + "编辑", "0", ezuiHolder.pages.dataGrid.sqlId, "/" + ezuiHolder.javas.action.beanName + "!update");
				}
				if (ezuiHolder.pages.dataGrid.getByIdable) {
					TextUtil.addFunction(TextUtil.randomId().toString(), ezuiHolder.pages.dataGrid.moduleName + "详情", "0", ezuiHolder.pages.dataGrid.sqlId, "/" + ezuiHolder.javas.action.beanName + "!getById");
				}
				if (ezuiHolder.pages.dataGrid.getByIdable) {
					TextUtil.addFunction(TextUtil.randomId().toString(), ezuiHolder.pages.dataGrid.moduleName + "列表", "0", ezuiHolder.pages.dataGrid.sqlId, "/" + ezuiHolder.javas.action.beanName + "!datagridByPage");
				}
				if (ezuiHolder.pages.dataGrid.deleteable) {
					TextUtil.addFunction(TextUtil.randomId().toString(), ezuiHolder.pages.dataGrid.moduleName + "删除", "0", ezuiHolder.pages.dataGrid.sqlId, "/" + ezuiHolder.javas.action.beanName + "!" + ezuiHolder.pages.dataGrid.deleteMethod);
				}
			}
			if (ezuiHolder.pages.form != null) {
				generateFTL(ezuiHolder, "form.ftl", ezuiHolder.pages.form.file);
			}
			if (ezuiHolder.javas.service != null) {
				generateFTL(ezuiHolder, "service.ftl", ezuiHolder.javas.service.file);
				generateFTL(ezuiHolder, "serviceImpl.ftl", ezuiHolder.javas.service.fileImpl);
			}
			if (ezuiHolder.javas.action != null) {
				generateFTL(ezuiHolder, "action.ftl", ezuiHolder.javas.action.file);
			}
			if (ezuiHolder.pages.trees.size() > 0) {
				for (Tree eachTree : ezuiHolder.pages.trees) {
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("ezuiHolder", ezuiHolder);
					m.put("tree", eachTree);
					generateFTL(m, "tree.ftl", eachTree.file);
					TextUtil.addFunction(TextUtil.randomId().toString(), eachTree.buttonText, "0", ezuiHolder.pages.dataGrid.sqlId, "/" + ezuiHolder.javas.action.beanName + "!" + eachTree.actionMethodName);
				}
			}
			if (ezuiHolder.pages.treegrids.size() > 0) {
				for (TreeGrid eachTreeGrid : ezuiHolder.pages.treegrids) {
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("ezuiHolder", ezuiHolder);
					m.put("treegrid", eachTreeGrid);
					generateFTL(m, "treegrid.ftl", eachTreeGrid.file);
					TextUtil.addFunction(TextUtil.randomId().toString(), eachTreeGrid.buttonText, "0", ezuiHolder.pages.dataGrid.sqlId, "/" + ezuiHolder.javas.action.beanName + "!" + eachTreeGrid.actionMethodName);
				}
			}
		}
		log.info("----------转换" + ezuiHolder.clazz.toString() + "结束----------");
	}

}
