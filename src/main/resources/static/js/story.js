function getAllStories(){
    $("#story-view").html("Loading....Please wait");
    $.get("/stories", function(response){
        $("#story-view").html(getStoryHTML(response));
    });
}

function getStoryHTML(response){
    var storyHTML = "";
    for (var i=0; i<response.length; i++){
        storyHTML+="<div class='col col-lg-4'>";
        storyHTML+="<div class='card'>";
        storyHTML+="<div class='card-header'>"
        storyHTML+="Story - "+response[i].issue.id;
        storyHTML+="<a style='float:right' href='#' class='btn btn-primary' onclick='editStory("+JSON.stringify(response[i])+")'>Edit</a>";
        storyHTML+="</div>"
        storyHTML+="<div class='card-body'>";
        storyHTML+="<p class='card-text'> <b> Title: </b>";
        storyHTML+=response[i].issue.title;
        storyHTML+="</p>";
        storyHTML+="<p class='card-text'> <b> Description: </b>";
        storyHTML+=response[i].issue.description;
        storyHTML+="</p>";
        storyHTML+="<p class='card-text'> <b>Developer: </b>"
        if(response[i].issue.developer!=undefined){
            storyHTML+= response[i].issue.developer.name;
        }
        storyHTML+="</p>";
        storyHTML+="<p class='card-text'> <b>Estimate: </b>";
        if(response[i].estimation!=undefined){
            storyHTML+= response[i].estimation;
        }
        storyHTML+="</p>";
        storyHTML+="<p class='card-text'> <b>Created Date: </b>"+response[i].issue.creationDate.slice(0,10)+"</p>";
        storyHTML+="</div>";
        storyHTML+="<div class='card-footer text-muted'>";
        storyHTML+=response[i].status;
        storyHTML+="</div>";
        storyHTML+="</div>";
        storyHTML+="</div>";
    }
    return storyHTML;
}

getAllStories();

function editStory(story){
    $('#storyDeveloperInput').prop("disabled", false);
    $("#storyTitleInput").val(story.issue.title);
    $("#storyDescriptionInput").val(story.issue.description);
    if(story.issue.developer!=undefined || story.issue.developer!=null){
        $("#storyDeveloperInput").val(story.issue.developer.name);
    }
    $("#storyStatusInput").val(story.status);
    $("#storyEstimateInput").val(story.estimation);
    $("#storyId").val(story.issue.id);
    $('#storyEditModal').modal('show');
}

function createNewStory(){
    $("#storyDeveloperInput").attr("disabled", "disabled");
    $("#storyTitleInput").val("");
    $("#storyDescriptionInput").val("");
    $("#storyDeveloperInput").val("");
    $("#storyStatusInput").val("NEW");
    $("#storyId").val("");
    $("#storyEstimateInput").val("");
    $('#storyEditModal').modal('show');
}

function saveStory(){
    var storyId = $("#storyId").val();
    if(storyId == undefined || storyId == null || storyId == ""){
        createStory();
    }else{
        updateStory();
    }
}
function createStory(){
    var story = {
        issue:{
            developer :{}
        }
    };
    story.issue.title = $("#storyTitleInput").val();
    story.issue.description = $("#storyDescriptionInput").val();
    if($("#storyDeveloperInput").val() != "" || $("#storyDeveloperInput").val() != undefined){
        story.issue.developer.name = $("#storyDeveloperInput").val();
    }
    story.estimation = $("#storyEstimateInput").val();
    story.status = $("#storyStatusInput").val();
    if(story.status == "" || story.status == undefined || story.status == null){
        story.status = "NEW";
    }
    $.ajax({
            url: '/stories',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(story),
            error: function(result){
                $('#storyEditModal').modal('hide');
                getAllStories();
                alert("An error occurred");
            },
            success: function(result) {
                $('#storyEditModal').modal('hide');
                getAllStories();
            }
        });
}
function updateStory(){
    var story = {
        issue:{
            developer :{}
        }
    };
    story.issue.id = $("#storyId").val();
    story.issue.title = $("#storyTitleInput").val();
    story.issue.description = $("#storyDescriptionInput").val();
    story.estimation = $("#storyEstimateInput").val();
    story.issue.developer.name = $("#storyDeveloperInput").val();
    story.status = $("#storyStatusInput").val();
    $.ajax({
        url: '/stories',
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(story),
        error: function(result){
            $('#storyEditModal').modal('hide');
            getAllStories();
            alert("An error occurred");
        },
        success: function(result) {
            $('#storyEditModal').modal('hide');
            getAllStories();
        }
    });
}