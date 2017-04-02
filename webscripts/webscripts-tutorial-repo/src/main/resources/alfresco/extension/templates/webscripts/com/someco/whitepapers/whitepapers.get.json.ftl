<#assign datetimeformat="EEE, dd MMM yyyy HH:mm:ss zzz">
{"whitepapers" : [
    <#list whitepapers as child>
        {
            "name" : "${child.whitepaper.properties.name}",
            "title" : "${child.whitepaper.properties["cm:title"]}",
            "link" : "${url.context}${child.whitepaper.url}",
            "type" : "${child.whitepaper.mimetype}",
            "size" : "${child.whitepaper.size}",
            "id" : "${child.whitepaper.id}",
            "description" : "<#if child.whitepaper.properties["cm:description"]?exists && child.whitepaper.properties["cm:description"] != "">${child.whitepaper.properties["cm:description"]}</#if>",
            "pubDate" : "${child.whitepaper.properties["cm:modified"]?string(datetimeformat)}",
            "rating" : {
                "average" : "${child.rating.average!"0"}",
                "count" : "${child.rating.count!"0"}"
            }
        }
        <#if !(child.whitepaper == whitepapers?last.whitepaper)>,</#if>
    </#list>
    ]
}