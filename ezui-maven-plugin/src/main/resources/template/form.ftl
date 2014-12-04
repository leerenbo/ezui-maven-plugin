<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
	if (id == null) {
		id = "";
	}
%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<jsp:include page="${pages.incPath}inc.jsp"></jsp:include>
<script type="text/javascript">
	var submitForm = function($dialog, $grid, $pjq) {
		if ($('form').form('validate')) {
			var url;
			if ($(':input[name="data.id"]').val().length > 0) {
				url = ez.contextPath + '/${javas.action.beanName}!update.action';
			} else {
				url = ez.contextPath + '/${javas.action.beanName}!save.action';
			}
			$('#ezAddAndUpdataform').form('submit', {
				url : url,
				onSubmit : function() {
					// do some check
					// return false to prevent submit;
					return true;
				},
				success : function(result) {
					result = $.parseJSON(result);
					$pjq.messager.alert('提示', result.msg, 'info');
					$grid.datagrid('load');
					$dialog.dialog('destroy');
				}
			});
		}
	};
	$(function() {
		if ($(':input[name="data.id"]').val().length > 0) {
			parent.$.messager.progress({
				text : '数据加载中....'
			});

			$.post(ez.contextPath + '/${javas.action.beanName}!getById.action', {
				'data.id' : $(':input[name="data.id"]').val()
			}, function(result) {
				if (result.id != undefined) {
					var data = $.parseJSON(ez.jsonToString(result));
					if (result.id != undefined) {
						$('#ezAddAndUpdataform').form('load', data);
					}
				}
				parent.$.messager.progress('close');
			}, 'json');
		}
	});
</script>
</head>
<body>
	<form id="ezAddAndUpdataform" method="post" enctype="multipart/form-data" class="form">
		<fieldset>
			<legend>用户基本信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
				<#list pages.form.formElements as element>
				<#if element_index%2==0&&element_index!=0>
				</tr>
				<tr>
				</#if>
					<th>${element.title}</th>
					<td><input name="data.${element.fieldName}" class="easyui-${element.elementType}" data-options=""/></td>
				</#list>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>