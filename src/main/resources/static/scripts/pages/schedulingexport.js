
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

    LoadDatatable();
    
    $("#btnSearch").on("click", function () {
        $('#dataTable').bootstrapTable("destroy");// 破坏原来的表
        LoadDatatable();
    });

    $("#btnExport").on("click", function () {
        $.SubAjax({
            type: 'post',
            data: {
                AreaId: $("#Area").val(),
                SubAreaId: $("#SubArea").val(),
                ProvinceCode: $("#ProvinceCode").val(),
                CityCode: $("#CityCode").val(),
                UserNo: $("#txtUserNo").val(),
                UserName: $("#txtUserName").val(),
                StoreName: $("#txtStoreName").val(),
                StoreNo: $("#txtStoreNo").val(),
                Mobile: $("#txtMobile").val(),
                SchedulingExportTimeSta: $("#SchedulingExportTimeSta").val(),
                SchedulingExportTimeEnd: $("#SchedulingExportTimeEnd").val(),
                
            },
            url: '/DataImport/Attendance/ExportAttendance',
            success: function (data) {
                if (data.IsSuccess) {
                    window.location.href = "/File/DownFile?filePath=" + data.Data + "&fileName=排班信息";
                }
                else {
                    $.ShowMessage("error", data.Msg);
                }
            }
        })
    })
})


function LoadDatatable() {
    var columns = [
        {
            field: 'leave_date',
            title: '离职时间'
        },
        {
            field: 'user_no',
            title: '小区'
        },
        {
            field: 'user_name',
            title: '商店编码'
        },
        {
            field: 'mobile',
            title: '商店标准名称'
        },
        {
            field: 'personel_status_code',
            title: '巡店督导',
        },
        {
            field: 'area_name',
            title: '巡店日期'
        },
        {
            field: 'sub_area_name',
            title: '提交表格时间'
        },
        {
            field: 'in_job_status_code',
            title: '提交表格时间'
        },

    ];
    $.SubAjax({
        type: 'post',
        data: {
            SchedulingExportTimeSta: $("#SchedulingExportTimeSta").val(),
            SchedulingExportTimeEnd: $("#SchedulingExportTimeSta").val(),
        },
        url: '/DataImport/SchedulingExport/QueryTitleName',
        success: function (data) {
            if (data.IsSuccess) {
                $.each(data.Data, function (index, value) {
                    columns.push({
                        title: value,
                        field: value,
                    });
                })
            }
        }
    })

    $('#dataTable').bootstrapTable({
        url: "/DataImport/SchedulingExport/GetDataList",
        pagination: "true",
        queryParams: queryParams,
        sidePagination: 'server',
        columns: columns
    });
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
        AttendanceTimeSta: $("#AttendanceTimeSta").val(),
        AttendanceTimeEnd: $("#AttendanceTimeEnd").val(),
        PageSize: params.limit,   //页面大小
        PageIndex: params.pageNumber,//  sort: params.sort,  //排序列名
        sortOrder: params.order,
        SkipCount: params.offset
    };
}
