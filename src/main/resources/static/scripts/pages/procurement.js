$(function () {
    var defaultYearMonthDate = new Date().getFullYear().toString() + "-";
    defaultYearMonthDate += (new Date().getMonth() + 1) + "-" + (new Date().getDate());
    $(".datepicker").datepicker({
        format: 'yyyy-mm-dd',
        weekStart: 1,
        autoclose: true,
        todayBtn: 'linked',
        language: 'zh-CN'
    });
    $(".select2").select2();
    //$(".datepicker").val(defaultYearMonthDate)
    loadAllProvince($("#ProvinceCode"));
    changeProvince($("#ProvinceCode"), $("#CityCode"))
    LoadAllArea($("#Area"))
    changeArea($("#Area"), $("#SubArea"))
    $('#dataTable').bootstrapTable({
        url: "/DataImport/Procurement/GetDataList",
        pagination: "true",
        queryParams: queryParams,
        sidePagination: 'server',
        onPostBody: function () {
            RenderRoleButton();
        },
        clickToSelect: true,
        columns: [

            {
                field: 'AreaName',
                title: '大区'
            },
             {
                 field: 'SubAreaName',
                 title: '小区',
             },
            {
                field: 'ProvinceName',
                title: '所在省'
            }, {
                field: 'CityName',
                title: '所在市'
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
                 title: '地址',
                 field: 'StoreAddress',
                 formatter: 'substrAddress',
             },
              {
                  title: '品牌',
                  field: 'BrandName',
              },
             {
                 title: '产品名称',
                 field: 'ProductName',
             },
             {
                 title: '进货个数',
                 field: 'Account',
             },
              {
                  title: '填报人',
                  field: 'FullInName',
              },
              {
                  title: '填报日期',
                  field: 'CreatDateString'
              },
        ],
    });
    $("#btnSearch").on("click", function () {
        $('#dataTable').bootstrapTable("refresh", { query: { SkipCount: 0 } });
    })

    $('#btnExport').on("click", function () {
        exportData()
    })
})
function substrAddress(value, row, index) {
    if (row.StoreAddress != null) {
        var leng = row.StoreAddress;
        if (row.StoreAddress.length > 10) {
            leng = row.StoreAddress.substr(0, 10) + '...'
        }
        return ' <span data-toggle="tooltip" data-placement="bottom"   title="' + row.StoreAddress + '" class="tooltip-show" style="cursor:default">' + leng + '</span>';
    }
}

function queryParams(params) {
    return {
        ProvinceId: $("#ProvinceCode").val(),
        CityId: $("#CityCode").val(),
        AreaId: $("#Area").val(),
        SubAreaId: $("#SubArea").val(),
        StoreName: $("#txtStoreName").val(),
        FullDate: $("#FullDate").val(),
        FullInName: $("#txtFullInName").val(),
        StoreNo: $("#txtStoreNo").val(),
        PageSize: params.limit,   //页面大小
        SkipCount: params.offset,//  sort: params.sort,  //排序列名
        sortOrder: params.order//排位命令（desc，asc）
    };
}

function exportData(type) {
    $.SubAjax({
        type: 'post',
        data: {
            ProvinceId: $("#ProvinceCode").val(),
            CityId: $("#CityCode").val(),
            AreaId: $("#Area").val(),
            SubAreaId: $("#SubArea").val(),
            StoreName: $("#txtStoreName").val(),
            FullDate: $("#FullDate").val(),
            FullInName: $("#txtFullInName").val(),
            StoreNo: $("#txtStoreNo").val(),
        },
        url: '/DataImport/Procurement/ExportData',
        success: function (data) {
            if (data.IsSuccess) {
                window.location.href = "/File/DownFile?filePath=" + data.Data + "&fileName=进货提报";
            }
            else {
                $.ShowMessage("error", data.Msg);
            }
        }
    })
}