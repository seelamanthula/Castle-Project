<!DOCTYPE>
<html>
    <head>
    </head>
    <body>
        
        <div id="divId">
            
            <h1>New Message</h1>
            <p></p>
            <object id="objid" width="500" height="600"></object>
            <img id="imgid" width="500" height="500">
            
        </div>
        <script>
            
            var url = "ask/file/string";
            
            var xhr = new XMLHttpRequest();
            console.log(url);
            console.log(xhr);
            
            xhr.onreadystatechange = function() {
                if (xhr.readyState == XMLHttpRequest.DONE) {
                	
                	console.log('Its DONE');
                	
            //        alert(xhr.responseText);
                    var data = xhr.responseText;
                 //   console.log(data);
                    
                    var id = document.getElementById("objid");
                    id.setAttribute("data", JSON.parse(data));

                    var id = document.getElementById("imgid");
                    id.setAttribute("src", JSON.parse(data));
                   
                }
            }
            xhr.open('get', url);
            xhr.send();
            console.log(xhr);

            </script>
    </body>
</html>