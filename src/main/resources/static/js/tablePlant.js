document.addEventListener("DOMContentLoaded", function () {
    console.log("Script loaded successfully");  

    var overviewButton = document.getElementById("overview-button");
    var subButton = document.getElementById("sub-button");
    var overviewTable = document.getElementById("overview-table");
    var subTable = document.getElementById("sub-table");

    overviewButton.addEventListener("click", function () {
        console.log("Overview button clicked");  
        overviewButton.classList.add("active");
        subButton.classList.remove("active");
        overviewTable.style.display = "table";
        subTable.style.display = "none";
    });

    subButton.addEventListener("click", function () {
        console.log("Sub button clicked");  
        subButton.classList.add("active");
        overviewButton.classList.remove("active");
        overviewTable.style.display = "none";
        subTable.style.display = "table";
    });
});
