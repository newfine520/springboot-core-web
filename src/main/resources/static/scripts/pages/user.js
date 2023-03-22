
$(function () {

    $(".select2").select2();
    $('#dataTable').bootstrapTable({
        url: "/System/User/GetUserList",
        pagination: "true",
        queryParams: queryParams,
        sidePagination: 'server',
        onPostBody: function () {
            RenderRoleButton(); 
        },
        columns: [
            {
                title: '操作',
                field: 'UserId',
                width: 300,
                formatter: 'actionFormatter',
                events: 'userLeaveOperate'
            }, {
                field: 'UserName',
                title: '姓名',
                sortable: "true"
            }, {
                field: 'Mobile',
                title: '手机号'
            }, {
            //    field: 'PartentName',
            //    title: '上级'
            //},
            //{
                field: 'PositionStatus',
                title: '在职状态'
            },
            
        ]
    });
    $('#dataTable').bootstrapTable("checkAll");

    $("#btnSearch").on("click", function () {
        $('#dataTable').bootstrapTable("refresh");
    })
    var uploader;
    var intw = 0;
    $('#modalImport').on('shown.bs.modal', function (e) {
        uploader = WebUploader.create({
            auto: false,
            // swf文件路径
            swf: '/Plugins/webuploader/Uploader.swf',
            // 文件接收服务端。
            server: '/System/User/ImportUser',
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#picker',
            accept: {
                title: 'excel',
                extensions: 'xlsx,xls',
                mimeTypes: 'application/*'
            },
            fileNumLimit: 1
        });
        uploader.on('fileQueued', function (file) {
            $("#thelist").empty();
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

        uploader.on('uploadError', function (file, reason) {
            $('#' + file.id).find('p.state').text('上传出错:原因' + reason);
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

    //弹出替换上级
    $("#btnUpdataParent").on("click", function () {
        var userIds = "";
        var userNames = "";
        $("[name='btSelectItem']").each(function () {
            if ($(this).is(":checked")) {
                userIds += $(this).val() + ",";
                userNames += $(this).parent().parent().find("td").eq(1).html() + ",";
            }
        })
        if (userIds == "") {
            $.ShowMessage("warning", "请选择人员！");
            return;
        }
        $("#AllUser").html(userNames);
        $("#UserIds").val(userIds)
        $("#modalChief").modal({ backdrop: 'static', keyboard: false });
    })

    //替换上级操作
    $("#btnUpdateChief").on("click", function () {
        $.SubAjax({
            type: 'post',
            data: { UserIds: $("#UserIds").val(), CheifUserId: $("#selUserChief").val() },
            url: '/System/User/UpdateChief',
            success: function (data) {
                if (data.IsSuccess) {
                    $.ShowMessage("success", "更新成功！");
                    $("#modalChief").modal("hide");
                    $('#dataTable').bootstrapTable("refresh");
                }
                else {
                    $.ShowMessage("error", data.Msg)
                }
            }
        });
    })

    $('#UserPositionModal').on('shown.bs.modal', function (e) {
        var userId = $("#UserPositionId").val();
        $('#dataTableBrand').bootstrapTable({
            url: "/System/User/GetPosition",
            pagination: "true",
            queryParams: queryParamsBrand,
            sidePagination: 'server',
            clickToSelect: true,
            columns: [
                {
                    field: 'brandName',
                    title: '品牌名称'
                },
                {
                    field: 'userNo',
                    title: '人员编号'
                },
                {
                    field: 'userPosition',
                    title: '人员职位'
                }
            ]
        });

        function queryParamsBrand(params) {
            return {
                UserId: userId,
                PageSize: params.limit,
                PageIndex: params.pageNumber,
                sortOrder: params.order,
                SkipCount: params.offset,
            };
        }
    }).on('hidden.bs.modal', function (e) {
        $("#UserPositionId").val("");
        $('#dataTableBrand').bootstrapTable('destroy');
    });
})

function actionFormatter(value, row, index) {
    var results = '<a class="like"onclick=editUser("' + value + '") data-role="Admin" title="编辑">编辑</a>';
    results += '<a class="check ml10" href="javascript:void(0)"title="查看"> 查看</a>';
    //if (row.PositionStatus != "离职") {
    //    results += '<a class="leave ml10" href="javascript:void(0)"data-role="Admin" title="离职"> 离职</a>'
    //}
    //if (row.PositionStatus == "待入职") {
    //    results += '<a class="entry ml10" href="javascript:void(0)"data-role="Admin" title="确认入职"> 确认入职</a>'
    //}
    results += '<a class="resetPassword ml10" href="javascript:void(0)"data-role="Admin" title="重置密码"> 重置密码</a>';
    results += '<a class="assem ml10" href="javascript:void(0)" data-role="Admin" title="套件权限"> 套件权限</a>';
    results += '<a class="position ml10" href="javascript:void(0)" data-role="Admin" title="职位信息"> 职位信息</a>';
    return results;
}

window.userLeaveOperate = {
  
    'click .resetPassword': function (e, value, row, index) {
        ResetPassword(row.UserId)
    }, 'click .check': function (e, value, row, index) {
        editUser(row.UserId);
        $("#btnUpdate").hide();
        $("#UserForm").find("input").prop("disabled", true);
        $("#UserForm").find("select").prop("disabled", true);
        $("#UserForm").find("textarea").prop("disabled", true);
        $("#btnAdd").hide();
    }, 'click .assem': function (e, value, row, index) {
        editAssem(row);
    }, 'click .position': function (e, value, row, index) {
        $("#UserPositionId").val(row.UserId);
        $("#UserPositionModal").modal({ backdrop: 'static', keyboard: false });
    }
}



var userForm = $("#UserForm");
userForm.validate({
    rules: {
        UserName: {
            required: true
        },
        UserNo: {
            required: true
        },
        PositionType:
        {
            required: true
        },
        Mobile:
        {
            required: true
        },
        PositionStatus:
        {
            required: true
        }       
    },
    messages: {
        UserName: {
            required: "请输入姓名",
        },
        UserNo: {
            required: "请输入工号",
        },
        PositionType:
        {
            required: "请输入职位",
        },
        Mobile:
        {
            required: "请输入手机号",
        },
        PositionStatus:
        {
            required: "请输入在职状态",
        }
    }
})

function queryParams(params) {
    return {
        UserName: $("#txtUserName").val(),
        UserNo: $("#txtUserNo").val(),
        UserMobile: $("#txtUserMobile").val(),
        PositionCode: $("#selPosition").val(),
        PositionStatusCode: $("#selPositionStatusCode").val(),
        Dispatch: $("#selDispatch").val(),
        DepartmentId: $("#txtDepartmentId").val(),
        ParentId: $("#selSuperior").val(),
        PageSize: params.limit,   //页面大小
        PageIndex: params.pageNumber,//  sort: params.sort,  //排序列名
        sortOrder: params.order,
        SkipCount: params.offset,
        //排位命令（desc，asc）
    };
}

$("#btnAddShow").on("click", function () {
    $("input[name='ProvinceCode']").val("")
    $("input[name='CityCode']").val("")
    $("input[name='AreaCode']").val("")
    $("input[name='SubAreaCode']").val("")
    $("#btnAdd").show();
    $("#btnUpdate").hide();
    $('#myModal').modal({ backdrop: 'static', keyboard: false });
    $("#UserForm").find(".form-control").val('');
    $("#UserForm").find(".select2").val('');
    $("#UserForm").find(".select2").select2();
    $("#EntryDateA2").datepicker({ format: 'yyyy-mm-dd', autoclose: true });
    $("#LeaveDate").datepicker({ format: 'yyyy-mm-dd', autoclose: true });
    $("#BirthDate").datepicker({ format: 'yyyy-mm-dd', autoclose: true });
    $("#UserForm").find("input").prop("disabled", false);
    $("#UserForm").find("select").prop("disabled", false);
    $("#UserForm").find("textarea").prop("disabled", false);

})
$('#myModal').on('shown.bs.modal', function (e) {
    $(".select2").select2();
})


$("#btnAdd").on("click", function () {
    $("input[name='ProvinceCode']").val($("#ProvinceCode").val())
    $("input[name='CityCode']").val($("#CityCode").val())
    $("input[name='AreaCode']").val($("#Area").val())
    $("input[name='SubAreaCode']").val($("#SubArea").val())
    if (userForm.valid()) {
        $.SubAjax({
            type: 'post',
            url: '/System/User/AddUser',
            data: userForm.serializeToJson(),
            success: function (data) {
                if (data.IsSuccess) {
                    $.ShowMessage("success", data.Msg);                    
                    $('#dataTable').bootstrapTable("refresh");
                    $("#myModal").modal("hide");
                }
                else {
                    $.ShowMessage("error", data.Msg)
                }
            }
        });
    }
})
$("#btnUpdate").on("click", function () {
    $("input[name='ProvinceCode']").val($("#ProvinceCode").val())
    $("input[name='CityCode']").val($("#CityCode").val())
    $("input[name='AreaCode']").val($("#Area").val())
    $("input[name='SubAreaCode']").val($("#SubArea").val())
    if (userForm.valid()) {
        $.SubAjax({
            type: 'post',
            url: '/System/User/UpdateUser',
            data: userForm.serializeToJson(),
            dataType: "json",
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
})

$("#btnExport").on("click", function () {
    $.SubAjax({
        type: 'post',
        data: {
            UserName: $("#txtUserName").val(),
            UserNo: $("#txtUserNo").val(),
            PositionCode: $("#selPosition").val(),
            PositionStatusCode: $("#selPositionStatusCode").val(),
            Dispatch: $("#selDispatch").val(),
            DepartmentId: $("#txtDepartmentId").val(),
            ParentId: $("#selSuperior").val(),
        },
        url: '/System/User/ExportUser',
        success: function (data) {
            if (data.IsSuccess) { 
                window.location.href = "/File/DownFile?filePath=" + data.Data + "&fileName=用户列表";
            }
            else {
                $.ShowMessage("error", data.Msg)
            } 
        }
    });
})
//$("#PositionType").change(function () {
//    if ($(this).val() == '5000410') {
//        $("#ParentId").val("");
//        $("#ParentId").select2();
//        $("#ParentId").prop("disabled", true);
//    }
//    else {
//        $("#ParentId").prop("disabled", false);
//    }
//})

function editUser(userId) {
    $("#UserForm").find("input").prop("disabled", false);
    $("#UserForm").find("select").prop("disabled", false);
    $("#UserForm").find("textarea").prop("disabled", false);
    $("#btnUpdate").show();
    $("#btnAdd").hide();
    $("#PositionStatus").val("")
    $("#myModal").modal({ backdrop: 'static', keyboard: false });
    $("#EntryDateA2").datepicker({ format: 'yyyy-mm-dd', autoclose: true });
    $("#LeaveDate").datepicker({ format: 'yyyy-mm-dd', autoclose: true });
    $("#BirthDate").datepicker({ format: 'yyyy-mm-dd', autoclose: true });
    $.SubAjax({
        type: 'post',
        data: { userId: userId },
        url: '/System/User/GetUserDetail',
        success: function (data) {
            if (data.IsSuccess) {
                data = data.Data;
                $("#UserId").val(userId);
                $("#UserName").val(data.UserName);
                //$("#Department").val(data.DepartmentName);
                //$("#DepartmentId").val(data.DepartmentId);
                $("#UserNo").val(data.UserNo);
                $("#PositionId").val(data.PositionId);
                $("#PositionType").val(data.PositionType);
                $("#PositionStatus").val(data.PositionStatusCode);
                $("#WorkArea").val(data.WorkArea);
                $("#IdCard").val(data.IdCard);
                //$("#ParentId").prop("disabled", true);
                
                //if (data.PositionType == '5000410') {
                //    $("#ParentId").prop("disabled", true);
                //    $("#ParentId").val("")
                //}
                //else {
                //    $("#ParentId").prop("disabled", false);
                //}
                $("#SexCode").val(data.SexCode);
                //$("#ProvinceCode").val(data.ProvinceCodes);
                //$("#Area").val(data.AreaCodes);
                $("#EntryDateA2").val(data.EntryDateA2);
                $("#LeaveDate").val(data.StrLeaveDate);
                //$("#EntryDateUC").val(data.EntryDateUC);
                //$("#Rank").val(data.Rank);
                //$("#DispatchCode").val(data.DispatchCode);
                $("#Address").val(data.Address);
                $("#Remark").val(data.Remark);
                $("#Mobile").val(data.Mobile)
                //$("#ParentId").val(data.ParentId);
                $("#Email").val(data.Email);
                //$("#SysItems").val(data.SysItems);
                //if (data.ProvinceCode != "" && data.ProvinceCode != null) {
                //    getCityByProvince($("#ProvinceCode"), $("#CityCode"))
                //}
                //$("#CityCode").val(data.CityCodes);
                //if (data.AreaCodes != "" && data.AreaCodes != null) {
                //    getSubAreaByAreas($("#Area"), $("#SubArea"))
                //}
                //$("#SubArea").val(data.SubAreaCodes);
                $(".select2").select2();

            }
            else {
                $.ShowMessage("error", data.Msg)
            }

        }
    });
}

function checkAll($this) {
    var _this = $($this);
    if (_this.is(":checked")) {
        $('#dataTable').bootstrapTable("checkAll");

    }
    else {
        $('#dataTable').bootstrapTable("uncheckAll");
        $("#UserIds").val('')
    }
};

//$("#btnOk").on("click", function () {
//    if ($("#txtLeaveDate").val() == "") {
//        $.ShowMessage("warning", "请选择离职日期");
//        return;
//    }
//    $.SubAjax({
//        type: 'post',
//        url: '/System/User/UserLeave',
//        data: { UserId: $("#leaveUseId").val(), LeaveTime: $("#txtLeaveDate").val(), LeaveTimePropose: $("#txtLeaveTimePropose").val() },
//        success: function (data) {
//            if (data.IsSuccess) {
//                $.ShowMessage("success", data.Msg);
//                //$("#modalLeave").modal("hide");
//                $('#dataTable').bootstrapTable("refresh");
//            }
//            else {
//                $.ShowMessage("error", data.Msg)
//            }
//        }
//    });
//})
//function UserEntry(userId) {
//    $("#btnOkEntry").on("click", function () {
//        if ($("#txtEntryDate").val() == "") {
//            $.ShowMessage("warning", "请选择入职日期");
//            return;
//        }
//        $.SubAjax({
//            type: 'post',
//            url: '/System/User/UserEntry',
//            data: { UserId: userId, EntryTime: $("#txtEntryDate").val() },
//            success: function (data) {
//                if (data.IsSuccess) {
//                    $.ShowMessage("success", data.Msg);
//                    //$("#modalLeave").modal("hide");
//                    $('#dataTable').bootstrapTable("refresh");
//                }
//                else {
//                    $.ShowMessage("error", data.Msg)
//                }
//            }
//        });
//    })
//}
function ResetPassword(userId) {
    parent.layer.confirm('确定重置该用户的密码么？', {
        btn: ['确定', '取消'], //按钮
        shade: false //不显示遮罩
    }, function () {
        $.SubAjax({
            type: 'post',
            url: '/System/User/ResetPassword',
            data: { UserId: userId },
            success: function (data) {
                if (data.IsSuccess) {
                    $.ShowMessage("success", data.Msg);
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
function editAssem(row) {

    var setting = {
        check: {
            enable: true,

        },
        view: {
            showIcon: false,
            showLine: false,

        },
        data: {
            simpleData: {
                enable: true
            }
        }

    };
    
    $.SubAjax({
        type: 'post',
        data: {  userId: row.UserId},
        url: '/System/User/GetAssem',
        success: function (data) {
            if (data.IsSuccess) {
                var ss = data.Data;
                
                var cData = JSON.parse(ss);
                zNodes = cData;
                $.fn.zTree.init($("#tree"), setting, zNodes);
                $("#uaUserId").val(row.UserId);
                $("#lblName").val(row.UserName);
                $("#myAssem").modal({ backdrop: 'static', keyboard: false });

            }
            else {
                $.ShowMessage("error", data.Msg)
            }
        }
    });

}

$("#btnAssUpdate").on("click", function () {

    var treeObj = $.fn.zTree.getZTreeObj("tree");
    var nodes = treeObj.getCheckedNodes(true);
    var str = JSON.stringify(nodes);
    var userId = $("#uaUserId").val();
    var arr= new Array();
    for (var i = 0; i < nodes.length; i++) {
        if (nodes[i].pId != null) {
            arr.push(nodes[i].id);
        }
    } 
    if (arr.length == 0){
        arr.push(0);
    }
    $.SubAjax({
        type: 'post',
        data: { list: arr, userId: userId },
        url: '/System/User/UpdateAssem',
        success: function (data) {
            if (data.IsSuccess) {
                $.ShowMessage("success", data.Msg);
                $("#myAssem").modal("hide");
            }
            else {
                $.ShowMessage("error", data.Msg)
            }
            layer.closeAll('dialog');
        }
    });
})

