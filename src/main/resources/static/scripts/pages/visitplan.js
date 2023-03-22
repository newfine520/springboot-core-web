﻿var visitPlanForm = $("#visitPlanForm");
visitPlanForm.validate({
    rules: {
        SelSupervisorId: {
            required: true
        },
        DateTime: {
            required: true
        }, TaskTypeCode: {
            required: true
        }
    },
    messages: {
        SelSupervisorId: {
            required: "请选择"
        },
        DateTime: {
            required: "请选择"
        },
        TaskTypeCode: {
            required: "请选择"
        }
    }
})

$(function () {
    //BindDptTree("menuContent", "treeDemo", "txtDepartment");
    //BindDptTree("treeContent", "tree", "Department");
    loadAllProvince($("#ProvinceCode"));
    $('#btnSubmit').on('click', function () {
        var startHH = Number($("#StartHH").val());
        var endHH = Number($("#EndHH").val());
        var StartSS = Number($("#StartSS").val());
        var EndSS = Number($("#EndSS").val());
        if (startHH > endHH) {
            $.ShowMessage("warning", "结束时间必须大于开始时间");
            return false;
        }
        if (startHH == endHH && StartSS >= EndSS) {
            $.ShowMessage("warning", "结束时间必须大于开始时间");
            return false;
        }
        if ($("#TaskTypeCode").val() == '940' && ($("#StoreId").val() == null || $("#StoreId").val() == "" || $("#StoreId").val() == "请选择"))
        {
            $.ShowMessage("warning", "请选择门店");
            return false;

        }
        if (visitPlanForm.valid()) {
            $.SubAjax({
                type: 'post',
                data: visitPlanForm.serializeToJson(),
                url: '/System/VisitPlan/Create',
                success: function (data) {
                    if (data.IsSuccess) {
                        $.ShowMessage("success", '添加成功');

                        $('#dataTable').bootstrapTable("refresh");
                    }
                    else {
                        $.ShowMessage("error", data.Msg)
                        return false;
                    }
                }
            });
        } else {
            //  $.ShowMessage("error", '表单验证失败');
            return false;
        }

    });
    var allStore = [];
    var allSuperVision = [];
    $("#TaskDateSta").val(getTimeMon());
    $("#TaskDateEnd").val(getTimeSun());
    $("#DateTime").datepicker({
        format: "yyyy-mm-dd",
        startDate: new Date(),
        autoclose: true,
        minView: "month",
        maxView: "decade",
        todayBtn: "linked",
        pickerPosition: "bottom-left"
    }).on("click", function (ev) {
        // $("#DateTime").datepicker("setStartDate", GetNexMon()).datepicker("setEndDate", GetNexNextMon());
    });
    $('#btnCreate').on('click', function () {
        $("#btnSubmit").show();
        $("#btnUpdate").hide();
        $('#modalCreate').modal({ backdrop: 'static', keyboard: false });
        //$('divPlanAddress').hide();
        //$('divStore').hide();
        //$('divTaskDetailTypeCode').hide();
        $(".select2").val('');
        $("#DateTime").val("");
    });
    $(".select2").select2();


    $('#modalCreate').on('shown.bs.modal', function (e) {
        $(".select2").select2();
    });


    $("#SelSupervisorId").on("change", function () {
        getAllStore();
    });

    $("#TaskTypeCode").on("change", function () {
        var taskTypeCode = $(this).val();
        $("#txtAddress").val('');
        //$("#CityId").val('');
        //$("#CityId").select2();
        if (taskTypeCode != "" && taskTypeCode != null) {
            if (taskTypeCode == "940") {
                $("#divTaskDetailTypeCode").hide();
                $("#divStore").show();
                getAllStore();
                $("#txtAddress").prop("disabled", true);
                $("#divPlanType").hide();
                $("#divPlanAddress").show();
                //$("#divPlanAddressCode").hide();
            }
            else {
                $("#divStore").hide();
                $("#divPlanType").show();
                $("#txtAddress").prop("disabled", false);
                $("#divTaskDetailTypeCode").show();
                // $("#divPlanAddress").hide();
                //$("#divPlanAddressCode").show();
                $.ajax({
                    type: "post",
                    data: { typeCode: taskTypeCode },
                    url: "/Common/GetParameterByType",
                    dataType: "json",
                    cache: false,
                    success: function (data) {
                        var jsonData = eval(data.Data);
                        var thisData = "[";
                        $.each(jsonData, function (index, item) {
                            if (index != jsonData.length - 1) {
                                thisData += "{\"id\":\"" + item.ParameterCode + "\",\"text\":\"" + item.ParameterValue + "\"},";
                            } else {
                                thisData += "{\"id\":\"" + item.ParameterCode + "\",\"text\":\"" + item.ParameterValue + "\"}";
                            }
                        });
                        thisData += "]";
                        var cData = $.parseJSON(thisData);
                        $("#TaskDetailTypeCode").empty();
                        $("#TaskDetailTypeCode").select2({
                            placeholder: "请选择",
                            allowClear: true,
                            data: cData
                        });
                    }
                })


            }
            $(".select2").select2();
        }
        else {
            $("#TaskDetailTypeCode").empty();
            $("#TaskDetailTypeCode").select2({
                placeholder: "请选择",
                data: []
            });
        }
    })
    //$("#selTaskTypeCode").on("change", function () {
    //    var taskTypeCode = $(this).val();
    //    if (taskTypeCode != "" && taskTypeCode != null) {

    //        $.ajax({
    //            type: "post",
    //            data: { typeCode: taskTypeCode },
    //            url: "/Common/GetParameterByType",
    //            dataType: "json",
    //            cache: false,
    //            success: function (data) {
    //                var jsonData = eval(data.Data);
    //                var thisData = "[";
    //                $.each(jsonData, function (index, item) {
    //                    if (index != jsonData.length - 1) {
    //                        thisData += "{\"id\":\"" + item.ParameterCode + "\",\"text\":\"" + item.ParameterValue + "\"},";
    //                    } else {
    //                        thisData += "{\"id\":\"" + item.ParameterCode + "\",\"text\":\"" + item.ParameterValue + "\"}";
    //                    }
    //                });
    //                thisData += "]";
    //                var cData = $.parseJSON(thisData);

    //                //$("#txtDepartmentId").val("");
    //                //$("#txtDepartment").val("");

    //                //$("#divDepartment").hide();
    //                $("#selTaskDetailTypeCode").empty();
    //                $("#selTaskDetailTypeCode").select2({
    //                    placeholder: "请选择",
    //                    allowClear: true,
    //                    data: cData
    //                });
    //            }
    //        })
    //    }
    //    else {
    //        $("#selTaskDetailTypeCode").empty();
    //        $("#selTaskDetailTypeCode").select2({
    //            placeholder: "请选择",
    //            data: []
    //        });
    //        //$("#divDepartment").show();
    //    }
    //})
    $('#dataTable').bootstrapTable({
        url: "/System/VisitPlan/QueryVisitPlan",
        pagination: "true",
        queryParams: queryParams,
        sidePagination: 'server',
        onPostBody: function () {
            RenderRoleButton();
        },
        clickToSelect: true,
        columns: [
            //{
            //    title: '<input data-index="0"  type="checkbox" onClick="checkAll(this)" />',
            //    formatter: function (value, row, index) {
            //        if (row.isParent && row.AuditStatus == "审核中") {
            //            return '<input data-index="0" name="btSelectItem"  type="checkbox" value="' + row.Id + '"/>'
            //        }
            //    }
            //},
            {
                title: '操作',
                field: 'Id',
                formatter: 'actionFormatter',
                events: 'OperateEvent',
                width: "100px"
            }, {
                field: 'UserName',
                title: '督导'
            },
            {
                field: 'WeekDate',
                title: '日期/星期'
            },
            {
                field: 'PlanTime',
                title: '计划时间'
            },
            {
                field: 'RealTime',
                title: '实际时间'
            },
            {
                field: 'Ts',
                title: '实际在店时间(分钟)'
            },
            {
                field: 'TaskStatus',
                title: '任务状态'
            }, {
                field: 'TaskPlanType',
                title: '工作类别'
            }
            ,
            //{
            //    field: 'Department',
            //    title: '大区'
            //},
            {
                field: 'PlanType',
                title: '计划类型'
            },
            {
                field: 'StoreNo',
                title: '门店编号'
            },

            {
                field: 'TaskPlanDetailType',
                title: '计划内容'
            }, {
                field: 'Address',
                title: '实际地点',
                formatter: 'substr'
            }, {
                field: 'Comment',
                title: '备注',
                formatter: 'substr15'
            } ,
            //{
            //    field: 'TaskDetail',
            //    title: '实际内容'
            //},
            {
                field: 'AuditStatus',
                title: '审核状态'
            }
        ]
    });
    $('#dataTable').bootstrapTable("checkAll");

    $("#TaskDateSta").datepicker({ format: 'yyyy-mm-dd', autoclose: true });
    $("#TaskDateEnd").datepicker({ format: 'yyyy-mm-dd', autoclose: true });
    $("#btnSearch").on("click", function () {
        $('#dataTable').bootstrapTable("refresh", { query: { SkipCount: 0 } });
    });

    var uploader;
    var intw = 0;
    $('#modalImport').on('shown.bs.modal', function (e) {
        uploader = WebUploader.create({
            auto: false,
            // swf文件路径
            swf: '/Plugins/webuploader/Uploader.swf',
            // 文件接收服务端。
            server: '/System/VisitPlan/ImportData',
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#picker',
            accept: {
                title: 'Files',
                extensions: 'xlsx,xls',
                mimeTypes: 'application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
            },
            fileNumLimit: 1
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
    //$("#StoreId").on("change", function () {
    //    var storeId = $(this).val();
    //    if (storeId != "") {
    //        $.SubAjax({
    //            type: "GET",
    //            data: { storeId: storeId },
    //            url: "/System/Store/GetStoreAddressById",
    //            dataType: "json",
    //            cache: false,
    //            success: function (data) {
    //                if (data.IsSuccess) {
    //                    data = data.Data;
    //                    $("#txtAddress").val(data.Address);
    //                    loadAllCity($("#CityId"));
    //                    $("#CityId").val(data.CityId);
    //                    $(".select2").select2();
    //                }
    //            }
    //        })
    //    }
    //})
    $("#selType").on("change", function () {
        if ($(this).val().indexOf('999') > -1) {
            $("#divTypeDetail").show();
        }
        else {
            $("#divTypeDetail").hide();
        }
    })

    function getTimeSun() {
        var sen = new Date();
        sen.setDate(sen.getDate() + 7);
        var ent = sen;
        ent.setDate(ent.getDate() + 7 - ent.getDay());
        ent = ent.getFullYear() + "-" + (ent.getMonth() + 1) + "-" + ent.getDate();
        return ent;
    }

    function getTimeMon() {
        var sen = new Date();
        sen.setDate(sen.getDate() + 7);
        var ent = sen;
        ent.setDate(ent.getDate() + 1 - ent.getDay());
        ent = ent.getFullYear() + "-" + (ent.getMonth() + 1) + "-" + ent.getDate();
        return ent;
    }


    function getAllStore() {
        //var userId = $("#UserId").val();
        var userId = $("#SelSupervisorId").val();
        var store = $("#StoreId");
        $.SubAjax({
            type: "GET",
            url: "/System/Store/GetMainStoreList",
            dataType: "json",
            data: { userId: userId },
            success: function (data) {
                var jsonData = data.rows;
                if (jsonData.length > 0) {
                    var thisData = "[{\"id\":\"\",\"text\":\"请选择\"},";
                    $.each(jsonData, function (index, item) {
                        if (item) {
                            if (index != jsonData.length - 1) {
                                thisData += "{\"id\":\"" + item.Id + "\",\"text\":\"" + item.StoreName + "\"},";
                            } else {
                                thisData += "{\"id\":\"" + item.Id + "\",\"text\":\"" + item.StoreName + "\"}";
                            }
                        }
                    });
                    thisData += "]";
                    var cData = $.parseJSON(thisData);
                    store.empty();
                    store.select2({
                        placeholder: "请选择",
                        allowClear: true,
                        data: cData
                    });
                    allStore = data.rows;
                } else {
                    store.empty();
                    store.select2({
                        placeholder: "请选择",
                        allowClear: true,
                        data:""
                    });

                }
            },
            error: function (data) {
                alert("数据加载失败！");
            }
        });
    }

    window.OperateEvent = {
        'click .update': function (e, value, row, index) {
            $("#btnSubmit").hide();
            $("#btnUpdate").show();
            $('#modalCreate').modal({ backdrop: 'static', keyboard: false });
            $("#UserId").val(row.UserId)
            $.SubAjax({
                type: 'post',
                data: { taskPlanId: row.Id },
                url: '/System/VisitPlan/GetTaskPlanDetail',
                success: function (data) {
                    if (data.IsSuccess) {
                        data = data.Data;
                        $("#Id").val(data.Id);
                        $("#SelSupervisorId").val(data.UserId);
                        $("#DateTime").val(data.DateTime);
                        $("#StartHH").val(data.StartHH);
                        $("#StartSS").val(data.StartSS);
                        $("#EndHH").val(data.EndHH);
                        $("#EndSS").val(data.EndSS);
                        $("#TaskTypeCode").val(data.TaskTypeCode);
                        if (data.TaskTypeCode == "940") {
                            //    $("#divPlanAddress").show();
                            $("#divStore").show();
                            $("#divPlanType").hide();
                            getAllStore();
                            $("#divTaskDetailTypeCode").hide();
                            $("#txtAddress").prop("disabled", true);
                            $("#StoreId").val(data.StoreId);
                            $("#txtAddress").val(data.Address);
                        }
                        else {
                            //  $("#divPlanAddress").hide();
                            $("#divPlanType").show();
                            $("#divStore").hide();
                            $("#divTaskDetailTypeCode").show();
                        }
                        if (data.TaskTypeCode != null && data.TaskTypeCode != "" && data.TaskTypeCode != "940") {

                            $.ajax({
                                type: "post",
                                data: { typeCode: data.TaskTypeCode },
                                url: "/Common/GetParameterByType",
                                dataType: "json",
                                cache: false,
                                anysc: false,
                                success: function (data) {
                                    var jsonData = eval(data.Data);
                                    var thisData = "[";
                                    $.each(jsonData, function (index, item) {
                                        if (index != jsonData.length - 1) {
                                            thisData += "{\"id\":\"" + item.ParameterCode + "\",\"text\":\"" + item.ParameterValue + "\"},";
                                        } else {
                                            thisData += "{\"id\":\"" + item.ParameterCode + "\",\"text\":\"" + item.ParameterValue + "\"}";
                                        }
                                    });
                                    thisData += "]";
                                    var cData = $.parseJSON(thisData);
                                    $("#TaskDetailTypeCode").empty();
                                    $("#TaskDetailTypeCode").select2({
                                        placeholder: "请选择",
                                        allowClear: true,
                                        data: cData
                                    });
                                }
                            })
                        }
                        $("#typeDetail").val(data.typeDetail);
                        $("#TaskDetailTypeCode").val(data.TaskDetailTypeCode);
                        $(".select2").select2();
                        $("#btnSubmit").hide();
                        $("#btnUpdate").show();
                    }
                    else {
                        $.ShowMessage("error", data.Msg)
                    }
                }
            });
        },
        'click .check': function (e, value, row, index) {
            parent.layer.confirm('确定审核通过么？', {
                btn: ['确定', '取消'], //按钮
                shade: false //不显示遮罩
            }, function () {
                $.SubAjax({
                    type: 'post',
                    data: { taskPlanId: row.Id },
                    url: '/System/VisitPlan/CheckTaskPlanDetail',
                    success: function (data) {
                        if (data.IsSuccess) {
                            layer.closeAll('dialog');
                            $.ShowMessage("success", '操作成功');
                            $('#dataTable').bootstrapTable("refresh");

                        }
                    }
                });
            })
        }, 'click .nocheck': function (e, value, row, index) {
            //parent.layer.confirm('确定不通过么？', {
            //    btn: ['确定', '取消'], //按钮
            //    shade: false //不显示遮罩
            //}, function () {
                parent.layer.prompt({
                    title: '请输入拒绝理由并确认',
                    formType: 1 //prompt风格，支持0-2
                }, function (text) {
                    $.SubAjax({
                        type: 'post',
                        data: { taskPlanId: row.Id , AuditReason : text},
                        url: '/System/VisitPlan/NotCheckTaskPlanDetail',
                        success: function (data) {
                            if (data.IsSuccess) {
                                layer.closeAll();
                                $.ShowMessage("success", '操作成功');
                                $('#dataTable').bootstrapTable("refresh");
                            } else {
                                $.ShowMessage("error", data.Msg)
                            }
                        }
                    });
               // });

                //parent.layer.confirm('确定不通过么？', {
                //    btn: ['确定', '取消'], //按钮
                //    shade: false //不显示遮罩
                //}, function () {
                //    $.SubAjax({
                //        type: 'post',
                //        data: { taskPlanId: row.Id },
                //        url: '/System/VisitPlan/NotCheckTaskPlanDetail',
                //        success: function (data) {
                //            if (data.IsSuccess) {
                //                layer.closeAll('dialog');
                //                $.ShowMessage("success", '操作成功');
                //                $('#dataTable').bootstrapTable("refresh");

                //            }
                //        }
                //    });
                //})
            })},



        'click .detail': function (e, value, row, index) {
            $("#modalDetail").modal({ backdrop: 'static', keyboard: false });
            $.SubAjax({
                type: 'post',
                data: { TaskPlanId: row.Id },
                url: '/System/VisitPlan/QueryVisPlan',
                success: function (data) {
                    
                    if (data.Data.CheckTime != null) {
                        $("#isCorrect").html(data.Data.isCorrectArea);
                        $("#checkTime").html(data.Data.CheckTime);
                    }
                    else {
                        $("#isCorrect").html('未拜访');
                        $("#checkTime").html('未拜访');
                    }
                }

            });
        },

        'click .photoDetail': function (e, value, row, index) {
            $("#modalPhotoDetail").modal({ backdrop: 'static', keyboard: false });
            $.SubAjax({
                type: 'post',
                data: { TaskPlanId: row.Id },
                url: '/System/VisitPlan/QueryVisPlan',
                success: function (data) {
                    if (data.Data.ImageUrl != null&&data.Data.ImageUrl !="") {
                        $("#isPhoto").html('');
                        $("#carousel2").show();
                        var imasges = "";
                        var li = "";
                        $.each(data.Data.ImageUrl, function (index, value) {
                            if (index == 0) {
                                li += '<li data-slide-to="0" data-target="#carousel2" class="active"></li>';
                                imasges += '<div class="item active"><img alt="image" class="img-responsive" src="' + value + '"></div>';
                            }
                            else {
                                li += '<li data-slide-to="' + index + '" data-target="#carousel2"></li>';
                                imasges += '<div class="item"><img alt="image" class="img-responsive" src="' + value + '"></div>';
                            }
                        })
                        $(".carousel-inner").html(imasges);
                        $(".carousel-indicators").html(li);
                    }
                    else {
                        $("#carousel2").hide();
                        $("#isPhoto").html('未上传照片！');
                    }
                }

            });
        },

        'click .visitqw': function (e, value, row, index) {
            $("#VisitPlanQWPartialModal").modal({ backdrop: 'static', keyboard: false });
            $("#QAS").val('');
            $.SubAjax({
                type: 'post',
                data: { TaskPlanId: row.Id },
                url: '/System/VisitPlan/QueryVisPlanQA',
                success: function (data) {
                    $("#QAS").val(data.Data.Description);
                    $("#VisitPlanId").val(row.Id);
                    if (data.Data.Photos != null && data.Data.Photos != "") {
                        $("#carousel3").show();
                        var imasges = "";
                        var li = "";
                        $.each(data.Data.Photos, function (index, value) {
                            if (index == 0) {
                                li += '<li data-slide-to="0" data-target="#carousel3" class="active"></li>';
                                imasges += '<div class="item active"><img alt="image" class="img-responsive" src="' + value + '"></div>';
                            }
                            else {
                                li += '<li data-slide-to="' + index + '" data-target="#carousel3"></li>';
                                imasges += '<div class="item"><img alt="image" class="img-responsive" src="' + value + '"></div>';
                            }
                        })

                        $(".carousel-inner").html(imasges);
                        $(".carousel-indicators").html(li);
                    }
                    else {
                        $("#carousel3").hide();
                    }
                }

            });
        }


    }


    $("#btnCheckAll").on("click", function () {
        var taskIds = "";
        $("[name='btSelectItem']").each(function () {
            if ($(this).is(":checked")) {
                taskIds += $(this).val() + ",";
            }
        })
        if (taskIds == "") {
            $.ShowMessage("warning", "请选择任务！");
            return;
        }
        parent.layer.confirm('确定审核通过么？', {
            btn: ['确定', '取消'], //按钮
            shade: false //不显示遮罩
        }, function () {
            $.SubAjax({
                type: 'post',
                data: { taskPlanId: taskIds },
                url: '/System/VisitPlan/CheckTaskPlanDetail',
                success: function (data) {
                    if (data.IsSuccess) {
                        layer.closeAll('dialog');
                        $.ShowMessage("success", '操作成功');
                        $('#dataTable').bootstrapTable("refresh");

                    }
                }
            });
        })
    })




    $("#btnUpdate").on("click", function () {
        var startHH = Number($("#StartHH").val());
        var endHH = Number($("#EndHH").val());
        var startHH = Number($("#StartHH").val());
        var endHH = Number($("#EndHH").val());
        var StartSS = Number($("#StartSS").val());
        var EndSS = Number($("#EndSS").val());
        if (startHH > endHH) {
            $.ShowMessage("warning", "结束时间必须大于开始时间");
            return false;
        }
        if (startHH == endHH && StartSS >= EndSS) {
            $.ShowMessage("warning", "结束时间必须大于开始时间");
            return false;
        }
        if ($("#TaskTypeCode").val() == '940' && ($("#StoreId").val() == null || $("#StoreId").val() == "" || $("#StoreId").val() == "请选择")) {
            $.ShowMessage("warning", "请选择门店");
            return false;

        }
        if (visitPlanForm.valid()) {
            $.SubAjax({
                type: 'post',
                data: visitPlanForm.serializeToJson(),
                url: '/System/VisitPlan/Update',
                success: function (data) {
                    if (data.IsSuccess) {
                        $.ShowMessage("success", '修改成功');
                        $('#dataTable').bootstrapTable("refresh");
                    }
                    else {
                        $.ShowMessage("error", data.Msg)
                    }
                }
            });
        } else {
            return false;
        }
    })
    $("#btnUpdate2").on("click", function () {
        if ($("#QAS").val() != null && $("#QAS").val() != "") {
            $.SubAjax({
                type: 'post',
                data: { TaskPlanId: $("#VisitPlanId").val(), Description: $("#QAS").val() },
                url: '/System/VisitPlan/QAUpdate',
                success: function (data) {
                    if (data.IsSuccess) {
                        $.ShowMessage("success", '修改成功');
                        $('#dataTable').bootstrapTable("refresh");
                    }
                    else {
                        $.ShowMessage("error", data.Msg)
                    }
                }
            });
        } else {
            $.ShowMessage("error", '表单验证失败')
        }
    })
    function getAllSuperVersion() {
        var superVision = $("#SuperVision");
        $.ajax({
            type: "GET",
            url: "/System/User/GetSuperVersionSimpleList?PageSize=900000",
            dataType: "json",
            cache: false,
            success: function (data) {
                var jsonData = data;
                var thisData = "[";
                $.each(jsonData, function (index, item) {
                    if (item) {
                        if (index != jsonData.length - 1) {
                            thisData += "{\"id\":\"" + item.Id + "\",\"text\":\"" + item.UserName + "(" + item.UserNo + ")\"},";
                        } else {
                            thisData += "{\"id\":\"" + item.Id + "\",\"text\":\"" + item.UserName + "(" + item.UserNo + ")\"}";
                        }
                    }
                });
                thisData += "]";
                var cData = $.parseJSON(thisData);
                superVision.empty();
                superVision.select2({
                    placeholder: "请选择",
                    allowClear: true,
                    data: cData
                });
                allSuperVision = data;
            },
            error: function (data) {
                alert("数据加载失败！");
            }
        });
    }

});
function queryParams(params) {
    return {

        SuperVersionId: $("#SupervisorId").val(),
        TaskType: $("#selTaskTypeCode").val(),
        VisitAuditStatus: $("#selVisitAuditStatus").val(),
        TaskDetailType: $("#selTaskDetailTypeCode").val(),
        TaskPlanType: $("#selTaskPlanType").val(),
        TaskStatus: $("#selTaskStatusCode").val(),
        StartTime: $("#TaskDateSta").val(),
        EndTime: $("#TaskDateEnd").val(),
        ProvinceCode: $("#ProvinceCode").val(),
        //DepartmentId: $("#txtDepartmentId").val(),
        StoreCode: $("#txtStoreCode").val(),
        PageSize: params.limit,   //页面大小
        PageIndex: params.pageNumber,//  sort: params.sort,  //排序列名
        sortOrder: params.order,
        SkipCount: params.offset,
    };
}

$("#btnExport").on("click", function () {
    exportData()
})
$("#btnExport2").on("click", function () {
    exportData()
})

function exportData(type) {
    $.SubAjax({
        type: 'post',
        data: {
            SuperVersionId: $("#SupervisorId").val(),
            TaskType: $("#selTaskTypeCode").val(),
            VisitAuditStatus: $("#selVisitAuditStatus").val(),
            TaskDetailType: $("#selTaskDetailTypeCode").val(),
            TaskPlanType: $("#selTaskPlanType").val(),
            StartTime: $("#TaskDateSta").val(),
            EndTime: $("#TaskDateEnd").val(),
            TaskStatus: $("#selTaskStatusCode").val(),
            StoreCode: $("#txtStoreCode").val(),
            DepartmentId: $("#txtDepartmentId").val(),
        },
        //url: '/System/VisitPlan/ExportVisitPlan',
        url: '/System/VisitPlan/ExportDataList',
        
        success: function (data) {
            if (data.IsSuccess) {
                window.location.href = "/File/DownFile?filePath=" + data.Data + "&fileName=任务计划表";
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
        $("#UserIds").val('');
    }
};

function actionFormatter(value, row, index) {
    var results = '';
    if (row.TaskStatus != '已结束' && row.TaskStatus != '已过期') {
        if (row.isParent) {
            results += ' <a class="update"  title="编辑" >编辑</a>';
        }
        else {
            results += '<a class="update"  title="编辑" data-role="SuperVisionAdmin" >编辑</a>';
        }
        //results += '<a class="nocheck ml10 red" href="javascript:void(0)" title="不通过"> 不通过</a>';
        if (row.PlanType != '计划外') {
            if (row.isParent && row.AuditStatus == "审核中") {
                results += '<a class="check ml10 green" href="javascript:void(0)" title="通过"> 通过</a>';
                results += '<a class="nocheck ml10 red" href="javascript:void(0)" title="不通过"> 不通过</a>';
            }
            if (row.isParent && row.AuditStatus == "已审核") {
                results += '<a class="nocheck ml10 red" href="javascript:void(0)" title="不通过"> 不通过</a>';
            }
        }
    }
    if (row.TaskPlanType == '巡店' && row.TaskStatus != '未开始') {
        results += '<a class="detail"  title="巡店详细" >  拜访详细</a>';
        results += '<a class="photoDetail"  title="照片采集" >  照片采集</a>';
    }
    //if (row.IsHasQA) {
    //    results += '<a class="visitqw"  title="问题反馈" >  问题反馈</a>';
    //}
    return results;
}

function GetNexMon() {
    var dt = new Date();
    var weekDay = ["7", "1", "2", "3", "4", "5", "6"];
    var thisWeek = weekDay[dt.getDay()];
    var nextWeek = 8 - thisWeek;
    return GetDateStr(nextWeek);
}
function GetNexNextMon() {
    var dt = new Date();
    var weekDay = ["7", "1", "2", "3", "4", "5", "6"];
    var thisWeek = weekDay[dt.getDay()];
    var nextWeek = 8 - thisWeek + 6;
    return GetDateStr(nextWeek);
}
function GetDateStr(AddDayCount) {
    var dd = new Date();
    dd.setDate(dd.getDate() + AddDayCount);//获取AddDayCount天后的日期
    var y = dd.getFullYear();
    var m = dd.getMonth() + 1;//获取当前月份的日期
    var d = dd.getDate();
    return y + "-" + m + "-" + d;
}

function substr(value, row, index) {
    if (row.Address != null) {
        var leng = row.Address;
        if (row.Address.length > 3) {
            leng = row.Address.substr(0, 3) + '...'
        }
        return ' <span data-toggle="tooltip" data-placement="bottom"   title="' + row.Address + '" class="tooltip-show" style="cursor:default">' + leng + '</span>';
    }

}
function substr15(value, row, index) {
    if (row.Comment != null) {
        var leng = row.Comment;
        if (row.Comment.length > 15) {
            leng = row.Comment.substr(0, 15) + '...'
        }
        return ' <span data-toggle="tooltip" data-placement="bottom"   title="' + row.Comment + '" class="tooltip-show" style="cursor:default">' + leng + '</span>';
    }

}