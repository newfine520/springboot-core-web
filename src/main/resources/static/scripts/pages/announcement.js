$(function () {
    //$.ajax({ type: "OPTIONS", url: "/", complete: function (x) { alert(x.getResponseHeader("Date")) } })
    $(".select2").select2();

    $(".datetimepicker").datetimepicker({
    language: 'zh-CN',
    todayBtn: 1,
    pickerPosition: "bottom-left",
    minuteStep: 10,
    format: 'yyyy-mm-dd hh:ii',
    autoclose: true,
    });
    $('#dataTable').bootstrapTable({
        url: "/DataImport/Announcement/GetAnnouncementList",
        pagination: "true",
        queryParams: queryParams,
        sidePagination: 'server',
        clickToSelect: true,
        onPostBody: function () {
            RenderRoleButton();
        },
        columns: [
            {
                title: '操作',
                field: 'Id',
                formatter: 'actionFormatter',
                //events: 'Operate',
            },
            {
                field: 'Title',
                title: '公告标题'
            }, {
                field: 'Content',
                title: '公告内容',
                formatter: 'substr'
            }, {
                field: 'Range',
                title: '公告范围'
            }, {
                field: 'StrBeginTime',
                title: '开始时间',
            },{
                field: 'StrEndTime',
                title: '结束时间',
            }
        ],
    });
})
function substr(value, row, index) {
    if (row.Content != null) {
        var leng = row.Content;
        if (row.Content.length > 15) {
            leng = row.Content.substr(0, 15) + '...'
        }
        return ' <span data-toggle="tooltip" data-placement="bottom"   title="' + row.Content + '" class="tooltip-show" style="cursor:default">' + leng + '</span>';
    }
}
function queryParams(params) {
    return {
        Title: $("#txtTitle").val(),
        PageSize: params.limit,   //页面大小
        PageIndex: params.pageNumber,//  sort: params.sort,  //排序列名
        sortOrder: params.order,
        SkipCount: params.offset,
        //排位命令（desc，asc）
    };
}
$("#btnSearch").on("click", function () {
    $('#dataTable').bootstrapTable("refresh");
});
function actionFormatter(value, row, index) {
    var results = '<a class="like"onclick=editAnnouncement("' + value + '") data-role="Admin" title="编辑">编辑</a>';
    results += '<a class="delete" onclick=deleteAnnounce("' + value + '") data-role="Admin" title="删除"> 删除</a>';
    //results += '<a class="check ml10" href="javascript:void(0)"title="查看"> 查看</a>';
    return results;
}
function editAnnouncement(Id) {
    $("#btnUpdate").show();
    $("#btnAdd").hide();
    $("#myModal").modal({ backdrop: 'static', keyboard: false });
    $("#announcementForm").find("label.error").remove();
    $("#announcementForm").find("input.error").removeClass("error");
    $.SubAjax({
        type: 'post',
        data: { Id: Id },
        url: '/DataImport/Announcement/GetAnnouncementDetail',
        success: function (data) {
            if (data.IsSuccess) {
                data = data.Data;
                $("#AnnouncementId").val(Id);
                $("#Title").val(data.Title);
                $("#Range").val(data.Ranges);
                $("#Content").val(data.Content);
                $("#BeginTime").val(data.StrBeginTime);
                $("#EndTime").val(data.StrEndTime);
                $(".select2").select2();
            }
            else {
                $.ShowMessage("error", data.Msg)
            }
        }
    });
}
var announcementForm = $("#announcementForm");
announcementForm.validate({
    rules: {

        Title: {
            required: true
        },
        Content: {
            required: true
        },
        BeginTime: {
            required: true
        },
        EndTime: {
            required: true
        },
    },
    messages: {

        Title: {
            required: "请输入公告标题",
        },
        Content: {
            required: "请输入公告内容",
        },
        BeginTime: {
            required: "请输入开始时间",
        },
        EndTime: {
            required: "请输入结束时间",
        },
    }
})
$("#btnUpdate").on("click", function () {
    var start = $("#BeginTime").val();
    var end = $("#EndTime").val();
    $("#Ranges").val($("#Range").val());
    if (start >= end) {
        $.ShowMessage("warning", "结束时间必须大于开始时间");
        return false;
    }
    var Id = $("#AnnouncementId").val();
    if (announcementForm.valid()) {
        $.SubAjax({
            type: 'post',
            url: '/DataImport/Announcement/UpdateAnnouncement?Id=' + Id,
            data: announcementForm.serializeToJson(),
            success: function (data) {
                if (data.IsSuccess) {
                    $.ShowMessage("success", data.Msg);
                    $("#myModal").modal("hide");
                    $('#dataTable').bootstrapTable("refresh");
                }
                else {
                    $.ShowMessage("error", data.Msg)
                }
            }
        });
    }
});

$("#btnAddShow").on("click", function () {
    $("#btnUpdate").hide();
    $("#btnAdd").show();
    $("#myModal").modal({ backdrop: 'static', keyboard: false });
    $("#announcementForm").find(".form-control").val('');
    //重置select
    $("#announcementForm").find(".select2").val('');
    $("#announcementForm").find(".select2").select2();

});

$("#btnAdd").on("click", function () {
    var start = $("#BeginTime").val();
    var end = $("#EndTime").val();
    $("#Ranges").val($("#Range").val());
    if (start >= end) {
        $.ShowMessage("warning", "结束时间必须大于开始时间");
        return false;
    }
    if (announcementForm.valid()) {
        $.SubAjax({
            type: 'post',
            url: '/DataImport/Announcement/Addannouncement',
            data: announcementForm.serializeToJson(),
            success: function (data) {
                if (data.IsSuccess) {
                    $.ShowMessage("success", data.Msg);
                    $("#myModal").modal("hide");
                    $('#dataTable').bootstrapTable("refresh");
                }
                else {
                    $.ShowMessage("error", data.Msg)
                }
            }
        });
    }
});


function deleteAnnounce(Id) {
    parent.layer.confirm('确定删除该条数据吗？', {
        btn: ['确定', '取消'], //按钮
        shade: false //不显示遮罩
    }, function () {
        $.SubAjax({
            type: 'post',
            url: '/DataImport/Announcement/DeleteAnnouncement',
            data: { Id: Id },
            success: function (data) {
                if (data.IsSuccess) {
                    $.ShowMessage("success", data.Msg);
                    $('#dataTable').bootstrapTable("refresh");
                }
                else {
                    $.ShowMessage("error", data.Msg)
                }
                layer.closeAll('dialog');
            }
        });
    }, function () {
    })
}
