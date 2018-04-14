<html>
    <head>
        <script src="${url.context}/scripts/someco/prototype.js" type="text/javascript"></script>
        <script src="${url.context}/scripts/someco/rating-script.js" type="text/javascript"></script>
    </head>
    <body>
        <script type="text/javascript">
            function initEvents() {
                init_rating();

                $$('.rating').each(function(n){
                n.immediateDescendants().each(function(c){
                    Event.observe(c, 'click', submitRating);
                    });
                });
            }
            function submitRating(evt) {
                var tmp = Event.element(evt).getAttribute('id').substr(5);
                var widgetId = tmp.substr(0, tmp.indexOf('_'));
                var starNbr = tmp.substr(tmp.indexOf('_')+1);
                if (document.login.userId.value != undefined && document.login.userId.value != "") {
                    curUser = document.login.userId.value;
                } else {
                    curUser = "jpotts";
                }
                postRating(widgetId, starNbr, curUser);
            }           

            function getXmlHttpRequestObject() {
                if (window.XMLHttpRequest) {
                    return new XMLHttpRequest();
                } else if(window.ActiveXObject) {
                    return new ActiveXObject("Microsoft.XMLHTTP");
                } else {
                    document.getElementById('p_status').innerHTML =
                    'Status: Cound not create XmlHttpRequest Object.' +
                    'Consider upgrading your browser.';
                }
            }

            function postRating(id, rating, user) {
                if (receiveReq.readyState == 4 || receiveReq.readyState == 0) {
                    receiveReq.open("POST", "${url.serviceContext}/someco/rating?id=" + id + "&rating=" + rating + "&guest=true&user=" + user, true);
                    receiveReq.onreadystatechange = handleRatingPosted;
                    receiveReq.send(null);
                }
            }       
        
            function handleRatingPosted() {
                if (receiveReq.readyState == 4) {
                    window.location.reload(true);
                }
            }
            
            function deleteRatings(id) {
                if (receiveReq.readyState == 4 || receiveReq.readyState == 0) {
                    receiveReq.open("DELETE", "${url.serviceContext}/someco/rating/delete.html?id=" + id);
                    receiveReq.onreadystatechange = handleRatingsDeleted;
                    receiveReq.send(null);
                }
            }

            function handleRatingsDeleted() {
                if (receiveReq.readyState == 4) {
                    window.location.reload(true);
                }
            }
            
            var receiveReq = getXmlHttpRequestObject();         
            Event.observe(window, 'load', initEvents);
        </script>

        <p><a href="${url.serviceContext}/someco/whitepapers.html?guest=true">Back to the list</a> of whitepapers</p>
        <p>Node: ${args.id}</p>
        <p>Average: ${rating.average}</p>
        <p># of Ratings: ${rating.count}</p>
        <#if (rating.user > 0)>
            <p>User rating: ${rating.user}</p>
        </#if>
        <form name="login">
            Rater:<input name="userId"></input>
        </form>
        Rating: <div class="rating" id="rating_${args.id}" style="display:inline">${rating.average}</div>
        <p><a href="#" onclick=deleteRatings("${args.id}")>Delete ratings</a> for this node</p>
    </body>
</html>