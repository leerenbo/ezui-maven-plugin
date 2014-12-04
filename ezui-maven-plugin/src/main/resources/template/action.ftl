package ${javas.action.packagee};

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;

import ${basePackage}.action.base.BaseAction;
import ${javas.genericClassFullName};
import ${javas.service.packagee}.${javas.service.className};

@Action("${javas.action.beanName}")
public class ${javas.action.className}Action extends BaseAction<${javas.genericClassSimpleName}> {
	
	@Resource(name = "sysDictService")
	public void setService(BaseService<${javas.genericClassSimpleName}> service) {
		this.service = service;
	}
	
}
