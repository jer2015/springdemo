<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="static/css/datetimepicker/bootstrap.min.css">
    <link rel="stylesheet" href="static/css/datetimepicker/bootstrap-datetimepicker.min.css">
    <script type="text/javascript" src="static/js/jquery/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="static/js/datetimepicker/bootstrap.min.js"></script>
    <script type="text/javascript" src="static/js/datetimepicker/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript" src="static/js/datetimepicker/bootstrap-datetimepicker.zh-CN.js"></script>
    <script>
        $(function () {
            var url = "http://127.0.0.1:8080/user/testAjax";
            $('#sub').click(function () {
                var obj = [];
                var o1 = {};
                var o2 = {};
                o1.u1 = '1';
                o1.u2 = 'a';
                obj.push(o1);
                o2.u1 = '2';
                o2.u2 = 'b';
                obj.push(o1);
                var a = JSON.stringify(obj);
                $.ajax({
                    url: url,
                    data: obj,
                    type: "get",
                    success: function (data) {
                        alert(data)
                    }
                });

                $('.form_datetime').datetimepicker({
                    language: 'zh-CN',
                    weekStart: 1,
                    todayBtn: 1,
                    autoclose: 1,
                    todayHighlight: 1,
                    startView: 1,
                    minView: 0,
                    maxView: 1,
                    forceParse: 0
                });
            })
        });

    </script>

</head>
<body>
<%--<a href="#" onclick="location.href='${pageContext.request.contextPath}/helloworld'">goto success page!</a><br/>--%>

<div class="container">
    <form action="/task/update" class="form-horizontal" role="form">
        <fieldset>
            <legend>Test</legend>
            <div class="form-group">
                <label for="jobName" class="col-md-2 control-label">Job Name</label>
                <div class="col-md-5">
                    <input id="jobName" name="jobName" class="form-control" size="16" type="text" value="TEST_JOB">
                </div>
            </div>

            <div class="form-group">
                <label for="jobTime" class="col-md-2 control-label">DateTime Picking</label>
                <div class="col-md-5">
                    <input id="jobTime" name="jobTime" class="form-control" size="16" type="text"
                           value="2017-01-01 00:00:00">
                    <button type="submit" class="btn btn-default">提交</button>
                </div>
            </div>
        </fieldset>
    </form>
</div>

<input type="button" value="sub" id="sub">

</body>
</html>