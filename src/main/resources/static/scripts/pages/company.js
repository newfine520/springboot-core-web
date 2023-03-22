
$(function () {
    
    $("#isCore").change(function () {
        if ($("#isCore").val()=="0") {
            $("#divApiUrl").hide();
            $("#divAddress").show();
        }else{
            $("#divAddress").hide();
            $("#divApiUrl").show();

        }     
    });


    $('#dataTable').bootstrapTable({
        url: "/System/CompanyBrand/GetBrand",
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
                field: 'Id',
                formatter: 'operateFormatter',
                events: 'operateEvents',
                width: "100px"
            },
            {
                field: 'name',
                title: '公司品牌名称'
            },
            {
                field: 'code',
                title: '公司品牌编号'
            },
            {
                title: '小图',
                field: 'smallPhoto',
                formatter: 'PhotoFormatter',
                width: "100px"
            },
            {
                title: 'LOGO图',
                field: 'logoPhoto',
                formatter: 'PhotoFormatter',
                width: "100px"
            },
             {
                 title: '主题图',
                 field: 'themePhoto',
                 formatter: 'PhotoFormatter',
                 width: "100px"
             },
            {
                field: 'count',
                title: '存在套件'
            },
            {
                field: 'strCreateTime',
                title: '创建时间'
            }

        ]
    });

    var uploaderLogo;
    var intw = 0;
    var uploaderTheme;
    var uploaderSmall;
    $('#CompanyInfoPartialModal').on('shown.bs.modal', function (e) {

        var $ = jQuery,
        $list = $('#fileList'),

        ratio = window.devicePixelRatio || 1,

        thumbnailWidth = 100 * ratio,
        thumbnailHeight = 100 * ratio;

       uploaderLogo = WebUploader.create({
            auto: true,
            // swf文件路径
            swf: '/Plugins/webuploader/Uploader.swf',
            // 文件接收服务端。
            server: '/imgs/upload',
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#filePickerLogo',
            // 只允许选择图片文件。
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            },
            fileNumLimit: 1
       });

       uploaderSmall = WebUploader.create({
           auto: true,
           // swf文件路径
           swf: '/Plugins/webuploader/Uploader.swf',
           // 文件接收服务端。
           server: '/imgs/upload',
           // 选择文件的按钮。可选。
           // 内部根据当前运行是创建，可能是input元素，也可能是flash.
           pick: '#filePickerSmall',
           // 只允许选择图片文件。
           accept: {
               title: 'Images',
               extensions: 'gif,jpg,jpeg,bmp,png',
               mimeTypes: 'image/*'
           },
           fileNumLimit: 1
       });

       uploaderTheme = WebUploader.create({
            auto: true,
            // swf文件路径
            swf: '/Plugins/webuploader/Uploader.swf',
            // 文件接收服务端。
            server: '/imgs/upload',
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#filePickerTheme',
            // 只允许选择图片文件。
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            },
            fileNumLimit: 1
        });

        uploaderLogo.on('fileQueued', function (file) {
      
        });

        // 文件上传过程中创建进度条实时显示。
        uploaderSmall.on('uploadProgress', function (file, percentage) {
            var $li = $('#' + file.id),
                $percent = $li.find('.progress span');

            // 避免重复创建
            if (!$percent.length) {
                $percent = $('<p class="progress"><span></span></p>')
                        .appendTo($li)
                        .find('span');
            }

            $percent.css('width', percentage * 100 + '%');
        });

        uploaderLogo.on('uploadProgress', function (file, percentage) {
            var $li = $('#' + file.id),
                $percent = $li.find('.progress span');

            // 避免重复创建
            if (!$percent.length) {
                $percent = $('<p class="progress"><span></span></p>')
                        .appendTo($li)
                        .find('span');
            }

            $percent.css('width', percentage * 100 + '%');
        });

        // 文件上传成功，给item添加成功class, 用样式标记上传成功。
        uploaderLogo.on('uploadSuccess', function (file,data) {
            $('#' + file.id).addClass('upload-state-done');
            $('#piclogo').val(data.url);
            $('#imgLogoPhoto').prop("src", data.url);
            uploaderLogo.removeFile(file);
            var obj = new Object();
            obj.imgPath = $('#piclogo').val();
            //uploaderAssem.option.formData = obj;
        });
        uploaderTheme.on('uploadSuccess', function (file,data) {
            $('#' + file.id).addClass('upload-state-done');
            $('#pictheme').val(data.url);
            $('#imgThemePhoto').prop("src", data.url);
            uploaderTheme.removeFile(file);
            var obj = new Object();
            obj.imgPath = $('#pictheme').val();
            //uploaderAssem.option.formData = obj;
        });
        uploaderSmall.on('uploadSuccess', function (file, data) {
            $('#' + file.id).addClass('upload-state-done');
            $('#picSmall').val(data.url);
            $('#imgSmallPhoto').prop("src", data.url);
            uploaderSmall.removeFile(file);
            var obj = new Object();
            obj.imgPath = $('#picSmall').val();
            //uploaderAssem.option.formData = obj;
        });
        // 文件上传失败，显示上传出错。
        uploaderLogo.on('uploadError', function (file) {
            var $li = $('#' + file.id),
                $error = $li.find('div.error');

            // 避免重复创建
            if (!$error.length) {
                $error = $('<div class="error"></div>').appendTo($li);
            }

            $error.text('上传失败');
        });

        // 完成上传完了，成功或者失败，先删除进度条。
        uploaderLogo.on('uploadComplete', function (file) {
            $('#' + file.id).find('.progress').remove();
        });

    }).on('hidden.bs.modal', function (e) {
        $('#fileList').empty();
        uploaderLogo.destroy();
        uploaderTheme.destroy();
        uploaderSmall.destroy();
    });

    $("#ctlBtn").on("click", function () {
        uploader.upload();
    })


    var uploaderAssem;
    $('#modalAssem').on('shown.bs.modal', function (e) {        
        var $ = jQuery,
        $list = $('#fileListAss'),

        ratio = window.devicePixelRatio || 1,

        thumbnailWidth = 100 * ratio,
        thumbnailHeight = 100 * ratio;
        $('#filePickerAss').empty();
        $('#filePickerAss').html("点击上传");
        uploaderAssem = WebUploader.create({
            auto: true,
            // swf文件路径
            swf: '/Plugins/webuploader/Uploader.swf',
            // 文件接收服务端。
            server: '/System/CompanyBrand/AddPhoto',
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#filePickerAss',
            // 只允许选择图片文件。
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            },
            duplicate :true ,
            fileNumLimit: 1
        });

        uploaderAssem.on('fileQueued', function (file) {

            //var $li = $(
            //        '<div id="' + file.id + '" class="file-item thumbnail">' +
            //            '<img>' +
            //            '<div class="info">' + file.name + '</div>' +
            //        '</div>'
            //        ),
            //    $img = $li.find('img');

            //// $list为容器jQuery实例
            //$list.append($li);
            //// 创建缩略图
            //// 如果为非图片文件，可以不用调用此方法。
            //// thumbnailWidth x thumbnailHeight 为 100 x 100
            
            //uploaderAssem.makeThumb(file, function (error, src) {
            //    if (error) {
            //        $img.replaceWith('<span>不能预览</span>');
            //        return;
            //    }
            //    $img.attr('src', src);
            //}, thumbnailWidth, thumbnailHeight);
        });

        // 文件上传过程中创建进度条实时显示。
        uploaderAssem.on('uploadProgress', function (file, percentage) {
            var $li = $('#' + file.id),
                $percent = $li.find('.progress span');

            // 避免重复创建
            if (!$percent.length) {
                $percent = $('<p class="progress"><span></span></p>')
                        .appendTo($li)
                        .find('span');
            }
            $percent.css('width', percentage * 100 + '%');
        });

        // 文件上传成功，给item添加成功class, 用样式标记上传成功。
        uploaderAssem.on('uploadSuccess', function (file, data) {
            $('#' + file.id).addClass('upload-state-done');
            $('#assPhoto').val(data.Msg);
            $('#imgAssPhoto').prop("src", data.Msg);
            uploaderAssem.removeFile(file);
            var obj = new Object();
            obj.imgPath = $('#assPhoto').val();
            uploaderAssem.option.formData = obj;
        });

        // 文件上传失败，显示上传出错。
        uploaderAssem.on('uploadError', function (file) {
            var $li = $('#' + file.id),
                $error = $li.find('div.error');

            // 避免重复创建
            if (!$error.length) {
                $error = $('<div class="error"></div>').appendTo($li);
            }

            $error.text('上传失败');
        });

        // 完成上传完了，成功或者失败，先删除进度条。
        uploaderAssem.on('uploadComplete', function (file) {
            $('#' + file.id).find('.progress').remove();
        });


    }).on('hidden.bs.modal', function (e) {
        uploaderAssem.destroy();

    });


    $('#AssemListModal').on('shown.bs.modal', function (e) {

        $('#dataTableAssem').bootstrapTable({
            url: "/System/CompanyBrand/GetAssem",
            pagination: "true",
            queryParams: queryParamsAssem,
            sidePagination: 'server',
            onReorderRow: onReorderRow,
            clickToSelect: true,
            columns: [
                {
                    title: '操作',
                    field: 'Id',
                    formatter: 'operateAssem',
                    events: 'operateEvents',
                    width: "50px"
                },
                {
                    field: 'assemName',
                    title: '套件名称'
                },
                {
                    field: 'assemTypeName',
                    title: '套件类型'
                },
                {
                    field: 'assemSign',
                    title: '套件标识'
                },
                {
                    field: 'assemVersion',
                    title: '套件版本'
                }, {
                    field: 'strIsCore',
                    title: '是否为核心板'
                },
                {
                    field: 'apiUrl',
                    title: 'API地址'
                },
                {
                    field: 'androidAddress',
                    title: '下载地址'
                },
                {
                    field: 'assemCount',
                    title: '使用人数'
                },
                {
                    title: '显示图',
                    field: 'photoAddress',
                    formatter: 'PhotoFormatter',
                    width: "100px"
                },
                {
                    field: 'strCreateTime',
                    title: '创建时间'
                }
            ]
        });
    }).on('hidden.bs.modal', function (e) {
        $("#alBrandId").val("");
        $('#dataTableAssem').bootstrapTable('destroy');
    });
})

