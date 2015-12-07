function setPagination(tableId) {
    var p = $(tableId).datagrid('getPager');
    $(p).pagination({
        pageSize: 20,
        pageList: [10, 15, 20, 25],
        beforePageText: '第',
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        onBeforeRefresh: function () {
            $(this).pagination('loading');
            $(this).pagination('loaded');
        }
    });
}