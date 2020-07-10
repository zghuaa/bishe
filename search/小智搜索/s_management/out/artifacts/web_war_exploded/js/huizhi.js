layui.use('table', function(){
    var table = layui.table;

    table.render({
        elem: '#test'
        ,url:'https://www.layui.com/demo/table/user/?page=1&limit=30'    //路径
        ,toolbar: '#toolbarDemo'
        ,title: '客户反馈表'
        ,totalRow: true
        ,cols: [[
            {type: 'checkbox', fixed: 'left'}
            ,{field:'date', title:'反馈时间', width:120, edit: 'text',sort: true,fixed: 'left', unresize: true, totalRowText: '合计'}
            ,{field:'mainType', title:'业务类型', width:80, sort: true }
            ,{field:'email', title:'客户邮箱', width:80, sort: true }
            ,{field:'status', title:'反馈状态', width:150, edit:'text'}
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