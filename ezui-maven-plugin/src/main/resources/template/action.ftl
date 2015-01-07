package ${javas.action.packagee};

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;

import ${basePackage}.action.base.BaseAction;
import ${javas.genericClassFullName};
import ${javas.service.packagee}.${javas.service.className};
import ${basePackage}.service.base.BaseService;
<#if (javas.relates?size>0)>
import ${basePackage}.model.sys.easyui.Message;
</#if>
@Action("${javas.action.beanName}")
public class ${javas.action.className} extends BaseAction<${javas.genericClassSimpleName}> {
	
	@Resource(name = "${javas.service.beanName}")
	public void setService(BaseService<${javas.genericClassSimpleName}> service) {
		this.service = service;
	}
	
	<#if (javas.relates?size>0)>
	<#list javas.relates as relate>
	public void ${relate.actionName}() throws IllegalAccessException {
		Message json = new Message();
		service.relate(data.getId(), ids, "${relate.relatedCollectionName}", ${relate.relatedClassFullName}.class);
		json.setSuccess(true);
		json.setMsg("操作成功");
		writeJson(json);
	}
	
	public void noSy_getRelated${relate.relatedClassSimpleName}() throws IllegalAccessException{
		writeJson(service.getById(data.getId()).get${relate.relatedCollectionName?cap_first}());
	}
	</#list>
	</#if>
	
	<#if javas.action.noSy_find>
	public void noSy_find() {
		find();
	}
	</#if>
	
	<#if (javas.action.beforeSaveIdnullables?size>0)> 
	@Override
	protected Message beforeSave() {
		<#list javas.action.beforeSaveIdnullables as beforeSaveIdnullable>
		${beforeSaveIdnullable}
		</#list>		
		return super.beforeSave();
	}
	</#if>
	
}
