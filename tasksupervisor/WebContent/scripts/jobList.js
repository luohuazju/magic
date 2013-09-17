jQuery(document).ready(function(){ 

	  jQuery("#list").jqGrid({
	    url:'job.do?method=listjson',
	    datatype: "json",
	    mtype: 'POST',
	    width: 1000,
	   	colModel:[
	   	       {name:'job_name',label:'任务编号', width:450,editable: false},
	   	       {name:'display_name',label:'任务名称',width:100, editable: true},
	   	       {name:'job_class_name',label:'任务类名', width:450,editable: true}
	   	],
	   	jsonReader : {
	        root: "rows",
	        page: "page",
	        total: "total",
	        records: "records",
	        repeatitems: false,
	        userdata: "userdata",
	        id: "job_name"
	    },
	    onSelectRow: function(id){ 
		    //alert(id);
		},
		onPaging: function(pgButton){
			//alert(pgButton);
		},
	   	pager : '#pager',
	    viewrecords : true,
	    sortorder : "desc", 
	    caption : "任务管理"
	  }); 

	  jQuery("#list").jqGrid('navGrid','#pager',
	  	{
		    addtext:"",
		    addtitle: "新增任务",
		    deltext: "",
		    deltitle: "删除所选数据",
		    refreshtext: "",
		    refreshtitle: "重新装载数据",
		    alertcap: "提示",
		    alerttext: "请选择一行数据！",
	  		add:true, 
	  		del:true, 
	  		search:false,
	  		edit:false,
	  		view:false
	  	},//nav
	  	{},//edit
	  	{ 
	  	     addCaption: "新增任务",
		     editCaption: "编辑任务",
		     url:"job.do?method=save",
		     mtype: "POST",
		     closeAfterAdd:true,
		     reloadAfterSubmit:true,
		     bSubmit: "提交",
		     bCancel: "取消",
		     bClose: "关闭",
		     saveData: "数据已经修改！是否保存？",
		     bYes : "是",
		     bNo : "否",
		     bExit : "取消"
	  	},//add
	  	{
		    caption: "删除任务",
		    msg: "删除所选任务？",
		    url:"job.do?method=cmd&action=remove",
		    mtype:"POST",
		    reloadAfterSubmit:true,
		    closeAfterDel:true,
		    bSubmit: "删除",
		    bCancel: "取消"
		}//delete
	  ).navButtonAdd('#pager',{
   		caption:"暂停", 
   		buttonicon:"ui-icon-add", 
   		onClickButton: function(){ 
     		var id = jQuery("#list").jqGrid('getGridParam','selrow');
			if (id)	{
				//有选择
				doCmd('pause',id);
			} else { 
				$.blockUI({ 
    				theme:true,
        			message: '<img src="./images/busy.gif" /> 请选择要操作的数据！',
        			timeout: 1000 
    			});
			}
   		}, 
   		position:"last"
	  }).navButtonAdd('#pager',{
   		caption:"恢复", 
   		buttonicon:"ui-icon-add", 
   		onClickButton: function(){ 
     		var id = jQuery("#list").jqGrid('getGridParam','selrow');
			if (id)	{
				//有选择
				doCmd('resume',id);
			} else { 
				$.blockUI({ 
    				theme:true,
        			message: '<img src="./images/busy.gif" /> 请选择要操作的数据！',
        			timeout: 1000 
    			});
			}
   		}, 
   		position:"last"
	  });
	  
	function doCmd(state, jobName) {
	// 客户端编码，服务端解码
	jobName = encodeURIComponent(encodeURIComponent(jobName));
	$.ajax({
		url : 'job.do?method=cmd&action='
				+ state + '&id=' + jobName,
		type : 'post',
		//dataType: 'xml',
		timeout: 10000,
		error : function() {
			$.blockUI({ 
    					theme:true,
        				message: '<img src="./images/busy.gif" /> 执行失败！',
        				timeout: 1000 
    		});
		},
		success : function(xml) {
			if (xml == 0) {
				$("#list").trigger("reloadGrid");
				$.blockUI({ 
    						theme:true,
        					message: '<img src="./images/busy.gif" /> 执行成功！',
        					timeout: 1000 
    			});
			} else {
				$.blockUI({ 
    					theme:true,
        				message: '<img src="./images/busy.gif" /> 执行失败！',
        				timeout: 1000 
    			});
			}
		}
	});
}
});
