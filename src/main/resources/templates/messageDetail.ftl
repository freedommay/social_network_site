<!DOCTYPE html>
<html lang="en">
<#include "head.ftl">
<body>
<#include "navbar.ftl">
<div class="container" style="margin-top: 20px">
    <#list messages as message>
        <div class="row">
            <div class="col-sm-2 text-center">
                <img src="${message.headURL}">
            </div>
            <div class="col-sm-10">
                <div class="talk-bubble">
                    <div class="row">
                        <div class="talk-text col-sm-8">
                            <p>${message.message.content}</p>
                        </div>
                        <div class="col-sm-3" STYLE="padding: 1em">
                            <p>${message.message.createdDate?datetime}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </#list>
</div>
</body>
</html>