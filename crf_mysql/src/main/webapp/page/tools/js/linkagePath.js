Ext.Loader.setConfig({enabled: true});
Ext.Loader.setPath('Ext.ux', rootPath +'/res/extjs/ux/');
Ext.require([
    'Ext.data.*',
    'Ext.grid.*',
    'Ext.util.*',
    'Ext.form.field.ComboBox',
    'Ext.form.FieldSet',
    'Ext.tip.QuickTipManager',
    'Ext.ux.data.PagingMemoryProxy'
    
]);

var data, store, columns, queryGrid,pager;
Ext.onReady(function(){
	//initCombo();
    onfocus();
    //initButton();
    //initCombo("simpleCombo");
    //initCombo("simpleCombo2");
});

//初始化下拉框
/*
function initCombo(){
	// 生成下拉框数据源
    var store = Ext.create('Ext.data.Store', {
        autoDestroy: true,
        // 指定下拉框属性
        fields: ['codeValue', 'codeName'],
        // 给请求附加参数
        // 利用ajax请求获取数据
        proxy: {
	        type: 'ajax',
	        url : rootPath + '/common/getCodeValue',
	        reader: {
	             type: 'json'
	         },
	        extraParams:{
            	codeType : '1003'
            },
	    }
    });
    
    // 2、创建一个ComboBox，可见的
   Ext.create('Ext.form.field.ComboBox', {
	   // 渲染到哪个元素，div的id为comboId
	    renderTo: 'opKindCombo',
	   //2.2、对应上面的仓库
	    store: store,
	    //2.3、显示字段名
	    displayField: 'codeName',
	    //2.4、值的字段名
	    valueField: 'codeValue',
	    width: 280,
        labelWidth: 130,
        typeAhead: true,
        // 监听下拉框选中事件
        listeners:{
        	'select':function(){
        		// 给opKind赋值
        		$("#opKind").val(this.getValue());
        	},
        	// 当页面重新渲染时，设置下拉框选中回显
    		render:function(){
    			//重新加载数据
    			store.load();
    			//回显下拉框
    			this.setValue($("#opKind").val());
    		}
        }
        
    });
}
*/

 //点击保存按钮
function saveUser(){
	// 判断  新增 / 修改
	var type = $("#type").val();
	// 声明要提交的url
	var url = "";
	/*
	if(type == "add"){
		url = rootPath + "/user/addUser";
		$("#userMesForm").attr("action",url);
	}else{
		url = rootPath + "/user/modifyUser";
		$("#userMesForm").attr("action",url);
	}
	$("#userMesForm").submit();*/

	if(type == "add"){
		url = rootPath + "/user/addUser";
	}else{
		url = rootPath + "/user/modifyUser";
	}
	
	// ajax 方式提交表单
	//1、获取表单参数
	var data = $("#userMesForm").serialize();
	var type = "json";
	$.post(url,data,function(result){
		alert("新增成功");
		//手动跳转页面
		window.location.href = rootPath + "/page/user/userMgnt.jsp";
	},type);
	
	alert("修改成功");
	//手动跳转页面
	window.location.href = rootPath + "/page/user/userMgnt.jsp";
	
}

/*
* 提示文字
*/
function qtips(value, cellmeta, record, rowIndex, colIndex, store){
    return '<span  title="'+ value +'">' + value + '</span>';    
}

/**
 * 文件上传
 * @returns {Boolean}
 */
function upload() {
	var f = $("#uploadFile").val();
	if (f == "") {// 先判断是否已选择了文件
		alert("请选择模板结构(Excel)文件！");
		return false;
	}
	var f2 = $("#uploadFile2").val();
	if (f2 == "") {// 先判断是否已选择了文件
		alert("请选择单病种模板(Excel)文件！");
		return false;
	}
	
	//获取file的全部id  
    var fileslist = $("input[name^=files]");  
    var filesId = [];  
    for (var i=0; i< fileslist.length; i++){  
	    if(fileslist[i].value){  
	    	filesId[i] = fileslist[i].id;  
	    }  
    }
	//也可以直接写死id的数组
    //var filesId=['uploadFile','uploadFile2'];
	
	$.ajaxFileUpload({
		url : rootPath + '/linkagePathController/uploadFiles',
		type : 'post',
		dataType : 'text',
		// 对应file标签的id
		//若多个，写成数组
		fileElementId : filesId,
		data : {
			
		},
		success : function(data) {
			alert("上传成功！");
		},
		error : function(data) {
			alert("上传失败");
		}
	});
}
/*=======================*/
function onfocus(){
    $("input[type='text']").focus(function(){
        $(this).addClass("blur");
    })
     $("input[type='text']").blur(function(){
        $(this).removeClass("blur");
    })
}