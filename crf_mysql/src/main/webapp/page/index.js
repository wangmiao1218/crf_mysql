Ext.onReady(function(){
	Ext.QuickTips.init();
	// 先获取菜单
    getMenu();
    // 再通过jquery操作节点
    menuOpera();
    initLogout();
});


function initLogout(){
	$(".logout").click(function(){
		window.location.href = rootPath + "/doLogout";
	});
	
}

/**
 * 得到所有菜单
 */
function getMenu() {
	var homePage = null;
	var menuData ="";
	
	$.ajax({
	 // 注意请求地址也要变为动态
   	/* url : rootPath + '/logincontroller/getMenu',*/
   	 // 注意请求地址也要变为动态
   	 url : rootPath + '/crfTemplateController/getCrfTemplateList',
   	 // 同步：false,默认是true
   	 async : false,
   	 dataType: 'json',
   	 type:'GET',
   	 data:{
   		 
   	 },
   	 success : function(data){
   		// 赋值
   		var menuData = data;
   		
   		var firstCount = 0; //记录当前菜单箭头位置，用于子菜单显示时确定自己的位置
   		for(var i = 0; i < menuData.length; i++) {
   			var menu = menuData[i];
   			var menuHtml="";
   			var secondMenuHtml="";
   			var oper = ""; //点击动作字符串
   			var css = ""; //css
   			//排除“首页”这个菜单
   			if (menu != null && menu != ""  &&  menu.funcLevel == "1") {
   				//如果链接为空，则输入默认点击操作
   				if(menu.funcUrl != null && menu.funcUrl != "" && menu.funcUrl != "*.jsp") {
   					oper = 'onclick=\'$("#indexiframe").attr("src","page/' + menu.funcUrl + '");$("#cnt-title").html("'+menu.funcName+'");\'';
   					//给首页赋值
   					if(i==0)homePage = "" + menu.funcUrl;
   				}
   				//根据code判断菜单小图标
   				if (menu.funcCode == "LIVING_HALL") {
   					css += "acc-ico";
   				} else if (menu.funcCode == "ABILITY_CUBE") {
   					css += "acc-ico";
   				} else if (menu.funcCode == "STRATEGY_CENTER") {
   					css += "acc-ico";
   				} else if (menu.funcCode == "ELEMENT_MGNT") {
   					css += "acc-ico";
   				} else if (menu.funcCode == "SYS_MGNT") {
   					css += "acc-ico";
   				} else if (menu.funcCode == "MAINTENANCE_MENT") {
   					css +="acc-ico";
   				}else if (menu.funcCode == "SHANGPINGUANLI") {
   					css +="goods-ico";
   				}else if (menu.funcCode == "DINGDANGUANLI") {
   					css +="busi-ico";
   				}else if (menu.funcCode == "GONGYINGSHANGGUANLI") {
   					css +="acc-ico";
   				}else {
   					css +="acc-ico";
   				}
   				css =" class=\""+css+"\" ";
   				if(menu.children != null && menu.children.length > 0) {
   					secondMenuHtml="<ul class=\"second-menu\">";
   					for (var y = 0; y < menu.children.length; y++) {
   						var secondMenu = menu.children[y];
   						var secondeOper = "";
   						if (secondMenu.funcUrl != null && secondMenu.funcUrl != "" && secondMenu.funcUrl != "*.jsp") {
   							secondeOper = 'onclick=\'$("#indexiframe").attr("src","page/' + secondMenu.funcUrl + '");$("#cnt-title").html("'+secondMenu.funcName+'");\'';
   							if(y==0){
   								secondMenuHtml+="<li id=\"defaultPage"+firstCount+"\" idsrc=\"page/"+secondMenu.funcUrl+"\" "+secondeOper+">"+secondMenu.funcName+"</li>"
   								//给首页赋值
   								firstCount++;
   							}else{
   								secondMenuHtml+="<li "+secondeOper+">"+secondMenu.funcName+"</li>"
   							}
   						}
   					}
   					secondMenuHtml+="</ul>"
   					menuHtml = "<li class=\"frt hasMenu\"><span "+oper+" ><i id=\""+menu.funcId+"\"" +css+"></i>"+menu.funcName+"</span><em class=\"m-arrow\">></em>"+secondMenuHtml+"</li>";
   				} else {
   					menuHtml = "<li id=\"defaultPage"+firstCount+"\" idsrc=\"page/+"+menu.funcUrl+"\" class=\"frt\"><span "+oper+" ><i id=\""+menu.funcId+"\"" +css+"></i>"+menu.funcName+"</span></li>";
   					firstCount++;
   				}
   				// 最终拼装html代码
   				$("#menu").append(menuHtml);
   				
   				//默认选中菜单，主页通用js中
   				setDefaultPage();
   			}
   		}
   	 },
   	 error : function(data){
   	 	 alert("请求失败");
   	 }
   });
	

};

