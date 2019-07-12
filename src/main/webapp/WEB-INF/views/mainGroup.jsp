<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/6/17
  Time: 16:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>组列表</title>
    <link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <script src="js/jquery-3.3.1.min.js"></script>
    <script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <style type="text/css">
        tr th{
            text-align: center;
        }
        .center{
            text-align:center!important
        }
    </style>
</head>
<body>
<div>
    <table class="table table-bordered">
        <thead>
        <tr>
            <td colspan="7">
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#createModal">创建组</button>

                  <a href="/dnshosts/export"><button type="button" class="btn btn-primary"> 导出hosts文件 </button></a>
                  <a href="/dnshosts/showLog"><button type="button" class="btn btn-primary">日志管理</button></a>

                <form class="form-inline" role="form" style="float: right;margin-right: 80em;">
                    <input type="text" class="form-control" name="groName" placeholder="组名称" value="${vo.groupName}">
                    <button type="button" class="btn btn-primary" onclick="select();">查询</button>
                </form>
            </td>
        </tr>
        <tr>
            <th>id</th>
            <th>组名称</th>
            <th>状态</th>
            <th>排序</th>
            <th>修改人</th>
            <th>修改日期</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${groupVo.results}" var="vo">
            <tr style="text-align: center;">
                <td>${vo.groupId}</td>
                <td>${vo.groupName}</td>
                <td>
                    <c:if test="${vo.state == 0}">已禁用</c:if>
                    <c:if test="${vo.state == 1}">已启用</c:if>
                </td>
                <td>${vo.listOrder}</td>
                <td>${vo.modUserName}</td>
                <td>
                    <fmt:formatDate value="${vo.modDate}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                </td>
                <td>
                    <span data-toggle="modal" data-target="#modifyModal" style="cursor: pointer" onclick="modify(${vo.groupId});">修改</span>
                    <span style="cursor: pointer;">
                       <a href="/dnshosts/showAnalysis?groupId=${vo.groupId}"/>解析管理</a>
                    </span>
                    <span style="cursor: pointer" onclick="isOpen(${vo.groupId});">
                        <c:if test="${vo.state == 0}">启用</c:if>
                        <c:if test="${vo.state == 1}">禁用</c:if>
                    </span>
                    <span onclick="del(${vo.groupId});" style="cursor: pointer;">删除</span>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="row">
        <div class="col-xs-12">
            <div class="center" id="dynamic-table_paginate">
                <ul class="pagination">
                    <li><a href="javascript:void(0);" onclick="demand('1')">&laquo;&laquo;</a></li>
                    <c:if test="${groupVo.pageNo==1}">
                        <li class="disabled"><a href="javascript:void(0);">&laquo;</a></li>
                    </c:if>
                    <c:if test="${groupVo.pageNo!=1}">
                        <li><a href="javascript:void(0);" onclick="demand('${groupVo.pageNo-1}')">&laquo;</a></li>
                    </c:if>
                    <c:forEach items="${groupVo.pageNos}" var="pno">
                        <c:if test="${pno == groupVo.pageNo}">
                            <li class="active"><a href="javascript:void(0);" onclick="demand('${pno}')">${pno}</a></li>
                        </c:if>
                        <c:if test="${pno != groupVo.pageNo}">
                            <li><a href="javascript:void(0);" onclick="demand('${pno}')">${pno}</a></li>
                        </c:if>
                    </c:forEach>
                    <c:if test="${groupVo.pageNo>=groupVo.totalPage}">
                        <li class="disabled"><a href="javascript:void(0);">&raquo;</a></li>
                    </c:if>
                    <c:if test="${groupVo.pageNo<groupVo.totalPage}">
                        <li><a href="javascript:void(0);" onclick="demand('${groupVo.pageNo+1}')">&raquo;</a></li>
                    </c:if>
                    <li><a href="javascript:void(0);" onclick="demand('${groupVo.totalPage}')">&raquo;&raquo;</a></li>
                    <li style="position: relative;top: 6px;left: 1em;">
                        共${groupVo.totalRecord}条记录,第${groupVo.pageNo}/${groupVo.totalPage}页,每页${groupVo.pageSize}条
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="createModal" tabindex="-1" role="dialog" aria-labelledby="createModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="createModalLabel">
                    创建组
                </h4>
            </div>
            <form id="form" class="bs-example bs-example-form" role="form" method="post" action="/dnshosts/groupSubmit">
                <div class="modal-body">
                    <table class="table table-bordered">
                        <tr>
                            <td>
                                <div class="input-group">
                                    <span class="input-group-addon">组名称</span>
                                    <input id="gname" type="text" name="groupName" class="form-control" value="">
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div style="text-align: center">
            <span>
            <input type="radio" name="state" value="1" >启用
            </span>
                                    <span style="padding-left: 10em;">
            <input type="radio" name="state" value="0">禁用
            </span>
                                </div>
                            </td>
                        </tr>
                        <%-- <tr>
                             <td style="text-align: center;">
                                 <button class="btn btn-primary">保存</button>
                                 <button class="btn btn-primary">返回</button>
                             </td>
                         </tr>--%>
                    </table>
                </div>
                <div class="modal-footer" style="text-align: center">
                    <button type="button" class="btn btn-default" data-dismiss="modal">返回
                    </button>
                    <input type="button" value="保存" class="btn btn-primary" onclick="sub();"/>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<div class="modal fade" id="modifyModal" tabindex="-1" role="dialog" aria-labelledby="modifyModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="modifyModalLabel">
                    修改组
                </h4>
            </div>
            <form id="form1" class="bs-example bs-example-form" role="form" method="post" action="/dnshosts/groupModify">
                <div class="modal-body">
                    <table class="table table-bordered">
                        <tr>
                            <td>
                                <div class="input-group">
                                    <span class="input-group-addon">组名称</span>
                                    <input id="name1" type="text" name="groupName" class="form-control" value="">
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div style="text-align: center">
            <span>
            <input type="radio" name="state" value="1" id="open">启用
            </span>
                                    <span style="padding-left: 10em;">
            <input type="radio" name="state" value="0" id="close">禁用
            </span>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="modal-footer" style="text-align: center">
                    <button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
                    <input type="hidden" value="" name="groupId" id="id1">
                    <input type="hidden" value="${param.get('pageNo')}" name="pageNo">
                    <input type="button" value="保存" class="btn btn-primary" onclick="sub1();"/>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</body>
