<!DOCTYPE html>
<html>
<head>
    <meta charset="utf‐8">
    <title>Hello World!</title>
</head>
<body>
    Hello ${name}!


    <table>
        <tr>
            <td>序号</td>
            <td>姓名</td>
            <td>年龄</td>
            <td>钱包</td>
            <td>出生时间</td>
        </tr>

        <#if stus??>
        <#list stus as stu>
            <tr>
                <td>${stu_index + 1}</td>
                <td <#if stu.name =='小明'>style="background:red;"</#if>>${stu.name}</td>
                <td>${stu.age}</td>
                <td>${stu.money}</td>
                <td>${stu.birthday?string("yyyy年MM月dd日")}</td>
            </tr>
        </#list>
        </#if>
    </table>


    姓名：${(stuMap['stu1'].name)!''}<br/>
    年龄：${(stuMap['stu1'].age)!''}<br/>


    输出stu1的学生信息：<br/>
    姓名：${(stuMap.stu1.name)!''}<br/>
    年龄：${(stuMap.stu1.age)!''}<br/>

    <table>
        <tr>
            <td>序号</td>
            <td>姓名</td>
            <td>年龄</td>
            <td>钱包</td>
        </tr>
        <#list stuMap?keys as k>
            <tr>
                <td>${k_index + 1}</td>
                <td>${stuMap[k].name}</td>
                <td>${stuMap[k].age}</td>
                <td>${stuMap[k].money}</td>
            </tr>
        </#list>
    </table>

<br/>
${point?c}

    <br/>

    <br/>
    <#assign text="{'bank':'工商银行','account':'10101920201920212'}" />
    <#assign data=text?eval />
    开户行：${data.bank} 账号：${data.account}

</body>
</html>
