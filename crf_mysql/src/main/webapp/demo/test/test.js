/**
 * @auth:wangmiao
 * @param globalObj：对象ID，用于防止内存泄露
 * @param elementID ：显示对象的elementID
 * @param title
 * @param subTitle
 * @param categories：X轴数据 一维数组
 * @param xName
 * @param yName
 * @param seriesArr：Y轴数据 二维数组
 */
function addLineChart(globalObj, elementID, title, subTitle, categories, xName,
		yName, seriesArr) {
	globalObj = new Highcharts.Chart({
		chart : {
			renderTo : elementID,
			defaultSeriesType : 'line',
			marginRight : 0,
			marginBottom : 80
		},
		title : {
			text : title,
			style : {
				fontSize : '12px'
			},
			x : -20
		},
		xAxis : {
			title : {
				text : xName
			},
			categories : categories,
			labels : {
				rotation : -45,
				align : 'right'
			}
		},
		yAxis : {
			title : {
				text : yName
			},
			min : 0,
			plotLines : [ {
				value : 0,
				width : 1,
				color : '#808080'
			} ]
		},
		tooltip : {
			formatter : function() {
				return '<b>' + this.x + '</b><br/>' + this.series.name + ': '
						+ this.y + '单位';
			}
		},
		legend : {
			layout : 'vertical',
			align : 'right',
			verticalAlign : 'top',
			x : 'left',
			y : -10,
			borderWidth : 0,
			layout : 'horizontal'
		},
		series : seriesArr
	});
}

//////////////////////////////////////////////////////////////

var chart1; // 全局变量
$(document).ready(function() {    
    var totalNum = $("#totalNum").val();//页面数据记录条数
    var st1=new Array();
    for(var i=totalNum-1;i>=0;i--){//组装数组数据
        var st2=new Array();
        var tt = $("#data"+i).val();
        var ttt = $("#Daily"+i).val();
        st2.push(Date.UTC(tt.split("-")[0],tt.split("-")[1]-1,tt.split("-")[2]));
        st2.push(parseInt(ttt));
        st1.push(st2);
    }
    chart1 = new Highcharts.Chart({
         credits: {  enabled: false},//去掉highcharts.com商标
         exporting: { enabled: false },  //去掉chart不必要属性
         chart: { renderTo: 'container', type: 'line' },
         title: { text: 'xxxx' },
         tooltip: {
              xDateFormat: '%Y-%m-%d, %A'//鼠标移动到趋势线上时显示的日期格式
         },
         xAxis: {type: 'datetime',
             dateTimeLabelFormats : { 
                day : '%m-%d',
                //second: '%H:%M:%S',
                   // minute: '%e. %b %H:%M',
                  //  hour: '%b/%e %H:%M',
                  //  day: '%e日/%b',
                   // week: '%e. %b',
                   // month: '%b %y',
                   // year: '%Y'
            },
            tickInterval: 2*24 * 3600 * 1000//间隔2天
        },
         yAxis: {title: { text: 'xxx', }, min:'0' },
         series: [{
            name: 'xxx',
            //用的数组格式
            data:st1
}] }); });












