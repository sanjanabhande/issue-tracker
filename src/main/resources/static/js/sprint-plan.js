function planSprint(){
    $("sprint-plan-container").html("<div>Loading...please wait</div>");
    $.ajax({
        url: '/plan',
        type: 'POST',
        contentType: 'application/json',
        error: function(result){
            alert("An error occurred");
        },
        success: function(result) {
            populate(result);
        }
    });
}
function populate(result){
    var sprintHTML = "";
    for(var i = 0; i<result.sprints.length; i++){
        sprintHTML+="<div class='card'>";
        sprintHTML+="<div class='card-header'' id='heading"+i+"'>";
        sprintHTML+="<h2 class='mb-0'>";
        sprintHTML+="<button class='btn btn-link' type='button' data-toggle='collapse' data-target='#collapse"+i+"' aria-expanded='true' aria-controls='collapse"+i+"'>";
        sprintHTML+=result.sprints[i].name;
        sprintHTML+="</button>";
        sprintHTML+="</h2>";
        sprintHTML+="</div>";
        sprintHTML+="<div id='collapse"+i+"' class='collapse show' aria-labelledby='heading"+i+"' data-parent='#sprint-plan-accordian'>";
        sprintHTML+="<div class='card-body'>";
        sprintHTML+="<div class='row'>";
        sprintHTML+=getStoryHTML(result.sprints[i].stories);
        sprintHTML+="</div>";
        sprintHTML+="</div>";
        sprintHTML+="</div>";
        sprintHTML+="</div>";
    }
    $("#sprint-plan-accordian").html(sprintHTML);
}
