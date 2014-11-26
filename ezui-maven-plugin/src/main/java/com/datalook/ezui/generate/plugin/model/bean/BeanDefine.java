package com.datalook.ezui.generate.plugin.model.bean;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.datalook.ezui.annotation.declare.EzuiTransparent;
import com.datalook.ezui.annotation.page.EzuiDataGrid;
import com.datalook.ezui.annotation.page.EzuiPropertyGrid;
import com.datalook.ezui.annotation.page.EzuiTree;
import com.datalook.ezui.annotation.page.EzuiTreeGrid;
import com.datalook.ezui.generate.plugin.model.bean.declare.Show;
import com.datalook.ezui.generate.plugin.model.bean.declare.ShowFactory;
import com.datalook.ezui.generate.plugin.model.bean.element.Element;
import com.datalook.ezui.generate.plugin.model.bean.element.ElementFactory;
import com.datalook.ezui.generate.plugin.model.bean.page.DataGrid;
import com.datalook.ezui.generate.plugin.model.bean.page.PropertyGrid;
import com.datalook.ezui.generate.plugin.model.bean.page.Tree;
import com.datalook.ezui.generate.plugin.model.bean.page.TreeGrid;

public class BeanDefine {
	public Class clazz;
	public List<Element> conditionElements = new ArrayList<Element>();
	public List<Element> formElements = new ArrayList<Element>();
	public List<Show> showFields = new ArrayList<Show>();

	public DataGrid dataGrid;
	public PropertyGrid propertyGrid;
	public Tree tree;
	public TreeGrid treeGrid;

	public void init() {
		Annotation[] clazzAnns = clazz.getAnnotations();
		for (int i = 0; i < clazzAnns.length; i++) {
			if (clazzAnns[i] instanceof EzuiDataGrid) {
				dataGrid = new DataGrid((EzuiDataGrid) clazzAnns[i]);
			}
			if (clazzAnns[i] instanceof EzuiPropertyGrid) {
				propertyGrid = new PropertyGrid((EzuiPropertyGrid) clazzAnns[i]);
			}
			if (clazzAnns[i] instanceof EzuiTree) {
				tree = new Tree((EzuiTree) clazzAnns[i]);
			}
			if (clazzAnns[i] instanceof EzuiTreeGrid) {
				treeGrid = new TreeGrid((EzuiTreeGrid) clazzAnns[i]);
			}
		}

		Field[] fields = clazz.getFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			EzuiTransparent ezuiTransparent = field.getAnnotation(EzuiTransparent.class);
			
			if (ezuiTransparent != null) {
				continue;
			}

			Element eConditon = ElementFactory.instance(field, ElementFactory.CONDITION);
			Element eForm = ElementFactory.instance(field, ElementFactory.FORM);
			Show s = ShowFactory.instance(field);

			if (eConditon != null)
				conditionElements.add(eConditon);
			if (eForm != null)
				formElements.add(eForm);
			if (s != null)
				showFields.add(s);
		}
	}
}
