
var myChart = echarts.init(document.getElementById('container'));
// 显示标题，图例和空的坐标轴
var app = {};
option = null;
option = {
    title : {
        text: '小智客服报表展示',
    },
    tooltip : {
        trigger: 'axis'
    },
    legend: {
        data:['用户点击量','用户访问量','留言量','已解决问题量','线上处理满意量']
    },
    toolbox: {
        show : true,
        feature : {
            dataView : {show: true, readOnly: false},
            magicType : {show: true, type: ['line', 'bar']},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : true,
    xAxis : [
        {
            type : 'category',
            data : ['2018年1月','2018年2月','2018年3月','2018年4月','2018年5月','2018年6月','2018年7月','2018年8月','2018年9月','2018年10月','2018年11月','2018年12月']
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        {
            name:'用户点击量',
            type:'bar',
            data:[],
            markPoint : {
                data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            markLine : {
                data : [
                    {type : 'average', name: '平均值'}
                ]
            }
        },
        {
            name:'用户访问量',
            type:'bar',
            data:[],
            markPoint : {
                data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            markLine : {
                data : [
                    {type : 'average', name : '平均值'}
                ]
            }
        },

        {
            name:'留言量',
            type:'bar',
            data:[],
            markPoint : {
                data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            markLine : {
                data : [
                    {type : 'average', name: '平均值'}
                ]
            }
        },
        {
            name:'已解决问题量',
            type:'bar',
            data:[],
            markPoint : {
                data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            markLine : {
                data : [
                    {type : 'average', name: '平均值'}
                ]
            }
        },
        {
            name:'线上处理满意量',
            type:'bar',
            data:[],
            markPoint : {
                data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            markLine : {
                data : [
                    {type : 'average', name: '平均值'}
                ]
            }
        },
    ]
};

myChart.showLoading();    //数据加载完之前先显示一段简单的loading动画

var names=[];    //类别数组（实际用来盛放X轴坐标值）
var nums=[];    //用户点击量数组（实际用来盛放Y坐标值）
var nums1=[];    //用户访问量数组
var nums2=[];    //留言量数组
var nums3=[];    //已解决问题量数组
var nums4=[];    //线上处理满意量数组
$.ajax({
    type: "post",
    async: true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
    url: "http://localhost:8080/TestServlet",    //请求发送到TestServlet处
    contentType: "application/json;charset=utf-8",
    data: "{\"aaa\":\"bbb\"}",
    dataType: "json",        //返回数据形式为json
    success: function (result) {
        //请求成功时执行该函数内容，result即为服务器返回的json对象
        if (result) {
            console.log(result);
            //挨个取出类别并填入类别数组
            var fankui = result['fankui'];
            var visit = result['visit'];
            var userVisit = result['userVisit'];
            for (var i = 0; i < 12; i++) {
                var fankuiClick = fankui['' + (i + 1) + ''];
                var visitClick = visit['' + (i + 1) + ''];
                var userVisitClick = userVisit['' + (i + 1) + ''];
                nums.push(fankuiClick);      //挨个取出用户点击量
                nums1.push(visitClick);   //挨个取出用户访问量
                nums2.push(userVisitClick);   //挨个取出留言量
                nums3.push(i);   //挨个取出已解决问题量
                nums4.push(visitClick-fankuiClick);   //挨个取出线上处理满意量
            }
            myChart.hideLoading();    //隐藏加载动画
            myChart.setOption({        //加载数据图表
                series: [
                    {
                        // 根据名字对应到相应的系列
                        name: '用户点击量',
                        data: nums
                    },
                    {
                        names: '用户访问量',
                        data: nums1
                    },
                    {
                        names: '留言量',
                        data: nums2
                    },
                    {
                        names: '已解决问题量',
                        data: nums3
                    },
                    {
                        names: '线上处理满意量',
                        data: nums4
                    }
                ]
            });
        }
    },
    error: function (errorMsg) {
        //请求失败时执行该函数
        alert("图表请求数据失败!");
        myChart.hideLoading();
    }
});

if (option && typeof option === "object") {
    myChart.setOption(option, true);
}