function queryParamsAssem(params) {
    return {
        brandId: $("#alBrandId").val(),
        PageSize: params.limit,   //页面大小
        PageIndex: params.pageNumber,//  sort: params.sort,  //排序列名
        sortOrder: params.order,
        SkipCount: params.offset,
    };
}

function queryParams(params) {
    return {
        PageSize: params.limit,   //页面大小
        PageIndex: params.pageNumber,//  sort: params.sort,  //排序列名
        sortOrder: params.order,
        SkipCount: params.offset,
    };
}


$("#ctlAssBtn").on("click", function () {
    if (asForm.valid()) {
        $.SubAjax({
            type: 'post',
            data: {
                brandId: $("#brandAssem").val(),
                assemId: $("#assemId").val(),
                assemName: $("#assumName").val(),
                assemVersion: $("#assumVersion").val(),
                assemTpye: $("#assumTpye").val(),
                assemSign: $("#assumSign").val(),
                androidDown: $("#androidDown").val(),
                iosDown: $("#iosDown").val(),
                androidKey: $("#androidKey").val(),
                iosKey: $("#iosKey").val(),
                strIsCore: $("#isCore").val(),
                apiUrl: $("#apiUrl").val(),
                photoAddress: $("#assPhoto").val()
            },
            url: '/System/CompanyBrand/AddAssem',
            success: function (data) {
                if (data.IsSuccess) {
                    $("#modalAssem").modal('hide');
                    $.ShowMessage("success", "保存成功");
                    $('#dataTable').bootstrapTable("refresh");
                    $('#dataTableAssem').bootstrapTable("refresh");
                }
                else {
                    $.ShowMessage("error", data.Msg);
                }
            }
        })
    }
})

