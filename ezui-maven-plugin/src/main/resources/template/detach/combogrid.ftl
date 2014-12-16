<#macro combogrid element>
			    <input name="data.${element.fieldName}.${element.idField}" class="easyui-combogrid" style="width:250px" data-options="
			            panelWidth: 500,
			            idField: '${element.idField}',
			            textField: '${element.textField}',
			            url: ez.contextPath + '/${element.insideEzuiHolder.javas.action.beanName}!noSy_find.action',
			            method: 'get',
			            columns: [[
			            	<#list element.insideEzuiHolder.pages.dataGrid.showElements as insideShow>
			            	<#if insideShow.isHidden>
			            	<#else>			
			            	{field:'${insideShow.fieldName}',title:'${insideShow.title}',width:150},
			            	</#if>
			            	</#list>
			            ]],
			            fitColumns: true
			        "/>
</#macro>