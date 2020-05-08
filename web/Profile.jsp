<html>
    <head>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script>
            function getUserData() {
                var htmlData;
                console.log("entered");
                $.ajax({
                    type: "GET",
                    url: "userData",
                    success: function (responseText) {
                        console.log(responseText);
                        htmlData = responseText;
                        document.getElementById('header').innerHTML = htmlData;
                    }
                });
            }
        </script>
        <script>
            $(document).ready(function () {
                $("#btnSubmit").click(function (event) {
                    event.preventDefault();
                    var form = $('#fileUploadForm')[0];
                    var data = new FormData(form);
                    data.append("CustomField", "This is some extra data, testing");
                    $("#btnSubmit").prop("disabled", true);
                    $.ajax({
                        type: "POST",
                        enctype: 'multipart/form-data',
                        url: "uploadFile",
                        data: data,
                        processData: false,
                        contentType: false,
                        cache: false,
                        timeout: 600000,
                        success: function (data) {
                            alert(data);
                        },
                        error: function (e) {
                            alert("fuck");
                            $("#result").text(e.responseText);
                            console.log("ERROR : ", e);
                            $("#btnSubmit").prop("disabled", false);
                        }
                    });
                });
            });
        </script>
        <script>
            function displayFiles() {
                $.ajax({
                    type:"GET",
                    url:"displayFiles",
                    success:function (responseText) {
                        alert(responseText);
                    },
                    error:function (f) {
                        alert("fuckoff");
                    }
                })
            }
        </script>
    </head>
    <body onload="getUserData()" >
    <p id="header"></p>
    <form method="POST" enctype="multipart/form-data" id="fileUploadForm">
        <input type="text" name="extraField"/><br/><br/>
        <input type="file" name="files"/><br/><br/>
        <input type="submit" value="Submit" id="btnSubmit"/>
    </form>
    <button onclick="displayFiles()">Display Files</button>
    </body>
</html>
