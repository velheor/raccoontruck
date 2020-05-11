<#include "security.ftl">
<div class="card-rows">
    <#list loads as load>
    <div class="card border-dark">
        <a href="/special-loads/${load.id}" style="color: black">
            <span style="margin-right: 18px;">${"From: " + load.cityFrom}</span>
            <span style="margin-right: 18px">${"To: " + load.cityTo}</span>
            <span style="margin-right: 18px">${"Weight: " + load.weight}</span>
            <span>${"Price: " + load.price}</span>
            <div>
                <#if load.filename??>
                    <img src="/img/${load.filename}">
                </#if>
            </div>
        </a>
        <div class="border-top border-dark">
            <div class="card-footer text-muted">
                <p>Shipper:
                    <a href="/user-loads/${load.customer.id}"
                       style="color: black">${load.customer.username}</a>

                    <#if load.customer.id == сurrentUserId>
                        <a class="btn btn-primary"
                           href="/user-loads/${load.customer.id}?load=${load.id}"
                           style="margin-left: auto">
                            Edit
                        </a>
                    </#if>

                    <#if isDriver>
                        <a class="btn btn-primary"
                           href="/user-loads/${сurrentUserId}?load=${load.id}"
                           style="margin-left: auto">
                            Select
                        </a>
                    </#if>
                </p>
            </div>
        </div>
    </div>
</div>
<#else>
    No loads
</#list>
