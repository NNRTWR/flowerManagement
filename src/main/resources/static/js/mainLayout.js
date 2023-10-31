document.addEventListener('DOMContentLoaded', function() {
   
    const content = document.getElementById('content-layout');
   
    //default
    window.scrollTo(0, 0);
    content.style.marginLeft = '256px';
    window.onbeforeunload = function() {
        window.scrollTo(0, 0); 
    };
});