$(function () {
    let date = new Date();

    let year = date.getFullYear();
    const months
        = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
    let month = months[date.getMonth()];
    let day = date.getDate();
    $("body").append("<footer></footer>");
    $("footer").html("Sheridan College &nbsp; 📖 &nbsp; Anish Siwakoti &nbsp;&nbsp;").append(`${month} ${day}, ${year}`);
});