$(function () {
    $(".select2").select2();
    $("#CityCode").select2({
        placeholder: "请选择",
        allowClear: true,
        data: ""
    });
    $("#selCirclyDate").val(getNowTime());
    LoadAllArea($("#Area"))
    changeArea($("#Area"), $("#SubArea"))
    loadAllProvince($("#ProvinceCode"));
    changeProvince($("#ProvinceCode"), $("#CityCode"));

    LoadDatatable();
    $("#btnSearch").on("click", function () {
        if ($("#selCirclyDate").val() == null || $("#selCirclyDate").val().substring(0, 7) == "") {
            $.ShowMessage("warning", "请选择日期！");
            return false;
        }
        $('#dataTable').bootstrapTable("destroy");
        LoadDatatable();
    })
  
    $("#selCirclyDate").datepicker({ format: 'yyyy-mm-dd', autoclose: true });
    $("#btnSearch").on("click", function () {
        if ($("#selCirclyDate").val() == null || $("#selCirclyDate").val().substring(0, 7) == "") {
            $.ShowMessage("warning", "请选择日期！");
            return false;
        }
        $('#dataTable').bootstrapTable("refresh");
    })

    $("#btnUpdate").on("click", function () {
        var salesVolumes = [];
        $.each($(".sub-brand-volume"), function (index, value) {
            salesVolumes.push({
                SubBrandId: $(this).find($("input[name='BrandVolume']")).data("brand"),
                SalvesVolume: $(this).find($("input[name='BrandVolume']")).val()
            });
        })
        $.SubAjax({
            type: 'post',
            data: JSON.stringify({
                StoreId: $("#StoreId").val(),
                StoreSalesVolumeId: $("#StoreSalesVolumeId").val(),
                PromoterId:$("#PromoterId").val(),
                SalesVolumes: salesVolumes,
                //SampleCount: $("#SampleCount").val(),
                //SampleSalesVolume: $("#SampleSalesVolume").val(),
                Total: $("#Total").val(),
                CirclyDate: $("#selCirclyDate").val()
            }),
            contentType: "application/json; charset=utf-8",
            url: '/DataImport/StoreSalesValume/UpdateStoreSalvesVolume',
            success: function (data) {
                if (data.IsSuccess) {
                    $("#StoreSalesVolumeModal").modal('hide')
                    $.ShowMessage("success", "保存成功");
                    $('#dataTable').bootstrapTable("refresh");
                }
                else {
                    $.ShowMessage("error", data.Msg);
                }
            }
        })
    })
})
function queryParams(params) {
    return {
        PageSize: params.limit,   //页面大小
        SkipCount: params.offset,//  sort: params.sort,  //排序列名
        sortOrder: params.order,//排位命令（desc，asc）
        CategoryType: categoryType.physiology,
        AreaId: $("#Area").val(),
        SubAreaId: $("#SubArea").val(),
        StoreNo: $("#txtStoreNo").val(),
        StoreName: $("#txtStoreName").val(),
        ProvinceId: $("#ProvinceCode").val(),
        CityId: $("#CityCode").val(),
        CirclyDate: $("#selCirclyDate").val(),
        SalesCycle: 4001,
        SubBrandId: $("#selSubBrand").val(),
        SkuTypeMark: "800310"
    };
}
function operateFormatter(value, row, index) {
    return [
        '<a class="edit" data-role="Admin" title="编辑">编辑</a>'
    ].join('');
}

$("#btnExport").on("click", function () {
    $.SubAjax({
        type: 'post',
        data: {
            AreaId: $("#Area").val(),
            SubAreaId: $("#SubArea").val(),
            StoreNo: $("#txtStoreNo").val(),
            StoreName: $("#txtStoreName").val(),
            ProvinceId: $("#ProvinceCode").val(),
            CityId: $("#CityCode").val(),
            CirclyDate: $("#selCirclyDate").val(),
            SalesCycle: 4001,
            SubBrandId: $("#selSubBrand").val(),
            SkuTypeMark: "800310"
        },
        url: '/DataImport/StoreSalesValume/ExportStoreSalesValume',
        success: function (data) {
            if (data.IsSuccess) {
                window.location.href = "/File/DownFile?filePath=" + data.Data + "&fileName=销量提报列表";
            }
            else {
                $.ShowMessage("error", data.Msg)
            }
        }
    });
})

function getNowTime() {
    var sen = new Date();
    sen = sen.getFullYear() + "-" + (sen.getMonth() + 1) + "-" + sen.getDate();
    return sen;
}
window.operateEvents = {
    'click .edit': function (e, value, row, index) {
        
        $.SubAjax({
            type: 'post',
            data: {
                UserId: row.UserId,
                StoreId: row.storeid,
                CirclyDate: $("#selCirclyDate").val()
            },
            url: '/DataImport/StoreSalesValume/QueryStoreSalesVolume',
            success: function (data) {
                if (data.IsSuccess) {
                    $("#StoreSalesVolumeModal").modal({ backdrop: 'static', keyboard: false });
                    $("#StoreId").val(row.storeid);
                    $("h2").html(row.StoreName);
                    var tpl = $("#tpl").html();
                    var html = juicer(tpl, data.Data);
                    $("#divStoreSalesVolume").html(html);
                } else {
                    $.ShowMessage("error", data.Msg)
                }
            }
        });
        
    }
}

function LoadDatatable() {
    var columns = [
        {
        //    title: '操作',
        //    field: 'StoreId',
        //    formatter: operateFormatter,
        //    events: 'operateEvents'
        //}, {
            field: 'area_name',
            title: '大区'
        }, {
            field: 'sub_area_name',
            title: '小区'
        }, {
            title: '省份',
            field: 'ProvinceName',
        }, {
            title: '城市',
            field: 'CityName',
        }, {
            field: 'StoreNo',
            title: '门店编号',
        }, {
            field: 'StoreName',
            title: '门店名称'
        }, {
            field: 'UserName',
            title: '促销员'
        }, {
            field: 'CreateName',
            title: '提报人'
        }, {
            field: 'volumetotal',
            title: '总销量(元)'
        }
    ];

    var get = $.SubAjax({
        type: 'post',
        data: {
            SubBrandId: $("#selSubBrand").val(),
            SkuTypeMark: "800310"
        },
        url: '/Common/GetSubBrandSkuIds',
        success: function (data) {
            if (data.IsSuccess) {
                $.each(data.Data, function (index, value) {
                    columns.push({
                        title: value.SkuName + '(元)',
                        field: value.SkuId,
                    });
                })
                $('#dataTable').bootstrapTable({
                    url: "/DataImport/StoreSalesValume/GetDataList",
                    pagination: "true",
                    queryParams: queryParams,
                    sidePagination: 'server',
                    onPostBody: function () {
                        RenderRoleButton();
                    },
                    columns: columns,
                });
            }
            //columns.push({
            //    title: "派样数量(包)",
            //    field: "sample_count",
            //});
            //columns.push({
            //    title: "派样产品售出量",
            //    field: "sample_sales_volume",
            //});
            //columns.push({
            //    title: '操作',
            //    field: 'StoreId',
            //    formatter: operateFormatter,
            //    events: 'operateEvents'
            //});
            
        }
    })

}