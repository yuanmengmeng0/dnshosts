<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/6/17
  Time: 16:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>解析列表</title>
    <link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <script src="js/jquery-3.3.1.min.js"></script>
    <script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <style type="text/css">
        tr th{
            text-align: center;
        }
        .center{
            text-align: center !important;
        }
    </style>
</head>
<body>
<div>
    <table class="table table-bordered">
        <thead>
        <tr>
            <td colspan="11">
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#createModal">创建解析</button>
                    <button type="button" class="btn btn-primary" onclick="importHost();" data-toggle="modal" data-target="#importModal">导入hosts文件</button>
                    <button type="button" class="btn btn-default" style="float: right;margin-right: 15em;">
                        <a href="javascript:history.go(-1);">返回</a>
                    </button>
                <form class="form-inline" role="form" style="float: right;margin-right: 70em;margin-top: -2em;">
                    <input type="text" class="form-control" id="ipAddress" name="ipAddress" placeholder="ip地址" value="${vo.ipAddress}">
                    <input type="text" class="form-control" id="hostName" name="hostNames" placeholder="host名称" value="${vo.hostNames}">
                    <button type="button" class="btn btn-primary" onclick="select();">查询</button>
                </form>
            </td>
        </tr>
        <tr>
            <th>host_id</th>
            <th>group_id</th>
            <th>版本</th>
            <th>ip地址</th>
            <th>host名称</th>
            <th>注释</th>
            <th>状态</th>
            <th>排序</th>
            <th>修改人</th>
            <th>修改日期</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${hosts.results}" var="vo">
        <tr style="text-align: center;">
            <td>${vo.hostsId}</td>
            <td>${vo.groupId}</td>
            <td>
                <c:if test="${vo.ipVersion ==4}">IPV4</c:if>
                <c:if test="${vo.ipVersion ==6}">IPV6</c:if>
            </td>
            <td>${vo.ipAddress}</td>
            <td>${vo.hostNames}</td>
            <td>${vo.memo}</td>
            <td>
                <c:if test="${vo.state ==0}">已禁用</c:if>
                <c:if test="${vo.state ==1}">已启用</c:if>
            </td>
            <td>${vo.listOrder}</td>
            <td>${vo.modUserName}</td>
            <td>
                <fmt:formatDate value="${vo.modDate}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
            </td>
            <td>
                <span data-toggle="modal" data-target="#modifyModal" style="cursor: pointer" onclick="modify(${vo.hostsId});">修改</span>
                <span style="cursor: pointer" onclick="isOpen(${vo.hostsId});">
                        <c:if test="${vo.state == 0}">启用</c:if>
                        <c:if test="${vo.state == 1}">禁用</c:if>
                </span>
                <span onclick="del(${vo.hostsId});" style="cursor: pointer;">删除</span>
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
                    <c:if test="${hosts.pageNo==1}">
                        <li class="disabled"><a href="javascript:void(0);">&laquo;</a></li>
                    </c:if>
                    <c:if test="${hosts.pageNo!=1}">
                        <li><a href="javascript:void(0);" onclick="demand('${hosts.pageNo-1}')">&laquo;</a></li>
                    </c:if>
                    <c:forEach items="${hosts.pageNos}" var="pno">
                        <c:if test="${pno == hosts.pageNo}">
                            <li class="active"><a href="javascript:void(0);" onclick="demand('${pno}')">${pno}</a></li>
                        </c:if>
                        <c:if test="${pno != hosts.pageNo}">
                            <li><a href="javascript:void(0);" onclick="demand('${pno}')">${pno}</a></li>
                        </c:if>
                    </c:forEach>
                    <c:if test="${hosts.pageNo>=hosts.totalPage}">
                        <li class="disabled"><a href="javascript:void(0);">&raquo;</a></li>
                    </c:if>
                    <c:if test="${hosts.pageNo<hosts.totalPage}">
                        <li><a href="javascript:void(0);" onclick="demand('${hosts.pageNo+1}')">&raquo;</a></li>
                    </c:if>
                    <li><a href="javascript:void(0);" onclick="demand('${hosts.totalPage}')">&raquo;&raquo;</a></li>
                    <li style="position: relative;top: 6px;left: 1em;">
                        共${hosts.totalRecord}条记录,第${hosts.pageNo}/${hosts.totalPage}页,每页${hosts.pageSize}条
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
                    创建解析
                </h4>
            </div>
            <form id="form" class="bs-example bs-example-form" role="form" method="post" action="">
                <div class="modal-body">
                    <table class="table table-bordered">
                        <tr>
                            <td>
                                <span>版本：</span>
                                <select id="ipVersion2" name="ipVersion">
                                    <option  value="4">IPV4</option>
                                    <option  value="6">IPV6</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="input-group">
                                    <span class="input-group-addon">ip地址</span>
                                    <input type="text" id="ipAddress2" name="ipAddress" class="form-control" value="">
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="input-group">
                                    <span class="input-group-addon">解析域名</span>
                                    <textarea id="hostNames2" style="width: 470px;line-height: 20px;" name="hostNames"></textarea>
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
                        <tr>
                            <td>
                                <div class="input-group">
                                    <span class="input-group-addon">备注</span>
                                    <textarea  id="memo2" name="memo" style="width: 498px;line-height: 20px;"></textarea>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
                <input type="hidden" id="groId" name="groupId">
                <div class="modal-footer" style="text-align: center">
                    <button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
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
                    修改解析
                </h4>
            </div>
            <form id="form1" class="bs-example bs-example-form" role="form" method="post" action="/dnshosts/hostModify">
                <div class="modal-body">
                    <table class="table table-bordered">
                        <tr>
                            <td>
                                <span>版本：</span>
                                <select id="ipVersion1" name="ipVersion">
                                    <option  value="4">IPV4</option>
                                    <option  value="6">IPV6</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="input-group">
                                    <span class="input-group-addon">ip地址</span>
                                    <input id="address1" type="text" name="ipAddress" class="form-control" value="">
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="input-group">
                                    <span class="input-group-addon">解析域名</span>
                                    <textarea id="hostName1" style="width: 470px;line-height: 33px;" name="hostNames"></textarea>
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
                        <tr>
                            <td>
                                <div class="input-group">
                                    <span class="input-group-addon">备注</span>
                                    <input id="memo1" type="text" name="memo" class="form-control" value="">
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="modal-footer" style="text-align: center">
                    <input type="hidden" id="hostId" name="hostsId">
                    <button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
                    <input type="button" value="保存" class="btn btn-primary" onclick="sub1();"/>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>



