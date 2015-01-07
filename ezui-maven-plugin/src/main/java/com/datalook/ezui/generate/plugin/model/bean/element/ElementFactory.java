package com.datalook.ezui.generate.plugin.model.bean.element;

import java.lang.reflect.Field;

import com.datalook.ezui.annotation.declare.EzuiCondition;
import com.datalook.ezui.annotation.declare.EzuiForm;
import com.datalook.ezui.annotation.declare.EzuiShow;
import com.datalook.ezui.annotation.element.EzuiElement;

public class ElementFactory {
	public static final int CONDITION = 0;
	public static final int FORM = 1;
	public static final int SHOW = 2;

	/**
	 * 优先级， 第一级 @EzuiCondition(element=@EzuiElement)
	 * 
	 * @EzuiFom(element=@EzuiElement) 指定条件生效 第二级 @EzuiElement
	 *                                Form，Condition，都条件生效。 第三級 @EzuiCondition
	 * @EzuiFom 指定默认生效 第四级 什么不都标注 Form，Condition，Show，默认生效
	 * @param field
	 * @param i
	 * @return
	 */
	public static Element instance(Field field, int i) {
		EzuiElement ezuiElement = field.getAnnotation(EzuiElement.class);
		EzuiCondition ezuiCondition = field.getAnnotation(EzuiCondition.class);
		EzuiForm ezuiForm = field.getAnnotation(EzuiForm.class);
		EzuiShow ezuiShow = field.getAnnotation(EzuiShow.class);

		// level 1
		switch (i) {
		case CONDITION:
			if (ezuiCondition != null) {
				if (ezuiCondition.element() != null) {
					return new Element(field, ezuiCondition.element());
				}
			}
			break;
		case FORM:
			if (ezuiForm != null) {
				if (ezuiForm.element() != null) {
					return new Element(field, ezuiForm.element());
				}
			}
			break;
		case SHOW:
			if (ezuiShow != null) {
				if (ezuiShow.element() != null) {
					return new Element(field, ezuiShow.element());
				}

			}
			break;
		}

		// level 2
		if (ezuiElement != null) {
			return new Element(field, ezuiElement);
		}

		// level 3
		switch (i) {
		case CONDITION:
			if (ezuiCondition != null) {
				return new Element(field);
			}
			break;
		case FORM:
			if (ezuiForm != null) {
				return new Element(field);
			}
			break;
		case SHOW:
			if (ezuiShow != null) {
				return new Element(field);
			}
			break;
		}

		// level 4
		if (ezuiElement == null && ezuiCondition == null && ezuiForm == null && ezuiShow == null) {
			return new Element(field);
		}

		return null;
	}

}
