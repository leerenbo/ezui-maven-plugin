package com.datalook.ezui.generate.plugin.model.bean;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.project.MavenProject;

import com.datalook.ezui.annotation.page.EzuiDataGrid;
//import com.datalook.ezui.annotation.page.EzuiPropertyGrid;
import com.datalook.ezui.annotation.page.EzuiTreeGrid;
import com.datalook.ezui.generate.plugin.model.bean.page.DataGrid;
import com.datalook.ezui.generate.plugin.model.bean.page.Form;
//import com.datalook.ezui.generate.plugin.model.bean.page.PropertyGrid;
import com.datalook.ezui.generate.plugin.model.bean.page.Tree;
import com.datalook.ezui.generate.plugin.model.bean.page.TreeGrid;

public class Pages {
	public String pagesPackage;

	public String webappDir;
	public String incPath = "";

	public DataGrid dataGrid;
	// public PropertyGrid propertyGrid;
	public Set<Tree> trees = new HashSet<Tree>();
	public Set<TreeGrid> treegrids = new HashSet<TreeGrid>();
	public TreeGrid treeGrid;
	public Form form = new Form();
	public String moduleName;

	public void init(Class clazz, MavenProject project) {
		webappDir = project.getBasedir() + "\\src\\main\\webapp\\";
		pagesPackage = StringUtils.substringAfter(clazz.getPackage().toString(), ".model.").replace(".", "\\");
		incPath = StringUtils.repeat("../", StringUtils.split(pagesPackage, "\\").length + 1);
		Annotation[] clazzAnns = clazz.getAnnotations();
		for (int i = 0; i < clazzAnns.length; i++) {
			if (clazzAnns[i] instanceof EzuiDataGrid) {
				dataGrid = new DataGrid((EzuiDataGrid) clazzAnns[i]);
				dataGrid.init(clazz);
				dataGrid.file = new File(webappDir + "pages\\" + pagesPackage + "\\" + StringUtils.uncapitalize(clazz.getSimpleName()) + "Datagrid.jsp");
				dataGrid.webappURL = "pages/" + pagesPackage.replace("\\", "/") + "/" + StringUtils.uncapitalize(clazz.getSimpleName()) + "Datagrid.jsp";
				moduleName = dataGrid.moduleName;
				if (dataGrid.uploadExcelable) {
					dataGrid.uploadExcelURL = "pages/" + pagesPackage.replace("\\", "/") + "/" + StringUtils.uncapitalize(clazz.getSimpleName()) + "UploadExcel.jsp";
					dataGrid.uploadExcelFile=new File(webappDir + "pages\\" + pagesPackage + "\\" + StringUtils.uncapitalize(clazz.getSimpleName()) + "UploadExcel.jsp");
				}
			}
			// if (clazzAnns[i] instanceof EzuiPropertyGrid) {
			// propertyGrid = new PropertyGrid((EzuiPropertyGrid) clazzAnns[i]);
			// }
			if (clazzAnns[i] instanceof EzuiTreeGrid) {
				treeGrid = new TreeGrid((EzuiTreeGrid) clazzAnns[i]);
			}
		}
		form.file = new File(webappDir + "pages\\" + pagesPackage + "\\" + StringUtils.uncapitalize(clazz.getSimpleName()) + "Form.jsp");
		form.webappURL = "pages/" + pagesPackage.replace("\\", "/") + "/" + StringUtils.uncapitalize(clazz.getSimpleName()) + "Form.jsp";
		form.fileName = StringUtils.uncapitalize(clazz.getSimpleName()) + "Form.jsp";
		form = form.init(clazz);
	}

	public String getPagesPackage() {
		return pagesPackage;
	}

	public void setPagesPackage(String pagesPackage) {
		this.pagesPackage = pagesPackage;
	}

	public String getWebappDir() {
		return webappDir;
	}

	public void setWebappDir(String webappDir) {
		this.webappDir = webappDir;
	}

	public String getIncPath() {
		return incPath;
	}

	public void setIncPath(String incPath) {
		this.incPath = incPath;
	}

	public DataGrid getDataGrid() {
		return dataGrid;
	}

	public void setDataGrid(DataGrid dataGrid) {
		this.dataGrid = dataGrid;
	}

	// public PropertyGrid getPropertyGrid() {
	// return propertyGrid;
	// }
	//
	// public void setPropertyGrid(PropertyGrid propertyGrid) {
	// this.propertyGrid = propertyGrid;
	// }

	public TreeGrid getTreeGrid() {
		return treeGrid;
	}

	public void setTreeGrid(TreeGrid treeGrid) {
		this.treeGrid = treeGrid;
	}

	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

	public Set<Tree> getTrees() {
		return trees;
	}

	public void setTrees(Set<Tree> trees) {
		this.trees = trees;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Set<TreeGrid> getTreegrids() {
		return treegrids;
	}

	public void setTreegrids(Set<TreeGrid> treegrids) {
		this.treegrids = treegrids;
	}

}
