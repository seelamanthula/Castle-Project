window.addEventListener("load", function () {
    
      // Create a test FormData object
    
    
    function sendForm() {
    
        var formData = new FormData();
        formData.append('key1', 'value1');
        formData.append('key2', 'value2');
        formData.append("userfile", document.getElementsByName("file").files[0]);

        // Display the key/value pairs
        for (var pair of formData.entries()) {
            console.log(pair[0]+ ', ' + pair[1]); 
        }
        
    }
    
    var t = document.getElementById("butt");
    t.onclick = sendForm;
    
    
});