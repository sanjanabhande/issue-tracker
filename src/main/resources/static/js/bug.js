function getAllBugs(){
    $("#bug-view").html("Loading....Please wait");
    $.get("/bugs", function(response){
        var bugHTML = "";
        for (var i=0; i<response.length; i++){
            bugHTML+="<div class='col col-lg-4'>";
            bugHTML+="<div class='card'>";
            bugHTML+="<div class='card-header'>"
            bugHTML+="Bug - "+response[i].issue.id;
            bugHTML+="<a style='float:right' href='#' class='btn btn-primary' onclick='editBug("+JSON.stringify(response[i])+")'>Edit</a>";
            bugHTML+="</div>"
            bugHTML+="<div class='card-body'>";
            bugHTML+="<p class='card-text'> <b> Title: </b>";
            bugHTML+=response[i].issue.title;
            bugHTML+="</p>";
            bugHTML+="<p class='card-text'> <b> Description: </b>";
            bugHTML+=response[i].issue.description;
            bugHTML+="</p>";
            bugHTML+="<p class='card-text'> <b>Developer: </b>"
            if(response[i].issue.developer!=undefined){
                bugHTML+= response[i].issue.developer.name;
            }
            bugHTML+="</p>";
            bugHTML+="<p class='card-text'> <b>Priority: </b>";
            if(response[i].priority!=undefined){
                bugHTML+= response[i].priority;
            }
            bugHTML+="</p>";
            bugHTML+="<p class='card-text'> <b>Created Date: </b>"+response[i].issue.creationDate.slice(0,10)+"</p>";
            bugHTML+="</div>";
            bugHTML+="<div class='card-footer text-muted'>";
            bugHTML+=response[i].status;
            bugHTML+="</div>";
            bugHTML+="</div>";
            bugHTML+="</div>";
        }
        $("#bug-view").html(bugHTML);
    });
}
getAllBugs();

function editBug(bug){
    $("#bugTitleInput").val(bug.issue.title);
    $("#bugDescriptionInput").val(bug.issue.description);
    if(bug.issue.developer!=undefined || bug.issue.developer!=null){
        $("#bugDeveloperInput").val(bug.issue.developer.name);
    }
    $("#bugStatusInput").val(bug.status);
    $("#bugPriorityInput").val(bug.priority);
    $("#bugId").val(bug.issue.id);
    $('#bugEditModal').modal('show');
}

function createNewBug(){
    $("#bugTitleInput").val("");
    $("#bugDescriptionInput").val("");
    $("#bugDeveloperInput").val("");
    $("#bugStatusInput").val("NEW");
    $("#bugId").val("");
    $("#bugPriorityInput").val("MINOR");
    $('#bugEditModal').modal('show');
}

function saveBug(){
    var bugId = $("#bugId").val();
    if(bugId == undefined || bugId == null || bugId == ""){
        createBug();
    }else{
        updateBug();
    }
}
function createBug(){
    var bug = {
        issue:{
            developer :{}
        }
    };
    bug.issue.title = $("#bugTitleInput").val();
    bug.issue.description = $("#bugDescriptionInput").val();
    if($("#bugDeveloperInput").val() != "" || $("#bugDeveloperInput").val() != undefined){
        bug.issue.developer.name = $("#bugDeveloperInput").val();
    }
    bug.priority = $("#bugPriorityInput").val();
    bug.status = $("#bugStatusInput").val();
    if(bug.status == "" || bug.status == undefined || bug.status == null){
        bug.status = "NEW";
    }
    $.ajax({
            url: '/bugs',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(bug),
            error: function(result){
                $('#bugEditModal').modal('hide');
                getAllBugs();
                alert("An error occurred");
            },
            success: function(result) {
                $('#bugEditModal').modal('hide');
                getAllBugs();
            }
        });
}
function updateBug(){
    var bug = {
        issue:{
            developer :{}
        }
    };
    bug.issue.id = $("#bugId").val();
    bug.issue.title = $("#bugTitleInput").val();
    bug.issue.description = $("#bugDescriptionInput").val();
    bug.priority = $("#bugPriorityInput").val();
    bug.issue.developer.name = $("#bugDeveloperInput").val();
    bug.status = $("#bugStatusInput").val();
    $.ajax({
        url: '/bugs',
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(bug),
        error: function(result){
            $('#bugEditModal').modal('hide');
            getAllBugs();
            alert("An error occurred");
        },
        success: function(result) {
            $('#bugEditModal').modal('hide');
            getAllBugs();
        }
    });
}