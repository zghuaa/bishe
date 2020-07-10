layui.use('table', function(){
    var table = layui.table;

    table.render({
        elem: '#test'
        ,url:'http://localhost:8080/CommonInfoServlet'    //路径
        ,toolbar: '#toolbarDemo'
        ,title: '普通管理员数据表'
        ,totalRow: true
        ,cols: [[
            {type: 'checkbox', fixed: 'left'}
            ,{field:'userID', title:'工号', width:80, fixed: 'left', unresize: true, sort: true, totalRowText: '合计'}
            ,{field:'userName', title:'姓名', width:120, edit: 'text',sort: true}
            ,{field:'nickName', title:'昵称', width:150, edit: 'text'}
            ,{field:'passWord', title:'密码', width:80, edit:'text'}
        ]]
        ,page: true
    });

    //工具栏事件
    table.on('toolbar(test)', function(obj){
        var checkStatus = table.checkStatus(obj.config.id);
        switch(obj.event){
            case 'getCheckData':
                var data = checkStatus.data;
                layer.alert(JSON.stringify(data));
                break;
            case 'getCheckLength':
                var data = checkStatus.data;
                layer.msg('选中了：'+ data.length + ' 个');
                break;
            case 'isAll':
                layer.msg(checkStatus.isAll ? '全选': '未全选')
                break;
        };
    });
});