<div class="modal fade" id="importModal" tabindex="-1" role="dialog" aria-labelledby="importModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="importModalLabel">
                    导入hosts文件
                </h4>
            </div>
            <form id="importForm" class="bs-example bs-example-form" role="form" method="post" action="">
                <div class="modal-body">
                  <textarea id="importHost" style="width: 570px;height: 350px;"></textarea>
                </div>
                <div class="input-group">
                    <span class="input-group-addon">备注</span>
                    <input id="memo3" type="text" name="memo" class="form-control" value="">
                </div>
                <div class="modal-footer" style="text-align: center">
                    <button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
                    <input type="button" value="保存" class="btn btn-primary" onclick="importSub();"/>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</body>
<script type="text/javascript">
    /*初始化*/
    jQuery(function($) {
        var str=${param.get("groupId")};
         $("#groId").val(str);
    });

    function demand(pageNo) {
        window.location.href="/dnshosts/showAnalysis?groupId=${param.get('groupId')}&pageNo="+pageNo+
            "&ipAddress=${param.get('ipAddress')}&hostNames=${param.get('hostNames')}";
    }

    /*保存导入的数据*/
    function importSub() {
        var ipHost=$("#importHost").val();
        var memo=$("#memo3").val();
        var groupId=${param.get("groupId")};
        $.ajax({
            url:"/dnshosts/saveImport",
            type:"post",
            dataType:"json",
            data:{
                groupId:groupId,
                ipHost:ipHost,
                memo:memo
            },
            success:function (data) {
                if(data.code == 1){
                    window.location.reload();
                }else{
                    alert(data.mes);
                }
            }
        })
    }

    function sub() {
        var groId=$("#groId").val();
        var version=$("#ipVersion2").val();
        var address=$("#ipAddress2").val();
        var hostname=$("#hostNames2").val();
        var memo=$("#memo2").val();
        var state=$("input:radio:checked").val();
        $.ajax({
            url:"/dnshosts/hostSubmit",
            type:"post",
            dataType:"json",
            async : false,
            data:{
                groupId:groId,
                ipVersion:version,
                ipAddress:address,
                hostNames:hostname,
                memo:memo,
                state: state
            },
            success:function (data) {
                if(data.code == 1){
                    window.location.href="/dnshosts/showAnalysis?groupId="+groId+"&ipAddress=&hostNames=";
                }else{
                    alert(data.mes);
                }
            }
        })
    }

    function sub1() {
        var hostId=$("#hostId").val();
        var version=$("#ipVersion1").val();
        var address=$("#address1").val();
        var hostname=$("#hostName1").val();
        var memo=$("#memo1").val();
        var state=$("input:radio:checked").val();
        var groId=${param.get("groupId")};
        $.ajax({
            url:"/dnshosts/hostModify",
            type:"post",
            dataType:"json",
            async : false,
            data:{
                groupId:groId,
                hostsId:hostId,
                ipVersion:version,
                ipAddress:address,
                hostNames:hostname,
                memo:memo,
                state: state
            },
            success:function (data) {
                if(data.code == 1){
                    window.location.href="/dnshosts/showAnalysis?groupId="+groId+"&ipAddress=${param.get('ipAddress')}&hostNames=${param.get('hostNames')}"+"&pageNo=${param.get('pageNo')}";
                }else{
                    alert(data.mes);
                }
            }
        })
    }

    /*是否开启还是禁用*/
    function isOpen(id) {
        $.ajax({
            url:"/dnshosts/isOpen",
            type:"post",
            dataType:"json",
            async : false,
            data:{
                hostsId:id
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
            url:"/dnshosts/delHost",
            type:"post",
            dataType:"json",
            async : false,
            data:{
                hostsId:id
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
    /*导入要显示的数据*/
    function importHost(){
        $.ajax({
            url:"/dnshosts/importData",
            type:"post",
            dataType:"json",
            async : false,
            success:function (data) {
                $.each(data,function (i,item) {
                    var temp=item
                    $("#importHost").html(temp);
                })

            }
        });
    }


    /*修改显示的数据*/
    function modify(id) {
        $.ajax({
            url:"/dnshosts/revise",
            type:"post",
            dataType:"json",
            async : false,
            data:{
                hid:id
            },
            success:function (data) {
                if(data.hvo.ipVersion == 4){
                    $("option[value='4']").attr("selected",'true');
                }else{
                    $("option[value='6']").attr("selected",'true');
                }
                $("#hostId").val(data.hvo.hostsId);
                $("#address1").val(data.hvo.ipAddress);
                $("#hostName1").val(data.hvo.hostNames);
                var obj=document.getElementsByName("state");
                for(var i=0;i<obj.length;i++){
                    if(obj[i].value == data.hvo.state){
                        obj[i].checked = 'checked';
                    }
                }
                $("#memo1").val(data.hvo.memo);
            }
        });
    }

    function select() {
        var gid=${param.get("groupId")};
        var address=$("#ipAddress").val();
        var hostname=$("#hostName").val();
        window.location.href="/dnshosts/showAnalysis?groupId="+gid+"&ipAddress="+address+"&hostNames="+hostname;
    }

</script>
</html>
