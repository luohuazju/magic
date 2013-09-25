jQuery(document).ready(function(){ 

	  jQuery("#list").jqGrid({
	    url:'trigger.do?method=listjson',
	    datatype: "json",
	    mtype: 'POST',
	    width: 1000,
	   	colModel:[
	   	       {name:'trigger_name',label:'调度编号', width:250,editable: true,formoptions:{label:'cron表达式'}},
	   	       {name:'display_name',label:'调度名称',width:100, editable: true},
	   	       {name:'display_job_name',label:'任务名称', width:100,editable: true,edittype:"select",editoptions:{dataUrl:"trigger.do?method=jobselect&" + new Date().getTime()}},
	   	       {name:'prev_fire_time',label:'上次执行时间',width:125,editable:false},
	   	       {name:'next_fire_time',label:'下次执行时间',width:125,editable:false},
	   	       {name:'start_time',label:'开始时间',width:125,editable:false},
	   	       {name:'end_time',label:'结束时间',width:125,editable:false},
	   	       {name:'status',label:'状态',width:50,editable:false}
	   	],
	   	jsonReader : {
	        root: "rows",
	        page: "page",
	        total: "total",
	        records: "records",
	        repeatitems: false,
	        userdata: "userdata",
	        id: "trigger_name"
	    },
	    onSelectRow: function(id){ 
		    //alert(id);
		},
		onPaging: function(pgButton){
			//alert(pgButton);
		},
	   	pager : '#pager',
	   	rowNum : 5,
	    viewrecords : true,
	    sortorder : "desc", 
	    caption : "调度管理"
	  }); 

	  jQuery("#list").jqGrid('navGrid','#pager',
	  	{
		    addtext:"",
		    addtitle: "新增调度",
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
	  	     addCaption: "新增调度",
		     editCaption: "编辑调度",
		     url:"trigger.do?method=save",
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
		    url:"trigger.do?method=cmd&action=remove",
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
	  
	function doCmd(state, triggerName, triggerState) {
	// 客户端编码，服务端解码
	triggerName = encodeURIComponent(encodeURIComponent(triggerName));
	$.ajax({
				url : 'trigger.do?method=cmd&action=' + state + '&id='
						+ triggerName,
				type : 'post',
				// dataType: 'xml',
				timeout : 10000,
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

