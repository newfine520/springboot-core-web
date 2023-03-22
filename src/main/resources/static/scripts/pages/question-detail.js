$(function ()
{
    //alert($("#Qid").val());
    $(".select2").select2();
    $("#selType").on("change", function () {
        var typeCode = $("#selType").val();
        if(typeCode!=null&&typeCode!="")
        {
            if (typeCode == "800001" || typeCode == "800002") {
                $("#ChooseOption").show();
                $("#divMaxNum").hide();
                $("#divKeyboardType").hide();
                $("#selKeyboardType").val("");
                $("#MaxNum").val("");
            }
            else if (typeCode == "800005")
            {
                $("#divMaxNum").show();
                $("#divKeyboardType").show();
                
                $("#MaxNum").val("");
                $("#ChooseOption").hide();
                $("#ParamaterA").val("");
                $("#ParamaterB").val("");
                $("#ParamaterC").val("");
                $("#ParamaterD").val("");
                $("#ParamaterE").val("");
                $("#ParamaterF").val("");
            } else
            {
                $("#ChooseOption").hide();
                $("#divMaxNum").hide();
                $("#divKeyboardType").hide();
                $("#selKeyboardType").val("");
                $("#MaxNum").val("");
                $("#ChooseOption").hide();
                $("#ParamaterA").val("");
                $("#ParamaterB").val("");
                $("#ParamaterC").val("");
                $("#ParamaterD").val("");
                $("#ParamaterE").val("");
                $("#ParamaterF").val("");
            }
        }


    });
    $('#myModal').on('shown.bs.modal', function (e) {
        $(".select2").select2();
    })
    $('#dataTable').bootstrapTable({
        url: "/DataImport/Question/GetQuestionDetailList",
        pagination: "true",
        queryParams: queryParams,
        sidePagination: 'server',
        onPostBody: function () {
            RenderRoleButton();
        },
        columns: [
            
            //{
            //    title: '<input data-index="0" type="checkbox" onClick="checkAll(this)" />',
            //    formatter: function (value, row, index) {
            //        return '<input data-index="0" name="btSelectItem" type="checkbox" value="' + row.Id + '" />'
            //    }
            //},
            {
                title: '操作',
                //field: 'SubAreaId',
                formatter: 'actionFormatter',
                events: 'questionOperate',
            }, {
                field: 'Type',
                title: '类型'
            },  {
                field: 'Content',
                title: '问题项内容',
            }, {
                field: 'IsNecessary',
                title: '是否必填项',
                formatter: function (value, row, index)
                {
                    if (row.IsNecessary == true) {
                        return "是"
                    } else {
                        return "否"
                    }
                }
            }, {
                field: 'SortId',
                title: '序号',
            },

            
        ],

    });

    var uploader;
    var intw = 0;
    $('#modalImport').on('shown.bs.modal', function (e) {
        uploader = WebUploader.create({
            auto: false,
            // swf文件路径
            swf: '/Plugins/webuploader/Uploader.swf',
            // 文件接收服务端。
            //server: '/DataImport/Question/ImportQuestion?Id=' + $("#Qid").val(),
            server: '/DataImport/Question/ImportQuestion',
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#picker',
        });

        uploader.on('fileQueued', function (file) {
            $("#thelist").append('<div id="' + file.id + '" class="item">' +
                '<h4 class="info">' + file.name + '</h4>' +
                '<p class="state">请上传.....</p>' +
            '</div>');
        });
        uploader.on('uploadProgress', function (file, percentage) {
            var $li = $('#' + file.id),
                $percent = $li.find('.progress .progress-bar');
            // 避免重复创建
            if (!$percent.length) {
                $percent = $('<div class="progress progress-striped active">' +
                  '<div class="progress-bar" role="progressbar" style="width: 0%">' +
                  '</div>' +
                '</div>').appendTo($li).find('.progress-bar');
            }
            $li.find('p.state').text('上传中...');
            $percent.css('width', percentage * 100 + '%');
            intw = layer.load(0, {
                shade: [0.2, '#000'] //0.1透明度的白色背景
            });
        })
        uploader.on('uploadProgress', function (file, percentage) {
            var $li = $('#' + file.id),
                $percent = $li.find('.progress .progress-bar');

            // 避免重复创建
            if (!$percent.length) {
                $percent = $('<div class="progress progress-striped active">' +
                  '<div class="progress-bar" role="progressbar" style="width: 0%">' +
                  '</div>' +
                '</div>').appendTo($li).find('.progress-bar');
            }
            $li.find('p.state').text('上传中');
            $percent.css('width', percentage * 100 + '%');
        });
        uploader.on('uploadSuccess', function (file, response) {
            if (response.IsSuccess) {
                $.ShowMessage("success", "文件导入成功");
                $("#modalImport").modal("hide");
                $('#dataTable').bootstrapTable("refresh");
            }
            else {
                $('#thelist').empty();
                $.each(response.Data, function (index, value) {
                    $('#thelist').append("<div style='color:red'>" + value.ErrorMsg + "</div><br/>")

                })
                uploader.removeFile(file);
            }
        });
        uploader.on('uploadError', function (file) {
            $('#' + file.id).find('p.state').text('上传出错');
        });

        uploader.on('uploadComplete', function (file) {
            $('#' + file.id).find('.progress').fadeOut();
            layer.close(intw)
        });
    }).on('hidden.bs.modal', function (e) {
        uploader.destroy();
        $('#thelist').empty();
    });

    $("#ctlBtn").on("click", function () {
        uploader.upload();
    })
});
function actionFormatter(value, row, index) {
    var results = '<a class="like"onclick=questionDetail("' + row.Id + '") data-role="Admin" title="编辑">编辑</a>'
    //results += '<a class="like"onclick=deleteDetail("' + row.Id + '") data-role="Admin" title="删除"> 删除</a>'
    return results;
}
window.questionOperate = {
    'click .check': function (e, value, row, index) {
        //editArea(row.Id
        editArea(value);
        $("#btnUpdate").hide();
        $("#btnAdd").hide();
       
    }
   
}
function queryParams(params) {
    return {
        Content: $("#txtContent").val(),
        Type: $("#CategoryCode").val(),
        Qid:$("#Qid").val(),
        PageSize: params.limit,   //页面大小
        PageIndex: params.pageNumber,//  sort: params.sort,  //排序列名
        sortOrder: params.order,
        SkipCount: params.offset,
        //排位命令（desc，asc）
    };
}
var questionForm = $("#questionForm");
questionForm.validate({
    rules: {
        SortId: {
            required: true
        },
        selType: {
            required: true
        },
        Content: {
            required: true
        },
    },
    messages: {
        SortId: {
            required: "请输入排序",
        },
        selType: {
            required: "请选择问题类型",
        },
        Content: {
            required: "请输入问题内容",
        },
    }
})

