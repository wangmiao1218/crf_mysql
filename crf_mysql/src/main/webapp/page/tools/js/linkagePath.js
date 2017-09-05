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

 //开始处理按钮
function dealFile(){
	url = rootPath + "/crfLinkagePathController/dealFile";
	$.ajax({
        type : "get",// 请求方式
        url : url,// 发送请求地址
        dataType : 'json',
        //加遮蔽罩
        beforeSend: function () {
        	$("#myShow").show();
        },
        
        // 请求成功后的回调函数
		success : function(data) {
			alert(data.msg);
			//手动跳转页面
			//window.location.href = rootPath + "/crfLinkagePathController/showFilesList";
		},
		complete: function () {
			$("#myShow").hide();
        },
		error : function(data) {
			alert("处理失败！");
		}
    });

}

/*//下载
function download(){
	url = rootPath + "/crfLinkagePathController/showFilesList";
	$.ajax({
        type : "get",// 请求方式
        url : url,// 发送请求地址
        //dataType : 'json',
        // 请求成功后的回调函数
		success : function(data) {
			alert("ok");
		},
		error : function(data) {
			alert("error");
		}
    });
}*/

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
		url : rootPath + '/crfLinkagePathController/uploadFiles',
		beforeSend: function () {  
		  // 禁用按钮防止重复提交  
		  $("#upload-btn").attr({disabled:"disabled" });  
		}, 
		type : 'post',
		dataType : 'json',
		// 对应file标签的id
		//若多个，写成数组
		fileElementId : filesId,
		data : {
			
		},
		success : function(data) {
			alert(data.msg);
		},
		complete: function () {  
	        $("#upload-btn").removeAttr("disabled");  
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