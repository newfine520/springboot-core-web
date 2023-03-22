﻿$(function () {
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
        url: "/DataImport/OutofStock/GetDataList",
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
                  title: '门店连锁',
                  field: 'StoreChain',
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
                 title: '缺货数量',
                 field: 'Account',
             },
              {
                  title: '填报人',
                  field: 'FullInName',
              },
              {
                  title: '缺货日期',
                  field: 'OutofStockDateString'
              },
        ],
    });
    $("#btnSearch").on("click", function () {
        $('#dataTable').bootstrapTable("refresh", { query: { SkipCount: 0 } });
    })

    /* excel文件上传解析 */
    var excelUploader;
    var intw = 0;
    $('#ImportModal').on('shown.bs.modal', function (e) {
        excelUploader = WebUploader.create({
            auto: false,
            swf: '/Plugins/webuploader/Uploader.swf',
            server: '/DataImport/OutofStock/Import',
            pick: '#btnFilePicker',
            accept: {
                title: 'excel',
                extensions: 'xlsx,xls',
                mimeTypes: 'application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
            },
            fileNumLimit: 1
        });
        excelUploader.on('filesQueued', function (files) {
            files.forEach(function (file) {
                $('#thelist').append('<div id="' + file.id + '" class="item">' +
                    '<h4 class="info">' + file.name + '</h4>' +
                    '<p class="state">请上传.....</p>' +
                    '</div>');
            });
        }).on('uploadComplete', function (file) {
            layer.close(intw);
        }).on('uploadProgress', function (file, percentage) {
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
        }).on('uploadSuccess', function (file, response) {
            if (response.IsSuccess) {
                $.ShowMessage("success", "文件导入成功");
                $("#ImportModal").modal("hide");
                $('#dataTable').bootstrapTable("refresh");
            }
            else {
                $('#thelist').empty();
                $.each(response.Data, function (index, value) {
                    $('#thelist').append("<div style='color:red'>" + value.ErrorMsg + "</div><br/>")
                })
                excelUploader.removeFile(file);
            }
        });
    }).on('hidden.bs.modal', function (e) {
        excelUploader.destroy();
        $('#thelist').empty();
    });

    $('#btnUploadFile').on('click', function () {
        excelUploader.upload();
    });

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
        IsOutOf: $("#selOutOf").val(),
        FullDateSta: $("#FullDateSta").val(),
        FullDateEnd: $("#FullDateEnd").val(),
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
            IsOutOf: $("#selOutOf").val(),
            FullDateSta: $("#FullDateSta").val(),
            FullDateEnd: $("#FullDateEnd").val(),
            FullInName: $("#txtFullInName").val(),
            StoreNo: $("#txtStoreNo").val(),
        },
        url: '/DataImport/OutofStock/ExportStoreSalesInfoData',
        success: function (data) {
            if (data.IsSuccess) {
                window.location.href = "/File/DownFile?filePath=" + data.Data + "&fileName=缺货统计";
            }
            else {
                $.ShowMessage("error", data.Msg);
            }
        }
    })
}