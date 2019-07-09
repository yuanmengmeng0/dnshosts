<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/7/9
  Time: 13:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>日志列表</title>
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
    <style>
        .top {
            position: absolute;
            top: 0;
            left: 0;
            width: /*1400px*/100%;
            height: 80px;
            background-color: rgba(3, 124, 224, 1);
            border: 1px solid rgba(201, 201, 201, 1);
            border-radius: 0px;
            font-family: 'Arial Negreta', 'Arial Normal', 'Arial';
            font-weight: 700;
            font-style: normal;
            line-height: 80px;
            font-size: 18px;
            z-index: 999;
        }

        .uname {
            margin-right: 10px;
            right: 80px;
            position: absolute;
            font-size: 14px;
            color: white;
        }

        .logout {
            margin-right: 10px;
            right: 30px;
            position: absolute;
            font-family: 'Arial Negreta', 'Arial Normal', 'Arial';
            font-weight: 700;
            font-style: normal;
            text-decoration: underline;
            color: #FF9900;
            font-size: 13px;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="top">
    　<span style="color: white">dns管理系统</span>
    <span class="uname">
        <span id="user_name">aa</span>
    </span>
    <span id="logout" class="logout" onclick="logOut()">退出</span>
</div>
<div style="width: 100%;min-height: 900px;position: relative;top: 5.5em;">
    <div style="background-color: #5bc0de;width: 10%;height: 900px;float: left;">
    <div class="panel-group" id="accordion">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4 class="panel-title">
                    <a data-toggle="collapse" data-parent="#accordion"
                       href="#collapseOne">
                        日志管理
                    </a>
                </h4>
            </div>
            <div id="collapseOne" class="panel-collapse collapse in">
                <div class="panel-body">
                   <span id="log" style="cursor: pointer;margin-left: 1em;">日志列表</span>
                </div>
                <div class="panel-body">
                    <sapn id="back" style="cursor: pointer;margin-left: 1em;">回收站</sapn>
                </div>
            </div>
        </div>
    </div>
</div>
    <div style="background-color: #d9edf7;width: 90%;height: 900px;float: right;">
        <div id="a" style="display: block;">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>id</th>
                    <th>日志时间</th>
                    <th>类型</th>
                    <th>类型ID</th>
                    <th>日志细节</th>
                    <th>修改人ID</th>
                </tr>
                </thead>
                <tbody>
                   <c:forEach items="${page.results}" var="vo">
                       <tr style="text-align: center">
                           <td>${vo.logId}</td>
                           <td>
                               <fmt:formatDate value="${vo.logDate}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                           </td>
                           <td>${vo.objectType}</td>
                           <td>${vo.objectId}</td>
                           <td>${vo.logDetail}</td>
                           <td>${vo.modUserId}</td>
                       </tr>
                   </c:forEach>
                </tbody>
            </table>
            <div class="row">
                <div class="col-xs-12">
                    <div class="center" id="dynamic-table_paginate">
                        <ul class="pagination">
                            <li><a href="javascript:void(0);" onclick="demand('1')">&laquo;&laquo;</a></li>
                            <c:if test="${page.pageNo==1}">
                                <li class="disabled"><a href="javascript:void(0);">&laquo;</a></li>
                            </c:if>
                            <c:if test="${page.pageNo!=1}">
                                <li><a href="javascript:void(0);" onclick="demand('${page.pageNo-1}')">&laquo;</a></li>
                            </c:if>
                            <c:forEach items="${page.pageNos}" var="pno">
                                <c:if test="${pno == page.pageNo}">
                                    <li class="active"><a href="javascript:void(0);" onclick="demand('${pno}')">${pno}</a></li>
                                </c:if>
                                <c:if test="${pno != page.pageNo}">
                                    <li><a href="javascript:void(0);" onclick="demand('${pno}')">${pno}</a></li>
                                </c:if>
                            </c:forEach>
                            <c:if test="${page.pageNo>=page.totalPage}">
                                <li class="disabled"><a href="javascript:void(0);">&raquo;</a></li>
                            </c:if>
                            <c:if test="${page.pageNo<page.totalPage}">
                                <li><a href="javascript:void(0);" onclick="demand('${page.pageNo+1}')">&raquo;</a></li>
                            </c:if>
                            <li><a href="javascript:void(0);" onclick="demand('${page.totalPage}')">&raquo;&raquo;</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div id="b" style="display: none">fdsfsdf</div>
    </div>
</div>
<script>
    function demand(pageNo) {
        window.location.href="/dnshosts/showLog?pageNo="+pageNo;
    }
    $("#log").click(function () {
        $("#a").css("display","block");
        $("#b").css("display","none");
    });
    $("#back").click(function () {
        $("#b").css("display","block");
        $("#a").css("display","none");
    })
</script>
</body>
</html>