<script type="text/javascript">
    function demand(pageNo) {
        window.location.href="/dnshosts/showGroup?pageNo="+pageNo+"&groName=${param.get('groName')}";
    }
    function select() {
        var gname=$("input[name='groName']").val();
        window.location.href="/dnshosts/showGroup?groName="+gname+"&pageNo=";
        /*$.ajax({
            url:"/dnshosts/showGroup",
            type:"post",
            dataType:"json",
            data:{
                groName:gname
            }
        });*/
    }

    /*创建开始*/
    function checkForm() {
         var canSub=true;
         var gname=$("#gname").val();
        if(gname == ""){
             alert("组名称不能为空");
             canSub=false;
         }
         return canSub;

    }
    function sub() {
        if(checkForm()){
            $("#form").submit();
        }
    }
    /*结束*/
    /*修改开始*/
    function checkForm1() {
        var canSub=true;
        var name1=$("#name1").val();
        if(name1 == ""){
            alert("组名称不能为空");
            canSub=false;
        }
        return canSub;

    }
    function sub1() {
        if(checkForm1()){
            $("#form1").submit();
        }
    }
    /*结束*/

    /*修改显示的数据*/
    function modify(id) {
        $.ajax({
            url:"/dnshosts/modify",
            type:"post",
            dataType:"json",
            async : false,
            data:{
                gid:id
            },
            success:function (data) {
                $("#id1").val(data.gvo.groupId);
                $("#name1").val(data.gvo.groupName);
                var obj=document.getElementsByName("state");
                for(var i=0;i<obj.length;i++){
                    if(obj[i].value == data.gvo.state){
                        obj[i].checked = 'checked';
                    }
                }
            }
        });
    }
    /*是否开启还是禁用*/
    function isOpen(id) {
        $.ajax({
            url:"/dnshosts/updateGroup",
            type:"post",
            dataType:"json",
            async : false,
            data:{
                groupId:id
            },
            success:function (result) {
                if(result.code == 1){
                    window.location.reload();
                }
            }
        });
    }
/*删除*/
    function del(id) {
        $.ajax({
            url:"/dnshosts/delGroup",
            type:"post",
            dataType:"json",
            async : false,
            data:{
                groupId:id
            },
            success:function (result) {
                if(result.code == 1){
                    window.location.reload();
                }else{
                    alert("删除失败")
                }
            }
        });
    }
</script>
</html>