var array = new Array();
function onReorderRow(newData) {
    if (newData.length > 1)
    {
        $("#btnSort").show();
        var arr = new Array();
        $.each(newData, function (index, item) {
            arr.push(item.assemId);
        });
        array = arr;
    }
}

function PhotoFormatter(value, row, index) {
    var results = '';
    if(value != null){
        results = ' <img src=' + value + ' height="30" width="80"  />';
    }
    return results;
}

function operateAssem(value, row, index) {
    var res = '<a class="editAssem" data-role="Admin" title="编辑">编辑</a>'
    res += ' <a class="deleteAssem" data-role="Admin" title="删除">删除</a>'
    return res;
}

function operateFormatter(value, row, index) {
    return [
        '<a class="edit" data-role="Admin" title="编辑">编辑</a>',
        '<a class="add" data-role="Admin" title="增加套件" style="margin-left:10px">增加套件</a>',
        '<a class="list" data-role="Admin" title="套件列表" style="margin-left:10px">套件列表</a>',
    ].join('');
}

window.operateEvents = {
    'click .edit': function (e, value, row, index) {
        $("#btnUpdate").show();
        $("#btnAdd").hide();
        $("#CompanyInfoPartialModal").modal({ backdrop: 'static', keyboard: false });
        $("#companyName").val(row.name);
        $("#companyId").val(row.id);
        $("#companyCode").val(row.code);
        $("#helpId").val(row.helpId);
        $("#msgUrl").val(row.msgUrl);
        $("#manageUrl").val(row.manageUrl);
        $("#ISGCode").val(row.ISGCode);
        $("#secret").val(row.secret);
        $("#appKey").val(row.appKey);
        $("#wise").val(row.wise);
        if (row.logoPhoto != null){
            $("#imgLogoPhoto").prop("src", row.logoPhoto);
            $("#piclogo").val(row.logoPhoto);
        }
        else{
            $("#imgLogoPhoto").prop("src", "");
            $("#piclogo").val("");
        }
        if (row.themePhoto != null) {
            $("#imgThemePhoto").prop("src", row.themePhoto);
            $("#pictheme").val(row.themePhoto);
        }
        else {
            $("#imgThemePhoto").prop("src", "");
            $("#pictheme").val("");
        }
        if (row.smallPhoto != null) {
            $("#imgSmallPhoto").prop("src", row.smallPhoto);
            $("#picSmall").val(row.smallPhoto);
        }
        else {
            $("#imgSmallPhoto").prop("src", "");
            $("#picSmall").val("");
        }
           
    },

    'click .add': function (e, value, row, index){
        $("#modalAssem").modal({ backdrop: 'static', keyboard: false });        
        $("#brandAssemName").val(row.name);
        $("#brandAssem").val(row.id);
        $("#assemId").val("");
        $("#assumName").val("");
        $("#assumTpye").val("");
        $("#assumVersion").val("");
        $("#assumSign").val("");
        $("#androidDown").val("");
        $("#iosDown").val("");
        $("#androidKey").val("");
        $("#iosKey").val("");
        $("#assPhoto").val("");
        $("#imgAssPhoto").prop("src", "");

        $("#isCore").val("0");
        $("#apiUrl").val("");
        $("#divApiUrl").hide();
        $("#divAddress").show();
    },

    'click .editAssem': function (e, value, row, index) {
        $("#modalAssem").modal({ backdrop: 'static', keyboard: false });
        $("#brandAssemName").val(row.cbName);
        $("#assemId").val(row.assemId);
        $("#brandAssem").val(row.cbId);
        $("#assumName").val(row.assemName);
        $("#assumTpye").val(row.assemType);
        $("#assumVersion").val(row.assemVersion);
        $("#assumSign").val(row.assemSign);
        $("#androidDown").val(row.androidAddress);
        $("#iosDown").val(row.iosAddress);
        $("#androidKey").val(row.androidKey);
        $("#iosKey").val(row.iosKey);
        $("#isCore").val(row.isCore==true?1:0);
        $("#isCore").change();
        $("#apiUrl").val(row.apiUrl);
        if (row.photoAddress!=null) {
             $("#assPhoto").val(row.photoAddress);
            $("#imgAssPhoto").prop("src", row.photoAddress);
        } else {
            $("#assPhoto").val("");
            $("#imgAssPhoto").prop("src", "");
        }
       
    },

    'click .list': function (e, value, row, index) {
        $("#alBrandId").val(row.id)
        $("#btnSort").hide();
        $("#AssemListModal").modal({ backdrop: 'static', keyboard: false });
    },

    'click .deleteAssem': function (e, value, row, index) {
        parent.layer.confirm('确定删除该套件吗？', {
            btn: ['确定', '取消'], //按钮
            shade: false //不显示遮罩
        }, function () {
            $.SubAjax({
                type: 'post',
                url: '/System/CompanyBrand/DeleteAssem',
                data: { aId: row.assemId },
                success: function (data) {
                    if (data.IsSuccess) {
                        $.ShowMessage("success", data.Msg);
                        $('#dataTableAssem').bootstrapTable("refresh");
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

}
$("#btnSearch").on("click", function () {
    $('#dataTable').bootstrapTable("refresh");
})

$("#btnUpdate").on("click", function () {

    $.SubAjax({
        type: 'post',
        data: {
            id: $("#companyId").val(),
            name: $("#companyName").val(),
            code: $("#companyCode").val(),
            helpId: $("#helpId").val(),
            msgUrl: $("#msgUrl").val(),
            manageUrl: $("#manageUrl").val(),
            isgCode: $("#ISGCode").val(),
            wise: $("#wise").val(),
            appKey: $("#appKey").val(),
            secret: $("#secret").val(),
            smallPhoto: $("#picSmall").val(),
            logoPhoto: $("#piclogo").val(),
            themePhoto: $("#pictheme").val()
        },
        url: '/companyBrand/updateBrand',
        success: function (data) {
            if (data.SUCCESS==0) {
                $("#CompanyInfoPartialModal").modal('hide');
                $.ShowMessage("success", "保存成功");
                $('#dataTable').bootstrapTable("refresh");

            }
            else {
                $.ShowMessage("error", data.Msg);
            }
        }
    })
})

$("#btnNew").on("click", function () {
    $(".select2").select2();
    $("#btnUpdate").hide();
    $("#btnAdd").show();
    $("#companyName").val("");
    $("#companyId").val("");
    $("#companyCode").val("");
    $("#companyId").val("");
    $("#helpId").val("");
    $("#msgUrl").val("");
    $("#manageUrl").val("");
    $("#ISGCode").val("");
    $("#secret").val("");
    $("#appKey").val("");
    $("#wise").val("");
    $("#imgSmallPhoto").prop("src", "");
    $("#imgThemePhoto").prop("src", "");
    $("#imgLogoPhoto").prop("src", "");
    $("#piclogo").val("");
    $("#pictheme").val("");
    $("#picSmall").val("");
    $("#CompanyInfoPartialModal").modal({ backdrop: 'static', keyboard: false });

})

$("#btnAdd").on("click", function () {

     var companyData={
         name: $("#companyName").val(),
        code: $("#companyCode").val(),
        helpId: $("#helpId").val(),
        msgUrl: $("#msgUrl").val(),
        manageUrl: $("#manageUrl").val(),
        isgCode: $("#ISGCode").val(),
        wise: $("#wise").val(),
        appKey: $("#appKey").val(),
        secret: $("#secret").val(),
        smallPhoto: $("#picSmall").val(),
        logoPhoto: $("#piclogo").val(),
        themePhoto: $("#pictheme").val()
     };
    $.SubAjax({
        type: 'post',
        data: JSON.stringify(companyData),
        dataType: 'json',
        url: '/companyBrand/addBrand',
        contentType:'application/json',
        success: function (data) {
            if (data.SUCCESS==0) {
                $("#CompanyInfoPartialModal").modal('hide');
                $.ShowMessage("success", "保存成功");
                $('#dataTable').bootstrapTable("refresh");
            }
            else {
                $.ShowMessage("error", data.Msg);
            }
        }
    })
})

var asForm = $("#AssemForm");
asForm.validate({
    rules: {
        assumName: {
            required: true
        },
        assumVersion: {
            required: true
        },
        assumSign:
        {
            required: true
        }
    },
    messages: {
        assumName: {
            required: "请输入套件名",
        },
        assumVersion: {
            required: "请输入套件版本",
        },
        assumSign:
        {
            required: "请输入套件标识",
        }
    }
})


$("#btnSort").on("click", function () {
    $.SubAjax({
        type: 'post',
        data: {
           arr:array
        },
        url: '/System/CompanyBrand/setAssSort',
        success: function (data) {
            if (data.IsSuccess) {
                $.ShowMessage("success", "保存成功");               
            }
            else {
                $.ShowMessage("error", data.Msg);
            }
        }
    })
})
