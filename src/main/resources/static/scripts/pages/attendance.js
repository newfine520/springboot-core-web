
$(function () {
    //initMap();
    $(".select2").select2();
    //省市下拉框
    loadAllProvince($("#ProvinceCode"));
    changeProvince($("#ProvinceCode"), $("#CityCode"));
    LoadAllArea($("#Area"));
    changeArea($("#Area"), $("#SubArea"));
    //BindDptTree("menuContent", "treeDemo", "txtDepartment");
    //BindDptTree("treeContent", "tree", "Department");
    $(".datepicker").datepicker({
        format: "yyyy-mm-dd",
        autoclose: true,
        todayBtn: 'linked',
    });
    $('#dataTable').bootstrapTable({
        url: "/System/Attendance/GetList",
        pagination: "true",
        queryParams: queryParams,
        sidePagination: 'server',
        onPostBody: function () {
            RenderRoleButton();
        },
        clickToSelect: true,
        columns: [
            {
                title: '操作',
                field: 'UserId',
                formatter: 'actionFormatter',
                events: 'OperateEvent'
            },
             {
                 field: 'AreaName',
                 title: '大区'
             },
             {
                 field: 'SubAreaName',
                 title: '小区'
             },
             {
                 field: 'ProvinceName',
                 title: '省份'
             },
             {
                 field: 'CityName',
                 title: '城市'
             },

             {
                 field: 'StoreNo',
                 title: '门店编号'
             },
             {
                 field: 'StoreName',
                 title: '门店名称'
             },
              {
                  field: 'StoreAddress',
                  title: '门店地址',
                  formatter: 'substr'
              },
              {
                  field: 'UserNo',
                  title: '工号'
              },
              {
                  field: 'UserName',
                  title: '促销员'
              },

             //, {
             //    field: 'AttendanceTimeString',
             //    title: '考勤时间',
             //    sortable: "false"
             //}
             //, {
             //    field: 'AttendType',
             //    title: '考勤类型',
             //    sortable: "false"
             //},
             //{
             //    title: '操作',
             //    field: 'Id',
             //    formatter: 'actionFormatter',
             //    events: 'OperateEvent'
             //},
             {
                 field: 'Mobile',
                 title: '手机'
             },

             {
                 field: 'TimeDiff',
                 title: '工作时长'
             },
             {
                 field: 'OnDutyTimeString',
                 title: '上班时间'
             },
             {
                 title: '上班定位',
                 field: 'OndutyLocation',
                 formatter: 'onlocationFormatter',
                 events: 'OperateEvent'
             },
             {
                 title: '上班距离(米)',
                 field: 'OndutyDistance'
             },
             //{
             //    field: 'ondutyComment',
             //    title: '上班备注',
             //    formatter: 'substrOndutyComment'
             //},
             {
                 field: 'OffDutyTimeString',
                 title: '下班时间'
             },
             {
                 title: '下班定位',
                 field: 'OffdutyLocation',
                 formatter: 'offlocationFormatter',
                 events: 'OperateEvent'
             },
              {
                  title: '下班距离(米)',
                  field: 'OffdutyDistance'
              },

             //{
             //    field: 'offdutyComment',
             //    title: '下班备注',
             //    formatter: 'substrOffdutyComment'
             //}


        ]
    });
    $('#dataTable').bootstrapTable("checkAll");

    $("#btnSearch").on("click", function () {
        $('#dataTable').bootstrapTable("refresh");
    });

    $("#btnExport").on("click", function () {
        $.SubAjax({
            type: 'post',
            data: {
                ProvinceCode: $("#ProvinceCode").val(),
                CityCode: $("#CityCode").val(),
                UserNo: $("#txtUserNo").val(),
                UserName: $("#txtUserName").val(),
                StoreName: $("#txtStoreName").val(),
                StoreNo: $("#txtStoreNo").val(),
                AttendanceTypeCode: $("#selAttendanceTypeCode").val(),
                // AttendanceTime: $("#AttendanceTime").val(),
                AttendanceTimeSta: $("#AttendanceTimeSta").val(),
                AttendanceTimeEnd: $("#AttendanceTimeEnd").val(),
               // DepartmentId: $("#txtDepartmentId").val(),
                Mobile: $("#txtMobile").val(),
            },
            url: '/System/Attendance/ExportAttendance',
            success: function (data) {
                if (data.IsSuccess) {
                    window.location.href = "/File/DownFile?filePath=" + data.Data + "&fileName=考勤信息";
                }
                else {
                    $.ShowMessage("error", data.Msg);
                }
            }
        })
    })
})