function deleteDetail(Id) {
    parent.layer.confirm('确定删除该条数据吗？', {
        btn: ['确定', '取消'], //按钮
        shade: false //不显示遮罩
    }, function () {
        $.SubAjax({
            type: 'post',
            url: '/DataImport/Question/DeleteQuestion?Id=' + Id,
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

function questionDetail(Id) {
    
    $("#btnUpdate").show();
    $("#btnAdd").hide();
    $("#myModal").modal({ backdrop: 'static', keyboard: false });
    $("#questionForm").find("label.error").remove();
    $("#questionForm").find(".error").removeClass("error");
    
    //var roleName = '@(((SFA.Service.UserService.Output.UserDescriptOutput)ViewData["CurrentUser"]).RoleNameEns)';
    $.SubAjax({
        type: 'post',
        data: { Id: Id },
        url: '/DataImport/Question/GetQuestionDetail',
        success: function (data) {
            if (data.IsSuccess) {
                data = data.Data;
                $("#QuestionId").val(Id)
                $("#SortId").val(data.SortId);
                $("#Content").val(data.Content);
                $("#selType").val(data.Type);
                if (data.IsNecessary) {
                    $("#IsNecessary1").prop("checked", true);
                    $("#IsNecessary2").prop("checked", false);
                } else {
                    $("#IsNecessary2").prop("checked", true);
                    $("#IsNecessary1").prop("checked", false);

                    //$("#divReason").hide();
                }
                if (data.Type == "800001" || data.Type == "800002") {
                    $("#ChooseOption").show();
                    $("#divMaxNum").hide();
                    $("#divKeyboardType").hide();
                    $("#MaxNum").val("");
                    $("#selKeyboardType").val("");
                    $("#ParamaterA").val(data.ParamaterA);
                    $("#ParamaterB").val(data.ParamaterB);
                    $("#ParamaterC").val(data.ParamaterC);
                    $("#ParamaterD").val(data.ParamaterD);
                    $("#ParamaterE").val(data.ParamaterE);
                    $("#ParamaterF").val(data.ParamaterF);
                } else if (data.Type == "800005")
                {
                    $("#divMaxNum").show();
                    $("#divKeyboardType").show();

                    $("#MaxNum").val(data.MaxNum);
                    $("#selKeyboardType").val(data.KeyboardType);

                    $("#ChooseOption").hide();
                    $("#ParamaterA").val("");
                    $("#ParamaterB").val("");
                    $("#ParamaterC").val("");
                    $("#ParamaterD").val("");
                    $("#ParamaterE").val("");
                    $("#ParamaterF").val("");

                }

                else {
                    $("#divMaxNum").hide();
                    $("#divKeyboardType").hide();
                    $("#MaxNum").val("");
                    $("#selKeyboardType").val("");
                    $("#ChooseOption").hide();
                    $("#ParamaterA").val("");
                    $("#ParamaterB").val("");
                    $("#ParamaterC").val("");
                    $("#ParamaterD").val("");
                    $("#ParamaterE").val("");
                    $("#ParamaterF").val("");
                }
                $(".select2").select2();
            }
            else {
                $.ShowMessage("error", data.Msg)
            }

        }
    });
}

$("#btnUpdate").on("click", function () {
    var isValue = $("input[name='IsNecessarys']:checked").val();
    $("input[name='IsNecessary']").val(isValue)
    var Qid = $("#QuestionId").val();
    if (questionForm.valid()) {
        $.SubAjax({
            type: 'post',
            url: '/DataImport/Question/UpdateQuestion?Id=' + Qid,
            data: questionForm.serializeToJson(),
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

$("#btnSearch").on("click", function () {
    $('#dataTable').bootstrapTable("refresh");
});

$("#btnAddShow").on("click", function () {
    $("#btnUpdate").hide();
    $("#btnAdd").show();
    $("#myModal").modal({ backdrop: 'static', keyboard: false });
    $("#questionForm").find(".form-control").val('');
    $("#questionForm").find(".select2").val('');
    $(".select2").select2();
    $("#IsNecessary2").prop("checked", false);
    $("#IsNecessary1").prop("checked", false);
    $("#ChooseOption").hide();
    $("#divMaxNum").hide();
    $("#divKeyboardType").hide();
    
});

$("#btnAdd").on("click", function () {
    $("#QlistId").val($("#Qid").val());
    QlistId = $("#Qid").val();
    //$("#selType").html("请选择");
    if (questionForm.valid()) {
        $.SubAjax({
            type: 'post',
            url: '/DataImport/Question/AddQuestion?QlistId=' + QlistId,
            data: questionForm.serializeToJson(),
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