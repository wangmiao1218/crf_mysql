<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>RWS测试_引用类型</title>
<style type="text/css">
#box {
	position: absolute;
	left: 900px;
	top: 300px;
	padding: 5px;
	background: #f0f3f9;
	font-size: 12px;
	-moz-box-shadow: 2px 2px 4px #666666;
	-webkit-box-shadow: 2px 2px 4px #666666;
}

#main {
	border: 1px solid #a0b3d6;
	background: white;
}

#bar {
	line-height: 24px;
	background: #beceeb;
	border-bottom: 1px solid #a0b3d6;
	padding-left: 5px;
	cursor: move;
}

#content {
	width: 300px;
	height: 200px;
	padding: 10px 5px;
}
</style>
<%
	String rootPath = request.getContextPath();
%>

<body>
	<h4>RWS测试_引用比较_事件</h4>
	<h5>引用类型——测试数据：pat_testrws002.txt(pat_testrws002_1.txt)</h5>
	<p />
	<a href="<%=rootPath%>/page/testRWS_1.jsp">RWS测试_直接比较_事件</a>
	<p />


	<div id="box">
		<div id="main">
			<div id="bar">所引用的指标</div>
			<div id="content">内容……</div>
		</div>
	</div>





	<div>
		<span>一、定义事件</span><br /> <input type="text" placeholder="事件名称" />
		检索结果： <select>
			<option value="">就诊.病案首页</option>
		</select> 结果处理函数： <select>
			<option value="">第一个</option>
			<option value="">全部</option>
			<option value="">最后一个</option>
			<option value="">第N个</option>
		</select>
	</div>
	<p />
	<div>
		<span>二、定义指标</span><br /> <input type="text" placeholder="指标名称" />
		指标类型： <select>
			<option value="">默认类型</option>
			<option value="">枚举类型</option>
		</select> 检索结果： <select>
			<option value="">就诊.病案首页</option>
		</select> 结果处理函数： <select>
			<option value="">第一个</option>
			<option value="">全部</option>
			<option value="">最后一个</option>
			<option value="">第N个</option>
		</select>
	</div>
	<p />

	<div>
		<span>1.数值类型:大于指标值</span><br /> <select>
			<option value=""></option>
		</select> <select>
			<option value="">大于指标值</option>
		</select> <select>
			<option value=""></option>
		</select> <input type="text" placeholder="" /> <input type="text"
			placeholder="" />
		<p />

		<span>2.数值类型:大于等于指标值</span><br /> <select>
			<option value=""></option>
		</select> <select>
			<option value="">大于等于指标值</option>
		</select> <select>
			<option value=""></option>
		</select> <input type="text" placeholder="" /> <input type="text"
			placeholder="" />
		<p />


		<span>3.数值类型:小于指标值</span><br /> <select>
			<option value=""></option>
		</select> <select>
			<option value="">小于指标值</option>
		</select> <select>
			<option value=""></option>
		</select> <input type="text" placeholder="" /> <input type="text"
			placeholder="" />
		<p />


		<span>4.数值类型:小于等于指标值</span><br /> <select>
			<option value=""></option>
		</select> <select>
			<option value="">小于等于指标值</option>
		</select> <select>
			<option value=""></option>
		</select> <input type="text" placeholder="" /> <input type="text"
			placeholder="" />
		<p />


		<span>5.数值类型:大于指标百分比</span><br /> <select>
			<option value=""></option>
		</select> <select>
			<option value="">大于指标百分比</option>
		</select> <select>
			<option value=""></option>
		</select> <input type="text" placeholder="" /> <input type="text"
			placeholder="" />
		<p />


		<span>6.数值类型:大于等于指标百分比</span><br /> <select>
			<option value=""></option>
		</select> <select>
			<option value="">大于等于指标百分比</option>
		</select> <select>
			<option value=""></option>
		</select> <input type="text" placeholder="" /> <input type="text"
			placeholder="" />
		<p />


		<span>7.数值类型:小于指标百分比</span><br /> <select>
			<option value=""></option>
		</select> <select>
			<option value="">小于指标百分比</option>
		</select> <select>
			<option value=""></option>
		</select> <input type="text" placeholder="" /> <input type="text"
			placeholder="" />
		<p />


		<span>8.数值类型:小于等于指标百分比</span><br /> <select>
			<option value=""></option>
		</select> <select>
			<option value="">大于指标值</option>
		</select> <select>
			<option value=""></option>
		</select> <input type="text" placeholder="" /> <input type="text"
			placeholder="" />
		<p />


		<span>9.日期类型:早于指标日期</span><br /> <select>
			<option value=""></option>
		</select> <select>
			<option value="">早于指标日期</option>
		</select> <select>
			<option value=""></option>
		</select> <input type="text" placeholder="" /> <input type="text"
			placeholder="" />
		<p />


		<span>10.日期类型:晚于指标日期</span><br /> <select>
			<option value=""></option>
		</select> <select>
			<option value="">晚于指标日期</option>
		</select> <select>
			<option value=""></option>
		</select> <input type="text" placeholder="" /> <input type="text"
			placeholder="" />
		<p />


	</div>





</body>
</html>
