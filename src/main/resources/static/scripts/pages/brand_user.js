$(function () {
    $('#brandId').select2();
    $('#assemId').select2();
    $('#dataTable').bootstrapTable({
        url: "/System/BrandUser/GetDataList",
        pagination: "true",
        queryParams: queryParams,
        sidePagination: 'server',
        onPostBody: function () {
            RenderRoleButton();
        },
        columns: [
            {
            //    title: '操作',
            //    field: 'UserId',
            //    width: 300,
            //    formatter: 'actionFormatter',
            //    events: 'userLeaveOperate'
            //}, {
                field: 'CompanyName',
                title: '品牌名称',
                sortable: "true"
            }, {
                field: 'AssemName',
                title: '套件名称'
            }, {
                field: 'UserNo',
                title: '人员编号'
            }, {
                field: 'UserName',
                title: '人员姓名'
            }, {
                field: 'UserMobile',
                title: '人员电话'
            }, {
                field: 'UserPostion',
                title: '人员职位'
            }

        ]
    });


    $('#brandId').change(function () {
       

        loadAssem();

    })

})

function queryParams(params) {
    return {
        BrandId: $("#brandId").val(),
        AssemId: $("#assemId").val(),
        UserName: $("#userName").val(),
        UserMobile: $("#userMobile").val(),
        PageSize: params.limit,   //页面大小
        PageIndex: params.pageNumber,//  sort: params.sort,  //排序列名
        sortOrder: params.order,
        SkipCount: params.offset,
    };
}
$("#btnSearch").on("click", function () {
    $('#dataTable').bootstrapTable("refresh");
})

$("#btnExport").on("click", function () {
    $.SubAjax({
        type: 'post',
        data: {
            BrandId: $("#brandId").val(),
            AssemId: $("#assemId").val(),
            UserName: $("#userName").val(),
            UserMobile: $("#userMobile").val(),
        },
        url: '/System/BrandUser/ExportData',
        success: function (data) {
            if (data.IsSuccess) {
                window.location.href = "/File/DownFile?filePath=" + data.Data + "&fileName=人员品牌列表";
            }
            else {
                $.ShowMessage("error", data.Msg)
            }
        }
    });
})



function loadAssem() {
    $.ajax({
        type: "post",
        url: "/System/BrandUser/GetAssem",
        dataType: "json",
        data: { bId: $("#brandId").val() },
        cache: false,
        async: false,
        success: function (data) {
            var jsonData = eval(data.Data);
            var thisData = "[";
            $.each(jsonData, function (index, item) {
                thisData += "{\"id\":\"" + item.assemId + "\",\"text\":\"" + item.assemName + "\"},";
            });
            thisData = thisData.substring(0, thisData.length - 1);
            thisData += "]";
            var cData = $.parseJSON(thisData);
            $("#assemId").empty();
            $("#assemId").select2({
                placeholder: "请选择",
                allowClear: true,
                data: cData
            });
        },
        error: function (data) {
            //  alert("数据加载失败！");
        }
    });
}