<!DOCTYPE html>
<html lang="en">
<#include "head.ftl">
<body>
<#include "navbar.ftl">
<div class="container" style="margin-top: 50px">
    <#list vos as vo>
        <hr/>
        <div class="row">
            <div class="col-sm-8 col-lg-8 col-md-8 col-xl-8">
                <div class="row">
                    <div class="col-sm-3" align="center">
                        <img width="100px" height="100px" alt="image" src="${vo.user.headURL}"/>
                        <#if user??>
                            <#if vo.user.id == user.id>
                        <button class="btn btn-light" data-toggle="modal" data-target="#messageModal"
                                style="margin-top: 20px" data-id="${vo.user.id}" disabled>发送私信
                        </button>
                            <#else>
                        <button class="btn btn-light" data-toggle="modal" data-target="#messageModal"
                                style="margin-top: 20px" data-id="${vo.user.id}">发送私信
                            </#if>
                        </#if>
                    </div>
                    <div class="col-sm-9">
                        <p>
                            <a href="/question/${vo.question.id}"
                               style="font-family: 微软雅黑;font-size: 18px;">
                                ${vo.question.title}
                            </a>
                        </p>
                        <div class="row">
                            <div class="col-sm-4">
                                <a href="/user/${vo.user.id}" class="badge badge-light">
                                    ${vo.user.name}
                                </a>
                            </div>
                            <div class="col-sm-6">
                                <p>
                                    ${vo.question.createdDate?datetime}
                                </p>
                            </div>
                        </div>
                        <div class="row-fluid">
                            <div class="col-sm-12">
                                <p>
                                    ${vo.question.content}
                                </p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-4">
                                <p>
                                    <#if user??>
                                    <#if vo.followed>
                                        <button type="button" class="btn btn-light" onclick="unfollow()"
                                                value="${vo.user.id}">取消关注
                                        </button>
                                    <#else>
                                        <button type="button" class="btn btn-light" onclick="follow()"
                                                value="${vo.user.id}">关注作者
                                        </button>
                                    </#if>
                                    </#if>
                                </p>
                            </div>
                            <div class="col-sm-4">
                                <p>
                                    评论数：${vo.question.commentCount}
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </#list>
</div>
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
