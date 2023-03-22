$(function () {
    $(".select2").select2();
    LoadAllArea($("#Area"))
    changeArea($("#Area"), $("#SubArea"))
    $(".datepicker").datepicker({
        format: 'yyyy-mm-dd',
        weekStart: 1,
        autoclose: true,
        todayBtn: 'linked',
        language: 'zh-CN'
    });
    LoadDatatable();

})

function LoadDatatable() {
    var columns = [
        //{
        //    title: '操作',
        //    //field: 'StoreId',
        //    formatter: operateFormatter,
        //    events: 'operateEvents'
        //},
        {
            field: '大区',
            title: '大区'
        },
        {
            field: '小区',
            title: '小区'
        },
        {
            field: '商店编码',
            title: '商店编码'
        },
        {
            field: '商店标准名称',
            title: '商店标准名称'
        },
        {
            field: '巡店督导',
            title: '巡店督导',
        }, 
        {
            field: '巡店日期',
            title: '巡店日期'
        },
        {
            field: '提交表格时间',
            title: '提交表格时间'
        },
    ];
    $.SubAjax({
        type: 'post',
        data: {
            QuestionWorkType: $("#QuestionWorkType").val(),
            QuestionName: $("#QuestionName").val(),
        },
        url: '/DataImport/ReportVisitPlan/QueryQuestionName',
        success: function (data) {
            if (data.IsSuccess) {
                $.each(data.Data, function (index, value) {
                    //var subBrandName = value.Name;
                    columns.push({
                        title: value,
                        field: value,
                    });
                })
            }
        }
    })
    $('#dataTable').bootstrapTable({
        url: "/DataImport/ReportVisitPlan/GetDataList",
        pagination: "true",
        queryParams: queryParams,
        sidePagination: 'server',
        columns: columns
    });
}

function queryParams(params) {
    return {
        AreaId: $("#Area").val(),
        SubAreaId: $("#SubArea").val(),
        //UserNo: $("#txtUserNo").val(),
        //UserName: $("#txtUserName").val(),
        StoreNo: $("#txtStoreNo").val(),
        StoreName: $("#txtStoreName").val(),
        QuestionName: $("#QuestionName").val(),
        BeginTime: $("#BeginTime").val(),
        EndTime: $("#EndTime").val(),
        UserId:$("#selPositionStatusCode").val(),
        PageSize: params.limit,   //页面大小
        PageIndex: params.pageNumber,//  sort: params.sort,  //排序列名
        sortOrder: params.order,
        SkipCount: params.offset,
    };
}
$("#btnSearch").on("click", function () {
    //$('#dataTable').bootstrapTable("refresh", { query: { SkipCount: 0 } });
    $('#dataTable').bootstrapTable("destroy");// 破坏原来的表
    LoadDatatable();
});

$(".export").on("click", function () {
    $.SubAjax({
        type: 'post',
        data: {
            AreaId: $("#Area").val(),
            SubAreaId: $("#SubArea").val(),
            UserId: $("#selPositionStatusCode").val(),
            //UserNo: $("#txtUserNo").val(),
            //UserName: $("#txtUserName").val(),
            QuestionName: $("#QuestionName").val(),
            StoreNo: $("#txtStoreNo").val(),
            StoreName: $("#txtStoreName").val(),
            BeginTime: $("#BeginTime").val(),
            EndTime: $("#EndTime").val(),
        },
        url: '/DataImport/ReportVisitPlan/ExportDataList',
        success: function (data) {
            if (data.IsSuccess) {
                window.location.href = "/File/DownFile?filePath=" + data.Data + "&fileName=巡店报表";
            }
            else {
                $.ShowMessage("error", data.Msg);
            }
        }
    })
})