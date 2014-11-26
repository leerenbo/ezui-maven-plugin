package com.datalook.ezui.generate.plugin.model.bean.declare;

import java.lang.reflect.Field;

import com.datalook.ezui.annotation.declare.EzuiCondition;
import com.datalook.ezui.annotation.declare.EzuiForm;
import com.datalook.ezui.annotation.declare.EzuiShow;
import com.datalook.ezui.annotation.element.EzuiElement;

public class ShowFactory {
	
	/**
	 * 优先级，
	 * 第一级	@EzuiShow
	 * 				指定条件生效
	 * 第二级	什么不都标注
	 * 				Form，Condition，Show，默认生效
	 * @param field
	 * @return
	 */
	public static Show instance(Field field){
		EzuiElement ezuiElement = field.getAnnotation(EzuiElement.class);
		EzuiCondition ezuiCondition = field.getAnnotation(EzuiCondition.class);
		EzuiForm ezuiForm = field.getAnnotation(EzuiForm.class);
		EzuiShow ezuiShow = field.getAnnotation(EzuiShow.class);
		
		//level 1
		if(ezuiShow!=null){
			return new Show(field,ezuiShow);
		}

		//level 2
		if(ezuiElement==null&&ezuiCondition==null&&ezuiForm==null&&ezuiShow==null){
			return new Show(field);
		}

		return null;
	}
}
