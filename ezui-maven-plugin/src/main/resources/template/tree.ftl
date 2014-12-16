<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
	if (id == null) {
		id = "";
	}
%>
<jsp:include page="${ezuiHolder.pages.form.fileName}"/>
<!DOCTYPE html>
<html>
<head>
<title></title>
<script type="text/javascript">
	var submitForm = function($dialog, $grid, $pjq) {
		var nodes = $('#tree').tree('getChecked', [ 'checked', 'indeterminate' ]);
		var ids = [];
		for (var i = 0; i < nodes.length; i++) {
			ids.push(nodes[i].id);
		}
		$.post(ez.contextPath + '/${ezuiHolder.javas.action.beanName}!${tree.actionMethodName}.action', {
			'data.id' : $(':input[name="data.id"]').val(),
			ids : ids.join(',')
		}, function(result) {
			if (result.success) {
				$dialog.dialog('destroy');
			} else {
				$pjq.messager.alert('提示', result.msg, 'error');
			}
			$pjq.messager.alert('提示', result.msg, 'info');
		}, 'json');
	};
	$(function() {
		parent.$.messager.progress({
			text : '数据加载中....'
		});
		$('#tree').tree({
			url : ez.contextPath + '/${tree.relateEzuiHolder.javas.action.beanName}!noSy_find.action<#if tree.haveStatus>?hqland_status_dengyu_String=1</#if>',
			parentField : '${tree.parentField!"pid"}',
			idField : '${tree.idField!"id"}',
			checkbox : true,
			formatter : function(node) {
				return node.${tree.treeField};
			},
			onLoadSuccess : function(node, data) {
				$.post(ez.contextPath + '/${ezuiHolder.javas.action.beanName}!noSy_getRelated${tree.relatedClassSimpleName}.action', {
					'data.id' : $(':input[name="data.id"]').val()
				}, function(result) {
					if (result) {
						for (var i = 0; i < result.length; i++) {
							var node = $('#tree').tree('find', result[i].${tree.idField!"id"});
							if (node) {
								var isLeaf = $('#tree').tree('isLeaf', node.target);
								if (isLeaf) {
									$('#tree').tree('check', node.target);
								}
							}
						}
					}
					parent.$.messager.progress('close');
				}, 'json');
			}
		});
	});
</script>
</head>
<body>
	<input name="data.id" value="<%=id%>" readonly="readonly" type="hidden" />
	<fieldset>
		<legend>关联${tree.relateEzuiHolder.pages.moduleName!""}</legend>
		<ul id="tree"></ul>
	</fieldset>
</body>
</html>