<#ftl output_format="HTML">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<#-- @ftlvariable name="data" type="io.qameta.allure.attachment.http.HttpResponseAttachment" -->

<#if (data.headers)?has_content>
<h4 style="font-family:Helvetica,Arial,sans-serif;">Headers:</h4>
<div>
    <pre class="preformated-text">
    <#list data.headers as name, value>
${name}: ${value}
    </#list>
    </pre>
</div>
</#if>

<#if (data.cookies)?has_content>
<h4 style="font-family:Helvetica,Arial,sans-serif;">Cookies:</h4>
<div>
    <pre class="preformated-text">
    <#list data.cookies as name, value>
${name}: ${value}
    </#list>
    </pre>
</div>
</#if>

<#if data.body??>
<h4 style="font-family:Helvetica,Arial,sans-serif;">Body:</h4>
<div>
    <pre class="preformated-text">${data.body}</pre>
</div>
</#if>