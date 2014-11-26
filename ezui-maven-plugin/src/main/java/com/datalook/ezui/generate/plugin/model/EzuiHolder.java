package com.datalook.ezui.generate.plugin.model;

import com.datalook.ezui.generate.plugin.model.bean.BeanDefine;
import com.datalook.ezui.generate.plugin.model.javacode.Action;
import com.datalook.ezui.generate.plugin.model.javacode.Service;

public class EzuiHolder {
	public BeanDefine beanDefine=new BeanDefine();
	public Action action=new Action();
	public Service service=new Service();
	
	
	public void init() {
		beanDefine.init();
	}
}