window.OperateEvent = {
    'click .detail': function (e, value, row, index) {
        $("#modalDetail").modal({ backdrop: 'static', keyboard: false });
        $.SubAjax({
            type: 'post',
            data: {
                StoreNo: row.StoreNo,
                PhotoTypeCode: PhotoType.Attendance,
                UploadType: "Attendance",
                UserId: row.UserId,
                ObjectIntId: row.RowId
            },
            url: '/Common/GetImages',
            success: function (data) {
                $("#isCorrect").html(data.Data.isCorrectArea);
                $("#checkTime").html(data.Data.CheckTime);
                if (data.TotalCount > 0) {
                    $("#carousel2").show();
                    var imasges = "";
                    var li = "";
                    $.each(data.Data, function (index, value) {

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
                }
            }

        });
    },
    'click .detail1': function (e, value, row, index) {
        $("#modalDetail").modal({ backdrop: 'static', keyboard: false });
        $.SubAjax({
            type: 'post',
            data: {
                StoreNo: row.StoreNo,
                PhotoTypeCode: PhotoType.Attendance,
                UploadType: "Attendance",
                UserId: row.UserId,
                ObjectIntId: row.OffDutyId
            },
            url: '/Common/GetImages',
            success: function (data) {
                $("#isCorrect").html(data.Data.isCorrectArea);
                $("#checkTime").html(data.Data.CheckTime);
                if (data.TotalCount > 0) {
                    $("#carousel2").show();
                    var imasges = "";
                    var li = "";
                    $.each(data.Data, function (index, value) {
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
                }
            }

        });
    },
    'click .onlocation': function (e, value, row, index) {
        $("#modalAttendanceLocation").modal({ backdrop: 'static', keyboard: false });
        var location = row.OndutyLocation.split(',');
        $("#longitude").val(location[0]);
        $("#latitude").val(location[1]);
    },
    'click .offlocation': function (e, value, row, index) {
        $("#modalAttendanceLocation").modal({ backdrop: 'static', keyboard: false });
        var location = row.OffdutyLocation.split(',');
        $("#longitude").val(location[0]);
        $("#latitude").val(location[1]);
    },
    'click .leavePost': function (e, value, row, index) {
        //alert(row.OnDutyTime);

        $.SubAjax({
            type: 'post',
            data: {
                StoreId: row.StoreId,
                UserId: row.UserId,
                OnDutyTimes: row.OnDutyTimeString,
                OffDutyTimes: row.OffDutyTimeString,
            },
            url: '/System/Attendance/QueryLeavePostListPC',
            success: function (data) {
                if (data.IsSuccess) {
                    if (data.Data != null && data.Data != "") {
                        var tpl = $("#tpl").html();
                        var html = juicer(tpl, data);
                        $("#LeavePostDiv").html(html);
                    }
                    else {
                        $("#LeavePostDiv").html(' <label>没有离岗记录！</label>');
                    }
                    $("#LeavePostPartialModal").modal({
                        backdrop: 'static', keyboard: false
                    });
                }
                else {
                    $.ShowMessage("error", "没有配置检测项")
                }
            }
        });

    },
}
$('#modalAttendanceLocation').on('shown.bs.modal', function (e, value, row, index) {
    //map.clearOverlays();
    //$("#allmap").clear();
    var map = new BMap.Map("allmap");
    var new_point = new BMap.Point($("#longitude").val(), $("#latitude").val())
    map.centerAndZoom(new_point, 17);
    map.enableScrollWheelZoom(true);
    map.addControl(new BMap.ScaleControl());
    map.addControl(new BMap.NavigationControl());
    var marker = new BMap.Marker(new_point);  // 创建标注
    map.addOverlay(marker);
    //var label = new BMap.Label("我是文字标注哦", { offset: new BMap.Size(20, -10) });
    //marker.setLabel(label);
    
    //map.panTo(new_point);
})

function substr(value, row, index) {
    if (row.StoreAddress != null) {
        var leng = row.StoreAddress;
        if (row.StoreAddress.length > 3) {
            leng = row.StoreAddress.substr(0, 3) + '...'
        }
        return ' <span data-toggle="tooltip" data-placement="bottom"   title="' + row.StoreAddress + '" class="tooltip-show" style="cursor:default">' + leng + '</span>';
    }
}


function queryParams(params) {
    return {
        ProvinceCode: $("#ProvinceCode").val(),
        CityCode: $("#CityCode").val(),
        AreaId:$("#Area").val(),
        SubAreaId:$("#SubArea").val(),
        UserName: $("#txtUserName").val(),
        UserNo: $("#txtUserNo").val(),
        StoreName: $("#txtStoreName").val(),    
        StoreNo: $("#txtStoreNo").val(),
        Mobile: $("#txtMobile").val(),
        AttendanceTypeCode: $("#selAttendanceTypeCode").val(),
        //AttendanceTime: $("#AttendanceTime").val(),
        AttendanceTimeSta: $("#AttendanceTimeSta").val(),
        AttendanceTimeEnd: $("#AttendanceTimeEnd").val(),
        //DepartmentId: $("#txtDepartmentId").val(),
        PageSize: params.limit,   //页面大小
        PageIndex: params.pageNumber,//  sort: params.sort,  //排序列名
        sortOrder: params.order,
        SkipCount: params.offset
    };
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
}
function actionFormatter(value, row, index) {
    if (row.OnDutyTimeString != null && row.OnDutyTimeString != "") {
        var results = '<a class="leavePost"  title="离岗信息">离岗信息</a>';
        results += '<a class="detail"  title="照片">  上班</a>';
    }
        if (row.OffDutyTimeString != null && row.OffDutyTimeString != "")
        { results += '<a class="detail1"  title="照片">  下班</a>'; }


    return results;
}
function onlocationFormatter(value, row, index) {
    if (row.OndutyLocation != null && row.OndutyLocation != "")
    { var results = '<a class="onlocation"  title="上班定位"><image src="/images/location.jpg" class="loc"/></a>'; }
    return results;
}
function offlocationFormatter(value, row, index) {
    if (row.OffdutyLocation != null && row.OffdutyLocation != "")
    { var results = '<a class="offlocation"  title="下班定位">  <image src="/images/location.jpg"  class="loc"/></a>'; }
    return results;
}

function substrOndutyComment(value, row, index) {
    if (row.OndutyComment != null) {
        var leng = row.OndutyComment;
        if (row.OndutyComment.length > 10) {
            leng = row.OndutyComment.substr(0, 10) + '...'
        }
        return ' <span data-toggle="tooltip" data-placement="bottom"   title="' + row.OndutyComment + '" class="tooltip-show" style="cursor:default">' + leng + '</span>';
    }
}

function substrOffdutyComment(value, row, index) {
    if (row.OffdutyComment != null) {
        var leng = row.OffdutyComment;
        if (row.OffdutyComment.length > 10) {
            leng = row.OffdutyComment.substr(0, 10) + '...'
        }
        return ' <span data-toggle="tooltip" data-placement="bottom"   title="' + row.OffdutyComment + '" class="tooltip-show" style="cursor:default">' + leng + '</span>';
    }
}