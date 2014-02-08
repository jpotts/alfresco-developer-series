<#assign datetimeformat="EEE, dd MMM yyyy HH:mm:ss zzz">
<html>
    <body>
        <h3>Whitepapers</h3>
        <table>
            <#list whitepapers as child>
                <tr>
                    <td><b>Name</b></td>
        <td>${child.whitepaper.properties.name}</td>
                </tr>
                <tr>
                    <td><b>Title</b></td>
        <td>${child.whitepaper.properties["cm:title"]}</td>
                </tr>
                <tr>
                    <td><b>Link</b></td>
        <td><a href="${url.context}${child.whitepaper.url}?guest=true">${url.context}${child.whitepaper.url}</a></td>
                </tr>
                <tr>
                    <td><b>Type</b></td>
        <td>${child.whitepaper.mimetype}</td>
                </tr>
                <tr>
                    <td><b>Size</b></td>
        <td>${child.whitepaper.size}</td>
                </tr>
                <tr>
                    <td><b>Id</b></td>
        <td>${child.whitepaper.id}</td>
                </tr>
                <tr>
                    <td><b>Description</b></td>
                    <td><p><#if child.whitepaper.properties["cm:description"]?exists
                && child.whitepaper.properties["cm:description"] !=
                "">${child.whitepaper.properties["cm:description"]}</#if></p>
                    </td>
                </tr>
                <tr>
                    <td><b>Pub Date</b></td>
        <td>${child.whitepaper.properties["cm:modified"]?string(datetimeformat)}</td>
                </tr>
                <tr>
                    <td><b><a href="${url.serviceContext}/someco/rating.html?id=${child.whitepaper.id}&guest=true">Ratings</a></b></td>
                    <td>
                        <table>
                            <tr>
                                <td><b>Average</b></td>
                <td>${child.rating.average!"0"}</td>
                            </tr>
                            <tr>
                                <td><b>Count</b></td>
                <td>${child.rating.count!"0"}</td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <#if !(child.whitepaper == whitepapers?last.whitepaper)>
                    <tr>
            <td colspan="2" bgcolor="999999">&nbsp;</td>
        </tr>
                </#if>
            </#list>
        </table>
    </body>
</html>
