function getAllDevelopers(){
    $("#developer-view").html("Loading....Please wait");
    $.get("/developers", function(response){
        var developerHtmlTable = "";
        for (var i=0; i<response.length; i++){
            developerHtmlTable+="<tr>";
            developerHtmlTable+="<td>";
            developerHtmlTable+=i+1;
            developerHtmlTable+="</td>";
            developerHtmlTable+="<td>";
            developerHtmlTable+=response[i].name;
            developerHtmlTable+="</td>";
            developerHtmlTable+="<td>";
            developerHtmlTable+="<button class='btn btn-primary' onclick='deleteDeveloper(\""+response[i].name+"\")'>Delete</button>"
            developerHtmlTable+="</td>";
            developerHtmlTable+="</tr>";
        }
        $("#developer-view").html(developerHtmlTable);
    });
}
getAllDevelopers();

function showCreateDeveloper(){
    $("#developer-name").val("");
    $("#createDeveloperButton").hide();
    $("#createDeveloperInput").show();
}

function save(){
    reset();
    var devName = $("#developer-name").val();
    if(devName == undefined || devName == ""){
        return;
    }
    var input = {name: devName};
    $.ajax({
            url: '/developers',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(input),
            error: function(result){
                getAllDevelopers();
                alert("An error occurred");
            },
            success: function(result) {
                getAllDevelopers();
            }
    });
}

function deleteDeveloper(devName){
    reset();
    var input = {name: devName};
    $.ajax({
            url: '/developers',
            type: 'DELETE',
            contentType: 'application/json',
            data: JSON.stringify(input),
            error: function(result){
                getAllDevelopers();
                alert("An error occurred");
            },
            success: function(result) {
                getAllDevelopers();
            }
        });
}

function reset(){
    $("#createDeveloperButton").show();
    $("#createDeveloperInput").hide();
}