<!DOCTYPE html>
<html lang="en">
<#include "head.ftl">
<body>
<#include "navbar.ftl">
<#list feeds as feed>
    <#if feed.type == 4>
        <@follow_question feed=feed></@follow_question>
    <#elseif feed.type == 1>
        <@comment_question feed=feed></@comment_question>
    </#if>
</#list>
<#macro follow_question feed>
<div class="container" style="margin-top: 50px">
        <hr/>
        <div class="row">
            <div class="col-sm-8 col-lg-8 col-md-8 col-xl-8">
                <div class="row">
                    <div class="col-sm-3" align="center">
                        <img width="100px" height="100px" alt="image" src="${feed.userHead}"/>
                        <#if user??>
                            <#if feed.userID == user.id>
                        <button class="btn btn-light" data-toggle="modal" data-target="#messageModal"
                                style="margin-top: 20px" data-id="${feed.userID}" disabled>发送私信
                        </button>
                            <#else>
                        <button class="btn btn-light" data-toggle="modal" data-target="#messageModal"
                                style="margin-top: 20px" data-id="${feed.userID}">发送私信
                            </#if>
                        </#if>
                    </div>
                    <div class="col-sm-9">
                        <p>
                            <a href="/user/${feed.userID}">
                                ${feed.userName}
                            </a> 关注了问题
                            <a href="/question/${feed.questionID}">
                                ${feed.questionTitle}
                            </a>
                        </p>
                        <div class="row-fluid">
                            <div class="col-sm-12">
                                <p>
                                    ${feed.questionContent}
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
</div>
</#macro>
<#macro comment_question feed>
<div class="container" style="margin-top: 50px">
    <hr/>
    <div class="row">
        <div class="col-sm-8 col-lg-8 col-md-8 col-xl-8">
            <div class="row">
                <div class="col-sm-3" align="center">
                    <img width="100px" height="100px" alt="image" src="${feed.userHead}"/>
                        <#if user??>
                            <#if feed.userID == user.id>
                        <button class="btn btn-light" data-toggle="modal" data-target="#messageModal"
                                style="margin-top: 20px" data-id="${feed.userID}" disabled>发送私信
                        </button>
                            <#else>
                        <button class="btn btn-light" data-toggle="modal" data-target="#messageModal"
                                style="margin-top: 20px" data-id="${feed.userID}">发送私信
                            </#if>
                        </#if>
                </div>
                <div class="col-sm-9">
                    <p>
                        <a href="/user/${feed.userID}">
                            ${feed.userName}
                        </a> 评论了问题
                        <a href="/question/${feed.questionID}">
                            ${feed.questionTitle}
                        </a>
                    </p>
                    <div class="row-fluid">
                        <div class="col-sm-12">
                            <p>
                                ${feed.questionContent}
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</#macro>
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">提问</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <input type="text" class="form-control" id="question" placeholder="你的问题">
                </div>
                <div class="form-group">
                    <label for="textarea">问题描述</label>
                    <textarea class="form-control" id="textarea" rows="3" placeholder="问题背景，条件等"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="add()">发布</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="messageModal" tabindex="-1" role="dialog" aria-labelledby="messageModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">发送私信</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <input type="hidden" name="id" id="id" value="">
                <div class="form-group">
                    <label for="textarea">内容</label>
                    <textarea class="form-control" id="message" rows="3" placeholder="私信内容"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="send()">发布</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
