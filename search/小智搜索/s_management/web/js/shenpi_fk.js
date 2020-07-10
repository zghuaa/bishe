layui.use('table', function() {
    var table = layui.table;
    table.render({
        elem: '#test'
        , url: '/ShenPiFkServlet'
        ,where:{
            "method":"searchFKui",
            "type":"fankui"
        }
        ,method:'post'
        ,contentType: 'application/json;charset=utf-8'
        , toolbar: '#toolbarDemo'
        , title: '用户数据表'
        , cols: [[
            {type: 'checkbox', fixed: 'left'}
            , {field: 'id', title: 'id', width: 120,hide:true}
            , {field: 'date', title: '提交时间', width: 200, fixed: 'left', unresize: true, sort: true}
            , {field: 'mainType', title: '部门类型', width: 200}
            , {field: 'subType', title: '业务类型', width: 200}
            , {field: 'email', title: '客户邮箱', width: 200}
            , {field: 'tijaoren', title: '提交人', width: 120}
            , {field: 'status', title: '审批状态', width: 120}
            , {title: '操作', toolbar: '#barDemo', width: 120}
        ]]
        , page: true
    });

    //头工具栏事件
    table.on('toolbar(test)', function (obj) {
        var checkStatus = table.checkStatus(obj.config.id);
        switch (obj.event) {
            case 'getCheckData':
                var data = checkStatus.data;
                layer.alert(JSON.stringify(data));
                break;
            case 'getCheckLength':
                var data = checkStatus.data;
                layer.msg('选中了：' + data.length + ' 个');
                break;
            case 'isAll':
                layer.msg(checkStatus.isAll ? '全选' : '未全选');
                break;
        }
    });

    table.on('tool(test)', function(obj){
        var  id = obj['data']['id'];
        console.log(obj['data']['id']);
       if(obj.event === 'edit'){
           localStorage.setItem("id", id);
           window.location.href="/views/show_fk.html";
        }
    });
});