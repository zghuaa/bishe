// 动态展示未被分配的人员
$(document).ready(function () {
    $.ajax({
        // 请求方式
        type:"post",
        url:"http://localhost:8080/RightServlet",
        // 同步异步
        async:true,
        dataType:"json",
        success:function (data) {
            var html="";
            for (var i=0;i<data.length;i++){
                var ls=data[i];
                var userID = ls['userID'];
                html +="<a  id=\"a\" class=\"white-mode\" href=\"right_second.html\"><img src=\"../image/a.png\">" +
                    "<span>"+ls['userName']+"</span>" +
                    "</a>"
            }
            $("#person").html(html);
            var ui=localStorage.setItem("userID",userID);
        }
    });

});

// 点击部门分类，保存部门的id，并动态展示隐藏的已分配人员的div层

function saveFirstVal(value) {
    $("#part_tab").hide();
    $("#person_tab").show();
    localStorage.setItem("department",value);
    var dpt = localStorage.getItem("department");
   // console.log(localStorage.getItem("department"));
    $.ajax({
            // 请求方式
            type: "post",
            url: "http://localhost:8080/FenPeiServlet",
            data: dpt,
            dataType: "json",
            // 同步异步
            async: true,
            success: function (data) {
                console.log(data);
                var html="";
                if (data===null) {
                    html += "暂无数据"
                } else {
                    for (var i = 0; i < data.length; i++) {
                        // var ls = eval('(' + data[i] + ')');
                        var ls = data[i];
                        var userID = ls['userID'];
                        console.log(ls);
                        html += "<li><a class=\"a\" onclick=\"c()\">" +
                            "<img src=\"../image/a.png\">" +
                            "<span>" + ls['userName'] + "</span></a>" +
                            "<ul class=\"drop menu4\">" +
                            "<li style=\"display: none\" class=\"info\">" +
                            "<a><p>普通员工姓名:" + ls['userName'] + "</p>" +
                            "<p>工号:" + ls['userID'] + "</p>" +
                            "<p>管理部门:" + ls['department'] + "</p>" +
                            "<p>管理系统:" + ls['system'] + "</p>" +
                            "<p>数据录入:" + ls['dataInsert'] + "</p>" +
                            "<p>客户回执:" + ls['huizhi'] + "</p>" +
                            "<p>客户反馈查看:" + ls['fankui'] + "</p>" +
                            "<p>是否在职:" + ls['workStatus'] + "</p>" +
                            "<p>是否已分配:" + ls['ifDistribution'] + "</p>" +
                            "<p><button class=\"white-mode\" ><a href='/views/right_second.html'>更改权限</a></button></p>" +
                            "</a></li></ul></li>"
                    }
                    $("#main").html(html);
                    var ui=localStorage.setItem("userID",userID);

                }
            }
        });
    }

function c() {
        $(".info").show();
}
function b() {
    $("#person_tab").hide();
    $("#part_tab").show();
}
//     $.ajax({
//         // 请求方式
//         type: "post",
//         url: "http://127.0.0.1:8080",
//         // 同步异步
//         async: true,
//         dataType: "json",
//         success: function (data) {
//             var html = "";
//             for (var i = 0; i < data.length; i++) {
//                 var ls = data[i];
//                 html += "<a><p>普通员工姓名:"+ls['username']+"</p>" +
//                     "<p>工号:"+ls['userID']+"</p>" +
//                     "<p>管理部门:"+ls['department']+"</p>" +
//                     "<p>管理系统:"+ls['system']+"</p>" +
//                     "<p>数据录入:"+ls['dataInsert']+"</p>" +
//                     "<p>客户回执:"+ls['huizhi']+"</p>" +
//                     "<p>客户反馈查看:"+ls['fankui']+"</p>" +
//                     "<p>客户聊天记录查看:"+ls['record']+"</p>" +
//                     "<p>人工客服:"+ls['customService']+"</p>" +
//                     "<p><button class=\"white-mode\" data-method=\"notice\">更改权限</button></p>" +
//                     "</a>"
//                }
//             $("#info").html(html);
//         }
//     });




// // 弹框的js
// layui.use('layer', function(){
//     var $ = layui.jquery, layer = layui.layer;
//
//     //触发事件
//     var active = {
//         notice: function () {
//             //示范一个公告层
//             layer.open({
//                 type: 1
//                 ,
//                 title: false //不显示标题栏
//                 ,
//                 closeBtn: false
//                 ,
//                 area: '300px;'
//                 ,
//                 shade: 0.8
//                 ,
//                 id: 'LAY_layuipro' //设定一个id，防止重复弹出
//                 ,
//                 btn: ['提交', '退出']
//                 ,
//                 btnAlign: 'c'
//                 ,
//                 moveType: 0 //拖拽模式，0或者1
//                 ,
//                 content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">' +
//                 '<div class="switch_box box_1">' +
//                 '      <input type="checkbox" class="switch_1">冻结账户' +
//                 '</div>' +
//                 '<table width="99%" border="0" align="center" style="border-bottom:1px solid #cccccc">' +
//                 '    <tr><td width="10"><select name="areaid" id="partid" onChange="setTown(\'partid\',\'systemid\')">' +
//                 '            <option value="">请选择部门</option>' +
//                 '            <option value="1">运输工具</option>' +
//                 '            <option value="2">舱单申报</option>' +
//                 '            <option value="3">货物申报</option>' +
//                 '            <option value="4">税费支付</option>' +
//                 '            <option value="5">其他业务</option>' +
//                 '            <option value="6">跨境电商</option>' +
//                 '        </select></td><br>' +
//                 '        <td  width="10"><select name="townid" id="systemid">' +
//                 '            <option value="">请选择系统</option>' +
//                 '        </select>' +
//                 '        </td>' +
//                 '    </tr>' +
//                 '</table>' +
//                 '<div class="checkbox">' +
//                 '      <label><input type="checkbox">数据录入权限</label>' +
//                 '</div>' +
//                 '<div class="checkbox">' +
//                 '      <label><input type="checkbox">客户回执权限</label>' +
//                 '</div>' +
//                 '<div class="checkbox">' +
//                 '      <label><input type="checkbox">查看反馈权限</label>' +
//                 '</div>'+
//                 '<div class="checkbox">' +
//                 '      <label><input type="checkbox">查看聊天记录</label>' +
//                 '</div>'+
//                 '<div class="checkbox">' +
//                 '      <label><input type="checkbox">人工客服权限</label>' +
//                 '</div>' +
//                 '</div>'
//                 ,
//
//                 success: function (layero) {
//                     var btn = layero.find('.layui-layer-btn');
//                     btn.find('.layui-layer-btn0').attr({
//                           href: 'right.html'
//                         , target: '_blank'
//                     });
//
//
//
//                 }
//             });
//
//         }
//
//     };
//     $('.white-mode').on('click', function () {
//         var othis = $(this), method = othis.data('method');
//         active[method] ? active[method].call(this, othis) : '';
//     });
//
//
// });

